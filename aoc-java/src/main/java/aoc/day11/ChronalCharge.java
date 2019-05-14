package aoc.day11;

import static aoc.day11.PowerCell.calcPowerCells;

class ChronalCharge {

    static void largestPowerSquare(int gridSize, int serialNumber) {
        var cells = calcPowerCells(gridSize, serialNumber);

        int largestSum = Integer.MIN_VALUE;
        int largestX = 0;
        int largestY = 0;
        int largestS = 0;

        for (int s = 2; s <= gridSize; s++) {
            // naive implementation -- could try to reuse previous calculations

            for (int i = 1; i <= gridSize - s; i++) {
                for (int j = 1; j <= gridSize - s; j++) {

                    int sum = sumSquare(i - 1, j - 1, s, cells);

                    if (sum > largestSum) {
                        largestSum = sum;
                        largestX = i;
                        largestY = j;
                        largestS = s;
                    }

                }
            }
        }

        System.out.println("Part two: " + largestX + "," + largestY + "," + largestS);
    }

    private static int sumSquare(int x, int y, int size, int[][] cells) {
        int sum = 0;

        for (int k = x; k < x + size; k++) {
            for (int l = y; l < y + size; l++) {
                sum += cells[k][l];
            }
        }

        return sum;
    }

    public static void main(String[] args) {
        largestPowerSquare(300, 6878);
    }

}
