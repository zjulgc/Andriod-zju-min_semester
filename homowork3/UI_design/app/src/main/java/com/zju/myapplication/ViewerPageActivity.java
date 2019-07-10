package com.zju.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;
import com.zju.myapplication.frangment_collection.Fragment0;
import com.zju.myapplication.frangment_collection.Fragment2;
import com.zju.myapplication.frangment_collection.Frangment1;

public class ViewerPageActivity extends AppCompatActivity {

    private ViewPager mvp;
    private TabLayout mto;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewer_page);

        mto = findViewById(R.id.tab_out_1);
        mvp = findViewById(R.id.view_page_1);

        mvp.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                switch (position)
                {
                    case 0:
                        return (new Fragment0());
                    case 1:
                        return (new Frangment1());
                    case 2:
                        return (new Fragment2());
                }
                return null;
            }

            @Override
            public int getCount() {
                return 3;
            }

            @Override
            public CharSequence getPageTitle(int position) {
                return "item." + position;
            }
        });

        mto.setupWithViewPager(mvp);
    }
}
