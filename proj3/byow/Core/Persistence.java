package byow.Core;

import byow.TileEngine.TETile;
import java.io.File;
import java.io.IOException;
import static byow.Core.Utils.*;

public class Persistence {

    static final File CWD = new File(System.getProperty("user.dir"));
    static File SAVE_FILE = join(CWD, "save_file.txt");


    public static void save(String input) {
        int noQ = input.length() - 2;
        String saveString = input.substring(0, noQ);
        Utils.writeContents(SAVE_FILE, saveString);
    }

    public static void setupPersistence() {
        try {
            SAVE_FILE.createNewFile();
        } catch (IOException e) {
            System.out.println("Averted!");
        }
    }

    public static TETile[][] load(String newActions) {
        String saveString = readContentsAsString(SAVE_FILE);
        Engine e = new Engine();
        return e.interactWithInputString(saveString + newActions);
    }

    public static TETile[][] load() {
        String saveString = readContentsAsString(SAVE_FILE);
        GUI.setInput(saveString);
        Engine e = new Engine();
        return e.interactWithInputString(saveString);
    }

    public static TETile[][] replay() {
        String saveString = readContentsAsString(SAVE_FILE);
        GUI.setInput(saveString);
        Engine e = new Engine();

        String seed = "N" + InputUtils.getSeed(saveString) + "S";
        return e.interactWithInputString(seed);
    }


}
