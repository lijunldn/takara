package me.takara.shared.rest;

import junit.framework.TestCase;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TestWhereClause extends TestCase {
    
    @Test
    public void testGetterSetter() {
        WhereClause wc = new WhereClause() {{
            setWhere("name");
            setWhereOp("is");
            setWhereContent(new ArrayList() {{ add("A"); add("B"); }});
        }};
        System.out.println(wc.toString());
    }

}