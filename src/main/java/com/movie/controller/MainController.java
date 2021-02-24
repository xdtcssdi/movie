package com.movie.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
public class MainController {
    @GetMapping("/")
    public String index() {
        return "mainPage";
    }

    @GetMapping("/mainPage")
    public String mainPage() {
        return "mainPage";
    }

    @GetMapping("/loginPage")
    public String login2() {
        return "login";
    }

    @GetMapping("/buySeat")
    public String buySeat() {
        return "buySeat";
    }

    @GetMapping("/buyTickets")
    public String buyTickets() {
        return "buyTickets";
    }

    @GetMapping("/center")
    public String center() {
        return "center";
    }

    @GetMapping("/footer")
    public String footer() {
        return "footer";
    }

    @GetMapping("/header")
    public String header() {
        return "header";
    }

    @GetMapping("/manage")
    public String manage() {
        return "manage";
    }

    @GetMapping("/movieDetail")
    public String movieDetail() {
        return "movieDetail";
    }

    @GetMapping("/movieList")
    public String movieList() {
        return "movieList";
    }

    @GetMapping("/pay")
    public String pay() {
        return "pay";
    }

    @GetMapping("/payStatus")
    public String payStatus() {
        return "payStatus";
    }

    @GetMapping("/selectSeat")
    public String selectSeat() {
        return "selectSeat";
    }
}
