import javax.swing.*;
import java.awt.*;

/**
 * class which represents one word with its name and corresponding article
 */
public class Entry {
    private boolean isMale, isFemale;
    private String wordName;
    private String article;


    public Entry(String name, String article) throws WordErrorException {
        wordName = name;
        this.article = article;

        switch(article){
            case "le": isMale = true; break;
            case "la": isFemale = true; break;
            default: throw new WordErrorException("Word needs to be either le or la");
        }
    }
    public static Entry handleInput(Container pane, String word, boolean male, boolean female){
        Entry result = null;
        try {
            if (male){
                result =  new  Entry(word, "le");
            }
            else if (female){
                result =  new Entry(word, "la");
            }

        }
        catch (WordErrorException x){
            x.printStackTrace();
        }
        return result;
    }

    public String getWord(){
        return wordName;
    }

    public String getGender() {return article; }



    /**
     * returns the format I used for writing in a file which consists of all words with their article
     * example  word : le/la
     * @return returns word and format for writing in a file
     */
    public String getEntryforFile(){
        return  wordName + " " + ":" + " " + article + "\r\n";
    }

}
