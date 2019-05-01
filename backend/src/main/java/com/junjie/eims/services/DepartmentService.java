package com.junjie.eims.services;

import com.junjie.eims.model.Department;

import java.util.List;
import java.util.Map;

/**
 * @author junjie.zhang
 * @since 2019/4/20 4:19 PM
 */
public interface DepartmentService {
  Department getById(long id);

  List<Department> getByDescription(String description);

  Map<Long, Department> getAllDepartmentMap();

  Department insert(Department department);

  int deleteById(long id);
}
