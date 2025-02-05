package com.recordpoint.connectors.sdk.service.notification.model;

import com.recordpoint.connectors.sdk.service.ServiceResponse;

import java.util.List;

public class NotificationFilter implements ServiceResponse {

    private FilterType included;

    private FilterType excluded;

    private String includedExpression;

    private String excludedExpression;

    public FilterType getIncluded() {
        return included;
    }

    public void setIncluded(FilterType included) {
        this.included = included;
    }

    public FilterType getExcluded() {
        return excluded;
    }

    public void setExcluded(FilterType excluded) {
        this.excluded = excluded;
    }

    public String getIncludedExpression() {
        return includedExpression;
    }

    public void setIncludedExpression(String includedExpression) {
        this.includedExpression = includedExpression;
    }

    public String getExcludedExpression() {
        return excludedExpression;
    }

    public void setExcludedExpression(String excludedExpression) {
        this.excludedExpression = excludedExpression;
    }

    public static class FilterType {
        private String boolOperator;

        private List<String> children;

        private FilterSearchTerm searchTerm;

        public String getBoolOperator() {
            return boolOperator;
        }

        public void setBoolOperator(String boolOperator) {
            this.boolOperator = boolOperator;
        }

        public List<String> getChildren() {
            return children;
        }

        public void setChildren(List<String> children) {
            this.children = children;
        }

        public FilterSearchTerm getSearchTerm() {
            return searchTerm;
        }

        public void setSearchTerm(FilterSearchTerm searchTerm) {
            this.searchTerm = searchTerm;
        }
    }

    public static class FilterSearchTerm {
        private String fieldName;

        private String fieldType;

        private String operator;

        private String fieldValue;

        private String categoricalValueType;

        private String searchContextIdentifier;

        public String getFieldName() {
            return fieldName;
        }

        public void setFieldName(String fieldName) {
            this.fieldName = fieldName;
        }

        public String getFieldType() {
            return fieldType;
        }

        public void setFieldType(String fieldType) {
            this.fieldType = fieldType;
        }

        public String getOperator() {
            return operator;
        }

        public void setOperator(String operator) {
            this.operator = operator;
        }

        public String getFieldValue() {
            return fieldValue;
        }

        public void setFieldValue(String fieldValue) {
            this.fieldValue = fieldValue;
        }

        public String getCategoricalValueType() {
            return categoricalValueType;
        }

        public void setCategoricalValueType(String categoricalValueType) {
            this.categoricalValueType = categoricalValueType;
        }

        public String getSearchContextIdentifier() {
            return searchContextIdentifier;
        }

        public void setSearchContextIdentifier(String searchContextIdentifier) {
            this.searchContextIdentifier = searchContextIdentifier;
        }
    }

}
