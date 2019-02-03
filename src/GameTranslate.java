import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class GameTranslate implements Game {
    private int numberOfWords;
    private ArrayList<EntryTranslate> playingEntries;
    private int maxNumber;
    private ArrayList<EntryTranslate> allEntries;
    private double percentageScore;
    private int maxPoints;
    private int pointsReached;

    @SuppressWarnings("unchecked")
    public GameTranslate(Controller controller, int wantedNumber){
        maxNumber = controller.getEntries().size();
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


    @Override
    public void makePLayingEntries(int numberOfWords) {
        ArrayList<Integer> randomNumbers = Game.super.makeRandomNumbersInRange(numberOfWords, allEntries.size());
        playingEntries = new ArrayList<>();

        for(Integer i : randomNumbers){
            EntryTranslate toAdd = allEntries.get(i);
            playingEntries.add(toAdd);
        }
    }

    @Override
    public int getPointsReached() {
        return pointsReached;
    }

    @Override
    public int getMaxPoints() {
        return maxPoints;
    }

    @Override
    /**
     * method which checks whether user scored a point or no
     * @param userInputGerman if this is true, user input is german
     */
    public boolean checkPoint(String input, String labelTranslation, boolean userInputGerman) {
        String correspondingTranslation = searchTranslation(input, playingEntries, userInputGerman);
        System.out.printf("my found translation is %s, and it should be %s", correspondingTranslation, labelTranslation);
        if (labelTranslation.equals(correspondingTranslation)) {
            incrementPoints();
            return true;
        }
        else
            return false;

    }

    private String searchTranslation(String input, ArrayList<EntryTranslate> listToSearch, boolean userInputGerman){
        System.out.printf("user input was %s", input);
        if (userInputGerman){
            for (EntryTranslate x : listToSearch){
                System.out.println(x.getGerman());
                if(x.getGerman().equals(input)) {
                    return x.getFrench();
                }
            }
        }
        else {
            for (EntryTranslate x : listToSearch){
                if(x.getFrench().equals(input)) {
                    return x.getGerman();
                }
            }
        }
        return " ";
    }

    @Override
    public ArrayList<EntryTranslate> getPlayingEntries() {
        return playingEntries;
    }

    @Override
    public double getPercentage() {
        return percentageScore;
    }

    @Override
    public void addResultButton(Controller control, Container container, ArrayList<GamingEntry> playingEntries, Game game, boolean userInputGerman) {
        JButton result = new JButton("Click to see results");
        result.addActionListener(new ActionListener() {
            ArrayList<Boolean> results = new ArrayList<>();

            @Override
            public void actionPerformed(ActionEvent e) {
                for (GamingEntry x : playingEntries) {
                    String input = x.getFieldArticle().trim();
                    String shouldBe = x.getShouldBe();
                    boolean isGood = game.checkPoint(input, shouldBe, userInputGerman);
                    results.add(isGood);
                }
                game.showResults(container, game, playingEntries, results);
                game.addEndButton(control, container);
            }
        });
        container.add(result);
    }

    public void incrementPoints(){
        pointsReached++;
        percentageScore = (double) pointsReached / (double) maxPoints;
    }
}
