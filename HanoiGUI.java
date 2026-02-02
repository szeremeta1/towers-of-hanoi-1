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
        // Reset all pegs to empty (0 means no disk)
        for (int i = 0; i < game.numDisks; i++) {
            pegA[i] = 0;
            pegB[i] = 0;
            pegC[i] = 0;
        }
        
        // Fill pegA with the disk values (largest disk at bottom, smallest at top)
        // Disk sizes go from NUM_DISKS (largest) at index 0 to 1 (smallest) at top
        for (int i = 0; i < game.numDisks; i++) {
            pegA[i] = game.numDisks - i;
        }
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
        // Draw the base platform
        g.setColor(new Color(139, 69, 19)); // Brown color
        int baseHeight = 20;
        int baseY = height - 50;
        g.fillRect(50, baseY, width - 100, baseHeight);
    }
    
    public void drawPegs(Graphics g, int width, int height) {
        // Draw three pegs evenly spaced across the panel
        g.setColor(new Color(139, 69, 19)); // Brown color for pegs
        
        int pegWidth = 10;
        int pegHeight = height / 2;
        int baseY = height - 50; // Position above the base
        
        // Peg A at 1/4 of width
        int pegAX = width / 4 - pegWidth / 2;
        g.fillRect(pegAX, baseY - pegHeight, pegWidth, pegHeight);
        
        // Peg B at 1/2 of width (center)
        int pegBX = width / 2 - pegWidth / 2;
        g.fillRect(pegBX, baseY - pegHeight, pegWidth, pegHeight);
        
        // Peg C at 3/4 of width
        int pegCX = 3 * width / 4 - pegWidth / 2;
        g.fillRect(pegCX, baseY - pegHeight, pegWidth, pegHeight);
    }

    public void drawDisksOnPeg(Graphics g, int[] peg, int pegPosition, int width, int height) {
        // Calculate the x position of the peg center
        int pegCenterX;
        if (pegPosition == 1) {
            pegCenterX = width / 4;
        } else if (pegPosition == 2) {
            pegCenterX = width / 2;
        } else {
            pegCenterX = 3 * width / 4;
        }
        
        int baseY = height - 50; // Base position
        int diskHeight = 20;
        int maxDiskWidth = width / 5; // Maximum disk width
        
        // Loop through the disks on this peg and draw each one
        for (int i = 0; i < peg.length; i++) {
            int diskSize = peg[i];
            if (diskSize > 0) {
                // Calculate disk width based on size (larger number = wider disk)
                int diskWidth = (diskSize * maxDiskWidth) / game.numDisks;
                
                // Calculate disk position (bottom disk at index 0)
                int diskX = pegCenterX - diskWidth / 2;
                int diskY = baseY - (i + 1) * diskHeight;
                
                // Set color based on disk size (different colors for each disk)
                Color[] colors = {Color.RED, Color.ORANGE, Color.YELLOW, Color.GREEN, Color.BLUE, 
                                  Color.MAGENTA, Color.CYAN, Color.PINK};
                g.setColor(colors[diskSize % colors.length]);
                
                // Draw the disk
                g.fillRect(diskX, diskY, diskWidth, diskHeight - 2);
                
                // Draw border
                g.setColor(Color.BLACK);
                g.drawRect(diskX, diskY, diskWidth, diskHeight - 2);
            }
        }
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
