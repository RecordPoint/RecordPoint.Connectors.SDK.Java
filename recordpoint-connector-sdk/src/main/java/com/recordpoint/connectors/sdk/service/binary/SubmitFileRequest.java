package com.recordpoint.connectors.sdk.service.binary;

import com.google.common.base.Preconditions;
import com.recordpoint.connectors.sdk.service.AbstractServiceRequest;
import com.recordpoint.connectors.sdk.service.binary.model.DirectBinarySubmission;
import com.recordpoint.connectors.sdk.service.binary.model.EmptyResponse;
import com.recordpoint.connectors.sdk.service.util.MessageFieldProvider;

import java.io.InputStream;

public class SubmitFileRequest extends AbstractServiceRequest<EmptyResponse> {

    private final InputStream file;
    private final DirectBinarySubmission fileSubmission;

    public SubmitFileRequest(Builder builder) {
        Preconditions.checkNotNull(builder.fileSubmission, MessageFieldProvider.getMessage("field.payload"));
        Preconditions.checkNotNull(builder.fileContent, MessageFieldProvider.getMessage("field.fileContent"));
        this.fileSubmission = builder.fileSubmission;
        this.file = builder.fileContent;
    }

    public static Builder Builder() {
        return new Builder();
    }

    public InputStream getFile() {
        return file;
    }

    public DirectBinarySubmission getFileSubmission() {
        return fileSubmission;
    }

    public static class Builder extends AbstractServiceRequest.Builder {

        private InputStream fileContent;
        private DirectBinarySubmission fileSubmission;

        public SubmitFileRequest.Builder setFileContent(InputStream fileContent) {
            this.fileContent = fileContent;
            return this;
        }

        public SubmitFileRequest.Builder setFileSubmissionInfo(DirectBinarySubmission fileSubmission) {
            this.fileSubmission = fileSubmission;
            return this;
        }

        @Override
        public SubmitFileRequest build() {
            return new SubmitFileRequest(this);
        }
    }

}
