package frc.robot.subsystems;

import frc.robot.variables.Motors;

public class ClimbSubsystem {
    /**
     * Constructor
     */
    public ClimbSubsystem() {

    }

    /**
     * Sets the speed for the climb motor
     * @param speed -1 to +1 speed for climb motor
     */
    public void driveClimbMotor(double speed) {
        Motors.climbLeader.set(speed);
    }
}
