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

    // public void translateToPosition(double desiredPositionX, double desiredPositionY, double speedScale) {
    //     double xDisplace =  Objects.drivetrain.getCurrentPose2d().getX();
    //     SmartDashboard.putNumber("xDisplace", xDisplace);
    //     double yDisplace = Objects.drivetrain.getCurrentPose2d().getY();
    //     SmartDashboard.putNumber("yDisplace", yDisplace);
    //     double xMovement = desiredPositionX - xDisplace;
    //     double yMovement = desiredPositionY - yDisplace;
    //     if (Math.abs(xMovement)<0.25 && Math.abs(yMovement)<0.25) {
    //         inRange = true;
    //     } else {
    //         inRange = false;
    //     }
    //     if (Math.abs(xMovement) >= 2 ) {
    //         if (xMovement<0) {
    //         xMovement = -speedScale;
    //         } else {
    //         xMovement = speedScale;
    //         }
    //         componentSpeeds[0] = xMovement;
    //     } else {
    //         componentSpeeds[0] =  Math.pow(speedScale * xMovement/2, 3);
    //     }

    //     if (Math.abs(yMovement) >= 2 ) {
    //         if (yMovement<0) {
    //             yMovement = -speedScale;
    //         } else {
    //             yMovement = speedScale;
    //         }
    //         componentSpeeds[1] = yMovement;    
    //     } else {
    //         componentSpeeds[1] = Math.pow(speedScale * yMovement/2, 3);
    //     }

    //     Objects.drivetrain.drive(componentSpeeds[0], componentSpeeds[1], 0, true);
    // }

    public void translateToPosition(double xPositionDesired, double yPositionDesired, double rotationDesired, double driveSpeed, double rotateSpeed) {
        double xDisplace =  Objects.drivetrain.getCurrentPose2d().getX() - xPositionDesired;
        SmartDashboard.putNumber("xDisplace", xDisplace);
        double yDisplace = Objects.drivetrain.getCurrentPose2d().getY() - yPositionDesired;
        SmartDashboard.putNumber("yDisplace", yDisplace);
        double currHeading = Objects.navx.getRotation2d().getRadians();
        SmartDashboard.putNumber("navxHeading", currHeading);

        double xTranslateSpeed = Math.min(Math.abs(xDisplace / 24), 1) * driveSpeed * (xDisplace - Math.abs(xDisplace));
        double yTranslateSpeed = Math.min(Math.abs(yDisplace / 24), 1) * driveSpeed * (yDisplace - Math.abs(yDisplace));
        double rotationSpeed = Math.min(closestAngleCalculator(currHeading, rotationDesired) / Math.PI, 1) * rotateSpeed;

        Objects.drivetrain.drive(xTranslateSpeed, yTranslateSpeed, rotationSpeed, true);
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
