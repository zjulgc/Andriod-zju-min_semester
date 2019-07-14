package com.bytedance.androidcamp.network.dou.api;

import com.bytedance.androidcamp.network.dou.model.ResponePost;
import com.bytedance.androidcamp.network.dou.model.ResponseGet;
import com.bytedance.androidcamp.network.dou.model.Video;

import java.util.List;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface IMiniDouyinService {
    // TODO 7: Define IMiniDouyinService

    String HOST = "http://test.androidcamp.bytedance.com/mini_douyin/invoke/";
    String PATH = "video";


    @GET(PATH)
    Call<ResponseGet> getvideos();

    @Multipart
    @POST(PATH)
    Call<ResponePost> postvideo(
            @Query("student_id") String studentId,
            @Query("user_name") String username,
            @Part MultipartBody.Part image,
            @Part MultipartBody.Part video
    );

}
