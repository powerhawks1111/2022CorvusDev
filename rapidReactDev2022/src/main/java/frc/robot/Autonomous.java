package frc.robot;

import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
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

    public void redPath() { //THREE BALL AUTO
        if(!start) {
            commandsList.add(new HoodZeroCommand(Objects.hoodSubsystem));
            commandsList.add(new MoveToCommand(Objects.moveToSubsystem, 37, 0, 95, .95, 0.75, 10));
            //commandsList.add(new IntakeCommand(Objects.intakeAuto));
            commandsList.add(new ReadyToShootCommand());
            commandsList.add (new ShootCommand(Objects.shootSubsystem, Objects.hoodSubsystem, Objects.drivetrain, Objects.visionSubsystem));
            commandsList.add(new ReadyToShootCommand());
            commandsList.add (new ShootCommand(Objects.shootSubsystem, Objects.hoodSubsystem, Objects.drivetrain, Objects.visionSubsystem));
        
            commandsList.add (new MoveToCommand(Objects.moveToSubsystem, -10, 90, 175, 1.5, .75, 10));
            commandsList.add (new MoveToCommand(Objects.moveToSubsystem, 26, 242, 160, 2, .75, 20));
            commandsList.add (new MoveToCommand(Objects.moveToSubsystem, -5, 80, 135, 2, .75, 30));

            commandsList.add(new ReadyToShootCommand());
            commandsList.add (new ShootCommand(Objects.shootSubsystem, Objects.hoodSubsystem, Objects.drivetrain, Objects.visionSubsystem));
            commandsList.add(new ReadyToShootCommand());
            commandsList.add (new ShootCommand(Objects.shootSubsystem, Objects.hoodSubsystem, Objects.drivetrain, Objects.visionSubsystem));
            //commandsList.add(new HoodZeroCommand(Objects.hoodSubsystem));
            // commandsList.add(new MoveToCommand(Objects.moveToSubsystem, -35, 3, 0, 0.4, 0.25));
            // commandsList.add(new MoveToCommand(Objects.moveToSubsystem,0 , 30, 0, 0.5, 0.25));
            

            start = true;
        }
        startAutonomous();
    }

    public void orangePath() {
        if (!start) {
            commandsList.add(new ParallelCommandGroup(new HoodZeroCommand(Objects.hoodSubsystem), new MoveToCommand(Objects.moveToSubsystem, 37, 0, 90, 0.5, 0.15, 10)));
            // commandsList.add(new HoodZeroCommand(Objects.hoodSubsystem));
            // commandsList.add(new MoveToCommand(Objects.moveToSubsystem, 37, 0, 90, 0.5, 0.15));
            //commandsList.add(new IntakeCommand(Objects.intakeAuto));
            commandsList.add(new ReadyToShootCommand());
            commandsList.add (new ShootCommand(Objects.shootSubsystem, Objects.hoodSubsystem, Objects.drivetrain, Objects.visionSubsystem));
            commandsList.add(new ReadyToShootCommand());
            commandsList.add (new ShootCommand(Objects.shootSubsystem, Objects.hoodSubsystem, Objects.drivetrain, Objects.visionSubsystem));
            commandsList.add(new MoveToCommand(Objects.moveToSubsystem, 37, 0, 180, 0.5, 0.25, 10));
            commandsList.add (new MoveToCommand(Objects.moveToSubsystem, 0, 80, 180, .65, .95, 10));

            commandsList.add(new ReadyToShootCommand());
            commandsList.add (new ShootCommand(Objects.shootSubsystem, Objects.hoodSubsystem, Objects.drivetrain, Objects.visionSubsystem));
            start=true;
        }
        startAutonomous();

    }

    public void yellowPath() { //TWO BALL
        commandsList.add(new HoodZeroCommand(Objects.hoodSubsystem));
            commandsList.add(new MoveToCommand(Objects.moveToSubsystem, 37, 0, 90, 0.5, 0.15, 10));
            //commandsList.add(new IntakeCommand(Objects.intakeAuto));
            commandsList.add(new ReadyToShootCommand());
            commandsList.add (new ShootCommand(Objects.shootSubsystem, Objects.hoodSubsystem, Objects.drivetrain, Objects.visionSubsystem));
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
