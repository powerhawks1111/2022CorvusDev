package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.MoveToSubsystem;

public class MoveToCommand extends CommandBase {
    @SuppressWarnings({ "PMD.UnusedPrivateTield", "PMD.SingularField" })
    private final MoveToSubsystem m_moveToSubsystem;

    public MoveToCommand(MoveToSubsystem moveToSubsystem, double desiredPositionX, double desiredPositionY, double heading, double strafeSpeed, double rotateSpeed) {
        m_moveToSubsystem = moveToSubsystem;
        addRequirements(moveToSubsystem);
    }

    @Override
    public void execute() {
        m_moveToSubsystem.translateToPosition(0, 15, 0, 0.25, 0.25);
    }

    @Override
    public boolean isFinished() {
        return m_moveToSubsystem.moveFinished();
    }

}
