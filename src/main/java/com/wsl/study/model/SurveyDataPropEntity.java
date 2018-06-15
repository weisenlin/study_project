package com.wsl.study.model;

public class SurveyDataPropEntity {
    private String id;

    private String surdataid;

    private String questionid;

    private String propvalue;

    private Integer rownumber;

    private String propid;

    private String propname;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getSurdataid() {
        return surdataid;
    }

    public void setSurdataid(String surdataid) {
        this.surdataid = surdataid == null ? null : surdataid.trim();
    }

    public String getQuestionid() {
        return questionid;
    }

    public void setQuestionid(String questionid) {
        this.questionid = questionid == null ? null : questionid.trim();
    }

    public String getPropvalue() {
        return propvalue;
    }

    public void setPropvalue(String propvalue) {
        this.propvalue = propvalue == null ? null : propvalue.trim();
    }

    public Integer getRownumber() {
        return rownumber;
    }

    public void setRownumber(Integer rownumber) {
        this.rownumber = rownumber;
    }

    public String getPropid() {
        return propid;
    }

    public void setPropid(String propid) {
        this.propid = propid == null ? null : propid.trim();
    }

    public String getPropname() {
        return propname;
    }

    public void setPropname(String propname) {
        this.propname = propname == null ? null : propname.trim();
    }
}