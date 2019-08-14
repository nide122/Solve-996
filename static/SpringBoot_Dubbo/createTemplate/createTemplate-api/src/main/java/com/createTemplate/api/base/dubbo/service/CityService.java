/*
* Copyright (c) 2017-2020 SHENZHEN ZHONGTUIKEJI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市科瑞特网络科技有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/
package com.createTemplate.api.base.dubbo.service;

import com.createTemplate.model.base.pojo.City;

/**
 * 城市业务逻辑接口类
 *
 * Created by bysocket on 07/02/2017.
 */
public interface CityService {

    /**
     * 获取城市
     *
     */
    City getCityByName(String cityName);

    /**
     * 新增城市信息
     *
     */
    void saveCity(City city);

    /**
     * 更新城市信息
     *
     */
    void updateCityDescription(String cityName, String description);

}
