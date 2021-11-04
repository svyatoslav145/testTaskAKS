package com.example.TestTaskOvsiyAurosKS.entity;

public class KPac {

    private Long id;
    private String title;
    private String description;
    private String creationDate;
    private String deleteButton;

    public KPac() {
    }

    public KPac(Long id, String title, String description, String creationDate) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.creationDate = creationDate;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(String creationDate) {
        this.creationDate = creationDate;
    }

    public String getDeleteButton() {
        return deleteButton;
    }

    public void setDeleteButton(String deleteButton) {
        this.deleteButton = deleteButton;
    }
}
