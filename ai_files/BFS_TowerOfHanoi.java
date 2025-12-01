import java.util.*;

public class BFS_TowerOfHanoi {

    static class State {
        List<List<Integer>> pegs;
        List<String> moves;

        State(List<List<Integer>> pegs, List<String> moves) {
            this.pegs = pegs;
            this.moves = moves;
        }

        // Deep copy the state
        State copy() {
            List<List<Integer>> newPegs = new ArrayList<>();
            for (List<Integer> peg : pegs) {
                newPegs.add(new ArrayList<>(peg));
            }
            return new State(newPegs, new ArrayList<>(moves));
        }

        boolean isGoal() {
            return pegs.get(2).size() == 3 &&
                    pegs.get(2).get(0) == 3 &&
                    pegs.get(2).get(1) == 2 &&
                    pegs.get(2).get(2) == 1;
        }

        @Override
        public String toString() {
            return pegs.toString();
        }
    }

    public static void main(String[] args) {

        // Initial state
        List<List<Integer>> start = new ArrayList<>();
        start.add(new ArrayList<>(Arrays.asList(3, 2, 1))); // Peg 0
        start.add(new ArrayList<>());                      // Peg 1
        start.add(new ArrayList<>());                      // Peg 2

        Queue<State> queue = new LinkedList<>();
        Set<String> visited = new HashSet<>();

        queue.add(new State(start, new ArrayList<>()));

        System.out.println("BFS Solution for Tower of Hanoi:");
        System.out.println("--------------------------------");

        while (!queue.isEmpty()) {

            State current = queue.poll();

            // Goal reached
            if (current.isGoal()) {
                System.out.println("Sequence of Moves:");
                for (String m : current.moves) {
                    System.out.println(m);
                }
                System.out.println("Final Peg State: " + current.pegs);
                return;
            }

            visited.add(current.toString());

            // Try all moves from each peg to each peg
            for (int from = 0; from < 3; from++) {
                if (current.pegs.get(from).isEmpty()) continue;

                int disk = current.pegs.get(from)
                        .get(current.pegs.get(from).size() - 1);

                for (int to = 0; to < 3; to++) {
                    if (from == to) continue;

                    // Check valid move
                    if (!current.pegs.get(to).isEmpty() &&
                            current.pegs.get(to).get(current.pegs.get(to).size() - 1) < disk)
                        continue;

                    // Create new state
                    State newState = current.copy();
                    newState.pegs.get(from).remove(newState.pegs.get(from).size() - 1);
                    newState.pegs.get(to).add(disk);

                    newState.moves.add("Move disk " + disk + " from peg " + from + " to peg " + to);

                    if (!visited.contains(newState.toString())) {
                        queue.add(newState);
                    }
                }
            }
        }
    }
}
