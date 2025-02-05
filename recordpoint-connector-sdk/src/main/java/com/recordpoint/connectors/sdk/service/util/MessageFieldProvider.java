package com.recordpoint.connectors.sdk.service.util;

import java.util.ResourceBundle;

public final class MessageFieldProvider {

    private static final String DEFAULT_KEY_MESSAGE = "field.default";
    private static final ResourceBundle RESOURCE_BUNDLE = ResourceBundle.getBundle("messages");

    private MessageFieldProvider() {
    }

    public static String getMessage(String key) {
        return RESOURCE_BUNDLE.containsKey(key)
                ? RESOURCE_BUNDLE.getString(key)
                : String.format(RESOURCE_BUNDLE.getString(DEFAULT_KEY_MESSAGE), key);
    }

}
