import javax.swing.*;
import java.awt.event.*;

public class Input implements MouseMotionListener, KeyListener {

    private JFrame frame;

    SaveFile saveFile;

    public static int MOUSE_X_POS;
    public static int MOUSE_Y_POS;

    public static boolean MOUSE_PRESSED;
    public static boolean MOUSE_RELEASED;
    public static long MOUSE_HOLD_DOWN_TIME;

    public static boolean MOUSE_CLICKED;

    public Input(JFrame frame)
    {
        this.frame = frame;

        frame.addMouseMotionListener(this);
        frame.addKeyListener(this);

        MOUSE_PRESSED = false;
        MOUSE_RELEASED = false;
    }
    public void mouseDragged(MouseEvent e) {

    }
    public void mouseMoved(MouseEvent e)
    {
        MOUSE_X_POS = e.getX();
        MOUSE_Y_POS = e.getY();
    }

    public void keyTyped(KeyEvent e) {

    }
    public void keyPressed(KeyEvent e) {
        //panah kanan code
        if (e.getKeyCode() == 39)
        {
            Cue.updateAngle(1);
        }
        //panah kiri code
        else if (e.getKeyCode() == 37)
        {
            Cue.updateAngle(-1);
        }
        else if (e.getKeyCode() == KeyEvent.VK_0)
        {

        }
    }
    public void keyReleased(KeyEvent e) {

    }
}
