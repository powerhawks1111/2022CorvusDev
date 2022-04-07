package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.variables.Objects;



public class ResetCommand extends CommandBase{
    

    public double m_navxOreintation = 0;
    public ResetCommand (double navxOrientation) {
        m_navxOreintation = navxOrientation;
    }
    
    @Override
    public void execute () {
        Objects.navx.setAngleAdjustment(m_navxOreintation);
         
    }

    @Override 
    public boolean isFinished () {
        return true;
    }


}
