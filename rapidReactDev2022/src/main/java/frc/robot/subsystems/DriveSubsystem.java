package frc.robot.subsystems;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import frc.robot.variables.Objects;

public class DriveSubsystem {
    
    /**
     * Sends drive speeds to the drivetrain
     * @param objects
     * @param xSpeed -1 to +1 speed in X direction
     * @param ySpeed -1 to +1 speed in Y direction
     * @param rot -1 to +1 speed to rotate
     * @param fieldRelative true = field relative
     */
    public void driveSwerve(double xSpeed, double ySpeed, double rot, boolean fieldRelative) {
        Objects.drivetrain.drive(xSpeed, ySpeed, rot, fieldRelative);
    }

    public double turnToTarget() {
        return 0;
    }

    /**
     * Autonomously moves the robot in a line towards the desired position
     * <br><br>
     * Odometry based off of where the robot started
     * @param desiredPositionX Desired position X coordinate (meters)
     * @param desiredPositionY Desired position Y coordinate (meters)
     * @param speedScale 0 to +1 how fast to travel
     * @return double[] of the (x,y) speeds to give to the drivetrain
     */
    public double[] translateToPosition(double desiredPositionX, double desiredPositionY, double speedScale) {
        double xDisplace =  Objects.drivetrain.getCurrentPose2d().getX();
        double yDisplace = Objects.drivetrain.getCurrentPose2d().getY();
        SmartDashboard.putNumber("Xposition", xDisplace);
        SmartDashboard.putNumber("YPosition", yDisplace);
        double xMovement = desiredPositionX - xDisplace;
        double yMovement = desiredPositionY - yDisplace;
        double[] componentSpeeds = {0,0};
        if (Math.abs(xMovement) >= 10 ) {
            if (xMovement<0) {
            xMovement = -speedScale;
            } else {
            xMovement = speedScale;
            }
            componentSpeeds[0] = xMovement;
        } else {
            componentSpeeds[0] =  Math.pow(speedScale * xMovement/10, 3);
        }

        if (Math.abs(yMovement) >= 10 ) {
            if (yMovement<0) {
                yMovement = -speedScale;
            } else {
                yMovement = speedScale;
            }
            componentSpeeds[1] = yMovement;    
        } else {
            componentSpeeds[1] = Math.pow(speedScale * yMovement/10, 3);
        }
        return componentSpeeds;
    }
}
