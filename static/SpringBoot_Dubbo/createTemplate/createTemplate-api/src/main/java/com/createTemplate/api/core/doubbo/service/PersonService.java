/*
* Copyright (c) 2017-2020 SHENZHEN ZHONGTUIKEJI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市科瑞特网络科技有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/
package com.createTemplate.api.core.doubbo.service;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.createTemplate.api.base.dao.MybatisDao;
import com.createTemplate.model.core.pojo.TPerson;
import com.createTemplate.model.core.vo.TPersonVO;
import com.createTemplate.model.exception.BusinessException;
import com.createTemplate.model.resultvo.Grid;

/**
 * 用户接口service
 * 
 * @author:
 * @date: 2018年6月23日 下午4:33:12
 * @version V1.0
 */
public interface PersonService extends MybatisDao {

    /**
     * 通过手机号密码参数登录并返回token 信息
     * 
     * @param person
     * @author libiqi
     * @date 2018年5月14日
     */
    TPersonVO login(TPersonVO person) throws BusinessException;

    /**
     * 注册服务
     * 
     * @param person
     * @author libiqi
     * @date 2018年5月14日
     */
    TPersonVO register(TPersonVO personVo) throws BusinessException;

    /**
     * 退出服务
     * 
     * @param person
     * @author libiqi
     * @date 2018年5月14日
     */
    void logout(TPersonVO person) throws BusinessException;

    /***
     * 根据用户ID 更新用户信息
     * 
     * @param id
     * @author libiqi
     * @date 2018年5月14日
     */
    TPerson updatePersonById(TPerson person) throws BusinessException;

    /**
     * 根据ID查询用户
     * 
     * @param id
     * @return
     * @author libiqi
     * @date 2018年5月14日
     */
    TPerson findById(Long id) throws BusinessException;

    /**
     * 根据IDs查询用户
     * 
     * @param id
     * @return
     * @author libiqi
     * @date 2018年5月14日
     */
    List<TPerson> findByIds(Set<Long> ids) throws BusinessException;

    /**
     * 分页查询person lname name beginDate endDate
     * 
     * @param vo
     * @return
     * @author libiqi
     * @date 2018年5月14日
     */
    Grid findPersonPage(TPersonVO vo) throws BusinessException;

    /**
     * 将用户信息插入redis 根据ID
     * 
     * @param PersonVo
     * @return
     * @author libiqi
     * @date 2018年5月15日
     */
    Map<String, Object> setPersonToRedis(TPersonVO PersonVo) throws BusinessException;

    /**
     * 根据token获取当前用户session
     * 
     * @param authToken
     * @return
     * @author libiqi
     * @date 2018年5月15日
     */
    TPersonVO getCurrentUser(String authToken) throws BusinessException;

    /**
     * 根据手机号判断用户是否已经注册
     * 
     * @param mobile
     * @return
     */
    Boolean checkPersonIsRepeat(Long mobile) throws BusinessException;

    /**
     * 根据手机查询person
     * 
     * @param mobile
     * @return
     */
    TPersonVO findByMobile(Long mobile) throws BusinessException;

    /**
     * 根据用户ID查询用户详情
     * 
     * @param uid
     * @return
     */
    TPersonVO findPersonDetailById(Long id) throws BusinessException;


    // 忘记密码
    void forgetPassword(TPersonVO tPersonVO) throws BusinessException;

    /**
     * 更新用户session
     * 
     * @param dbPersonVo
     */
    void updatePersonSession(TPersonVO dbPersonVo);
}
