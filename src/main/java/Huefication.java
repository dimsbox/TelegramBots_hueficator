
import java.util.Arrays;
import java.util.List;

public class Huefication {

    private final String receivedText;

    private String respond;

    public void run() {
        String valuableRespond = "";
        for (String word : receivedText.split(" ")) {
            valuableRespond = valuableRespond.concat(hueficate(word)).concat(" ");
        }
        respond = valuableRespond.trim();
    }

    private String hueficate(String word) {

        if (word.isEmpty()) {
            return "";
        }

        if (word.equals("/start")) {
            return "huestart";
        }

        //Если перенос строки
        if (word.contains("\n")) {
            if (word.startsWith("\n")) {
                word = word.replace("\n", "");
                return "\n".concat(hueficate(word));
            } else if (word.endsWith("\n")) {
                word = word.replace("\n", "");
                return hueficate(word).concat("\n");
            } else {
                String[] words = word.split("\n", 2);
                return hueficate(words[0]).concat("\n").concat(hueficate(words[1]));
            }
        }

        String text = removePunctuations(word);
        String regex = "[а-яА-ЯЁё]+";
        if (!text.matches(regex)) {
            return word;
        }

        if (text.length() < 3) {
            return text;
        }

        List<Character> gb = Arrays.asList('а', 'о', 'у', 'ы', 'э', 'я', 'е', 'ё', 'ю', 'и');

        String huext = String.valueOf(text.toLowerCase().charAt(0) == text.charAt(0) ? 'x' : 'X') + 'у';

        if (!gb.contains(text.toLowerCase().charAt(0)) && !gb.contains(text.toLowerCase().charAt(1))) {
            huext += getOpposite(text.toLowerCase().charAt(2)) + text.substring(3);
        } else if (!gb.contains(text.toLowerCase().charAt(0))) {
            huext += getOpposite(text.toLowerCase().charAt(1)) + text.substring(2);
        } else {
            huext += getOpposite(text.toLowerCase().charAt(0)) + text.substring(1);
        }
        return huext;
    }

    private char getOpposite(char ch) {
        return switch (ch) {
            case 'у' -> 'ю';
            case 'о' -> 'ё';
            case 'ы' -> 'и';
            case 'а' -> 'я';
            default -> ch;
        };
    }

    public static String removePunctuations(String source) {
        return source.replaceAll("\\p{IsPunctuation}", "");
    }

    public Huefication(String receivedText) {
        this.receivedText = receivedText;
    }

    public String getRespond() {
        return respond;
    }
}
