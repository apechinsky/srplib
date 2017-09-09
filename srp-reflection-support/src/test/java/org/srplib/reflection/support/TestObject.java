package org.srplib.reflection.support;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author Q-APE
 * @author Q-ONA
 */
public class TestObject {

    private boolean booleanField;
    private byte byteField;
    private short shortField;
    private char charField;
    private int intField;
    private long longField;
    private float floatField;
    private double doubleField;

    private boolean[] booleanFieldArray;
    private byte[] byteFieldArray;
    private short[] shortFieldArray;
    private char[] charFieldArray;
    private int[] intFieldArray;
    private long[] longFieldArray;
    private float[] floatFieldArray;
    private double[] doubleFieldArray;

    private String stringField;
    private String[] stringFieldArray;

    private Date dateField;
    private Date[] dateFieldArray;

    private Object objectField;
    private Object[] objectFieldArray;

    private int[] intFieldArrayNull;
    private Integer intFieldWrapperNull;
    private String stringFieldNull;
    private String[] stringFieldArrayNull;
    private Date dateFieldNull;
    private Date[] dateFieldArrayNull;

    private TestEnum enumField;

    private TestEnum[] enumFieldArray;

    private List listField;

    private Map mapField;

    public boolean isBooleanField() {
        return booleanField;
    }

    public void setBooleanField(boolean booleanField) {
        this.booleanField = booleanField;
    }

    public byte getByteField() {
        return byteField;
    }

    public void setByteField(byte byteField) {
        this.byteField = byteField;
    }

    public short getShortField() {
        return shortField;
    }

    public void setShortField(short shortField) {
        this.shortField = shortField;
    }

    public char getCharField() {
        return charField;
    }

    public void setCharField(char charField) {
        this.charField = charField;
    }

    public int getIntField() {
        return intField;
    }

    public void setIntField(int intField) {
        this.intField = intField;
    }

    public long getLongField() {
        return longField;
    }

    public void setLongField(long longField) {
        this.longField = longField;
    }

    public float getFloatField() {
        return floatField;
    }

    public void setFloatField(float floatField) {
        this.floatField = floatField;
    }

    public double getDoubleField() {
        return doubleField;
    }

    public void setDoubleField(double doubleField) {
        this.doubleField = doubleField;
    }

    public boolean[] getBooleanFieldArray() {
        return booleanFieldArray;
    }

    public void setBooleanFieldArray(boolean[] booleanFieldArray) {
        this.booleanFieldArray = booleanFieldArray;
    }

    public byte[] getByteFieldArray() {
        return byteFieldArray;
    }

    public void setByteFieldArray(byte[] byteFieldArray) {
        this.byteFieldArray = byteFieldArray;
    }

    public short[] getShortFieldArray() {
        return shortFieldArray;
    }

    public void setShortFieldArray(short[] shortFieldArray) {
        this.shortFieldArray = shortFieldArray;
    }

    public char[] getCharFieldArray() {
        return charFieldArray;
    }

    public void setCharFieldArray(char[] charFieldArray) {
        this.charFieldArray = charFieldArray;
    }

    public int[] getIntFieldArray() {
        return intFieldArray;
    }

    public void setIntFieldArray(int[] intFieldArray) {
        this.intFieldArray = intFieldArray;
    }

    public long[] getLongFieldArray() {
        return longFieldArray;
    }

    public void setLongFieldArray(long[] longFieldArray) {
        this.longFieldArray = longFieldArray;
    }

    public float[] getFloatFieldArray() {
        return floatFieldArray;
    }

    public void setFloatFieldArray(float[] floatFieldArray) {
        this.floatFieldArray = floatFieldArray;
    }

    public double[] getDoubleFieldArray() {
        return doubleFieldArray;
    }

    public void setDoubleFieldArray(double[] doubleFieldArray) {
        this.doubleFieldArray = doubleFieldArray;
    }

    public String getStringField() {
        return stringField;
    }

    public void setStringField(String stringField) {
        this.stringField = stringField;
    }

    public String[] getStringFieldArray() {
        return stringFieldArray;
    }

    public void setStringFieldArray(String[] stringFieldArray) {
        this.stringFieldArray = stringFieldArray;
    }

    public Object getObjectField() {
        return objectField;
    }

    public void setObjectField(Object objectField) {
        this.objectField = objectField;
    }

    public Object[] getObjectFieldArray() {
        return objectFieldArray;
    }

    public void setObjectFieldArray(Object[] objectFieldArray) {
        this.objectFieldArray = objectFieldArray;
    }

    public int[] getIntFieldArrayNull() {
        return intFieldArrayNull;
    }

    public void setIntFieldArrayNull(int[] intFieldArrayNull) {
        this.intFieldArrayNull = intFieldArrayNull;
    }

    public Integer getIntFieldWrapperNull() {
        return intFieldWrapperNull;
    }

    public void setIntFieldWrapperNull(Integer intFieldWrapperNull) {
        this.intFieldWrapperNull = intFieldWrapperNull;
    }

    public String getStringFieldNull() {
        return stringFieldNull;
    }

    public void setStringFieldNull(String stringFieldNull) {
        this.stringFieldNull = stringFieldNull;
    }

    public String[] getStringFieldArrayNull() {
        return stringFieldArrayNull;
    }

    public void setStringFieldArrayNull(String[] stringFieldArrayNull) {
        this.stringFieldArrayNull = stringFieldArrayNull;
    }

    public TestEnum getEnumField() {
        return enumField;
    }

    public void setEnumField(TestEnum enumField) {
        this.enumField = enumField;
    }

    public TestEnum[] getEnumFieldArray() {
        return enumFieldArray;
    }

    public void setEnumFieldArray(TestEnum[] enumFieldArray) {
        this.enumFieldArray = enumFieldArray;
    }

    public Date getDateField() {
        return dateField;
    }

    public void setDateField(Date dateField) {
        this.dateField = dateField;
    }

    public Date[] getDateFieldArray() {
        return dateFieldArray;
    }

    public void setDateFieldArray(Date[] dateFieldArray) {
        this.dateFieldArray = dateFieldArray;
    }

    public Date getDateFieldNull() {
        return dateFieldNull;
    }

    public void setDateFieldNull(Date dateFieldNull) {
        this.dateFieldNull = dateFieldNull;
    }

    public Date[] getDateFieldArrayNull() {
        return dateFieldArrayNull;
    }

    public void setDateFieldArrayNull(Date[] dateFieldArrayNull) {
        this.dateFieldArrayNull = dateFieldArrayNull;
    }
}