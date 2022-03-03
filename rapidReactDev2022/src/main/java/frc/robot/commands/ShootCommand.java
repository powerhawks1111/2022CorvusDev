package frc.robot.commands;

import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.HoodSubsystem;
import frc.robot.subsystems.ShootSubsystem;
import frc.robot.subsystems.VisionSubsystem;
import frc.robot.variables.Motors;
import frc.robot.variables.Objects;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class ShootCommand extends CommandBase{
    @SuppressWarnings({ "PMD.UnusedPrivateTield", "PMD.SingularField" })
    private final ShootSubsystem m_shootSubsystem;
    private HoodSubsystem m_hoodSubsystem;
    private Drivetrain m_drivetrain;
    private VisionSubsystem m_visionSubsystem;

    public ShootCommand(ShootSubsystem shootSubsystem, HoodSubsystem hoodSubsystem, Drivetrain drivetrain, VisionSubsystem visionSubsystem) {
        m_shootSubsystem = shootSubsystem;
        m_hoodSubsystem = hoodSubsystem;
        m_drivetrain = drivetrain;
        m_visionSubsystem = visionSubsystem;
        addRequirements(shootSubsystem);
        addRequirements(hoodSubsystem);
        addRequirements(drivetrain);
        addRequirements(visionSubsystem);
    }

    @Override
    public void execute() {
            m_shootSubsystem.setShooterRPM(m_visionSubsystem.rpmFromVision());
            double rot = -m_visionSubsystem.turnToTargetPower() ;
            m_hoodSubsystem.adjustHood(m_visionSubsystem.hoodAngleFromVision());
            m_drivetrain.drive(0, 0, rot, true);
    }

    public boolean isFinished() {
        if(!Objects.indexShooterSensor.get()) {
        //    Motors.shooterLeader.set(0);
        }
        return !Objects.indexShooterSensor.get();
    }
}
