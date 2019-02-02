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

    /**
     * modified version of makeGamingEntries
     * @param inputEntries entries for the game
     * @param frenchToGerman if this is true, we want to translate french words to german
     * @return returns List of Gaming Entries which can be added to the window of the game
     */
    public ArrayList<GamingEntry> makeGamingEntries(ArrayList<EntryTranslate> inputEntries, boolean frenchToGerman) {
        ArrayList<GamingEntry> result = new ArrayList<>();
        for (EntryTranslate x : inputEntries) {
            String german = x.getGerman();
            String french = x.getFrench();
            GamingEntry i;
            if(frenchToGerman)
                i = new GamingEntry(french, german);
            else {
                i = new GamingEntry(german, french);
            }
            result.add(i);
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


    /**
     * method which checks if user translation matches the translation which is written in the file
     * @param german if input is written german
     * @param french same for french
     * @param labelTranslation the translation which stands before input
     * @param input what the user has inputted as translation
     * @return returns true if the user translation is correct
     */
    public boolean translationCorrect(boolean german, boolean french, String labelTranslation, String input){
        System.out.printf("my input is %s, the label word is %s ", input, labelTranslation);
        if(input == null || input.length() == 0){
            return false;
        }
        for(EntryTranslate x: entries){
            if (german){
               if (x.getGerman().equals(input)){
                   String shouldBe = x.getFrench();
                   System.out.printf("in the file is the word %s\n", shouldBe);
                   if(shouldBe.equals(labelTranslation))
                       return true;
               }
            }
            else if (french) {
                if (x.getFrench().equals(input)){
                    String shouldBe = x.getFrench();
                    System.out.printf("in the file is the word %s\n", shouldBe);
                    if (shouldBe.equals(labelTranslation))
                        return true;
                }
            }
        }
        return false;
    }

    @Override
    public boolean checkInput(String text) {
        System.out.println(text);
        String regex = "\\s*[A-Za-z][a-z]*\\s*[A-Za-z][a-z]*\\s*";
        System.out.println(text.matches(regex));
        return text.matches(regex);
    }

    public void startGame(int number, boolean frenchToGerman) {
        GameTranslate game = new GameTranslate(this, number);
        JFrame gameWindow = new JFrame();
        Container content = gameWindow.getContentPane();
        content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));


        //make list of GamingEntries which are needed for the game
        ArrayList<GamingEntry> entriesForGame = makeGamingEntries(game.getPlayingEntries(), frenchToGerman);
        for(GamingEntry x : entriesForGame){
            JPanel panel = new JPanel();
            panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
            panel.add(x.getInputArticle());
            panel.add(Box.createRigidArea(new Dimension(10,1)));
            panel.add(x.getWordLabel());
            panel.add(x.getMarker());
            content.add(panel);
        }
        GUI.addResultButton2(game, content, entriesForGame);
        gameWindow.setLocationRelativeTo(null);
        gameWindow.pack();
        gameWindow.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        gameWindow.setVisible(true);
    }




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
        System.out.println(text);
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
