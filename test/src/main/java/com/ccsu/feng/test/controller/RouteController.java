package com.ccsu.feng.test.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * @author admin
 * @create 2020-03-07-16:41
 */
@Controller
public class RouteController {

    @RequestMapping("/page/person/{type}")
    public String toPerson(@PathVariable("type") String type,RedirectAttributes attr){
        System.out.println(type);
//        attr.addFlashAttribute("type", type);
        attr.addAttribute("type",type);
        return "redirect:/page/person";
    }
}
