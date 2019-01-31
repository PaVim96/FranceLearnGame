import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;

public class ControllerArticle implements Controller{


    private String fileName;
    private ArrayList<EntryArticle> entries;


      ControllerArticle(String fileName){
        this.fileName = fileName;
        entries = new ArrayList<>();
        readFile();


    }
    // method which returns JFrame if there is JFrame on top, else returns null
    /*public static JFrame getJFrame(Container panel){
          while(!(panel instanceof JFrame)){
              panel = panel.getParent();
          }
          return (JFrame) panel;
    }*/


    //TODO: Have to change the startButton so theres a name game which is started every time you click on start game (?)
    public void startGame(int number){
        System.out.println(number);
        Game game = new Game(this, number);
        System.out.println(game.getMaxPoints());
        JFrame gameWindow = new JFrame();
        Container content = gameWindow.getContentPane();
        content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));


        //make list of GamingEntries which are needed for the game
        ArrayList<GamingEntry> entriesForGame = makeGamingEntries(game.getPlayingEntries());
        for(GamingEntry x : entriesForGame){
            JPanel panel = new JPanel();
            panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
            panel.add(x.getInputArticle());
            panel.add(Box.createRigidArea(new Dimension(10,1)));
            panel.add(x.getWordLabel());
            panel.add(x.getMarker());
            content.add(panel);
        }
        GUI.addResultButton(game, content, entriesForGame);
        gameWindow.setLocationRelativeTo(null);
        gameWindow.pack();
        gameWindow.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        gameWindow.setVisible(true);
    }

     public ArrayList<GamingEntry> makeGamingEntries(ArrayList<EntryArticle> inputEntries){
          ArrayList<GamingEntry> result = new ArrayList<>();
        for (EntryArticle x : inputEntries){
            String word = x.getWord();
            String article = x.getGender();
            GamingEntry i = new GamingEntry(word,article);
            result.add(i);
        }
          return result;
    }

    public EntryArticle makeEntry(String text){
          boolean ok = checkInput(text);

          if(ok){
              text = text.trim(); // deletes beginning and ending whitespaces
              int lastWhite = text.lastIndexOf(" " );
              String article = text.substring(0, lastWhite).trim();
              String word = text.substring(lastWhite).trim();
              return new EntryArticle(word, article);

          }
          else{
              return null;
          }

    }


    public boolean checkInput(String message){
          String regex = "\\s*[l][ea]\\s*[A-Za-z][a-z]*(\\s*)";
          return message.matches(regex);
    }

    public boolean addEntry(Entry e){
        EntryArticle entry = (EntryArticle) e;
         boolean isAlreadyThere = checkExisting(entry);

         if(!isAlreadyThere){
             entries.add(entry);
             writeEntryToFile(entry);
             return true;
         }
         else {
             System.out.printf("Word %s is already there", entry.getWord());
             System.out.println();
             return false;
         }
    }

    public boolean  addListOfEntries(ArrayList<? extends Entry> e){
        boolean result = false;
         for(Entry x: e) {
           boolean thisOne = addEntry(x);
           if (!result && thisOne)
               result = true;
         }
         return result;
    }

    public boolean checkExisting(Entry e){
         EntryArticle entry = (EntryArticle) e;
         boolean result = false;
         String inputWord = entry.getWord();


         for(EntryArticle x: entries){
             String compareWord = x.getWord();
             if(inputWord.equals(compareWord))
                 result = true;
         }

         return result;
    }


    public void readFile(){
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

                 EntryArticle e = new EntryArticle(word,article);
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

    public void writeEntryToFile(Entry e){
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

    public ArrayList<EntryArticle> getEntries(){
          return entries;
    }

}