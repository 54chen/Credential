
package com.john.biz;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.beryx.textio.InputReader.ValueChecker;

/**
 * SwearChecker
 */
public class SwearChecker implements ValueChecker<String> {
    private final List<String> swearWord = listPossibleVariants();

    private final static List<String> swears = new ArrayList<>(
            Arrays.asList("fuck", "bitch", "bloody", "cock", "shit", "fucker", "fuckstick",
                    "asshole", "dick", "piss", "cunt"));

    private final static Map<String, String> exchange = new HashMap<>();
    static {
        exchange.put("l", "1");
        exchange.put("o", "0");
        exchange.put("I", "1");
        exchange.put("O", "0");
        exchange.put("i", "1");
        exchange.put("u", "4");
    }

    @Override
    public List<String> getErrorMessages(String val, String itemName) {
        if (val == null || val == "") {
            return null;
        }
        if (swearWord.contains(val)) {
            return Arrays.asList("Username should not use swear words!");
        }
        return null;
    }

    private List<String> listPossibleVariants() {
        List<String> fullSwears = new ArrayList<>();

        swears.forEach((v) -> {

            listPossibleVariants(fullSwears, v);

        });

        return fullSwears;
    }

    /**
     * example: blooody -> bl0oody,blo0ody,bloo0dy, bl00ody, bl0o0dy, blo00dy,
     * bl000dy
     * 
     * @param swear
     * @param number
     */
    private void listPossibleVariants(List<String> result, String swear) {
        char[] c = swear.toCharArray();
        List<Integer> x = new ArrayList<>();
        List<String> y = new ArrayList<>();
        exchange.forEach((k, v) -> {
            for (int i = 0; i < c.length; i++) {
                if (c[i] == k.charAt(0)) {
                    x.add(i);
                    y.add(v);
                }
            }
        });

        permutation(result, c, 0, y, x);
    }

    private void permutation(List<String> result, char[] c, int start, List<String> y, List<Integer> x) {
        result.add(new String(c));
        for (int i = start; i < x.size(); i++) {
            char temp = c[x.get(i)];
            c[x.get(i)] = y.get(i).charAt(0);
            permutation(result, c, start + 1, y, x);
            c[x.get(i)] = temp;
        }
    }
}