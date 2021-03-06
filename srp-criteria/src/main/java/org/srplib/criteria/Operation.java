package org.srplib.criteria;

/**
 * Models name value(s) relationship operation.
 *
 * @author Anton Pechinsky
 */
public enum Operation {

    EQUALS("="),

    CONTAINS("()"),

    GREATER(">"),

    GREATER_EQUALS(">="),

    LESS("<"),

    LESS_EQUALS("<="),

    LIKE("like");

    private String symbol;

    Operation(String symbol) {
        this.symbol = symbol;
    }

    /**
     * Returns symbol for operation. Symbol is a simple mnemonic code and should not be used for statement generation.
     *
     * @return String representing operation mnemonic code.
     */
    public String getSymbol() {
        return symbol;
    }
}
