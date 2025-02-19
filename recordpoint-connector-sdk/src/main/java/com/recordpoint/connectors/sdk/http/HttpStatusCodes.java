package com.recordpoint.connectors.sdk.http;

/**
 * Utility class for defining HTTP status codes and related methods.
 * Provides constants for common HTTP status codes and a method to determine if a status code indicates success.
 *
 * @author Pedro Moran
 * @version 1.0.0
 */
public final class HttpStatusCodes {

    /**
     * Status code for a successful request.
     */
    public static final int STATUS_CODE_OK = 200;

    /**
     * Status code for a successful request that has been fulfilled to create a new resource.
     */
    public static final int STATUS_CODE_CREATED = 201;

    /**
     * Status code for a successful request that has been received but not yet acted upon.
     */
    public static final int STATUS_CODE_ACCEPTED = 202;

    /**
     * Status code for a successful request with no content information.
     */
    public static final int STATUS_CODE_NO_CONTENT = 204;

    /**
     * Status code for a resource corresponding to any one of a set of representations.
     */
    public static final int STATUS_CODE_MULTIPLE_CHOICES = 300;

    /**
     * Status code for a resource that has permanently moved to a new URI.
     */
    public static final int STATUS_CODE_MOVED_PERMANENTLY = 301;

    /**
     * Status code for a resource that has temporarily moved to a new URI.
     */
    public static final int STATUS_CODE_FOUND = 302;

    /**
     * Status code for a resource that has moved to a new URI and should be retrieved using GET.
     */
    public static final int STATUS_CODE_SEE_OTHER = 303;

    /**
     * Status code for a resource that access is allowed but the document has not been modified.
     */
    public static final int STATUS_CODE_NOT_MODIFIED = 304;

    /**
     * Status code for a resource that has temporarily moved to a new URI.
     */
    public static final int STATUS_CODE_TEMPORARY_REDIRECT = 307;
    /**
     * Status code for a request that could not be understood by the server.
     */
    public static final int STATUS_CODE_BAD_REQUEST = 400;
    /**
     * Status code for a request that requires user authentication.
     */
    public static final int STATUS_CODE_UNAUTHORIZED = 401;
    /**
     * Status code for a server that understood the request, but is refusing to fulfill it.
     */
    public static final int STATUS_CODE_FORBIDDEN = 403;
    /**
     * Status code for a server that has not found anything matching the Request-URI.
     */
    public static final int STATUS_CODE_NOT_FOUND = 404;
    /**
     * Status code for a method specified in the Request-Line is not allowed for the resource
     * identified by the Request-URI.
     */
    public static final int STATUS_CODE_METHOD_NOT_ALLOWED = 405;
    /**
     * Status code for a request that could not be completed due to a resource conflict.
     */
    public static final int STATUS_CODE_CONFLICT = 409;
    /**
     * Status code for a request for which one of the conditions it was made under has failed.
     */
    public static final int STATUS_CODE_PRECONDITION_FAILED = 412;
    /**
     * Status code for a request for which the content-type and the request's syntax were correct but
     * server was not able to process entity.
     */
    public static final int STATUS_CODE_UNPROCESSABLE_ENTITY = 422;
    /**
     * Status code for an internal server error.
     */
    public static final int STATUS_CODE_SERVER_ERROR = 500;
    /**
     * Status code for a bad gateway.
     */
    public static final int STATUS_CODE_BAD_GATEWAY = 502;
    /**
     * Status code for a service that is unavailable on the server.
     */
    public static final int STATUS_CODE_SERVICE_UNAVAILABLE = 503;
    /**
     * Status code for a resource that has permanently moved to a new URI.
     */
    private static final int STATUS_CODE_PERMANENT_REDIRECT = 308;

    /**
     * Returns whether the given HTTP response status code is a success code {@code >= 200 and < 300}.
     */
    public static boolean isSuccess(int statusCode) {
        return statusCode >= STATUS_CODE_OK && statusCode < STATUS_CODE_MULTIPLE_CHOICES;
    }

    /**
     * Returns whether the given HTTP response status code is forbidden or unauthorized code {@code == 401 or 403}.
     */
    public static boolean isForbiddenOrUnauthorized(int statusCode) {
        return statusCode == STATUS_CODE_FORBIDDEN || statusCode == STATUS_CODE_UNAUTHORIZED;
    }

}
