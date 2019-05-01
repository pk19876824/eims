package com.junjie.eims.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author junjie.zhang
 * @since 2019/4/21 11:23 AM
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EmployeeVo {
  private long id;
  private Person person;
  private Department department;
  private Level level;
}
