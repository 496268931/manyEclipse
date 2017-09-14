package com.bxt.sptask.spideraccount.vo;

public class SpideraccountVO {
    private Integer id; // 账号ID
    private Integer inuse; // 记录帐号使用次数,每次使用时取inuse最小的
    private String password; // 密码
    private Integer sourceid; // 账号来源
    private String username; // 用户名
	
    /**
     * Description: 属性 id 的get方法
     * 
     * @version V1.0  2017-03-13 下午 05:04:56 星期一
     * @author wxd(wxd@inter3i.com)
     * @return Integer 
     */
    public Integer getId() {
        return id;
    }
     
    /**
     * Description: 属性 id 的set方法
     * 
     * @version V1.0  2017-03-13 下午 05:04:56 星期一
     * @author wxd(wxd@inter3i.com)
     * @param  id Integer 
     * @return void 
     */
    public void setId(Integer id) {
        this.id = id;
    }
    /**
     * Description: 属性 inuse 的get方法
     * 
     * @version V1.0  2017-03-13 下午 05:04:56 星期一
     * @author wxd(wxd@inter3i.com)
     * @return Integer 
     */
    public Integer getInuse() {
        return inuse;
    }
     
    /**
     * Description: 属性 inuse 的set方法
     * 
     * @version V1.0  2017-03-13 下午 05:04:56 星期一
     * @author wxd(wxd@inter3i.com)
     * @param  inuse Integer 
     * @return void 
     */
    public void setInuse(Integer inuse) {
        this.inuse = inuse;
    }
    /**
     * Description: 属性 password 的get方法
     * 
     * @version V1.0  2017-03-13 下午 05:04:56 星期一
     * @author wxd(wxd@inter3i.com)
     * @return String 
     */
    public String getPassword() {
        return password;
    }
     
    /**
     * Description: 属性 password 的set方法
     * 
     * @version V1.0  2017-03-13 下午 05:04:56 星期一
     * @author wxd(wxd@inter3i.com)
     * @param  password String 
     * @return void 
     */
    public void setPassword(String password) {
        this.password = password;
    }
    /**
     * Description: 属性 sourceid 的get方法
     * 
     * @version V1.0  2017-03-13 下午 05:04:56 星期一
     * @author wxd(wxd@inter3i.com)
     * @return Integer 
     */
    public Integer getSourceid() {
        return sourceid;
    }
     
    /**
     * Description: 属性 sourceid 的set方法
     * 
     * @version V1.0  2017-03-13 下午 05:04:56 星期一
     * @author wxd(wxd@inter3i.com)
     * @param  sourceid Integer 
     * @return void 
     */
    public void setSourceid(Integer sourceid) {
        this.sourceid = sourceid;
    }
    /**
     * Description: 属性 username 的get方法
     * 
     * @version V1.0  2017-03-13 下午 05:04:56 星期一
     * @author wxd(wxd@inter3i.com)
     * @return String 
     */
    public String getUsername() {
        return username;
    }
     
    /**
     * Description: 属性 username 的set方法
     * 
     * @version V1.0  2017-03-13 下午 05:04:56 星期一
     * @author wxd(wxd@inter3i.com)
     * @param  username String 
     * @return void 
     */
    public void setUsername(String username) {
        this.username = username;
    }

	
}