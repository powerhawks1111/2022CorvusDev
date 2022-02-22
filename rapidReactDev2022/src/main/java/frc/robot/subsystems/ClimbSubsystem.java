package frc.robot.subsystems;

import com.revrobotics.SparkMaxPIDController;
import com.revrobotics.CANSparkMax.ControlType;

import frc.robot.variables.Motors;

public class ClimbSubsystem {
    private double minEncoderVal = 0;
    private double maxEncoderVal = 600;
    private SparkMaxPIDController climbPID = Motors.climbLeader.getPIDController();
    /**
     * Constructor
     */
    public ClimbSubsystem() {
        //TODO tune these later
        climbPID.setP(0.1);
        climbPID.setI(0.0001);
        climbPID.setOutputRange(-1, 1);
    }

    /**
     * Sets the speed for the climb motor
     * @param speed -1 to +1 speed for climb motor
     */
    public void driveClimbMotor(double speed) {
        climbPID.setReference(speed, ControlType.kDutyCycle);
    }

    /**
     * Uses PID to extend the climb to its maximum height
     */
    public void extendClimb() {
        climbPID.setReference(maxEncoderVal, ControlType.kPosition);
    }

    /**
     * Uses PID to retract the climb in to its minimum height
     */
    public void retractClimb() {
        climbPID.setReference(minEncoderVal, ControlType.kPosition);
    }
}
