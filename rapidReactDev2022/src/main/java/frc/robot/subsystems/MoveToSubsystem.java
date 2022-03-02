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
        double difference = closestAngleCalculator(currentAngle, desiredAngle);
        double percentError = rotSpeed*(difference / (Math.PI)); //proportion error control
        return percentError;
    }

    public void translateToPosition(double xPositionDesired, double yPositionDesired, double rotationAngleDesired, double speed) {
        double currentPoseX = Objects.drivetrain.getCurrentPose2d().getX();
        double currentPoseY = Objects.drivetrain.getCurrentPose2d().getY();
        
        double rotation = turnToAngle(rotationAngleDesired, speed);

        SmartDashboard.putNumber("angleValue",  rotation);

        double xPositionError = -xPositionDesired - currentPoseX;
        double yPositionError = yPositionDesired - currentPoseY;

        SmartDashboard.putNumber("xPositionError", xPositionError);
        SmartDashboard.putNumber("yPositionError", yPositionError);

        double xTranslatePower = Math.min((xPositionError / 10), 1) * speed;
        double yTranslatePower = Math.min((yPositionError / 10), 1) * speed;


        if (Math.abs(xPositionError) < 0.75 && Math.abs(yPositionError) < 0.75) {
            inRange = true;

        }
        else {
            inRange = false;
            Objects.drivetrain.drive(xTranslatePower, yTranslatePower, 0, true);
        }
        
       

    }

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
