package com.recordpoint.connectors.sdk.service;

import java.io.Serializable;

/**
 * Marker interface for service payloads used in {@link com.recordpoint.connectors.sdk.http.HttpRequest} requests.
 * <p>
 * Classes implementing this interface represent the payload of a request
 * sent to a service. These payloads are serialized and included in the
 * body of HTTP requests, such as POST or PUT operations.
 * </p>
 *
 * <p>
 * Implementations of this interface must ensure that all fields are
 * serializable to support seamless transmission over the network.
 * </p>
 *
 * @see java.io.Serializable
 * @see AbstractServiceClient#postRequest(String, ServicePayload, Class)
 * @see AbstractServiceClient#putRequest(String, ServicePayload, Class)
 */
public interface ServicePayload extends Serializable {
}
