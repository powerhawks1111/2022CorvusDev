package frc.robot.commands;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.variables.Objects;

public class ExpellBallCommand extends CommandBase{
    private long startTime = 0;
    private long timeElapsed = 0;
    private boolean start = false;
    
    public ExpellBallCommand() {
        
    }

    @Override
    public void execute () {
        if (!start) {
            startTime = System.currentTimeMillis();
            start=true;
        }
        Objects.intakeSubsystem.ejectBall();
        Objects.indexSubsystem.updateEject(true);
        timeElapsed = System.currentTimeMillis()-startTime;
        SmartDashboard.putNumber("timeElapsed", timeElapsed);
        System.out.println("Start time:");
        System.out.println(startTime);
        System.out.println("Elapsed Time:");
        System.out.println(timeElapsed);
    }

    @Override
    public boolean isFinished () {
        if (timeElapsed>2000) {
            Objects.intakeSubsystem.retractIntake();
            Objects.indexSubsystem.updateEject(false);
        }
        return (timeElapsed>3000);
    }
}
