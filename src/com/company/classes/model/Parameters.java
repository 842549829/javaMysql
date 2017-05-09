package com.company.classes.model;

public class Parameters {
    private ParametersType type;
    private Object value;

    public Parameters(ParametersType type, Object value) {
        this.setType(type);
        this.setValue(value);
    }

    public void setType(ParametersType type) {
        this.type = type;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public ParametersType getType() {
        return this.type;
    }

    public Object getValue() {
        return this.value;
    }
}
