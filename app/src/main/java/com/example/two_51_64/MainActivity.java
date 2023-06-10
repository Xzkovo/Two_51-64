package com.example.two_51_64;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity {
    private DrawerLayout mDrawer;
    private ImageView mChange;
    private NavigationView mNav;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        mNav = (NavigationView) findViewById(R.id.nav);
        mDrawer = (DrawerLayout) findViewById(R.id.drawer);
        mChange = (ImageView) findViewById(R.id.change);
        mChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mDrawer.isDrawerOpen(GravityCompat.START)) {
                    mDrawer.closeDrawer(GravityCompat.START);
                } else {
                    mDrawer.openDrawer(GravityCompat.START);
                }
                mNav.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        Class myclass = null;
                        switch (item.getItemId()) {
                            case R.id.LXDT:
                                myclass = MainActivity_51.class;
                                break;
                            case R.id.RZCX:
                                myclass = MainActivity_53.class;
                                break;
                            case R.id.YHGL:
                                myclass = MainActivity_54.class;
                                break;
                            case R.id.GJCX:
                                myclass = MainActivity_55.class;
                                break;
                            case R.id.SSJT:
                                myclass = MainActivity_56.class;
                                break;
                            case R.id.HJJC:
                                myclass = MainActivity_57.class;
                                break;
                            case R.id.WZFX:
                                myclass = MainActivity_58.class;
                                break;
                            case R.id.CLWZ:
                                myclass = MainActivity_59.class;
                                break;
                            case R.id.WDDY:
                                myclass = MainActivity_60.class;
                                break;
                            case R.id.SHCX:
                                myclass = MainActivity_61.class;
                                break;
                            case R.id.WZLXFX:
                                myclass = MainActivity_62.class;
                                break;
                            case R.id.LXZS:
                                myclass = MainActivity_63.class;
                                break;
                            case R.id.GSETC:
                                myclass = MainActivity_64.class;
                                break;
                        }
                        if (myclass != null) {
                            Intent intent = new Intent(MainActivity.this, myclass);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);
                        }

                        return false;
                    }
                });

            }
        });

    }

}