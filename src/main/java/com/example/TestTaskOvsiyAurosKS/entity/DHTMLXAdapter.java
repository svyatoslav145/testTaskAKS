package com.example.TestTaskOvsiyAurosKS.entity;

import java.util.List;

public class DHTMLXAdapter<T> {

    private List<T> data;
    private int total_count = 0;
    private int from = 0;

    public DHTMLXAdapter(List<T> data) {
        this.data = data;
        this.total_count = data.size();
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }

    public int getTotal_count() {
        return total_count;
    }

    public void setTotal_count(int total_count) {
        this.total_count = total_count;
    }

    public int getFrom() {
        return from;
    }

    public void setFrom(int from) {
        this.from = from;
    }
}
