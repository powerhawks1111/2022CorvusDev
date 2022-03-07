package frc.robot;

import frc.robot.variables.Motors;
import frc.robot.variables.Objects;

import javax.swing.plaf.basic.BasicInternalFrameTitlePane.RestoreAction;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.filter.SlewRateLimiter;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.subsystems.HoodSubsystem;
import frc.robot.subsystems.ShootSubsystem;
import frc.robot.subsystems.Drivetrain;


public class DriveAndOperate {

    // private final XboxController m_controller = new XboxController(0);
    private final Joystick m_DriverController = new Joystick(0);
    public final Joystick m_OperatorController = new Joystick(1);

    private final SlewRateLimiter m_xspeedLimiter = new SlewRateLimiter(10);
    private final SlewRateLimiter m_yspeedLimiter = new SlewRateLimiter(10);
    private final SlewRateLimiter m_rotLimiter = new SlewRateLimiter(10);


    private double driverXStick = 0;
    private double driverYStick = 0;
    private double driverRotateStick = 0;
    private boolean intakeButton = false;
    //private boolean shootWithVisionButton = false;
    private boolean spoolUpButton = false;
    private boolean climbForwardButton = false;
    private boolean climbBackwardButton = false;
    //private double driverLeftHood = 0;
    //private boolean testHood = false;
    private boolean visionShoot = false;
    //private double driverRightShootThrottle = 0;
    private boolean ejectBall = false;
    private boolean rotateBackwardButton = false;
    private boolean rotateForwardButton = false;
    private boolean lowGoal = false;
    private boolean resetNavx = false;
    private boolean manualButton = false;


    /**
     * Calculates drive values and sends the values to swerve drive
     */
    public void driveAndOperate () {
        double xSpeed = -m_xspeedLimiter.calculate(MathUtil.applyDeadband(driverXStick, 0.05)) * Drivetrain.kMaxSpeed;
        double ySpeed = -m_yspeedLimiter.calculate(MathUtil.applyDeadband(-driverYStick, 0.05)) * Drivetrain.kMaxSpeed;
        double rot = m_rotLimiter.calculate(-MathUtil.applyDeadband(driverRotateStick, 0.05)) * Drivetrain.kMaxAngularSpeed;

        boolean fieldRelative = true;
        Objects.visionSubsystem.updateVision();
        Objects.shootSubsystem.shoot(visionShoot);

        if (rotateForwardButton) {
            Objects.climbSubsystem.rotateClimb(-.1);
        } else if (rotateBackwardButton) {
            Objects.climbSubsystem.rotateClimb(.1);
        } else {
            Motors.climbHigher.stopMotor();
        }

        if (intakeButton) {
            Objects.intakeSubsystem.extendIntake();
            Objects.intakeSubsystem.runIntakeWheels(0.6);
        }
        else {
            Objects.intakeSubsystem.retractIntake();
            Objects.intakeSubsystem.runIntakeWheels(0);
        }

        Objects.indexSubsystem.updateEject(ejectBall);
        Objects.indexSubsystem.updateManual(manualButton);
        // Objects.indexSubsystem.shoot(m_DriverRight.getRawButton(3));
        if (visionShoot) {
            Objects.shootSubsystem.setShooterRPM(Objects.visionSubsystem.rpmFromVision());
            rot = -Objects.visionSubsystem.turnToTargetPower()*(.76);
            Objects.hoodSubsystem.adjustHood(Objects.visionSubsystem.hoodAngleFromVision());
            SmartDashboard.putBoolean("isShootingButton", true);
        } else if (spoolUpButton) {
            Objects.shootSubsystem.spoolUp();
        }
        else if (lowGoal) {
            Objects.shootSubsystem.setShooterRPM(900);
            SmartDashboard.putBoolean("isShootingButton", false);
            Objects.hoodSubsystem.adjustHood(.1);
        } else {
            Motors.shooterLeader.stopMotor();
            SmartDashboard.putBoolean("isShootingButton", false);
        }

        if (climbForwardButton) {
            Objects.climbSubsystem.driveClimbMotor(.8);
        } else if (climbBackwardButton) {
            Objects.climbSubsystem.driveClimbMotor(-.8);
        } else {
            Motors.climbLeader.stopMotor();
        }
        if (resetNavx) {
            Objects.navx.reset();
        }
        // if (m_DriverLeft.getRawButton(1)) {
        //     Objects.hoodSubsystem.setHoodZero();
            
        // }

        SmartDashboard.putNumber("xSpeed", xSpeed);
        Objects.driveSubsystem.driveSwerve(xSpeed, ySpeed, rot +.0001 , fieldRelative); //final movement; sends drive values to swerve
        Objects.drivetrain.updateOdometry(); //where are we?
    }

    /**
     * Reads the driver controller buttons and stores their current state
     */
    public void readDriverController() {
        driverXStick =  m_DriverController.getRawAxis(0);
        driverYStick =  m_DriverController.getRawAxis(1);
        driverRotateStick = m_DriverController.getRawAxis(4);
        //driverLeftHood = (m_DriverController.getRawAxis(3)+1)/2;
        visionShoot = m_DriverController.getRawButton(6);
        
    }
    
    /**
     * Reads the operator controller buttons and stores their current state
     */
    public void readOperatorController() {
        spoolUpButton = m_OperatorController.getRawButton(1);
        ejectBall = m_OperatorController.getRawButton(2);
        lowGoal= m_OperatorController.getRawButton(4);
        resetNavx = m_OperatorController.getRawButton(8);
        manualButton = m_OperatorController.getRawButton(3);
        //lineupButton = m_OperatorController.getRawButton(6); //right bumper

        if (m_OperatorController.getPOV() == 0) {
            climbForwardButton = true;
            climbBackwardButton = false;
        }
        else if (m_OperatorController.getPOV() == 180) {
            climbBackwardButton = true;
            climbForwardButton = false;
        }
        else {
            climbForwardButton = false;
            climbBackwardButton = false;
        }

        if (m_OperatorController.getRawButton(6)){
            intakeButton = true;
        }
        else if (m_OperatorController.getRawButton(5)) {
            intakeButton = false;
        }
        
        if (m_OperatorController.getPOV() == 90) {
            rotateForwardButton = true;
            rotateBackwardButton = false;
        }
        else if (m_OperatorController.getPOV() == 270) {
            rotateBackwardButton = true;
            rotateForwardButton = false;
        }
        else {
            rotateForwardButton = false;
            rotateBackwardButton = false;
        }
    }

    public Boolean shootIndex () {
        // return m_DriverRight.getRawButton(3);
        return false;
    }
}
