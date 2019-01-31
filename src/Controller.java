import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public interface Controller {

    default JFrame getJFrame(Container panel){
        while(!(panel instanceof JFrame)){
            panel = panel.getParent();
        }
        return (JFrame) panel;
    }

    boolean checkExisting(Entry e);

    boolean checkInput(String text);


     void startGame(int number);

     ArrayList<GamingEntry> makeGamingEntries(ArrayList<Entry> inputEntries);

    void readFile();

    Entry makeEntry(String text);

    boolean addEntry(Entry e);

    void writeEntryToFile(Entry e);

    ArrayList<Entry> getEntries();

    public void addListOfEntries(ArrayList<Entry> e);
}
