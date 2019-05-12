package aoc.day09;

import org.junit.Test;

import static aoc.day09.MarbleGame.play;
import static org.assertj.core.api.Assertions.assertThat;

public class MarbleGameTest {

    @Test
    public void play_example1() {
        assertThat(play(10, 1618)).isEqualTo(8317);
    }

    @Test
    public void play_example2() {
        assertThat(play(13, 7999)).isEqualTo(146373);
    }

    @Test
    public void play_example3() {
        assertThat(play(17, 1104)).isEqualTo(2764);
    }

    @Test
    public void play_example4() {
        assertThat(play(21, 6111)).isEqualTo(54718);
    }

    @Test
    public void play_example5() {
        assertThat(play(30, 5807)).isEqualTo(37305);
    }

}
