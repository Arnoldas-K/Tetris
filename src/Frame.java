/**
 * Created by Arnoldas on 30/11/2016.
 */

import javax.swing.*;
import java.awt.*;
import java.util.Random;
import java.util.TimerTask;
import java.util.Timer;

public class Frame extends JFrame {
    public static boolean game_going = false; // for game state
    public static boolean newBlock = false; // if new block true, we need to spawn a new figure
    public static int time_interval; // for speed
    public static int level; // game level
    public Component[] comp = new Component[2]; // an array of Component for game panel and stats panel
    public Figure currentFigure; // for current figure
    Random r = new Random(); // random number generator
    private static int FIGURE_COUNT = Figure.values().length; // getting how many figures is available
    public boolean newGame = true; // if a new game
    public Timer timer = new Timer(); // creating a timer

    public Frame(Component comp, Component scomp, String title) { // constructor
        super(title);
        this.comp[0] = comp; // game panel
        this.comp[1] = scomp; // stats panel
        // adding both components to the frame
        getContentPane().add(BorderLayout.CENTER, comp);
        getContentPane().add(BorderLayout.EAST, scomp);
        pack();
        this.setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
    }

    public void startTimer() { // move currently active block at fixed intervals:
        timer = new Timer();
        TimerTask task = new TimerTask() {
            public void run() {
                if (game_going) { // if game going on do next move
                    nextMove();
                    repaint();
                } else { // if block reached bottom or made a collision with other blocks spawn a new figure
                    if (!Tetris.gamePanel.gameOver) {
                        timer.cancel(); startTimer(); // cancel and start timer again for smoother experience after block reached bottom
                        game_going = true;
                        newBlock = true;
                    }
                }
            }
        };
        timer.scheduleAtFixedRate(task, 0, time_interval); // starting a timer
    }

    public void nextMove() {
        if (Tetris.gamePanel.score >= 100 * level && level < 9) { // adjusting level min 1 max 9
            level++;
            time_interval = 1000 - (level * 100);
            timer.cancel(); startTimer(); // canceling and starting timer to adjust speed by level
        }
        if (game_going) { // if game is going
            if (newBlock) { // if a new block spawn it
                Tetris.gamePanel.spawnFigure(currentFigure);
                currentFigure = Figure.values()[r.nextInt(FIGURE_COUNT)];
                newBlock = false;
            } else { // if we have a new block, moving it
                Tetris.gamePanel.moveFigure("down");
            }
        }
    }

    public void newGame() { // for a new game, reset all values
        timer.cancel();
        newGame = false;
        game_going = true;
        newBlock = true;
        Tetris.gamePanel.gameOver = false;
        Tetris.gamePanel.score = 0;
        time_interval = 1000;
        level = 11 - time_interval / 100;
        for (int i = 0; i < Tetris.gamePanel.w; i++) { // clearing an old array of blocks
            for (int j = 0; j < Tetris.gamePanel.h; j++) {
                Tetris.gamePanel.a[i][j] = 0;
            }
        }
        currentFigure = Figure.values()[r.nextInt(FIGURE_COUNT)];
        startTimer();
        repaint();
    }
}