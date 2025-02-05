package com.recordpoint.connectors.sdk.service;

import java.io.Serializable;

/**
 * Marker interface for service responses returned from {@link com.recordpoint.connectors.sdk.http.HttpRequest} requests.
 * <p>
 * Classes implementing this interface represent the response data
 * received from a service. These responses are deserialized from
 * the body of {@link com.recordpoint.connectors.sdk.http.HttpResponse} responses, typically in JSON format.
 * </p>
 *
 * <p>
 * Implementations of this interface must ensure that all fields are
 * serializable to support efficient processing and caching.
 * </p>
 *
 * @see java.io.Serializable
 * @see AbstractServiceClient#getRequest(String, Class)
 * @see AbstractServiceClient#getRequestList(String, Class)
 * @see AbstractServiceClient#postRequest(String, ServicePayload, Class)
 * @see AbstractServiceClient#putRequest(String, ServicePayload, Class)
 */
public interface ServiceResponse extends Serializable {
}
