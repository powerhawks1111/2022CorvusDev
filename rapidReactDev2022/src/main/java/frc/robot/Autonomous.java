package frc.robot;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.commands.IntakeCommand;
import frc.robot.commands.MoveToCommand;
import frc.robot.variables.Objects;

import java.util.ArrayList;

public class Autonomous {

    private ArrayList<CommandBase> commandsList;
    boolean start = false;
    
    public Autonomous() {
        commandsList = new ArrayList<CommandBase>();
    }

    public void redPath() {
        if(!start) {
            // commandsList.add(new MoveToCommand(Objects.moveToSubsystem, -35, 3, 0, 0.4, 0.25));
            commandsList.add(new MoveToCommand(Objects.moveToSubsystem,0 , 30, 0, 0.5, 0.25));
            commandsList.add(new IntakeCommand(Objects.intakeAuto));

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
