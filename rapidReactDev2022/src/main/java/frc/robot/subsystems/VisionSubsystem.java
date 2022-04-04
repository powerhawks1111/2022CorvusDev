package frc.robot.subsystems;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.math.filter.LinearFilter;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.variables.Objects;
public class VisionSubsystem extends SubsystemBase{
    private double area;
    private double yAngle;
    private double xAngle;
    private boolean validTargets;
    private LinearFilter filter = LinearFilter.movingAverage(2);

    double cameraHeight = 37; //inches
    double targetHeight = 114;
    double correctedTargetHeight = targetHeight - cameraHeight;
    double cameraAngle = 35; //degrees
    double cameraAngleRadians = cameraAngle * (Math.PI / 180);
    boolean shoot = false;
    
    /**
     * Updates the local vision variables from the limelight network table
     */
    public void updateVision () {
        area = NetworkTableInstance.getDefault().getTable("limelight").getEntry("ta").getDouble(0);
        yAngle = NetworkTableInstance.getDefault().getTable("limelight").getEntry("ty").getDouble(0);
        xAngle = NetworkTableInstance.getDefault().getTable("limelight").getEntry("tx").getDouble(0);
        validTargets = NetworkTableInstance.getDefault().getTable("limelight").getEntry("tv").getBoolean(false);
        SmartDashboard.putNumber("xAngle", xAngle);
        
        SmartDashboard.putNumber("xPixy", Objects.pixyCamSubsystem.getPixyX(0));

        SmartDashboard.putNumber("YPixy", Objects.pixyCamSubsystem.getPixyY(0));

        SmartDashboard.putNumber("yAngle", yAngle);
        if (validTargets) {
            SmartDashboard.putBoolean("Targets", true);
        } else {
            SmartDashboard.putBoolean("Targets", false);
        }
    }

    /**
     * Calculates the shooter rpm based on the limelight distance regression model
     * @return Necessary shooter RPM
     */
    public double rpmFromVisionOld () {
        return (-22.185*yAngle) +1496; //1456
    }

    /**
     * Calculates the shooter rpm based on the limelight distance cubic regression model
     * @return Shooter RPM
     */
    public double rpmFromVisionCube () {
        return (-0.12906 * Math.pow(yAngle, 3)) + (2.167459 * Math.pow(yAngle, 2)) - (3.7657413 * yAngle) + (1345.6);
    }

    /**
     * Calculates the shooter rpm based on the limelight distance quadratic regression model
     * @return Shooter RPM
     */
    public double rpmFromVision () {
        return (0.680456 * Math.pow(yAngle, 2))-(31.10814 * yAngle) + (3845.7302); //c = 1358.11;

    }

    /**
     * Calculates how far we are from the goal using trig
     * @return Distance from the goal, in inches
     */
    public double calculateDistanceInches () {
        double currentTargetAngle = 35 + yAngle;
        double targetAngleRadians = currentTargetAngle * (Math.PI / 180);
        double targetDistance = correctedTargetHeight / (Math.tan(targetAngleRadians));
        return targetDistance;
    }

    /**
     * Calculates the amount to rotate away from the target based on how fast the robot is moving
     * @return
     */
    public double turnToTargetPower_whileMoving() {
        double ballFlightTime = 0; //ball flight vs yValue regression
        double chassisXSpeed = Objects.drivetrain.getChassisSpeeds().vyMetersPerSecond;//inch per second sideways
        double limelightOffset = Math.atan2(chassisXSpeed * ballFlightTime, calculateDistanceInches());

        double turnKp = 0.85;
        double constantLimelightCorrection = 2;
        double rotatePower = (xAngle - constantLimelightCorrection - limelightOffset) / (27 * turnKp); //we shoot to the right
        return rotatePower;
    }

    public double rpmFromVision_whileMoving() {
        double ballFlightTime = 0; //ball flight vs yValue regression
        double stationaryShotVelocityPrediction = calculateDistanceInches() / ballFlightTime;
        double adjustedShotVelocity = stationaryShotVelocityPrediction - Objects.drivetrain.getChassisSpeeds().vxMetersPerSecond;
        double adjustedRPM = 0; //regression of velocity vs rpm
        return adjustedRPM;
    }


    /**
     * Calculates how much rotate power to apply to the drivetrain to line up with the goal
     * @return Double from -1 to +1 of how fast to turn to the goal
     */
    public double turnToTargetPower() {
        double turnKp = 0.85;
        double rotatePower = (xAngle-2) / (27 * turnKp); //we shoot to the right
        return rotatePower;
    }

    /**
     * Limelight x angle
     * @return Double of target x angle from limelight (-27 to 27 degrees)
     */
    public double getXAngle() {
        updateVision();
        return xAngle;
    }

    /**
     * Limelight y angle
     * @return Double of target y angle from limelight (-20.5 to 20.5 degrees)
     */
    public double getYAngle() {
        updateVision();
        return yAngle;
    }

    /**
     * Whether or not the limelight has found any targets
     * @return Boolean of whether or not the limelight has targets
     */
    public boolean getHasValidTargets() {
        updateVision();
        return validTargets;
    }

    /**
     * Returns whether or not the target is in an acceptable range to shoot
     * @return Boolean of whether the target is close enough to the center to shoot
     */
    public boolean linedUp() {
        return (Math.abs(xAngle)<7);
    }


    /**
     * Calculates the correct hood angle based on a linear regression model
     * @return Hood motor value for hood PID reference (number of motor rotations)
     */
    public double hoodAngleFromVision () {
       double motorPosition = (((-.005403 * yAngle) + .04157));
        if (motorPosition <0) {
             motorPosition = 0;
         }
        return motorPosition;
    }

    public double hoodAngleFromVision_whileMoving() {
        double shooterRPM = rpmFromVision_whileMoving();
        double motorPosition = 0; //regression of shooter rpm vs hood angle
        if (motorPosition <0) {
            motorPosition = 0;
        }
        return motorPosition;
    }

    /**
     * Turns off limelight leds
     */
    public void turnOffLeds() {
        NetworkTableInstance.getDefault().getTable("limelight").getEntry("ledMode").setNumber(0);
    }

    /*
     * Turns on limelight leds
     */
    public void turnOnLeds() {
        NetworkTableInstance.getDefault().getTable("limelight").getEntry("ledMode").setNumber(3);
    }

    /**
     * Sets the limelight to led blink mode (annoying defense mode?)
     */
    public void blinkLeds() {
        NetworkTableInstance.getDefault().getTable("limelight").getEntry("ledMode").setNumber(2);
    }
}
