package com.recordpoint.connectors.sdk.http;

import com.google.common.io.ByteStreams;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

public class FileContent implements StreamingContent {

    private final InputStream file;

    public FileContent(InputStream file) {
        this.file = file;
    }

    @Override
    public InputStream getInputStream() throws IOException {
        try (ByteArrayInputStream contentStream = new ByteArrayInputStream(ByteStreams.toByteArray(file))) {
            return contentStream;
        }
    }

    @Override
    public Object getContent() {
        return file;
    }

}
