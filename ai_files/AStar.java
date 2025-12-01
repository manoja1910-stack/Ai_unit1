import java.util.*;

public class AStar {

    static class State implements Comparable<State> {
        List<List<Integer>> pegs;
        List<String> path;
        int cost;
        int heuristic;

        State(List<List<Integer>> pegs, List<String> path, int cost) {
            this.pegs = pegs;
            this.path = path;
            this.cost = cost;
            this.heuristic = heuristicValue();
        }

        boolean isGoal() {
            return pegs.get(2).size() == 3 &&
                   pegs.get(2).get(0) == 3 &&
                   pegs.get(2).get(1) == 2 &&
                   pegs.get(2).get(2) == 1;
        }

        int heuristicValue() {
            return 3 - pegs.get(2).size();  // smaller = better
        }

        int f() { return cost + heuristic; }

        State copy() {
            List<List<Integer>> newPegs = new ArrayList<>();
            for (List<Integer> peg : pegs) {
                newPegs.add(new ArrayList<>(peg));
            }
            return new State(newPegs, new ArrayList<>(path), cost);
        }

        @Override
        public int compareTo(State other) {
            return Integer.compare(this.f(), other.f());
        }

        @Override
        public String toString() {
            return pegs.toString();
        }
    }


    public static void main(String[] args) {

        List<List<Integer>> start = new ArrayList<>();
        start.add(new ArrayList<>(Arrays.asList(3, 2, 1)));
        start.add(new ArrayList<>());
        start.add(new ArrayList<>());

        PriorityQueue<State> pq = new PriorityQueue<>();
        Set<String> visited = new HashSet<>();

        pq.add(new State(start, new ArrayList<>(), 0));

        System.out.println("A* Search Solution:");

        while (!pq.isEmpty()) {
            State current = pq.poll();

            if (current.isGoal()) {
                for (String move : current.path)
                    System.out.println(move);
                System.out.println(current.pegs);
                return;
            }

            visited.add(current.toString());

            for (int from = 0; from < 3; from++) {
                if (current.pegs.get(from).isEmpty()) continue;

                int disk = current.pegs.get(from).get(current.pegs.get(from).size() - 1);

                for (int to = 0; to < 3; to++) {
                    if (from == to) continue;

                    if (!current.pegs.get(to).isEmpty() &&
                        current.pegs.get(to).get(current.pegs.get(to).size() - 1) < disk)
                        continue;

                    State newState = current.copy();
                    newState.pegs.get(from).remove(newState.pegs.get(from).size() - 1);
                    newState.pegs.get(to).add(disk);
                    newState.path.add("Move disk " + disk + " from peg " + from + " to " + to);
                    newState.cost += 1;

                    if (!visited.contains(newState.toString())) {
                        pq.add(newState);
                    }
                }
            }
        }
    }
}
