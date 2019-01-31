import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class AdderWindowTranslate extends JFrame {
    private Container window;
    private int numberOfAdd;
    private  Controller control;
    private ArrayList<JPanel> inputs;


    public AdderWindowTranslate(Controller control, int number){
        this.control = control;
        inputs = new ArrayList<>();
        numberOfAdd = number;
        window = this.getContentPane();
        window.setLayout(new BoxLayout(window, BoxLayout.Y_AXIS));
        addInfo();
        addEntryLabels(numberOfAdd);
        addOkCancel();
        initWindow();
    }

    private void addInfo(){
        JLabel info = new JLabel("Enter german word in first and french word in second textfield");
        window.add(info);
    }

    private void addEntryLabels(int number){
        for(int i = 0; i < number; i++){
            JPanel whole = new JPanel();
            whole.setLayout(new BoxLayout(whole, BoxLayout.X_AXIS));
            JTextField german = new JTextField("");
            JTextField french = new JTextField("");
            whole.add(german);
            whole.add(french);
            whole.setMaximumSize(new Dimension(250, 50));
            inputs.add(whole);
            window.add(whole);
        }
    }

    private ArrayList<EntryTranslate> makeEntries(ArrayList<JPanel> inputs){
        ArrayList<EntryTranslate> result = new ArrayList<>();
        for(JPanel x : inputs){
            JTextField germanTextField = (JTextField) x.getComponent(0);
            String german = germanTextField.getText();
            JTextField frenchTextField = (JTextField) x.getComponent(1);
            String french = frenchTextField.getText();

            EntryTranslate e = (EntryTranslate) control.makeEntry(german + " " +  french);
            System.out.println(e);
            if(e != null){
                result.add(e);
            }
        }
        return result;
    }

    private void addOkCancel(){
        JButton ok = new JButton("OK");
        JButton cancel = new JButton("Cancel");

        ok.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ArrayList<EntryTranslate> toAdd = makeEntries(inputs);
                if(toAdd.size() != 0) {
                    boolean ok = control.addListOfEntries(toAdd);
                    //Todo: show added entries
                    String message;
                    if(ok)
                        message = toAdd.size() == 1 ? "Added valid entry successfully" : "Added valid entries successfully";
                    else
                        message = "Entry already in file";

                    JOptionPane.showMessageDialog(window, message);
                    dispose();
                }
                else {
                    JOptionPane.showMessageDialog(window, "No valid word to add");
                    dispose();
                }
            }
        });

        cancel.addActionListener(e -> dispose());


        window.add(ok);
        window.add(cancel);
    }

    private void initWindow(){
        setTitle("Add vocabulary to game");
        setLocationRelativeTo(null);
        pack();
        setVisible(true);

    }
}