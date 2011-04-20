package org.srplib.criteria;

/**
 * A builder simplifying criteria {@link Criteria} creation.
 *
 * TODO: consider removing this class
 *
 * @author Anton Pechinsky
 */
public class CriteriaBuilder {

    private State state;

    public CriteriaBuilder eq(String property, Object value) {
        register(Criteria.eq(property, value));
        return this;
    }

    public CriteriaBuilder ne(String property, Object value) {
        register(Criteria.ne(property, value));
        return this;
    }

    public CriteriaBuilder gt(String property, Object value) {
        register(Criteria.gt(property, value));
        return this;
    }

    public CriteriaBuilder ge(String property, Object value) {
        register(Criteria.ge(property, value));
        return this;
    }

    public CriteriaBuilder ls(String property, Object value) {
        register(Criteria.ls(property, value));
        return this;
    }

    public CriteriaBuilder le(String property, Object value) {
        register(Criteria.le(property, value));
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
