package frc.robot.subsystems;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.variables.Objects;
import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import frc.robot.variables.Motors;
import frc.robot.variables.Objects;
import frc.robot.DriveAndOperate;

import com.revrobotics.SparkMaxPIDController;
import com.revrobotics.CANSparkMax.ControlType;
import com.revrobotics.RelativeEncoder;
import frc.robot.variables.Motors;
import frc.robot.variables.Objects;
import edu.wpi.first.wpilibj.DoubleSolenoid;
public class IntakeAuto extends SubsystemBase{


    public IntakeAuto() {

    }

    @Override
    public void periodic() {
        //called once per scheduler run
    }
    public void extendIntake() {
        Objects.intakeSubsystem.extendIntake();
    }

    /**
     * Activates relay to retract intake piston
     */
    public void retractIntake() {
        Objects.intakeSubsystem.retractIntake();
    }

}

