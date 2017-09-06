package org.srplib.reflection.classgraph;

/**
 * Перечисление.
 *
 * @author Q-APE
 */
public enum TestEnum {

    VALUE1("v1"),

    VALUE2("v2");

    private String value;

    TestEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
