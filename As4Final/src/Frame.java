import javax.swing.*;
import java.awt.*;

public class Frame {
    public static void main(String[] args)
    {
        JFrame frame = new JFrame();
        final int FRAME_WIDTH = 600;
        final int FRAME_HEIGHT = 600;
        frame.setSize(FRAME_WIDTH, FRAME_HEIGHT);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Screen screen = new Screen();
        frame.add(screen, BorderLayout.CENTER);
        frame.setVisible(true);
        screen.start();
    }
}