package byow.Core;
import org.junit.Test;
import static org.junit.Assert.*;

public class InputUtilsTest {
    @Test
    /** Adds a few things to the list, checking isEmpty() and size() are correct,
     * finally printing the results.
     *
     * && is the "and" operation. */
    public void getSeedTest() {


        String input1 = "N3412S";
        long seed1 = 3412L;

        String input2 = "N341256SWDSA";
        long seed2 = 341256L;

        String input3 = "S341256SWDSA";
        long seed3 = 341256L;

        // The && operator is the same as "and" in Python.
        // It's a binary operator that returns true if both arguments true, and false otherwise.
        assertEquals(seed1, InputUtils.getSeed(input1));
        assertEquals(seed2, InputUtils.getSeed(input2));
        assertEquals(seed3, InputUtils.getSeed(input3));


    }

    @Test
    /** Adds a few things to the list, checking isEmpty() and size() are correct,
     * finally printing the results.
     *
     * && is the "and" operation. */
    public void getActionTest() {


        String input1 = "N3412SDDDWSA";
        long seed1 = 3412L;

        String input2 = "N341256SWDSA:Q";
        long seed2 = 341256L;

        String input3 = "LWDSA:Sww";
        long seed3 = 341256L;

        // The && operator is the same as "and" in Python.
        // It's a binary operator that returns true if both arguments true, and false otherwise.
        assertEquals("DDDWSA", InputUtils.getActions(input1));
        assertEquals("WDSAQ", InputUtils.getActions(input2));
        assertEquals("WDSAWW", InputUtils.getActions(input3));


    }
}
