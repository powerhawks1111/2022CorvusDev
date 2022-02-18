package frc.robot;

import frc.robot.variables.Objects;
import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.filter.SlewRateLimiter;
import edu.wpi.first.wpilibj.Joystick;
import frc.robot.subsystems.Drivetrain;

public class DriveAndOperate {

    // private final XboxController m_controller = new XboxController(0);
    private final Joystick m_DriverLeft = new Joystick(0);
    private final Joystick m_DriverRight = new Joystick(1);
    private final Joystick m_OperatorController = new Joystick(2);

    private final SlewRateLimiter m_xspeedLimiter = new SlewRateLimiter(3);
    private final SlewRateLimiter m_yspeedLimiter = new SlewRateLimiter(3);
    private final SlewRateLimiter m_rotLimiter = new SlewRateLimiter(3);


    
    /**
     * Calculates drive values and sends the values to swerve drive
     * @param objects
     */
    public void drive () {
        double xSpeed = m_xspeedLimiter.calculate(MathUtil.applyDeadband(m_DriverLeft.getRawAxis(0), 0.05)) * Drivetrain.kMaxSpeed;
        double ySpeed = m_yspeedLimiter.calculate(MathUtil.applyDeadband(-m_DriverLeft.getRawAxis(1), 0.05)) * Drivetrain.kMaxSpeed;
        double rot = m_rotLimiter.calculate(-MathUtil.applyDeadband(m_DriverRight.getRawAxis(0), 0.05)) * Drivetrain.kMaxAngularSpeed;
        boolean fieldRelative = true;

        if (m_OperatorController.getRawButton(1)) { //shoot!

        }
        
        
        
        
        Objects.driveSubsystem.driveSwerve(xSpeed, ySpeed, rot, fieldRelative); //final movement; sends drive values to swerve
        Objects.drivetrain.updateOdometry(); //where are we?
    }
}
