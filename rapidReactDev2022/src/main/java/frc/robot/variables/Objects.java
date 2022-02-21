package frc.robot.variables;

import com.kauailabs.navx.frc.AHRS; 
import edu.wpi.first.wpilibj.Relay;
import edu.wpi.first.wpilibj.Relay.Direction;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.DigitalInput;

import frc.robot.SmartDashboardUpdater;

import frc.robot.subsystems.DriveSubsystem;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.HoodSubsystem;
import frc.robot.subsystems.IndexSubsystem;
import frc.robot.subsystems.IntakeSubsystem;
import frc.robot.subsystems.ShootSubsystem;

import frc.robot.variables.Objects;

public class Objects {
    /**
     * ---------------------------------------------------------------------------------------
     * Misc Objects
     * ---------------------------------------------------------------------------------------
     */
    public static AHRS navx = new AHRS();
    public static Relay relay = new Relay(0, Direction.kReverse);
    public static DoubleSolenoid intakePiston = new DoubleSolenoid(PneumaticsModuleType.REVPH, 1, 2);
    public static DigitalInput indexIntakeSensor = new DigitalInput(0);
    public static DigitalInput indexMidSensor = new DigitalInput(2);
    public static DigitalInput indexShooterSensor = new DigitalInput(1);
    public static DigitalInput limitSwitch = new DigitalInput(3);

    /**
     * ---------------------------------------------------------------------------------------
     * Subsystems
     * ---------------------------------------------------------------------------------------
     */
    public static Drivetrain drivetrain = new Drivetrain();
    public static DriveSubsystem driveSubsystem = new DriveSubsystem();
    public static IndexSubsystem indexSubsystem = new IndexSubsystem();
    public static IntakeSubsystem intakeSubsystem = new IntakeSubsystem();
    public static ShootSubsystem shootSubsystem = new ShootSubsystem();
    public static SmartDashboardUpdater smartDashboardUpdater = new SmartDashboardUpdater();
    public static HoodSubsystem hoodSubsystem = new HoodSubsystem();

}
