package com.lzz.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by lzz on 2018/4/18.
 */
@Controller
public class FileController {
    @RequestMapping("/files")
    public String fileList(Model model) {
        return "file-list";
    }
}
