import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.stream.Collectors;


public class GUI extends JFrame {
    Controller control;

    public GUI(int width, int height, Controller control){
        this.control = control;
        initGui(width, height);
    }

    /**
     * Method which sets the JPanel that is responsible for modifying entries
     * @param panel the container panel which is the content pane of the Window frame
     */
    private void setJPanel(JPanel panel){
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        addInfo(panel);
        addEntryButtons(panel);
        addShowButton(panel);
        panel.setVisible(true);
        panel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
    }

    /**
     * method which adds a button to show all entries
     * @param pane the container panel which is the adding jpanel
     */
    private void addShowButton(Container pane){
        JButton showEntries = new JButton("click me to show all Entries currently in file");
        showEntries.addActionListener(e -> showEntries());
        pane.add(showEntries);
    }

    private void showEntries(){
        System.out.println(control.getEntries().size());
        JFrame entryWindow = new JFrame();
        Container pane = entryWindow.getContentPane();
        pane.setLayout(new BoxLayout(pane, BoxLayout.Y_AXIS));

        ArrayList<JLabel> entries = new ArrayList<>();
       for(Entry x : control.getEntries()) {
           entries.add(new JLabel(x.getEntryforFile()));
       }

       System.out.println(entries.size());
       for (JLabel i : entries)
           pane.add(i);


       entryWindow.setLocationRelativeTo(null);
       entryWindow.setVisible(true);
       entryWindow.pack();
       entryWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    }


    private void addInfo(Container pane){
        JLabel infoText = new JLabel("On this region you can modify all entries in your list of entries");
        pane.add(infoText);
        pane.add(Box.createRigidArea(new Dimension(1,20)));

    }

    /**
     * method responsible for adding buttons that modify the file
     * @param panel container jpanel
     */
    private void addEntryButtons(JPanel panel){
        JButton oneEntry = new JButton("Add one entry to list");
        JButton moreEntry = new JButton("Add multiple entries to list");

        oneEntry.addActionListener(e -> control.addEntry(checkEntryInput(panel)));
        //moreEntry.addActionListener(e -> control.addListOfEntries(checkMultipleEntryInput(panel)));
        panel.add(oneEntry);
        panel.add(moreEntry);

    }

    /**
     * method responsible for handling the input of the JOptionPane
     * @param panel container jpanel
     * @return returns entry of input dialog if input was okay
     */
    private Entry checkEntryInput(JPanel panel){
        JCheckBox male = new JCheckBox("male");
        JCheckBox female = new JCheckBox("female");
        String message = "Add entry here and click whether female or male";
        Object[] messageDialog = {message, male, female};
        Entry newEntry = null;
        String word = "";
        while(newEntry == null || word.length() == 0 ) {
            word = JOptionPane.showInputDialog(messageDialog);
            boolean isMale = male.isSelected();
            boolean isFemale = female.isSelected();
            newEntry = Entry.handleInput(panel, word, isMale, isFemale);

            //TODO: create own JOPtionPane in order to make sure one can cancel adding entries
            if (newEntry == null)
                JOptionPane.showMessageDialog(panel, "Please select the gender of the word");
        }
        return newEntry;
    }


    /**
     * sets the basic window
     * @param width window width
     * @param height window height
     */
    private void initGui(int width, int height){
        setTitle("Learning articles");
        setSize(new Dimension(width, height));
        setLayout(new BorderLayout());
        Container pane = getContentPane();

        adderPane(pane);

        setVisible(true);
        pack();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

    }

    /**
     * sets the pane which holds all components to modify or show entries
     * @param pane container which is the window jframe in this case (content pane of it)
     */
    private void adderPane(Container pane){
        JPanel entryAdder = new JPanel();
        setJPanel(entryAdder);
        pane.add(entryAdder);
    }
}
