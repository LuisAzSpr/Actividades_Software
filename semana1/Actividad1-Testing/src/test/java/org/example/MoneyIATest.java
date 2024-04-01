package org.example;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;


public class MoneyIATest {
    private final static String VALID_CURRENCY = "USD";
    @ParameterizedTest
    @ValueSource(ints={-1234,-42,-1})
    void constructorShouldThrowIAForInvalidAmount(int invalidAmount){
        assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(()->{
                    new Money(invalidAmount,VALID_CURRENCY);
                });
    }
}
