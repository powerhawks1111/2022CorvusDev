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
    private SparkMaxPIDController shooterFollowerPID = Motors.shooterFollower.getPIDController();

    private double kP; //0.031 ultimate gain //.1395 //.01
    private double kI; //.00000001 .000000000001
    private double kD; //.00125
    private double kF;
    private double currentSetpoint;
    private double rpmTolerance;
    boolean m_shoot = false;

    /**
     * Constructor
     */
    public ShootSubsystem() {
        kP = 0.00012; //0.0420 ultimate gain //.1395 //.01 //.003 //.0012
        //kI = 0.000001; //.00000001 .000000000001
        kD = 0.000019; // 0.0002 //.00002
        kF = .000197; //0.0002
        shooterPID.setP(kP);
        shooterPID.setI(kI);
        shooterPID.setD(kD);
        shooterPID.setFF(kF);

        shooterFollowerPID.setP(kP);
        shooterFollowerPID.setI(kI);
        shooterFollowerPID.setD(kD);
        shooterFollowerPID.setFF(kF);


        Motors.shooterLeader.setClosedLoopRampRate(0.3);
        Motors.shooterFollower.setClosedLoopRampRate(0.3);

    }
    public void shoot (Boolean shoot) {
        m_shoot = shoot;
    }
    /**
     * Uses spark max onboard PID to set the shooter RPM
     *
     * @param shooterRPM double of shooter RPM <br><br>
     * Positive RPM even if motor is backwards; this function will reverse it if necessary
     */
    public void setShooterRPM(double shooterRPM) {
        currentSetpoint = shooterRPM;
        SmartDashboard.putNumber("Setpoint", shooterRPM);
        shooterPID.setReference(shooterRPM, ControlType.kVelocity);
        //shooterFollowerPID.setReference(-shooterRPM, ControlType.kVelocity);
        Motors.shooterFollower.follow(Motors.shooterLeader, true);
        SmartDashboard.putNumber("MotorRPM", Motors.shooterLeader.getEncoder().getVelocity());
        SmartDashboard.putNumber("ShooterLeaderCurrent", Motors.shooterLeader.getOutputCurrent());
        SmartDashboard.putNumber("MotorFollowerRPM", Motors.shooterFollower.getEncoder().getVelocity());
        SmartDashboard.putNumber("ShooterFollowerCurrent", Motors.shooterFollower.getOutputCurrent());

    }



    
    public void spoolUp () {
        shooterPID.setReference(Objects.visionSubsystem.rpmFromVision()-200, ControlType.kVelocity);
        Motors.shooterFollower.follow(Motors.shooterLeader, true);
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
        return (currentSetpoint>0)&&(errDelta <= 50)&&m_shoot; //((shooterEncoder.getVelocity() >= currentSetpoint-20) && shooterEncoder.getVelocity() <= currentSetpoint +50)
    
    }
}
