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


    void readFile();

    Entry makeEntry(String text);

    boolean addEntry(Entry e);

    void writeEntryToFile(Entry e);

    ArrayList<? extends Entry> getEntries();

     boolean addListOfEntries(ArrayList<? extends Entry> e);
}
