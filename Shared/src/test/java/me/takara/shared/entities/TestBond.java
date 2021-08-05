package me.takara.shared.entities;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;

public class TestBond {

    @Test
    public void testJsonToString() {
        Bond bond = new Bond(1, "A BOND");

        try {
            System.out.println(new ObjectMapper().writeValueAsString(bond));
        }
        catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        System.out.println(bond.toString());
    }
}