/*
* Copyright (c) 2017-2020 SHENZHEN ZHONGTUIKEJI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市科瑞特网络科技有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/
package com.createTemplate.provider.config;

import org.apache.solr.client.solrj.impl.CloudSolrClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.solr.repository.config.EnableSolrRepositories;

//@Configuration
//@EnableSolrRepositories(basePackages = {"com.createTemplate"}, multicoreSupport = true)
public class SolrConfig {

    @Value("${spring.data.solr.zk.host}")
    private String zkHost;

    @Value("${spring.data.solr.zk.client.timeout}")
    private int zkClientTimeout;

    @Value("${spring.data.solr.zk.connect.timeout}")
    private int zkConnectTimeout;

    @Value("${spring.data.solr.defaultCollection}")
    private String defaultCollection;

    @Bean
    public CloudSolrClient solrClient() {
        CloudSolrClient cloudSolrClient = new CloudSolrClient(zkHost);
        cloudSolrClient.setZkClientTimeout(zkClientTimeout);
        cloudSolrClient.setZkConnectTimeout(zkConnectTimeout);
        cloudSolrClient.setDefaultCollection(defaultCollection);
        return cloudSolrClient;
    }

}
