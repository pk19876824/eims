package com.junjie.eims.services.impl;

import com.junjie.eims.dao.LevelDAO;
import com.junjie.eims.model.Level;
import com.junjie.eims.services.LevelService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import lombok.extern.slf4j.Slf4j;

/**
 * @author junjie.zhang
 * @since 2019/4/20 4:22 PM
 */
@Slf4j
@Service
public class LevelServiceImpl implements LevelService {

  private static final String DEFAULT_DESCRIPTION = "未知";

  @Autowired
  private LevelDAO levelDAO;

  @Override
  public Level getById(long id) {
    return levelDAO.getById(id);
  }

  @Override
  public List<Level> getByDescription(String description) {
    return levelDAO.getByDescription(description);
  }

  @Override
  public Map<Long, Level> getAllLevelMap() {
    List<Level> allLevels = levelDAO.getAll();
    return allLevels.stream().collect(Collectors.toMap(Level::getId, Function.identity()));
  }

  @Override
  public Level insert(Level level) {
    if (null == level) {
      level = getDefaultLevel();
    }
    List<Level> levels = levelDAO.getByDescription(level.getDescription());
    if (0 == levels.size()) {
      levelDAO.insert(level);
      return levelDAO.getById(level.getId());
    }
    return levels.get(0);
  }

  @Override
  public int deleteById(long id) {
    return levelDAO.deleteById(id);
  }

  private static Level getDefaultLevel() {
    return Level.builder().description(DEFAULT_DESCRIPTION).build();
  }

}
