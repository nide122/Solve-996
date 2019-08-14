package com.createTemplate.provider.solrcloud;

import java.util.Date;

import org.apache.solr.client.solrj.beans.Field;
import org.springframework.data.solr.core.mapping.SolrDocument;

import com.createTemplate.model.annotation.AntField;

import io.swagger.annotations.ApiModelProperty;

/**
 * 公司
 * @version 1.0
 */
@SolrDocument(solrCoreName = "companyCollection")
public class CompanySolr {
	private static final long serialVersionUID = -6626977647959072701L;
	/**id*/
	@Field
	private Long id;

	/**修改者用户编号 */
    @Field
    private String update_cust_id;
    
    /** 企业编号*/
    @Field
    private String ent_cust_id; 
    
    /** 公司logo */
    @Field
    private String company_logo;
    
    /** 公司名称 */
    @Field
    private String company_name;
    
    /** 公司地址 */
    @Field
    private String companAddress;
    
    /** 公司电话 */
    @Field
    private String phone;
    
    /** 公司网址 */
    @Field
    private String company_url;

    /** 公司简介 */
    @Field
    private String company_remark;

    /** 行业领域 多个逗号分隔 数据字典维护 */
    @Field
    private String business_type;
    
    /** 公司介绍 */
    @Field
    private String company_introduct;
    
    /** 人数标志 0 15-50;1 50-150;2 150-500;3 500-2000;4 2000以上 */
    @Field
    private String employee_count_flag;

    /** 接收简历邮箱 */
    @Field
    private String receive_email;

    /** 公司信息完整度 */
    @Field
    private Integer company_complete;
    
    /** 公司照片 多个逗号分隔；第一个地址是首页 */
    @Field
    private String company_img_url;
    /** 更新时间 */
    @Field
    private Date update_date;
    
    /** 插入时间 */
    @Field
    private Date input_date;
    
    /** 公司简称 */
    @Field
    private String company_short_name;
    
    /** 公司状态 0 正常；1 禁用 */
    @Field
    private Integer status;
    
    /** 招聘热线 */
    @Field
    private String recruitment_hotline;
    /** 审核人用户编码 */
    @Field
    private String  auth_person_id ;
    /** 审核时间 */
    @Field
    private Date auth_date;
    /** 审核状态 0 未审核 1 已审核  默认是0 */
    @Field
    private Integer auth_status;
    /** 校园升级失败原因 */
    @Field
    private String school_auth_reason;
    /** 校园升级材料，图片文件地址，多个地址逗号隔开 */
    @Field
    private String school_auth_data;
    /** 校园升级申请时间 */
    @Field
    private Date school_auth_time;
    /**人才市场升级失败原因 */
    @Field
    private String market_auth_reason;
    /** 人才市场升级材料，图片文件地址，多个地址逗号隔开 */
    @Field
    private String market_auth_data;
    /** 人才市场升级申请时间*/
    @Field
    private Date market_auth_time;
    /** 是否已升级为校园 0 未升级 1 升级申请中 2 已升级 3 升级申请不通过*/
    @Field
    private Integer is_school_auth;
    /** 是否已升级为人才市场 0 未升级 1 升级申请中 2 已升级 3 升级申请不通过 */
    @Field
    private Integer is_market_auth;
	
    /** 当前公司地址省份代码 */
    @Field
    private String company_place_provincecode;
    
    /** 当前公司地址城市代码 */
    @Field
    private String company_place_citycode;

    /** 公司标签数量 */
    @Field
    private Integer tab_count;

    /** 公司标签，多个逗号分隔 */
    @Field
    private String tab_name;
    
    /** 招聘职位数 */
    @Field
    private Integer position_count;

    /** 面试评价数量 */
    @Field
    private Integer evaluate_count;
    
    /** 是否热门搜索公司 0 不是 1 是  默认是0司 */
    @Field
    private Integer hotsearch_flag;
    /** 搜索次数 */
    @Field
    private Integer search_count;
    
    /** 简历处理平均用时 */
    @Field
    private Integer deal_resume_day;
    
    /** 简历及时处理率 */
    @Field
    private Integer deal_resume_rage;

    
    public Long getId() {
        return id;
    }

    
    public void setId(Long id) {
        this.id = id;
    }

    
    public String getUpdate_cust_id() {
        return update_cust_id;
    }

    
    public void setUpdate_cust_id(String update_cust_id) {
        this.update_cust_id = update_cust_id;
    }

    
    public String getEnt_cust_id() {
        return ent_cust_id;
    }

    
    public void setEnt_cust_id(String ent_cust_id) {
        this.ent_cust_id = ent_cust_id;
    }

    
    public String getCompany_logo() {
        return company_logo;
    }

    
    public void setCompany_logo(String company_logo) {
        this.company_logo = company_logo;
    }

    
    public String getCompany_name() {
        return company_name;
    }

    
    public void setCompany_name(String company_name) {
        this.company_name = company_name;
    }

    
    public String getPhone() {
        return phone;
    }

    
    public void setPhone(String phone) {
        this.phone = phone;
    }

    
    public String getCompany_url() {
        return company_url;
    }

    
    public void setCompany_url(String company_url) {
        this.company_url = company_url;
    }

    
    public String getCompany_remark() {
        return company_remark;
    }

    
    public void setCompany_remark(String company_remark) {
        this.company_remark = company_remark;
    }

    
    public String getBusiness_type() {
        return business_type;
    }

    
    public void setBusiness_type(String business_type) {
        this.business_type = business_type;
    }

    
    public String getCompany_introduct() {
        return company_introduct;
    }

    
    public void setCompany_introduct(String company_introduct) {
        this.company_introduct = company_introduct;
    }

    
    public String getEmployee_count_flag() {
        return employee_count_flag;
    }

    
    public void setEmployee_count_flag(String employee_count_flag) {
        this.employee_count_flag = employee_count_flag;
    }

    
    public String getReceive_email() {
        return receive_email;
    }

    
    public void setReceive_email(String receive_email) {
        this.receive_email = receive_email;
    }

    
    public Integer getCompany_complete() {
        return company_complete;
    }

    
    public void setCompany_complete(Integer company_complete) {
        this.company_complete = company_complete;
    }

    
    public String getCompany_img_url() {
        return company_img_url;
    }

    
    public void setCompany_img_url(String company_img_url) {
        this.company_img_url = company_img_url;
    }

    
    public Date getUpdate_date() {
        return update_date;
    }

    
    public void setUpdate_date(Date update_date) {
        this.update_date = update_date;
    }

    
    public Date getInput_date() {
        return input_date;
    }

    
    public void setInput_date(Date input_date) {
        this.input_date = input_date;
    }

    
    public String getCompany_short_name() {
        return company_short_name;
    }

    
    public void setCompany_short_name(String company_short_name) {
        this.company_short_name = company_short_name;
    }

    
    public Integer getStatus() {
        return status;
    }

    
    public void setStatus(Integer status) {
        this.status = status;
    }

    
    public String getRecruitment_hotline() {
        return recruitment_hotline;
    }

    
    public void setRecruitment_hotline(String recruitment_hotline) {
        this.recruitment_hotline = recruitment_hotline;
    }

    
    public String getAuth_person_id() {
        return auth_person_id;
    }

    
    public void setAuth_person_id(String auth_person_id) {
        this.auth_person_id = auth_person_id;
    }

    
    public Date getAuth_date() {
        return auth_date;
    }

    
    public void setAuth_date(Date auth_date) {
        this.auth_date = auth_date;
    }

    
    public Integer getAuth_status() {
        return auth_status;
    }

    
    public void setAuth_status(Integer auth_status) {
        this.auth_status = auth_status;
    }

    
    public String getSchool_auth_reason() {
        return school_auth_reason;
    }

    
    public void setSchool_auth_reason(String school_auth_reason) {
        this.school_auth_reason = school_auth_reason;
    }

    
    public String getSchool_auth_data() {
        return school_auth_data;
    }

    
    public void setSchool_auth_data(String school_auth_data) {
        this.school_auth_data = school_auth_data;
    }

    
    public Date getSchool_auth_time() {
        return school_auth_time;
    }

    
    
    public String getCompanAddress() {
        return companAddress;
    }


    
    public void setCompanAddress(String companAddress) {
        this.companAddress = companAddress;
    }


    public void setSchool_auth_time(Date school_auth_time) {
        this.school_auth_time = school_auth_time;
    }

    
    public String getMarket_auth_reason() {
        return market_auth_reason;
    }

    
    public void setMarket_auth_reason(String market_auth_reason) {
        this.market_auth_reason = market_auth_reason;
    }

    
    public String getMarket_auth_data() {
        return market_auth_data;
    }

    
    public void setMarket_auth_data(String market_auth_data) {
        this.market_auth_data = market_auth_data;
    }

    
    public Date getMarket_auth_time() {
        return market_auth_time;
    }

    
    public void setMarket_auth_time(Date market_auth_time) {
        this.market_auth_time = market_auth_time;
    }

    
    public Integer getIs_school_auth() {
        return is_school_auth;
    }

    
    public void setIs_school_auth(Integer is_school_auth) {
        this.is_school_auth = is_school_auth;
    }

    
    public Integer getIs_market_auth() {
        return is_market_auth;
    }

    
    public void setIs_market_auth(Integer is_market_auth) {
        this.is_market_auth = is_market_auth;
    }

    
    public String getCompany_place_provincecode() {
        return company_place_provincecode;
    }

    
    public void setCompany_place_provincecode(String company_place_provincecode) {
        this.company_place_provincecode = company_place_provincecode;
    }

    
    public String getCompany_place_citycode() {
        return company_place_citycode;
    }

    
    public void setCompany_place_citycode(String company_place_citycode) {
        this.company_place_citycode = company_place_citycode;
    }

    
    public Integer getTab_count() {
        return tab_count;
    }

    
    public void setTab_count(Integer tab_count) {
        this.tab_count = tab_count;
    }

    
    public String getTab_name() {
        return tab_name;
    }

    
    public void setTab_name(String tab_name) {
        this.tab_name = tab_name;
    }

    
    public Integer getPosition_count() {
        return position_count;
    }

    
    public void setPosition_count(Integer position_count) {
        this.position_count = position_count;
    }

    
    public Integer getEvaluate_count() {
        return evaluate_count;
    }

    
    public void setEvaluate_count(Integer evaluate_count) {
        this.evaluate_count = evaluate_count;
    }

    
    public Integer getHotsearch_flag() {
        return hotsearch_flag;
    }

    
    public void setHotsearch_flag(Integer hotsearch_flag) {
        this.hotsearch_flag = hotsearch_flag;
    }

    
    public Integer getSearch_count() {
        return search_count;
    }

    
    public void setSearch_count(Integer search_count) {
        this.search_count = search_count;
    }

    
    public Integer getDeal_resume_day() {
        return deal_resume_day;
    }

    
    public void setDeal_resume_day(Integer deal_resume_day) {
        this.deal_resume_day = deal_resume_day;
    }

    
    public Integer getDeal_resume_rage() {
        return deal_resume_rage;
    }

    
    public void setDeal_resume_rage(Integer deal_resume_rage) {
        this.deal_resume_rage = deal_resume_rage;
    }
	
	
	
}
