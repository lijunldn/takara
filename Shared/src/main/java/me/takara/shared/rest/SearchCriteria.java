package me.takara.shared.rest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;

@Data
public class SearchCriteria {

    private String l_value;
    private String r_value;
    private Operator operator;

    private Scope scope = Scope.GET_FIRST;
//    private List<String> whereContent;

    public SearchCriteria() {}

    @Override
    public String toString() { return String.format("%s %s %s", l_value, operator, r_value); }

    public String toJson() {
        try {
            return new ObjectMapper().writeValueAsString(this);
        } catch (JsonProcessingException ex) {
            return this.toString();
        }
    }

    public enum Operator {
        EQ("=="), LT("<"), GT(">");

        String op;
        private Operator(String op) {
            this.op = op;
        }

        @Override
        public String toString() { return op; }
    }

    public enum Scope {
        GET_FIRST("First Only"), GET_ALL("All");

        String scope;
        private Scope(String op) {
            this.scope = op;
        }

        @Override
        public String toString() { return scope; }
    }
}
