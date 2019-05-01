package com.junjie.eims.services.impl;

import com.junjie.eims.dao.PersonDAO;
import com.junjie.eims.model.Person;
import com.junjie.eims.services.PersonService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;

import lombok.extern.slf4j.Slf4j;

/**
 * @author junjie.zhang
 * @since 2019/4/20 4:17 PM
 */
@Service
@Slf4j
public class PersonServiceImpl implements PersonService {

  @Autowired
  private PersonDAO personDAO;

  @Override
  public Person getById(long id) {
    return personDAO.getById(id);
  }

  @Override
  public List<Person> getByIds(List<Long> ids) {
    if (CollectionUtils.isEmpty(ids)) {
      return Collections.emptyList();
    }
    return personDAO.getByIds(ids);
  }

  @Override
  public Person insert(Person person) {
    if (null == person) {
      return null;
    }
    personDAO.insert(person);
    return personDAO.getById(person.getId());
  }

  @Override
  public int deleteById(long id) {
    return personDAO.deleteById(id);
  }

  @Override
  public int batchDeleteByIds(List<Long> ids) {
    if (CollectionUtils.isEmpty(ids)) {
      return 0;
    }
    return personDAO.batchDeleteByIds(ids);
  }
}
