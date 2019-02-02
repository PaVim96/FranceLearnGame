import java.util.ArrayList;
import java.util.Random;

public class GameTranslate {
    private int numberOfWords;
    private ArrayList<EntryTranslate> playingEntries;
    private int maxNumber;
    private ArrayList<EntryTranslate> allEntries;
    private double percentageScore;
    private int maxPoints;
    private int pointsReached;
    ControllerTranslate control;

    public GameTranslate(ControllerTranslate controller, int wantedNumber) {
        maxNumber = controller.getEntries().size();
        control = controller;
        if (wantedNumber > maxNumber)
            numberOfWords = maxNumber;
        else {
            numberOfWords = wantedNumber;
            maxNumber = wantedNumber;
        }

        allEntries = (ArrayList<EntryTranslate>) controller.getEntries();
        makePLayingEntries(numberOfWords);
        percentageScore = 0.0;
        maxPoints = maxNumber;
        pointsReached = 0;
    }

    private void makePLayingEntries(int numberOfWords){
        ArrayList<Integer> randomNumbers = Game.makeRandomNumbersInRange(numberOfWords, maxNumber);
        playingEntries = new ArrayList<>();

        for(Integer i : randomNumbers){
            EntryTranslate toAdd = allEntries.get(i);
            playingEntries.add(toAdd);
        }
    }
    public int getPointsReached(){
        return pointsReached;
    }

    public int getMaxPoints(){
        return maxPoints;
    }


    public boolean checkPoint(boolean inputGerman, boolean inputFrench, String input, String labelTranslation){
        boolean right = control.translationCorrect(inputGerman, inputFrench, labelTranslation, input);
        if(right)
            incrementPoints();
        return right;

    }

    public ArrayList<EntryTranslate> getPlayingEntries(){
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
