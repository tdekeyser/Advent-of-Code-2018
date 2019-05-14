package aoc.day11;

class PowerCell {

    static int[][] calcPowerCells(int gridSize, int gridSerialNumber) {
        int[][] cells = new int[gridSize][gridSize];

        for (int i = 1; i <= gridSize; i++) {
            for (int j = 1; j <= gridSize; j++) {
                cells[i - 1][j - 1] = calcPower(i, j, gridSerialNumber);
            }
        }

        return cells;
    }

    static int calcPower(int x, int y, int gridSerialNumber) {
        int rackId = x + 10;
        return (((((rackId * y) + gridSerialNumber) * rackId) / 100) % 10) - 5;
    }

}
