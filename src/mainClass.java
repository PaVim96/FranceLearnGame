import java.util.ArrayList;

public class mainClass {

    public static void main(String [] args) throws WordErrorException{
        Controller obj = new Controller("Words.txt");
        Entry test = new Entry("supermarché", "le");
        Entry test1 = new Entry("bonjour", "le");
        ArrayList<Entry> x = new ArrayList<Entry>();
        x.add(test);
        x.add(test1);
        x.add(test);

        obj.addPackageOfEntries(x);


    }
}
