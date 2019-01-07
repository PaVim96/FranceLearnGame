import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.stream.Collectors;


public class GUI extends JFrame {
    private Controller control;

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
        addInfo(panel, "On this region you can modify all entries in your list of entries");
        addEntryButtons(panel);
        addShowButton(panel);
        panel.setVisible(true);
        panel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
    }

    private void setGamingArea(JPanel panel){
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        addInfo(panel, "This area is for the game");
        // create a game with a default setting of 10 words
        Game game = new Game(control, 10);
        addStartButton(panel, game );
        //addOptionsButtons(panel); //TODO: implement
        panel.setVisible(true);
        panel.setBorder(BorderFactory.createLineBorder(Color.BLACK));


    }

    private void addStartButton(Container pane, Game game ){
        JButton start = new JButton("Start the game");
        start.addActionListener(e -> control.startGame(game));
        pane.add(start);
    }

    /**
     * method which adds a button to show all entries
     * @param pane the container panel which is the adding jpanel
     */
    private void addShowButton(Container pane){
        JButton showEntries = new JButton("Show entries");
        showEntries.addActionListener(e -> showEntries());
        pane.add(showEntries);
    }

    /**
     * method which makes a new window showing all entries in the file
     */
    private void showEntries(){
        if (control.getEntries().size() != 0) {
            JFrame entryWindow = new JFrame();
            Container pane = entryWindow.getContentPane();
            pane.setLayout(new BoxLayout(pane, BoxLayout.Y_AXIS));

            //Button which can be pressed to close window too
            JButton closeButton = new JButton("Close");
            closeButton.addActionListener(e -> closeFrame(entryWindow));

            ArrayList<JLabel> entries = new ArrayList<>();
            for (Entry x : control.getEntries()) {
                entries.add(new JLabel(x.getEntryforFile()));
            }

            for (JLabel i : entries)
                pane.add(i);

            pane.add(closeButton);
            entryWindow.setLocationRelativeTo(null);
            entryWindow.setVisible(true);
            entryWindow.pack();
            entryWindow.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        }
        else {
            JFrame frame = new JFrame("Empty file");
            Container content = frame.getContentPane();
            JOptionPane.showMessageDialog(content,"Your list of entries is empty");
            frame.pack();
            frame.setVisible(true);
            frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        }
    }

    private void closeFrame(JFrame window){
        window.dispose();
    }


    private void addInfo(Container pane, String message ){
        JLabel infoText = new JLabel(message);
        pane.add(infoText);
        pane.add(Box.createRigidArea(new Dimension(1,20)));

    }

    /**
     * method responsible for adding buttons that modify the file
     * @param panel container jpanel
     */
    private void addEntryButtons(JPanel panel){
        JButton oneEntry = new JButton("Add one entry");
        JButton moreEntry = new JButton("Add multiple entries");

        // adds the action listener to button which shows a message if input entry is already in file
        oneEntry.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                boolean wasAdded = control.addEntry(checkEntryInput(panel));
                if (!wasAdded)
                    JOptionPane.showMessageDialog(panel, "Your input is already in the list of words");
            }
        });
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
        Container pane = getContentPane();
        pane.setLayout(new BoxLayout(pane, BoxLayout.X_AXIS));

        adderPane(pane);
        gamingPane(pane);

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

    private void gamingPane(Container pane){
        JPanel gamingArea = new JPanel();
        setGamingArea(gamingArea);
        pane.add(gamingArea);
    }
}
