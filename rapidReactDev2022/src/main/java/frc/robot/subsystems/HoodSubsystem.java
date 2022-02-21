package frc.robot.subsystems;

import javax.swing.text.Position;

import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import frc.robot.variables.Motors;
import frc.robot.variables.Objects;

import com.revrobotics.SparkMaxPIDController;
import com.revrobotics.CANSparkMax.ControlType;
import com.revrobotics.RelativeEncoder;
public class HoodSubsystem {
    private SparkMaxPIDController hoodPID = Motors.hoodMotor.getPIDController();
    private double kP = 0.0001;
    private double kI = 0;
    private double kD = 0;

    double defaultPosition;
    double currentPosition;

    public HoodSubsystem() {
        hoodPID.setP(kP);
        hoodPID.setI(kI);
        hoodPID.setD(kD);
    }
    public void setHoodZero () {
        if (Objects.limitSwitch.get()) { //replace with limit switch
            //turn motors off
            defaultPosition = Motors.hoodMotor.getEncoder().getPosition();
            currentPosition = 0;
            //reset encoder
        } else { //move motor down until we hit
            Motors.hoodMotor.set(-.1);
        }

    }
    /**
     * Adjusts hood in the background of the program
     */
    public void adjustHood () { //runs in the background
        //gather vision output for distance to RPM/hood angle
        currentPosition = (((defaultPosition - Motors.hoodMotor.getEncoder().getPosition()) * 11)/540); //use this somehow lmao
        double wantedPosition = .1;
        double motorPosition = ((wantedPosition * 540) /11) - defaultPosition;
        hoodPID.setReference(motorPosition, ControlType.kPosition);
        //set hood based on encoder
    }



}
