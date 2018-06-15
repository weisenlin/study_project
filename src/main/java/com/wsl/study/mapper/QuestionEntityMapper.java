package com.wsl.study.mapper;

import com.wsl.study.model.QuestionEntity;

public interface QuestionEntityMapper {
    int deleteByPrimaryKey(String id);

    int insert(QuestionEntity record);

    int insertSelective(QuestionEntity record);

    QuestionEntity selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(QuestionEntity record);

    int updateByPrimaryKey(QuestionEntity record);
}