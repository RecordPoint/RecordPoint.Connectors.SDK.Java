package com.recordpoint.connectors.sdk.service.model;

import com.recordpoint.connectors.sdk.service.item.model.ItemSubmission;
import com.recordpoint.connectors.sdk.service.util.MessageFieldProvider;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class OptionalMetadata {
    private static final String NULL_STRING = null;
    private static final String EMPTY_STRING = "";

    private enum NullFieldHandling {
        EMPTY_FIELD, IGNORE_FIELD, MANDATORY_FIELD;
    }

    private final Metadata metadata;
    private final NullFieldHandling fieldHandling;

    private OptionalMetadata(Metadata metadata, NullFieldHandling fieldHandling) {
        this.metadata = metadata;
        this.fieldHandling = fieldHandling;
    }

    public Optional<Metadata> getMetadata() {
        if (metadata.getValue() == null) {
            if (fieldHandling == NullFieldHandling.MANDATORY_FIELD) {
                throw new RuntimeException(String.format(
                        MessageFieldProvider.getMessage("error.optionalmetadata.mandatory"),
                        metadata.getName()
                ));
            } else if (fieldHandling == NullFieldHandling.IGNORE_FIELD) {
                return Optional.empty();
            } else {
                return Optional.of(Metadata.of(
                        metadata.getName(), EMPTY_STRING
                ));
            }
        } else {
            return Optional.of(metadata);
        }
    }

    public static List<Metadata> makeSourceProperties(List<OptionalMetadata>  sourceProperties) {
        return sourceProperties.stream()
                .map(OptionalMetadata::getMetadata)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toList());
    }

    public static Builder Builder() {
        return new Builder();
    }

    public static class Builder {
        private Metadata metadata;

        public Builder of(String name, String value) {
            this.metadata = Metadata.of(name, value);
            return this;
        }

        public Builder of(String name, Integer value) {
            if (value == null) {
                this.metadata = Metadata.of(name, NULL_STRING);
            } else {
                this.metadata = Metadata.of(name, value);
            }
            return this;
        }

        public Builder of(String name, Double value) {
            if (value == null) {
                this.metadata = Metadata.of(name, NULL_STRING);
            } else {
                this.metadata = Metadata.of(name, value);
            }
            return this;
        }

        public Builder of(String name, Boolean value) {
            if (value == null) {
                this.metadata = Metadata.of(name, NULL_STRING);
            } else {
                this.metadata = Metadata.of(name, value);
            }
            return this;
        }

        public Builder of(String name, Instant value) {
            if (value == null) {
                this.metadata = Metadata.of(name, NULL_STRING);
            } else {
                this.metadata = Metadata.of(name, value);
            }
            return this;
        }

        /**
         * Build the OptionalMetadata element, excluding it from the output if the
         * value is null (i.e., the field will not be sent to RecordPoint if null)
         *
         * @return OptionalMetadata the built metadata element
         */
        public OptionalMetadata orIgnore() {
            return new OptionalMetadata(metadata, NullFieldHandling.IGNORE_FIELD);
        }

        /**
         * Build the OptionalMetadata element, including it as a blank string if
         * the value is null (i.e., the field will be blank in RecordPoint if null)
         *
         * @return OptionalMetadata the built metadata element
         */
        public OptionalMetadata orEmpty() {
            return new OptionalMetadata(metadata, NullFieldHandling.EMPTY_FIELD);
        }

        /**
         * Build the OptionalMetadata element, throwing an error if the value is null
         * when getMetadata(), or makeSourceProperties(), is called
         *
         * @return OptionalMetadata the built metadata element
         */
        public OptionalMetadata orThrow() {
            return new OptionalMetadata(metadata, NullFieldHandling.MANDATORY_FIELD);
        }
    }
}
