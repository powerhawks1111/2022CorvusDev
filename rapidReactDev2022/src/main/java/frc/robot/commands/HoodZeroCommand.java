package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.HoodSubsystem;
import frc.robot.variables.Motors;
import frc.robot.variables.Objects;

public class HoodZeroCommand extends CommandBase{
    @SuppressWarnings({ "PMD.UnusedPrivateTield", "PMD.SingularField" })
    private final HoodSubsystem m_hoodSubsystem;

    public HoodZeroCommand(HoodSubsystem hoodSubsystem) {
        m_hoodSubsystem = hoodSubsystem;
    }

    @Override
    public void execute() {
        m_hoodSubsystem.setHoodZero();
        Objects.shootSubsystem.spoolUp();
    }

    public boolean isFinished() {
        if (m_hoodSubsystem.getHoodZeroed()) {
            Motors.hoodMotor.stopMotor();
        }
        return m_hoodSubsystem.getHoodZeroed();
    }
}
