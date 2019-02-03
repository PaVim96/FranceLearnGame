import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class GameArticle implements Game {
    private int numberOfWords;
    private ArrayList<EntryArticle> playingEntries;
    private int maxNumber;
    private ArrayList<EntryArticle> allEntries;
    private double percentageScore;
    private int maxPoints;
    private int pointsReached;

    @SuppressWarnings("unchecked")
    public GameArticle(Controller controller, int wantedNumber) {
        maxNumber = controller.getEntries().size();
        if (wantedNumber > maxNumber)
            numberOfWords = maxNumber;
        else {
            numberOfWords = wantedNumber;
            maxNumber = wantedNumber;
        }

        allEntries = (ArrayList<EntryArticle>) controller.getEntries();
        makePLayingEntries(numberOfWords);
        percentageScore = 0.0;
        maxPoints = maxNumber;
        pointsReached = 0;
    }

    public void makePLayingEntries(int numberOfWords) {
        ArrayList<Integer> randomNumbers = Game.super.makeRandomNumbersInRange(numberOfWords, allEntries.size());
        playingEntries = new ArrayList<>();

        for (Integer i : randomNumbers) {
            EntryArticle toAdd = allEntries.get(i);
            playingEntries.add(toAdd);
        }
    }

    public int getPointsReached() {
        return pointsReached;
    }

    public int getMaxPoints() {
        return maxPoints;
    }


    public boolean checkPoint(String input, String shouldBe, boolean __) {

        if (input.equals(shouldBe)) {
            incrementPoints();
            return true;
        }
        return false;
    }

    public ArrayList<EntryArticle> getPlayingEntries() {
        return playingEntries;
    }

    /*public int getGameWordAmount(){
        return numberOfWords;
    }*/

    public void incrementPoints() {
        pointsReached++;
        percentageScore = (double) pointsReached / (double) maxPoints;
    }

    public double getPercentage() {
        return percentageScore;
    }

    @Override
    public void addResultButton(Controller control, Container container, ArrayList<GamingEntry> playingEntries, Game game, boolean __) {
        JButton result = new JButton("Click to see results");
        result.addActionListener(new ActionListener() {
            ArrayList<Boolean> results = new ArrayList<>();

            @Override
            public void actionPerformed(ActionEvent e) {
                for (GamingEntry x : playingEntries) {
                    String input = x.getFieldArticle().trim().toLowerCase();
                    String shouldBe = x.getInput();
                    boolean isGood = game.checkPoint(input, shouldBe, false);
                    results.add(isGood);
                }
                game.showResults(container, game, playingEntries, results);
                game.addEndButton(control, container);
            }
        });
        container.add(result);
    }



}
