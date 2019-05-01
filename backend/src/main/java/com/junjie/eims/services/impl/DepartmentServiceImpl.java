package com.junjie.eims.services.impl;

import com.junjie.eims.dao.DepartmentDAO;
import com.junjie.eims.model.Department;
import com.junjie.eims.services.DepartmentService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import lombok.extern.slf4j.Slf4j;

/**
 * @author junjie.zhang
 * @since 2019/4/20 4:20 PM
 */
@Slf4j
@Service
public class DepartmentServiceImpl implements DepartmentService {

  private static final String DEFAULT_DESCRIPTION = "未知";

  @Autowired
  private DepartmentDAO departmentDAO;

  @Override
  public Department getById(long id) {
    return departmentDAO.getById(id);
  }

  @Override
  public List<Department> getByDescription(String description) {
    return departmentDAO.getByDescription(description);
  }

  @Override
  public Map<Long, Department> getAllDepartmentMap() {
    List<Department> allDepartments = departmentDAO.getAll();
    return allDepartments.stream().collect(Collectors.toMap(Department::getId,
      Function.identity()));
  }

  @Override
  public Department insert(Department department) {
    if (null == department) {
      department = getDefaultDepartment();
    }
    List<Department> departments = departmentDAO.getByDescription(department.getDescription());
    if (0 == departments.size()) {
      departmentDAO.insert(department);
      return departmentDAO.getById(department.getId());
    }
    return departments.get(0);
  }

  @Override
  public int deleteById(long id) {
    return departmentDAO.deleteById(id);
  }

  private static Department getDefaultDepartment() {
    return Department.builder()
      .description(DEFAULT_DESCRIPTION)
      .build();
  }
}
