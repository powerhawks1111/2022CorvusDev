package frc.robot.subsystems;

import frc.robot.variables.Motors;
import frc.robot.variables.Objects;

public class IndexSubsystem {

    private double indexWheelSpeed = 0.5; //speed scale for index wheel

    //constructor
    public IndexSubsystem () {

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
        if(Objects.indexMidSensor.get()  && !Objects.indexShooterSensor.get()){
            driveIndexWheel(indexWheelSpeed);
        }
        else if (Objects.shootSubsystem.shouldFeedToShooter()) {
            driveIndexWheel(indexWheelSpeed);
        }
        else {
            Motors.indexLeader.stopMotor();
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
