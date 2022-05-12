package com.ruoyi.system.vo;

import java.sql.Timestamp;

public class WeiXinVo {
    private String session_key;
    private String openid;
    private String phoneNumber;
    private String purePhoneNumber;
    private String countryCode;
    public  Watermark watermark;

    public String getSession_key() {
        return session_key;
    }
    public void setSession_key(String session_key) {
        this.session_key = session_key;
    }
    public String getOpenid() {
        return openid;
    }
    public void setOpenid(String openid) {
        this.openid = openid;
    }
    @Override
    public String toString() {
        return "WeiXinVo [session_key=" + session_key + ", openid=" + openid
                + "]";
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
    public String getPurePhoneNumber() {
        return purePhoneNumber;
    }
    public void setPurePhoneNumber(String purePhoneNumber) {
        this.purePhoneNumber = purePhoneNumber;
    }
    public String getCountryCode() {
        return countryCode;
    }
    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }
    public Watermark getWatermark() {
        return watermark;
    }
    public void setWatermark(Watermark watermark) {
        this.watermark = watermark;
    }

    class Watermark {
        private Timestamp timestamp;
        private String appid;
        public Timestamp getTimestamp() {
            return timestamp;
        }
        public void setTimestamp(Timestamp timestamp) {
            this.timestamp = timestamp;
        }
        public String getAppid() {
            return appid;
        }
        public void setAppid(String appid) {
            this.appid = appid;
        }
    }
}

