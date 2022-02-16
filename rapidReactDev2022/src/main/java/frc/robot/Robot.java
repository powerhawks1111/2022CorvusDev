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
import frc.driver;

import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import frc.robot.variables.Objects;

public class Robot extends TimedRobot {
    /**
     * Main robot functions
     */
    Driver driver = new Driver();
    Operator operator = new Operator();
    Autonomous autonomous = new Autonomous();
    Test test = new Test();

    /**
     * Objects
     */
    private Objects objects = new Objects();

    /**
     * Robot variables
     */
    public String autoSelected;

    @Override
    public void robotInit() {

    }

    @Override
    public void robotPeriodic() {
    }

    @Override
    public void autonomousInit() {
        autoSelected = objects.smartDashboardUpdater.getAutoSelected();
    }

    @Override
    public void autonomousPeriodic() {
        switch (autoSelected) {
        case "Red Path":
            autonomous.redPath();
            break;
        case "Orange Path":
            autonomous.orangePath();
            break;
        case "Yellow Path":
            autonomous.yellowPath();
            break;
        case "Green Path":
            autonomous.greenPath();
            break;
        case "Blue Path":
            autonomous.bluePath();
            break;
        case "Purple Path":
            autonomous.purplePath();
            break;
        case "Default Path":
        default:
            autonomous.defaultPath();
            break;
        }
        
        objects.m_swerve.updateOdometry();
    }

    @Override
    public void teleopInit() {

    }

    @Override
    public void teleopPeriodic() {
        driver.drive(objects);
    }


}
