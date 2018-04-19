package com.lzz.model;

import java.util.List;

/**
 * Created by lzz on 2018/4/19.
 */
public class FileQueryParam {
    private List<Host> hostList;
    private String path;
    private List<String> files;
    private String query;

    public List<Host> getHostList() {
        return hostList;
    }

    public void setHostList(List<Host> hostList) {
        this.hostList = hostList;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public List<String> getFiles() {
        return files;
    }

    public void setFiles(List<String> files) {
        this.files = files;
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }
}
