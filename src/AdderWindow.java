import javax.swing.*;
import java.awt.*;

public class AdderWindow extends JFrame {
    Container window;
    String windowTitle;
    int numberOfAdd;
    Controller control;

    public AdderWindow(Controller control, int number, String title){
        this.control = control;
        windowTitle = title;
        numberOfAdd = number;
        window = this.getContentPane();
        initWindow();
    }

    private void initWindow(){
        setTitle(windowTitle);
        setLayout(new BoxLayout(window, BoxLayout.Y_AXIS));
        setLocationRelativeTo(null);
        pack();
        setVisible(true);

    }
}
