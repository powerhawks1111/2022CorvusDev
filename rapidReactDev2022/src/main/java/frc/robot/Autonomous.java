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

    public void redPath() { //FOUR BALL AUTO
        if(!start) {
            commandsList.add(new HoodZeroCommand(Objects.hoodSubsystem));
            commandsList.add(new MoveToCommand(Objects.moveToSubsystem, 29, 1, 90, .7, 0.6, 10, true)); //FIRST BALL
            //commandsList.add(new IntakeCommand(Objects.intakeAuto));
            commandsList.add(new ReadyToShootCommand());
            commandsList.add (new ShootCommand(Objects.shootSubsystem, Objects.hoodSubsystem, Objects.drivetrain, Objects.visionSubsystem, true, 1490)); //SHOOT First two
            commandsList.add(new ReadyToShootCommand());
            commandsList.add (new ShootCommand(Objects.shootSubsystem, Objects.hoodSubsystem, Objects.drivetrain, Objects.visionSubsystem, true, 1490));
        
            commandsList.add (new MoveToCommand(Objects.moveToSubsystem, -5, 90, 175, .7, .6, 20, true));//SECOND BALL
            commandsList.add (new MoveToCommand(Objects.moveToSubsystem, 40, 253, 175, .9, .6, 40, true));//THIRD BALL,  OG 28, 234
            //commandsList.add(new IntakeCommand( Objects.intakeAuto));
    
            commandsList.add (new MoveToCommand(Objects.moveToSubsystem, 40, 48, 135, 1, .6, 30, true)); //GO HOME

            commandsList.add(new ReadyToShootCommand());
            commandsList.add (new ShootCommand(Objects.shootSubsystem, Objects.hoodSubsystem, Objects.drivetrain, Objects.visionSubsystem, false, Objects.visionSubsystem.rpmFromVision()));
            commandsList.add(new ReadyToShootCommand());
            commandsList.add (new ShootCommand(Objects.shootSubsystem, Objects.hoodSubsystem, Objects.drivetrain, Objects.visionSubsystem, false, Objects.visionSubsystem.rpmFromVision()));
            //commandsList.add(new HoodZeroCommand(Objects.hoodSubsystem));
            // comman54dsList.add(new MoveToCommand(Objects.moveToSubsystem, -35, 3, 0, 0.4, 0.25));
            // commandsList.add(new MoveToCommand(Objects.moveToSubsystem,0 , 30, 0, 0.5, 0.25));
            

            start = true;
        }
        startAutonomous();
    }

    public void orangePath() { //THREE BALL PARALLEL
        if (!start) {
            commandsList.add(new ParallelCommandGroup(new HoodZeroCommand(Objects.hoodSubsystem), new MoveToCommand(Objects.moveToSubsystem, 30, -5, 90, 0.5, .15, 10, true))); //FIRST BALL
            // commandsList.add(new HoodZeroCommand(Objects.hoodSubsystem));
            // commandsList.add(new MoveToCommand(Objects.moveToSubsystem, 37, 0, 90, 0.5, 0.15));
            //commandsList.add(new IntakeCommand(Objects.intakeAuto));
            commandsList.add(new ReadyToShootCommand());
            commandsList.add (new ShootCommand(Objects.shootSubsystem, Objects.hoodSubsystem, Objects.drivetrain, Objects.visionSubsystem, true, 1550));
            commandsList.add(new ReadyToShootCommand());
            commandsList.add (new ShootCommand(Objects.shootSubsystem, Objects.hoodSubsystem, Objects.drivetrain, Objects.visionSubsystem, false, Objects.visionSubsystem.rpmFromVision()));
            commandsList.add (new MoveToCommand(Objects.moveToSubsystem, -10, 90, 180, .65, .95, 10, true)); //SECOND BALL

            commandsList.add(new ReadyToShootCommand());
            commandsList.add (new ShootCommand(Objects.shootSubsystem, Objects.hoodSubsystem, Objects.drivetrain, Objects.visionSubsystem, false, Objects.visionSubsystem.rpmFromVision()));
            start=true;
        }
        startAutonomous();

    }

    public void yellowPath() { //TWO BALL in four ball position
        commandsList.add(new HoodZeroCommand(Objects.hoodSubsystem));
            commandsList.add(new MoveToCommand(Objects.moveToSubsystem, 29, 1, 90, 0.5, 0.15, 10, true)); //FIRST BALL
            //commandsList.add(new IntakeCommand(Objects.intakeAuto));
            commandsList.add(new ReadyToShootCommand());
            commandsList.add (new ShootCommand(Objects.shootSubsystem, Objects.hoodSubsystem, Objects.drivetrain, Objects.visionSubsystem, true, 1550));
            commandsList.add(new ReadyToShootCommand());
            commandsList.add (new ShootCommand(Objects.shootSubsystem, Objects.hoodSubsystem, Objects.drivetrain, Objects.visionSubsystem, true, 1550));
            commandsList.add(new MoveToCommand(Objects.moveToSubsystem, 20, 90, 90, 0.5, 0.15, 10, false));
    }

    public void greenPath() { //TWO BALL in two ball position (BOTTOM LEFT)
        commandsList.add(new HoodZeroCommand(Objects.hoodSubsystem));
        commandsList.add(new MoveToCommand(Objects.moveToSubsystem, -15, 32, -135, 0.5, 0.15, 10, true)); //FIRST BALL
        commandsList.add(new ReadyToShootCommand());
        commandsList.add (new ShootCommand(Objects.shootSubsystem, Objects.hoodSubsystem, Objects.drivetrain, Objects.visionSubsystem, false, Objects.visionSubsystem.rpmFromVision() ));
        commandsList.add(new ReadyToShootCommand());
        commandsList.add (new ShootCommand(Objects.shootSubsystem, Objects.hoodSubsystem, Objects.drivetrain, Objects.visionSubsystem, false, Objects.visionSubsystem.rpmFromVision() ));
        commandsList.add(new MoveToCommand(Objects.moveToSubsystem, -15, 50, 180, 0.5, 0.15, 10, false)); //MOVE AWAY

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
