package me.takara.shared.rest;

import com.fasterxml.jackson.core.JsonProcessingException;
import junit.framework.TestCase;
import me.takara.shared.entities.fields.BondFields;
import org.junit.Test;

public class TestWhereClause extends TestCase {
    
    @Test
    public void testGetterSetter() throws JsonProcessingException {
        SearchCriteria wc = new SearchCriteria() {{
            setScope(Scope.GET_FIRST);
            setOperator(Operator.EQ);
            setL_value(BondFields.NAME.toString());
            setR_value("12");
            //setWhereContent(new ArrayList() {{ add("A"); add("B"); }});
        }};
        System.out.println(wc.toJson());
    }

}