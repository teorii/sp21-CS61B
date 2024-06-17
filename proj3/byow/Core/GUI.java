package byow.Core;

import byow.TileEngine.TETile;
import edu.princeton.cs.introcs.StdDraw;
import java.awt.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class GUI {

    private static final Font HEADER = new Font("Sans Serif", Font.BOLD, 48);
    private static final Font SUBTITLE = new Font("Sans Serif", Font.BOLD, 13);
    private static final Font TEXT = new Font("Sans Serif", Font.BOLD, 24);
    private static final Font HUD = new Font("San Serif", Font.PLAIN, 12);
    private static final Font DATE = new Font("San Serif", Font.PLAIN, 14);
    private static String input = null;


    /* Creates the Game's Main Menu */
    public static void createTitleScreen() {
        /* Setting Scales/Sizes */
        StdDraw.setCanvasSize(1280, 720);
        StdDraw.setXscale(0.0, 5.0);
        StdDraw.setYscale(0.0, 5.0);

        /* Setting background to Black */
        StdDraw.clear(StdDraw.BLACK);

        /* Creating the Game Title Text */
        StdDraw.setPenColor(Color.WHITE);
        Font header = new Font("Sans Serif", Font.BOLD, 60);
        StdDraw.setFont(header);
        StdDraw.text(2.5, 4, "CS61B: The Game");

        /* Creating the other Main Menu Text */
        Font text = new Font("Sans Serif", Font.BOLD, 24);
        StdDraw.setFont(text);
        StdDraw.text(2.5, 2.7, "New Game (N)");
        StdDraw.text(2.5, 2.5, "Load Game (L)");
        StdDraw.text(2.5, 2.3, "Replay Game (R)");
        StdDraw.text(2.5, 2.1, "Quit (Q)");
    }

    public static TETile[][] createInputScreen() {
        /* initializes the world */
        TETile[][] world = null;

        /* Setting Scales/Sizes */
        StdDraw.enableDoubleBuffering();
        baseInputScreen();

        /* Creating the other Main Menu Text */
        StdDraw.setFont(TEXT);
        StdDraw.text(2.5, 2.0, "_");
        StdDraw.show();

        char userInput = 'a';
        String inputSeed = "null";
        while (userInput != 'q') {
            if (StdDraw.hasNextKeyTyped()) { // check for keyboard input
                userInput = StdDraw.nextKeyTyped();
                if (Character.isDigit(userInput)) {
                    if (!(inputSeed.equals("null"))) {
                        inputSeed = inputSeed + userInput;
                    }
                    if (inputSeed.equals("null")) {
                        inputSeed = String.valueOf(userInput);
                    }
                    baseInputScreen();
                    StdDraw.setFont(TEXT);
                    StdDraw.text(2.5, 2.0, inputSeed);
                    StdDraw.show();
                }
                if (userInput == 's' || userInput == 'S') {
                    if (inputSeed.equals("null")) {
                        StdDraw.setFont(SUBTITLE);
                        StdDraw.text(2.5, 1.6, "Please input a seed before pressing 's'.");
                        StdDraw.show();
                    } else {
                        Engine newGame = new Engine();
                        input = "N" + inputSeed + "S";
                        world = newGame.interactWithInputString(input);
                        return world;
                    }
                }
            }
        }
        return null;
    }

    public static void baseInputScreen() {
        /* Setting background to Black */
        StdDraw.clear(StdDraw.BLACK);

        /* Creating the Game Title Text */
        StdDraw.setPenColor(Color.WHITE);
        StdDraw.setFont(HEADER);
        StdDraw.text(2.5, 3.0, "Input Seed:");

        /* Creating the Subtitle Text */
        StdDraw.setPenColor(Color.WHITE);
        StdDraw.setFont(SUBTITLE);
        StdDraw.text(2.5, 2.7, "Press Q to quit. Press S once the Seed has been inputted.");
    }

    private static void createHUD(String text) {
        /* Creating the Game Title Text */
        StdDraw.setPenColor(Color.WHITE);
        StdDraw.setFont(HUD);
        StdDraw.text(3, 47, text);

        /* Getting the current Date into String Format */
        Date date = new Date();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        String currentTime = dateFormat.format(date);

        /* Font was too small to display the date properly */
        StdDraw.setFont(DATE);

        /* Putting the currentTime on screen. */
        StdDraw.text(6, 46, currentTime);

        StdDraw.show();
    }

    public static void clearHUD() {
        StdDraw.setPenColor(Color.BLACK);
        StdDraw.filledRectangle(1, 47, 20, 4);
        StdDraw.show();
    }

    private static String mouseTile(TETile[][] world) {
        double x = StdDraw.mouseX();
        double y = StdDraw.mouseY();
        TETile tile = world[(int) x][(int) y];
        return tile.description();
    }

    public static void mainHud(TETile[][] world) {
        String tileDescription = mouseTile(world);
        createHUD(tileDescription);
    }

    public static void setInput(String setting) {
        input = setting;
    }

    public static String getInput() {
        return input;
    }
}
