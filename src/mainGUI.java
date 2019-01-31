import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class mainGUI extends JFrame {
   private Container window;
   Controller obj, obj2;

    public mainGUI(int width, int height, Controller obj, Controller obj2){
        window = getContentPane();
        this.obj = obj;
        this.obj2 = obj2;
        window.setLayout(new BoxLayout(window, BoxLayout.Y_AXIS));
        window = getContentPane();
        initGui(width, height);

    }

    private void initGui(int width, int height){
        setTitle("Learn french by");
        addInfo();
        addGameOptions(width, height);
        pack();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        setLocationRelativeTo(null);
    }

    private void addInfo(){
        JLabel info = new JLabel("Below you can choose between learning words or articles for word");
        info.setAlignmentX(Component.CENTER_ALIGNMENT);
        window.add(info);
        window.add(Box.createRigidArea(new Dimension(1,15)));
    }

    private void addGameOptions(int width, int height){
        JPanel buttons = new JPanel();
        buttons.setLayout(new FlowLayout());
        JButton words = new JButton("Click to learn words");
        JButton articles = new JButton("Click to learn articles");

        words.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                GUI words = new GUI(width, height, obj2);
                dispose();
            }
        });

        articles.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                GUI articles = new GUI(width,height,obj);
                dispose();
            }
        });
        buttons.add(words);
        buttons.add(articles);
        window.add(buttons);

    }
}
