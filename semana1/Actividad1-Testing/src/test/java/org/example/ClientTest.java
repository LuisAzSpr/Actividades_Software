package org.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ClientTest {
    private Address address = new Address("street A");
    private Address secondAddress = new Address("street N");

    private Client client;

    @BeforeEach
    void setUp(){
        client = new Client();
    }

    @Test
    void afterCreationShouldHaveNoAddress(){

    }

    @Test
    void shouldAllowToAddAddress(){

    }

    @Test
    void ShouldAllowToAddress(){
        client.addAddress(address);
        client.addAddress(secondAddress);
    }

}
