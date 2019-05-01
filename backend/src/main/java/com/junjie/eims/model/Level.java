package com.junjie.eims.model;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author junjie.zhang
 * @since 2019/4/20 12:00 PM
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Level {
  private long id;
  private String description;
  private int deleteFlag;
  private Date createTime;
}
