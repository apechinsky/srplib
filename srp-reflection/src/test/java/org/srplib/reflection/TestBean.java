package org.srplib.reflection;

/**
 *
 * @author Q-APE
 */
public class TestBean {

    private String param1;

    private String param2;

    private int param3;

    public TestBean() {
    }

    public TestBean(String exceptionMessage, boolean runtime) throws TestBeanException {
        throwExceptionStatic(exceptionMessage, runtime);
    }

    public TestBean(String param1, String param2, int param3) {
        this.param1 = param1;
        this.param2 = param2;
        this.param3 = param3;
    }

    public String getParam1() {
        return param1;
    }

    public void setParam1(String param1) {
        this.param1 = param1;
    }

    public String getParam2() {
        return param2;
    }

    public void setParam2(String param2) {
        this.param2 = param2;
    }

    public int getParam3() {
        return param3;
    }

    public void setParam3(int param3) {
        this.param3 = param3;
    }

    public void throwException(String exceptionMessage, boolean runtime) throws TestBeanException {
        throwExceptionStatic(exceptionMessage, runtime);
    }

    public static void throwExceptionStatic(String exceptionMessage, boolean runtime) throws TestBeanException {
        if (exceptionMessage != null) {

            if (runtime) {
                throw new TestBeanRuntimeException(exceptionMessage);
            }
            else {
                throw new TestBeanException(exceptionMessage);
            }
        }
    }

}