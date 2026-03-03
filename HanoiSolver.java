import java.util.ArrayList;
import java.util.List;

public class HanoiSolver {
    private List<Move> moves;
    private int moveIndex;
    
    public HanoiSolver(int numDisks) {
        moves = new ArrayList<>();
        moveIndex = 0;
        generateMoves(numDisks, 'A', 'C', 'B');
    }
    
    private void generateMoves(int n, char fromPeg, char toPeg, char auxPeg) {
        if (n == 1) {
            // Base case: only one disk, move it directly
            moves.add(new Move(fromPeg, toPeg));
            return;
        }
        
        // Move n-1 disks from source to auxiliary using target as temporary
        generateMoves(n - 1, fromPeg, auxPeg, toPeg);
        
        // Move the largest disk from source to target
        moves.add(new Move(fromPeg, toPeg));
        
        // Move n-1 disks from auxiliary to target using source as temporary
        generateMoves(n - 1, auxPeg, toPeg, fromPeg);
    }
    
    public Move getNextMove() {
        if (moveIndex < moves.size()) {
            Move move = moves.get(moveIndex);
            moveIndex++;
            return move;
        }
        return null;
    }
    
    public boolean hasNextMove() {
        return moveIndex < moves.size();
    }
    
    public int getTotalMoves() {
        return moves.size();
    }
    
    public int getCurrentMoveIndex() {
        return moveIndex;
    }
    
    public void reset() {
        moveIndex = 0;
    }
    
    public static class Move {
        private char fromPeg;
        private char toPeg;
        
        public Move(char fromPeg, char toPeg) {
            this.fromPeg = fromPeg;
            this.toPeg = toPeg;
        }
        
        public char getFromPeg() {
            return fromPeg;
        }
        
        public char getToPeg() {
            return toPeg;
        }
        
        public String toString() {
            return "Move disk from " + fromPeg + " to " + toPeg;
        }
    }
}
