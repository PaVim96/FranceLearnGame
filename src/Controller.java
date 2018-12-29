import java.io.*;
import java.lang.reflect.Array;
import java.util.ArrayList;

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



    public void addEntry(Entry e){
         boolean isAlreadyThere = checkExisting(e);

         if(!isAlreadyThere){
             entries.add(e);
             writeEntryToFile(e);
         }
         else {
             System.out.printf("Word %s is already there", e.getWord());
             System.out.println();
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