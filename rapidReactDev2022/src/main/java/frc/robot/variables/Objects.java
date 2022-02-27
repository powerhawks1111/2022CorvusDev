package frc.robot.variables;

import com.kauailabs.navx.frc.AHRS; 
import edu.wpi.first.wpilibj.Relay;
import edu.wpi.first.wpilibj.Relay.Direction;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.DigitalInput;
import com.revrobotics.*;

import frc.robot.DriveAndOperate;
import frc.robot.SmartDashboardUpdater;
import frc.robot.subsystems.ClimbSubsystem;
import frc.robot.commands.Scheduler;
import frc.robot.subsystems.DriveSubsystem;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.HoodSubsystem;
import frc.robot.subsystems.IndexSubsystem;
import frc.robot.subsystems.IntakeSubsystem;
import frc.robot.subsystems.MoveToSubsystem;
import frc.robot.subsystems.ShootSubsystem;
import frc.robot.subsystems.VisionSubsystem;
import edu.wpi.first.wpilibj.Compressor;


import frc.robot.variables.Objects;

public class Objects {
    /**
     * ---------------------------------------------------------------------------------------
     * Misc Objects
     * ---------------------------------------------------------------------------------------
     */
 
     public static AHRS navx = new AHRS();
    public static DoubleSolenoid intakePistonLeft = new DoubleSolenoid(PneumaticsModuleType.REVPH, 0, 1);
    public static DoubleSolenoid intakePistonRight = new DoubleSolenoid(PneumaticsModuleType.REVPH, 2, 3);
    public static Compressor compressor = new Compressor(1, PneumaticsModuleType.REVPH);
    public static DigitalInput limitSwitch = new DigitalInput(2);
    public static DigitalInput indexFirstSensor = new DigitalInput(0);
    public static DigitalInput indexShooterSensor = new DigitalInput(1);
    public static Relay relay = new Relay(0, Direction.kReverse);

    /**
     * ---------------------------------------------------------------------------------------
     * Subsystems
     * ---------------------------------------------------------------------------------------
     */
    public static ClimbSubsystem climbSubsystem = new ClimbSubsystem();
    public static DriveSubsystem driveSubsystem = new DriveSubsystem();
    public static Drivetrain drivetrain = new Drivetrain();
    public static IndexSubsystem indexSubsystem = new IndexSubsystem();
    public static IntakeSubsystem intakeSubsystem = new IntakeSubsystem();
    public static ShootSubsystem shootSubsystem = new ShootSubsystem();
    public static MoveToSubsystem moveToSubsystem = new MoveToSubsystem();
    public static SmartDashboardUpdater smartDashboardUpdater = new SmartDashboardUpdater();
    public static HoodSubsystem hoodSubsystem = new HoodSubsystem();
    public static VisionSubsystem visionSubsystem = new VisionSubsystem();

    public static Scheduler scheduler = new Scheduler();

}
