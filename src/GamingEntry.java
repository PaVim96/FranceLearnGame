import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

public class GamingEntry {

    private JTextField inputArticle;
    private String article;
    private JLabel wordLabel ;
    private String word;
    private String fieldArticle;
    private JLabel marker;

    public GamingEntry(String word, String article){
        fieldArticle = "";
        this.word = word;
        inputArticle = new JTextField();
        setFocusListener(inputArticle);
        this.wordLabel  = new JLabel(word);
        this.article = article.trim().toLowerCase();
        marker = new JLabel("");
        marker.setVisible(false);
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
                fieldArticle = inputArticle.getText();
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
    public String getWord(){
        return word;
    }
    public JTextField getInputArticle(){
        return inputArticle;
    }

    public JLabel getWordLabel(){
        return wordLabel;
    }

    public String getArticle(){
        return article;
    }

}
