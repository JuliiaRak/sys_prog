import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.*;

//5. Знайти лише ті слова, які мають здвоєні приголосні літери.

public class laba1_O {
    public static void main(String[] args) {
        ArrayList<String> words = new ArrayList<>();
        Scanner inputFile = null;
        String nextLine;
        try {
            // зчитування
            inputFile = new Scanner(Paths.get("testFile2.txt"));
            while (inputFile.hasNext()) {
                nextLine = inputFile.nextLine();
                if (nextLine.isEmpty()) {
                    nextLine = " ";
                }
                List<String> wordsInLine = Arrays.asList(nextLine.split("[\\s(),.!?:;@|-]+"));

                // перевірка слова
                for (String word : wordsInLine) {
                    word = word.toLowerCase();
                    if (word.length() > 30) {
                        word = word.substring(0, 30);
                    }
                    boolean twoConsonantal = false;
                    if (words.contains(word) || word.isEmpty()) {
                        twoConsonantal = false;
                    }
                    for (int i = 0; i < (word.length() - 1); i++) {
                        if (isConsonantal(word.charAt(i))) {
                            if (word.charAt(i) == word.charAt(i + 1)) {
                                twoConsonantal = true;
                                break;
                            }
                        }
                    }
                    if (twoConsonantal) {
                        words.add(word);
                    }
                }
            }

            // вивід
            //BufferedWriter writer = new BufferedWriter(new FileWriter("output2.txt"));
            for (String word : words) {
                //writer.write(word + '\n');
                System.out.println(word);
            }
            //writer.close();
        } catch (IOException | NoSuchElementException | IllegalStateException e) {
            System.out.println("Something wrong\n");
        }
    }

    // перевірка на приголосну
    private static boolean isConsonantal(char letter) {
        switch (letter) {
            case 'e':
            case 'u':
            case 'i':
            case 'o':
            case 'a':
                return false;
            default:
                return true;
        }
    }

}
