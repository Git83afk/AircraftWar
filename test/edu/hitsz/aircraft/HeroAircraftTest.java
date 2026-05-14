package edu.hitsz.aircraft;

import edu.hitsz.basic.AbstractFlyingObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.*;

class HeroAircraftTest {

    private HeroAircraft heroAircraft;

    // 两个测试方法运行前，都会自动先执行一次 setUp，保证测试互不干扰
    @BeforeEach
    void setUp() throws NoSuchFieldException, IllegalAccessException {
        System.out.println("**--Resetting HeroAircraft state for test--**");
        heroAircraft = HeroAircraft.getInstance();

        // 1. 统一重置血量为固定的 100
        heroAircraft.increaseHp(1000);
        heroAircraft.decreaseHp(100);

        // 2. 统一强行重置存活状态 (复活飞机)
        Field validField = AbstractFlyingObject.class.getDeclaredField("isValid");
        validField.setAccessible(true);
        validField.set(heroAircraft, true);
    }

    // ---------- 第一个测试：加血 ----------
    @ParameterizedTest
    @DisplayName("Test method increaseHP")
    @CsvSource({
            "20, 120",
            "30, 130",
            "150, 200"
    })
    void increaseHp(int increase, int expected) {
        heroAircraft.increaseHp(increase);
        assertEquals(expected, heroAircraft.getHp());
    }

    // ---------- 第二个测试：扣血 ----------
    @ParameterizedTest
    @DisplayName("Test method decreaseHp")
    @CsvSource({
            "30, 70, false",
            "100, 0, true",
            "150, 0, true"
    })
    void decreaseHp(int decrease, int expectedHp, boolean expectedVanished) {
        heroAircraft.decreaseHp(decrease);
        assertEquals(expectedHp, heroAircraft.getHp());
        assertEquals(expectedVanished, heroAircraft.notValid());

    }

    // ---------- 第三个测试：测试子弹威力值 ----------
    @Test
    @DisplayName("Test method getpower")
    void getpower (){
        assertEquals(30,heroAircraft.getPower());
    }


}

