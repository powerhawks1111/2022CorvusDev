package frc.robot.variables;
import com.kauailabs.navx.frc.AHRS; 
import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.filter.SlewRateLimiter;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Relay;
import edu.wpi.first.wpilibj.Relay.Direction;
import com.kauailabs.navx.frc.AHRS;

import frc.robot.SmartDashboardUpdater;
import frc.robot.subsystems.DriveSubsystem;
import frc.robot.subsystems.Drivetrain;
import frc.robot.variables.Objects;

public class Objects {
    public AHRS navx = new AHRS();
    public DriveSubsystem drive = new DriveSubsystem();
    public Drivetrain m_swerve = new Drivetrain(navx);
    public Relay relay = new Relay(0, Direction.kReverse);
    public static SmartDashboardUpdater smartDashboardUpdater = new SmartDashboardUpdater();
}
