package com.lzz.controller;

import com.lzz.logic.FileLogic;
import com.lzz.model.ExeParam;
import com.lzz.model.Response;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
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

    @RequestMapping("/remote-exe")
    @ResponseBody
    public Response remoteExe(@RequestBody  ExeParam exeParam) {
        Map<String, Map<String, String>> res = fileLogic.remoteExe( exeParam );
        return Response.Obj(0, res);
    }


}
