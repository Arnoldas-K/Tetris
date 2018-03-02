import javax.swing.*;
import java.awt.*;

/**
 * Created by Arnoldas on 05/12/2016.
 */
public class StatsPanel extends JComponent {
    // creating fonts for g.drawString method
    private static final Font SFont = new Font("Tahoma", Font.BOLD, 11);
    private static final Font LFont = new Font("Impact", Font.BOLD, 30);

    // ---------------------------------------
    public StatsPanel() {
        setPreferredSize(new Dimension(Tetris.gamePanel.w * Tetris.gamePanel.size + 50, Tetris.gamePanel.h * Tetris.gamePanel.size));
        setVisible(true);
    }

    @Override
    public void paintComponent(Graphics g) {
        // filling background
        g.setColor(Color.black);
        g.fill3DRect(0, 0, Tetris.gamePanel.w * Tetris.gamePanel.size + 50, Tetris.gamePanel.h * Tetris.gamePanel.size, true);
        g.setColor(Color.white);
        g.fillRect(0, 0, 5, Tetris.gamePanel.h * Tetris.gamePanel.size);
        // -------------------

        // text
        g.setColor(Color.white);
        g.setFont(LFont);
        if (Tetris.frame.newGame) { // if a new game
            g.drawString("TETRIS", 75, 50);
            g.setFont(SFont);
            g.drawString("Press ENTER to start a new game", 30, 130);
        } else if (!Tetris.gamePanel.gameOver) { // while the game is going
            g.drawString("TETRIS", 75, 50);
            g.setFont(SFont);
            g.drawString("Level : " + Frame.level, 30, 100);
            g.drawString("Score: " + Tetris.gamePanel.score, 30, 130);
            String figurename = Tetris.frame.currentFigure.toString();
            g.drawString("Next figure: " + figurename.substring(figurename.length()-1), 30, 150);
        } else if (!Tetris.frame.game_going && Tetris.gamePanel.gameOver) { // game over
            g.drawString("GAME OVER", 60, 50);
            g.setFont(SFont);
            g.drawString("Your level was : " + Frame.level, 30, 100);
            g.drawString("Your score was: " + Tetris.gamePanel.score, 30, 130);
            g.drawString("Press ENTER to start a new game", 30, 160);
        }
        g.fill3DRect(0, 180, Tetris.gamePanel.w * Tetris.gamePanel.size + 50, 5, true);
        // controls
        g.setFont(SFont);
        g.setColor(Color.green);
        g.drawString("Controls:", 90, 210);
        g.drawString("Left arrow/mouse button - move to left", 10, 240);
        g.drawString("Up arrow/middle mouse button - rotate", 10, 260);
        g.drawString("Right arrow/mouse button - move to right", 10, 280);
        g.drawString("Down arrow button - speed up", 10, 300);
        g.drawString("Enter - start a new game", 10, 320);
        g.drawString("Space - Pause", 10, 340);
    }
}
