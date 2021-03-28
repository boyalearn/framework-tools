package com.http.demo.client.service;

import com.http.demo.client.task.DownloadPicTaskInfo;
import com.http.demo.client.utils.FileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

@Service
public class PicIdentityService {

    @Autowired
    private DownloadService downloadService;

    @Autowired
    private UploadService uploadService;

    public boolean doService(DownloadPicTaskInfo taskInfo) throws IOException {
        InputStream inputStream = downloadService.download(taskInfo.getUrl());
        if (null == inputStream) {
            return false;
        }
        FileUtil.write(inputStream, "", "temp" + taskInfo.getSuffix());
        Map<String, String> map = uploadService.upload(FileUtil.read("", "temp" + taskInfo.getSuffix()));
        String fileName = map.get("pic_str");
        if (null == fileName || fileName.isEmpty()) {
            return false;
        }
        File sourceFile = FileUtil.read("", "temp" + taskInfo.getSuffix());
        File target = FileUtil.read(taskInfo.getBankName(), fileName + taskInfo.getSuffix());
        FileUtil.copy(sourceFile, target);
        return true;
    }
}
