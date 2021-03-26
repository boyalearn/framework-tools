package com.http.demo.client.config;

import com.http.demo.client.service.PicIdentityService;
import com.http.demo.client.task.DownloadPicTaskInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Configuration
public class TaskConfig {

    @Autowired
    private PicIdentityService picIdentityService;

    @PostConstruct
    public void init() {
        List<DownloadPicTaskInfo> tasks = new ArrayList<>();
        //tasks.add(new DownloadPicTaskInfo("https://sbank.hxb.com.cn/gluebanking/captcha.jpg?randomKey=0.14264345266130984", "华夏银行", 50, ".png"));
        //tasks.add(new DownloadPicTaskInfo("https://corporbank-simp.icbc.com.cn/servlet/com.icbc.inbs.servlet.Verifyimage?disFlag=2&randomKey=1616133788089345785", "工商银行", 50, ".png"));
        //tasks.add(new DownloadPicTaskInfo("https://ebank.95559.com.cn/CEBS/DynImgCode.do", "交通银行", 50, ".png"));
        //tasks.add(new DownloadPicTaskInfo("https://auth.orangebank.com.cn/outside-api/auth-server/v1/verifyCode?vCodeKey=10bcd21dcd1b4aea81ace3ce4fa38ad0&_t=RRRrandomRRR", "平安银行", 50, ".jpg"));
        //tasks.add(new DownloadPicTaskInfo("https://ebankhent.spdb.com.cn/newent/TokenGenerateServlet?1616134390948", "上海浦东发展银行", 50, ".png"));
        //tasks.add(new DownloadPicTaskInfo("https://ebank.cbhb.com.cn/entWeb/LoginVercode?rand=0.6910654322920149", "渤海银行", 50, ".jpg"));
        //tasks.add(new DownloadPicTaskInfo("https://ibank.bankofshanghai.com/eweb/GenTokenImg.do", "上海银行", 50, ".jpg"));
        //tasks.add(new DownloadPicTaskInfo("https://corpebankq.psbc.com/eweb/GenTokenImg.do?random=0.16166580857254442", "邮政储蓄银行", 50, ".png"));
        //tasks.add(new DownloadPicTaskInfo("https://ebank.jsbchina.cn/newperbank/VerifyImage", "江苏银行", 50, ".jpg"));
        //tasks.add(new DownloadPicTaskInfo("https://cb.nbcb.com.cn/corporbank/VerifyImage?flag=1616141045158", "宁波银行", 50, ".jpg"));
        //tasks.add(new DownloadPicTaskInfo("http://www.adbc.com.cn/n6/n125/demo/eweb/images/ffg.png", "农业发展银行", 50, ".png"));
        //tasks.add(new DownloadPicTaskInfo("https://corp.bank.ecitic.com/cotb/VerifyCodeServlet?type=0", "中信银行", 50, ".png"));
        //tasks.add(new DownloadPicTaskInfo("https://ebank.cgbchina.com.cn/corporbank/VerifyImage", "广发银行", 50, ".jpg"));
        //tasks.add(new DownloadPicTaskInfo("https://ebank.4001961200.com:8080/eweb/GenTokenImg.do?random=0.4442036059316339", "农村商业银行(深圳)", 50, ".png"));
        tasks.add(new DownloadPicTaskInfo("https://online.hsbank.cc/newpweb/GenTokenImg.do?random=0.8017339737028941", "微商银行", 50, ".png"));
        tasks.forEach(t -> {
            for (int i = 0; i < t.getNum(); i++) {
                try {
                    picIdentityService.doService(t);
                } catch (IOException e) {
                    e.printStackTrace();
                    System.out.println("num is " + i + t);
                }
            }
        });

    }
}
