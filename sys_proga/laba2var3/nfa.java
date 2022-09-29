import java.io.FileNotFoundException;
import java.util.*;

class NFA {
    Set<Character> alphabet;
    Set<Integer> states;
    Integer startState;
    Set<Integer> finalStates;
    Map<Integer, Map<Character, Set<Integer>>> transitionFunction;

    private NFA(Scanner fileScanner) {
        String preAlphabet = "abcdefghijklmnopqrstuvwxyz";
        int alphabetSize = fileScanner.nextInt(); // <= 26
        alphabet = new HashSet<>();
        for (int i = 0; i < alphabetSize; ++i) {
            alphabet.add(preAlphabet.charAt(i));
        }

        int numberOfStates = fileScanner.nextInt();
        states = new HashSet<>(numberOfStates);
        for (int i = 0; i < numberOfStates; ++i) {
            states.add(i);
        }

        startState = fileScanner.nextInt();

        int numberOfFinalStates = fileScanner.nextInt();
        finalStates = new HashSet<>(numberOfFinalStates);
        for (int i = 0; i < numberOfFinalStates; ++i) {
            finalStates.add(fileScanner.nextInt());
        }

        transitionFunction = new HashMap<>(numberOfStates);
        for (Integer state : states) {
            transitionFunction.put(state, new HashMap<>());
        }

        while (fileScanner.hasNext()) {
            Integer fromState = fileScanner.nextInt();
            Character viaLetter = fileScanner.next().charAt(0);
            Integer toState = fileScanner.nextInt();
            if (!transitionFunction.get(fromState).keySet().contains(viaLetter)) {
                transitionFunction.get(fromState).put(viaLetter, new HashSet<>());
            }
            transitionFunction.get(fromState).get(viaLetter).add(toState);
        }
    }

    NFA(String pathname) throws FileNotFoundException {
        this(Main.getScanner(pathname));
    }

    Set<Integer> processWord(String word) { // перший етап - передаємо далі початковий стан
        return processWordFromState(word, startState);
    }

    private Set<Integer> processWordFromState(String word, Integer fromState) {
        Set<Integer> fromStates = new HashSet<>(); // другий етап - створюємо сет станів, додаємо початковий і передаємо
                                                   // далі
        fromStates.add(fromState);
        return processWordFromStates(word, fromStates);
    }

    Set<Integer> processWordFromStates(String word, Set<Integer> fromStates) { // третій етап
        Set<Integer> states_ = new HashSet<>(fromStates);
        for (Character viaLetter : word.toCharArray()) { // розбираємо слово по літерах
            Set<Integer> nextStates = new HashSet<>();
            for (Integer fromState_ : states_) { // розбираємо стани
                if (transitionFunction.get(fromState_).containsKey(viaLetter)) {
                    // якщо є потрібний перехід - додаємо в стани наступний стан
                    nextStates.addAll(transitionFunction.get(fromState_).get(viaLetter));
                }
            }
            states_ = new HashSet<>(nextStates);
        }
        return states_;
    }
}
