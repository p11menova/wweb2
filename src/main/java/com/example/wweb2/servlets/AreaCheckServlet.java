package com.example.wweb2.servlets;

import com.example.wweb2.bean.Result;
import com.example.wweb2.bean.ResultBean;
import com.example.wweb2.logic.ServerLogic;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.logging.Logger;

@WebServlet("/areaCheck-servlet")
public class AreaCheckServlet extends HttpServlet {
    public static Logger logger = Logger.getLogger(AreaCheckServlet.class.getName());
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        logger.info("into areaCheckServlet");

        long executionStart = System.nanoTime();

        String x = req.getParameter("x");
        String y = req.getParameter("y");
        String r = req.getParameter("r");


        boolean inArea = ServerLogic.checkIfInArea(x, y, r);
        logger.info("success: " + inArea);

        ResultBean bean = (ResultBean) req.getSession().getAttribute("resultBean");
        if (bean == null) {
            bean = new ResultBean();
            req.getSession().setAttribute("resultBean", bean);
        }

        Result result = new Result();
        result.setX(new DecimalFormat("#0.000").format(Float.parseFloat(x)));
        result.setY(new DecimalFormat("#0.000").format(Float.parseFloat(y)));
        result.setR(r);
        result.setSuccess(inArea);
        result.setExecutionTime(String.valueOf(new DecimalFormat("#0.00").format((System.nanoTime() - executionStart) * 0.000001)).replaceAll(",", "."));
        LocalDateTime ldt = LocalDateTime.now();
        String current_time = ldt.format(DateTimeFormatter.ofPattern("HH:mm:ss"));
        result.setCurrentTime(current_time);

        bean.addResult(result);

        logger.info("result: " + result);
        logger.info("resultBean:" + bean);
        resp.setStatus(200);
        resp.setContentType("application/json");
        resp.getWriter().write(result.toString());
        resp.getWriter().flush();

    }
}
