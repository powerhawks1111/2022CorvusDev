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

    private double indexWheelSpeed = -0.065; //speed scale for index wheel, needs to be negative
    boolean awaitingBall = false;
    //constructor
    private SparkMaxPIDController indexPID = Motors.indexLeader.getPIDController();
    private double kP = 0.05;
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
        indexPID.setReference(power, ControlType.kDutyCycle);
    }

    /**
     * Runs constantly in the autonomous and teleop periodic functions
     * <br><br>
     * It checks the optical sensors and polls the shooter to see if it should run the index wheel
     */
    public void backgroundIndex() {
        
        
        if(Objects.shootSubsystem.shouldFeedToShooter()) {
            driveIndexWheel(-.12);//fast to shoot
            currentPosition = Motors.indexLeader.getEncoder().getPosition();
        } else if (Objects.indexFirstSensor.get()&& !Objects.indexShooterSensor.get() && !awaitingBall) {
            driveIndexWheel(indexWheelSpeed);
            currentPosition = Motors.indexLeader.getEncoder().getPosition();
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
