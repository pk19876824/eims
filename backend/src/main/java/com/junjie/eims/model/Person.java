package com.junjie.eims.model;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author junjie.zhang
 * @since 2019/4/20 11:17 AM
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Person {
  private long id;
  private String name;
  private int gender;
  private Date birthday;
  private int deleteFlag;
  private Date createTime;
  private Date updateTime;
}
