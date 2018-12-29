import java.util.ArrayList;

public class mainClass {

    public static void main(String [] args) throws WordErrorException, AlreadyInFileException{
        Controller obj = new Controller("Words.txt");
        GUI test = new GUI(1600,900, obj);






    }
}
