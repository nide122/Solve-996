package com.createTemplate.model.core.pojo;
import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Id;

import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiModel;
@Entity(name = "T_PERSON")
@ApiModel(value = "用户表")
@SuppressWarnings("serial")
/*** 用户表***/
public class TPerson implements Serializable{
	@ApiModelProperty(value="主键")
	@Id
	private Long id;
	@ApiModelProperty(value="手机号，登录账号")
	private Long phone_num;
	@ApiModelProperty(value="密码")
	private String password;
	@ApiModelProperty(value="昵称")
	private String alias_name;
	@ApiModelProperty(value="头像")
	private String photo;
	@ApiModelProperty(value="上次登录时间")
	private Date last_login;
	@ApiModelProperty(value="注册时间")
	private Date input_date;
	@ApiModelProperty(value="出生年月 格式：yyyy-mm-dd")
	@DateTimeFormat(pattern="yyyy-mm-dd")
	private Date birthday;
	@ApiModelProperty(value="性别  0男  1女")
	private Integer sex;
	@ApiModelProperty(value="籍贯")
	private String native_place;
	@ApiModelProperty(value="婚姻状况 0未婚 1丧偶 2离异无子 3离异有子 4已婚")
	private Integer marital_status;
	@ApiModelProperty(value="工作地点省代码")
	private String job_place_province_code;
	@ApiModelProperty(value="工作地点市代码")
	private String job_place_city_code;
	@ApiModelProperty(value="工作地点区代码")
	private String job_place_areas_code;
	@ApiModelProperty(value="兴趣爱好")
	private String hobbies;
	@ApiModelProperty(value="星座 1白羊座 2金牛座 3双子座 4巨蟹座 5狮子座 6处女座 7天秤座 8天蝎座 9射手座 10摩羯座 11水瓶座 12双鱼座")
	private Integer constellation;
	@ApiModelProperty(value="生肖 1鼠 2牛 3虎 4兔 5龙 6蛇 7马 8羊  9猴 10鸡 11狗 12猪")
	private Integer chinese_zodiac;
	@ApiModelProperty(value="是否公开隐私设置  0不公开   1公开")
	private Integer open_privacy_flag;
	@ApiModelProperty(value="是否是VIP   0不是   1是")
	private Integer vip_flag;
	@ApiModelProperty(value="vipk开始时间")
	private Date vip_begin_date;
	@ApiModelProperty(value="vip到期时间")
	private Date vip_end_date;
	@ApiModelProperty(value="修改时间")
	private Date update_date;
	
	@ApiModelProperty(value="推荐人")
    private String referrer_name;
	
	@ApiModelProperty(value="推荐人电话")
    private String referrer_phone;
	
	
	
    public String getReferrer_name() {
        return referrer_name;
    }
    
    public void setReferrer_name(String referrer_name) {
        this.referrer_name = referrer_name;
    }
    
    public String getReferrer_phone() {
        return referrer_phone;
    }
    
    public void setReferrer_phone(String referrer_phone) {
        this.referrer_phone = referrer_phone;
    }
    public void setId(Long id){
		this.id = id;
	}
	public Long getId(){
		return id;
	}
	public void setPhone_num(Long phone_num){
		this.phone_num = phone_num;
	}
	public Long getPhone_num(){
		return phone_num;
	}
	public void setPassword(String password){
		this.password = password;
	}
	public String getPassword(){
		return password;
	}
	public void setAlias_name(String alias_name){
		this.alias_name = alias_name;
	}
	public String getAlias_name(){
		return alias_name;
	}
	public void setPhoto(String photo){
		this.photo = photo;
	}
	public String getPhoto(){
		return photo;
	}
	public void setLast_login(Date last_login){
		this.last_login = last_login;
	}
	public Date getLast_login(){
		return last_login;
	}
	public void setInput_date(Date input_date){
		this.input_date = input_date;
	}
	public Date getInput_date(){
		return input_date;
	}
	public void setBirthday(Date birthday){
		this.birthday = birthday;
	}
	public Date getBirthday(){
		return birthday;
	}
	public void setSex(Integer sex){
		this.sex = sex;
	}
	public Integer getSex(){
		return sex;
	}
	public void setNative_place(String native_place){
		this.native_place = native_place;
	}
	public String getNative_place(){
		return native_place;
	}
	public void setMarital_status(Integer marital_status){
		this.marital_status = marital_status;
	}
	public Integer getMarital_status(){
		return marital_status;
	}
	public void setJob_place_province_code(String job_place_province_code){
		this.job_place_province_code = job_place_province_code;
	}
	public String getJob_place_province_code(){
		return job_place_province_code;
	}
	public void setJob_place_city_code(String job_place_city_code){
		this.job_place_city_code = job_place_city_code;
	}
	public String getJob_place_city_code(){
		return job_place_city_code;
	}
	public void setJob_place_areas_code(String job_place_areas_code){
		this.job_place_areas_code = job_place_areas_code;
	}
	public String getJob_place_areas_code(){
		return job_place_areas_code;
	}
	public void setHobbies(String hobbies){
		this.hobbies = hobbies;
	}
	public String getHobbies(){
		return hobbies;
	}
	public void setConstellation(Integer constellation){
		this.constellation = constellation;
	}
	public Integer getConstellation(){
		return constellation;
	}
	public void setChinese_zodiac(Integer chinese_zodiac){
		this.chinese_zodiac = chinese_zodiac;
	}
	public Integer getChinese_zodiac(){
		return chinese_zodiac;
	}
	public void setOpen_privacy_flag(Integer open_privacy_flag){
		this.open_privacy_flag = open_privacy_flag;
	}
	public Integer getOpen_privacy_flag(){
		return open_privacy_flag;
	}
	public void setVip_flag(Integer vip_flag){
		this.vip_flag = vip_flag;
	}
	public Integer getVip_flag(){
		return vip_flag;
	}
	public void setVip_begin_date(Date vip_begin_date){
		this.vip_begin_date = vip_begin_date;
	}
	public Date getVip_begin_date(){
		return vip_begin_date;
	}
	public void setVip_end_date(Date vip_end_date){
		this.vip_end_date = vip_end_date;
	}
	public Date getVip_end_date(){
		return vip_end_date;
	}
	public void setUpdate_date(Date update_date){
		this.update_date = update_date;
	}
	public Date getUpdate_date(){
		return update_date;
	}
}