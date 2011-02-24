package com.srplib.criteria;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Contains static factory methods simplifying creation of {@link Criterion}.
 *
 * @author Q-APE
 */
public class Criteria {

    public static Criterion create(String property, Operation operation, Object value) {
        return new SimpleCriterion(property, operation, value);
    }

    public static Criterion equals(String property, Object value) {
        return create(property, Operation.EQUALS, value);
    }

    public static Criterion notEquals(String property, Object value) {
        return not(equals(property, value));
    }

    public static Criterion greate(String property, Object value) {
        return create(property, Operation.GREATER, value);
    }

    public static Criterion greateEquals(String property, Object value) {
        return create(property, Operation.GREATER_EQUALS, value);
    }

    public static Criterion less(String property, Object value) {
        return create(property, Operation.LESS, value);
    }

    public static Criterion lessEquals(String property, Object value) {
        return create(property, Operation.LESS_EQUALS, value);
    }

    public static Criterion not(Criterion criterion) {
        return new Junction(JunctionType.NOT, Collections.singletonList(criterion));
    }

    public static Criterion and(List<Criterion> criteria) {
        return new Junction(JunctionType.AND, criteria);
    }

    public static Criterion and(Criterion... criteria) {
        return and(Arrays.asList(criteria));
    }

    public static Criterion or(List<Criterion> criteria) {
        return new Junction(JunctionType.OR, criteria);
    }

    public static Criterion or(Criterion... criteria) {
        return or(Arrays.asList(criteria));
    }

}
