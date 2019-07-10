package com.zju.myapplication;

import androidx.fragment.app.Fragment;

import com.zju.myapplication.frangment_collection.Fragment0;
import com.zju.myapplication.frangment_collection.Fragment2;
import com.zju.myapplication.frangment_collection.Frangment1;

public class MyFragment extends Fragment {

    Fragment fragment = null;

    public MyFragment(int position)
    {
        switch (position)
        {
            case 0:
                fragment = new Fragment0();
                break;
            case 1:
                fragment = new Frangment1();
                break;
            case 2:
                fragment = new Fragment2();
                break;
        }
    }

}
