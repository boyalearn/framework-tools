package com.http.demo.client.task;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class DownloadPicTaskInfo {

    private String url;

    private String bankName;

    private int num;

    private String suffix;
}
