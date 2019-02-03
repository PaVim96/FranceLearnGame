public class EntryTranslate implements Entry{
    private String german, french;
    private int rank;


    public EntryTranslate(String german, String french){
        this.german = german;
        this.french = french;
        rank = 0;
    }


    public String getGerman(){
        return german;
    }

    public String getFrench(){
        return french;
    }

    public int getRank(){
        return rank;
    }

    public void incrementRank(){
        rank++;
    }

    @Override
    public String getEntryforFile() {
        return german + " " + ":" + " " + french + "\r\n";
    }
}
