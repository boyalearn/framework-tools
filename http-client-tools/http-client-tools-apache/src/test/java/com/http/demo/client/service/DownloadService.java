package com.http.demo.client.service;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;

@Service
public class DownloadService implements DisposableBean {

    CloseableHttpClient httpclient;

    public InputStream download(String url) throws IOException {

        if (url.contains("RRRrandomRRR")) {
            url = url.replace("RRRrandomRRR", new Date().getTime() + "");
        }

        HttpGet httpget = new HttpGet(url);
        HttpResponse response = httpclient.execute(httpget);
        System.out.println(response.getStatusLine());
        HttpEntity entity = response.getEntity();
        if (entity != null) {
            InputStream inputStream = entity.getContent();

            return inputStream;
        }
        return null;
    }

    @PostConstruct
    public void init() {
        httpclient = HttpClients.createDefault();
    }

    @Override
    public void destroy() throws Exception {
        if (null != httpclient) {
            httpclient.close();
        }
    }
}
