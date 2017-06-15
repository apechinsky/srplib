package org.srplib;

import org.junit.Assert;
import org.junit.Test;
import org.srplib.criteria.Criteria;
import org.srplib.criteria.Criterion;

import static org.srplib.criteria.Criteria.and;
import static org.srplib.criteria.Criteria.eq;
import static org.srplib.criteria.Criteria.gt;
import static org.srplib.criteria.Criteria.le;
import static org.srplib.criteria.Criteria.ls;
import static org.srplib.criteria.Criteria.not;
import static org.srplib.criteria.Criteria.or;

/**
 * @author Anton Pechinsky
 */
public class CriteriaTest {

    @Test
    public void testCreate() throws Exception {
        Criterion criterion =
            Criteria.and(
                Criteria.not(Criteria.and(
                    Criteria.eq("p1", "v1"),
                    Criteria.le("p2", "v2"),
                    Criteria.eq("p3", "v3")
                )),
                Criteria.or(
                    Criteria.eq("p1", "v1"),
                    Criteria.ls("p2", "v2"),
                    Criteria.gt("p3", "v3")
                ));

        Assert.assertEquals("AND(NOT(AND(p1 = v1,p2 <= v2,p3 = v3)),OR(p1 = v1,p2 < v2,p3 > v3))", criterion.toString());
    }

    @Test
    public void testCreate2() throws Exception {
        Criterion criterion = and(
            not(
                and(
                    eq("p1", "v1"),
                    le("p2", "v2"),
                    eq("p3", "v3")
                )
            ),
            or(
                eq("p1", "v1"),
                ls("p2", "v2"),
                gt("p3", "v3")
            )
        );

        Assert.assertEquals("AND(NOT(AND(p1 = v1,p2 <= v2,p3 = v3)),OR(p1 = v1,p2 < v2,p3 > v3))", criterion.toString());
    }

}
