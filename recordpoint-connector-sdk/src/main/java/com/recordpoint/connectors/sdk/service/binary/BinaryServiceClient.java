package com.recordpoint.connectors.sdk.service.binary;

import com.recordpoint.connectors.sdk.http.FileContent;
import com.recordpoint.connectors.sdk.http.exception.HttpExecutionException;
import com.recordpoint.connectors.sdk.http.exception.HttpResponseException;
import com.recordpoint.connectors.sdk.json.JsonMapperException;
import com.recordpoint.connectors.sdk.service.AbstractServiceClient;
import com.recordpoint.connectors.sdk.service.ServiceSettings;
import com.recordpoint.connectors.sdk.service.binary.model.DirectBinarySubmissionOutput;

public class BinaryServiceClient extends AbstractServiceClient {

    public BinaryServiceClient(AbstractServiceClient.Builder builder) {
        super(builder);
    }

    public static AbstractServiceClient.Builder Builder() {
        return new Builder();
    }

    public boolean submitBinary(SubmitBinaryArchiveRequest request) throws JsonMapperException, HttpExecutionException, HttpResponseException {
        String resourceUrl = String.format("%s%s?ConnectorId=%s&ItemExternalId=%s&BinaryExternalId=%s", getRootUrl(), request.getServicePath(), request.getConnectorId(), request.getItemExternalId(), request.getBinaryExternalId());
        postRequest(resourceUrl, null);
        return true;
    }

    public DirectBinarySubmissionOutput getBlobToken(GetBlobTokenResourceRequest request) throws JsonMapperException, HttpExecutionException, HttpResponseException {
        String resourceUrl = String.format("%s%s", getRootUrl(), request.getServicePath());
        return postRequest(resourceUrl, request.getPayload(), DirectBinarySubmissionOutput.class);
    }

    public boolean notifyBinaryUploaded(NotifiesNewBinaryUploadedRequest request) throws HttpExecutionException, HttpResponseException {
        String resourceUrl = String.format("%s%s", getRootUrl(), request.getServicePath());
        postRequest(resourceUrl, request.getPayload());
        return true;
    }

    public boolean submitFile(SubmitFileRequest request) throws JsonMapperException, HttpExecutionException, HttpResponseException {
        DirectBinarySubmissionOutput blobUrl = getBlobToken(GetBlobTokenResourceRequest.Builder()
                .setPayload(request.getFileSubmission()).build());
        putExternalRequest(blobUrl.getUrl(), new FileContent(request.getFile()));
        return notifyBinaryUploaded(NotifiesNewBinaryUploadedRequest.Builder()
                .setPayload(request.getFileSubmission()).build());
    }

    public static class Builder extends AbstractServiceClient.Builder {

        @Override
        public BinaryServiceClient build() {
            return new BinaryServiceClient(this);
        }

        @Override
        public AbstractServiceClient.Builder setServiceSettings(ServiceSettings serviceSettings) {
            super.setServiceSettings(serviceSettings);
            return this;
        }

    }

}
