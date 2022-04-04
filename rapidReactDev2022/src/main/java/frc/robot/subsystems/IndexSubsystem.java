package frc.robot.subsystems;
import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import frc.robot.variables.Motors;
import frc.robot.variables.Objects;
import frc.robot.DriveAndOperate;

import com.revrobotics.SparkMaxPIDController;
import com.revrobotics.CANSparkMax.ControlType;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import com.revrobotics.RelativeEncoder;
import frc.robot.variables.Motors;
import frc.robot.variables.Objects;

public class IndexSubsystem {

    private double indexWheelSpeed = -0.55;                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                          ; //speed scale for index wheel, needs to be negative
    boolean awaitingBall = false;
    //constructor
    private SparkMaxPIDController indexPID = Motors.indexLeader.getPIDController();
    private double kP = 0; //0.05
    private double kI = 0;
    private double kD = 0;
    private double kFF = 0.00000005;
    private boolean m_eject = false;
    private boolean m_manual = false;
    double currentPosition;

    public IndexSubsystem () {
        indexPID.setP(kP);
        indexPID.setI(kI);
        indexPID.setD(kD);
        indexPID.setFF(kFF);
    }

    /**
     * Sets the speed of the index wheel
     * @param power <ul><li>-1 to +1 speed to drive index motor</ul></li>
     */
    public void driveIndexWheel(double power) {
        indexPID.setReference(power, ControlType.kDutyCycle);
    }

    public void updateEject(Boolean eject) {
        m_eject = eject;
    }

    public void updateManual (Boolean manual) {
        m_manual = manual;
    }

    /**
     * Runs constantly in the autonomous and teleop periodic functions
     * <br><br>
     * It checks the optical sensors and polls the shooter to see if it should run the index wheel
     */
    public void backgroundIndex() {
        
        if (m_eject) {
            driveIndexWheel(1);
        } else if (m_manual) {
            driveIndexWheel(-1);
        }
        else if (Objects.shootSubsystem.shouldFeedToShooter()) { //
            driveIndexWheel(-.7);//fast to shoot
            currentPosition = Motors.indexLeader.getEncoder().getPosition();
        } else if (Objects.indexFirstSensor.get()&& !Objects.indexShooterSensor.get() && !awaitingBall) {
            driveIndexWheel(indexWheelSpeed);
            currentPosition = Motors.indexLeader.getEncoder().getPosition();
            SmartDashboard.putString("index", "running");
            awaitingBall = true;
        } else if (Objects.indexShooterSensor.get() && awaitingBall) {
            awaitingBall = false;
            indexPID.setReference(currentPosition, ControlType.kPosition);
        } else if (awaitingBall) {
            driveIndexWheel(indexWheelSpeed);
            currentPosition = Motors.indexLeader.getEncoder().getPosition();
        } else {
            awaitingBall = false;
            indexPID.setReference(Motors.indexLeader.getEncoder().getPosition(), ControlType.kPosition);
            SmartDashboard.putString("index", "not running");
            //indexPID.setReference(currentPosition, ControlType.kPosition);
        }
    }

    /**
     * Deprecated test function when initially testing motors
     */
    public void testMotors () {
        Motors.intakeLeader.set(.55);
        Motors.indexLeader.set(-.25); //BACKWARD
        Motors.shooterLeader.set(.2);
        Motors.hoodMotor.set(.1); //UP

    }

    /**
     * Stops the intake, index, shooter, and hood motors
     */
    public void stopMotors () {
        Motors.intakeLeader.stopMotor();
        Motors.indexLeader.stopMotor();
        Motors.shooterLeader.stopMotor();
        Motors.hoodMotor.stopMotor();
    }
}
