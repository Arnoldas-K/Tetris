import java.awt.*;
import java.util.Random;

/**
 * Created by Arnoldas on 30/11/2016.
 */
public enum Figure { // describing all the possible figures and their rotations

    FigureO(2, 2, new boolean[][]{
            { // 1 rotation
                    true, true,
                    true, true,
            },
            { // 2 rotation
                    true, true,
                    true, true,
            },
            { // 3 rotation
                    true, true,
                    true, true,
            },
            { // 4 rotation
                    true, true,
                    true, true,
            }
    }),

    FigureI(4, 4, new boolean[][]{
            {
                    false, false, false, false,
                    false, false, false, false,
                    true, true, true, true,
                    false, false, false, false,
            },
            {
                    false, false, true, false,
                    false, false, true, false,
                    false, false, true, false,
                    false, false, true, false,
            },
            {
                    true, true, true, true,
                    false, false, false, false,
                    false, false, false, false,
                    false, false, false, false,
            },
            {
                    false, true, false, false,
                    false, true, false, false,
                    false, true, false, false,
                    false, true, false, false,
            }
    }),

    FigureT(3, 3, new boolean[][]{
            { //
                    false, true, false,
                    false, true, true,
                    false, true, false,
            },
            { //
                    false, true, false,
                    true, true, true,
                    false, false, false,
            },
            { //
                    false, true, false,
                    true, true, false,
                    false, true, false,
            },

            { //
                    true, true, true,
                    false, true, false,
                    false, false, false,
            }
    }),

    FigureL(3, 3, new boolean[][]{
            { //
                    false, false, true,
                    false, false, true,
                    false, true, true,
            },
            { //
                    true, true, true,
                    false, false, true,
                    false, false, false,
            },
            { //
                    true, true, false,
                    true, false, false,
                    true, false, false,
            },
            { //
                    true, false, false,
                    true, true, true,
                    false, false, false,
            }
    }),

    FigureJ(3, 3, new boolean[][]{
            { //
                    false, false, true,
                    true, true, true,
                    false, false, false,
            },
            { //
                    false, true, true,
                    false, false, true,
                    false, false, true,
            },
            { //
                    true, true, true,
                    true, false, false,
                    false, false, false,
            },
            { //
                    true, false, false,
                    true, false, false,
                    true, true, false,
            }
    }),

    FigureZ(3, 3, new boolean[][]{
            {
                    false, true, true,
                    true, true, false,
                    false, false, false,
            },
            {
                    false, true, false,
                    false, true, true,
                    false, false, true,
            },
            {
                    false, false, false,
                    false, true, true,
                    true, true, false,
            },
            {
                    true, false, false,
                    true, true, false,
                    false, true, false,
            }
    }),

    FigureS(3, 3, new boolean[][]{
            {
                    true, true, false,
                    false, true, true,
                    false, false, false,
            },
            {
                    false, true, false,
                    true, true, false,
                    true, false, false,
            },
            {
                    false, false, false,
                    true, true, false,
                    false, true, true,
            },
            {
                    false, false, true,
                    false, true, true,
                    false, true, false,
            }
    });

    private int cols; // how many columns figure takes
    private int rows; // how many rows figure takes
    private int color; // figure color
    private boolean[][] squares; // figure rotations
    Random r = new Random(); // random number generator

    Figure(int cols, int rows, boolean[][] squares) { // creating a new figure type
        this.cols = cols;
        this.rows = rows;
        this.squares = squares;
    }

    public int getCols() {
        return cols;
    } // get how many columns takes current figure

    public int getRows() {
        return rows;
    } // get how many rows takes current figure

    public boolean[][] getSquares() {
        return squares;
    } // get current figure boolean array

    public int getColor() { // get random color for a figure
        color = r.nextInt(GamePanel.colors.length);
        if (this.color == 0) {
            while (this.color == 0) {
                this.color = r.nextInt(GamePanel.colors.length);
            }
        }
        return color;
    }
}