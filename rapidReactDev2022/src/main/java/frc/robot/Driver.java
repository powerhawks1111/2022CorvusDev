package frc.robot;

public class Driver {
    
    public void drive (Objects objects) {
        objects.drive.drive(objects);
        objects.m_swerve.updateOdometry();
        objects.relay.set(Relay.Value.kOn);
    }
}
