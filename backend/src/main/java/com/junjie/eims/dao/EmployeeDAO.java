package com.junjie.eims.dao;

import com.junjie.eims.model.Employee;
import com.junjie.eims.utils.BatchOperationLanguageDriver;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Lang;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * @author junjie.zhang
 * @since 2019/4/20 1:22 PM
 */
public interface EmployeeDAO {
  String TABLE = "employee";
  String ALL_COL = "id, person_id, department_id, level_id, delete_flag, create_time, update_time";

  @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
  @Insert("insert into " +
    TABLE +
    " set" +
    " person_id = #{personId}," +
    " department_id = #{departmentId}," +
    " level_id = #{levelId}")
  int insert(Employee employee);

  @Select("select " + ALL_COL + " from " + TABLE + " where id = #{id}")
  @Results({
    @Result(property = "personId", column = "person_id"),
    @Result(property = "departmentId", column = "department_id"),
    @Result(property = "levelId", column = "level_id"),
    @Result(property = "createTime", column = "create_time"),
    @Result(property = "updateTime", column = "update_time"),
    @Result(property = "deleteFlag", column = "delete_flag")
  })
  Employee getById(@Param("id") long id);

  @Lang(BatchOperationLanguageDriver.class)
  @Select("select " + ALL_COL + " from " + TABLE + " where id in (#{ids})")
  @Results({
    @Result(property = "personId", column = "person_id"),
    @Result(property = "departmentId", column = "department_id"),
    @Result(property = "levelId", column = "level_id"),
    @Result(property = "createTime", column = "create_time"),
    @Result(property = "updateTime", column = "update_time"),
    @Result(property = "deleteFlag", column = "delete_flag")
  })
  List<Employee> getByIds(@Param("ids") List<Long> ids);

  @Select("select " + ALL_COL + " from " + TABLE + " where delete_flag = 0")
  @Results({
    @Result(property = "personId", column = "person_id"),
    @Result(property = "departmentId", column = "department_id"),
    @Result(property = "levelId", column = "level_id"),
    @Result(property = "createTime", column = "create_time"),
    @Result(property = "updateTime", column = "update_time"),
    @Result(property = "deleteFlag", column = "delete_flag")
  })
  List<Employee> getAll();

  @Update("update " + TABLE + " set delete_flag = 1 where id = #{id}")
  int deleteById(@Param("id") long id);

  @Lang(BatchOperationLanguageDriver.class)
  @Update("update " + TABLE + " set delete_flag = 1 where id in (#{ids})")
  int batchDeleteByids(@Param("ids") List<Long> ids);
}
