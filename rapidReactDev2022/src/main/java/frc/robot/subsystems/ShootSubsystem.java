package frc.robot.subsystems;
import frc.robot.variables.Motors;
import frc.robot.variables.Objects;

import com.revrobotics.CANSparkMax.ControlType;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import com.revrobotics.SparkMaxPIDController;
import com.revrobotics.RelativeEncoder;

public class ShootSubsystem {
    private SparkMaxPIDController shooterPID = Motors.shooterLeader.getPIDController();
    private RelativeEncoder shooterEncoder = Motors.shooterLeader.getEncoder();

    private double kP = 0.005;
    private double kI = 0;
    private double kD = 0.05;

    private double currentSetpoint;
    private double rpmTolerance;

    /**
     * Constructor
     */
    public ShootSubsystem() {
        shooterPID.setP(kP);
        shooterPID.setI(kI);
        shooterPID.setD(kD);
    }

    /**
     * Uses spark max onboard PID to set the shooter RPM
     * @param shooterRPM double of shooter RPM <br><br>
     * Positive RPM even if motor is backwards; this function will reverse it if necessary
     */
    public void setShooterRPM(double shooterRPM) {
        currentSetpoint = shooterRPM;
        shooterPID.setReference(shooterRPM, ControlType.kVelocity);
        SmartDashboard.putNumber("MotorRPM", Motors.shooterLeader.getEncoder().getVelocity());
    }

    /**
     * Called by background index function to decide whether balls should be
     * fed into the shooter
     * @return boolean of whether to send balls into shooter
     */
    public boolean shouldFeedToShooter() {
        double errDelta = Math.abs(shooterEncoder.getVelocity() - currentSetpoint);
        SmartDashboard.putNumber("errDelta", errDelta);
        SmartDashboard.putNumber("currentSetpoint", currentSetpoint);
        SmartDashboard.putNumber("currentVelocity", shooterEncoder.getVelocity());
        return (currentSetpoint>0)&&(errDelta <= 100);
    }
}
