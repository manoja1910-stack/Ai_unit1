// TowerDFS.java
public class TowerDFS {
    public static void main(String[] args) {
        int n = 3; // number of disks
        char from = 'A', aux = 'B', to = 'C';
        System.out.println("DFS (recursive) solution for " + n + " disks:");
        solve(n, from, to, aux);
    }

    // recursive solution: move n disks from 'from' to 'to' using 'aux'
    static void solve(int n, char from, char to, char aux) {
        if (n == 0) return;
        solve(n - 1, from, aux, to);
        System.out.println("Move disk " + n + " from peg " + from + " to peg " + to);
        solve(n - 1, aux, to, from);
    }
}
