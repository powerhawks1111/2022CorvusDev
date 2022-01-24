// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.math.kinematics.SwerveDriveKinematics;
import edu.wpi.first.math.kinematics.SwerveDriveOdometry;
import edu.wpi.first.wpilibj.AnalogGyro;
import com.kauailabs.navx.frc.AHRS; 

/** Represents a swerve drive style drivetrain. */
public class Drivetrain {
    public static final double kMaxSpeed = 3.68; // 3.68 meters per second or 12.1 ft/s (max speed of SDS Mk3 with Neo motor)
    public static final double kMaxAngularSpeed = Math.PI; // 1/2 rotation per second

    private final Translation2d m_frontLeftLocation = new Translation2d(0.538, 0.538);
    private final Translation2d m_frontRightLocation = new Translation2d(0.538, -0.538);
    private final Translation2d m_backLeftLocation = new Translation2d(-0.538, 0.538);
    private final Translation2d m_backRightLocation = new Translation2d(-0.538, -0.538);

    // private final SwerveModule m_frontRight = new SwerveModule(1, 2, 10, 0.8174);
    // private final SwerveModule m_frontLeft  = new SwerveModule(3, 4, 11, 0.1154);
    // private final SwerveModule m_backLeft   = new SwerveModule(5, 6, 12, 0.1755);
    // private final SwerveModule m_backRight  = new SwerveModule(7, 8, 13, 0.8171);

    private final SwerveModule m_frontRight = new SwerveModule(1, 2, 10, 0);
    private final SwerveModule m_frontLeft  = new SwerveModule(3, 4, 11, 0);
    private final SwerveModule m_backLeft   = new SwerveModule(5, 6, 12, 0);
    private final SwerveModule m_backRight  = new SwerveModule(7, 8, 13, 0);

    private final AnalogGyro m_gyro = new AnalogGyro(0);
    
    private final AHRS m_navx;

    private final SwerveDriveKinematics m_kinematics = new SwerveDriveKinematics(m_frontLeftLocation, m_frontRightLocation, m_backLeftLocation, m_backRightLocation);

    private final SwerveDriveOdometry m_odometry = new SwerveDriveOdometry(m_kinematics, m_gyro.getRotation2d());

    //Constructor
    public Drivetrain(AHRS navx) {
        m_navx = navx;
    }

    /**
     * Method to drive the robot using joystick info.
     *
     * @param xSpeed Speed of the robot in the x direction (forward).
     * @param ySpeed Speed of the robot in the y direction (sideways).
     * @param rot Angular rate of the robot.
     * @param fieldRelative Whether the provided x and y speeds are relative to the field.
     */
    @SuppressWarnings("ParameterName")
    public void drive(double xSpeed, double ySpeed, double rot, boolean fieldRelative) {
        var swerveModuleStates = m_kinematics.toSwerveModuleStates(fieldRelative ? ChassisSpeeds.fromFieldRelativeSpeeds(xSpeed, ySpeed, rot, new Rotation2d(m_navx.getAngle() * (Math.PI / 180))): new ChassisSpeeds(xSpeed, ySpeed, rot));
        SwerveDriveKinematics.desaturateWheelSpeeds(swerveModuleStates, kMaxSpeed);
        m_frontLeft.setDesiredState(swerveModuleStates[0]);
        m_frontRight.setDesiredState(swerveModuleStates[1]);
        m_backLeft.setDesiredState(swerveModuleStates[2]);
        m_backRight.setDesiredState(swerveModuleStates[3]);
    }

    /** Updates the field relative position of the robot. */
    public void updateOdometry() {
        m_odometry.update(m_gyro.getRotation2d(), m_frontLeft.getState(), m_frontRight.getState(), m_backLeft.getState(), m_backRight.getState());
    }

    /**
     * Gives the current position and rotation of the robot (meters) based on the wheel odometry
     * @return Pose2d of current robot position
     */
    public Pose2d getCurrentPose2d() {
        return m_odometry.getPoseMeters();
    }
}
