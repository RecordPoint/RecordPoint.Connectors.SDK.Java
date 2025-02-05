package com.recordpoint.connectors.sdk.service.binary.model;

import com.recordpoint.connectors.sdk.service.ServiceResponse;

public class DirectBinarySubmissionOutput implements ServiceResponse {

    private String url;
    private Integer maxFileSize;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Integer getMaxFileSize() {
        return maxFileSize;
    }

    public void setMaxFileSize(Integer maxFileSize) {
        this.maxFileSize = maxFileSize;
    }
}
