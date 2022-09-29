import java.io.File;
import java.io.FileNotFoundException;
import java.util.Objects;
import java.util.Scanner;
import java.util.Set;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        try {
            NFA nfa = new NFA("test.txt"); // зчитування автомата з файла

            System.out.format("Введіть w0: ");
            String w0 = scanner.next(); // зчитуємо слово з консолі
            w0 = new StringBuilder(w0).reverse().toString(); // перевертаємо введене слово

            NFA infa = nfa.inverse(); // перевертаємо автомат

            Set<Integer> beforeW0 = infa.processWordFromStates(w0, infa.finalStates); // 1,2
            Set<Integer> beforeW0ReachableFrom = infa.getReachableFromStates(beforeW0); // 0
            if (beforeW0ReachableFrom.contains(infa.startState)) {
                System.out.println("Існує таке слово w1 що w1w0 приймається автоматом.");
            } else {
                System.out.println("Не існує такого слова w1 що w1w0 приймається автоматом.");
            }
        } catch (FileNotFoundException ex) {
            System.out.println("Неправильний шлях до файлу.");
        }
    }

    static class CompleteStepNotPossibleException extends Exception {
        CompleteStepNotPossibleException(String message) {
            super(message);
        }
    }

    static Scanner getScanner(String pathname) throws FileNotFoundException {
        File file = new File(pathname);

        if (!file.exists()) {
            System.out.format("File '%s' does not exist.%n", pathname);
        }

        if (!file.canRead()) {
            System.out.format("Cannot read file '%s'.%n", pathname);
        }

        return new Scanner(file);
    }
}
