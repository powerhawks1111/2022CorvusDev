package frc.robot.subsystems;

import frc.robot.variables.Motors;
import frc.robot.variables.Objects;

import edu.wpi.first.wpilibj.DoubleSolenoid;

public class IntakeSubsystem {

    public IntakeSubsystem() {

    }

    /**
     * Activates relay to extend intake piston
     */
    public void extendIntake() {
        Objects.intakePistonRight.set(DoubleSolenoid.Value.kReverse);
        Objects.intakePistonLeft.set(DoubleSolenoid.Value.kReverse);
    }

    /**
     * Activates relay to retract intake piston
     */
    public void retractIntake() {
        Objects.intakePistonRight.set(DoubleSolenoid.Value.kForward);
        Objects.intakePistonLeft.set(DoubleSolenoid.Value.kForward);
    }

    /**
     * Rund the intake motor
     * @param intakeSpeed -1 to +1 speed to run the intake motor
     */
    public void runIntakeWheels(double intakeSpeed) {
        Motors.intakeLeader.set(intakeSpeed);
    }
}
