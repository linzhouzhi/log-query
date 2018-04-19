package com.lzz.controller;

import com.lzz.logic.FileLogic;
import com.lzz.model.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by lzz on 2018/4/18.
 */
@Controller
public class FileController {
    public static ExecutorService  executorService = Executors.newCachedThreadPool();

    @Resource
    FileLogic fileLogic;

    @RequestMapping("/files")
    public String fileList(Model model) {
        return "file-list";
    }

    @RequestMapping("/add-host")
    @ResponseBody
    public Response addHost(@RequestBody HostParam hostParam) {
        boolean res = fileLogic.addHost( hostParam );
        if( res ){
            return Response.OK();
        }
        return Response.Fail();
    }

    @RequestMapping(value = "/host-list",  method = RequestMethod.GET)
    @ResponseBody
    public Response hostList() {
        List<HostParam> res = fileLogic.hostList();
        return Response.Obj(0, res);
    }


    @RequestMapping("/remote-exe")
    @ResponseBody
    public Response remoteExe(@RequestBody  ExeParam exeParam) {
        Map<String, Map<String, String>> res = fileLogic.remoteExe( exeParam );
        return Response.Obj(0, res);
    }

    @RequestMapping("/file-list")
    @ResponseBody
    public Response fileList(@RequestBody FileParam fileParam) {
        String[] res = fileLogic.fileList( fileParam );
        return Response.Obj(0, res);
    }

    @RequestMapping("/file-query")
    @ResponseBody
    public Response fileQuery(@RequestBody FileQueryParam queryParam) {
        Map<String, Map<String, String>> res = fileLogic.fileQuery( queryParam );
        return Response.Obj(0, res);
    }


}
