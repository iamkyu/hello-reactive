package learningtest;

import org.junit.Test;

import java.util.Iterator;

import static org.assertj.core.api.Assertions.assertThat;

public class IteratorTest {
    @Test
    public void pull() {
        // given
        Iterable<Integer> iter = () ->
                new Iterator<Integer>() {
                    final static int MAX = 10;
                    int i = 0;

                    @Override
                    public boolean hasNext() {
                        return i < MAX;
                    }

                    @Override
                    public Integer next() {
                        return ++i;
                    }
                };

        Counter counter = new Counter();

        // when
        for (int each : iter) {
            counter.call();
        }

        // then
        assertThat(counter.getCount()).isEqualTo(10);
    }

    class Counter {
        private int count;

        public Counter() {
            this.count = 0;
        }

        public void call() {
            count++;
        }

        public int getCount() {
            return count;
        }
    }
}
