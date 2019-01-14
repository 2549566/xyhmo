package com.xyhmo.domain;

import java.io.Serializable;

public class GenProjectOrder implements Serializable{

    private static final long serialVersionUID = -3835814076393043988L;

    /**
     * id
     *
     * */
    private Long id;
    /**
     * order 名称
     *
     * */
    private String projectName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }
}
