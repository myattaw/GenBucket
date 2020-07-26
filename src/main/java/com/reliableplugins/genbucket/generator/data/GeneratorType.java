package com.reliableplugins.genbucket.generator.data;

public enum GeneratorType {

    VERTICAL("Vertical"), HORIZONTAL("Horizontal"), PATCH("Patch");

    private String name;

    GeneratorType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
