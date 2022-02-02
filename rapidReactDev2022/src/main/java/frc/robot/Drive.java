package frc.robot;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.filter.SlewRateLimiter;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Relay;
import edu.wpi.first.wpilibj.Relay.Direction;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import com.kauailabs.navx.frc.AHRS; 
import frc.robot.Objects;
import org.photonvision.PhotonCamera;

public class Drive {
    private final XboxController m_controller = new XboxController(0);
    private final Joystick m_JoystickLeft = new Joystick(0);
    private final Joystick m_JoystickRight = new Joystick(1);
    private final SlewRateLimiter m_xspeedLimiter = new SlewRateLimiter(3);
    private final SlewRateLimiter m_yspeedLimiter = new SlewRateLimiter(3);
    private final SlewRateLimiter m_rotLimiter = new SlewRateLimiter(3);

    PhotonCamera camera = new PhotonCamera("mainVisionCamera");
    
    
    public void drive(Objects objects) {
        double xSpeed = m_xspeedLimiter.calculate(MathUtil.applyDeadband(m_JoystickLeft.getRawAxis(0), 0.05)) * Drivetrain.kMaxSpeed;
        double ySpeed = m_yspeedLimiter.calculate(MathUtil.applyDeadband(-m_JoystickLeft.getRawAxis(1), 0.05)) * Drivetrain.kMaxSpeed;
        double rot = 0;
        boolean fieldRelative = true;
        if (m_JoystickLeft.getRawButton(1)) {
            rot = m_rotLimiter.calculate(-MathUtil.applyDeadband(turnToTarget(), 0.05)) * Drivetrain.kMaxAngularSpeed;
            xSpeed = translateToPosition(objects, 0, 0, 1)[0];
            ySpeed = translateToPosition(objects, 0, 0, 1)[1];
            
            
        } else {
            rot = m_rotLimiter.calculate(-MathUtil.applyDeadband(m_JoystickRight.getRawAxis(0), 0.05)) * Drivetrain.kMaxAngularSpeed;
        }

        objects.m_swerve.drive(xSpeed, ySpeed, rot, fieldRelative);
    }

    public double turnToTarget() {
        var result = camera.getLatestResult();
        double xPosition = 0;
        SmartDashboard.putNumber("XPos", xPosition);
        boolean hasTargets = result.hasTargets();
        if (result.hasTargets()) {
            xPosition = result.getBestTarget().getYaw();
            SmartDashboard.putNumber("XPos", xPosition);
        } else {
            xPosition = 0;
        }
        
        return xPosition / 50;

    }
    public double[] translateToPosition(Objects objects, double desiredPositionX, double desiredPositionY, double speedScale) {
        double xDisplace =  objects.m_swerve.getCurrentPose2d().getX();
        double yDisplace = objects.m_swerve.getCurrentPose2d().getY();
        SmartDashboard.putNumber("Xposition", xDisplace);
        SmartDashboard.putNumber("YPosition", yDisplace);
        double xMovement = desiredPositionX - xDisplace;
        double yMovement = desiredPositionY - yDisplace;
        double[] componentSpeeds = {0,0};
        if (Math.abs(xMovement) >= 10 ) {
            if (xMovement<0) {
            xMovement = -speedScale;
            } else {
            xMovement = speedScale;
            }
            componentSpeeds[0] = xMovement;
        } else {
            componentSpeeds[0] =  Math.pow(speedScale * xMovement/10, 3);
        }

        if (Math.abs(yMovement) >= 10 ) {
            if (yMovement<0) {
                yMovement = -speedScale;
            } else {
                yMovement = speedScale;
            }
            componentSpeeds[1] = yMovement;    
        } else {
            componentSpeeds[1] = Math.pow(speedScale * yMovement/10, 3);
        }
        
 


        
        
        return componentSpeeds;
    }
}
