import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

public class Game {
    private int numberOfWords;
    private ArrayList<Entry> playingEntries;
    private int maxNumber;
    private ArrayList<Entry> allEntries;
    private double percentageScore;
    private int maxPoints;
    private int pointsReached;


    public Game(Controller controller, int wantedNumber){
        maxNumber = controller.getEntries().size();
        if (wantedNumber > maxNumber)
            numberOfWords = maxNumber;
        else {
            numberOfWords = wantedNumber;
            maxNumber = wantedNumber;
        }

        allEntries = controller.getEntries();
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
    public int getPointsReached(){
        return pointsReached;
    }

    public int getMaxPoints(){
        return maxPoints;
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

    public boolean checkPoint(String input, String shouldBe){

        if (input.equals(shouldBe)) {
            incrementPoints();
            return true;
        }
        return false;
    }

    public ArrayList<Entry> getPlayingEntries(){
        return playingEntries;
    }

    public int getGameWordAmount(){
        return numberOfWords;
    }

    public void incrementPoints(){
        pointsReached++;
        percentageScore = (double) pointsReached / (double) maxPoints;
    }

    public double getPercentage(){
        return percentageScore;
    }

}
