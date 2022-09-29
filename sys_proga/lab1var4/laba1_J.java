import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.*;

//4. Знайти лише ті слова, кожне з яких складається з літер що не повторюються.

public class laba1_J {
    public static void main(String[] args) {
        ArrayList<String> words = new ArrayList<>();
        Scanner inputFile = null;
        String nextLine;
        try {
            //зчитування
            inputFile = new Scanner(Paths.get("testFile.txt"));
            while (inputFile.hasNext()) {
                nextLine = inputFile.nextLine();
                if (nextLine.isEmpty()) {
                    nextLine = " ";
                }
                List<String> wordsInLine = Arrays.asList(nextLine.split("[\\s(),.!?:;@|-]+"));
                
                //аналіз слова
                for (String word : wordsInLine) {
                    word = word.toLowerCase();
                    if (word.length() > 30) {
                        word = word.substring(0, 30);
                    }
                    boolean unique = true;
                    if (words.contains(word) || word.isEmpty()) {
                        unique = false;
                    }
                    for (int i = 0; i < word.length(); i++) {
                        for (int j = i + 1; j < word.length(); j++) {
                            if (word.charAt(i) == word.charAt(j)) {
                                unique = false;
                                break;
                            }

                        }
                    }
                    if (unique) {
                        words.add(word);
                    }
                }
            }   

            //вивід
            //BufferedWriter writer = new BufferedWriter(new FileWriter("output2.txt"));
            for (String word : words) {
                //writer.write(word+'\n');
                System.out.println(word);
            }
            //writer.close();
        } catch (IOException | NoSuchElementException | IllegalStateException e) {
            System.out.println("Something wrong\n");
        }
    }
}
