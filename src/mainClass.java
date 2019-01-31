

public class mainClass {

    public static void main(String [] args) {
        Controller obj = new ControllerArticle("Words.txt");
        Controller obj2 = new ControllerTranslate("Translate.txt");
        GUI test = new GUI(1600,900, obj2);









    }
}
