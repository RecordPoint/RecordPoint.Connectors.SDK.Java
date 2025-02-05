package com.recordpoint.connectors.sdk.service;

/**
 * Represents an abstract service request that defines the structure for sending requests
 * and receiving responses in a service client.
 * <p>
 * This class serves as a base for specific request types, where the response type
 * must extend {@link ServiceResponse}.
 * </p>
 *
 * @param <T> the type of the response, which must extend {@link ServiceResponse}.
 */
public abstract class AbstractServiceRequest<T extends ServiceResponse> {

    /**
     * The response associated with this request.
     * <p>
     * This field holds the deserialized response from the service after the request is executed.
     * </p>
     */
    protected T response;

    /**
     * Default constructor for {@code AbstractServiceRequest}.
     * <p>
     * Provides a no-argument constructor for subclasses.
     * </p>
     */
    protected AbstractServiceRequest() {
    }

    /**
     * Builder class for constructing instances of {@link AbstractServiceRequest}.
     * <p>
     * Subclasses of this builder should implement the {@link #build()} method to create
     * specific request types.
     * </p>
     */
    public static abstract class Builder {

        /**
         * Builds and returns an instance of a specific {@link AbstractServiceRequest}.
         *
         * @param <P> the type of the request being built, which extends {@link AbstractServiceRequest}.
         * @return an instance of the specific {@link AbstractServiceRequest}.
         */
        public abstract <P extends AbstractServiceRequest<?>> P build();
    }
}
