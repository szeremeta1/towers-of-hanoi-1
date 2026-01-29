public class HanoiGame {
    public int numDisks;
    public char sourcePeg;
    public char targetPeg;
    public char auxiliaryPeg;

    public HanoiGame(int numDisks) {
        this.numDisks = numDisks;
        this.sourcePeg = 'A';
        this.targetPeg = 'C';
        this.auxiliaryPeg = 'B';
    }

    public void solve() {
        moveDisks(numDisks, sourcePeg, targetPeg, auxiliaryPeg);
    }

    public void moveDisks(int n, char fromPeg, char toPeg, char auxPeg) {
        if (n == 1) {
            System.out.println("Move disk 1 from peg " + fromPeg + " to peg " + toPeg);
            return;
        }
        moveDisks(n - 1, fromPeg, auxPeg, toPeg);
        System.out.println("Move disk " + n + " from peg " + fromPeg + " to peg " + toPeg);
        moveDisks(n - 1, auxPeg, toPeg, fromPeg);
    }

    public static void main(String[] args) {
        int numDisks = 5;
        HanoiGame game = new HanoiGame(numDisks);
        game.solve();
    }
}