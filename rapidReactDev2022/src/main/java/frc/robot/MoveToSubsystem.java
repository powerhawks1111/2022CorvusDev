package frc.robot;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class MoveToSubsystem extends SubsystemBase{
    double[] componentSpeeds = {0,0};
    boolean inRange;

    public MoveToSubsystem() {

    }

    @Override
    public void periodic() {
        //called once per scheduler run
    }

    public void translateToPosition(Objects objects, double desiredPositionX, double desiredPositionY, double speedScale) {
        double xDisplace =  objects.m_swerve.getCurrentPose2d().getX();
        double yDisplace = objects.m_swerve.getCurrentPose2d().getY();
        double xMovement = desiredPositionX - xDisplace;
        double yMovement = desiredPositionY - yDisplace;
        if (Math.abs(xMovement)<5 && Math.abs(yMovement)<5) {
            inRange = true;
        } else {
            inRange = false;
        }
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
    }

    public boolean moveFinished () {
        return inRange;
    }
}
