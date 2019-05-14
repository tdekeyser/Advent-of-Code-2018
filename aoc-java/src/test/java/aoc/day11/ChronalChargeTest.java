package aoc.day11;

import org.junit.Test;

import static aoc.day11.PowerCell.calcPower;
import static org.assertj.core.api.Assertions.assertThat;

public class ChronalChargeTest {

//    @Test
//    public void largestPowerSquare_1() {
//        assertThat(largestPowerSquare(300, 18)).isEqualTo(new Coord(33,45, largestS));
//    }
//
//    @Test
//    public void largestPowerSquare_2() {
//        assertThat(largestPowerSquare(300, 42)).isEqualTo(new Coord(21,61, largestS));
//    }

    @Test
    public void calcPower_1() {
        assertThat(calcPower(3, 5, 8)).isEqualTo(4);
    }

    @Test
    public void calcPower_2() {
        assertThat(calcPower(122, 79, 57)).isEqualTo(-5);
    }

    @Test
    public void calcPower_3() {
        assertThat(calcPower(217, 196, 39)).isEqualTo(0);
    }

    @Test
    public void calcPower_4() {
        assertThat(calcPower(101, 153, 71)).isEqualTo(4);
    }

}
