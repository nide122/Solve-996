package com.createTemplate.model.core.vo;
import com.createTemplate.model.core.pojo.TPerson;
import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import io.swagger.annotations.ApiModel;
import com.createTemplate.model.mybatis.page.PageParameter;
import io.swagger.annotations.ApiModelProperty;
@Entity(name = "T_PERSON")
@ApiModel(value = "用户表VO")
@SuppressWarnings("serial")
/*** 用户表***/
public class TPersonVO extends TPerson implements Serializable{
	@ApiModelProperty(value="当前页")
	private Integer page;
	@ApiModelProperty(value="每页的条数")
	private Integer rows;
	@ApiModelProperty(value="分页参数")
	private PageParameter pageParameter;
	@ApiModelProperty(value="列")
	private String column;
	@ApiModelProperty(value="验证码")
	private String captcha;
	
	@ApiModelProperty(value="authToken")
	private String authToken;
	@ApiModelProperty(value="authToken到期时间")
    private Long expireTime;
	
	@ApiModelProperty(value="收入情况")
    private String income;
    @ApiModelProperty(value="姓名")
    private String real_name;
    @ApiModelProperty(value="联系方式  0手机 1微信号 2qq号码")
    private Integer contact_flag;
    @ApiModelProperty(value="联系方式详情")
    private String contact;
    @ApiModelProperty(value="择偶标准")
    private String spouse_criterion;
    @ApiModelProperty(value="恋爱次数")
    private Integer love_count;
    @ApiModelProperty(value="是否想要孩子   0否  1是")
    private Integer kids_flag;
    @ApiModelProperty(value="是否愿意与父母同住   0否  1是")
    private Integer willing_live_with_parents_flag;
    @ApiModelProperty(value="是否独生子女  0否  1是\n ")
    private Integer only_child;
    @ApiModelProperty(value="父母是否有社保医保   0否  1是")
    private Integer parents_social_security_insurance_flag;
    @ApiModelProperty(value="父母是否有商业保险  0否  1是")
    private Integer parents_commercial_insurance_flag;
    @ApiModelProperty(value="本人是否有商业保险   0否  1是")
    private Integer i_commercial_insurance_flag;
    @ApiModelProperty(value="用户Id")
    private Long person_id;
    @ApiModelProperty(value="余额,以分为单位")
    private Integer balance;
    @ApiModelProperty(value="总积分")
    private Integer integral;
    
    
    @ApiModelProperty(value="单位性质 0政府机关 1事业单位 2外资企业 3上市公司 4国有企业 5私营企业 6自有公司")
    private Integer nature_unit;
    @ApiModelProperty(value="单位")
    private String unit;
    @ApiModelProperty(value="职业类别 数据字典表维护")
    private Long occupational_class_flag;
    @ApiModelProperty(value="职务")
    private String duty;
    @ApiModelProperty(value="学历 数据字典维护")
    private String edu_back_ground;
    @ApiModelProperty(value="邮箱")
    private String email;
    @ApiModelProperty(value="婚姻情况")
    private String marital_status;
    @ApiModelProperty(value="小孩数量(离婚有孩才需要写)")
    private Integer kid_count;
    @ApiModelProperty(value="出生年份(离婚有孩才需要写)")
    private String kid_year;
    @ApiModelProperty(value="身高")
    private Double stature;
    @ApiModelProperty(value="体重,只保存整数")
    private Integer weight;
    @ApiModelProperty(value="民族 数据字段维护")
    private Long nation;
    @ApiModelProperty(value="个人标签 多个标签用逗号隔开")
    private String label;
    @ApiModelProperty(value="是否吸烟   0有吸烟习惯 1社交偶尔吸烟 2不吸烟但不反感  3不吸烟且反感")
    private Integer smoke_flag;
    @ApiModelProperty(value="是否喝酒 0有喝酒习惯 1社交偶尔喝酒 2不喝酒")
    private Integer drink_flag;
    @ApiModelProperty(value="是否购房 0老家已购房 1现居住地已购房 2无购房")
    private Integer purchase_flag;
    @ApiModelProperty(value="是否购车 0已购车 1无购车")
    private Integer vehicle_flag;
    @ApiModelProperty(value="父母所在地")
    private String Parents_location;
    @ApiModelProperty(value="是否有宠物 0否 1是")
    private Integer pet_flag;
    @ApiModelProperty(value="宠物情况")
    private String pet_condition;
    @ApiModelProperty(value="对象要求")
    private String object_request;
    
    
    
    
    
    public String getIncome() {
        return income;
    }


    
    public void setIncome(String income) {
        this.income = income;
    }


    
    public String getReal_name() {
        return real_name;
    }


    
    public void setReal_name(String real_name) {
        this.real_name = real_name;
    }


    
    public Integer getContact_flag() {
        return contact_flag;
    }


    
    public void setContact_flag(Integer contact_flag) {
        this.contact_flag = contact_flag;
    }


    
    public String getContact() {
        return contact;
    }


    
    public void setContact(String contact) {
        this.contact = contact;
    }


    
    public String getSpouse_criterion() {
        return spouse_criterion;
    }


    
    public void setSpouse_criterion(String spouse_criterion) {
        this.spouse_criterion = spouse_criterion;
    }


    
    public Integer getLove_count() {
        return love_count;
    }


    
    public void setLove_count(Integer love_count) {
        this.love_count = love_count;
    }


    
    public Integer getKids_flag() {
        return kids_flag;
    }


    
    public void setKids_flag(Integer kids_flag) {
        this.kids_flag = kids_flag;
    }


    
    public Integer getWilling_live_with_parents_flag() {
        return willing_live_with_parents_flag;
    }


    
    public void setWilling_live_with_parents_flag(Integer willing_live_with_parents_flag) {
        this.willing_live_with_parents_flag = willing_live_with_parents_flag;
    }


    
    public Integer getOnly_child() {
        return only_child;
    }


    
    public void setOnly_child(Integer only_child) {
        this.only_child = only_child;
    }


    
    public Integer getParents_social_security_insurance_flag() {
        return parents_social_security_insurance_flag;
    }


    
    public void setParents_social_security_insurance_flag(Integer parents_social_security_insurance_flag) {
        this.parents_social_security_insurance_flag = parents_social_security_insurance_flag;
    }


    
    public Integer getParents_commercial_insurance_flag() {
        return parents_commercial_insurance_flag;
    }


    
    public void setParents_commercial_insurance_flag(Integer parents_commercial_insurance_flag) {
        this.parents_commercial_insurance_flag = parents_commercial_insurance_flag;
    }


    
    public Integer getI_commercial_insurance_flag() {
        return i_commercial_insurance_flag;
    }


    
    public void setI_commercial_insurance_flag(Integer i_commercial_insurance_flag) {
        this.i_commercial_insurance_flag = i_commercial_insurance_flag;
    }


    
    public Long getPerson_id() {
        return person_id;
    }


    
    public void setPerson_id(Long person_id) {
        this.person_id = person_id;
    }


    
    public Integer getBalance() {
        return balance;
    }


    
    public void setBalance(Integer balance) {
        this.balance = balance;
    }


    
    public Integer getIntegral() {
        return integral;
    }


    
    public void setIntegral(Integer integral) {
        this.integral = integral;
    }


    
    public Integer getNature_unit() {
        return nature_unit;
    }


    
    public void setNature_unit(Integer nature_unit) {
        this.nature_unit = nature_unit;
    }


    
    public String getUnit() {
        return unit;
    }


    
    public void setUnit(String unit) {
        this.unit = unit;
    }


    
    public Long getOccupational_class_flag() {
        return occupational_class_flag;
    }


    
    public void setOccupational_class_flag(Long occupational_class_flag) {
        this.occupational_class_flag = occupational_class_flag;
    }


    
    public String getDuty() {
        return duty;
    }


    
    public void setDuty(String duty) {
        this.duty = duty;
    }


    
    public String getEdu_back_ground() {
        return edu_back_ground;
    }


    
    public void setEdu_back_ground(String edu_back_ground) {
        this.edu_back_ground = edu_back_ground;
    }


    
    public String getEmail() {
        return email;
    }


    
    public void setEmail(String email) {
        this.email = email;
    }


    


    
    public void setMarital_status(String marital_status) {
        this.marital_status = marital_status;
    }


    
    public Integer getKid_count() {
        return kid_count;
    }


    
    public void setKid_count(Integer kid_count) {
        this.kid_count = kid_count;
    }


    
    public String getKid_year() {
        return kid_year;
    }


    
    public void setKid_year(String kid_year) {
        this.kid_year = kid_year;
    }


    
    public Double getStature() {
        return stature;
    }


    
    public void setStature(Double stature) {
        this.stature = stature;
    }


    
    public Integer getWeight() {
        return weight;
    }


    
    public void setWeight(Integer weight) {
        this.weight = weight;
    }


    
    public Long getNation() {
        return nation;
    }


    
    public void setNation(Long nation) {
        this.nation = nation;
    }


    
    public String getLabel() {
        return label;
    }


    
    public void setLabel(String label) {
        this.label = label;
    }


    
    public Integer getSmoke_flag() {
        return smoke_flag;
    }


    
    public void setSmoke_flag(Integer smoke_flag) {
        this.smoke_flag = smoke_flag;
    }


    
    public Integer getDrink_flag() {
        return drink_flag;
    }


    
    public void setDrink_flag(Integer drink_flag) {
        this.drink_flag = drink_flag;
    }


    
    public Integer getPurchase_flag() {
        return purchase_flag;
    }


    
    public void setPurchase_flag(Integer purchase_flag) {
        this.purchase_flag = purchase_flag;
    }


    
    public Integer getVehicle_flag() {
        return vehicle_flag;
    }


    
    public void setVehicle_flag(Integer vehicle_flag) {
        this.vehicle_flag = vehicle_flag;
    }


    
    public String getParents_location() {
        return Parents_location;
    }


    
    public void setParents_location(String parents_location) {
        Parents_location = parents_location;
    }


    
    public Integer getPet_flag() {
        return pet_flag;
    }


    
    public void setPet_flag(Integer pet_flag) {
        this.pet_flag = pet_flag;
    }


    
    public String getPet_condition() {
        return pet_condition;
    }


    
    public void setPet_condition(String pet_condition) {
        this.pet_condition = pet_condition;
    }


    
    public String getObject_request() {
        return object_request;
    }


    
    public void setObject_request(String object_request) {
        this.object_request = object_request;
    }


    public Long getExpireTime() {
        return expireTime;
    }

    
    public void setExpireTime(Long expireTime) {
        this.expireTime = expireTime;
    }

    public String getCaptcha() {
        return captcha;
    }
    
    public void setCaptcha(String captcha) {
        this.captcha = captcha;
    }
    public void setPage(Integer page){
		this.page = page;
	}
	public Integer getPage(){
		return page;
	}
	public void setRows(Integer rows){
		this.rows = rows;
	}
	public Integer getRows(){
		return rows;
	}
	public void setPageParameter(PageParameter pageParameter){
		this.pageParameter = pageParameter;
	}
	public PageParameter getPageParameter(){
		return pageParameter;
	}
	public void setColumn(String column){
		this.column = column;
	}
	public String getColumn(){
		return column;
	}
    
    public String getAuthToken() {
        return authToken;
    }
    
    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }
	

}