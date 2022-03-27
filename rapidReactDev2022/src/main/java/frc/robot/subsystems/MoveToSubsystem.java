package frc.robot.subsystems;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.variables.Objects;

public class MoveToSubsystem extends SubsystemBase{
    double[] componentSpeeds = {0,0};
    boolean inRange;

    public MoveToSubsystem() {

    }

    @Override
    public void periodic() {
        //called once per scheduler run
    }
    public double turnToAngle (double desiredAngle, double rotSpeed) {
        double currentAngle = (Objects.navx.getAngle() * Math.PI)/180;
        double difference = closestAngleCalculator(currentAngle, desiredAngle*(Math.PI/180));
        double percentError = rotSpeed*(difference / 2 * (Math.PI)); //proportion error control
        return percentError;
    }

    /**
     * Autonomous focused function to move the robot to a particular location on the field relative to its starting position
     * @param xPositionDesired x position in inches
     * @param yPositionDesired y position in inches
     * @param rotationAngleDesired rotation angle in degrees
     * @param speed speed to drive, 0-1
     * @param decelParam parameter to set the slowdown curve for stopping at the end of the path (number of inches from endpoint to start slowing down)
     */
    public void translateToPosition(double xPositionDesired, double yPositionDesired, double rotationAngleDesired, double speed, double decelParam) {
        double currentPoseX = Objects.drivetrain.getCurrentPose2d().getX();
        double currentPoseY = Objects.drivetrain.getCurrentPose2d().getY();
        
        double rotation = -turnToAngle(rotationAngleDesired, speed);

        SmartDashboard.putNumber("angleValue",  rotation);

        double xPositionError = -xPositionDesired - currentPoseX;
        double yPositionError = yPositionDesired - currentPoseY;

        SmartDashboard.putNumber("xPositionError", xPositionError);
        SmartDashboard.putNumber("yPositionError", yPositionError);

        double xTranslatePower = Math.min((xPositionError / decelParam), 1) * speed;
        double yTranslatePower = Math.min((yPositionError / decelParam), 1) * speed;


        if (Math.abs(xPositionError) < 1 && Math.abs(yPositionError) < 1) {
            inRange = true;

        }
        else {
            inRange = false;
            Objects.drivetrain.drive(xTranslatePower, yTranslatePower, rotation, true);
        }
        
       

    }

    /**
     * Whether the robot has arrived at the destination
     * @return Boolean of whether or not the robot has completed its translation
     */
    public boolean moveFinished () {
        return inRange;
    }

    /**
     * Calculates the closest angle and direction between two points on a circle.
     * @param currentAngle <ul><li>where you currently are</ul></li>
     * @param desiredAngle <ul><li>where you want to end up</ul></li>
     * @return <ul><li>signed double of the angle (rad) between the two points</ul></li>
     */
    public double closestAngleCalculator(double currentAngle, double desiredAngle) {
        double signedDiff = 0.0;
        double rawDiff = currentAngle > desiredAngle ? currentAngle - desiredAngle : desiredAngle - currentAngle; // find the positive raw distance between the angles
        double modDiff = rawDiff % (2 * Math.PI); // constrain the difference to a full circle

        if (modDiff > Math.PI) { // if the angle is greater than half a rotation, go backwards
            signedDiff = ((2 * Math.PI) - modDiff); //full circle minus the angle
            if (desiredAngle > currentAngle) signedDiff = signedDiff * -1; // get the direction that was lost calculating raw diff
        }
        else {
            signedDiff = modDiff;
            if (currentAngle > desiredAngle) signedDiff = signedDiff * -1;
        }
        return signedDiff;
    }
}
