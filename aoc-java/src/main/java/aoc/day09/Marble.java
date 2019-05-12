package aoc.day09;

class Marble {

    Marble next;
    Marble previous;
    long value;
    long score;

    Marble(long value) {
        this.value = value;
        next = this;
        previous = this;
    }

    private Marble(long value, Marble previous, Marble next) {
        this.value = value;
        this.previous = previous;
        this.next = next;
    }

    Marble simpleAdd(Long marbleValue) {
        var oneRight = next;
        var twoRight = next.next;

        var newMarble = new Marble(marbleValue, oneRight, twoRight);

        oneRight.next = newMarble;
        twoRight.previous = newMarble;

        return newMarble;
    }

    Marble specialAdd(Long marble) {
        var sevenLeft = previous.previous.previous.previous.previous.previous.previous;
        sevenLeft.next.score = marble + sevenLeft.value;

        sevenLeft.previous.next = sevenLeft.next;
        sevenLeft.next.previous = sevenLeft.previous;

        return sevenLeft.next;
    }

}
