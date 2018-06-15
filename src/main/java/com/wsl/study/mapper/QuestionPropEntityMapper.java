package com.wsl.study.mapper;

import com.wsl.study.model.QuestionPropEntity;

public interface QuestionPropEntityMapper {
    int deleteByPrimaryKey(String id);

    int insert(QuestionPropEntity record);

    int insertSelective(QuestionPropEntity record);

    QuestionPropEntity selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(QuestionPropEntity record);

    int updateByPrimaryKey(QuestionPropEntity record);
}