package com.junjie.eims.dao;

import com.junjie.eims.model.Department;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * @author junjie.zhang
 * @since 2019/4/21 10:28 AM
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class DepartmentDAOTest {

  @Autowired
  private DepartmentDAO departmentDAO;

  @Test
  public void testInsert() {
    Department department = Department.builder()
      .description("产品部")
      .build();
    int i = departmentDAO.insert(department);
    Assert.assertEquals(i, 1);
  }

  @Test
  public void testGetByDescription() {
    List<Department> departments = departmentDAO.getByDescription("产品部");
    Assert.assertEquals(1, departments.size());
  }


}
