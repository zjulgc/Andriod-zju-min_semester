package com.bytedance.androidcamp.network.dou.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResponseGet {
    @SerializedName("feeds") public List<Video> videos;
    @SerializedName("success") public boolean suc;
}
