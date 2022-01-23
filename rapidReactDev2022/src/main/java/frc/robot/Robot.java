// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.filter.SlewRateLimiter;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.Joystick;
import com.kauailabs.navx.frc.AHRS; 
import frc.robot.Objects;

public class Robot extends TimedRobot {
    private final Objects objects = new Objects();
    private final XboxController m_controller = new XboxController(0);
    private final Joystick m_JoystickLeft = new Joystick(0);
    private final Joystick m_JoystickRight = new Joystick(1);
    private final Drivetrain m_swerve = new Drivetrain(objects.navx);


    // Slew rate limiters to make joystick inputs more gentle; 1/3 sec from 0 to 1.
    private final SlewRateLimiter m_xspeedLimiter = new SlewRateLimiter(3);
    private final SlewRateLimiter m_yspeedLimiter = new SlewRateLimiter(3);
    private final SlewRateLimiter m_rotLimiter = new SlewRateLimiter(3);

    @Override
    public void autonomousPeriodic() {
        driveWithJoystick(false);
        m_swerve.updateOdometry();
    }

    @Override
    public void teleopPeriodic() {
        driveWithJoystick(true);
    }

    private void driveWithJoystick(boolean fieldRelative) {
        final var xSpeed = m_xspeedLimiter.calculate(MathUtil.applyDeadband(m_JoystickLeft.getRawAxis(0), 0.05)) * Drivetrain.kMaxSpeed;
        final var ySpeed = m_yspeedLimiter.calculate(MathUtil.applyDeadband(m_JoystickLeft.getRawAxis(1), 0.05)) * Drivetrain.kMaxSpeed;
        final var rot = m_rotLimiter.calculate(MathUtil.applyDeadband(m_JoystickRight.getRawAxis(0), 0.05)) * Drivetrain.kMaxAngularSpeed;

        m_swerve.drive(xSpeed, ySpeed, rot, fieldRelative);
    }
}
