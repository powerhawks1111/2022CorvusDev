// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.filter.SlewRateLimiter;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Relay;
import edu.wpi.first.wpilibj.Relay.Direction;
import com.kauailabs.navx.frc.AHRS; 
import frc.robot.Objects;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.CommandScheduler;

public class Robot extends TimedRobot {
    private final Objects objects = new Objects();

    @Override
    public void robotPeriodic() {
        CommandScheduler.getInstance().run();
    }
    
    @Override
    public void autonomousPeriodic() {
        objects.drive.drive(objects);
        objects.m_swerve.updateOdometry();
    }

    @Override
    public void teleopPeriodic() {
        objects.drive.drive(objects);
        objects.m_swerve.updateOdometry();
        objects.relay.set(Relay.Value.kOn);
    }

    
}
