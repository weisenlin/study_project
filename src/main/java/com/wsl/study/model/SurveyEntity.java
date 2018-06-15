package com.wsl.study.model;

import java.util.Date;

public class SurveyEntity {
    private String id;

    private String title;

    private String template;

    private String subtitle;

    private String note;

    private Date startdate;

    private Date enddate;

    private Integer postback;

    private Integer weight;

    private Integer validate;

    private String endnote;

    private String endaction;

    private String endactionname;

    private Integer period;

    private String describe;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    public String getTemplate() {
        return template;
    }

    public void setTemplate(String template) {
        this.template = template == null ? null : template.trim();
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle == null ? null : subtitle.trim();
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note == null ? null : note.trim();
    }

    public Date getStartdate() {
        return startdate;
    }

    public void setStartdate(Date startdate) {
        this.startdate = startdate;
    }

    public Date getEnddate() {
        return enddate;
    }

    public void setEnddate(Date enddate) {
        this.enddate = enddate;
    }

    public Integer getPostback() {
        return postback;
    }

    public void setPostback(Integer postback) {
        this.postback = postback;
    }

    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    public Integer getValidate() {
        return validate;
    }

    public void setValidate(Integer validate) {
        this.validate = validate;
    }

    public String getEndnote() {
        return endnote;
    }

    public void setEndnote(String endnote) {
        this.endnote = endnote == null ? null : endnote.trim();
    }

    public String getEndaction() {
        return endaction;
    }

    public void setEndaction(String endaction) {
        this.endaction = endaction == null ? null : endaction.trim();
    }

    public String getEndactionname() {
        return endactionname;
    }

    public void setEndactionname(String endactionname) {
        this.endactionname = endactionname == null ? null : endactionname.trim();
    }

    public Integer getPeriod() {
        return period;
    }

    public void setPeriod(Integer period) {
        this.period = period;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe == null ? null : describe.trim();
    }
}