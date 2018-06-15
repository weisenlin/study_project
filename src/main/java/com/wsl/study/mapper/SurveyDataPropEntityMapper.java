package com.wsl.study.mapper;

import com.wsl.study.model.SurveyDataPropEntity;

public interface SurveyDataPropEntityMapper {
    int deleteByPrimaryKey(String id);

    int insert(SurveyDataPropEntity record);

    int insertSelective(SurveyDataPropEntity record);

    SurveyDataPropEntity selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(SurveyDataPropEntity record);

    int updateByPrimaryKey(SurveyDataPropEntity record);
}