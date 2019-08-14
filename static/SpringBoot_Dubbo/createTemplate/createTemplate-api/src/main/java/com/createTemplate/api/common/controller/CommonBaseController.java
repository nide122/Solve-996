/*
* Copyright (c) 2017-2020 SHENZHEN ZHONGTUIKEJI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市科瑞特网络科技有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/
package com.createTemplate.api.common.controller;

import com.createTemplate.model.exception.BusinessException;

public class CommonBaseController extends BaseController {

    /**
     * 校验当前页和每页的条数
     * 
     * @param page
     * @param rows
     */
    public void checkPageRows(Integer page, Integer rows) {
        if (page == null || rows == null) {
            throw new BusinessException("page、rows不能为空");
        }
        if (rows > 500) {
            throw new BusinessException("rows 不能大于500");
        }
    }

}
