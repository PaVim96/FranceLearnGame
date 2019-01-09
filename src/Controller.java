import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;

public class Controller{


    private String fileName;
    private ArrayList<Entry> entries;


      Controller(String fileName){
        this.fileName = fileName;
        entries = new ArrayList<>();
        try {
            readFile();
        }
        catch (WordErrorException x){
            x.printStackTrace();
            System.out.println("File contains word that isn't in the right formatting");
        }
    }


    public void startGame(Game game){
        JFrame gameWindow = new JFrame();
        Container content = gameWindow.getContentPane();
        content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));


        //make list of GamingEntries which are needed for the game
        ArrayList<GamingEntry> entriesForGame = makeGamingEntries(game.getPlayingEntries());
        for(GamingEntry x : entriesForGame){
            JPanel panel = new JPanel();
            panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
            panel.add(x.getInputArticle());
            panel.add(x.getWordLabel());
            content.add(panel);
        }

        //TODO: Have to think about how to implement the result button
        //TODO: which means i have to think about how to store the inputs of JTextField
        gameWindow.setLocationRelativeTo(null);
        gameWindow.pack();
        gameWindow.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        gameWindow.setVisible(true);
    }

    private ArrayList<GamingEntry> makeGamingEntries(ArrayList<Entry> inputEntries){
          ArrayList<GamingEntry> result = new ArrayList<>();
        for (Entry x : inputEntries){
            String word = x.getWord();
            String article = x.getGender();
            GamingEntry i = new GamingEntry(word,article);
            result.add(i);
        }

          return result;
    }

    public boolean addEntry(Entry e){
         boolean isAlreadyThere = checkExisting(e);

         if(!isAlreadyThere){
             entries.add(e);
             writeEntryToFile(e);
             return true;
         }
         else {
             System.out.printf("Word %s is already there", e.getWord());
             System.out.println();
             return false;
         }
    }

    public void addListOfEntries(ArrayList<Entry> e){
         for(Entry x: e)
             addEntry(x);
    }

    private boolean checkExisting(Entry e){
         boolean result = false;
         String inputWord = e.getWord();


         for(Entry x: entries){
             String compareWord = x.getWord();
             if(inputWord.equals(compareWord))
                 result = true;
         }

         return result;
    }


    private void readFile() throws WordErrorException{
         try {
             BufferedReader reader = new BufferedReader(new FileReader(fileName));

             String line = reader.readLine();
             int countLine = 0;

             while(line != null && line.contains(":")){
                 String[] entry = line.split(":");
                 String word = entry[0].trim();
                 if(countLine == 0)
                 word = word.replaceAll("[\uFEFF-\uFFFF]", "");


                 String article = entry[1].trim();

                 Entry e = new Entry(word,article);
                 entries.add(e);
                 countLine++;
                 line = reader.readLine();
             }
             reader.close();
         }
         catch (IOException e ){
             System.out.println("Couldn't open file");
             e.printStackTrace();
         }
    }
    private void writeListToFile(ArrayList<Entry> e ){
         for(Entry x: e ){
             writeEntryToFile(x);
         }
    }

    private void writeEntryToFile(Entry e){
         try{
             BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, true));

             String wordContent = e.getEntryforFile();
             writer.write(wordContent);
             writer.flush();
         }
         catch(IOException x){
             System.out.println("There was an error while opening the file to write it");
             x.printStackTrace();
         }
    }

    public ArrayList<Entry> getEntries(){
          return entries;
    }

}