public class EntryTranslate implements Entry{
    private String german, french;


    public EntryTranslate(String german, String french){
        this.german = german;
        this.french = french;
    }


    public String getGerman(){
        return german;
    }

    public String getFrench(){
        return french;
    }

    @Override
    public String getEntryforFile() {
        return german + " " + ":" + " " + french + "\r\n";
    }
}
