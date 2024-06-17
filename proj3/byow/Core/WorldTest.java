package byow.Core;

import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;

public class WorldTest {
    @Test
    public void interactTest() {
        String input1 = "N3412S";
        Integer seed1 = 3412;

        String input2 = "N341256SWDSA";
        Integer seed2 = 341256;

        String input3 = "S341256SWDSA";
        Integer seed3 = 341256;

        // The && operator is the same as "and" in Python.
        // It's a binary operator that returns true if both arguments true, and false otherwise.
        Engine e = new Engine();

        if (Arrays.deepEquals(e.interactWithInputString(input1),
                e.interactWithInputString(input1))) {
            System.out.println("correct");
        } else {
            assertEquals(2, 0);
        }
        if (!Arrays.deepEquals(e.interactWithInputString(input1),
                e.interactWithInputString(input2))) {
            System.out.println("nice");
        } else {
            assertEquals(1, 0);
        }
    }

    @Test
    public void titleScreenTest() {
        String input1 = "N3412S";
        Integer seed1 = 3412;

        Engine e = new Engine();

        GUI.createTitleScreen();

        String input2 = "N435S";
    }
}
