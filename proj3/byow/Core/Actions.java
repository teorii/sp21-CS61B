package byow.Core;

import byow.TileEngine.TERenderer;
import byow.TileEngine.TETile;
import edu.princeton.cs.introcs.StdDraw;
import java.util.HashMap;


public class Actions {

    public static void takeActions(TETile[][] world, String actions, Player player, String input) {
        for (int i = 0; i < actions.length(); i++) {
            char action = actions.charAt(i);
            if (action == 'Q') {
                if (i > 0 && actions.charAt(i - 1) == ':') {
                    Persistence.save(input);
                    return;
                }
            }
            takeAction(world, action, player);
        }
    }

    public static void slowTakeActions(TETile[][] world,
                                       String actions,
                                       Player player,
                                       TERenderer ter) {
        for (int i = 0; i < actions.length(); i++) {
            /* takes actions with 300 millisecond delay */
            char action = actions.charAt(i);
            takeAction(world, action, player);
            Engine.updateRender(world, ter);
            StdDraw.pause(300);
        }
    }

    public static void takeAction(TETile[][] world, char action, Player player) {
        action = Character.toUpperCase(action);
        HashMap<Character, int[]> actionMeaning = new HashMap<Character, int[]>() {{
                    put('W', new int[]{0, 1});
                    put('A', new int[]{-1, 0});
                    put('S', new int[]{0, -1});
                    put('D', new int[]{1, 0});
            }};
        int[] actionDirections = actionMeaning.get(action);
        if (actionDirections != null) {
            player.move(world, actionDirections[0], actionDirections[1]);
        }
    }

}
