package frc.robot;

import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.ParallelDeadlineGroup;
import edu.wpi.first.wpilibj2.command.ParallelRaceGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.commands.ExpellBallCommand;
import frc.robot.commands.HoodZeroCommand;
import frc.robot.commands.IntakeCommand;
import frc.robot.commands.MoveToCommand;
import frc.robot.commands.PartyModeCommand;
import frc.robot.commands.ReadyToShootCommand;
import frc.robot.subsystems.HoodSubsystem;
import frc.robot.variables.Objects;
import frc.robot.commands.ShootCommand;
import frc.robot.commands.ResetCommand;


import java.util.ArrayList;

public class Autonomous {

    private ArrayList<CommandBase> commandsList;
    boolean start = false;
    
    public Autonomous() {
        commandsList = new ArrayList<CommandBase>();
    }

    public void redPath() { //Original FOUR BALL AUTO from comp 2
        if(!start) {
            commandsList.add(new ResetCommand(90));
            commandsList.add(new HoodZeroCommand(Objects.hoodSubsystem));
            commandsList.add(new MoveToCommand(Objects.moveToSubsystem, 40, -1, 90, .7, 0.6, 10, true)); //FIRST BALL
            //commandsList.add(new IntakeCommand(Objects.intakeAuto));
            commandsList.add(new ReadyToShootCommand());
            commandsList.add (new ShootCommand(Objects.shootSubsystem, Objects.hoodSubsystem, Objects.drivetrain, Objects.visionSubsystem, false, Objects.visionSubsystem.rpmFromVision(), false)); //SHOOT First two
            commandsList.add(new ReadyToShootCommand());
            commandsList.add (new ShootCommand(Objects.shootSubsystem, Objects.hoodSubsystem, Objects.drivetrain, Objects.visionSubsystem, false, Objects.visionSubsystem.rpmFromVision(), true));
        
            commandsList.add(new MoveToCommand(Objects.moveToSubsystem, 0, -90, 180, .8, .6, 40, true));
            commandsList.add (new MoveToCommand(Objects.moveToSubsystem, 17, -253, 165, .8, .6, 40, true));//THIRD BALL,  OG 28, 234
            //commandsList.add(new IntakeCommand( Objects.intakeAuto));

            commandsList.add (new MoveToCommand(Objects.moveToSubsystem, 15, -70, 115, 1, .6, 40, true)); //GO HOME //orig 10, -48, 115, 1, .6, 40

            commandsList.add(new ReadyToShootCommand());
            commandsList.add (new ShootCommand(Objects.shootSubsystem, Objects.hoodSubsystem, Objects.drivetrain, Objects.visionSubsystem, false, Objects.visionSubsystem.rpmFromVision(), false));
            commandsList.add(new ReadyToShootCommand());
            commandsList.add (new ShootCommand(Objects.shootSubsystem, Objects.hoodSubsystem, Objects.drivetrain, Objects.visionSubsystem, false, Objects.visionSubsystem.rpmFromVision(), true));
            //commandsList.add(new HoodZeroCommand(Objects.hoodSubsystem));
            // comman54dsList.add(new MoveToCommand(Objects.moveToSubsystem, -35, 3, 0, 0.4, 0.25));
            // commandsList.add(new MoveToCommand(Objects.moveToSubsystem,0 , 30, 0, 0.5, 0.25));
            

            start = true;
        }
        startAutonomous();
    }

    public void orangePath() { //THREE BALL
        if (!start) {
            commandsList.add(new ResetCommand(90));
            commandsList.add(new HoodZeroCommand(Objects.hoodSubsystem));
            commandsList.add(new MoveToCommand(Objects.moveToSubsystem, 40, -1, 90, 0.7, .6, 10, true)); //FIRST BALL
            // commandsList.add(new HoodZeroCommand(Objects.hoodSubsystem));
            // commandsList.add(new MoveToCommand(Objects.moveToSubsystem, 37, 0, 90, 0.5, 0.15));
            //commandsList.add(new IntakeCommand(Objects.intakeAuto));
            commandsList.add(new ReadyToShootCommand());
            commandsList.add (new ShootCommand(Objects.shootSubsystem, Objects.hoodSubsystem, Objects.drivetrain, Objects.visionSubsystem, false , Objects.visionSubsystem.rpmFromVision(), false));
            commandsList.add(new ReadyToShootCommand());
            commandsList.add (new ShootCommand(Objects.shootSubsystem, Objects.hoodSubsystem, Objects.drivetrain, Objects.visionSubsystem, false, Objects.visionSubsystem.rpmFromVision(), true));
            
            //commandsList.add (new MoveToCommand(Objects.moveToSubsystem, -10, -90, 180, .65, .95, 10, true, false)); //SECOND BALL //TODO REMOVE THIS

            commandsList.add(new ReadyToShootCommand());
            commandsList.add (new ShootCommand(Objects.shootSubsystem, Objects.hoodSubsystem, Objects.drivetrain, Objects.visionSubsystem, false, Objects.visionSubsystem.rpmFromVision(), false));
            
            start=true;
        }
        startAutonomous();

    }

    public void yellowPath() { //TWO BALL in four ball position
    if (!start) {
        commandsList.add(new ResetCommand(90));
        commandsList.add(new HoodZeroCommand(Objects.hoodSubsystem));
        commandsList.add(new MoveToCommand(Objects.moveToSubsystem, 40, -1, 90, .7, 0.6, 10, true)); //FIRST BALL
        //commandsList.add(new IntakeCommand(Objects.intakeAuto));
        commandsList.add(new ReadyToShootCommand());
        commandsList.add (new ShootCommand(Objects.shootSubsystem, Objects.hoodSubsystem, Objects.drivetrain, Objects.visionSubsystem, false, Objects.visionSubsystem.rpmFromVision(), false)); //SHOOT First two
        commandsList.add(new ReadyToShootCommand());
        commandsList.add (new ShootCommand(Objects.shootSubsystem, Objects.hoodSubsystem, Objects.drivetrain, Objects.visionSubsystem, false, Objects.visionSubsystem.rpmFromVision(), true));
        
        start = true;
        }
            startAutonomous();
    }

    public void greenPath() { //TWO BALL with one ball denial in two ball position (BOTTOM LEFT)
        if (!start) {
            commandsList.add(new ResetCommand(-135));
            commandsList.add(new HoodZeroCommand(Objects.hoodSubsystem));
            commandsList.add(new MoveToCommand(Objects.moveToSubsystem, -23, -32, -135, 0.5, 0.15, 10, true)); //FIRST BALL
            commandsList.add(new ReadyToShootCommand());
            commandsList.add (new ShootCommand(Objects.shootSubsystem, Objects.hoodSubsystem, Objects.drivetrain, Objects.visionSubsystem, false, Objects.visionSubsystem.rpmFromVision(), false ));
            commandsList.add(new ReadyToShootCommand());
            commandsList.add (new ShootCommand(Objects.shootSubsystem, Objects.hoodSubsystem, Objects.drivetrain, Objects.visionSubsystem, false, Objects.visionSubsystem.rpmFromVision(), false ));
            commandsList.add(new MoveToCommand(Objects.moveToSubsystem, -71, -12, -80, .5, .6, 10, true));
            commandsList.add(new MoveToCommand(Objects.moveToSubsystem, -56, -70, -180, .5, .6, 15, true));
            commandsList.add( new ExpellBallCommand());
            commandsList.add(new MoveToCommand(Objects.moveToSubsystem, -71, -12, -10, .2, .6, 10, false));
    
            start = true;
        }
        startAutonomous();
    }

    public void bluePath() { //TWO BALL with TWO BALL denail
        if(!start) {
            commandsList.add(new ResetCommand(-135));
            commandsList.add(new HoodZeroCommand(Objects.hoodSubsystem));
            commandsList.add(new MoveToCommand(Objects.moveToSubsystem, -23, -32, -135, 0.5, 0.15, 10, true)); //FIRST BALL
            commandsList.add(new ReadyToShootCommand());
            commandsList.add (new ShootCommand(Objects.shootSubsystem, Objects.hoodSubsystem, Objects.drivetrain, Objects.visionSubsystem, false, Objects.visionSubsystem.rpmFromVision(), false ));
            commandsList.add(new ReadyToShootCommand());
            commandsList.add (new ShootCommand(Objects.shootSubsystem, Objects.hoodSubsystem, Objects.drivetrain, Objects.visionSubsystem, false, Objects.visionSubsystem.rpmFromVision(), false));
            
            commandsList.add (new MoveToCommand(Objects.moveToSubsystem, 59, -70, 90, .3, .7, 15, true)); //bottom right ball
            
            commandsList.add(new MoveToCommand(Objects.moveToSubsystem, -71, -12, -80, .5, .6, 10, true)); // TOP LEFT  BALL
            commandsList.add(new MoveToCommand(Objects.moveToSubsystem, -56, -70, -180, .5, .6, 15, true));
            commandsList.add( new ExpellBallCommand());
            commandsList.add(new MoveToCommand(Objects.moveToSubsystem, -71, -12, -10, .2, .6, 10, false));

            start = true;
        }
        startAutonomous();
    }

    public void purplePath() { //5 ball
        if(!start) {
            commandsList.add(new ResetCommand(90));
            commandsList.add(new HoodZeroCommand(Objects.hoodSubsystem));

            //pickup the first ball
            commandsList.add (new MoveToCommand(Objects.moveToSubsystem, -15, -90, 175, .8, .6, 20, true));//SECOND BALL

            //shoot both balls
            commandsList.add(new ReadyToShootCommand());
            commandsList.add (new ShootCommand(Objects.shootSubsystem, Objects.hoodSubsystem, Objects.drivetrain, Objects.visionSubsystem, false, Objects.visionSubsystem.rpmFromVision(), false)); //SHOOT First two
            commandsList.add(new ReadyToShootCommand());
            commandsList.add (new ShootCommand(Objects.shootSubsystem, Objects.hoodSubsystem, Objects.drivetrain, Objects.visionSubsystem, false, Objects.visionSubsystem.rpmFromVision(), true));
            
            //pickup player station ball
            commandsList.add (new MoveToCommand(Objects.moveToSubsystem, 17, -252, 160, .8, .6, 40, true));//THIRD BALL,  OG 28, 234
            
            //wait for human player rollout
            commandsList.add (new ReadyToShootCommand());
            commandsList.add(new IntakeCommand(Objects.intakeAuto));

            //drive to shooting position
            commandsList.add (new MoveToCommand(Objects.moveToSubsystem, 10, 0, 90, 1, .6, 40, true)); //GO HOME

            //shoot both balls
            commandsList.add(new ReadyToShootCommand());
            commandsList.add (new ShootCommand(Objects.shootSubsystem, Objects.hoodSubsystem, Objects.drivetrain, Objects.visionSubsystem, false, Objects.visionSubsystem.rpmFromVision(), false)); //SHOOT First two
            commandsList.add(new ReadyToShootCommand());
            commandsList.add (new ShootCommand(Objects.shootSubsystem, Objects.hoodSubsystem, Objects.drivetrain, Objects.visionSubsystem, false, Objects.visionSubsystem.rpmFromVision(), true));
            

            //pickup final ball
            commandsList.add(new MoveToCommand(Objects.moveToSubsystem, 40, -1, 90, .4, 0.7, 12, true)); //FIRST BALL

            //shoot final ball
            commandsList.add(new ReadyToShootCommand());
            commandsList.add (new ShootCommand(Objects.shootSubsystem, Objects.hoodSubsystem, Objects.drivetrain, Objects.visionSubsystem, false, Objects.visionSubsystem.rpmFromVision(), false));
            
            //commandsList.add(new HoodZeroCommand(Objects.hoodSubsystem));
            // comman54dsList.add(new MoveToCommand(Objects.moveToSubsystem, -35, 3, 0, 0.4, 0.25));
            // commandsList.add(new MoveToCommand(Objects.moveToSubsystem,0 , 30, 0, 0.5, 0.25));
            

            start = true;
        }
        startAutonomous();
    }

    public void defaultPath() {

    }

    public void startAutonomous() {
        Objects.scheduler.addCommands(commandsList);
        Objects.scheduler.run();
    }
}
