/*
* Copyright (c) 2017-2020 SHENZHEN ZHONGTUIKEJI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市科瑞特网络科技有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/
package com.createTemplate.personal.web.core.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.dubbo.config.annotation.Reference;
import com.createTemplate.api.base.dubbo.service.CommonRedisDao;
import com.createTemplate.api.common.dubbo.service.MessageService;
import com.createTemplate.api.core.doubbo.service.PersonService;
import com.createTemplate.api.util.StringUtil;
import com.createTemplate.model.auth.AuthToken;
import com.createTemplate.model.core.pojo.TPerson;
import com.createTemplate.model.core.vo.TPersonVO;
import com.createTemplate.model.exception.BusinessException;
import com.createTemplate.model.resultvo.ResultInfo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

/*
 * 用户控制层
 * 
 */
@RestController
@Scope(value = "prototype")
@RequestMapping(value = "/service/person")
@Api(value = "用户controller", tags = { "用户操作接口" })
public class PersonController {

    @Reference(version = "1.0.0",check = false)
    PersonService personService;

    @Reference(version = "1.0.0",check = false)
    private CommonRedisDao commonRedisDao;

    @Resource
    private HttpServletRequest request;

    @Reference(version = "1.0.0",check = false)
    private MessageService messageService;

    /**
     * 注册 (加入同步锁，同一个手机账号在同一时刻只能注册一个。防止重复注册。)
     * 
     * @author xf
     * @param
     * @return
     * @date 2015-3-12 下午05:59:24
     * @version V1.0
     */
    @ApiImplicitParams({
            @ApiImplicitParam(name = "phone_num", value = "手机号，登录账号", required = true, dataType = "Long", paramType = "query"),
            @ApiImplicitParam(name = "password", value = "密码", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "captcha", value = "验证码", required = true, dataType = "String", paramType = "query"), })
    @ApiOperation(value = "regist", notes = "注册")
    @RequestMapping(value = "/regist", method = RequestMethod.POST)
    public ResultInfo<TPersonVO> regist( @RequestBody(required=false) TPersonVO personVo) {
        if (StringUtil.checkNull(personVo.getCaptcha()) || personVo.getPhone_num() == null || StringUtil.checkNull(
                personVo.getPassword())) {
            throw new BusinessException("请填写手机号、短信验证码、密码!");
        }
        /** 校验验证码 */
        Boolean checkCaptcha = messageService.checkCaptchaToken(personVo.getPhone_num().toString(), personVo
                .getCaptcha());
        if (checkCaptcha) {
            throw new BusinessException("验证码错误,请重新获取！");
        }
        TPersonVO personVos = personService.register(personVo);
        return new ResultInfo<TPersonVO>(ResultInfo.SUCCESS, "注册成功", personVos);
    }

    @ApiImplicitParams({
            @ApiImplicitParam(name = "authToken", value = "authToken", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "alias_name", value = "昵称", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "photo", value = "头像", required = false, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "birthday", value = "出生年月 格式：yyyy-mm-dd", required = true, dataType = "Date", paramType = "query"),
            @ApiImplicitParam(name = "sex", value = "性别  0男  1女", required = true, dataType = "Integer", paramType = "query"),
            @ApiImplicitParam(name = "native_place", value = "籍贯", required = false, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "marital_status", value = "婚姻状况 0未婚 1丧偶 2离异无子 3离异有子 4已婚", required = true, dataType = "Integer", paramType = "query"),
            @ApiImplicitParam(name = "job_place_province_code", value = "工作地点省代码", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "job_place_city_code", value = "工作地点市代码", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "job_place_areas_code", value = "工作地点区代码", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "hobbies", value = "兴趣爱好", required = false, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "constellation", value = "星座 1白羊座 2金牛座 3双子座 4巨蟹座 5狮子座 6处女座 7天秤座 8天蝎座 9射手座 10摩羯座 11水瓶座 12双鱼座", required = false, dataType = "Integer", paramType = "query"),
            @ApiImplicitParam(name = "chinese_zodiac", value = "生肖 1鼠 2牛 3虎 4兔 5龙 6蛇 7马 8羊  9猴 10鸡 11狗 12猪", required = false, dataType = "Integer", paramType = "query"),
            @ApiImplicitParam(name = "referrer_name", value = "推荐人", required = false, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "referrer_phone", value = "推荐人电话", required = false, dataType = "String", paramType = "query"), })
    @ApiOperation(value = "完善用户信息", notes = "完善用户信息")
    @RequestMapping(value = "/perfectionPerson", method = RequestMethod.POST)
    @AuthToken(type = "createTemplate")
    public ResultInfo<Object> perfectionPerson( @RequestBody(required=false) TPerson person) {
        if (StringUtil.checkNull(person.getAlias_name()) || person.getBirthday() == null || person.getSex() == null
                || person.getMarital_status() == null || StringUtil.checkNull(person.getJob_place_province_code())
                || StringUtil.checkNull(person.getJob_place_city_code()) || StringUtil.checkNull(person
                        .getJob_place_areas_code())) {
            throw new BusinessException("昵称、出生年月、性别、婚姻状况、工作地点省代码、工作地点市代码、工作地点区代码不能为空");
        }
        String authToken = request.getParameter("authToken");
        TPersonVO sessionPerson = personService.getCurrentUser(authToken);
        person.setId(sessionPerson.getId());
        personService.updatePersonById(person);
        return new ResultInfo<Object>(ResultInfo.SUCCESS, "保存成功", null);
    }

    @ApiImplicitParams({
            @ApiImplicitParam(name = "phone_num", value = "手机号，登录账号", required = true, dataType = "Long", paramType = "query"),
            @ApiImplicitParam(name = "password", value = "密码", required = true, dataType = "String", paramType = "query"), })
    @ApiOperation(value = "登录（根据是否有用户名来判断是否完善信息）", notes = "登录")
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResultInfo<TPersonVO> login( @RequestBody(required=false) TPersonVO tPersonVO) {
        if (StringUtil.checkNull(tPersonVO.getPassword()) || tPersonVO.getPhone_num() == null) {
            throw new BusinessException("请填写用户名和密码");
        }
        return new ResultInfo<TPersonVO>(ResultInfo.SUCCESS, "登录成功", personService.login(tPersonVO));
    }

    @ApiImplicitParams({
            @ApiImplicitParam(name = "phone_num", value = "手机号，登录账号", required = true, dataType = "Long", paramType = "query"),
            @ApiImplicitParam(name = "password", value = "新密码", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "captcha", value = "验证码", required = true, dataType = "String", paramType = "query"), })
    @ApiOperation(value = "忘记密码", notes = "忘记密码")
    @RequestMapping(value = "/forgetPassword", method = RequestMethod.POST)
    public ResultInfo<Object> forgetPassword( @RequestBody(required=false) TPersonVO tPersonVO) {
        if (tPersonVO.getPhone_num() == null || StringUtil.checkNull(tPersonVO.getPassword()) || StringUtil.checkNull(
                tPersonVO.getCaptcha())) {
            throw new BusinessException("请填写用户名、验证码和新密码");
        }
        /** 校验验证码 */
        Boolean checkCaptcha = messageService.checkCaptchaToken(tPersonVO.getPhone_num().toString(), tPersonVO
                .getCaptcha());
        if (checkCaptcha) {
            throw new BusinessException("验证码错误,请重新获取！");
        }

        personService.forgetPassword(tPersonVO);
        return new ResultInfo<Object>(ResultInfo.SUCCESS, "修改成功", null);
    }

    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "用户id", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "page", value = "当前页数", required = true, dataType = "Integer", paramType = "query"),
            @ApiImplicitParam(name = "rows", value = "条数", required = true, dataType = "Integer", paramType = "query"), })
    @ApiOperation(value = "根据用户id查询用户基本信息", notes = "根据用户id查询用户基本信息")
    @RequestMapping(value = "/findTPersonById", method = RequestMethod.POST)
    @AuthToken(type = "createTemplate")
    public ResultInfo<TPerson> findTPersonById( @RequestBody(required=false) TPersonVO personVO) {
        if (personVO.getId() == null) {
            throw new BusinessException("id不能为空");
        }
        return new ResultInfo<TPerson>(ResultInfo.SUCCESS, "成功", personService.findById(personVO.getId()));
    }

}
