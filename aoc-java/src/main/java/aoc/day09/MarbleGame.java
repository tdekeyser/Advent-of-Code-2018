package aoc.day09;

import static java.util.Arrays.stream;

class MarbleGame {

    static long play(int numPlayers, long lastPoints) {
        var turn = 1;
        var scores = new long[numPlayers];
        var currentMarble = new Marble(0);

        for (long i = 1; i <= lastPoints; i++) {
            if (i % 23 == 0) {
                currentMarble = currentMarble.specialAdd(i);
            } else {
                currentMarble = currentMarble.simpleAdd(i);
            }

            scores[turn] += currentMarble.score;
            turn = ++turn % numPlayers;
        }

        return stream(scores).max().getAsLong();
    }

    public static void main(String[] args) {
        System.out.println("Part one: " + play(416, 71_617));
        System.out.println("Part two: " + play(416, 7_161_700));
    }

}
