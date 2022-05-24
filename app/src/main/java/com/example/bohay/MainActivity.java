package com.example.bohay;


import android.annotation.SuppressLint;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.menu.MenuBuilder;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.bohay.model.DanhMuc;
import com.example.bohay.model.TinTuc;
import com.example.bohay.model.Url;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener{

    public DrawerLayout drawerLayout;
    public ActionBarDrawerToggle actionBarDrawerToggle;

    ListView lvDanhMuc;
    RecyclerView recycler_news;

    DanhMucAdapter danhMucAdapter;
    TinTucAdapter tinTucAdapter;

    ArrayList<DanhMuc> arrayListDanhmuc;
    ArrayList<TinTuc> tinTucArrayList;

    private SwipeRefreshLayout swipeRefreshLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        anhxa();
        addNavigationView();

        arrayListDanhmuc = new ArrayList<>();
        tinTucArrayList = new ArrayList<>();

        setTitle("Thời sự");

        loadDanhmuc();
        loadTintheoDanhmuc(1);


        danhMucAdapter = new DanhMucAdapter(MainActivity.this, arrayListDanhmuc);
        lvDanhMuc.setAdapter(danhMucAdapter);


        configRecyclerView();
        tinTucAdapter = new TinTucAdapter(MainActivity.this,tinTucArrayList);
        recycler_news.setAdapter(tinTucAdapter);

        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.colorRed));


        lvDanhMuc.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                tinTucArrayList.clear();

                String title = arrayListDanhmuc.get(i).getTendanhmuc();
                setTitle(title);
                int iddanhmuc = arrayListDanhmuc.get(i).getId_danhmuc();

                loadTintheoDanhmuc(iddanhmuc);
                configRecyclerView();
                tinTucAdapter = new TinTucAdapter(MainActivity.this,tinTucArrayList);
                recycler_news.setAdapter(tinTucAdapter);

            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        switch (item.getItemId()){
            case R.id.itemTinDaDoc:
                Toast.makeText(MainActivity.this, "Tin đã đọc", Toast.LENGTH_LONG).show();

                return true;
            case R.id.itemTinDaLuu:
                Toast.makeText(MainActivity.this, "Tin đã lưu", Toast.LENGTH_LONG).show();
                return true;
            case R.id.itemThongTin:
                Toast.makeText(MainActivity.this, "Thông tin ứng dụng", Toast.LENGTH_LONG).show();
                return true;
            case R.id.itemBandem:
                Toast.makeText(MainActivity.this, "Chế độ ban đêm", Toast.LENGTH_LONG).show();
                return true;
            case R.id.itemReload:
                Toast.makeText(MainActivity.this, "Đã load", Toast.LENGTH_LONG).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    @SuppressLint("RestrictedApi")
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();

        inflater.inflate(R.menu.option_menu, menu);
        if(menu instanceof MenuBuilder){
            MenuBuilder m = (MenuBuilder) menu;
            m.setOptionalIconsVisible(true);
        }
        return true;
    }

    private void anhxa(){
        lvDanhMuc = (ListView) findViewById(R.id.lvDanhmuc);
        recycler_news = (RecyclerView) findViewById(R.id.recyler_news);
        swipeRefreshLayout = findViewById(R.id.swipeRefreshLayout);

    }

    private void addNavigationView(){
        // drawer layout instance to toggle the menu icon to open
        // drawer and back button to close drawer
        drawerLayout = findViewById(R.id.my_drawer_layout);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.nav_open, R.string.nav_close);

        // pass the Open and Close toggle for the drawer layout listener
        // to toggle the button
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

        // to make the Navigation drawer icon always appear on the action bar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void loadDanhmuc(){
        final RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.POST, Url.getUrlListCategory(), null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {

                        for (int i = 0; i< response.length(); i++){
                            try {
                                JSONObject object = response.getJSONObject(i);
                                String ten = object.getString("tendanhmuc");
                                int iddanhmuc = object.getInt("id_danhmuc");
                                arrayListDanhmuc.add(new DanhMuc(iddanhmuc ,ten, R.drawable.ic_baseline_arrow_right_24));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            danhMucAdapter.notifyDataSetChanged();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(MainActivity.this, "Chưa có dữ liệu", Toast.LENGTH_LONG).show();
                    }
                });
        requestQueue.add(jsonArrayRequest);
    }

    private void loadTintheoDanhmuc(int id){

        String iddm =String.valueOf(id);

        final RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.POST, Url.getUrlAllByCate(iddm), null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        for (int i = 0; i< response.length(); i++){
                            try {
                                JSONObject object = response.getJSONObject(i);
                                int id_tin = object.getInt("id_tin");
                                String tieude = object.getString("tieude");
                                String urlImage = object.getString("anhminhhoa");
                                String ngaydang = object.getString("ngaydang");
                                String tendanhmuc = object.getString("tendanhmuc");
                                String nguontin = object.getString("nguontin");

                                tinTucArrayList.add(new TinTuc(id_tin ,tieude, urlImage ,ngaydang, tendanhmuc, nguontin));

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            tinTucAdapter.notifyDataSetChanged();
                        }

                    }


                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(MainActivity.this, "Chưa có dữ liệu", Toast.LENGTH_LONG).show();
                    }
                });
        requestQueue.add(jsonArrayRequest);

    }

    private void configRecyclerView() {
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this,2);
        recycler_news.hasFixedSize();
        recycler_news.setLayoutManager(layoutManager);
    }

    @Override
    public void onRefresh() {

        tinTucAdapter.notifyDataSetChanged();
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                swipeRefreshLayout.setRefreshing(false);
            }
        }, 3000);

    }
}