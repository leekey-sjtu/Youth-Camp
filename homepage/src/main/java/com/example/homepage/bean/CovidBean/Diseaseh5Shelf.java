package com.example.homepage.bean.CovidBean;

import java.util.List;

public class Diseaseh5Shelf {

    private String lastUpdateTime;
    private ChinaTotal chinaTotal;
    private ChinaAdd chinaAdd;
    private boolean isShowAdd;
    private ShowAddSwitch showAddSwitch;
    private List<AreaTree> areaTree;
    public void setLastUpdateTime(String lastUpdateTime) {
         this.lastUpdateTime = lastUpdateTime;
     }
     public String getLastUpdateTime() {
         return lastUpdateTime;
     }

    public void setChinaTotal(ChinaTotal chinaTotal) {
         this.chinaTotal = chinaTotal;
     }
     public ChinaTotal getChinaTotal() {
         return chinaTotal;
     }

    public void setChinaAdd(ChinaAdd chinaAdd) {
         this.chinaAdd = chinaAdd;
     }
     public ChinaAdd getChinaAdd() {
         return chinaAdd;
     }

    public void setIsShowAdd(boolean isShowAdd) {
         this.isShowAdd = isShowAdd;
     }
     public boolean getIsShowAdd() {
         return isShowAdd;
     }

    public void setShowAddSwitch(ShowAddSwitch showAddSwitch) {
         this.showAddSwitch = showAddSwitch;
     }
     public ShowAddSwitch getShowAddSwitch() {
         return showAddSwitch;
     }

    public void setAreaTree(List<AreaTree> areaTree) {
         this.areaTree = areaTree;
     }
     public List<AreaTree> getAreaTree() {
         return areaTree;
     }

}