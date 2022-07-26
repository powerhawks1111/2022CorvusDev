package frc.robot.subsystems;

import com.revrobotics.SparkMaxPIDController;
import com.revrobotics.CANSparkMax.ControlType;

import frc.robot.variables.Motors;
import frc.robot.variables.Objects;
import edu.wpi.first.wpilibj.DoubleSolenoid;

public class ClimbSubsystem {
    private double minEncoderVal = 0;
    private double maxEncoderVal = 600;
   
    /**
     * Constructor
     */
    public ClimbSubsystem() {
        //TODO tune these later
        
    }

    
}
