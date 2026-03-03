public class HanoiGame {
    public int numDisks;
    public char sourcePeg;
    public char targetPeg;
    public char auxiliaryPeg;
    public HanoiSolver solver;

    public HanoiGame(int numDisks) {
        this.numDisks = numDisks;
        this.sourcePeg = 'A';
        this.targetPeg = 'C';
        this.auxiliaryPeg = 'B';
        this.solver = new HanoiSolver(numDisks);
    }

    public void solve() {
        // Print all moves from the solver
        solver.reset();
        while (solver.hasNextMove()) {
            HanoiSolver.Move move = solver.getNextMove();
            System.out.println(move);
        }
    }

    public static void main(String[] args) {
        int numDisks = 5;
        HanoiGame game = new HanoiGame(numDisks);
        game.solve();
    }
}