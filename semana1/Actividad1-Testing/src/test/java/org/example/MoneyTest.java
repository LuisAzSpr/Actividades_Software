package org.example;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

class MoneyTest {
    public static final String USD = "USD";
    @Test
    void constructorShouldSetAmountAndCurrency(){
        Money money = new Money(10,USD);
        assertThat(money.getAmount()).isEqualTo(10);
        assertThat(money.getCurrency()).isEqualTo(USD);

        money = new Money(20,USD);
        assertThat(money.getAmount()).isEqualTo(20);
        assertThat(money.getCurrency()).isEqualTo(USD);
    }
    /*
    @Test
    public void test1(){
        Money_2 money = new Money_2(2000,"USD");
        assertThat(money.getAmount()).isEqualTo(2000);
        assertThat(money.getCurrency()).isEqualTo("USD");
    }

    @Test
    public void test2(){
        Money_2 money1 = new Money_2(1000,"USD");
        Money_2 money2 = new Money_2(1000,"USD");
        Money_2 money3 = new Money_2(1000,"USD");
        Money_2 money4 = new Money_2(1000,"S/.");

        assertThat(money1.equals(money2)).isEqualTo(true);
        assertThat(money3.equals(money4)).isEqualTo(false);

    }
    */
}




