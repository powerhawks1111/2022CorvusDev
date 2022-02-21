package frc.robot.subsystems;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.math.filter.LinearFilter;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;
public class VisionSubsystem {
    double area;
    Boolean validTargets;

    public void getVision () {
        area = NetworkTableInstance.getDefault().getTable("limelight").getEntry("ta").getDouble(0);
        validTargets = NetworkTableInstance.getDefault().getTable("limelight").getEntry("tv").getBoolean(false);
        LinearFilter filter = LinearFilter.movingAverage(2);
        area = filter.calculate(area);
        SmartDashboard.putNumber("Area", area);
    }
}
