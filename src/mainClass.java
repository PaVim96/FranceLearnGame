import java.util.ArrayList;
import java.util.Random;

public class mainClass {

    public static void main(String [] args) {
        Controller obj = new ControllerArticle("Words.txt");
        Controller obj2 = new ControllerTranslate("Translate.txt");
        mainGUI test = new mainGUI(1600,900, obj, obj2);
    }


    public static ArrayList<Integer> makeRandomNumbersInRange(int numberOfWords, int maxNumber){
        ArrayList<Integer> randomNumbers = new ArrayList<>();
        Random generator = new Random();

        for(int i = 0; i < numberOfWords; i++){
            int number = generator.nextInt(maxNumber);
            if (randomNumbers.size() == 0 )
                randomNumbers.add(number);
            else {
                while (randomNumbers.contains(number))
                    number = generator.nextInt(maxNumber);

                randomNumbers.add(number);
            }
        }
        return randomNumbers;
    }
}
