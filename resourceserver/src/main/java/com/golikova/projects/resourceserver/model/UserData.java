package com.golikova.projects.resourceserver.model;

import java.util.List;

public class UserData {

    private String selection;
    private List<String> sources;

    public List<String> getSources() {
        return sources;
    }

    public void setSources(List<String> sources) {
        this.sources = sources;
    }

    public String getSelection() {
        return selection;
    }

    public void setSelection(String selection) {
        this.selection = selection;
    }

}
