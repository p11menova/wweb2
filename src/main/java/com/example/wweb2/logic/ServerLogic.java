package com.example.wweb2.logic;

public class ServerLogic {
    public static boolean checkIfInArea(String x1, String y1, String r1) {
        var x = Float.parseFloat(x1);
        var y = Float.parseFloat(y1);
        var r = Integer.parseInt(r1);

        if (x >= 0 && y >= 0) { // 1-st
            return x * x + y * y <= r * r;
        }

        else if (x <= 0 && y >= 0) { // 2-nd
            return x >= (float) -r /2 && y <= r;
        }

        else if (x <= 0 && y <= 0) { // 3-rd
            return y >= -x - r;
        }

        return false; // another = outside
    }
}
