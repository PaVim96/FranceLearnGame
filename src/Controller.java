import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;


public class Controller {
    private ArrayList<Entry> wordPackage;
    private File containsWords;
    private String fileName;
    private boolean exists;


    public Controller(String fileName){
        this.fileName = fileName;
        containsWords = new File(fileName);
        exists = containsWords.exists();
        wordPackage = new ArrayList<Entry>();




    }

    /**
     * adds one entry to the package and writes it into file using method below
     * @param e entry to add
     */
    public void addEntry(Entry e){

        wordPackage.add(e);
        writeEntryToFile(e);
    }

    /**
     * adds a whole list of entries to the package and writes it into file
     * @param e list of entries to add
     */
    public void addPackageOfEntries(ArrayList<Entry> e){
        wordPackage.addAll(e);
        writeListToFile(e);
    }

    /**
     * writes one entry into file
     */
    private void writeEntryToFile(Entry e ) {
        String content = e.getEntryforFile();

        try {
            Files.write(Paths.get(containsWords.getPath()), content.getBytes(), StandardOpenOption.APPEND);
        }
        catch (IOException i){
            System.out.println("Couldn't write file");
            i.printStackTrace();
        }
    }

    /**
     * writes List of entries into file
     * @param e list of entries
     */
    private void writeListToFile(ArrayList<Entry> e){

        try {
            for(int i = 0; i < e.size(); i++){
                Files.write(Paths.get(containsWords.getPath()), e.get(i).getEntryforFile().getBytes(), StandardOpenOption.APPEND);
            }
        }
        catch(IOException i){
            System.out.println("Couldn't write file");
            i.printStackTrace();
        }

    }


    public ArrayList<Entry> getEntries(){
        return wordPackage;
    }


}
