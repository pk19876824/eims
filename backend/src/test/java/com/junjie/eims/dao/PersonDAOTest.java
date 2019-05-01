package com.junjie.eims.dao;

import com.junjie.eims.model.Person;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * @author junjie.zhang
 * @since 2019/4/20 3:04 PM
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class PersonDAOTest {
  @Autowired
  private PersonDAO personDAO;

  static long id = 0;
  static Person p;

  static {
    DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    Date birthday = new Date();
    try {
      birthday = dateFormat.parse("1993-02-01");
    } catch (ParseException e) {
      e.printStackTrace();
    }
    p = Person.builder()
      .name("张俊杰")
      .gender(0)
      .birthday(birthday)
      .build();
  }

  @Test
  public void testInsert() {
    int i = personDAO.insert(p);
    Assert.assertEquals(1, i);
  }

  @Test
  public void testGetById() {
    int i = personDAO.insert(p);
    Assert.assertEquals(1, i);
    Person pOther = personDAO.getById(p.getId());
    Assert.assertEquals(p.getName(), pOther.getName());
    Assert.assertEquals(p.getId(), pOther.getId());
    Assert.assertEquals(p.getGender(), pOther.getGender());
    Assert.assertEquals(p.getBirthday(), pOther.getBirthday());
  }

  @Test
  public void testBatchDeleteByIds() {
    List<Long> ids = Collections.emptyList();
    personDAO.batchDeleteByIds(ids);
  }
}
