package frc.robot.subsystems;
import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import frc.robot.variables.Motors;
import frc.robot.variables.Objects;

import com.revrobotics.SparkMaxPIDController;
import com.revrobotics.CANSparkMax.ControlType;
import com.revrobotics.RelativeEncoder;
import frc.robot.variables.Motors;
import frc.robot.variables.Objects;

public class IndexSubsystem {

    private double indexWheelSpeed = -0.05; //speed scale for index wheel, needs to be negative
    boolean awaitingBall = false;
    //constructor
    private SparkMaxPIDController indexPID = Motors.indexLeader.getPIDController();
    private double kP = 0.0001;
    private double kI = 0;
    private double kD = 0;

    double currentPosition;

    public IndexSubsystem () {
        indexPID.setP(kP);
        indexPID.setI(kI);
        indexPID.setD(kD);
    }

    /**
     * Sets the speed of the index wheel
     * @param power <ul><li>-1 to +1 speed to drive index motor</ul></li>
     */
    public void driveIndexWheel(double power) {
        Motors.indexLeader.set(power);
    }

    /**
     * Runs constantly in the autonomous and teleop periodic functions
     * <br><br>
     * It checks the optical sensors and polls the shooter to see if it should run the index wheel
     */
    public void backgroundIndex() {
        //currentPosition = Motors.hoodMotor.getEncoder().getPosition();
        currentPosition = -10;
        if(Objects.shootSubsystem.shouldFeedToShooter()) {
            driveIndexWheel(indexWheelSpeed);
        } else if (Objects.indexFirstSensor.get()&& !Objects.indexShooterSensor.get() && !awaitingBall) {
            driveIndexWheel(indexWheelSpeed);
            awaitingBall = true;
        } else if (Objects.indexShooterSensor.get() && awaitingBall) {
            awaitingBall = false;
            Motors.indexLeader.stopMotor();
            //indexPID.setReference(currentPosition, ControlType.kPosition);
        } else if (awaitingBall) {
            driveIndexWheel(indexWheelSpeed);
        } else {
            awaitingBall = false;
            Motors.indexLeader.stopMotor();
            //indexPID.setReference(currentPosition, ControlType.kPosition);
        }
    }

    public void testMotors () {
        Motors.intakeLeader.set(.55);
        Motors.indexLeader.set(-.25); //BACKWARD
        Motors.shooterLeader.set(.2);
        Motors.hoodMotor.set(.1); //UP

    }

    public void stopMotors () {
        Motors.intakeLeader.stopMotor();;
        Motors.indexLeader.stopMotor();
        Motors.shooterLeader.stopMotor();
        Motors.hoodMotor.stopMotor();
    }
}
