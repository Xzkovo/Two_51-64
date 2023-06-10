package com.example.two_51_64;

import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.RemoteException;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.MapView;
import com.amap.api.maps.MapsInitializer;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.MarkerOptions;
import com.example.two_51_64.HTTP.HttpUtil;
import com.example.two_51_64.User.JWD;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class MainActivity_51 extends AppCompatActivity {

    private ImageView mBack;
    private AMap aMap;
    private MapView mMap;
    private ImageView mMapLocation;
    private int images[] = {R.drawable.marker_one, R.drawable.marker_second, R.drawable.marker_thrid, R.drawable.marker_forth};
    private ImageView mMapLayer;
    private ImageView mMapMarker;
    private TextView mXs;
    private List<JWD> jwds;
    private  int qqq=123;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main51);

        try {
            MapsInitializer.initialize(getApplicationContext());
            MapsInitializer.setTerrainEnable(true);

            //隐私权限
            MapsInitializer.updatePrivacyShow(this, true, true);
            MapsInitializer.updatePrivacyAgree(this, true);


        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }

        initView();

        get_site_data();

        //在activity执行onCreate时执行mMapView.onCreate(savedInstanceState)，创建地图
        mMap = (MapView) findViewById(R.id.map);
        mMap.onCreate(savedInstanceState);

        if (aMap == null) {
            aMap = mMap.getMap();
        }

        getUisettinegs();


        mMapMarker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 创建 MarkerOptions 对象，并设置标记点的位置、标题、内容和图标
                MarkerOptions optionA = new MarkerOptions()
                        .position(new LatLng(39.906901, 116.397972))
                        .title("1号小车")
                        .snippet("天安门广场")
                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.marker_one));
                MarkerOptions optionB = new MarkerOptions()
                        .position(new LatLng(39.949913, 116.435838))
                        .title("二号小车")
                        .snippet("当代MOMA")
                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.marker_second));
                MarkerOptions optionC = new MarkerOptions()
                        .position(new LatLng(39.940709, 116.330669))
                        .title("三号小车")
                        .snippet("北京动物园")
                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.marker_thrid));
                MarkerOptions optionD = new MarkerOptions()
                        .position(new LatLng(39.950665, 116.350769))
                        .title("四号小车")
                        .snippet("交通大学路")
                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.marker_forth));

// 在地图中添加标记点
                aMap.addMarker(optionA);
                aMap.addMarker(optionB);
                aMap.addMarker(optionC);
                aMap.addMarker(optionD);
                mXs.setText("1、2、3、4号小车地图标记已完成");
            }
        });

        mMapLayer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu popupMenu = new PopupMenu(MainActivity_51.this, view);
                popupMenu.inflate(R.menu.menu1);
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        switch (menuItem.getItemId()) {
                            case R.id.DHST:
                                aMap.setMapType(AMap.MAP_TYPE_NAVI);//导航地图
                                mXs.setText("已切换到导航视图");
                                break;
                            case R.id.YJST:
                                aMap.setMapType(AMap.MAP_TYPE_NIGHT);//夜景地图
                                mXs.setText("已切换到夜景地图");
                                break;
                            case R.id.BZST:
                                aMap.setMapType(AMap.MAP_TYPE_NORMAL);//普通地图
                                mXs.setText("已切换到普通地图");
                                break;
                            case R.id.WXST:
                                aMap.setMapType(AMap.MAP_TYPE_SATELLITE);//普通地图
                                mXs.setText("已切换到普通地图");
                                break;
                        }
                        return false;
                    }
                });
                popupMenu.show();
            }
        });

    }

    private void get_site_data() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("UserName", "user1");
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        new HttpUtil()
                .sendResUtil("get_site_data", jsonObject.toString(), "POST", new Callback() {
                    @Override
                    public void onFailure(@NonNull Call call, @NonNull IOException e) {

                    }

                    @Override
                    public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                        String s = response.body().string();
                        try {
                            JSONObject jsonObject1 = new JSONObject(s);
                            jwds = new Gson().fromJson(jsonObject1.getJSONArray("ROWS_DETAIL").toString(), new TypeToken<List<JWD>>() {
                            }.getType());
                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }

                    }
                });
    }

    private void getUisettinegs() {
        aMap.getUiSettings().setZoomControlsEnabled(true);  //控制缩放按钮是否可见。
        aMap.getUiSettings().setScrollGesturesEnabled(true);        //控制地图平移手势是否启用。
        aMap.getUiSettings().setZoomGesturesEnabled(true);      //控制地图缩放手势是否启用。
        aMap.getUiSettings().setTiltGesturesEnabled(true);      //控制地图倾斜手势是否启用。
        aMap.getUiSettings().setRotateGesturesEnabled(true); // 控制地图旋转手势是否启用。
        aMap.getUiSettings().setIndoorSwitchEnabled(true);
    }


    private void initView() {
        mBack = (ImageView) findViewById(R.id.back);
        mMap = (MapView) findViewById(R.id.map);
        mBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        mMapLocation = (ImageView) findViewById(R.id.map_location);
        mMapLayer = (ImageView) findViewById(R.id.map_layer);
        mMapMarker = (ImageView) findViewById(R.id.map_marker);
        mXs = (TextView) findViewById(R.id.xs);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        //在activity执行onDestroy时执行mMapView.onDestroy()，销毁地图
        mMap.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
        //在activity执行onResume时执行mMapView.onResume ()，重新绘制加载地图
        mMap.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        //在activity执行onPause时执行mMapView.onPause ()，暂停地图的绘制
        mMap.onPause();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //在activity执行onSaveInstanceState时执行mMapView.onSaveInstanceState (outState)，保存地图当前的状态
        mMap.onSaveInstanceState(outState);
    }

}