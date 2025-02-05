package com.recordpoint.connectors.sdk.http.apache;

import org.apache.hc.core5.http.ClassicHttpResponse;

import java.io.IOException;
import java.io.InputStream;

/**
 * An implementation of {@link InputStream} that wraps the response content of an Apache HTTP response.
 * This class provides a custom stream for reading the response body from an Apache {@link ClassicHttpResponse}
 * and ensures that the response is properly closed when the stream is closed.
 *
 * @author Pedro Moran
 * @version 1.0.0
 */
public class ApacheResponseContent extends InputStream {
    /**
     * The Apache HTTP response associated with this content.
     */
    private final ClassicHttpResponse response;

    /**
     * The underlying input stream that wraps the response content.
     */
    private final InputStream wrappedStream;

    /**
     * Constructs an {@link ApacheResponseContent} to wrap the provided {@link InputStream} and
     * associate it with the given {@link ClassicHttpResponse}.
     *
     * @param wrappedStream the underlying {@link InputStream} representing the response content
     * @param response      the {@link ClassicHttpResponse} associated with this content
     */
    public ApacheResponseContent(InputStream wrappedStream, ClassicHttpResponse response) {
        this.wrappedStream = wrappedStream;
        this.response = response;
    }

    /**
     * Reads the next byte of data from the input stream.
     *
     * @return the next byte of data, or -1 if the end of the stream is reached
     * @throws IOException if an I/O error occurs
     */
    @Override
    public int read() throws IOException {
        return this.wrappedStream.read();
    }

    /**
     * Reads up to {@code len} bytes of data from the input stream into an array of bytes.
     *
     * @param b   the buffer into which the data is read
     * @param off the start offset in the buffer
     * @param len the maximum number of bytes to read
     * @return the number of bytes read, or -1 if the end of the stream is reached
     * @throws IOException if an I/O error occurs
     */
    @Override
    public int read(byte[] b, int off, int len) throws IOException {
        return wrappedStream.read(b, off, len);
    }

    /**
     * Skips over and discards {@code n} bytes of data from the input stream.
     *
     * @param n the number of bytes to skip
     * @return the actual number of bytes skipped
     * @throws IOException if an I/O error occurs
     */
    @Override
    public long skip(long n) throws IOException {
        return wrappedStream.skip(n);
    }

    /**
     * Returns the number of bytes that can be read from this input stream without blocking.
     *
     * @return the number of available bytes
     * @throws IOException if an I/O error occurs
     */
    @Override
    public int available() throws IOException {
        return wrappedStream.available();
    }

    /**
     * Marks the current position in the input stream.
     *
     * @param readlimit the maximum number of bytes that can be read before the mark is invalidated
     */
    @Override
    public synchronized void mark(int readlimit) {
        wrappedStream.mark(readlimit);
    }

    /**
     * Resets the input stream to the last marked position.
     *
     * @throws IOException if the stream has not been marked or an I/O error occurs
     */
    @Override
    public synchronized void reset() throws IOException {
        wrappedStream.reset();
    }

    /**
     * Closes the input stream and the associated {@link ClassicHttpResponse}.
     * This ensures that the resources are released when the stream is closed.
     *
     * @throws IOException if an I/O error occurs during the close operation
     */
    @Override
    public void close() throws IOException {
        if (wrappedStream != null) {
            wrappedStream.close();
        }
        if (response != null) {
            response.close();
        }
    }

    /**
     * Returns whether marking the stream's position is supported.
     *
     * @return {@code true} if marking is supported, {@code false} otherwise
     */
    @Override
    public boolean markSupported() {
        return wrappedStream.markSupported();
    }

    /**
     * Returns the associated Apache HTTP response.
     *
     * @return the {@link org.apache.hc.core5.http.HttpResponse} associated with this stream
     */
    org.apache.hc.core5.http.HttpResponse getResponse() {
        return response;
    }
}
