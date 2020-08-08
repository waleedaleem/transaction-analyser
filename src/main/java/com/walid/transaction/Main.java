package com.walid.transaction;

import java.util.Arrays;

/**
 * @author Walid Moustaf
 */
public class Main {

    private static final String[] HELP_ALIASES = { "-h", "-help", "--help" };

    public static void main(String[] args) {
        if (args.length != 1 || Arrays.asList(HELP_ALIASES).contains(args[0])) {
            System.out
                .println("usage: java -jar transaction-analyser <startup transactions CSV file>");
            System.exit(0);
        }
    }
}
