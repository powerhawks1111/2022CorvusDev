package frc.robot;
import edu.wpi.first.wpilibj.Relay;
import frc.robot.variables.Objects;

public class Driver {
    
    public void drive (Objects objects) {
        objects.drive.drive(objects);
        objects.m_swerve.updateOdometry();
        objects.relay.set(Relay.Value.kOn);
    }
}
