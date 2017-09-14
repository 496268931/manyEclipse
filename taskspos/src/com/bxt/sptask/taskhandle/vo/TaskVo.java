package com.bxt.sptask.taskhandle.vo;

import java.io.Serializable;

public class TaskVo implements Serializable{
  private String id;
  private Integer tasktype;
  private Integer taskpagestyletype;
  private Integer task;
  private Integer tasklevel;
  private Integer local;
  private Integer remote;
  private Integer timeout;
  private Integer activatetime;
  private Integer conflictdelay;
  private Integer starttime;
  private Integer endtime;
  private Integer taskstatus;
  private Integer datastatus;
  private String taskparams;
  private String remarks;
  private String machine;
  private Integer tenantid;
  private Integer userid;
  private Integer error_code;
  private String spcfdmac;
  private String error_msg;
  private String taskclassify;
  private String create_time;
  private String modifier_code;

  public String getCreate_time()
  {
    return this.create_time;
  }

  public void setCreate_time(String create_time) {
    this.create_time = create_time;
  }

  public String getModifier_code() {
    return this.modifier_code;
  }

  public void setModifier_code(String modifier_code) {
    this.modifier_code = modifier_code;
  }

  public String getId() {
    return this.id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public Integer getError_code() {
    return this.error_code;
  }

  public void setError_code(Integer error_code) {
    this.error_code = error_code;
  }

  public String getSpcfdmac() {
    return this.spcfdmac;
  }

  public void setSpcfdmac(String spcfdmac) {
    this.spcfdmac = spcfdmac;
  }

  public String getError_msg() {
    return this.error_msg;
  }

  public void setError_msg(String error_msg) {
    this.error_msg = error_msg;
  }

  public String getTaskclassify() {
    return this.taskclassify;
  }

  public void setTaskclassify(String taskclassify) {
    this.taskclassify = taskclassify;
  }

  public Integer getTasktype() {
    return this.tasktype;
  }

  public void setTasktype(Integer tasktype) {
    this.tasktype = tasktype;
  }

  public Integer getTaskpagestyletype() {
    return this.taskpagestyletype;
  }

  public void setTaskpagestyletype(Integer taskpagestyletype) {
    this.taskpagestyletype = taskpagestyletype;
  }

  public Integer getTask() {
    return this.task;
  }

  public void setTask(Integer task) {
    this.task = task;
  }

  public Integer getTasklevel() {
    return this.tasklevel;
  }

  public void setTasklevel(Integer tasklevel) {
    this.tasklevel = tasklevel;
  }

  public Integer getLocal() {
    return this.local;
  }

  public void setLocal(Integer local) {
    this.local = local;
  }

  public Integer getRemote() {
    return this.remote;
  }

  public void setRemote(Integer remote) {
    this.remote = remote;
  }

  public Integer getTimeout() {
    return this.timeout;
  }

  public void setTimeout(Integer timeout) {
    this.timeout = timeout;
  }

  public Integer getActivatetime() {
    return this.activatetime;
  }

  public void setActivatetime(Integer activatetime) {
    this.activatetime = activatetime;
  }

  public Integer getConflictdelay() {
    return this.conflictdelay;
  }

  public void setConflictdelay(Integer conflictdelay) {
    this.conflictdelay = conflictdelay;
  }

  public Integer getStarttime() {
    return this.starttime;
  }

  public void setStarttime(Integer starttime) {
    this.starttime = starttime;
  }

  public Integer getEndtime() {
    return this.endtime;
  }

  public void setEndtime(Integer endtime) {
    this.endtime = endtime;
  }

  public Integer getTaskstatus() {
    return this.taskstatus;
  }

  public void setTaskstatus(Integer taskstatus) {
    this.taskstatus = taskstatus;
  }

  public Integer getDatastatus() {
    return this.datastatus;
  }

  public void setDatastatus(Integer datastatus) {
    this.datastatus = datastatus;
  }

  public String getTaskparams() {
    return this.taskparams;
  }

  public void setTaskparams(String taskparams) {
    this.taskparams = taskparams;
  }

  public String getRemarks() {
    return this.remarks;
  }

  public void setRemarks(String remarks) {
    this.remarks = remarks;
  }

  public String getMachine() {
    return this.machine;
  }

  public void setMachine(String machine) {
    this.machine = machine;
  }

  public Integer getTenantid() {
    return this.tenantid;
  }

  public void setTenantid(Integer tenantid) {
    this.tenantid = tenantid;
  }

  public Integer getUserid() {
    return this.userid;
  }

  public void setUserid(Integer userid) {
    this.userid = userid;
  }
}