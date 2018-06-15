package com.wsl.study.mapper;

import com.wsl.study.model.SurveyEntity;

public interface SurveyEntityMapper {
    int deleteByPrimaryKey(String id);

    int insert(SurveyEntity record);

    int insertSelective(SurveyEntity record);

    SurveyEntity selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(SurveyEntity record);

    int updateByPrimaryKey(SurveyEntity record);
}