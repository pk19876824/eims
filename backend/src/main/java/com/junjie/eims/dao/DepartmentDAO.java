package com.junjie.eims.dao;

import com.junjie.eims.model.Department;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * @author junjie.zhang
 * @since 2019/4/20 1:14 PM
 */
public interface DepartmentDAO {
  String TABLE = "department";
  String ALL_COL = "id, description, delete_flag, create_time";

  @Select("select " + ALL_COL + " from " + TABLE + " where delete_flag = 0 and id = #{id}")
  @Results({
    @Result(property = "deleteFlag", column = "delete_flag"),
    @Result(property = "createTime", column = "create_time")
  })
  Department getById(@Param("id") long id);

  @Select("select " + ALL_COL + " from " + TABLE + " where delete_flag = 0 and description = " +
    "#{description}")
  @Results({
    @Result(property = "deleteFlag", column = "delete_flag"),
    @Result(property = "createTime", column = "create_time")
  })
  List<Department> getByDescription(@Param("description") String description);

  @Select("select " + ALL_COL + " from " + TABLE + " where delete_flag = 0")
  @Results({
    @Result(property = "deleteFlag", column = "delete_flag"),
    @Result(property = "createTime", column = "create_time")
  })
  List<Department> getAll();

  @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
  @Insert("insert into " + TABLE + " set description = #{description}")
  int insert(Department department);

  @Update("update " + TABLE + " set delete_flag = 1 where id = #{id}")
  int deleteById(@Param("id") long id);
}
