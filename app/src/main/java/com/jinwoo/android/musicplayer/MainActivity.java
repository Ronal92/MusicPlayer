package com.jinwoo.android.musicplayer;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.List;

public class MainActivity extends AppCompatActivity {


    public static MusicAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 버전체크해서 마시멜로우 보다 낮으면 런타임권한 체크를 하지 않는다.
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            checkPermission();
        } else {
            init();
        }


    }

    // 데이터를 로드한다.
    private void init(){

        Message.show("프로그램을 실행합니다.", this);
        // 3.1 데이터를 불러온다.
        List<Music> datas = DataLoader.get(this);
        // 3.2 리사이클러뷰 세팅
        listInit();

    }

    private void listInit(){

        RecyclerView listView = (RecyclerView)findViewById(R.id.recyclerView);
        adapter = new MusicAdapter(this);
        listView.setAdapter(adapter);
        listView.setLayoutManager(new LinearLayoutManager(this));

    }



    private final int REQ_CODE = 100;

    // 1. 권한체크
    @TargetApi(Build.VERSION_CODES.M) // Target 지정 애너테이션
    private void checkPermission(){
        // 1.1 런타임 권한 체크
        if( checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED  ){
            // 1.2 요청할 권한 목록 작성
            String permArr[] = {Manifest.permission.READ_EXTERNAL_STORAGE};
            // 1.3 시스템에 권한 요청
            requestPermissions(permArr, REQ_CODE); // 리퀘스트 창을 팝업해서 보여준다.

        } else{
            init();
        }
    }

    // 2. 권한체크 후 콜백<사용자가 확인 후 시스템이 호출하는 함수>
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == REQ_CODE){
            // 배열에 넘긴 런타임권한을 체크해서 승인이 됬으면
            if(grantResults[0] == PackageManager.PERMISSION_GRANTED ){
                // 프로그램 실행
                init();
            } else {
                Message.show("권한을 허용하지 않으면 프로그램을 실행할 수 없습니다", this);
                finish();
            }
        }
    }

}
