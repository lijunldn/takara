package me.takara.client.query;

import me.takara.shared.Instrument;
import me.takara.shared.entities.fields.BondFields;
import me.takara.shared.rest.SearchCriteria;

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
        return this;
    }

    public List<Instrument> getResultList() {
        criteria.setScope(SearchCriteria.Scope.GET_FIRST);
        return callback.apply(criteria);
    }

}
