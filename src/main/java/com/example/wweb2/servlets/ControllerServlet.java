package com.example.wweb2.servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import com.example.wweb2.validation.Validator;


import java.io.IOException;

import java.util.logging.Logger;



@WebServlet("/controller-servlet")
public class ControllerServlet extends HttpServlet {
    public static Logger logger = Logger.getLogger(ControllerServlet.class.getName());

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        logger.info("received request:" + req.getQueryString());
        Validator validator = new Validator(req);

        if (!validator.checkParameters()) {
            logger.severe("400 error wrong params number");
            send400(resp, "неверное количество аргументов");
            return;
        };
        try {
            logger.info("чекаю валидность координат");
            if (!validator.checkCoordinates()) {
                send400(resp, "нееееет сюда НЕЛЬЗЯ тапать");
                return;}
            logger.info("дааа все норм");
            req.getRequestDispatcher("/areaCheck-servlet").forward(req, resp);

        } catch (NumberFormatException e){
            logger.severe("400 error ОДЗ");
            send400(resp, "невалидные параметры тн ОДЗ");
            return;
        }
    }
    public void send400(HttpServletResponse resp,String msg) throws IOException {
        logger.info("sending 400 response");
        resp.setContentType("application/json");
        resp.setStatus(400);
        resp.setCharacterEncoding("UTF-8");
        resp.getWriter().write("{\"error\": \"%s\"}".formatted(msg));
    }

;}
