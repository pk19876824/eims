package com.junjie.eims.dao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * @author junjie.zhang
 * @since 2019/4/19 5:07 PM
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class TestDAOTest {
  @Autowired
  private TestDAO testDAO;

  @Test
  public void testGetAll() {
    List<com.junjie.eims.model.Test> result = testDAO.getAll();
    System.out.println(result);
  }
}
