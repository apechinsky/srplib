package org.srplib.criteria;

/**
 * @author Q-APE
 */
public class CriteriaBuilder {

    private State state;

    public CriteriaBuilder equals(String property, Object value) {
        register(Criteria.equals(property, value));
        return this;
    }

    public CriteriaBuilder notEquals(String property, Object value) {
        register(Criteria.notEquals(property, value));
        return this;
    }

    public CriteriaBuilder greate(String property, Object value) {
        register(Criteria.greate(property, value));
        return this;
    }

    public CriteriaBuilder greateEquals(String property, Object value) {
        register(Criteria.greateEquals(property, value));
        return this;
    }

    public CriteriaBuilder less(String property, Object value) {
        register(Criteria.less(property, value));
        return this;
    }

    public CriteriaBuilder lessEquals(String property, Object value) {
        register(Criteria.lessEquals(property, value));
        return this;
    }

    public CriteriaBuilder not() {
        return this;
    }

    private void register(Criterion criterion) {
        state = state.register(criterion);
    }
    
    private void register(Junction criterion) {
        state = state.register(criterion);
    }


    private abstract class State {

        private Criterion criterion;

        protected State(Criterion criterion) {
            this.criterion = criterion;
        }

        public Criterion getCriterion() {
            return criterion;
        }

        abstract State register(Criterion criterion);

        abstract State register(Junction junction);
    }

    private class CriterionState extends State {

        private CriterionState(Criterion criterion) {
            super(criterion);
        }

        @Override
        public State register(Criterion criterion) {
            throw new IllegalStateException("Invalid usage of API. Multiple criteria should be joined.");
        }

        @Override
        State register(Junction junction) {
            return null;
        }
    }

    private class NotState extends State {

        private NotState(Criterion criterion) {
            super(criterion);
        }

        public State register(Criterion criterion) {
            return new CriterionState(Criteria.not(getCriterion()));
        }

        @Override
        State register(Junction junction) {
            return null;
        }
    }


    
}
