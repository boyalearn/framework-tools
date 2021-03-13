package com.frameworktool.webmvc.filter;

import javax.servlet.ServletOutputStream;
import javax.servlet.WriteListener;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

public class TraceResponseWrapper extends HttpServletResponseWrapper {

    private ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    private PrintWriter writer;
    private Map<String, Object> header = new HashMap<String, Object>();

    private HttpServletResponse response;

    public TraceResponseWrapper(HttpServletResponse response) {
        super(response);
        this.response = response;
    }
    
    public void setHeader() throws IOException {
        ServletOutputStream outputStream = response.getOutputStream();
    }

    @Override
    public ServletOutputStream getOutputStream() throws IOException {
        return new ServletOutputStreamWrapper(response.getOutputStream());
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
    public void setCharacterEncoding(String charset) {
        header.put("Character-Encoding", charset);
        super.setCharacterEncoding(charset);
    }

    @Override
    public void setContentLength(int len) {
        header.put("Content-Length", len);
        super.setContentLength(len);
    }

    @Override
    public void setContentLengthLong(long length) {
        header.put("Content-Length", length);
        super.setContentLengthLong(length);
    }

    @Override
    public void setContentType(String type) {
        header.put("Content-Type", type);
        super.setContentType(type);
    }






    /**
     * 获取响应体
     *
     * @return bytes
     */
    public byte[] getBytes() {
        if (null != writer) {
            writer.close();
            return bytes.toByteArray();
        }
        return bytes.toByteArray();
    }

    public Map<String, Object> getHeaders() {
        return header;
    }

    private class ServletOutputStreamWrapper extends ServletOutputStream {
        private ServletOutputStream outputStream;

        public ServletOutputStreamWrapper(ServletOutputStream stream) {
            this.outputStream = stream;
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
