package com.junjie.eims.services;

import com.junjie.eims.model.Level;

import java.util.List;
import java.util.Map;

/**
 * @author junjie.zhang
 * @since 2019/4/20 4:21 PM
 */
public interface LevelService {

  Level getById(long id);

  List<Level> getByDescription(String description);

  Map<Long, Level> getAllLevelMap();

  Level insert(Level level);

  int deleteById(long id);
}
