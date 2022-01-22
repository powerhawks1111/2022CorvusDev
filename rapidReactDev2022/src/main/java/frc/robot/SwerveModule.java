// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.controller.ProfiledPIDController;
import edu.wpi.first.math.controller.SimpleMotorFeedforward;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.kinematics.SwerveModuleState;
import edu.wpi.first.math.trajectory.TrapezoidProfile;
import edu.wpi.first.wpilibj.CAN;
import edu.wpi.first.wpilibj.motorcontrol.MotorController;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel;
import com.revrobotics.RelativeEncoder;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DutyCycle;
import edu.wpi.first.wpilibj.DutyCycleEncoder;

public class SwerveModule {
        private static final double kWheelRadius = (0.1016 / 2) / 8.16; // 0.1016 M wheel diameter (4"), divide by 2 for radius, divide by 8.16 for gear ratio of swerve module

        private static final double kModuleMaxAngularVelocity = Drivetrain.kMaxAngularSpeed;
        private static final double kModuleMaxAngularAcceleration = 2 * Math.PI; // radians per second squared

        private final CANSparkMax m_driveMotor;
        private final CANSparkMax m_turningMotor;

        private final RelativeEncoder m_driveEncoder;
        private final DigitalInput m_TurnEncoderInput;
        private final DutyCycleEncoder m_TurnPWMEncoder;

        // Gains are for example purposes only - must be determined for your own robot!
        private final PIDController m_drivePIDController = new PIDController(1, 0, 0);

        // Gains are for example purposes only - must be determined for your own robot!
        private final ProfiledPIDController m_turningPIDController = new ProfiledPIDController( 1, 0, 0, new TrapezoidProfile.Constraints(kModuleMaxAngularVelocity, kModuleMaxAngularAcceleration) );

        // Gains are for example purposes only - must be determined for your own robot!
        private final SimpleMotorFeedforward m_driveFeedforward = new SimpleMotorFeedforward(1, 3);
        private final SimpleMotorFeedforward m_turnFeedforward = new SimpleMotorFeedforward(1, 0.5);

        /**
         * Constructs a SwerveModule with a drive motor, turning motor, drive encoder and turning encoder.
         *
         * @param driveMotorChannel CAN ID for the drive motor.
         * @param turningMotorChannel CAN ID for the turning motor.
         * @param driveEncoder DIO input for the drive encoder channel A
         * @param turnEncoderPWMChannel DIO input for the drive encoder channel B
         */
        public SwerveModule(int driveMotorChannel, int turningMotorChannel, int turnEncoderPWMChannel) {
            // can spark max motor controller objects
            m_driveMotor = new CANSparkMax(driveMotorChannel, CANSparkMaxLowLevel.MotorType.kBrushless);
            m_turningMotor = new CANSparkMax(turningMotorChannel, CANSparkMaxLowLevel.MotorType.kBrushless);

            //spark max built-in encoder
            m_driveEncoder = m_driveMotor.getEncoder();

            //PWM encoder from CTRE mag encoders
            m_TurnEncoderInput = new DigitalInput(turnEncoderPWMChannel);
            m_TurnPWMEncoder = new DutyCycleEncoder(m_TurnEncoderInput);

            // Limit the PID Controller's input range between -pi and pi and set the input
            // to be continuous.
            m_turningPIDController.enableContinuousInput(-Math.PI, Math.PI);
        }

        /**
         * Returns the current state of the module.
         *
         * @return The current state of the module.
         */
        public SwerveModuleState getState() {
            return new SwerveModuleState(m_driveEncoder.getVelocity(), new Rotation2d(m_TurnPWMEncoder.get()));
        }

        /**
         * Sets the desired state for the module.
         *
         * @param desiredState Desired state with speed and angle.
         */
        public void setDesiredState(SwerveModuleState desiredState) {
            // Optimize the reference state to avoid spinning further than 90 degrees
            SwerveModuleState state = SwerveModuleState.optimize(desiredState, new Rotation2d(m_TurnPWMEncoder.get() * 2 * Math.PI));

            // Calculate the drive output from the drive PID controller.
            final double driveOutput = m_drivePIDController.calculate(m_driveEncoder.getVelocity(), state.speedMetersPerSecond);

            final double driveFeedforward = m_driveFeedforward.calculate(state.speedMetersPerSecond);

            // Calculate the turning motor output from the turning PID controller.
            //TODO: Can we use our own P error control here?
            final double turnOutput = m_turningPIDController.calculate(m_TurnPWMEncoder.get(), state.angle.getRadians());

            final double turnFeedforward = m_turnFeedforward.calculate(m_turningPIDController.getSetpoint().velocity);

            m_driveMotor.setVoltage(driveOutput + driveFeedforward);
            m_turningMotor.setVoltage(turnOutput + turnFeedforward);
        }
}
