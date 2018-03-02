/**
 * Created by Arnoldas on 30/11/2016.
 */

import javax.swing.*;
import java.awt.*;

import static java.awt.Color.*;

public class GamePanel extends JComponent {
    static Color[] colors =
            {black, green, blue, red,
                    yellow, magenta, cyan, orange}; // all colors
    public int[][] a; // array of blocks
    public int w, h; // width and height
    public static int size = 20;
    public boolean gameOver = true; // if game is finished
    public int score = 0; // game score
    // current figure variables
    private Figure currentFigure; // current figure type
    public int currentRotation; // for current figure rotation
    public int[] xcoord = new int[4]; // x coordinates of current figure
    public int[] ycoord = new int[4]; // y coordinates of current figure
    private int currentColor; // for color of current figure
    ////////////////////////////////////////////////////////////////////

    public GamePanel(int[][] a) { // getting array, adjusting width and height
        this.a = a;
        w = a.length;
        h = a[0].length;
    }


    public void spawnFigure(Figure figure) { // spawning a new figure,
        // assigning values //
        currentColor = figure.getColor();
        currentFigure = figure;
        currentRotation = 0;
        int hor = 0; // colored blocks
        int y = 0; // index of figure boolean array
        while (hor < 4) { // coloring blocks, finish when all 4 will be colored
            for (int i = w / 2 - 1; i < w / 2 + currentFigure.getCols() - 1; i++) {
                for (int j = h; j > h - currentFigure.getRows(); j--) {
                    if (currentFigure.getSquares()[currentRotation][y]) { // if boolean array element is true, color the block
                        a[i][h - j] = currentColor;
                        xcoord[hor] = i; // getting block x and y in drawing
                        ycoord[hor] = h - j;
                        hor++;
                    }
                    y++;
                }
            }
        }
        if (gameOver()) { // checking if spawned figure can move downwards, if not game is over everything is stopped
            Frame.game_going = false;
            gameOver = true;
            Tetris.frame.timer.cancel();
        } else {
            repaint();
        }
    }

    public boolean gameOver() { // checking if it's possible to move downwards
        int lowest = minHeight(ycoord);
        if (a[xcoord[lowest]][ycoord[lowest] + 1] != 0) {
            return true;
        } else {
            return false;
        }
    }

    public void moveFigure(String direction) { // moving current figure according to the given string - down,left or right
        if (isValid(direction)) { // if possible to move to given direction - continue
            for (int i = 0; i < 4; i++) {
                clearBlock(xcoord[i], ycoord[i]); // deleting old blocks
                switch (direction) {
                    case "down":
                        ycoord[i] += 1;
                        break;
                    case "left":
                        xcoord[i] -= 1;
                        break;
                    case "right":
                        xcoord[i] += 1;
                        break;
                }
            }
            for (int i = 0; i < 4; i++) { // coloring new blocks of current figure
                a[xcoord[i]][ycoord[i]] = currentColor;
            }
            repaint();
        }
    }

    public void rotateFigure() { // rotating figure
        if (isValid("down")) { // if it is possible to move downwards
            currentRotation++;
            int hor = 0, y = 0; // which block out of 4 / index for figure booleans
            int passed = 0; // counter for blocks which passed tests
            int[] txcoord = new int[4]; // temporary figure x coordinates
            int[] tycoord = new int[4]; // temporary figure y coordinates
            for (int i = 0; i < 4; i++) { // copying old values to temporary ones, and deleting them
                txcoord[i] = xcoord[i];
                tycoord[i] = ycoord[i];
                a[xcoord[i]][ycoord[i]] = 0;
                xcoord[i] = 0;
                ycoord[i] = 0;
            }
            int lowestpoint = minHeight(tycoord); // getting the lowest point of figure
            if (currentRotation % 4 == 0) { // only 4 rotations possible
                currentRotation = 0;
            }
            for (int i = txcoord[0]; i < txcoord[0] + currentFigure.getCols(); i++) {
                for (int j = tycoord[lowestpoint] - currentFigure.getRows()+1; j < tycoord[lowestpoint]+1; j++) {
                    if (currentFigure.getSquares()[currentRotation][y]) {
                        if (i >= 0 && i < 10 && j >= 0 && hor < 4) { // if doesn't collide with borders
                            if (a[i][j] == 0) { // if block is black
                                passed++;
                                xcoord[hor] = i;
                                ycoord[hor] = j;
                                hor++;
                            }
                        }
                    }
                    y++;
                }
            }
            if (passed == 4) { // if all 4 blocks passed tests, color current figure
                for (int z = 0; z < 4; z++) {
                    a[xcoord[z]][ycoord[z]] = currentColor;
                }
                repaint();
            } else { // if not, recover old values
                xcoord = txcoord.clone();
                ycoord = tycoord.clone();
                currentRotation--;
            }
        }
    }

    public int minHeight(int[] col) { // getting lowest point of figure
        int lowest = 0;
        for (int i = 1; i < 4; i++) {
            if (col[i] > col[lowest]) {
                lowest = i;
            }
        }
        return lowest;
    }

    public boolean isCurrentFigure(int x, int y) { // if block is from current figure
        for (int i = 0; i < 4; i++) {
            if (x == xcoord[i] && y == ycoord[i]) {
                return true;
            }
        }
        return false;
    }

    public boolean isValid(String direction) { // if given direction doesn't make any collisions with borders of other blocks
        for (int i = 0; i < 4; i++) { // going through all the blocks
            switch (direction) {
                case "down": // can it move downwards
                    if (ycoord[i] + 1 < size && (a[xcoord[i]][ycoord[i] + 1] == 0 || isCurrentFigure(xcoord[i], ycoord[i] + 1))) {
                        continue;
                    } else {
                        Frame.game_going = false; // laikina
                        clearLines();
                        return false;
                    }
                case "left": // can it move to left
                    if (xcoord[i] - 1 >= 0 && (a[xcoord[i] - 1][ycoord[i]] == 0 || isCurrentFigure(xcoord[i] - 1, ycoord[i]))) {
                        continue;
                    } else {
                        return false;
                    }
                case "right": // can it move to right
                    if (xcoord[i] + 1 < 10 && (a[xcoord[i] + 1][ycoord[i]] == 0 || isCurrentFigure(xcoord[i] + 1, ycoord[i]))) {
                        continue;
                    } else {
                        return false;
                    }
            }
        }
        return true;
    }

    public void clearLines() { // clear full lines if there is any and sum the score
        int fullBlocks = 0;
        for (int i = 0; i < h; i++) {
            for (int j = 0; j < w; j++) { // check how many blocks is full
                if (a[j][i] != 0) {
                    fullBlocks++;
                }
            }
            if (fullBlocks == 10) { // if line is full, delete it and add score
                deleteLine(i);
                score += 10;
                fullBlocks = 0;
            } else {
                fullBlocks = 0;
            }
        }
    }

    public void deleteLine(int x) { // deleted selected line

        for (int i = x; i > 0; i--) {
            for (int j = 0; j < w; j++) {
                a[j][i] = a[j][i - 1];
            }
        }
    }

    public void clearBlock(int x, int y) {
        a[x][y] = 0;
    } // delete selected block

    public void paintComponent(Graphics g) { // painting an array of tetris
        super.paintComponent(g);
        for (int i = 0; i < w; i++) {
            for (int j = 0; j < h; j++) {
                g.setColor(colors[a[i][j]]);
                g.fill3DRect(i * size, j * size,
                        size, size, true);
            }
        }
    }

    public Dimension getPreferredSize() {
        return new Dimension(w * size, h * size);
    } // adjusting window size
}
