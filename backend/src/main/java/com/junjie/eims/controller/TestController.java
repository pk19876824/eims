package com.junjie.eims.controller;

import com.junjie.eims.dao.TestDAO;
import com.junjie.eims.utils.RestJsonUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

/**
 * @author junjie.zhang
 * @since 2019/4/19 3:22 PM
 */
@RestController
@Slf4j
public class TestController {

  @Autowired
  private TestDAO testDAO;

  @GetMapping("test")
  @CrossOrigin
  public String test() {
    return RestJsonUtil.resultOk("hello");
  }
}
