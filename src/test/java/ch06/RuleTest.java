package ch06;

import org.junit.jupiter.api.Test;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertTimeout;

public class RuleTest {


    @Test
    void willFail() {
        assertTimeout(Duration.ofMillis(20), () -> Thread.sleep(100));
    }

    @Test
    void willPass() {
        assertTimeout(Duration.ofMillis(100), () -> Thread.sleep(50));
    }
}
