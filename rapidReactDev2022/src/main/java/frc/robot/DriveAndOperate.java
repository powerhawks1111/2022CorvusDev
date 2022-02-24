package frc.robot;

import frc.robot.variables.Motors;
import frc.robot.variables.Objects;
import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.filter.SlewRateLimiter;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.subsystems.HoodSubsystem;
import frc.robot.subsystems.Drivetrain;


public class DriveAndOperate {

    // private final XboxController m_controller = new XboxController(0);
    private final Joystick m_DriverLeft = new Joystick(0);
    public final Joystick m_DriverRight = new Joystick(1);
    public final Joystick m_OperatorController = new Joystick(2);

    private final SlewRateLimiter m_xspeedLimiter = new SlewRateLimiter(3);
    private final SlewRateLimiter m_yspeedLimiter = new SlewRateLimiter(3);
    private final SlewRateLimiter m_rotLimiter = new SlewRateLimiter(3);


    private double driverXStick = 0;
    private double driverYStick = 0;
    private double driverRotateStick = 0;
    private boolean intakeButton = false;
    private boolean shootWithVisionButton = false;
    private boolean shootNormalButton = false;
    private boolean climbForwardButton = false;
    private boolean climbBackwardButton = false;

    /**
     * Calculates drive values and sends the values to swerve drive
     */
    public void driveAndOperate () {
        double xSpeed = m_xspeedLimiter.calculate(MathUtil.applyDeadband(driverXStick, 0.05)) * Drivetrain.kMaxSpeed;
        double ySpeed = m_yspeedLimiter.calculate(MathUtil.applyDeadband(-driverYStick, 0.05)) * Drivetrain.kMaxSpeed;
        double rot = m_rotLimiter.calculate(-MathUtil.applyDeadband(driverRotateStick, 0.05)) * Drivetrain.kMaxAngularSpeed;
        boolean fieldRelative = true;

        Objects.hoodSubsystem.adjustHood();

        if (intakeButton) {
            Objects.intakeSubsystem.extendIntake();
            Objects.intakeSubsystem.runIntakeWheels(0.5);
        }
        else {
            Objects.intakeSubsystem.retractIntake();
            Objects.intakeSubsystem.runIntakeWheels(0);
        }
        
        if (shootNormalButton) {
            Objects.shootSubsystem.setShooterRPM(800);
        }
        else {
            Motors.shooterLeader.stopMotor();
        }

        if (climbForwardButton) {
            Objects.climbSubsystem.driveClimbMotor(.1);
        } else if (climbBackwardButton) {
            Objects.climbSubsystem.driveClimbMotor(-.1);
        } else {
            Motors.climbLeader.stopMotor();
        }
        
        SmartDashboard.putNumber("xSpeed", xSpeed);
        Objects.driveSubsystem.driveSwerve(xSpeed, ySpeed, rot, fieldRelative); //final movement; sends drive values to swerve
        Objects.drivetrain.updateOdometry(); //where are we?
    }

    /**
     * Reads the driver controller buttons and stores their current state
     */
    public void readDriverController() {
        driverXStick = m_DriverLeft.getRawAxis(0);
        driverYStick = m_DriverLeft.getRawAxis(1);
        driverRotateStick = m_DriverRight.getRawAxis(0);

        intakeButton = m_DriverRight.getRawButton(1);
    }
    
    /**
     * Reads the operator controller buttons and stores their current state
     */
    public void readOperatorController() {
        shootWithVisionButton = m_OperatorController.getRawButton(1);
        shootNormalButton = m_OperatorController.getRawButton(2);
        climbForwardButton = m_OperatorController.getRawButton(3);
        climbBackwardButton = m_OperatorController.getRawButton(4);
    }
}
