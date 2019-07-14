package com.bytedance.androidcamp.network.dou.model;

import com.google.gson.annotations.SerializedName;

public class ResponePost {
    @SerializedName("url")public String url;
    @SerializedName("success")public boolean suc;
    public void setUrl(String url)
    {
        this.url = url;
    }
    public String getUrl()
    {
        return url;
    }
    public void setSuc(boolean suc)
    {
        this.suc = suc;
    }
    public boolean getSuc()
    {
        return suc;
    }
    @Override
    public String toString() {
        return "suc";
    }
}
