package com.createTemplate.provider.core.dubbo.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import javax.transaction.Transactional;

import org.apache.commons.lang3.StringUtils;
import org.redisson.Redisson;
import org.redisson.core.RLock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.createTemplate.api.base.dubbo.service.CommonRedisDao;
import com.createTemplate.api.common.redission.DistributedQueue;
import com.createTemplate.api.core.doubbo.service.PersonService;
import com.createTemplate.api.util.DataUtils;
import com.createTemplate.api.util.PropertyUtils;
import com.createTemplate.api.util.StringUtil;
import com.createTemplate.api.util.TokenProcessor;
import com.createTemplate.model.constant.RedisConstant;
import com.createTemplate.model.core.pojo.TPerson;
import com.createTemplate.model.core.vo.TPersonVO;
import com.createTemplate.model.exception.BusinessException;
import com.createTemplate.model.mybatis.page.PageParameter;
import com.createTemplate.model.resultvo.Grid;
import com.createTemplate.provider.base.dao.impl.MybatisDaoImpl;
import com.createTemplate.provider.config.RedisConfig;

@Service(version = "1.0.0", retries = 0, timeout = 6000, parameters = { "sendMessage.timeout",
        "12000" }, interfaceClass = PersonService.class)
public class PersonServiceImpl extends MybatisDaoImpl implements PersonService {

    public final String className = "person";

    private final Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * 通过手机号、密码参数登录并返回token 信息
     * 
     * @author libiqi
     * @date 2018年5月21日
     */
    @Override
    public TPersonVO login(TPersonVO person) {
        if (StringUtils.isBlank(person.getPassword())) {
            throw new BusinessException("缺少密码参数!");
        }
        if (null == person.getPhone_num()) {
            throw new BusinessException("缺少手机号参数!");
        }
        person.setColumn(PropertyUtils.getPropertyNames(TPerson.class, "p"));
        TPersonVO obj = this.baseDao.selectOne(className + ".findPersonByMobile", person);
        if (null == obj) {
            throw new BusinessException("没有此账号!");
        }
        Boolean isPasswordEquals = obj.getPassword().equals(person.getPassword());
        if (isPasswordEquals == false) {
            throw new BusinessException("密码输入不对!");
        }
        // 登录成功将用户信息放置到redis
        Map<String, Object> setPersonToRedis = this.setPersonToRedis(obj);
        obj.setAuthToken(setPersonToRedis.get("token").toString());
        obj.setExpireTime(Long.parseLong(setPersonToRedis.get("expireTime").toString()));
        return obj;
    }

    /**
     * 注册
     * 
     * @author libiqi
     * @date 2018年5月21日
     */
    @Override
    @Transactional
    public TPersonVO register(TPersonVO tPersonVO) {
        RedisConfig redisConfig = applicationContext.getBean(RedisConfig.class);
        String key = "regist_" + tPersonVO.getPhone_num();
        // 加锁(分布式锁)
        Redisson redisson = DistributedQueue.getRedisson(redisConfig.getAddress(), redisConfig.getPassword());
        // 以activityId为锁字段
        RLock rlock = redisson.getLock(key);
        // 如果没有 则加锁 + 有则等待锁释放
        rlock.lock(3, TimeUnit.SECONDS);

        // 检验手机/账号是否重复
        if (checkPersonIsRepeat(tPersonVO.getPhone_num())) {
            throw new BusinessException("已有此账号!");
        }
        // 添加用户表
        TPerson person = new TPerson();
        DataUtils.copyPropertiesIgnoreNull(tPersonVO, person);
        person.setVip_flag(0);
        person.setInput_date(new Date());
        person.setOpen_privacy_flag(0);
        super.save(person);
        DataUtils.copyPropertiesIgnoreNull(person, tPersonVO);
        tPersonVO.setId(person.getId());
        tPersonVO.setPassword(null);
        // 生成token 存入redis 根据id 生成 如果传入token则不重新生成
        Map<String, Object> setPersonToRedis = this.setPersonToRedis(tPersonVO);
        if (rlock != null) {
            rlock.unlock();
        }
        tPersonVO.setAuthToken(setPersonToRedis.get("token").toString());
        return tPersonVO;
    }

    /**
     * 根据手机号判断用户是否已经注册
     * 
     * @param mobile
     * @return
     */
    @Override
    public Boolean checkPersonIsRepeat(Long mobile) {
        TPersonVO findByMobile = findByMobile(mobile);
        return null != findByMobile;
    }

    /**
     * 退出登录 清空用户session
     * 
     * @author libiqi
     * @date 2018年5月21日
     */
    @Override
    public void logout(TPersonVO person) {
        this.clearPersonRedis(person.getId());
    }

    /**
     * 根据ID更新用户信息
     * 
     * @author libiqi
     * @date 2018年5月21日
     */
    @Override
    public TPerson updatePersonById(TPerson obj) {
        TPerson oldObj = findById(obj.getId());
        DataUtils.copyPropertiesIgnoreNull(obj, oldObj);
        super.update(oldObj);
        // 如果更新头像、昵称 更新session信息
        if (StringUtils.isNotBlank(obj.getPhoto()) || StringUtils.isNotBlank(obj.getAlias_name())) {
            TPersonVO findPersonDetailById = findPersonDetailById(obj.getId());
            updateSessionByUid(findPersonDetailById);
        }
        return oldObj;
    }

    /**
     * 通过ID 查询person
     * 
     * @author libiqi
     * @date 2018年5月14日
     */
    @Override
    public TPerson findById(Long id) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("id", id);
        map.put("column", PropertyUtils.getPropertyNames(TPerson.class));
        return this.baseDao.selectOne(className + ".findById", map);
    }

    /**
     * 分页查询用户信息
     * 
     * @author libiqi
     * @date 2018年5月14日
     */
    @Override
    public Grid findPersonPage(TPersonVO vo) {
        vo.setPageParameter(new PageParameter(vo.getPage(), vo.getRows()));
        vo.setColumn(PropertyUtils.getPropertyNames(TPerson.class, "p"));
        List<TPerson> list = this.baseDao.selectList(className + ".findPersonPage", vo);
        Grid grid = new Grid();
        grid.setCount(Long.valueOf(vo.getPageParameter().getTotalCount()));
        grid.setList(list);
        return grid;
    }

    /**
     * 生成token 存入redis 根据id 生成 如果传入token则不重新生成
     * 
     * @author libiqi
     * @date 2018年4月12日
     */
    @Override
    public Map<String, Object> setPersonToRedis(TPersonVO vo) {
        CommonRedisDao commonRedisDao = applicationContext.getBean(CommonRedisDao.class);
        if (null == vo) {
            throw new BusinessException("用户不能为空!");
        }
        this.clearPersonRedis(vo.getId());
        String seed = StringUtil.md5(vo.getId().toString());
        String token;
        Integer expireTime = 60 * 24 * 7;
        if (StringUtils.isBlank(vo.getAuthToken())) {
            token = TokenProcessor.getInstance().generateToken(seed);
        } else {
            token = vo.getAuthToken();
        }
        commonRedisDao.set(RedisConstant.personSeed + seed, token);
        // 设置登录失效 7天
        commonRedisDao.set(RedisConstant.personToken + token, JSON.toJSONString(vo), expireTime);
        Map<String, Object> map = new HashMap<String, Object>();
        // 返回token
        map.put("token", token);
        // 返回失效时间
        map.put("expireTime", expireTime);
        return map;
    }

    /**
     * 清空redis
     * 
     * @param id
     * @author libiqi
     * @date 2018年5月15日
     */
    public void clearPersonRedis(Long id) {
        CommonRedisDao commonRedisDao = applicationContext.getBean(CommonRedisDao.class);
        if (null != id) {
            String seed = StringUtil.md5(id.toString());
            String token = commonRedisDao.get(RedisConstant.personSeed + seed);
            if (null != token) {
                commonRedisDao.delete(RedisConstant.personToken + token);
            }
        } else {
            throw new BusinessException("id不能为空!");
        }
    }

    /**
     * 根据token获取当前用户session
     * 
     * @see com.jindan.springboot.dubbo.consumer.PersonService#getCurrentUser(java.lang.String)
     * @author libiqi
     * @date 2018年5月15日
     */
    @Override
    public TPersonVO getCurrentUser(String authToken) {
        CommonRedisDao commonRedisDao = applicationContext.getBean(CommonRedisDao.class);
        String redisPerson = commonRedisDao.get(RedisConstant.personToken + authToken);
        if (StringUtils.isBlank(redisPerson)) {
            return null;
        }
        TPersonVO vo = JSON.parseObject(redisPerson, TPersonVO.class);
        vo.setAuthToken(authToken);
        return vo;
    }

    /**
     * 根据用户uid 更新用户session
     * 
     * @param obj
     */
    private void updateSessionByUid(TPersonVO findPersonDetailById) {
        CommonRedisDao commonRedisDao = applicationContext.getBean(CommonRedisDao.class);
        // 取用户token
        String seed = StringUtil.md5(findPersonDetailById.getId().toString());
        String token = commonRedisDao.get(RedisConstant.personSeed + seed);
        if (StringUtils.isNotEmpty(token)) {
            findPersonDetailById.setAuthToken(token);
            this.setPersonToRedis(findPersonDetailById);
        }
    }

    /**
     * 根据手机号查找用户
     * 
     * @param mobile
     * @return
     * @see com.jindan.springboot.dubbo.consumer.PersonService#findByMobile(java.lang.String)
     */
    @Override
    public TPersonVO findByMobile(Long mobile) {
        TPersonVO person = new TPersonVO();
        person.setPhone_num(mobile);
        return this.baseDao.selectOne(className + ".findPersonByMobile", person);
    }

    /**
     * 根据用户ID查询用户详情
     * 
     * @param userId
     * @return
     * @see com.jindan.springboot.dubbo.consumer.PersonService#findPersonDetailById(java.lang.Long)
     */
    @Override
    public TPersonVO findPersonDetailById(Long userId) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("id", userId);
        map.put("column", PropertyUtils.getPropertyNames(TPerson.class));
        return baseDao.selectOne(className + ".findPersonDetailById", map);
    }

    /**
     * 忘记密码
     * 
     * @param tPersonVO
     * @throws BusinessException
     * @see com.createTemplate.api.core.doubbo.service.PersonService#forgetPassword(com.createTemplate.model.core.vo.TPersonVO)
     */
    @Override
    @Transactional
    public void forgetPassword(TPersonVO tPersonVO) throws BusinessException {
        TPersonVO dbTPersonVO = this.findByMobile(tPersonVO.getPhone_num());
        if (dbTPersonVO == null) {
            throw new BusinessException("手机号码没注册");
        }
        TPerson newTPerson = new TPerson();
        newTPerson.setId(dbTPersonVO.getId());
        newTPerson.setPassword(tPersonVO.getPassword());
        this.update(newTPerson);
    }

    /**
     * 
     * @param ids
     * @return
     * @throws BusinessException
     * @see com.createTemplate.api.core.doubbo.service.PersonService#findByIds(java.util.Set)
     */
    @Override
    public List<TPerson> findByIds(Set<Long> ids) throws BusinessException {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("ids", ids);
        map.put("column", PropertyUtils.getPropertyNames(TPerson.class));
        return this.baseDao.selectList(className + ".findByIds", map);
    }

    /**
     * @param dbPersonVo
     * @see com.createTemplate.api.core.doubbo.service.PersonService#updatePersonSession(com.createTemplate.model.core.vo.TPersonVO)
     */
    @Override
    public void updatePersonSession(TPersonVO personVo) {
        logger.debug("-------------PersonService.updatePersonSession start");
        if (personVo == null) {
            return;
        }
        CommonRedisDao commonRedisDao = this.applicationContext.getBean(CommonRedisDao.class);
        TPersonVO obj = this.findByMobile(personVo.getPhone_num());
        if (null == obj) {
            return;
        }
        obj.setAuthToken(personVo.getAuthToken());
        obj.setExpireTime(personVo.getExpireTime());
        // 如果用户类型为空，默认不操作
        commonRedisDao.set(RedisConstant.personToken + personVo.getAuthToken(), JSON.toJSONString(obj));
        commonRedisDao.set(RedisConstant.personIdFix + personVo.getPerson_id(), JSON.toJSONString(obj));
        logger.debug("-------------PersonService.updatePersonSession end");
    }

}
