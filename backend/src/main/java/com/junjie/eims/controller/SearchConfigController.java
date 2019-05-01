package com.junjie.eims.controller;

import com.google.common.collect.ImmutableMap;

import com.junjie.eims.model.Department;
import com.junjie.eims.model.Level;
import com.junjie.eims.services.DepartmentService;
import com.junjie.eims.services.LevelService;
import com.junjie.eims.utils.RestJsonUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import lombok.extern.slf4j.Slf4j;

/**
 * @author junjie.zhang
 * @since 2019/4/23 12:51 AM
 */
@RestController
@CrossOrigin
@Slf4j
@RequestMapping("config")
public class SearchConfigController {

  @Autowired
  private DepartmentService departmentService;

  @Autowired
  private LevelService levelService;

  @GetMapping("")
  public String config() {
    Map<String, Object> configMap = new HashMap<>();
    Map<Long, Department> allDepartment = departmentService.getAllDepartmentMap();
    Map<Long, String> departmentMap = allDepartment.values().stream()
      .collect(Collectors.toMap(Department::getId, Department::getDescription));
    Map<Long, Level> allLevel = levelService.getAllLevelMap();
    Map<Long, String> levelMap = allLevel.values().stream()
      .collect(Collectors.toMap(Level::getId, Level::getDescription));
    departmentMap.put(-1L, "全部");
    levelMap.put(-1L, "全部");
    Map<Long, String> genderMap = ImmutableMap.of(-1L, "全部", 0L, "男", 1L, "女");
    configMap.put("department", departmentMap);
    configMap.put("level", levelMap);
    configMap.put("gender", genderMap);
    return RestJsonUtil.resultOk(configMap);
  }
}
