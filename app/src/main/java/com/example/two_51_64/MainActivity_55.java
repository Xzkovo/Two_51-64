package com.example.two_51_64;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.two_51_64.Fragment.one_55;
import com.example.two_51_64.Fragment.two_55;

import java.util.ArrayList;
import java.util.List;

public class MainActivity_55 extends AppCompatActivity {
    private ViewPager mVp;
    private ImageView mBack;
    private FragmentAdapter fragmentAdapter;
    private final List<Fragment> fragments = new ArrayList<>();
    private TextView mOne;
    private TextView mTwo;
    private TextView mTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main55);

        initView();

        mVp.setOffscreenPageLimit(2);
        mVp.setAdapter(fragmentAdapter);
        mVp.setCurrentItem(0);
        mVp.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                changeTextColor(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }


    private void changeTextColor(int position) {
        if (position == 0) {
            mTitle.setText("一号站台");
            mOne.setBackgroundColor(Color.parseColor("#2196F3"));
            mOne.setTextColor(Color.WHITE);
            mTwo.setBackgroundColor(Color.parseColor("#ffffff"));
            mTwo.setTextColor(Color.parseColor("#000000"));
        } else if (position == 1) {
            mTitle.setText("二号站台");
            mTwo.setBackgroundColor(Color.parseColor("#2196F3"));
            mTwo.setTextColor(Color.WHITE);
            mOne.setBackgroundColor(Color.parseColor("#ffffff"));
            mOne.setTextColor(Color.parseColor("#000000"));
        }
    }


    private void initView() {
        fragmentAdapter = new FragmentAdapter(this.getSupportFragmentManager(), fragments);
        fragments.add(new one_55());
        fragments.add(new two_55());
        mVp = (ViewPager) findViewById(R.id.vp);
        mBack = (ImageView) findViewById(R.id.back);
        mBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        mOne = (TextView) findViewById(R.id.one);
        mTwo = (TextView) findViewById(R.id.two);
        mOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mVp.setCurrentItem(0);
            }
        });
        mTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mVp.setCurrentItem(1);
            }
        });
        mTitle = (TextView) findViewById(R.id.title);
    }

    private static class FragmentAdapter extends FragmentPagerAdapter {
        List<Fragment> fragmentList = new ArrayList<Fragment>();

        public FragmentAdapter(FragmentManager fm, List<Fragment> fragmentList) {
            super(fm);
            this.fragmentList = fragmentList;
        }

        @Override
        public Fragment getItem(int position) {
            return fragmentList.get(position);
        }

        @Override
        public int getCount() {
            return fragmentList.size();
        }
    }


}