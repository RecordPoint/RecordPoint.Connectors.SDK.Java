package com.recordpoint.connectors.sdk.service.model;

import com.google.common.base.Preconditions;

import java.util.Objects;

public final class RelationshipData {

    private final String relatedItemNumber;
    private final String relationshipType;

    public RelationshipData(String relatedItemNumber, String relationshipType) {
        Preconditions.checkArgument(Objects.nonNull(relatedItemNumber));
        Preconditions.checkArgument(Objects.nonNull(relationshipType));
        this.relatedItemNumber = relatedItemNumber;
        this.relationshipType = relationshipType;
    }

    public String getRelatedItemNumber() {
        return relatedItemNumber;
    }

    public String getRelationshipType() {
        return relationshipType;
    }
}
