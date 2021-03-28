package com.http.demo.client.feign;


import com.http.demo.client.entity.IdentityBean;

import java.util.Map;

public interface PicIdentityClient {

    public Map<String,String> doIdentityPic( IdentityBean bean);
}
