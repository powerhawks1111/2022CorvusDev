package frc.robot.subsystems;

import javax.swing.text.Position;
import javax.swing.text.StyleContext.SmallAttributeSet;

import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import frc.robot.variables.Motors;
import frc.robot.variables.Objects;

import com.revrobotics.SparkMaxPIDController;
import com.revrobotics.CANSparkMax.ControlType;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import com.revrobotics.RelativeEncoder;
public class HoodSubsystem extends SubsystemBase{
    private SparkMaxPIDController hoodPID = Motors.hoodMotor.getPIDController();
    private double kP = 0.02; //was .01
    private double kI = 0;
    private double kD = 0;
    

    private boolean isZeroed = false;

    private double defaultPosition;
    private double currentPosition;
    private double homePosition;

    public HoodSubsystem() {
        hoodPID.setP(kP);
        hoodPID.setI(kI);
        hoodPID.setD(kD);
    }

    /**
     * Moves the hood to its home position based on the limit switch
     */
    public void setHoodZero () {
        if (Objects.limitSwitch.get()) { //replace with limit switch
            //turn motors off
            Motors.hoodMotor.stopMotor();
            homePosition = Motors.hoodMotor.getEncoder().getPosition();
            isZeroed = true;
            

            //reset encoder
        } else { //move motor down until we hit
            Motors.hoodMotor.set(-.05);
            
        }
        

    }

    /**
     * Adjusts hood in the background of the program
     */
    public void adjustHood (double wantedPosition) { //runs in the background
        //gather vision output for distance to RPM/hood angle
        SmartDashboard.putNumber("wantedHood", wantedPosition);
        double motorPosition = wantedPosition * 122 + homePosition;
        if (isZeroed&& motorPosition>homePosition) {
            hoodPID.setReference(motorPosition, ControlType.kPosition);
        } else {
            Motors.hoodMotor.stopMotor();
        }
        //currentPosition = (((defaultPosition - Motors.hoodMotor.getEncoder().getPosition()) * 11)/540); //use this somehow lmao

        
        //set hood based on encoder
    }

    public boolean getHoodZeroed() {
        return isZeroed;
    }

}
