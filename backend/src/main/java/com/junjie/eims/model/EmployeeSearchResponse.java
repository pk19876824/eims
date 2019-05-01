package com.junjie.eims.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author junjie.zhang
 * @since 2019/4/23 11:18 AM
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EmployeeSearchResponse {
  private long id;
  private String name;
  private String department;
  private String level;
  private String gender;
  private int age;
}
