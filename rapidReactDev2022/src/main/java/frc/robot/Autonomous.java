package frc.robot;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.commands.HoodZeroCommand;
import frc.robot.commands.IntakeCommand;
import frc.robot.commands.MoveToCommand;
import frc.robot.commands.ReadyToShootCommand;
import frc.robot.subsystems.HoodSubsystem;
import frc.robot.variables.Objects;
import frc.robot.commands.ShootCommand;

import java.util.ArrayList;

public class Autonomous {

    private ArrayList<CommandBase> commandsList;
    boolean start = false;
    
    public Autonomous() {
        commandsList = new ArrayList<CommandBase>();
    }

    public void redPath() {
        if(!start) {
            commandsList.add(new HoodZeroCommand(Objects.hoodSubsystem));
            commandsList.add(new MoveToCommand(Objects.moveToSubsystem, 37, 0, 0, 0.5, 0.25));
            //commandsList.add(new IntakeCommand(Objects.intakeAuto));
            commandsList.add(new ReadyToShootCommand());
            commandsList.add (new ShootCommand(Objects.shootSubsystem, Objects.hoodSubsystem, Objects.drivetrain, Objects.visionSubsystem));
            commandsList.add(new ReadyToShootCommand());
            commandsList.add (new ShootCommand(Objects.shootSubsystem, Objects.hoodSubsystem, Objects.drivetrain, Objects.visionSubsystem));
            commandsList.add (new MoveToCommand(Objects.moveToSubsystem, 35, 60, 0, .5, .25));
            //commandsList.add(new HoodZeroCommand(Objects.hoodSubsystem));
            // commandsList.add(new MoveToCommand(Objects.moveToSubsystem, -35, 3, 0, 0.4, 0.25));
            // commandsList.add(new MoveToCommand(Objects.moveToSubsystem,0 , 30, 0, 0.5, 0.25));
            

            start = true;
        }
        startAutonomous();
    }

    public void orangePath() {

    }

    public void yellowPath() {

    }

    public void greenPath() {

    }

    public void bluePath() {

    }

    public void purplePath() {

    }

    public void defaultPath() {

    }

    public void startAutonomous() {
        Objects.scheduler.addCommands(commandsList);
        Objects.scheduler.run();
    }
}
