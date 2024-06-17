package byow.Core;

import java.util.HashSet;

public class InputUtils {

    public static long getSeed(String arg) {
        /* Returns a string of numbers from an input
        * of N###S... where the numbers are the #'s */
        String input = arg.toUpperCase();
        StringBuilder seedBuilder = new StringBuilder();
        for (int letter = 1; letter < input.length(); letter++) {
            char c = input.charAt(letter);
            if (c != 'S') {
                seedBuilder.append(input.charAt(letter));
            } else {
                break;
            }
        }
        String seedString = seedBuilder.toString();
        long seed = Long.parseLong(seedString);
        return seed;
    }

    public static String getActions(String arg) {
        /* Converts the String to Upper case */
        String input = arg.toUpperCase();

        /* Removes the N##S from the input */
        input = removeSeed(input);

        /* Obtains the action characters */
        HashSet<Character> valid = new HashSet<>();
        valid.add('D');
        valid.add('S');
        valid.add('A');
        valid.add('W');
        StringBuilder actionBuilder = new StringBuilder();
        for (int letter = 0; letter < input.length(); letter++) {
            Character c = input.charAt(letter);
            if (valid.contains(c)) {
                actionBuilder.append(c);
            } else if (c.equals(':')) {
                letter++;
                c = input.charAt(letter);
                if (c.equals('Q')) {
                    actionBuilder.append(':');
                    actionBuilder.append('Q');
                    break;
                }
            }
        }
        return actionBuilder.toString();
    }

    public static String removeSeed(String arg) {
        /* Removes the N##S from the input and returns everything after*/
        StringBuilder seedBuilder = new StringBuilder();
        boolean sPassed = false;
        for (int letter = 0; letter < arg.length(); letter++) {
            char c = arg.charAt(letter);
            if (sPassed) {
                seedBuilder.append(c);
            } else if (c == 'S' || c == 'L') {
                sPassed = true;
            }
        }
        return seedBuilder.toString();
    }
}
