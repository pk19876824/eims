package com.junjie.eims.dao;

import com.junjie.eims.model.Person;
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
 * @since 2019/4/20 1:00 PM
 */
public interface PersonDAO {

  String TABLE = "person";
  String ALL_COL = "id, name, gender, birthday, delete_flag, create_time, " +
    "update_time";

  @Select("select " + ALL_COL + " from " + TABLE + " where delete_flag = 0 and id = #{id}")
  @Results({
    @Result(property = "deleteFlag", column = "delete_flag"),
    @Result(property = "createTime", column = "create_time"),
    @Result(property = "updateTime", column = "update_time")
  })
  Person getById(@Param("id") long id);

  @Lang(BatchOperationLanguageDriver.class)
  @Select("select " + ALL_COL + " from " + TABLE + " where delete_flag = 0 and id in (#{ids})")
  @Results({
    @Result(property = "deleteFlag", column = "delete_flag"),
    @Result(property = "createTime", column = "create_time"),
    @Result(property = "updateTime", column = "update_time")
  })
  List<Person> getByIds(@Param("ids") List<Long> ids);

  @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
  @Insert("insert into " +
    TABLE +
    " set name = #{name}," +
    " gender = #{gender}," +
    " birthday = #{birthday}")
  int insert(Person person);

  @Update("update " + TABLE + " set delete_flag = 1 where id = #{id}")
  int deleteById(@Param("id") long id);

  @Lang(BatchOperationLanguageDriver.class)
  @Update("update " + TABLE + " set delete_flag = 1 where id in (#{ids})")
  int batchDeleteByIds(@Param("ids") List<Long> ids);
}
