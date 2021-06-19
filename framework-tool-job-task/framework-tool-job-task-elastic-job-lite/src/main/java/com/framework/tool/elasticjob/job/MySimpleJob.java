package com.framework.tool.elasticjob.job;


import org.apache.shardingsphere.elasticjob.api.ShardingContext;
import org.apache.shardingsphere.elasticjob.simple.job.SimpleJob;

public class MySimpleJob implements SimpleJob {

    @Override
    public void execute(ShardingContext context) {
        System.out.println("do service " + context.getShardingItem());
    }
}
