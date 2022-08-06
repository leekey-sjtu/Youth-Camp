package com.example.homepage.bean.CovidBean;

import java.util.List;

public class AreaTree {

    private Today today;
    private Total total;
    private List<Children> children;
    private String name;
    public void setToday(Today today) {
         this.today = today;
     }
     public Today getToday() {
         return today;
     }

    public void setTotal(Total total) {
         this.total = total;
     }
     public Total getTotal() {
         return total;
     }

    public void setChildren(List<Children> children) {
         this.children = children;
     }
     public List<Children> getChildren() {
         return children;
     }

    public void setName(String name) {
         this.name = name;
     }
     public String getName() {
         return name;
     }

}