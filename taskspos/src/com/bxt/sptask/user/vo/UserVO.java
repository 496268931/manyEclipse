package com.bxt.sptask.user.vo;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import com.bxt.sptask.common.utils.QueryCondition;


/**
 * Description: UsersVO 用户VO实体 

 * All Rights Reserved.
 * 
 * @version V1.0 2017-02-16 下午 02:37:48 星期四
 * @author wxd (wxd@inter3i.com)
 */
public class UserVO extends QueryCondition {
    private static final long serialVersionUID = 468151513732989820L;
    
    private Integer userid; // 用户ID
    private Integer alloweditinfo; // 是否允许修改个人信息
    private Integer binduserid; // usertype为2时，此字段表示绑定的用户
    private String email; // 邮件地址
    private Integer expiretime; // 账号失效时间
    private Integer isonline; // 是否在线0.离线1.在线
    private Integer onlinetime; // 在线时长
    private String password; // 密码
    private String realname; // 真实姓名
    private Integer tenantid; // 租户ID
    private Integer updatetime; // updatetime
    private String username; // 用户名
    private Integer usertype; // 用户类型，1普通用户，2只读用户（绑定到一个普通用户）
	
    /**
     * Description: 属性 userid 的get方法
     * 
     * @version V1.0  2017-02-16 下午 02:37:48 星期四
     * @author wxd(wxd@inter3i.com)
     * @return Integer 
     */
    public Integer getUserid() {
        return userid;
    }
     
    /**
     * Description: 属性 userid 的set方法
     * 
     * @version V1.0  2017-02-16 下午 02:37:48 星期四
     * @author wxd(wxd@inter3i.com)
     * @param  userid Integer 
     * @return void 
     */
    public void setUserid(Integer userid) {
        this.userid = userid;
    }
    /**
     * Description: 属性 alloweditinfo 的get方法
     * 
     * @version V1.0  2017-02-16 下午 02:37:48 星期四
     * @author wxd(wxd@inter3i.com)
     * @return Integer 
     */
    public Integer getAlloweditinfo() {
        return alloweditinfo;
    }
     
    /**
     * Description: 属性 alloweditinfo 的set方法
     * 
     * @version V1.0  2017-02-16 下午 02:37:48 星期四
     * @author wxd(wxd@inter3i.com)
     * @param  alloweditinfo Integer 
     * @return void 
     */
    public void setAlloweditinfo(Integer alloweditinfo) {
        this.alloweditinfo = alloweditinfo;
    }
    /**
     * Description: 属性 binduserid 的get方法
     * 
     * @version V1.0  2017-02-16 下午 02:37:48 星期四
     * @author wxd(wxd@inter3i.com)
     * @return Integer 
     */
    public Integer getBinduserid() {
        return binduserid;
    }
     
    /**
     * Description: 属性 binduserid 的set方法
     * 
     * @version V1.0  2017-02-16 下午 02:37:48 星期四
     * @author wxd(wxd@inter3i.com)
     * @param  binduserid Integer 
     * @return void 
     */
    public void setBinduserid(Integer binduserid) {
        this.binduserid = binduserid;
    }
    /**
     * Description: 属性 email 的get方法
     * 
     * @version V1.0  2017-02-16 下午 02:37:48 星期四
     * @author wxd(wxd@inter3i.com)
     * @return String 
     */
    public String getEmail() {
        return email;
    }
     
    /**
     * Description: 属性 email 的set方法
     * 
     * @version V1.0  2017-02-16 下午 02:37:48 星期四
     * @author wxd(wxd@inter3i.com)
     * @param  email String 
     * @return void 
     */
    public void setEmail(String email) {
        this.email = email;
    }
    /**
     * Description: 属性 expiretime 的get方法
     * 
     * @version V1.0  2017-02-16 下午 02:37:48 星期四
     * @author wxd(wxd@inter3i.com)
     * @return Integer 
     */
    public Integer getExpiretime() {
        return expiretime;
    }
     
    /**
     * Description: 属性 expiretime 的set方法
     * 
     * @version V1.0  2017-02-16 下午 02:37:48 星期四
     * @author wxd(wxd@inter3i.com)
     * @param  expiretime Integer 
     * @return void 
     */
    public void setExpiretime(Integer expiretime) {
        this.expiretime = expiretime;
    }
    /**
     * Description: 属性 isonline 的get方法
     * 
     * @version V1.0  2017-02-16 下午 02:37:48 星期四
     * @author wxd(wxd@inter3i.com)
     * @return Integer 
     */
    public Integer getIsonline() {
        return isonline;
    }
     
    /**
     * Description: 属性 isonline 的set方法
     * 
     * @version V1.0  2017-02-16 下午 02:37:48 星期四
     * @author wxd(wxd@inter3i.com)
     * @param  isonline Integer 
     * @return void 
     */
    public void setIsonline(Integer isonline) {
        this.isonline = isonline;
    }
    /**
     * Description: 属性 onlinetime 的get方法
     * 
     * @version V1.0  2017-02-16 下午 02:37:48 星期四
     * @author wxd(wxd@inter3i.com)
     * @return Integer 
     */
    public Integer getOnlinetime() {
        return onlinetime;
    }
     
    /**
     * Description: 属性 onlinetime 的set方法
     * 
     * @version V1.0  2017-02-16 下午 02:37:48 星期四
     * @author wxd(wxd@inter3i.com)
     * @param  onlinetime Integer 
     * @return void 
     */
    public void setOnlinetime(Integer onlinetime) {
        this.onlinetime = onlinetime;
    }
    /**
     * Description: 属性 password 的get方法
     * 
     * @version V1.0  2017-02-16 下午 02:37:48 星期四
     * @author wxd(wxd@inter3i.com)
     * @return String 
     */
    public String getPassword() {
        return password;
    }
     
    /**
     * Description: 属性 password 的set方法
     * 
     * @version V1.0  2017-02-16 下午 02:37:48 星期四
     * @author wxd(wxd@inter3i.com)
     * @param  password String 
     * @return void 
     */
    public void setPassword(String password) {
        this.password = password;
    }
    /**
     * Description: 属性 realname 的get方法
     * 
     * @version V1.0  2017-02-16 下午 02:37:48 星期四
     * @author wxd(wxd@inter3i.com)
     * @return String 
     */
    public String getRealname() {
        return realname;
    }
     
    /**
     * Description: 属性 realname 的set方法
     * 
     * @version V1.0  2017-02-16 下午 02:37:48 星期四
     * @author wxd(wxd@inter3i.com)
     * @param  realname String 
     * @return void 
     */
    public void setRealname(String realname) {
        this.realname = realname;
    }
    /**
     * Description: 属性 tenantid 的get方法
     * 
     * @version V1.0  2017-02-16 下午 02:37:48 星期四
     * @author wxd(wxd@inter3i.com)
     * @return Integer 
     */
    public Integer getTenantid() {
        return tenantid;
    }
     
    /**
     * Description: 属性 tenantid 的set方法
     * 
     * @version V1.0  2017-02-16 下午 02:37:48 星期四
     * @author wxd(wxd@inter3i.com)
     * @param  tenantid Integer 
     * @return void 
     */
    public void setTenantid(Integer tenantid) {
        this.tenantid = tenantid;
    }
    /**
     * Description: 属性 updatetime 的get方法
     * 
     * @version V1.0  2017-02-16 下午 02:37:48 星期四
     * @author wxd(wxd@inter3i.com)
     * @return Integer 
     */
    public Integer getUpdatetime() {
        return updatetime;
    }
     
    /**
     * Description: 属性 updatetime 的set方法
     * 
     * @version V1.0  2017-02-16 下午 02:37:48 星期四
     * @author wxd(wxd@inter3i.com)
     * @param  updatetime Integer 
     * @return void 
     */
    public void setUpdatetime(Integer updatetime) {
        this.updatetime = updatetime;
    }
    /**
     * Description: 属性 username 的get方法
     * 
     * @version V1.0  2017-02-16 下午 02:37:48 星期四
     * @author wxd(wxd@inter3i.com)
     * @return String 
     */
    public String getUsername() {
        return username;
    }
     
    /**
     * Description: 属性 username 的set方法
     * 
     * @version V1.0  2017-02-16 下午 02:37:48 星期四
     * @author wxd(wxd@inter3i.com)
     * @param  username String 
     * @return void 
     */
    public void setUsername(String username) {
        this.username = username;
    }
    /**
     * Description: 属性 usertype 的get方法
     * 
     * @version V1.0  2017-02-16 下午 02:37:48 星期四
     * @author wxd(wxd@inter3i.com)
     * @return Integer 
     */
    public Integer getUsertype() {
        return usertype;
    }
     
    /**
     * Description: 属性 usertype 的set方法
     * 
     * @version V1.0  2017-02-16 下午 02:37:48 星期四
     * @author wxd(wxd@inter3i.com)
     * @param  usertype Integer 
     * @return void 
     */
    public void setUsertype(Integer usertype) {
        this.usertype = usertype;
    }

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

	@Override
	public int hashCode() {
		return HashCodeBuilder.reflectionHashCode(this, false);
	}

	@Override
	public boolean equals(final Object obj) {
		return EqualsBuilder.reflectionEquals(this, false);
	}
}
