package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.variables.Objects;

public class PartyModeCommand extends CommandBase{
    boolean turnOnLights =  true;

    public PartyModeCommand () {

    }

     @Override 
     public void execute () {
        Objects.drivetrain.drive(0,0,.2,false, false);
        Objects.intakeSubsystem.retractIntake();
        if(turnOnLights) {
            Objects.visionSubsystem.turnOnLeds();
            
            turnOnLights = false;
        } else {
            Objects.visionSubsystem.turnOffLeds();
            turnOnLights = true;
        }
     } 

}
