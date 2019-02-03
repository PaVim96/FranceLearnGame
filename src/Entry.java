import javax.swing.*;
import java.awt.*;

/**
 * class which represents one word with its name and corresponding article
 */
public interface Entry {





    /**
     * returns the format I used for writing in a file which consists of all words with their article
     * example  word : le/la
     * @return returns word and format for writing in a file
     */
     String getEntryforFile();


     int getRank();

     void incrementRank();


}
