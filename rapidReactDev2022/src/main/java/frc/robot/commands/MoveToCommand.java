package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.MoveToSubsystem;
import frc.robot.variables.Objects;

public class MoveToCommand extends CommandBase {
    @SuppressWarnings({ "PMD.UnusedPrivateTield", "PMD.SingularField" })
    private final MoveToSubsystem m_moveToSubsystem;
    private final Objects objects;

    public MoveToCommand(MoveToSubsystem moveToSubsystem, Objects inputObjects) {
        m_moveToSubsystem = moveToSubsystem;
        addRequirements(moveToSubsystem);
        objects = inputObjects;
    }

    @Override
    public void execute() {
        m_moveToSubsystem.translateToPosition(objects, 0, 0, 1);
    }

    @Override
    public boolean isFinished() {
        return m_moveToSubsystem.moveFinished();
    }

}
