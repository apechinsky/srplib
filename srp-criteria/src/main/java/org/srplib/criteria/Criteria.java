package org.srplib.criteria;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * A factory class containing static factory methods simplifying creation of {@link Criterion} objects.
 *
 * <p>
 *     Usage example:
 * <pre>
 *  // Let's create Criterion for the following expression:
 *  // NOT (p1 = v1 AND p2 <= v2 AND p3 = v3) AND (p1 = v1 OR p2 < v2 OR p3 > v3)
 *  // the same in Java:
 *  // !(p1 = v1 && p2 <= v2 && p3 = v3) && (p1 = v1 || p2 < v2 || p3 > v3)
 *
 *  Criterion criterion = Criteria.and(
 *      Criteria.not(
 *          Criteria.and(
 *              Criteria.eq("p1", "v1"),
 *              Criteria.le("p2", "v2"),
 *              Criteria.eq("p3", "v3")
 *          )
 *      ),
 *      Criteria.or(
 *          Criteria.eq("p1", "v1"),
 *          Criteria.ls("p2", "v2"),
 *          Criteria.gt("p3", "v3")
 *      )
 *  );
 *
 *  // or even simpler with static imports:
 *
 *  Criterion criterion = and(
 *      not(
 *          and(
 *              eq("p1", "v1"),
 *              le("p2", "v2"),
 *              eq("p3", "v3")
 *          )
 *      ),
 *      or(
 *          eq("p1", "v1"),
 *          ls("p2", "v2"),
 *          gt("p3", "v3")
 *      )
 *  );
 *
 * </pre>
 * </p>
 *
 * @author Anton Pechinsky
 */
public class Criteria {

    /**
     * Creates criterion using explicit operation object.
     *
     * <pre>
     *     Criteria.create("name", Operation.EQUALS, "Mike");
     * </pre>
     *
     * @param property String property name
     * @param operation Operation a relationship between property and value
     * @param value Object property value.
     * @return Criterion a criterion
     */
    public static Criterion create(String property, Operation operation, Object value) {
        return new SimpleCriterion(property, operation, value);
    }

    /**
     * Equals criterion.
     *
     * <p>Operation: {@link Operation#EQUALS}</p>
     *
     * @param property String property name
     * @param value Object property value.
     * @return Criterion strict equality
     */
    public static Criterion eq(String property, Object value) {
        return create(property, Operation.EQUALS, value);
    }

    /**
     * Not eq criterion.
     *
     * @param property String property name
     * @param value Object property value.
     * @return Criterion not eq
     */
    public static Criterion ne(String property, Object value) {
        return not(eq(property, value));
    }

    /**
     * Greater criterion.
     *
     * <p>Operation: {@link Operation#GREATER}</p>
     *
     * @param property String property name
     * @param value Object property value.
     * @return Criterion greater
     */
    public static Criterion gt(String property, Object value) {
        return create(property, Operation.GREATER, value);
    }

    /**
     * Greater or eq criterion.
     *
     * <p>Operation: {@link Operation#GREATER_EQUALS}</p>
     *
     * @param property String property name
     * @param value Object property value.
     * @return Criterion greater or eq.
     */
    public static Criterion ge(String property, Object value) {
        return create(property, Operation.GREATER_EQUALS, value);
    }

    /**
     * Less or eq criterion.
     *
     * <p>Operation: {@link Operation#LESS}</p>
     *
     * @param property String property name
     * @param value Object property value.
     * @return Criterion ls.
     */
    public static Criterion ls(String property, Object value) {
        return create(property, Operation.LESS, value);
    }

    /**
     * Less or eq criterion.
     *
     * <p>Operation: {@link Operation#LESS_EQUALS}</p>
     *
     * @param property String property name
     * @param value Object property value.
     * @return Criterion ls or eq.
     */
    public static Criterion le(String property, Object value) {
        return create(property, Operation.LESS_EQUALS, value);
    }

    /**
     * Adds not to specified criterion.
     *
     * <p>Operation: {@link JunctionType#NOT}</p>
     *
     * @param criterion Criterion source criterion
     * @return Criterion not criterion
     */
    public static Criterion not(Criterion criterion) {
        return new Junction(JunctionType.NOT, Collections.singletonList(criterion));
    }

    /**
     * Joins specified criteria using {@link JunctionType#AND} operator.
     *
     * @param criteria List a list of Criterion to join
     * @return Criterion joined criteria
     */
    public static Criterion and(List<Criterion> criteria) {
        return new Junction(JunctionType.AND, criteria);
    }

    /**
     * Joins specified criteria using {@link JunctionType#AND} operator.
     *
     * @param criteria vararg Criterion parameter
     * @return Criterion joined criteria
     */
    public static Criterion and(Criterion... criteria) {
        return and(Arrays.asList(criteria));
    }

    /**
     * Joins specified criteria using {@link JunctionType#OR} operator.
     *
     * @param criteria List a list of Criterion to join
     * @return Criterion joined criteria
     */
    public static Criterion or(List<Criterion> criteria) {
        return new Junction(JunctionType.OR, criteria);
    }

    /**
     * Joins specified criteria using {@link JunctionType#OR} operator.
     *
     * @param criteria vararg Criterion parameter
     * @return Criterion joined criteria
     */
    public static Criterion or(Criterion... criteria) {
        return or(Arrays.asList(criteria));
    }

}
