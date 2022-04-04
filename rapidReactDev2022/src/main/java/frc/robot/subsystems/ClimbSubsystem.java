package frc.robot.subsystems;

import com.revrobotics.SparkMaxPIDController;
import com.revrobotics.CANSparkMax.ControlType;

import frc.robot.variables.Motors;
import frc.robot.variables.Objects;
import edu.wpi.first.wpilibj.DoubleSolenoid;

public class ClimbSubsystem {
    private double minEncoderVal = 0;
    private double maxEncoderVal = 600;
    private SparkMaxPIDController climbPID = Motors.climbLeaderDeprecated.getPIDController();
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

    public void controlTopHooks (Boolean state) {
        if (state){
        Objects.topLeftHook.set(DoubleSolenoid.Value.kReverse);
        Objects.topRightHook.set(DoubleSolenoid.Value.kReverse);
        } else {
            Objects.topLeftHook.set(DoubleSolenoid.Value.kForward);
            Objects.topRightHook.set(DoubleSolenoid.Value.kForward);
        }
    }
    public void controlBottomHooks (Boolean state) {
        if (state){
        Objects.bottomLeftHook.set(DoubleSolenoid.Value.kReverse);
        Objects.bottomRightHook.set(DoubleSolenoid.Value.kReverse);
        } else {
            Objects.bottomLeftHook.set(DoubleSolenoid.Value.kForward);
            Objects.bottomRightHook.set(DoubleSolenoid.Value.kForward);
        }
    }
    /**
     * Uses PID to extend the climb to its maximum height
     */
    public void extendClimb() {
        climbPID.setReference(maxEncoderVal, ControlType.kPosition);
    }

    public void rotateClimb(double speed) {
        Motors.climbHigher.set(speed);
    }

    /**
     * Uses PID to retract the climb in to its minimum height
     */
    public void retractClimb() {
        climbPID.setReference(minEncoderVal, ControlType.kPosition);
    }
}
