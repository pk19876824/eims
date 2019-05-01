package com.junjie.eims.dao;

import com.junjie.eims.model.Test;

import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author junjie.zhang
 * @since 2019/4/19 12:04 PM
 */
public interface TestDAO {
  String TABLE = "test";
  String ALL_COL = " id, name";

  @Select("select " + ALL_COL + " from " + TABLE)
  List<Test> getAll();
}
