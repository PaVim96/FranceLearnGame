/**
 * class which represents one word with its name and corresponding article
 */
public class Entry {
    boolean isMale, isFemale;
    String wordName;


    public Entry(String name, String article) throws WordErrorException {
        wordName = name;
        System.out.println(article);

        switch(article){
            case "le": isMale = true; break;
            case "la": isFemale = true; break;
            default: throw new WordErrorException("Word needs to be either le or la");
        }
    }

    public String getWord(){
        return wordName;
    }

    /**
     * method which returns the gender of the entry as an in t
     * @return 0 stands for le aka male and 1 stands for la aka female
     */
    public int getGender(){
        if(isMale)
            return 0;
        else
            return 1;
    }

    /**
     * returns the format I used for writing in a file which consists of all words with their article
     * example  word : le/la
     * @return returns word and format for writing in a file
     */
    public String getEntryforFile(){
        String gender = "";
        if (isMale)
            gender = "le";
        else
            gender = "la";

        return  wordName + " " + ":" + " " + gender + "\r\n";
    }

}
