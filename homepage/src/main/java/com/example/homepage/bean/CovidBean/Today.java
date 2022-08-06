package com.example.homepage.bean.CovidBean;

public class Today {

    private String wzz_add;
    private long confirm;
    private int confirmCuts;
    private boolean isUpdated;
    public void setWzz_add(String wzz_add) {
         this.wzz_add = wzz_add;
     }
     public String getWzz_add() {
         return wzz_add;
     }

    public void setConfirm(long confirm) {
         this.confirm = confirm;
     }
     public long getConfirm() {
         return confirm;
     }

    public void setConfirmCuts(int confirmCuts) {
         this.confirmCuts = confirmCuts;
     }
     public int getConfirmCuts() {
         return confirmCuts;
     }

    public void setIsUpdated(boolean isUpdated) {
         this.isUpdated = isUpdated;
     }
     public boolean getIsUpdated() {
         return isUpdated;
     }

}