import java.util.ArrayList;

public class mainClass {

    public static void main(String [] args) throws WordErrorException, AlreadyInFileException{
        Controller obj = new Controller("Words.txt");
        Entry test = new Entry("supermarché", "le");
        Entry test1 = new Entry("bonjour", "le");
        obj.addEntry(test);
        obj.addEntry(test1);




    }
}
