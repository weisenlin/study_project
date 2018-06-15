package com.wsl.study.model;

public class SurveyPageEntity {
    private String id;

    private String surid;

    private String title;

    private Integer index;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getSurid() {
        return surid;
    }

    public void setSurid(String surid) {
        this.surid = surid == null ? null : surid.trim();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    public Integer getIndex() {
        return index;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }
}