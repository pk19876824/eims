package com.junjie.eims.model;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author junjie.zhang
 * @since 2019/4/20 12:01 PM
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Employee {
  private long id;
  private long personId;
  private long departmentId;
  private long levelId;
  private int deleteFlag;
  private Date createTime;
  private Date updateTime;
}
