package com.bxt.sptask.taskhandle.vo;

import java.io.Serializable;

public class SpiderconfigVO
  implements Serializable
{
  private static final long serialVersionUID = -165930382289803783L;
  private Integer id;
  private String content;
  private String detailurlregex;
  private String name;
  private Integer templatetype;
  private String urlconfigrule;
  private String urlregex;

  public Integer getId()
  {
    return this.id;
  }

  public void setId(Integer id)
  {
    this.id = id;
  }

  public String getContent()
  {
    return this.content;
  }

  public void setContent(String content)
  {
    this.content = content;
  }

  public String getDetailurlregex()
  {
    return this.detailurlregex;
  }

  public void setDetailurlregex(String detailurlregex)
  {
    this.detailurlregex = detailurlregex;
  }

  public String getName()
  {
    return this.name;
  }

  public void setName(String name)
  {
    this.name = name;
  }

  public Integer getTemplatetype()
  {
    return this.templatetype;
  }

  public void setTemplatetype(Integer templatetype)
  {
    this.templatetype = templatetype;
  }

  public String getUrlconfigrule()
  {
    return this.urlconfigrule;
  }

  public void setUrlconfigrule(String urlconfigrule)
  {
    this.urlconfigrule = urlconfigrule;
  }

  public String getUrlregex()
  {
    return this.urlregex;
  }

  public void setUrlregex(String urlregex)
  {
    this.urlregex = urlregex;
  }
}