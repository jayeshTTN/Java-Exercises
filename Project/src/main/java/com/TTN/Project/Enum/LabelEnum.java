package com.TTN.Project.Enum;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import java.util.Arrays;
import java.util.stream.Stream;

public enum LabelEnum {
    HOME("HOME"),OFFICE("OFFICE");

    private String code;

    private LabelEnum(String code) {
        this.code=code;
    }

    @JsonCreator
    public static LabelEnum decode(final String code) {
        for (LabelEnum category : values()) {
            if (category.code.equalsIgnoreCase(code)) {
                return category;
            }
        }
        throw new IllegalArgumentException(
                "Unknown enum type " + code + ", Allowed values are " + Arrays.toString(values()));
    }

    @JsonValue
    public String getCode() {
        return code;
    }

}
