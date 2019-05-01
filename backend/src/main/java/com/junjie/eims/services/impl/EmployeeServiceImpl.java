package com.junjie.eims.services.impl;

import com.junjie.eims.dao.EmployeeDAO;
import com.junjie.eims.model.Department;
import com.junjie.eims.model.Employee;
import com.junjie.eims.model.EmployeeFilter;
import com.junjie.eims.model.EmployeeVo;
import com.junjie.eims.model.Level;
import com.junjie.eims.model.Person;
import com.junjie.eims.services.DepartmentService;
import com.junjie.eims.services.EmployeeService;
import com.junjie.eims.services.LevelService;
import com.junjie.eims.services.PersonService;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import lombok.extern.slf4j.Slf4j;

/**
 * @author junjie.zhang
 * @since 2019/4/21 11:25 AM
 */
@Slf4j
@Service
public class EmployeeServiceImpl implements EmployeeService {

  private static final long NONE_DEPARTMENT_ID = -1L;
  private static final long NONE_LEVEL_ID = -1L;
  private static final long NONE_GENDER = -1;

  @Autowired
  private EmployeeDAO employeeDAO;

  @Autowired
  private DepartmentService departmentService;

  @Autowired
  private LevelService levelService;

  @Autowired
  private PersonService personService;

  @Override
  public EmployeeVo insert(EmployeeVo employeeVo) {
    //1.查找Department
    Department department = departmentService.getById(employeeVo.getDepartment().getId());
    //2.查找Level
    Level level = levelService.getById(employeeVo.getLevel().getId());
    if (null == department || null == level) {
      return null;
    }
    //3.添加Person
    Person person = personService.insert(employeeVo.getPerson());
    if (null == person) {
      return null;
    }
    //4.添加Employee
    Employee employee = Employee.builder()
      .personId(person.getId())
      .departmentId(department.getId())
      .levelId(level.getId())
      .build();
    employeeDAO.insert(employee);
    return EmployeeVo.builder()
      .department(department)
      .level(level)
      .person(person)
      .build();
  }

  @Override
  public Employee getById(long id) {
    return employeeDAO.getById(id);
  }

  @Override
  public List<Employee> getByIds(List<Long> ids) {
    if (CollectionUtils.isEmpty(ids)) {
      return Collections.emptyList();
    }
    return employeeDAO.getByIds(ids);
  }

  @Override
  public List<EmployeeVo> search(EmployeeFilter filter) {
    List<Employee> employees = employeeDAO.getAll();
    //筛选department
    employees = filterDepartment(employees, filter.getDepartmentIds());
    //筛选level
    employees = filterLevel(employees, filter.getLevelIds());

    //筛选person
    List<Long> personIds = employees.stream()
      .map(Employee::getPersonId)
      .collect(Collectors.toList());
    List<Person> personList = personService.getByIds(personIds);
    personList = filterGender(personList, filter.getGenders());
    personList = filterName(personList, filter.getName());

    if (CollectionUtils.isEmpty(personList)) {
      return Collections.emptyList();
    }

    Map<Long, Department> allDepartment = departmentService.getAllDepartmentMap();
    Map<Long, Level> allLevel = levelService.getAllLevelMap();
    Map<Long, Person> personMap = personList.stream()
      .collect(Collectors.toMap(Person::getId, Function.identity()));

    List<EmployeeVo> searchResult = new ArrayList<>();
    for (Employee employee : employees) {
      if (personMap.containsKey(employee.getPersonId())
        && allDepartment.containsKey(employee.getDepartmentId())
        && allLevel.containsKey(employee.getLevelId())) {
        searchResult.add(EmployeeVo.builder()
          .id(employee.getId())
          .person(personMap.get(employee.getPersonId()))
          .department(allDepartment.get(employee.getDepartmentId()))
          .level(allLevel.get(employee.getLevelId()))
          .build());
      }
    }
    return searchResult;
  }

  @Override
  public int deleteById(long id) {
    return employeeDAO.deleteById(id);
  }

  @Override
  public int batchDeleteByIds(List<Long> ids) {
    if (CollectionUtils.isEmpty(ids)) {
      return 0;
    }
    return employeeDAO.batchDeleteByids(ids);
  }

  private static List<Employee> filterDepartment(List<Employee> employees,
                                                 List<Long> departmentIds) {
    if (CollectionUtils.isEmpty(employees)) {
      return Collections.emptyList();
    }
    if (CollectionUtils.isEmpty(departmentIds)) {
      return employees;
    }
    return employees.stream().filter(x -> departmentIds.contains(x.getDepartmentId())).collect(Collectors.toList());
  }

  private static List<Employee> filterLevel(List<Employee> employees, List<Long> levelIds) {
    if (CollectionUtils.isEmpty(employees)) {
      return Collections.emptyList();
    }
    if (CollectionUtils.isEmpty(levelIds)) {
      return employees;
    }
    return employees.stream().filter(x -> levelIds.contains(x.getLevelId())).collect(Collectors.toList());
  }

  private static List<Person> filterGender(List<Person> personList, List<Integer> genders) {
    if (CollectionUtils.isEmpty(personList)) {
      return Collections.emptyList();
    }
    if (CollectionUtils.isEmpty(genders)) {
      return personList;
    }
    return personList.stream().filter(x -> genders.contains(x.getGender())).collect(Collectors.toList());
  }

  private static List<Person> filterName(List<Person> personList, String name) {
    if (CollectionUtils.isEmpty(personList)) {
      return Collections.emptyList();
    }
    if (StringUtils.isBlank(name)) {
      return personList;
    }
    return personList.stream()
      .filter(x -> (x.getName().contains(name)))
      .collect(Collectors.toList());
  }


}
