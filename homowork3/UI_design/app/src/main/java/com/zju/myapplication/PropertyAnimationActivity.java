package com.zju.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.ObjectAnimator;
import android.os.Bundle;

public class PropertyAnimationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_property_animation);

        Animator animator = AnimatorInflater.loadAnimator(this, R.animator.scale_property);
        animator.setTarget(findViewById(R.id.image_view));
        animator.start();
    }
}
