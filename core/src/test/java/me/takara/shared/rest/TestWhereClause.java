package me.takara.shared.rest;

import junit.framework.TestCase;
import me.takara.core.entities.fields.BondFields;
import me.takara.core.rest.SearchCriteria;
import org.junit.Test;

public class TestWhereClause extends TestCase {
    
    @Test
    public void testGetterSetter() {
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