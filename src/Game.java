import java.util.ArrayList;
import java.util.Random;

public class Game {
    private int numberOfWords;
    private ArrayList<Entry> playingEntries;
    private int maxNumber;
    private ArrayList<Entry> allEntries;
    private double percentageScore;
    private double maxPoints;
    private double pointsReached;


    public Game(Controller controller, int wantedNumber){
        maxNumber = controller.getEntries().size();
        if (wantedNumber > maxNumber)
            numberOfWords = maxNumber;
        else
            numberOfWords = wantedNumber;

        makePLayingEntries(numberOfWords);
        percentageScore = 0.0;
        maxPoints = maxNumber;
        pointsReached = 0;
    }

    private void makePLayingEntries(int numberOfWords){
        ArrayList<Integer> randomNumbers = makeRandomNumbersInRange(numberOfWords, maxNumber);
        playingEntries = new ArrayList<>();

        for(Integer i : randomNumbers){
            Entry toAdd = allEntries.get(i);
            playingEntries.add(toAdd);
        }
    }

    /**
     * method which sets the number of words a user wants to play with
     * @param amount the wanted amount
     * @return true if amount is in range of maximum allowed amount, false if wanted amount is too high
     */
    public boolean setNumberOfWords(int amount){
        boolean tooMany = false;
        if (amount > maxNumber)
            tooMany = true;
        else {
            numberOfWords = amount;
        }
        return tooMany;
    }


    /**
     * method which generates a arraylist of random numbers
     * @param numberOfWords the number of random numbers one wants to have
     * @param maxNumber range for random numbers to be: [0, maxNumber-1]
     * @return ArrayList<Integer> of random numbers in particular range
     */
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


    public ArrayList<Entry> getPlayingEntries(){
        return playingEntries;
    }

    public int getGameWordAmount(){
        return numberOfWords;
    }

    public void incrementPoints(){
        pointsReached++;
    }
}
