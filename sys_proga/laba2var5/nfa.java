import java.io.FileNotFoundException;
import java.util.*;

class NFA {
    Set<Character> alphabet;   
    Set<Integer> states;
    Integer startState;
    Set<Integer> finalStates;
    Map<Integer, Map<Character, Set<Integer>>> transitionFunction;

    NFA() {
        alphabet = new HashSet<>();
        states = new HashSet<>();
        startState = 0;
        finalStates = new HashSet<>();
        transitionFunction = new HashMap<>();
    }

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

    NFA inverse() {
        NFA nfa = new NFA();
        nfa.alphabet = new HashSet<>(alphabet);
        nfa.states = new HashSet<>(states);
        nfa.startState = startState;
        nfa.finalStates = new HashSet<>(finalStates);

        nfa.transitionFunction = new HashMap<>();
        for (Integer fromState : states) {
            nfa.transitionFunction.put(fromState, new HashMap<>());
        }
        for (Integer fromState : states) {
            for (Character viaLetter : transitionFunction.get(fromState).keySet()) {
                for (Integer toState : transitionFunction.get(fromState).get(viaLetter)) {
                    if (!nfa.transitionFunction.get(toState).keySet().contains(viaLetter)) {
                        nfa.transitionFunction.get(toState).put(viaLetter, new HashSet<>());
                    }
                    nfa.transitionFunction.get(toState).get(viaLetter).add(fromState);
                }
            }
        }
        return nfa;
    }

    private Map<Integer, Boolean> bfsFromStates(Set<Integer> fromStates) { //створює меп з усіма станами 
                                                                           // і позначає тру тільки ті стани, 
                                                                           //з яких можна прийти в початкові стани для w0
        Map<Integer, Boolean> used = new HashMap<>();
        for (Integer state : states) {
            used.put(state, false);
        }

        Queue<Integer> states = new LinkedList<>(fromStates);
        while (!states.isEmpty()) {
            Integer fromState_ = states.peek();
            used.put(fromState_, true);

            for (Character viaLetter : transitionFunction.get(fromState_).keySet()) {
                for (Integer toState : transitionFunction.get(fromState_).get(viaLetter)) {
                    if (!used.get(toState)) {
                        states.add(toState);
                    }
                }
            }

            states.poll();
        }

        return used;
    }

    Set<Integer> getReachableFromStates(Set<Integer> fromStates) {   //повертає набір станів, з яких можна 
                                                                    //прийти в набір початкових станів для w0          
        Map<Integer, Boolean> used = bfsFromStates(fromStates);
        Set<Integer> visited = new HashSet<>();
        for (Integer state : used.keySet()) {
            if (used.get(state)) {
                visited.add(state);
            }
        }
        return visited;
    }

    Set<Integer> processWordFromStates(String word, Set<Integer> fromStates) { //повертає набір початкових станів для w0  
        Set<Integer> states_ = new HashSet<>(fromStates);
        for (Character viaLetter : word.toCharArray()) { // розбираємо слово по літерах
            Set<Integer> nextStates = new HashSet<>();
            for (Integer fromState_ : states_) {  // розбираємо стани
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
