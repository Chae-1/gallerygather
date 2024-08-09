package com.kosa.gallerygather.others;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.Instant;

public class InstantClassTest {
    @Test
    void instantTest() throws InterruptedException {
        Instant now = Instant.now().plusSeconds(3600);
        Thread.sleep(1000);

        Instant afterOneSecond = Instant.now();

        if (now.isAfter(afterOneSecond)) {
            System.out.println(":이미지남");
        }
    }
}
