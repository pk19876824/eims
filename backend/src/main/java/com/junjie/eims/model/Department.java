package com.junjie.eims.model;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author junjie.zhang
 * @since 2019/4/20 11:59 AM
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Department {
  private long id;
  private String description;
  private int deleteFlag;
  private Date createTime;
}
