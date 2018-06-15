package com.wsl.study.mapper;

import com.wsl.study.model.SurveyDataEntity;

public interface SurveyDataEntityMapper {
    int deleteByPrimaryKey(String id);

    int insert(SurveyDataEntity record);

    int insertSelective(SurveyDataEntity record);

    SurveyDataEntity selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(SurveyDataEntity record);

    int updateByPrimaryKey(SurveyDataEntity record);
}