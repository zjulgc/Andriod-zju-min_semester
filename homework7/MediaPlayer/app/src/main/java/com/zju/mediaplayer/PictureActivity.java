package com.zju.mediaplayer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.zju.mediaplayer.Picture.ViewAdapter;

import java.util.ArrayList;
import java.util.List;

import static android.Manifest.permission.READ_EXTERNAL_STORAGE;

public class PictureActivity extends AppCompatActivity {

    ViewPager pager = null;
    LayoutInflater layoutInflater = null;
    List<View> pages = new ArrayList<View>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_picture);

        layoutInflater = getLayoutInflater();
        pager = (ViewPager)findViewById(R.id.view_page);

        addImage(R.raw.drawableimage);
        addImage(R.drawable.ic_markunread);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if(PackageManager.PERMISSION_GRANTED != checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE))
            {
                Toast.makeText(PictureActivity.this, "You can not read sdcard!\nPlease get permissions first", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(PictureActivity.this, GetPermissionActivity.class);
                startActivity(intent);
            }
            addImage("/sdcard/DCIM/Camera/IMG_20190714_180951.jpg");
        }
        addImage("file:///android_asset/assetsimage.jpg");
        addImage(R.raw.rawimage);
        addImage("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1562328963756&di=9c0c6c839381c8314a3ce8e7db61deb2&imgtype=0&src=http%3A%2F%2Fpic13.nipic.com%2F20110316%2F5961966_124313527122_2.jpg");
        ViewAdapter adapter = new ViewAdapter();
        adapter.setDatas(pages);
        pager.setAdapter(adapter);
    }

    private void addImage(int resId) {
        ImageView imageView = (ImageView) layoutInflater.inflate(R.layout.activity_image_item, null);
        Glide.with(this)
                .load(resId)
                .error(R.drawable.error)
                .into(imageView);
        pages.add(imageView);
    }

    private void addImage(String path) {
        ImageView imageView = (ImageView) layoutInflater.inflate(R.layout.activity_image_item, null);
        Glide.with(this)
                .load(path)
                .apply(new RequestOptions().circleCrop().diskCacheStrategy(DiskCacheStrategy.ALL))
                .error(R.drawable.error)
                .into(imageView);
        pages.add(imageView);
    }
}
