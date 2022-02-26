package frc.robot.variables;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel;

public class Motors {
    public static CANSparkMax shooterLeader = new CANSparkMax(9, CANSparkMaxLowLevel.MotorType.kBrushless);
    // public static CANSparkMax shooterFollower = new CANSparkMax(10, CANSparkMaxLowLevel.MotorType.kBrushless);

    public static CANSparkMax hoodMotor = new CANSparkMax(11, CANSparkMaxLowLevel.MotorType.kBrushless);

    public static CANSparkMax indexLeader = new CANSparkMax(12, CANSparkMaxLowLevel.MotorType.kBrushless);

    public static CANSparkMax intakeLeader = new CANSparkMax(13, CANSparkMaxLowLevel.MotorType.kBrushless);

    public static CANSparkMax climbLeader = new CANSparkMax(14, CANSparkMaxLowLevel.MotorType.kBrushless);


    
}
