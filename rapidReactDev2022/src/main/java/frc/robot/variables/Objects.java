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
import frc.robot.subsystems.IntakeAuto;
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
    public static DoubleSolenoid intakePistonLeft = new DoubleSolenoid(PneumaticsModuleType.REVPH, 0, 2);
    public static DoubleSolenoid intakePistonRight = new DoubleSolenoid(PneumaticsModuleType.REVPH, 1, 3);
    public static Compressor compressor = new Compressor(1, PneumaticsModuleType.REVPH);
    public static DigitalInput limitSwitch = new DigitalInput(21);
    public static DigitalInput indexFirstSensor = new DigitalInput(19);
    public static DigitalInput indexShooterSensor = new DigitalInput(20);
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
    public static IntakeAuto intakeAuto = new IntakeAuto();
    
    

    public static Scheduler scheduler = new Scheduler();


    /**
     * ---------------------------------------------------------------------------------------
     * Climb
     * ---------------------q------------------------------------------------------------------
     */
    public static DoubleSolenoid climbLeftRaise = new DoubleSolenoid(PneumaticsModuleType.REVPH, 8, 9);
    public static DoubleSolenoid climbRightRaise= new DoubleSolenoid(PneumaticsModuleType.REVPH, 10, 11);
    public static DoubleSolenoid bottomRightHook = new DoubleSolenoid(PneumaticsModuleType.REVPH, 4, 5);
    public static DoubleSolenoid topRightHook = new DoubleSolenoid (PneumaticsModuleType.REVPH, 6, 7);
    public static DoubleSolenoid topLeftHook = new DoubleSolenoid (PneumaticsModuleType.REVPH, 12, 13);
    public static DoubleSolenoid bottomLeftHook = new DoubleSolenoid (PneumaticsModuleType.REVPH, 15, 14); //intentionally reversed

}
