package com.example.TestTaskOvsiyAurosKS.entity;

import java.util.List;

public class KSet {

    private Long id;
    private String title;
    private List<KPac> kPacList;
    private String deleteButton;

    public KSet(Long id, String title) {
        this.id = id;
        this.title = title;
        this.deleteButton = " <button data-clipboard-text=\"dxi dxi-delete\" class=\"icon-card\">\n" +
                "        <i class=\"dxi dxi-delete\"> </i>\n" +
                "    </button>";
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDeleteButton() {
        return deleteButton;
    }

    public List<KPac> getkPacList() {
        return kPacList;
    }

    public void setkPacList(List<KPac> kPacList) {
        this.kPacList = kPacList;
    }

    public void setDeleteButton(String deleteButton) {
        this.deleteButton = deleteButton;
    }
}
