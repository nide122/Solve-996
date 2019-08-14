/*
* Copyright (c) 2017-2020 SHENZHEN ZHONGTUIKEJI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市科瑞特网络科技有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/
package com.createTemplate.api.common.solrcloud.dao.impl;

import java.io.IOException;

import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.CloudSolrClient;
import org.apache.solr.client.solrj.impl.CloudSolrServer;
import org.apache.solr.client.solrj.response.UpdateResponse;
import org.springframework.beans.factory.annotation.Autowired;

import com.createTemplate.api.common.solrcloud.dao.CommonSolrCloudDao;


public class CommonSolrCloudDaoImpl implements CommonSolrCloudDao{
   
    @Autowired
    protected CloudSolrClient cloudSolrClient;

    @Override
    public void save(Object obj) throws IOException, SolrServerException {
        UpdateResponse updateResponse =  cloudSolrClient.addBean(obj);
        System.out.println(updateResponse);
        cloudSolrClient.commit();
    }
    @Override
    public void deleteById( String id) throws SolrServerException, IOException {
        cloudSolrClient.deleteById(id);
        cloudSolrClient.commit();
    }

}
