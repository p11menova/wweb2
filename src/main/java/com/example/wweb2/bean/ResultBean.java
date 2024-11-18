package com.example.wweb2.bean;


import java.io.Serializable;
import java.util.LinkedList;


public class ResultBean implements Serializable {
    private LinkedList<Result> results = new LinkedList<>();

    public void addResult(Result result) {
        this.results.add(result);
    }

    public LinkedList<Result> getResults() {
        return results;
    }

    public void setResults(LinkedList<Result> results) {
        this.results = results;
    }
}
