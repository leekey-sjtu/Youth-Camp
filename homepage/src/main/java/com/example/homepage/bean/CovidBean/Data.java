package com.example.homepage.bean.CovidBean;

import java.util.List;

public class Data {

    private Diseaseh5Shelf diseaseh5Shelf;
    private List<StatisGradeCityDetail> statisGradeCityDetail;
    public void setDiseaseh5Shelf(Diseaseh5Shelf diseaseh5Shelf) {
         this.diseaseh5Shelf = diseaseh5Shelf;
     }
     public Diseaseh5Shelf getDiseaseh5Shelf() {
         return diseaseh5Shelf;
     }

    public void setStatisGradeCityDetail(List<StatisGradeCityDetail> statisGradeCityDetail) {
         this.statisGradeCityDetail = statisGradeCityDetail;
     }
     public List<StatisGradeCityDetail> getStatisGradeCityDetail() {
         return statisGradeCityDetail;
     }

}