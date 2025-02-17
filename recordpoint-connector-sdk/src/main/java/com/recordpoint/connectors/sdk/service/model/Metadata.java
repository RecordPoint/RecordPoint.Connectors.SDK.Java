package com.recordpoint.connectors.sdk.service.model;

import java.time.Instant;
import java.util.StringJoiner;

public final class Metadata {
    private String name;
    private String type;
    private String value;
    private Boolean isSysAdminOnly;

    public Metadata() {
    }

    public Metadata(String name, String type, String value, Boolean isSysAdminOnly) {
        this.name = name;
        this.type = type;
        this.value = value;
        this.isSysAdminOnly = isSysAdminOnly;
    }

    public static Builder Builder() {
        return new Builder();
    }

    public static Metadata of(String name, String value) {
        return new Metadata(name, "String", value, null);
    }

    public static Metadata of(String name, Integer value) {
        return new Metadata(name, "Number", value.toString(), null);
    }

    public static Metadata of(String name, Double value) {
        return new Metadata(name, "Double", value.toString(), null);
    }

    public static Metadata of(String name, Boolean value) {
        return new Metadata(name, "Boolean", value.toString(), null);
    }

    public static Metadata of(String name, Instant value) {
        return new Metadata(name, "DateTime", value.toString(), null);
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public String getValue() {
        return value;
    }

    public Boolean getSysAdminOnly() {
        return isSysAdminOnly;
    }

    public static class Builder {
        private String name;
        private String type;
        private String value;
        private Boolean isSysAdminOnly;

        public Builder setName(String name) {
            this.name = name;
            return this;
        }

        public Builder setType(String type) {
            this.type = type;
            return this;
        }

        public Builder setValue(String value) {
            this.value = value;
            return this;
        }

        public Builder setSysAdmin(Boolean sysAdmin) {
            this.isSysAdminOnly = sysAdmin;
            return this;
        }

        public Metadata build() {
            return new Metadata(this.name, this.type, this.value, this.isSysAdminOnly);
        }
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Metadata.class.getSimpleName() + "[", "]")
                .add("name='" + name + "'")
                .add("type='" + type + "'")
                .add("value='" + value + "'")
                .toString();
    }
}
