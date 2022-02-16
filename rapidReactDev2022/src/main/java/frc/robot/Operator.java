package frc.robot;
import edu.wpi.first.wpilibj.Joystick;

public class Operator {
    private final Joystick m_JoystickRight = new Joystick(1);

    public Joystick getJoystick () {
        return m_JoystickRight;
    }

}
