import javax.swing.*;
public class GamingEntry {

    private JTextField inputArticle;
    private String article;
    private boolean male,female;
    private JLabel wordLabel ;
    private String word;

    public GamingEntry(String word, String article){
        this.word = word;
        inputArticle = new JTextField();
        this.wordLabel  = new JLabel(word);
        this.article = article.trim().toLowerCase();
        male = false;
        female = false;
        setGender();
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

    private void setGender(){
        switch(article){
            case "le": male = true; break;
            case "la": female = true; break;
        }
    }

}
