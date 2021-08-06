package me.takara.shared.entities;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Assert;
import org.junit.Test;

public class TestBond {

    @Test
    public void testJsonToString() throws JsonProcessingException {
        Bond bond = new Bond(1, "A BOND");

        String msg = new ObjectMapper().writeValueAsString(bond);
        Assert.assertEquals("{\"id\":1,\"name\":\"A BOND\"}", msg);
    }
}