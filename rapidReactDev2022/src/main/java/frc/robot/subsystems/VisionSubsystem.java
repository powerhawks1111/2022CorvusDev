package frc.robot.subsystems;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.math.filter.LinearFilter;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;
public class VisionSubsystem {
    double area;
    double yAngle;
    double xAngle;
    boolean validTargets;
    LinearFilter filter = LinearFilter.movingAverage(2);
    
    public void updateVision () {
        area = NetworkTableInstance.getDefault().getTable("limelight").getEntry("ta").getDouble(0);
        yAngle = NetworkTableInstance.getDefault().getTable("limelight").getEntry("ty").getDouble(0);
        xAngle = NetworkTableInstance.getDefault().getTable("limelight").getEntry("tx").getDouble(0);
        validTargets = NetworkTableInstance.getDefault().getTable("limelight").getEntry("tv").getBoolean(false);

        yAngle = filter.calculate(yAngle);
        xAngle = filter.calculate(xAngle);
        area = filter.calculate(area);

        SmartDashboard.putNumber("yAngle", yAngle);
        if (validTargets) {
            SmartDashboard.putBoolean("Targets", true);
        } else {
            SmartDashboard.putBoolean("Targets", false);
        }
    }

    public void calculateAngle () {

    }
}
