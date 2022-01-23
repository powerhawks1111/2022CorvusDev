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
        private static final double kWheelDiameter = 0.1016; // 0.1016 M wheel diameter (4")
        private static final double kWheelCircumference = Math.PI *kWheelDiameter;
        private static final double rpmToVelocityScaler = kWheelCircumference / 8.16; //SDS Mk3 standard gear ratio from motor to wheel

        private static final double kModuleMaxAngularVelocity = Drivetrain.kMaxAngularSpeed;
        private static final double kModuleMaxAngularAcceleration = 2 * Math.PI; // radians per second squared

        private final CANSparkMax m_driveMotor;
        private final CANSparkMax m_turningMotor;

        private final RelativeEncoder m_driveEncoder;
        private final DigitalInput m_TurnEncoderInput;
        private final DutyCycle m_TurnPWMEncoder;
        private double turnEncoderOffset;

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
         * @param turnOffset offset from 0 to 1 for the home position of the encoder
         */
        public SwerveModule(int driveMotorChannel, int turningMotorChannel, int turnEncoderPWMChannel, double turnOffset) {
            // can spark max motor controller objects
            m_driveMotor = new CANSparkMax(driveMotorChannel, CANSparkMaxLowLevel.MotorType.kBrushless);
            m_turningMotor = new CANSparkMax(turningMotorChannel, CANSparkMaxLowLevel.MotorType.kBrushless);

            //spark max built-in encoder
            m_driveEncoder = m_driveMotor.getEncoder();
            m_driveEncoder.setVelocityConversionFactor(rpmToVelocityScaler);

            //PWM encoder from CTRE mag encoders
            m_TurnEncoderInput = new DigitalInput(turnEncoderPWMChannel);
            m_TurnPWMEncoder = new DutyCycle(m_TurnEncoderInput);
            turnEncoderOffset = turnOffset;

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
            //the getVelocity() function normally returns RPM but is scaled in the SwerveModule constructor to return actual wheel speed
            return new SwerveModuleState( m_driveEncoder.getVelocity(), new Rotation2d(getTurnEncoderRadians()) );
        }

        /**
         * Sets the desired state for the module.
         *
         * @param desiredState Desired state with speed and angle.
         */
        public void setDesiredState(SwerveModuleState desiredState) {
            // Optimize the reference state to avoid spinning further than 90 degrees
            SwerveModuleState state = SwerveModuleState.optimize( desiredState, new Rotation2d(getTurnEncoderRadians()) );

            // Calculate the drive output from the drive PID controller.
            final double driveOutput = m_drivePIDController.calculate( m_driveEncoder.getVelocity(), state.speedMetersPerSecond );

            

            final double signedAngleDifference = closestAngleCalculator(getTurnEncoderRadians(), state.angle.getRadians());
            double rotateMotorPercentPower = signedAngleDifference / (Math.PI); //proportion error control

            m_driveMotor.setVoltage((driveOutput / Drivetrain.kMaxSpeed) * Math.cos(rotateMotorPercentPower));
            m_turningMotor.setVoltage(rotateMotorPercentPower);
        }

        /**
         * Applies the absolute encoder offset value and converts range
         * from 0-1 to 0-2 pi radians
         * @return Angle of the absolute encoder in radians
         */
        private double getTurnEncoderRadians() {
            double appliedOffset = (m_TurnPWMEncoder.getOutput() - turnEncoderOffset) % 1;
            return appliedOffset * 2 * Math.PI;
        }

        /**
         * Calculates the closest angle and direction between two points on a circle.
         * @param currentAngle <ul><li>where you currently are</ul></li>
         * @param desiredAngle <ul><li>where you want to end up</ul></li>
         * @return <ul><li>signed double of the angle (rad) between the two points</ul></li>
         */
        public double closestAngleCalculator(double currentAngle, double desiredAngle) {
            double signedDiff = 0.0;
            double rawDiff = currentAngle > desiredAngle ? currentAngle - desiredAngle : desiredAngle - currentAngle; // find the positive raw distance between the angles
            double modDiff = rawDiff % (2 * Math.PI); // constrain the difference to a full circle

            if (modDiff > Math.PI) { // if the angle is greater than half a rotation, go backwards
                signedDiff = ((2 * Math.PI) - modDiff); //full circle minus the angle
                if (desiredAngle > currentAngle) signedDiff = signedDiff * -1; // get the direction that was lost calculating raw diff
            }
            else {
                signedDiff = modDiff;
                if (currentAngle > desiredAngle) signedDiff = signedDiff * -1;
            }
            return signedDiff;
        }
}
