package com.lzz.logic;

import com.lzz.controller.FileController;
import com.lzz.dao.XmlDb;
import com.lzz.model.*;
import com.lzz.util.RemoteShellUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * Created by gl49 on 2018/4/19.
 */
@Component
public class FileLogic {
    @Resource
    private XmlDb xmlDb;
    private static String IP_FILE = "ips.xml";

    public Map<String, Map<String, String>> remoteExe(final ExeParam exeParam){
        Map<String, Map<String, String>> res = new HashMap();
        List<Host> hostList = exeParam.getHostList();
        final String cmd = exeParam.getCmd();
        res = getMulityHostExeRes( hostList, cmd );
        return  res;
    }

    private Map<String,Map<String,String>> getMulityHostExeRes(List<Host> hostList, final String cmd) {
        Map<String, Map<String, String>> res = new HashMap();
        List<Future<Map<String, String>>> futureList = new ArrayList();
        for(int i = 0; i < hostList.size(); i++){
            final Host host = hostList.get( i );
            Future<Map<String, String>> future = FileController.executorService.submit(new Callable<Map<String, String>>() {
                @Override
                public Map<String, String> call() throws Exception {
                    RemoteShellUtil remoteShellUtil = new RemoteShellUtil( host.getIp(), host.getUsername(), host.getPassword() );
                    String res = remoteShellUtil.execRemote( cmd );
                    Map<String, String> resMap = new HashMap();
                    resMap.put("ip", host.getIp());
                    resMap.put("res", res);
                    return resMap;
                }
            });
            futureList.add( future );
        }
        int i = 0;
        for(Future<Map<String, String>> future : futureList){
            try {
                Map<String, String> fRes = future.get();
                Map<String, String> eRes = new HashMap<String, String>();
                eRes.put("ip", fRes.get("ip"));
                eRes.put("con", fRes.get("res"));
                res.put( "ip-" + i++, eRes );
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }
        return res;
    }

    public String[] fileList(FileParam fileParam) {
        Host host = fileParam.getHost();
        RemoteShellUtil remoteShellUtil = new RemoteShellUtil( host.getIp(), host.getUsername(), host.getPassword() );
        String cmd = "ls  " + fileParam.getPath();
        String res = remoteShellUtil.execRemote( cmd );
        String[] resList = res.split("\n");
        return resList;
    }

    public Map<String,Map<String,String>> fileQuery(FileQueryParam queryParam) {
        Map<String, Map<String, String>> res = new HashMap();
        List<Host> hostList = queryParam.getHostList();
        String path = queryParam.getPath();
        List<String> files = queryParam.getFiles();
        String filestr = StringUtils.join( files, " ");
        String cmd = "cd " + path + "; grep -i " + queryParam.getQuery() + " " + filestr;
        res = getMulityHostExeRes( hostList, cmd );
        return  res;
    }

    public boolean addHost(HostParam hostParam) {
        String hostStr = hostParam.series();
        String key = hostParam.getService() + hostParam.getIp();
        return xmlDb.add(IP_FILE, key, hostStr);
    }

    public List<HostParam> hostList() {
        List<HostParam> hosts = new ArrayList();
        Map<String, String> hostMap  = xmlDb.list( IP_FILE );
        for(Map.Entry<String, String> entry : hostMap.entrySet()){
            String value = entry.getValue();
            HostParam hostParam = HostParam.unSeries( value );
            hosts.add( hostParam );
        }
        return hosts;
    }

    public static void main(String[] args){
        RemoteShellUtil remoteShellUtil = new RemoteShellUtil( "", "", "" );
        String res = remoteShellUtil.execRemote( "sed -n '5,10p' /tmp/hello" );
        System.out.println( res );
    }

}
