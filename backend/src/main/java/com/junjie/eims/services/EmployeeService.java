package com.junjie.eims.services;

import com.junjie.eims.model.Employee;
import com.junjie.eims.model.EmployeeFilter;
import com.junjie.eims.model.EmployeeVo;

import java.util.List;

/**
 * @author junjie.zhang
 * @since 2019/4/20 4:24 PM
 */
public interface EmployeeService {
  EmployeeVo insert(EmployeeVo employeeVo);

  List<EmployeeVo> search(EmployeeFilter filter);

  Employee getById(long id);

  List<Employee> getByIds(List<Long> ids);

  int deleteById(long id);

  int batchDeleteByIds(List<Long> ids);
}
