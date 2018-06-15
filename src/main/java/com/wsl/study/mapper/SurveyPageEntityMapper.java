package com.wsl.study.mapper;

import com.wsl.study.model.SurveyPageEntity;

public interface SurveyPageEntityMapper {
    int deleteByPrimaryKey(String id);

    int insert(SurveyPageEntity record);

    int insertSelective(SurveyPageEntity record);

    SurveyPageEntity selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(SurveyPageEntity record);

    int updateByPrimaryKey(SurveyPageEntity record);
}