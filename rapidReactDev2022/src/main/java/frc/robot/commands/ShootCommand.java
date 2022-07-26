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
    private boolean m_custom;
    private double m_customRPM;
    private boolean m_iDontGiveAFuck;

    public ShootCommand(ShootSubsystem shootSubsystem, HoodSubsystem hoodSubsystem, Drivetrain drivetrain, VisionSubsystem visionSubsystem, boolean custom, double customRPM, boolean iDontGiveAFuck) {
        m_shootSubsystem = shootSubsystem;
        m_hoodSubsystem = hoodSubsystem;
        m_drivetrain = drivetrain;
        m_visionSubsystem = visionSubsystem;
        m_custom = custom;
        m_customRPM = customRPM;
        m_iDontGiveAFuck = iDontGiveAFuck;
        addRequirements(shootSubsystem);
        addRequirements(hoodSubsystem);
        addRequirements(drivetrain);
        addRequirements(visionSubsystem);
    }

    @Override
    public void execute() {
            if (m_custom) {
                m_shootSubsystem.setShooterRPM(m_customRPM);
            } else {
            m_shootSubsystem.setShooterRPM(m_visionSubsystem.rpmFromVision());
            }
            if (m_iDontGiveAFuck) {
                Objects.indexSubsystem.updateManual(true);
            }
            double rot = -m_visionSubsystem.turnToTargetPower()*(.67);
            m_hoodSubsystem.adjustHood(m_visionSubsystem.hoodAngleFromVision());
            m_shootSubsystem.shoot(true);
            m_drivetrain.drive(0, 0, rot, true, false);
    }

    public boolean isFinished() {
        if(!Objects.indexShooterSensor.get()) {
        m_shootSubsystem.shoot(false);
        Objects.indexSubsystem.updateManual(false);
        Objects.drivetrain.drive(0, 0, 0, true, false);
        //Motors.shooterLeader.stopMotor();
        //Motors.shooterFollower.stopMotor();
        }

        return !Objects.indexShooterSensor.get();
    }
}
