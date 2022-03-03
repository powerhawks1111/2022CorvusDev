package frc.robot.subsystems;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.math.filter.LinearFilter;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
public class VisionSubsystem extends SubsystemBase{
    private double area;
    private double yAngle;
    private double xAngle;
    private boolean validTargets;
    private LinearFilter filter = LinearFilter.movingAverage(2);

    double cameraHeight = 37; //inches
    double targetHeight = 114;
    double cameraAngle = 35; //degrees
    double cameraAngleRadians = cameraAngle * (Math.PI / 180);
    
    public void updateVision () {
        area = NetworkTableInstance.getDefault().getTable("limelight").getEntry("ta").getDouble(0);
        yAngle = NetworkTableInstance.getDefault().getTable("limelight").getEntry("ty").getDouble(0);
        xAngle = NetworkTableInstance.getDefault().getTable("limelight").getEntry("tx").getDouble(0);
        validTargets = NetworkTableInstance.getDefault().getTable("limelight").getEntry("tv").getBoolean(false);

        

        SmartDashboard.putNumber("yAngle", yAngle);
        if (validTargets) {
            SmartDashboard.putBoolean("Targets", true);
        } else {
            SmartDashboard.putBoolean("Targets", false);
        }
    }
    public double rpmFromVision () {
        return (-22.185*yAngle) +1406; //1456
    }
    public double calculateDistanceInches () {
        double cameraToGoalInches = (targetHeight - cameraHeight) / Math.tan(cameraAngleRadians);
        return cameraToGoalInches;
    }

    public double distanceToRPM (double distanceToGoalInches) {
        double rpm = (4.373 * distanceToGoalInches) + 1001; //1051
        return rpm;
    }

    public double turnToTargetPower() {
        double turnKp = 0.85;
        double rotatePower = xAngle / (27 * turnKp);
        return rotatePower;
    }

    public double getXAngle() {
        updateVision();
        return xAngle;
    }

    public double getYAngle() {
        updateVision();
        return yAngle;
    }

    public boolean getHasValidTargets() {
        updateVision();
        return validTargets;
    }

    public double hoodAngleFromVision () {
        double motorPosition = -.00507*yAngle+.007908; //+.006908
        if (motorPosition <=0) {
             motorPosition = 0;
         }
        return motorPosition;
    }
}
