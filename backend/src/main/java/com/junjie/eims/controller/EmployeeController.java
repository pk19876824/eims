package com.junjie.eims.controller;

import com.junjie.eims.model.Department;
import com.junjie.eims.model.Employee;
import com.junjie.eims.model.EmployeeFilter;
import com.junjie.eims.model.EmployeeSearchResponse;
import com.junjie.eims.model.EmployeeVo;
import com.junjie.eims.model.Level;
import com.junjie.eims.model.Person;
import com.junjie.eims.services.EmployeeService;
import com.junjie.eims.services.PersonService;
import com.junjie.eims.utils.RestJsonUtil;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import lombok.extern.slf4j.Slf4j;

import static com.junjie.eims.enums.ErrorCodeEnum.ADD_EMPLOYEE_ERROR;
import static com.junjie.eims.enums.ErrorCodeEnum.UNKNOWN_ERROR;

/**
 * @author junjie.zhang
 * @since 2019/4/21 2:59 PM
 */
@Slf4j
@RestController
@CrossOrigin
@RequestMapping("employee")
public class EmployeeController {

  @Autowired
  private EmployeeService employeeService;

  @Autowired
  private PersonService personService;

  private static final String SORT_FIELD_NAME = "name";
  private static final String SORT_FIELD_AGE = "age";
  private static final String SORT_ORDER_ASC = "ascend";
  private static final String SORT_ORDER_DESC = "descend";

  @PostMapping("")
  String addEmployee(@RequestParam("name") String name,
                     @RequestParam("gender") int gender,
                     @RequestParam("birthday") Date birthday,
                     @RequestParam("department") long departmentId,
                     @RequestParam("level") long levelId) {
    try {
      EmployeeVo employeeVo = EmployeeVo.builder()
        .person(Person.builder().name(name).gender(gender).birthday(birthday).build())
        .department(Department.builder().id(departmentId).build())
        .level(Level.builder().id(levelId).build())
        .build();
      EmployeeVo result = employeeService.insert(employeeVo);
      return null == result ? RestJsonUtil.resultWithFailed(ADD_EMPLOYEE_ERROR) :
        RestJsonUtil.resultOk(result);
    } catch (Exception e) {
      log.error("exception", e);
      return RestJsonUtil.resultWithFailed(UNKNOWN_ERROR);
    }
  }

  @GetMapping("")
  String searchEmployee(@RequestParam(value = "name", required = false, defaultValue = "") String name,
                        @RequestParam(value = "department[]", required = false, defaultValue = "")
                          List<Long> departmentId,
                        @RequestParam(value = "level[]", required = false, defaultValue = "")
                          List<Long> levelId,
                        @RequestParam(value = "gender[]", required = false, defaultValue = "")
                          List<Integer> gender,
                        @RequestParam(value = "sortField", required = false, defaultValue = "")
                          String sortField,
                        @RequestParam(value = "sortOrder", required = false, defaultValue = "")
                          String sortOrder,
                        @RequestParam(value = "results", required = false, defaultValue = "10") int results,
                        @RequestParam(value = "page", required = false, defaultValue = "1") int page) {
    EmployeeFilter employeeFilter = EmployeeFilter.builder()
      .name(name)
      .departmentIds(departmentId)
      .levelIds(levelId)
      .genders(gender)
      .build();
    try {
      List<EmployeeVo> employeeVos = employeeService.search(employeeFilter);
      List<EmployeeSearchResponse> employeeSearchResponseList = new ArrayList<>();
      employeeVos.forEach(x -> {
        employeeSearchResponseList.add(EmployeeSearchResponse.builder()
          .id(x.getId())
          .name(x.getPerson().getName())
          .department(x.getDepartment().getDescription())
          .level(x.getLevel().getDescription())
          .gender(x.getPerson().getGender() == 1 ? "女" : "男")
          .age(new Date().getYear() - x.getPerson().getBirthday().getYear())
          .build());
      });
      if (StringUtils.isNotBlank(sortField)) {
        switch (sortField) {
          case SORT_FIELD_NAME: {
            Comparator<EmployeeSearchResponse> comparator =
              Comparator.comparing(EmployeeSearchResponse::getName);
            if (SORT_ORDER_ASC.equals(sortOrder)) {
              employeeSearchResponseList.sort(comparator);
            } else if (SORT_ORDER_DESC.equals(sortOrder)) {
              employeeSearchResponseList.sort(comparator.reversed());
            }
            break;
          }
          case SORT_FIELD_AGE: {
            Comparator<EmployeeSearchResponse> comparator =
              Comparator.comparing(EmployeeSearchResponse::getAge);
            if (SORT_ORDER_ASC.equals(sortOrder)) {
              employeeSearchResponseList.sort(comparator);
            } else if (SORT_ORDER_DESC.equals(sortOrder)) {
              employeeSearchResponseList.sort(comparator.reversed());
            }
            break;
          }
          default:
            break;
        }
      }

      List<EmployeeSearchResponse> resultList;

      page = page < 1 ? 1 : page;
      results = results < 1 ? 10 : results;
      int fromIndex = (page - 1) * results;
      if (fromIndex >= employeeSearchResponseList.size()) {
        resultList = Collections.emptyList();
      } else {
        int toIndex = fromIndex + results;
        toIndex = toIndex > employeeSearchResponseList.size() ? employeeSearchResponseList.size() :
          toIndex;
        resultList = employeeSearchResponseList.subList(fromIndex, toIndex);
      }

      Map<String, Object> resultMap = new HashMap<>();
      resultMap.put("results", resultList);
      resultMap.put("count", employeeSearchResponseList.size());
      return RestJsonUtil.resultOk(resultMap);
    } catch (Exception e) {
      log.error("exception", e);
      return RestJsonUtil.resultWithFailed(UNKNOWN_ERROR);
    }
  }

  @DeleteMapping("")
  public String batchDeleteEmployees(@RequestParam("ids[]") List<Long> ids) {
    try {
      List<Employee> employees = employeeService.getByIds(ids);
      //1.删除person
      List<Long> personIds =
        employees.stream().map(Employee::getPersonId).collect(Collectors.toList());
      personService.batchDeleteByIds(personIds);
      //2.删除employee
      ids = employees.stream().map(Employee::getId).collect(Collectors.toList());
      employeeService.batchDeleteByIds(ids);
      return RestJsonUtil.resultOk();
    } catch (Exception e) {
      log.error("exception", e);
      return RestJsonUtil.resultWithFailed(UNKNOWN_ERROR);
    }
  }

}
