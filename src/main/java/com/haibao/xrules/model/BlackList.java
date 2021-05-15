package com.haibao.xrules.model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 黑名单
 */
@Entity
@Table( name ="BLACK_LIST" , schema = "")
public class BlackList implements Serializable {

  @Id
  @Column(name = "id" )
  private long id;
  /**
   * 维度
   */
  @Column(name = "dimension" )
  private String dimension;
  /**
   * 类型
   */
  @Column(name = "type" )
  private String type;
  /**
   * 值
   */
  @Column(name = "value" )
  private String value;
  /**
   * 时间
   */
  @Column(name = "time" )
  private java.sql.Timestamp time;
  /**
   * 详情
   */
  @Column(name = "detail" )
  private String detail;


  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }


  public String getDimension() {
    return dimension;
  }

  public void setDimension(String dimension) {
    this.dimension = dimension;
  }


  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }


  public String getValue() {
    return value;
  }

  public void setValue(String value) {
    this.value = value;
  }


  public java.sql.Timestamp getTime() {
    return time;
  }

  public void setTime(java.sql.Timestamp time) {
    this.time = time;
  }


  public String getDetail() {
    return detail;
  }

  public void setDetail(String detail) {
    this.detail = detail;
  }

  @Override
  public String toString() {
    return "BlackList{" +
            "id=" + id +
            ", dimension='" + dimension + '\'' +
            ", type='" + type + '\'' +
            ", value='" + value + '\'' +
            ", time=" + time +
            ", detail='" + detail + '\'' +
            '}';
  }
}
