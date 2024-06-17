package byow.Core;

import byow.TileEngine.TERenderer;
import byow.TileEngine.TETile;
import edu.princeton.cs.introcs.StdDraw;


public class Engine {
    /* Feel free to change the width and height. */
    public static final int WIDTH = 96;
    public static final int HEIGHT = 48;
    private static Player player1;

    /* Keeps track what state the game is in */
    private static int gameState = 0;
    /**
     * Method used for exploring a fresh world. This method should handle all inputs,
     * including inputs from the main menu.
     */
    public void interactWithKeyboard() {
        TETile[][] world = null;
        boolean replayed = false;
        TERenderer ter;
        if (gameState == 0) {
            /* Calling Main Menu */
            GUI.createTitleScreen();
        }

        while (gameState == 0) {
            /* Creating a New Game */
            if (StdDraw.isKeyPressed('N')
                    || StdDraw.isKeyPressed('n')) {
                world = GUI.createInputScreen();
                gameState = 1;
            }

            /* Loading a Saved Game */
            if (StdDraw.isKeyPressed('L')
                    || StdDraw.isKeyPressed('l')) {
                gameState = 1;
                world = Persistence.load();
            }

            /* Replaying a Saved Game */
            if (StdDraw.isKeyPressed('R')
                    || StdDraw.isKeyPressed('r')) {
                gameState = 1;
                replayed = true;
                world = Persistence.replay();
            }

            /* Quitting */
            if (StdDraw.isKeyPressed('Q')
                    || StdDraw.isKeyPressed('q')) {
                System.exit(0);
            }
        }

        /* renders the world and runs the game loop */
        ter = render();
        updateRender(world, ter);

        if (replayed) {
            String guiInput = GUI.getInput();
            String actions = InputUtils.getActions(guiInput);
            Actions.slowTakeActions(world, actions, player1, ter);
        }
        gameLoop(ter, world);
    }

    private void gameLoop(TERenderer ter, TETile[][] world) {
        /* quiting conditions */
        boolean readyToQuit = false;
        boolean quitting = false;

        /* key logger */
        StringBuilder keyLog = new StringBuilder();

        /* main loop */
        while (!quitting) {
            /* updates the HUD */
            GUI.clearHUD();
            GUI.mainHud(world);
            StdDraw.pause(20);
            if (StdDraw.hasNextKeyTyped()) {
                /* logs the key pressed */
                char nextAction = StdDraw.nextKeyTyped();
                keyLog.append(nextAction);
                /* actions based on the key pressed */
                if (nextAction == ':') {
                    readyToQuit = true;
                } else if (readyToQuit) {
                    if (nextAction == 'q'
                            || nextAction == 'Q') {
                        quitting = true;
                    } else {
                        readyToQuit = false;
                    }
                } else {
                    Actions.takeAction(world, nextAction, player1);
                    updateRender(world, ter);
                }
            }
        }

        String guiInput = GUI.getInput();
        Persistence.save(guiInput + keyLog);
        System.exit(0);
    }

    /**
     * Method used for autograding and testing your code. The input string will be a series
     * of characters (for example, "n123sswwdasdassadwas", "n123sss:q", "lwww". The engine should
     * behave exactly as if the user typed these characters into the engine using
     * interactWithKeyboard.
     *
     * Recall that strings ending in ":q" should cause the game to quite save. For example,
     * if we do interactWithInputString("n123sss:q"), we expect the game to run the first
     * 7 commands (n123sss) and then quit and save. If we then do
     * interactWithInputString("l"), we should be back in the exact same state.
     *
     * In other words, both of these calls:
     *   - interactWithInputString("n123sss:q")
     *   - interactWithInputString("lww")
     *
     * should yield the exact same world state as:
     *   - interactWithInputString("n123sssww")
     *
     * @param input the input string to feed to your program
     * @return the 2D TETile[][] representing the state of the world
     */
    public TETile[][] interactWithInputString(String input) {
        // passed in as an argument, and return a 2D tile representation of the
        // world that would have been drawn if the same inputs had been given
        // to interactWithKeyboard().
        //
        // See proj3.byow.InputDemo for a demo of how you can make a nice clean interface
        // that works for many different input types.

        String inputUpper = input.toUpperCase();

        long seed;
        TETile[][] world;

        if (inputUpper.charAt(0) == 'N') {
            /* takes the seed from the input and generates a world*/
            seed = InputUtils.getSeed(inputUpper);
            world = WorldGeneration.worldBuilder(seed);
            /* creates a save file */
            Persistence.setupPersistence();
            /* places the avatar in a room and takes all the actions given by the input */
            int[] roomCenter =  Room.findRoomCenter(WorldGeneration.getRooms().get(0));
            player1 = new Player(roomCenter[0], roomCenter[1]);
            String actions = InputUtils.getActions(input);
            Actions.takeActions(world, actions, player1, input);
        } else if (inputUpper.charAt(0) == 'L') {
            /* loads the world from the save */
            String actions = input.substring(1);
            world = Persistence.load(actions);
        } else {
            world = null;
        }
        return world;
    }

    public TERenderer render() {
        TERenderer ter = new TERenderer();
        ter.initialize(WIDTH, HEIGHT);
        return ter;
    }

    public static void updateRender(TETile[][] world, TERenderer ter) {
        ter.renderFrame(world);
    }
}
