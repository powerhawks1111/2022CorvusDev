package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.MoveToSubsystem;

public class MoveToCommand extends CommandBase {
    @SuppressWarnings({ "PMD.UnusedPrivateTield", "PMD.SingularField" })
    private final MoveToSubsystem m_moveToSubsystem;

    public MoveToCommand(MoveToSubsystem moveToSubsystem) {
        m_moveToSubsystem = moveToSubsystem;
        addRequirements(moveToSubsystem);
    }

    @Override
    public void execute() {
        m_moveToSubsystem.translateToPosition(0, 0, 1);
    }

    @Override
    public boolean isFinished() {
        return m_moveToSubsystem.moveFinished();
    }

}
