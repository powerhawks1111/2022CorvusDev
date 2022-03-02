package frc.robot.subsystems;
import frc.robot.variables.Motors;
import frc.robot.variables.Objects;

import com.revrobotics.CANSparkMax.ControlType;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import com.revrobotics.SparkMaxPIDController;
import com.revrobotics.RelativeEncoder;

public class ShootSubsystem extends SubsystemBase{
    private SparkMaxPIDController shooterPID = Motors.shooterLeader.getPIDController();
    private RelativeEncoder shooterEncoder = Motors.shooterLeader.getEncoder();

    private double kP; //0.031 ultimate gain //.1395 //.01
    private double kI; //.00000001 .000000000001
    private double kD; //.00125
    private double kF;
    private double currentSetpoint;
    private double rpmTolerance;

    /**
     * Constructor
     */
    public ShootSubsystem() {
        kP = .0420; //0.031 ultimate gain //.1395 //.01
        kI = 0.00000; //.00000001 .000000000001
        kD = 0.0002; //.00125
        kF = .000226;
        shooterPID.setP(kP);
        shooterPID.setI(kI);
        shooterPID.setD(kD);
        shooterPID.setFF(kF);
    }

    /**
     * Uses spark max onboard PID to set the shooter RPM
     *
     * @param shooterRPM double of shooter RPM <br><br>
     * Positive RPM even if motor is backwards; this function will reverse it if necessary
     */
    public void setShooterRPM(double shooterRPM) {
        currentSetpoint = shooterRPM;
        if (Math.abs(currentSetpoint-Motors.shooterLeader.getEncoder().getVelocity())>100) {
            // kP = .00000005;
            // kI = 0.00000;
            // kD = .00000;
            // kF = 0.00000004810;
            // shooterPID.setP(kP);
            // shooterPID.setI(kI);
            // shooterPID.setD(kD);
            // shooterPID.setFF(kF);
        } else {
            // kP = .050; //0.031 ultimate gain //.1395 //.01
            // kI = 0.00000; //.00000001 .000000000001
            // kD = 0; //.00125
            // kF = .000002;
            // shooterPID.setP(kP);
            // shooterPID.setI(kI);
            // shooterPID.setD(kD);
            // shooterPID.setFF(kF);
        }
        SmartDashboard.putNumber("Setpoint", shooterRPM);
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
        return (currentSetpoint>0)&&(errDelta <= 50);
    
    }
}
