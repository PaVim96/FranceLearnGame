public class EntryArticle implements Entry{

    private String wordName;
    private String article;


    public EntryArticle(String name, String article) {
        wordName = name;
        this.article = article;
    }

    public String getWord(){
        return wordName;
    }

    public String getGender() {return article; }


    /**
     * returns the format I used for writing in a file which consists of all words with their article
     * example  word : le/la
     * @return returns word and format for writing in a file
     */
    public String getEntryforFile(){
        return  wordName + " " + ":" + " " + article + "\r\n";
    }
}
