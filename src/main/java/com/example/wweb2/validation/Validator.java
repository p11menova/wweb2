package com.example.wweb2.validation;


import com.example.wweb2.servlets.ControllerServlet;
import jakarta.servlet.http.HttpServletRequest;

public class Validator {
    private final String x;
    private final String y;
    private final String r;


    public Validator(HttpServletRequest req){
        this.x = req.getParameter("x");
        this.y = req.getParameter("y");
        this.r = req.getParameter("r");
    }

    public boolean checkParameters(){
        return this.x != null && this.y != null && this.r != null;

    }
    public boolean checkCoordinates() throws NumberFormatException{
        float x1 = Float.parseFloat(this.x);
        float y1 = Float.parseFloat(this.y);
        int r1 = Integer.parseInt(this.r);
        ControllerServlet.logger.info("вот они гномики" + x1 +" "+ y1+" "+r1);
        ControllerServlet.logger.info("x1"+ (-5 <= x1 && x1 <= 5));
        if (!(-5 <= x1 && x1 <= 5) ) return false;
        ControllerServlet.logger.info("y1"+(-2 <= y1 && y1 <= 2));
        if (!(-2 <= y1 && y1 <= 2)) return false;
        return 0 <= r1 && r1 <= 5;
    }

}
