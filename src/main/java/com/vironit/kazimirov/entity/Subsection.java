package com.vironit.kazimirov.entity;

import java.util.Objects;

public class Subsection {
    private int id;
    private String title;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Subsection(int id, String title){
        this.id=id;
        this.title=title;
    }

    public Subsection() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Subsection that = (Subsection) o;
        return id == that.id &&
                Objects.equals(title, that.title);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title);
    }

    @Override
    public String toString() {
        return "Subsection" +
                "id=" + id +
                ", title='" + title + '\'';
    }
}
