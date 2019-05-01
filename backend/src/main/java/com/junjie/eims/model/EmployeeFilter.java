package com.junjie.eims.model;

import java.util.Collections;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author junjie.zhang
 * @since 2019/4/21 4:16 PM
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EmployeeFilter {
  @Builder.Default
  private String name = "";
  @Builder.Default
  private List<Long> departmentIds = Collections.emptyList();
  @Builder.Default
  private List<Long> levelIds = Collections.emptyList();
  @Builder.Default
  private List<Integer> genders = Collections.emptyList();
}
