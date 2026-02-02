import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class HanoiGUI {
    
    public static final int WINDOW_WIDTH = 800;
    public static final int WINDOW_HEIGHT = 500;
    public static final int NUM_DISKS = 5;
    
    public JFrame frame;
    public DrawingPanel drawingPanel;

    public HanoiGame game;
    
    public int[] pegA;
    public int[] pegB;
    public int[] pegC;

    public HanoiGUI() {
        game = new HanoiGame(NUM_DISKS);
        
        // Initialize the peg arrays
        pegA = new int[NUM_DISKS];
        pegB = new int[NUM_DISKS];
        pegC = new int[NUM_DISKS];
    }
    
    public void setupGUI() {
        frame = new JFrame("Towers of Hanoi");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        frame.setLayout(new BorderLayout());
        
        drawingPanel = new DrawingPanel();
        frame.add(drawingPanel, BorderLayout.CENTER);
        
        JPanel buttonPanel = createButtonPanel();
        frame.add(buttonPanel, BorderLayout.SOUTH);
        
        initializeGame();
        
        frame.setVisible(true);
    }
    
    public JPanel createButtonPanel() {
        JPanel buttonPanel = new JPanel();
        
        // Create the Solve button
        JButton solveButton = new JButton("Solve");
        solveButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                solveButtonClicked();
            }
        });
        
        // Create the Reset button
        JButton resetButton = new JButton("Reset");
        resetButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                resetButtonClicked();
            }
        });
        
        // Create the Step button (does one move at a time)
        JButton stepButton = new JButton("Step");
        stepButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                stepButtonClicked();
            }
        });
        
        buttonPanel.add(solveButton);
        buttonPanel.add(resetButton);
        buttonPanel.add(stepButton);
        
        return buttonPanel;
    }
    
    public class DrawingPanel extends JPanel {
        
        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            drawGame(g);
        }
    }
    
    public void initializeGame() {
        // TODO: Initialize the peg arrays
        
        // Reset all pegs to empty (0 means no disk)
        for (int i = 0; i < game.numDisks; i++) {
            pegA[i] = 0;
            pegB[i] = 0;
            pegC[i] = 0;
        }
        
        // TODO: Fill pegA with the disk values
        
    }

    public void drawGame(Graphics g) {
        int width = drawingPanel.getWidth();
        int height = drawingPanel.getHeight();
        g.setColor(Color.LIGHT_GRAY);
        g.fillRect(0, 0, width, height);
        
        drawBase(g, width, height);
        
        drawPegs(g, width, height);
        
        drawDisksOnPeg(g, pegA, 1, width, height); // Peg A is position 1
        drawDisksOnPeg(g, pegB, 2, width, height); // Peg B is position 2
        drawDisksOnPeg(g, pegC, 3, width, height); // Peg C is position 3
    }
    
    public void drawBase(Graphics g, int width, int height) {
        // TODO: Use g.setColor() and g.fillRect() to draw the base
        
    }
    
    public void drawPegs(Graphics g, int width, int height) {
        // TODO: Draw three pegs evenly spaced across the panel
    }

    public void drawDisksOnPeg(Graphics g, int[] peg, int pegPosition, int width, int height) {
        // TODO: Loop through the disks on this peg and draw each one
        
    }
    
    public void drawDisk(Graphics g, int diskSize, int x, int y) {
        // TODO: Calculate the width based on diskSize
        
    }

    public void solveButtonClicked() {
        System.out.println("Solve button clicked - not implemented yet");
        
        // TODO: Use the game object to solve the puzzle
        
    }
    
    public void resetButtonClicked() {
        System.out.println("Reset button clicked");
        initializeGame();
        drawingPanel.repaint();
    }
    
    public void stepButtonClicked() {
        System.out.println("Step button clicked - not implemented yet");
        
        // TODO: Perform one move of the solution
        //       Update the display with drawingPanel.repaint()
        
    }

    public void moveDisk(char fromPeg, char toPeg) {
        // TODO: Find the top disk on fromPeg
        //       Remove it from fromPeg
        //       Add it to toPeg
        //       Redraw with drawingPanel.repaint()
        
    }
    
    public int[] getPegArray(char pegChar) {
        if (pegChar == 'A') return pegA;
        if (pegChar == 'B') return pegB;
        return pegC;
    }
    
    public int getNumDisks() {
        return game.numDisks;
    }
    
    public HanoiGame getGame() {
        return game;
    }
    
    public static void main(String[] args) {
        HanoiGUI gui = new HanoiGUI();
        gui.setupGUI();
    }
}
