package com.junjie.eims.dao;

import com.junjie.eims.model.Level;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * @author junjie.zhang
 * @since 2019/4/21 11:12 AM
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class LevelDAOTest {

  @Autowired
  private LevelDAO levelDAO;

  @Test
  public void testInsert() {
    Level level = Level.builder()
      .description("组长")
      .build();
    int i = levelDAO.insert(level);
    Assert.assertEquals(1, i);
  }

  @Test
  public void testGetByDescription() {
    List<Level> levels = levelDAO.getByDescription("组长");
    Assert.assertEquals(1, levels.size());
  }
}
