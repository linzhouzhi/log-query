package com.lzz.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by lzz on 2018/4/18.
 */
@Controller
public class ServiceController {
    @RequestMapping("/services")
    public String serviceList(Model model) {
        return "service-list";
    }
}
