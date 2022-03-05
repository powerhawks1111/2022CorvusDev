package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.MoveToSubsystem;
import frc.robot.variables.Objects;

public class MoveToCommand extends CommandBase {
    @SuppressWarnings({ "PMD.UnusedPrivateTield", "PMD.SingularField" })
    private final MoveToSubsystem m_moveToSubsystem;

    private double desiredX, desiredY, desiredHeading, translateSpeed, rotationSpeed, m_decelParam;
    private boolean m_intake; 

    public MoveToCommand(MoveToSubsystem moveToSubsystem, double desiredPositionX, double desiredPositionY, double heading, double strafeSpeed, double rotateSpeed, double decelParam, boolean intake) {
        m_moveToSubsystem = moveToSubsystem;
        desiredX = desiredPositionX;
        desiredY = desiredPositionY;
        desiredHeading = heading;
        translateSpeed = strafeSpeed;
        rotationSpeed = rotateSpeed;
        m_decelParam = decelParam;
        m_intake = intake;
        addRequirements(moveToSubsystem);
    }

    @Override
    public void execute() {
        m_moveToSubsystem.translateToPosition(desiredX, desiredY, desiredHeading, translateSpeed, m_decelParam);
        if (m_intake) {
            Objects.intakeSubsystem.extendIntake();
        } else {
            Objects.intakeSubsystem.retractIntake();
        }
    }

    @Override
    public boolean isFinished() {
        if (m_moveToSubsystem.moveFinished()){
            // Objects.intakeSubsystem.retractIntake();
        }
        return m_moveToSubsystem.moveFinished();
    }

}
