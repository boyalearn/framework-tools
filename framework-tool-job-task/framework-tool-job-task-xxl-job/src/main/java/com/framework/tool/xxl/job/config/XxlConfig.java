package com.framework.tool.xxl.job.config;

import com.xxl.job.core.executor.impl.XxlJobSpringExecutor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class XxlConfig {

    private Logger logger = LoggerFactory.getLogger(XxlConfig.class);

    @Value("${xxl.job.admin.addresses:'http://127.0.0.1:8081/xxl-job-admin'}")
    private String adminAddresses;

    @Value("${xxl.job.accessToken:''}")
    private String accessToken;

    @Value("${xxl.job.executor.appname:'demo'}")
    private String appname;

    @Value("${xxl.job.executor.address:''}")
    private String address;

    @Value("${xxl.job.executor.ip:''}")
    private String ip;

    @Value("${xxl.job.executor.port:9090}")
    private int port;

    @Value("${xxl.job.executor.logpath:''}")
    private String logPath;

    @Value("${xxl.job.executor.logretentiondays:0}")
    private int logRetentionDays;

    @Bean
    public XxlJobSpringExecutor xxlJobExecutor() throws Exception {
        logger.info(">>>>>>>>>>> xxl-job config init.");
        XxlJobSpringExecutor xxlJobSpringExecutor = new XxlJobSpringExecutor();
        xxlJobSpringExecutor.setAdminAddresses(adminAddresses);
        xxlJobSpringExecutor.setAppName(appname);
        xxlJobSpringExecutor.setIp(ip);
        xxlJobSpringExecutor.setPort(port);
        xxlJobSpringExecutor.setAccessToken(accessToken);
        xxlJobSpringExecutor.setLogPath(logPath);
        xxlJobSpringExecutor.setLogRetentionDays(logRetentionDays);
        xxlJobSpringExecutor.start();
        return xxlJobSpringExecutor;
    }
}
