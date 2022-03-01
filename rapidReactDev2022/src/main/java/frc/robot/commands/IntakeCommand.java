package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.IndexSubsystem;
import frc.robot.subsystems.MoveToSubsystem;
import frc.robot.subsystems.IntakeAuto;
import frc.robot.variables.Objects;

public class IntakeCommand extends CommandBase {
    @SuppressWarnings({ "PMD.UnusedPrivateTield", "PMD.SingularField" })
    private final IntakeAuto m_intakeAuto;


    public IntakeCommand(IntakeAuto intakeAuto) {
        m_intakeAuto= intakeAuto;
        addRequirements(intakeAuto);
    }

    @Override
    public void execute() {
        m_intakeAuto.extendIntake();
    }

    @Override
    public boolean isFinished() {
        if (Objects.indexFirstSensor.get()) {
            m_intakeAuto.retractIntake();
        }
        return Objects.indexFirstSensor.get();
    }

}

