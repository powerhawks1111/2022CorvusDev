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
import edu.wpi.first.wpilibj.DoubleSolenoid;


public class DriveAndOperate {

    // private final XboxController m_controller = new XboxController(0);
    private final Joystick m_DriverController = new Joystick(0);
    public final Joystick m_OperatorController = new Joystick(1);

    private final SlewRateLimiter m_xspeedLimiter = new SlewRateLimiter(10);
    private final SlewRateLimiter m_yspeedLimiter = new SlewRateLimiter(10);
    private final SlewRateLimiter m_rotLimiter = new SlewRateLimiter(10);


    /**
     * State of all buttons
     */
    private double climbRotateSpeed = 0;
    private boolean rotateDown = false;
    private double driverXStick = 0;
    private double driverYStick = 0;
    private double driverRotateStick = 0;
    private boolean intakeButton = false;
    private boolean shootWithVisionButton = false;
    private boolean pixyLineUp = false; 
    private boolean spoolUpButton = false;

    //private double driverLeftHood = 0;
    private double testHood = 0;
    private boolean visionShoot = false;
    private double driverShootThrottle = 0;
    private boolean ejectBall = false;
    private boolean rotateBackwardButton = false;
    private boolean rotateForwardButton = false;
    private boolean lowGoal = false;
    private boolean resetNavx = false;
    private boolean manualButton = false;
    private boolean raiseClimb = false;
    

    /**
     * Runs subsystems on the robot based on pre-evaluated driver and operator inputs
     */
    public void driveAndOperate () {
        double xSpeed = -m_xspeedLimiter.calculate(MathUtil.applyDeadband(driverXStick, 0.05)) * Drivetrain.kMaxSpeed;
        double ySpeed = -m_yspeedLimiter.calculate(MathUtil.applyDeadband(-driverYStick, 0.05)) * Drivetrain.kMaxSpeed;
        double rot = m_rotLimiter.calculate(-MathUtil.applyDeadband(driverRotateStick, 0.05)) * Drivetrain.kMaxAngularSpeed;

        boolean fieldRelative = true;
        Objects.visionSubsystem.updateVision();
        Objects.shootSubsystem.shoot(visionShoot);

        Motors.climbLeader.set(climbRotateSpeed);

        Objects.indexSubsystem.updateEject(ejectBall); //update flags for background index
        
        Objects.indexSubsystem.updateManual(manualButton); //update flags for background index

        Objects.visionSubsystem.turnOnLeds();
        

        if (raiseClimb) {
            Motors.climbLeader.set(climbRotateSpeed);
        }

        /**
         * INTAKE
         */
        if (intakeButton) {
            Objects.intakeSubsystem.extendIntake();
            Objects.intakeSubsystem.runIntakeWheels(1);
        }
        else if (ejectBall) {
            Objects.intakeSubsystem.ejectBall();
        }
        else {
            Objects.intakeSubsystem.retractIntake();
            Objects.intakeSubsystem.runIntakeWheels(0);
        }


        /**
         * SHOOT
         */
        if (visionShoot) {
            Objects.visionSubsystem.turnOnLeds();
            Objects.shootSubsystem.setShooterRPM(Objects.visionSubsystem.rpmFromVision());
            rot = -Objects.visionSubsystem.turnToTargetPower()*(.67);
            Objects.hoodSubsystem.adjustHood(Objects.visionSubsystem.hoodAngleFromVision());
            SmartDashboard.putBoolean("isShootingButton", true);
        } else if (spoolUpButton) {
            Objects.shootSubsystem.spoolUp();
        }
        else if (lowGoal) {
            Objects.shootSubsystem.setShooterRPM(950); //TODO TEST LOWGOAL
            SmartDashboard.putBoolean("isShootingButton", false);
            Objects.hoodSubsystem.adjustHood(.05);
        } else {
            Motors.shooterLeader.stopMotor();
            Motors.shooterFollower.stopMotor();
            SmartDashboard.putBoolean("isShootingButton", false);
        }

        if(pixyLineUp) {
            Objects.hoodSubsystem.setHoodZero();
        
        }
        
        /**
         * RESET NAVX
         */
        if (resetNavx) {
            Objects.navx.reset();
        }

        /**
         * DRIVE THE ROBOT
         */
        Objects.driveSubsystem.driveSwerve(xSpeed, ySpeed, rot +.0001 , fieldRelative); //final movement; sends drive values to swerve
        Objects.drivetrain.updateOdometry(); //where are we? --- idk, we're all lost
    }

    
    /**
     * Reads the driver controller buttons and stores their current state
     */
    public void readDriverController() {
        driverXStick =  m_DriverController.getRawAxis(0);
        driverYStick =  m_DriverController.getRawAxis(1);
        driverRotateStick = m_DriverController.getRawAxis(4);
        //driverLeftHood = (m_DriverController.getRawAxis(3)+1)/2;
        visionShoot = m_DriverController.getRawButton(6); // right bumper lineup and shoot
        pixyLineUp = m_DriverController.getRawButton(5);
        resetNavx = m_DriverController.getRawButton(8) && m_DriverController.getRawButton(7);
        
    }
    
    /**
     * Reads the operator controller buttons and stores their current state
     */
    public void readOperatorController() {
        spoolUpButton = m_OperatorController.getRawButton(1); // a button
        ejectBall = m_OperatorController.getRawButton(2); // b button
        lowGoal= m_OperatorController.getRawButton(4); // y button
         //center buttons menu and hamburger buttons
        manualButton = m_OperatorController.getRawButton(3); //manual index x button
        intakeButton = m_OperatorController.getRawButton(6); //right bumper
        if (!raiseClimb) {
            raiseClimb = m_OperatorController.getRawButton(8) && m_OperatorController.getRawButton(7);
        }
        climbRotateSpeed = m_OperatorController.getRawAxis(3) - m_OperatorController.getRawAxis(2);
        
        
        // if (shootWithVisionButton) {
        //     Objects.hoodSubsystem.adjustHood(testHood);
        //     Objects.shootSubsystem.setShooterRPM(driverShootThrottle);
        // } else {
        //     Motors.shooterLeader.stopMotor();
        //     Motors.shooterFollower.stopMotor();
        // }
        /**
         * OTHER CLIMB
         */


        //old climb
        // if (m_OperatorController.getPOV() == 0) {
        //     releaseTopHooks = true;
        //     releaseBottomHooks = false;
        // }
        // else if (m_OperatorController.getPOV() == 180) {
        //     releaseBottomHooks = true;
        //     releaseTopHooks = false;
        // }
        // else {
        //     releaseTopHooks = false;
        //     releaseBottomHooks = false;
        // }


        // //old climb
        // if (m_OperatorController.getPOV() == 90) {
        //     rotateForwardButton = true;
        //     rotateBackwardButton = false;
        // }
        // else if (m_OperatorController.getPOV() == 270) {
        //     rotateBackwardButton = true;
        //     rotateForwardButton = false;
        // }
        // else {
        //     rotateForwardButton = false;
        //     rotateBackwardButton = false;
        // }
        // climbRotateSpeed = m_OperatorController.getRawAxis(3) - m_OperatorController.getRawAxis(2);
        
    }

    public void testJoystickRead () {
        // driverShootThrottle = (((m_DriverController.getRawAxis(3)+1))*1000 + 1000) * (25/9);
        // testHood = (m_OperatorController.getRawAxis(3)+1)*.1;
        // resetHood = m_DriverController.getRawButton(5);
        // shootWithVisionButton = m_DriverController.getRawButton(1);
        // climbRotateSpeed = m_OperatorController.getRawAxis(3) - m_OperatorController.getRawAxis(2);
        // intakeButton = m_OperatorController.getRawButton(6); //right bumper

    }
}
