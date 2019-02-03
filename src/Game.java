import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Random;

public interface Game {

    void makePLayingEntries(int numberOfWords);

    int getPointsReached();

    int getMaxPoints();

    void incrementPoints();

    default ArrayList<Integer> makeRandomNumbersInRange(int numberOfWords, int maxNumber, ArrayList<? extends Entry> entries){
        ArrayList<Integer> randomNumbers = new ArrayList<>();
        Random generator = new Random();

        for(int i = 0; i < numberOfWords; i++){

            int number = generator.nextInt(maxNumber);
            if (randomNumbers.size() == 0 )
                randomNumbers.add(number);
            else {
                while (randomNumbers.contains(number)) {
                    number = generator.nextInt(maxNumber);
                }
                randomNumbers.add(number);
            }
        }
        return randomNumbers;
    }

    /**
     * Method which checks whether user gets a point for a valid entry or not
     * @param input the input of the user
     * @param shouldBe what the input should be
     * @param userInputGerman if true user input is german so we have to look for french word (is only user in translate game)
     * @return returns true if input is correct
     */
    boolean checkPoint(String input, String shouldBe, boolean userInputGerman);

    ArrayList<? extends Entry> getPlayingEntries();


    double getPercentage();

    void addResultButton(Controller control, Container container, ArrayList<GamingEntry> playingEntries, Game game, boolean userInputGerman);

    default void showResults(Container panel, Game game, ArrayList<GamingEntry> gameEntries, ArrayList<Boolean> results){
        int i = 0;
        for(boolean t: results){
            gameEntries.get(i).setMarker(t);
            gameEntries.get(i).makeVisible();
            if(!t){
                String rightText = gameEntries.get(i).getInput();
                gameEntries.get(i).getInputWord().setText(rightText);
                gameEntries.get(i).getInputWord().setForeground(Color.RED);
            }
            i++;
        }
        String score = "You scored " + Integer.toString(game.getPointsReached()) + " out of " + Integer.toString(game.getMaxPoints()) + " points!";
        JLabel scoreText = new JLabel(score);
        JLabel percentage = new JLabel(Double.toString(game.getPercentage() * 100 ) + "%" );
        Object[] message = {scoreText, percentage};
        JOptionPane.showMessageDialog(panel, message);
        //replace showResultButton with EndApplication button
    }

    default void addEndButton(Controller control, Container panel){
        //remove showResult button, we know its the last item
        panel.remove(panel.getComponents().length -1);
        Component[] comp = panel.getComponents();
        for(Component x : comp)
            System.out.println(x);
        panel.revalidate();
        panel.repaint();
        JButton endButton = new JButton("End game");
        JFrame window = control.getJFrame(panel);

        endButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                window.dispose();
            }
        });
        panel.add(endButton);
    }
}
