package me.takara.client.query;

import me.takara.core.Instrument;
import me.takara.core.entities.fields.BondFields;
import me.takara.core.rest.SearchCriteria;

import java.util.List;
import java.util.function.Function;

/**
 * Inspired by CriteriaBuilder
 * @param <T>
 */
public class CriteriaBuilder<T> {

    private Function<SearchCriteria, List<Instrument>> callback;
    private SearchCriteria criteria = new SearchCriteria();

    public CriteriaBuilder(Function<SearchCriteria, List<Instrument>> call) {
        callback = call;
    }

    public CriteriaBuilder equal(BondFields fields, T v) {

        criteria.setL_value(fields.toString());
        criteria.setR_value(v.toString());
        criteria.setOperator(SearchCriteria.Operator.EQ);
        return this;
    }

    public CriteriaBuilder greaterThan(BondFields fields, T v) {
        return this;
    }

    public CriteriaBuilder lessThan(BondFields fields, T v) {
        criteria.setL_value(fields.toString());
        criteria.setR_value(v.toString());
        criteria.setOperator(SearchCriteria.Operator.LT);
        return this;
    }

    public List<Instrument> fetchFirstOnly() {
        criteria.setScope(SearchCriteria.Scope.GET_FIRST);
        return callback.apply(criteria);
    }
    public List<Instrument> fetchAll() {
        criteria.setScope(SearchCriteria.Scope.GET_ALL);
        return callback.apply(criteria);
    }
}
