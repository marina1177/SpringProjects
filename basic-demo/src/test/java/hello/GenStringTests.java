package hello;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.Random;

class GenStringTests {

    static private GenString greeter;
    static private String str = null;
    Random r = new Random();
    int min = 1;
    int max = 100;

    @BeforeAll
    static void SetUp() {
        greeter = new GenString();
    }

    @Test
    void NullTest() {
        try {
            str = greeter.getRandomString(20);
        } catch (Exception e) {
            e.printStackTrace();
        }
        assertNotNull(str);
    }

    @RepeatedTest(5)
    void PositiveNumCharTest() {

        try {
            int lsize = r.nextInt(max - min) + min;
            System.out.println("lsize = " + lsize);
            str = greeter.getRandomString(lsize);
            assertEquals(lsize, str.length());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @ParameterizedTest
    @ValueSource(ints = {0, -3, 495, -123, 101})
    void NotValidNumCharTest(int lsize) {
        try {
            str = greeter.getRandomString(lsize);
            assertEquals("world", str);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

