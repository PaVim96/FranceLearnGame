import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;

public class ControllerTranslate implements  Controller {
    private String fileName;
    private ArrayList<EntryTranslate> entries;

    public ControllerTranslate(String fileName){
        this.fileName = fileName;
        entries = new ArrayList<>();
        readFile();
    }

    @SuppressWarnings("unchecked")
    public void startGame(int number, boolean userInputGerman){
        Game game = new GameTranslate(this, number);
        JFrame gameWindow = new JFrame();
        Container content = gameWindow.getContentPane();
        content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));


        //make list of GamingEntries which are needed for the game
        ArrayList<GamingEntry> entriesForGame = makeGamingEntries((ArrayList<EntryArticle>) game.getPlayingEntries(), userInputGerman);
        for(GamingEntry x : entriesForGame){
            JPanel panel = new JPanel();
            panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
            panel.add(x.getInputWord());
            panel.add(Box.createRigidArea(new Dimension(10,1)));
            panel.add(x.getWordLabel());
            panel.add(x.getMarker());
            content.add(panel);
        }
        game.addResultButton(this, content, entriesForGame, game, userInputGerman);
        gameWindow.setLocationRelativeTo(null);
        gameWindow.pack();
        gameWindow.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        gameWindow.setVisible(true);
    }

    @Override
    @SuppressWarnings("unchecked")
    /**
     * method which makes gamingEntries based off of the playing option of translation
     * if userInputGerman == true we want to have the input word as german and the label word as french
     */
    public ArrayList<GamingEntry> makeGamingEntries(ArrayList<? extends Entry> inputEntries, boolean userInputGerman) {
        ArrayList<GamingEntry> result = new ArrayList<>();
        ArrayList<EntryTranslate> toIterate = (ArrayList<EntryTranslate>) inputEntries;
        if (userInputGerman) {
            for (EntryTranslate x : toIterate) {
                String labelWord = x.getFrench();
                String input = x.getGerman();
                GamingEntry i = new GamingEntry(labelWord, input);
                result.add(i);
            }
        }
        else {
            for (EntryTranslate x : toIterate) {
                String labelWord = x.getGerman();
                String input = x.getFrench();
                GamingEntry i = new GamingEntry(labelWord, input);
                result.add(i);
            }
        }
        return result;
    }

    @Override
    public boolean checkExisting(Entry e) {
        EntryTranslate entry = (EntryTranslate) e;
        boolean result = false;
        String inputWord = entry.getGerman();


        for(EntryTranslate x: entries){
            String compareWord = x.getGerman();
            if(inputWord.equals(compareWord))
                result = true;
        }

        return result;
    }

    @Override
    public boolean checkInput(String text) {
        System.out.println(text);
        String regex = "\\s*[A-Za-zÀ-Úà-ú][a-zà-ú]*\\s*[A-ZÀ-Úa-zà-ú][a-zà-ú]*(/[a-zà-ú]*)?\\s*";
        System.out.println(text.matches(regex));
        return text.matches(regex);
    }

    @Override
    public void readFile() {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(fileName));

            String line = reader.readLine();
            int countLine = 0;

            while(line != null && line.contains(":")){
                String[] entry = line.split(":");
                String german = entry[0].trim();
                if(countLine == 0)
                    german = german.replaceAll("[\uFEFF-\uFFFF]", "");


                String french = entry[1].trim();

                EntryTranslate e = new EntryTranslate(german,french);
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

    @Override
    public Entry makeEntry(String text) {
        boolean ok = checkInput(text);
        if(ok){
            text = text.trim(); // deletes beginning and ending whitespaces
            int lastWhite = text.indexOf(" ");
            String german = text.substring(0, lastWhite).trim();
            String french = text.substring(lastWhite).trim();
            return new EntryTranslate(german, french);
        }
        else{
            return null;
        }
    }

    @Override
    public boolean addEntry(Entry e) {
        EntryTranslate entry = (EntryTranslate) e;
        boolean isAlreadyThere = checkExisting(entry);

        if (!isAlreadyThere) {
            entries.add(entry);
            writeEntryToFile(entry);
            return true;
        } else {
            System.out.printf("Word %s is already there", entry.getGerman());
            System.out.println();
            return false;
        }
    }

    @Override
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

    @Override
    public ArrayList<? extends Entry> getEntries() {
        return entries;
    }

    @Override
    public boolean addListOfEntries(ArrayList<? extends Entry> e) {
        boolean result = false;
        for(Entry x: e) {
            boolean thisOne = addEntry(x);
            if (!result && thisOne ){
                result = true;
            }
        }
        return result;
    }
}
