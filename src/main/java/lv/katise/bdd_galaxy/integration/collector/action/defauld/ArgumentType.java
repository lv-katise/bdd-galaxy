package lv.katise.bdd_galaxy.integration.collector.action.defauld;

import io.cucumber.core.internal.com.fasterxml.jackson.annotation.JsonCreator;
import io.cucumber.core.internal.com.fasterxml.jackson.annotation.JsonValue;

public enum ArgumentType {

    STRING("STRING"),
    BOOLEAN("BOOLEAN"),
    INTEGER("INTEGER"),
    DOUBLE("DOUBLE"),
    DATE_TIME("DATE_TIME");

    private final String value;

    ArgumentType(String value) {
        this.value = value;
    }

    @Override
    @JsonValue
    public String toString() {
        return String.valueOf(value);
    }

    @JsonCreator
    public static ArgumentType fromValue(String text) {
        for (ArgumentType b : ArgumentType.values()) {
            if (String.valueOf(b.value).equals(text)) {
                return b;
            }
        }
        return null;
    }
}