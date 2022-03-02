package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.HoodSubsystem;
import frc.robot.variables.Motors;
import frc.robot.variables.Objects;

public class ReadyToShootCommand extends CommandBase{
    @SuppressWarnings({ "PMD.UnusedPrivateTield", "PMD.SingularField" })

    public ReadyToShootCommand() {
        
    }

    @Override
    public void execute() {

    }

    public boolean isFinished() {
        return (Objects.indexShooterSensor.get());
    }
}
