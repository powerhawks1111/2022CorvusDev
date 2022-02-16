package frc.robot;
import edu.wpi.first.wpilibj.Relay;
import frc.robot.variables.Objects;
import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.filter.SlewRateLimiter;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.XboxController;
import frc.robot.Operator;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Relay;
import edu.wpi.first.wpilibj.Relay.Direction;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.subsystems.Drivetrain;

public class Driver {

    private final XboxController m_controller = new XboxController(0);
    private final Joystick m_JoystickLeft = new Joystick(0);
    private final Joystick m_JoystickRight = new Joystick(1);

    private final SlewRateLimiter m_xspeedLimiter = new SlewRateLimiter(3);
    private final SlewRateLimiter m_yspeedLimiter = new SlewRateLimiter(3);
    private final SlewRateLimiter m_rotLimiter = new SlewRateLimiter(3);

    double xSpeed = m_xspeedLimiter.calculate(MathUtil.applyDeadband(m_JoystickLeft.getRawAxis(0), 0.05)) * Drivetrain.kMaxSpeed;
    double ySpeed = m_yspeedLimiter.calculate(MathUtil.applyDeadband(-m_JoystickLeft.getRawAxis(1), 0.05)) * Drivetrain.kMaxSpeed;
    double rot = m_rotLimiter.calculate(-MathUtil.applyDeadband(m_JoystickRight.getRawAxis(0), 0.05)) * Drivetrain.kMaxAngularSpeed;
    boolean fieldRelative = true;
    
    /**
     * Calculates drive values and sends the values to swerve drive
     * @param objects
     */
    public void drive (Objects objects) {
        if (objects.operator.getJoystick().getRawButton(1)) { //shoot!

        }
        
        
        
        
        objects.driveSubsystem.driveSwerve(objects, xSpeed, ySpeed, rot, fieldRelative); //final movement; sends drive values to swerve
        objects.m_swerve.updateOdometry(); //where are we?
    }
}
