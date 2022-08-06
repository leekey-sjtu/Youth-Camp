package com.example.homepage.bean;

import java.util.List;

public class NewsResponse {

    private String code;
    private boolean charge;
    private String msg;
    private Result1 result;

    public void setCode(String code){
        this.code = code;
    }
    public String getCode(){
        return this.code;
    }
    public void setCharge(boolean charge){
        this.charge = charge;
    }
    public boolean getCharge(){
        return this.charge;
    }
    public void setMsg(String msg){
        this.msg = msg;
    }
    public String getMsg(){
        return this.msg;
    }
    public void setResult(Result1 result){
        this.result = result;
    }
    public Result1 getResult(){
        return this.result;
    }


    public static class Result1 {

        private String status;
        private String msg;
        private Result2 result;

        public void setStatus(String status){
            this.status = status;
        }
        public String getStatus(){
            return this.status;
        }
        public void setMsg(String msg){
            this.msg = msg;
        }
        public String getMsg(){
            return this.msg;
        }
        public void setResult(Result2 result){
            this.result = result;
        }
        public Result2 getResult(){
            return this.result;
        }
    }


    public static class Result2 {

        private String channel;
        private String num;

        private List<Info> list;

        public void setChannel(String channel){
            this.channel = channel;
        }
        public String getChannel(){
            return this.channel;
        }
        public void setNum(String num){
            this.num = num;
        }
        public String getNum(){
            return this.num;
        }
        public void setList(List<Info> list){
            this.list = list;
        }
        public List<Info> getList(){
            return this.list;
        }
    }

    public static class Info {

        private String title;
        private String time;
        private String src;
        private String category;
        private String pic;
        private String content;
        private String url;
        private String weburl;

        public void setTitle(String title){
            this.title = title;
        }
        public String getTitle(){
            return this.title;
        }
        public void setTime(String time){
            this.time = time;
        }
        public String getTime(){
            return this.time;
        }
        public void setSrc(String src){
            this.src = src;
        }
        public String getSrc(){
            return this.src;
        }
        public void setCategory(String category){
            this.category = category;
        }
        public String getCategory(){
            return this.category;
        }
        public void setPic(String pic){
            this.pic = pic;
        }
        public String getPic(){
            return this.pic;
        }
        public void setContent(String content){
            this.content = content;
        }
        public String getContent(){
            return this.content;
        }
        public void setUrl(String url){
            this.url = url;
        }
        public String getUrl(){
            return this.url;
        }
        public void setWeburl(String weburl){
            this.weburl = weburl;
        }
        public String getWeburl(){
            return this.weburl;
        }
    }

}