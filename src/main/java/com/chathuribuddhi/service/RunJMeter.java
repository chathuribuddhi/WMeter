package com.chathuribuddhi.service;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

/**
 * Created by CHATHURI on 2017-02-26.
 */
@WebServlet("/RunJMeter")
public class RunJMeter extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        String url = request.getParameter("url");
//        String usercount = request.getParameter("usercount");
        System.out.println("request received : " + new Date().toString());
    }
}
