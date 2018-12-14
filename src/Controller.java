import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.stream.Collectors;


public class Controller {
    private ArrayList<Entry> wordPackage;
    private File containsWords;
    private String fileName;


    public Controller(String fileName){
        this.fileName = fileName;
        containsWords = new File(this.fileName);
        wordPackage = new ArrayList<Entry>();
        readFile(fileName);



    }

    private void readFile(String fileName){

        try {
            BufferedReader read = new BufferedReader(new FileReader(fileName));

            try {
                String line = read.readLine();

                while(line != null || line.length() != 0 ){
                    System.out.println(line.length());
                    System.out.println(line);
                    String[] attributes = line.split(":");
                    System.out.println("1: " + attributes[0] + " 2: " + attributes[1] );
                    String word = attributes[0].substring(0, attributes[0].length() - 1);
                    String article = attributes[1].substring(0, attributes[1].length() -1 );
                    Entry e = new Entry(word, article);
                    wordPackage.add(e);
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (WordErrorException e) {
                e.printStackTrace();
            }


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }


    }
    private boolean checkExisting(Entry e ){
        boolean result = false;

        String toInput = e.getWord();

        for(int i = 0; i < wordPackage.size(); i++){
            String toCompare = wordPackage.get(i).getWord();
            if (toInput.equals(toCompare)){
                result = true;
                break;
            }
        }

        return result;
    }

    /**
     * adds one entry to the package and writes it into file using method below
     * @param e entry to add
     */
    public void addEntry(Entry e) throws AlreadyInFileException {
        boolean isThere = checkExisting(e);

        if(!isThere) {
            wordPackage.add(e);
            writeEntryToFile(e);
            System.out.println("hallo");
        }
        else{
            throw new AlreadyInFileException("Word is already written in File");
        }
    }

    /**
     * adds a whole list of entries to the package and writes it into file
     * @param e list of entries to add
     */
    public void addPackageOfEntries(ArrayList<Entry> e) throws AlreadyInFileException {


        for(Entry x : e){
            boolean isThere = checkExisting(x);
            if(isThere) {
                e.remove(x);
            }
        }

        if(e.size() != 0 ) {
            ArrayList<String> stillThere = new ArrayList<>();
            stillThere = (ArrayList<String>) e.stream().map(x -> x.getWord()).collect(Collectors.toList());
            for(String s : stillThere){
                System.out.println("Added the following word to file: " + s);
            }
            wordPackage.addAll(e);
            writeListToFile(e);

        }
        else{
            throw new AlreadyInFileException("ALl words already in file, none added");
        }
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
