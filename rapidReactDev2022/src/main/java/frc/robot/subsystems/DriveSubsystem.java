package frc.robot.subsystems;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.filter.SlewRateLimiter;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Relay;
import edu.wpi.first.wpilibj.Relay.Direction;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import com.kauailabs.navx.frc.AHRS;

import frc.robot.variables.Objects;

import org.photonvision.PhotonCamera;

public class DriveSubsystem {



    PhotonCamera camera = new PhotonCamera("mainVisionCamera");
    
    /**
     * Does basic operation of swerve; does what it is told
     * @param objects
     * @param xSpeed x component
     * @param ySpeed y component 
     * @param rot rotation value
     * @param fieldRelative true= field relative
     */
    public void driveSwerve(Objects objects, double xSpeed, double ySpeed, double rot, boolean fieldRelative) {
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
