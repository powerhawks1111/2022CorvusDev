package frc.robot;
import com.kauailabs.navx.frc.AHRS; 
import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.filter.SlewRateLimiter;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Relay;
import edu.wpi.first.wpilibj.Relay.Direction;
import com.kauailabs.navx.frc.AHRS; 
import frc.robot.Objects;

public class Objects {
    public AHRS navx = new AHRS();
    public Drive drive = new Drive();
    public Drivetrain m_swerve = new Drivetrain(navx);
    public Relay relay = new Relay(0, Direction.kReverse);
}
