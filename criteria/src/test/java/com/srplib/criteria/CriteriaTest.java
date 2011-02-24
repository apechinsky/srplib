package com.srplib.criteria;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Q-APE
 */
public class CriteriaTest {

    @Test
    public void testCreate() throws Exception {
        Criterion criterion =
            Criteria.and(
                Criteria.not(Criteria.and(
                    Criteria.equals("p1", "v1"),
                    Criteria.lessEquals("p2", "v2"),
                    Criteria.equals("p3", "v3")
                )),
                Criteria.or(
                    Criteria.equals("p1", "v1"),
                    Criteria.less("p2", "v2"),
                    Criteria.greate("p3", "v3")
                ));

        Assert.assertEquals("AND(NOT(AND(p1 = v1,p2 <= v2,p3 = v3)),OR(p1 = v1,p2 < v2,p3 > v3))", criterion.toString());
    }

}
