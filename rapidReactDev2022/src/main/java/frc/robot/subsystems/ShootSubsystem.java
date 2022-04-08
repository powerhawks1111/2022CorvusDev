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
     * Constructor to set pid gains
     */
    public ShootSubsystem() {
        kP = 0.0003; // .000262//0.00024; //.00022 //0.0420 ultimate gain //.1395 //.01 //.003 //.0012
        //kI = 0.000001; //.00000001 .000000000001
        kD = 0.08;//.00041// 0.0003; //.0005// 0.0002 //.00002
        kF = .000175; //0.0002
        shooterPID.setP(kP);
        shooterPID.setI(kI);
        shooterPID.setD(kD);
        shooterPID.setFF(kF);

        shooterFollowerPID.setP(kP);
        shooterFollowerPID.setI(kI);
        shooterFollowerPID.setD(kD);
        shooterFollowerPID.setFF(kF);


        Motors.shooterLeader.setClosedLoopRampRate(0.2);
        Motors.shooterFollower.setClosedLoopRampRate(0.2);

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
        // if (Motors.shooterLeader.getEncoder().getVelocity()< (shooterRPM-300)) {
        //     Motors.shooterLeader.set(.5);
        //     Motors.shooterFollower.follow(Motors.shooterLeader, true);
        // } else {
        shooterPID.setReference(shooterRPM, ControlType.kVelocity);
        Motors.shooterFollower.follow(Motors.shooterLeader, true);
        //}
        
        SmartDashboard.putNumber("MotorRPM", Motors.shooterLeader.getEncoder().getVelocity());
        //SmartDashboard.putNumber("ShooterLeaderCurrent", Motors.shooterLeader.getOutputCurrent());
        SmartDashboard.putNumber("MotorFollowerRPM", Motors.shooterFollower.getEncoder().getVelocity());
        //SmartDashboard.putNumber("ShooterFollowerCurrent", Motors.shooterFollower.getOutputCurrent());

    }



    /**
     * Function to let the operator spool up the motors so they are ready before we shoot
     */
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
        return (currentSetpoint>0)&&(errDelta <= 40)&&m_shoot&&Objects.visionSubsystem.linedUp(); //((shooterEncoder.getVelocity() >= currentSetpoint-20) && shooterEncoder.getVelocity() <= currentSetpoint +50)
        // return (currentSetpoint>0)&&(errDelta <= 50); //((shooterEncoder.getVelocity() >= currentSetpoint-20) && shooterEncoder.getVelocity() <= currentSetpoint +50)
    
    }
}
