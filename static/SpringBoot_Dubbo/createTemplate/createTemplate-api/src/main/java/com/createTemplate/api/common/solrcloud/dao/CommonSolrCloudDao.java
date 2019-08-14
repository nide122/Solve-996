/*
* Copyright (c) 2017-2020 SHENZHEN ZHONGTUIKEJI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市科瑞特网络科技有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/
package com.createTemplate.api.common.solrcloud.dao;

import java.io.IOException;

import org.apache.solr.client.solrj.SolrServerException;

public interface CommonSolrCloudDao {
    
    
    /**
     *  添加solr文档，,未提交事物
     * @param obj
     * @throws IOException
     * @throws SolrServerException
     */
    public void save( Object obj) throws IOException, SolrServerException;
    
    /**
     * 根据id删除solr文档，已提交事物
     * @param id
     * @throws SolrServerException
     * @throws IOException
     */
    public void deleteById(String id) throws SolrServerException, IOException;
    
}
