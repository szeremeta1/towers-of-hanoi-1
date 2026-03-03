import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

import java.awt.event.ActionEvent; 
import java.awt.event.ActionListener;

public class HanoiGUI {
    
    public static final int WINDOW_WIDTH = 800;
    public static final int WINDOW_HEIGHT = 500;
    public static final int DEFAULT_DISKS = 5;
    
    public JFrame frame;
    public DrawingPanel drawingPanel;
    public JLabel statusLabel;
    private Timer solveTimer;
    private JComboBox<Integer> diskSelector;

    public HanoiGame game;
    
    public int[] pegA;
    public int[] pegB;
    public int[] pegC;
    public int moveCount;

    public HanoiGUI() {
        game = new HanoiGame(DEFAULT_DISKS);
        
        pegA = new int[DEFAULT_DISKS];
        pegB = new int[DEFAULT_DISKS];
        pegC = new int[DEFAULT_DISKS];
    }
    
    public void setupGUI() {
        frame = new JFrame("Towers of Hanoi");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        frame.setLayout(new BorderLayout());
        
        drawingPanel = new DrawingPanel();
        frame.add(drawingPanel, BorderLayout.CENTER);
        
        statusLabel = new JLabel("Click a peg to select it, then click another peg to move.", SwingConstants.CENTER);
        statusLabel.setFont(new Font("SansSerif", Font.PLAIN, 14));
        statusLabel.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 0));
        frame.add(statusLabel, BorderLayout.NORTH);
        
        JPanel buttonPanel = createButtonPanel();
        frame.add(buttonPanel, BorderLayout.SOUTH);
        
        initializeGame();
        
        frame.setVisible(true);
    }
    
    public JPanel createButtonPanel() {
        JPanel buttonPanel = new JPanel();
        
        JLabel diskLabel = new JLabel("Disks:");
        diskSelector = new JComboBox<>();
        for (int i = 3; i <= 10; i++) {
            diskSelector.addItem(i);
        }
        diskSelector.setSelectedItem(game.numDisks);
        diskSelector.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                diskCountChanged();
            }
        });
        
        JButton solveButton = new JButton("Solve");
        solveButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                solveButtonClicked();
            }
        });
        
        JButton resetButton = new JButton("Reset");
        resetButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                resetButtonClicked();
            }
        });
        
        JButton stopButton = new JButton("Stop");
        stopButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                stopButtonClicked();
            }
        });
        
        buttonPanel.add(diskLabel);
        buttonPanel.add(diskSelector);
        buttonPanel.add(solveButton);
        buttonPanel.add(stopButton);
        buttonPanel.add(resetButton);
        
        return buttonPanel;
    }
    
    public class DrawingPanel extends JPanel implements MouseListener {
    
           private char clicked = '\0';
           private char selected = '\0';
           
           public DrawingPanel() {
               addMouseListener(this);
           }
           
           public void paintComponent(Graphics g) {
               super.paintComponent(g);
               drawGame(g);
               drawSelectedHighlight(g);
           }
           
           private void drawSelectedHighlight(Graphics g) {
               if (selected == '\0') return;
               int width = getWidth();
               int height = getHeight();
               int baseY = height - 50;
               int pegCenterX;
               if (selected == 'A') pegCenterX = width / 4;
               else if (selected == 'B') pegCenterX = width / 2;
               else pegCenterX = 3 * width / 4;
               
               Graphics2D g2 = (Graphics2D) g;
               g2.setColor(new Color(0, 120, 255, 80));
               g2.fillRect(pegCenterX - width / 8, baseY - height / 2, width / 4, height / 2);
           }
           
           public void mousePressed(MouseEvent e) { 
              int clickX = e.getX(); 
              int width = drawingPanel.getWidth(); 
              
              // Divide panel into three equal zones for click detection
              int zoneWidth = width / 3;
              
              if (clickX < zoneWidth) {
                  clicked = 'A';
              } else if (clickX < 2 * zoneWidth) {
                  clicked = 'B';
              } else {
                  clicked = 'C';
              }
              
              if (clicked != '\0') {
                  if (selected == '\0') {
                     // First click: only allow selecting a peg that has disks
                     if (getTopDisk(getPegArray(clicked)) > 0) {
                        selected = clicked;
                        statusLabel.setText("Selected peg " + clicked + ". Click destination peg (or same peg to cancel).");
                     } else {
                        statusLabel.setText("Peg " + clicked + " is empty. Select a peg with disks.");
                     }
                  } else if (selected == clicked) {
                     // Clicked same peg again: deselect
                     selected = '\0';
                     statusLabel.setText("Selection cancelled.");
                  } else {
                     // Second click on a different peg: attempt move
                     boolean success = moveDisk(selected, clicked);
                     if (success) {
                        statusLabel.setText("Moved disk from peg " + selected + " to peg " + clicked + ". (Move #" + moveCount + ")");
                        checkWin();
                     }
                     selected = '\0';
                  }
              }
               
              clicked = '\0';
              repaint();
           }
           
           public void mouseClicked(MouseEvent e) { 
           
           } 
           
           public void mouseEntered(MouseEvent e) { 
           
           } 
           
           public void mouseReleased(MouseEvent e) {
           
           }
           
           public void mouseExited(MouseEvent e) { 
           
           }
       }
    
    public void initializeGame() {
        for (int i = 0; i < game.numDisks; i++) {
            pegA[i] = 0;
            pegB[i] = 0;
            pegC[i] = 0;
        }
        
        for (int i = 0; i < game.numDisks; i++) {
            pegA[i] = game.numDisks - i;
        }
        
        moveCount = 0;
        game.solver.reset();
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
        g.setColor(new Color(87, 45, 8)); // Brown color
        int baseHeight = 20;
        int baseY = height - 50;
        g.fillRect(50, baseY, width - 100, baseHeight);
    }
    
    public void drawPegs(Graphics g, int width, int height) {
        // Draw three pegs evenly spaced across the panel
        g.setColor(new Color(87, 45, 8)); // Brown color for pegs
        
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

    public void solveButtonClicked() {
        // Stop any existing solve animation
        stopSolveTimer();
        
        // Reset the game first, then animate the solution
        initializeGame();
        drawingPanel.repaint();
        if (statusLabel != null) statusLabel.setText("Solving...");
        
        game.solver.reset();
        
        // Use a Swing Timer so the GUI doesn't freeze during animation
        solveTimer = new Timer(400, null);
        solveTimer.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                if (game.solver.hasNextMove()) {
                    HanoiSolver.Move move = game.solver.getNextMove();
                    moveDisk(move.getFromPeg(), move.getToPeg());
                    statusLabel.setText("Solving... move " + game.solver.getCurrentMoveIndex() + " of " + game.solver.getTotalMoves());
                } else {
                    stopSolveTimer();
                    statusLabel.setText("Solved in " + game.solver.getTotalMoves() + " moves!");
                }
            }
        });
        solveTimer.start();
    }
    
    public void stopSolveTimer() {
        if (solveTimer != null && solveTimer.isRunning()) {
            solveTimer.stop();
        }
    }
    
    public void stopButtonClicked() {
        stopSolveTimer();
        if (statusLabel != null) statusLabel.setText("Auto-solve stopped. Move #" + moveCount);
    }
    
    public void resetButtonClicked() {
        stopSolveTimer();
        initializeGame();
        drawingPanel.selected = '\0';
        drawingPanel.repaint();
        if (statusLabel != null) statusLabel.setText("Game reset. Click a peg to select it.");
    }
    
    public void diskCountChanged() {
        stopSolveTimer();
        int count = (Integer) diskSelector.getSelectedItem();
        game = new HanoiGame(count);
        pegA = new int[count];
        pegB = new int[count];
        pegC = new int[count];
        initializeGame();
        drawingPanel.selected = '\0';
        drawingPanel.repaint();
        if (statusLabel != null) statusLabel.setText("Game reset with " + count + " disks. Click a peg to select it.");
    }
    


    public int getTopDisk(int[] peg) {
        for (int i = peg.length - 1; i >= 0; i--) {
            if (peg[i] > 0) return peg[i];
        }
        return 0;
    }
    
    private int getTopIndex(int[] peg) {
        for (int i = peg.length - 1; i >= 0; i--) {
            if (peg[i] > 0) return i;
        }
        return -1;
    }
    
    public boolean moveDisk(char fromPeg, char toPeg) {
        int[] fromArray = getPegArray(fromPeg);
        int[] toArray = getPegArray(toPeg);
        
        // Find the top disk on the source peg
        int fromIndex = getTopIndex(fromArray);
        if (fromIndex == -1) {
            statusLabel.setText("Illegal: Peg " + fromPeg + " is empty!");
            return false;
        }
        int diskToMove = fromArray[fromIndex];
        
        // Check if the target peg's top disk is smaller (illegal move)
        int topOfTarget = getTopDisk(toArray);
        if (topOfTarget > 0 && diskToMove > topOfTarget) {
            statusLabel.setText("Illegal: Can't place disk " + diskToMove + " on smaller disk " + topOfTarget + "!");
            return false;
        }
        
        // Find the first empty slot on the target peg
        int toIndex = -1;
        for (int i = 0; i < toArray.length; i++) {
            if (toArray[i] == 0) {
                toIndex = i;
                break;
            }
        }
        if (toIndex == -1) {
            statusLabel.setText("Illegal: No space on peg " + toPeg + "!");
            return false;
        }
        
        fromArray[fromIndex] = 0;
        toArray[toIndex] = diskToMove;
        moveCount++;
        
        drawingPanel.repaint();
        return true;
    }
    
    public void checkWin() {
        for (int i = 0; i < game.numDisks; i++) {
            if (pegC[i] != game.numDisks - i) return;
        }
        statusLabel.setText("Congratulations! Puzzle solved in " + moveCount + " moves!");
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
