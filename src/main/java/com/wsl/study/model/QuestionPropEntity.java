package com.wsl.study.model;

public class QuestionPropEntity {
    private String id;

    private String quesid;

    private String propname;

    private String propvalue;

    private Integer rownumber;

    private String propognl;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getQuesid() {
        return quesid;
    }

    public void setQuesid(String quesid) {
        this.quesid = quesid == null ? null : quesid.trim();
    }

    public String getPropname() {
        return propname;
    }

    public void setPropname(String propname) {
        this.propname = propname == null ? null : propname.trim();
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

    public String getPropognl() {
        return propognl;
    }

    public void setPropognl(String propognl) {
        this.propognl = propognl == null ? null : propognl.trim();
    }
}