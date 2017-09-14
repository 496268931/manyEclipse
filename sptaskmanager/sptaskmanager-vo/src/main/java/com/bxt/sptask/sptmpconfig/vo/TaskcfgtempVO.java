package com.bxt.sptask.sptmpconfig.vo;

public class TaskcfgtempVO
{
  private String nodeid;
  private Integer hitnum;
  private String isextend;
  private String nodename;
  private String parnodeid;
  private String paramvalue;
  private String paramtype;
  private String paramextype;
  private String userid;

  public String getParnodeid()
  {
    return this.parnodeid;
  }
  public String getNodeid() {
    return this.nodeid;
  }
  public void setParnodeid(String parnodeid) {
    this.parnodeid = parnodeid;
  }
  public void setNodeid(String nodeid) {
    this.nodeid = nodeid;
  }
  public String getParamextype() {
    return this.paramextype;
  }
  public void setParamextype(String paramextype) {
    this.paramextype = paramextype;
  }
  public String getParamvalue() {
    return this.paramvalue;
  }
  public void setParamvalue(String paramvalue) {
    this.paramvalue = paramvalue;
  }
  public String getParamtype() {
    return this.paramtype;
  }
  public void setParamtype(String paramtype) {
    this.paramtype = paramtype;
  }

  public Integer getHitnum() {
    return this.hitnum;
  }
  public void setHitnum(Integer hitnum) {
    this.hitnum = hitnum;
  }
  public String getIsextend() {
    return this.isextend;
  }
  public void setIsextend(String isextend) {
    this.isextend = isextend;
  }
  public String getNodename() {
    return this.nodename;
  }
  public void setNodename(String nodename) {
    this.nodename = nodename;
  }

  public String getUserid() {
    return this.userid;
  }
  public void setUserid(String userid) {
    this.userid = userid;
  }
}