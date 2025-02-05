/**
 * Provides the core classes and interfaces for configuring and interacting
 * with the Recorpoint SDK service layer.
 * <p>
 * This package contains essential components such as:
 * </p>
 * <ul>
 *   <li>{@link com.recordpoint.connectors.sdk.service.ServiceSettings} - Encapsulates
 *       the configuration settings required for the SDK, including authentication details
 *       and service endpoints.</li>
 *   <li>{@link com.recordpoint.connectors.sdk.service.ServicePayload} - A marker interface
 *       for defining request payloads to be used in service operations.</li>
 *   <li>{@link com.recordpoint.connectors.sdk.service.ServiceResponse} - A marker interface
 *       for defining responses returned from service operations.</li>
 *   <li>{@link com.recordpoint.connectors.sdk.service.AbstractServiceRequest} - A base class
 *       for constructing service requests, supporting customizable request builders.</li>
 *   <li>{@link com.recordpoint.connectors.sdk.service.AbstractServiceClient} - An abstract
 *       client that provides core functionalities for executing HTTP requests and
 *       handling service responses in the SDK.</li>
 * </ul>
 * <p>
 * Together, these classes and interfaces provide a framework for integrating with the
 * Recorpoint platform, enabling secure and efficient service communication.
 * </p>
 */
package com.recordpoint.connectors.sdk.service;