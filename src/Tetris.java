import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Created by Arnoldas on 30/11/2016.
 */

public class Tetris {
    private static int w = 10; // width
    private static int h = 20; // height
    private static int[][] a = new int[w][h]; // array for blocks
    public static GamePanel gamePanel = new GamePanel(a); // creates game panel
    public static StatsPanel stats = new StatsPanel(); // creates stats panel
    public static Frame frame; // Frame instance

    public static void main(String[] args) { // creating and starting a new game
        new Tetris();
    }

    public Tetris() {
        this.frame = new Frame(gamePanel, stats, "Tetris - Arnoldas Katkevicius 1502603"); // creating instance of frame which hold game panel and stats
        frame.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) { // mouse listener
                switch (e.getButton()) {
                    case MouseEvent.BUTTON1: { // move to left
                        if (frame.game_going) {
                            Tetris.gamePanel.moveFigure("left");
                        }
                        break;
                    }

                    case MouseEvent.BUTTON2: { // rotate
                        if (frame.game_going) {
                            Tetris.gamePanel.rotateFigure();
                        }
                        break;
                    }

                    case MouseEvent.MOUSE_WHEEL: { // rotate
                        if (frame.game_going) {
                            Tetris.gamePanel.rotateFigure();
                        }
                        break;
                    }

                    case MouseEvent.BUTTON3: { // move to right
                        if (frame.game_going) {
                            Tetris.gamePanel.moveFigure("right");
                        }
                        break;
                    }

                }
            }
        });


        frame.addKeyListener(new KeyAdapter() { // adding key listener
            @Override
            public void keyPressed(KeyEvent e) {
                switch (e.getKeyCode()) { // get pressed key

                    case KeyEvent.VK_UP: { // up arrow - rotate
                        if (frame.game_going) {
                            Tetris.gamePanel.rotateFigure();
                        }
                        break;
                    }

                    case KeyEvent.VK_LEFT: { // left arrow - move left
                        if (frame.game_going && Tetris.gamePanel.isValid("down")) { // if game still run & there's still space left to move downwards
                            Tetris.gamePanel.moveFigure("left");
                        }
                        break;
                    }

                    case KeyEvent.VK_RIGHT: { // right arrow - more right
                        if (frame.game_going && Tetris.gamePanel.isValid("down")) { // if game still run & there's still space left to move downwards
                            Tetris.gamePanel.moveFigure("right");
                        }
                        break;
                    }

                    case KeyEvent.VK_DOWN: { // down arrow - move down

                        if (frame.game_going && Tetris.gamePanel.isValid("down")) { // if game still run & there's still space left to move downwards
                            Tetris.gamePanel.moveFigure("down");
                        }
                        break;
                    }

                    case KeyEvent.VK_SPACE: { // pause
                        if (frame.game_going) {
                            frame.timer.cancel();
                            frame.game_going = false;
                        } else {
                            frame.startTimer();
                            frame.game_going = true;
                        }
                        break;
                    }

                    case KeyEvent.VK_ENTER: { // to start a new game
                        if (Tetris.gamePanel.gameOver) { // if game is over let to start a new game
                            System.out.println("Start new game");
                            frame.newGame(); // starting a new game
                        }
                        break;
                    }
                }
            }
        });
    }
}