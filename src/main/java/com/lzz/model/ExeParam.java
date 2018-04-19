package com.lzz.model;

import java.util.List;

/**
 * Created by gl49 on 2018/4/19.
 */
public class ExeParam {
    private List<Host> hostList;
    private String cmd;

    public List<Host> getHostList() {
        return hostList;
    }

    public void setHostList(List<Host> hostList) {
        this.hostList = hostList;
    }

    public String getCmd() {
        return cmd;
    }

    public void setCmd(String cmd) {
        this.cmd = cmd;
    }
}
