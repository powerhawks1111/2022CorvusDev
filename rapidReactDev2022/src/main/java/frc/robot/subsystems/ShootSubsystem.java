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
            kP = .00000005;
            kI = 0.000005;
            kD = .000005;
            kF = 0.00000004810;
            shooterPID.setP(kP);
            shooterPID.setI(kI);
            shooterPID.setD(kD);
            shooterPID.setFF(kF);
        } else {
            kP = .050; //0.031 ultimate gain //.1395 //.01
            kI = 0.000005; //.00000001 .000000000001
            kD = .05; //.00125
            kF = .000005;
            shooterPID.setP(kP);
            shooterPID.setI(kI);
            shooterPID.setD(kD);
            shooterPID.setFF(kF);
        }
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
        // return (currentSetpoint>0)&&(errDelta <= 100);
        return false;
    }
}
