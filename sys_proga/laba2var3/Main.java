import java.io.File;
import java.io.FileNotFoundException;
import java.util.Objects;
import java.util.Scanner;
import java.util.Set;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        try {
            NFA nfa = new NFA("test.txt");   //зчитування автомата з файла

            System.out.format("Введіть слово для обробки: ");   //зчитуємо слово з консолі
            String word = scanner.next();

            Set<Integer> states = nfa.processWord(word);    //обробка слова
            if (states.isEmpty())
                System.out.println("Слово не може бути оброблено автоматом");
            else {
                System.out.print("Слово успішно оброблено автоматом! Фінальні стани - ");
                for (var item : states)
                    System.out.print(item + " ");
                System.out.println("");
            }
        } catch (FileNotFoundException ex) {
            System.out.println("Неправильний шлях");
        }
    }

    static Scanner getScanner(String pathname) throws FileNotFoundException {
        File file = new File(pathname);

        if (!file.exists()) {
            System.out.format("Файлу '%s' не існує.%n", pathname);
        }

        if (!file.canRead()) {
            System.out.format("Неможливо прочитати файл '%s'.%n", pathname);
        }

        return new Scanner(file);
    }
}
