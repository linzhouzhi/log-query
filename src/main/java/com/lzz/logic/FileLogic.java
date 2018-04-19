package com.lzz.logic;

import com.lzz.controller.FileController;
import com.lzz.model.ExeParam;
import com.lzz.model.Host;
import com.lzz.util.RemoteShellUtil;
import org.springframework.stereotype.Component;

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
    public Map<String, Map<String, String>> remoteExe(final ExeParam exeParam){
        Map<String, Map<String, String>> res = new HashMap();
        List<Host> hostList = exeParam.getHostList();
        List<Future<Map<String, String>>> futureList = new ArrayList();
        for(int i = 0; i < hostList.size(); i++){
            final Host host = hostList.get( i );
            Future<Map<String, String>> future = FileController.executorService.submit(new Callable<Map<String, String>>() {
                @Override
                public Map<String, String> call() throws Exception {
                    RemoteShellUtil remoteShellUtil = new RemoteShellUtil( host.getIp(), host.getUsername(), host.getPassword() );
                    String res = remoteShellUtil.execRemote( exeParam.getCmd() );
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
        return  res;
    }

    public static void main(String[] args){
        RemoteShellUtil remoteShellUtil = new RemoteShellUtil( "", "", "" );
        String res = remoteShellUtil.execRemote( "sed -n '5,10p' /tmp/hello" );
        System.out.println( res );
    }
}
