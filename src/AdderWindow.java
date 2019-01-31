import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class AdderWindow extends JFrame {
    Container window;
    int numberOfAdd;
    Controller control;
    ArrayList<JTextField> inputs;

    public AdderWindow(Controller control, int number){
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

    private void addEntryLabels(int number){
        for(int i = 0; i < number; i++){
            JTextField addField = new JTextField("");
            addField.setMaximumSize(new Dimension(250, 50));
            inputs.add(addField);
            window.add(addField);
        }
    }

    private void addOkCancel(){
        JButton ok = new JButton("OK");
        JButton cancel = new JButton("Cancel");

        ok.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ArrayList<Entry> toAdd = makeEntries(inputs);
                if(toAdd.size() != 0) {
                    control.addListOfEntries(toAdd);
                    //Todo: show added entries
                    String message = toAdd.size() == 1 ? "Added valid entry successfully" : "Added valid entries successfully";
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

    private ArrayList<Entry> makeEntries(ArrayList<JTextField> inputs){
        ArrayList<Entry> result = new ArrayList<>();
        for(JTextField x : inputs){
            Entry e = control.makeEntry(x.getText());
            if(e != null){
                result.add(e);
            }
        }
        return result;
    }

    private void addInfo(){
        JLabel info = new JLabel("Enter article and word down below ");
        window.add(info);
    }
    private void initWindow(){
        setTitle("Add vocabulary to game");
        setLocationRelativeTo(null);
        pack();
        setVisible(true);

    }
}
