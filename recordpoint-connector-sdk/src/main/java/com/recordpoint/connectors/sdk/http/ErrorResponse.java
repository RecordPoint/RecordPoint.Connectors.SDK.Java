package com.recordpoint.connectors.sdk.http;

import java.io.Serializable;

/**
 * Exception thrown when an error status code is detected in an HTTP response.
 *
 * @author Pedro Moran
 * @version 1.0.0
 */
public final class ErrorResponse implements Serializable {

    /**
     * The error details associated with this response.
     */
    private Error error;

    /**
     * Default constructor for ErrorResponse.
     */
    public ErrorResponse() {
    }

    /**
     * Retrieves the error details.
     *
     * @return the {@link Error} object containing error information.
     */
    public Error getError() {
        return error;
    }

    /**
     * Sets the error details.
     *
     * @param error the {@link Error} object containing error information.
     */
    public void setError(Error error) {
        this.error = error;
    }

    /**
     * Represents detailed information about an error.
     */
    public static class Error {

        /**
         * The type of the error, represented as an integer.
         */

        private Integer type;

        /**
         * The message describing the error.
         */
        private String message;

        /**
         * A code identifying the specific error.
         */
        private String messageCode;

        /**
         * The severity of the error, represented as an integer.
         */
        private Integer severity;

        /**
         * The date and time when the error occurred, as a string.
         */
        private String dateTime;

        /**
         * The target of the error, such as a field, property, or service endpoint.
         */
        private String target;

        /**
         * Additional details about the error, such as a nested error object.
         */
        private Object innerError;

        /**
         * Aggregated errors, often used for cases involving multiple issues.
         */
        private Object aggregateErrors;

        /**
         * Default constructor for Error.
         */
        public Error() {
        }

        /**
         * Retrieves the error type.
         *
         * @return the type of the error.
         */
        public Integer getType() {
            return type;
        }

        /**
         * Sets the error type.
         *
         * @param type the type of the error.
         */
        public void setType(Integer type) {
            this.type = type;
        }

        /**
         * Retrieves the error message.
         *
         * @return the error message.
         */
        public String getMessage() {
            return message;
        }

        /**
         * Sets the error message.
         *
         * @param message the error message.
         */
        public void setMessage(String message) {
            this.message = message;
        }

        /**
         * Retrieves the error message code.
         *
         * @return the error message code.
         */
        public String getMessageCode() {
            return messageCode;
        }

        /**
         * Sets the error message code.
         *
         * @param messageCode the error message code.
         */
        public void setMessageCode(String messageCode) {
            this.messageCode = messageCode;
        }

        /**
         * Retrieves the error severity.
         *
         * @return the severity of the error.
         */
        public Integer getSeverity() {
            return severity;
        }

        /**
         * Sets the error severity.
         *
         * @param severity the severity of the error.
         */
        public void setSeverity(Integer severity) {
            this.severity = severity;
        }

        /**
         * Retrieves the date and time when the error occurred.
         *
         * @return the date and time of the error occurrence.
         */
        public String getDateTime() {
            return dateTime;
        }

        /**
         * Sets the date and time of the error occurrence.
         *
         * @param dateTime the date and time when the error occurred.
         */
        public void setDateTime(String dateTime) {
            this.dateTime = dateTime;
        }

        /**
         * Retrieves the target of the error.
         *
         * @return the target of the error.
         */
        public String getTarget() {
            return target;
        }

        /**
         * Sets the target of the error.
         *
         * @param target the target of the error.
         */
        public void setTarget(String target) {
            this.target = target;
        }

        /**
         * Retrieves the inner error details.
         *
         * @return the inner error object.
         */
        public Object getInnerError() {
            return innerError;
        }

        /**
         * Sets additional inner error details.
         *
         * @param innerError the inner error object.
         */
        public void setInnerError(Object innerError) {
            this.innerError = innerError;
        }

        /**
         * Retrieves aggregated error details.
         *
         * @return the aggregated errors object.
         */
        public Object getAggregateErrors() {
            return aggregateErrors;
        }

        /**
         * Sets aggregated error details.
         *
         * @param aggregateErrors the aggregated errors object.
         */
        public void setAggregateErrors(Object aggregateErrors) {
            this.aggregateErrors = aggregateErrors;
        }
    }

}
