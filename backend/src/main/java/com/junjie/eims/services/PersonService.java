package com.junjie.eims.services;

import com.junjie.eims.model.Person;

import java.util.List;

/**
 * @author junjie.zhang
 * @since 2019/4/20 4:16 PM
 */
public interface PersonService {
  Person getById(long id);

  List<Person> getByIds(List<Long> ids);

  Person insert(Person person);

  int deleteById(long id);

  int batchDeleteByIds(List<Long> ids);
}
