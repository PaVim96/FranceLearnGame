import javax.swing.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

public class GamingEntry {

    private JTextField inputWord;
    private String input;
    private JLabel wordLabel ;
    private String fieldArticle;
    private JLabel marker;
    private String shouldBe;

    public GamingEntry(String labelWord, String input){
        fieldArticle = "";
        inputWord = new JTextField();
        shouldBe = labelWord;
        setFocusListener(inputWord);
        this.wordLabel  = new JLabel(labelWord);
        this.input = input.trim().toLowerCase();
        marker = new JLabel("");
        marker.setVisible(false);
    }

    public String getShouldBe(){
        return shouldBe;
    }
    private void setFocusListener(JTextField field){
        field.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                {

                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                fieldArticle = inputWord.getText();
            }
        });
    }
    public void setMarker(boolean good ){
        int asciiValue = 0;
        if (good)
            asciiValue = 10003;
        else
            asciiValue = 10060;
        String value = Character.toString((char) asciiValue);
        marker.setText(value);
    }

    public JLabel getMarker(){
        return marker;
    }


    public void makeVisible(){
        marker.setVisible(true);
    }

    public String getFieldArticle(){
        return fieldArticle;
    }
    public JTextField getInputWord(){
        return inputWord;
    }

    public JLabel getWordLabel(){
        return wordLabel;
    }

    public String getInput(){
        return input;
    }

}
