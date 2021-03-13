package com.frameworktool.test.config;

import org.apache.catalina.connector.Response;
import org.apache.catalina.connector.ResponseFacade;

import javax.servlet.ServletOutputStream;
import javax.servlet.WriteListener;
import javax.servlet.http.Cookie;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

public class TraceResponseFacade extends ResponseFacade {

    private ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    private PrintWriter writer;

    private Map<String, Object> header = new HashMap<String, Object>();

    public TraceResponseFacade(Response response) {
        super(response);
    }

    @Override
    public ServletOutputStream getOutputStream() throws IOException {
        return new ServletOutputStreamFacade(response.getOutputStream());
    }

    @Override
    public PrintWriter getWriter() throws IOException {
        try {
            writer = new PrintWriter(new OutputStreamWriter(bytes, "utf-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return writer;
    }

    public byte[] getBytes() {
        if (null != writer) {
            writer.close();
            return bytes.toByteArray();
        }
        return bytes.toByteArray();
    }


    @Override
    public void setContentLength(int len) {
        super.setContentLength(len);
    }

    @Override
    public void setContentLengthLong(long length) {
        super.setContentLengthLong(length);
    }

    @Override
    public void setContentType(String type) {
        header.put("Content-Type", type);
        super.setContentType(type);
    }

    @Override
    public void addCookie(Cookie cookie) {
        super.addCookie(cookie);
    }

    @Override
    public void setDateHeader(String name, long date) {
        header.put(name, date);
        super.setDateHeader(name, date);
    }

    @Override
    public void addDateHeader(String name, long date) {
        header.put(name, date);
        super.addDateHeader(name, date);
    }

    @Override
    public void setHeader(String name, String value) {
        header.put(name, value);
        super.setHeader(name, value);
    }

    @Override
    public void addHeader(String name, String value) {
        header.put(name, value);
        super.addHeader(name, value);
    }

    @Override
    public void setIntHeader(String name, int value) {
        header.put(name, value);
        super.setIntHeader(name, value);
    }

    @Override
    public void addIntHeader(String name, int value) {
        header.put(name, value);
        super.addIntHeader(name, value);
    }

    @Override
    public void setStatus(int sc) {
        super.setStatus(sc);
    }

    @Override
    public void setStatus(int sc, String sm) {
        super.setStatus(sc, sm);
    }

    @Override
    public void setCharacterEncoding(String arg0) {
        header.put("Character-Encoding", arg0);
        super.setCharacterEncoding(arg0);
    }

    public Map<String, Object> getHeaders() {
        return header;
    }

    private class ServletOutputStreamFacade extends ServletOutputStream {
        private ServletOutputStream outputStream;

        public ServletOutputStreamFacade(ServletOutputStream outputStream) {
            this.outputStream = outputStream;
        }

        @Override
        public void write(int b) throws IOException {
            bytes.write(b);
            outputStream.write(b);
        }

        @Override
        public boolean isReady() {
            return false;
        }

        @Override
        public void setWriteListener(WriteListener writeListener) {
        }
    }
}
