package com.http.demo.client.service;

import com.http.demo.client.utils.JsonUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Map;

@Service
public class UploadService implements DisposableBean {
    CloseableHttpClient httpClient;

    public Map<String, String> upload(File file) {

        CloseableHttpResponse response = null;
        try {


            // 把一个普通参数和文件上传给下面这个地址 是一个servlet
            HttpPost httpPost = new HttpPost("http://upload.chaojiying.net/Upload/Processing.php");

            HttpEntity reqEntity = MultipartEntityBuilder.create()
                    .addTextBody("user", "18328508416")
                    .addTextBody("pass", "Hing@1991")
                    .addTextBody("softid", "914174")
                    .addTextBody("codetype", "1902")
                    .addBinaryBody("userfile", file)
                    .build();

            httpPost.setEntity(reqEntity);

            // 发起请求 并返回请求的响应
            response = httpClient.execute(httpPost);


            // 获取响应对象
            HttpEntity resEntity = response.getEntity();
            if (resEntity != null) {
                String json = EntityUtils.toString(resEntity, Charset.forName("UTF-8"));
                return JsonUtils.jsonToMap(json);
            }
            // 销毁
            EntityUtils.consume(resEntity);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (response != null) {
                    response.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @PostConstruct
    public void init() {
        httpClient = HttpClients.createDefault();
    }


    @Override
    public void destroy() throws Exception {
        if (httpClient != null) {
            httpClient.close();
        }
    }
}
