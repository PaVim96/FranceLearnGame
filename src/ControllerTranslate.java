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
        String regex = "\\s*[A-Za-z][a-z]*\\s*[A-Za-z][a-z]\\s*";
        return text.matches(regex);
    }

    @Override
    public void startGame(int number) {

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
            int lastWhite = text.lastIndexOf(" " );
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
    public void addListOfEntries(ArrayList<? extends Entry> e) {

    }
}
