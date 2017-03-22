package com.jinwoo.android.musicplayer;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.ContactsContract;
import android.provider.MediaStore;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by JINWOO on 2017-02-01.
 */

public class DataLoader {

    // 데이터 컨텐츠 URI 정의
    private final static Uri URI = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
    // 데이터에서 가져올 데이터 컬럼명을 String 배열에 담는다.
    //    데이터 컬럼명은 Content Uri의 패키지에 들어있다.
    private final static String PROJ[] = {
            MediaStore.Audio.Media._ID,
            MediaStore.Audio.Media.ALBUM_ID,
            MediaStore.Audio.Media.TITLE,
            MediaStore.Audio.Media.ARTIST
    };

    // datas를 두개의 activity에서 공유하기 위해 static 형태로 변경
    private static List<Music> datas = new ArrayList<>();

    // static 변수인 datas를 체크해서 null이면 load를 실행
    public static List<Music> get(Context context){
        if(datas == null || datas.size() == 0){
            load(context);
        }
        return datas;
    }

    // load 함수는 get 함수를 통해서만 접근한다.
    public static void load(Context context){

        // 1. 데이터에 접근하기 위해 ContentResolver 를 불러온다.
        ContentResolver resolver = context.getContentResolver();

        // 2. Content Resolver로 쿼리한 데이터를 커서에 담는다.
        //    데이터 URI : MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
        Cursor cursor =  resolver.query(URI, PROJ, null, null, null); // 데이터의 주소, 가져올 데이터 컬럼명 배열, 조건절에 들어가는 컴럼명들 지정, 지정된 컬렴명과 매핑되는 실제 조건 값, 정렬

        // 3. 커서에 담긴 데이터를 반복문을 돌면서 꺼낸다.
        if(cursor != null){
            while( cursor.moveToNext()){
                Music music = new Music();

                music.id = getValue(cursor, PROJ[0]);
                music.album_id = getValue(cursor, PROJ[1]);
                music.title = getValue(cursor, PROJ[2]);
                music.artist = getValue(cursor, PROJ[3]);
                // 앨범 자킷 이미지가 있는 디렉토리에서 주소를 가져온다.
                music.album_image = getAlbumImageSimple(music.album_id);
                //music.bitmap_image = getAlbumImgaeBitmap(music.album_id); // OutOFMemory 발생!!
                music.uri = getMusicUri(music.id);

                datas.add(music);
            }
            // 4. 커서를 닫아준다.
            cursor.close();
        }
    }

    private  static String getValue(Cursor cursor, String columnName){
        int idx = cursor.getColumnIndex(columnName);
        return cursor.getString(idx);
    }

    // 음악 id로 uri를 가져오는 함수
    private static Uri getMusicUri(String music_id){
        Uri content_uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        return Uri.withAppendedPath(content_uri,music_id);
    }


    // 앨범 Uri를 강제로 생성
    // 가장 간단하게 앨범이미지를 가져오는 방법
    // 문제점: 실제 앨범데이터만 있어서 이미지를 불러오지 못하는 경우가 있다.
    private static Uri getAlbumImageSimple(String album_id){
        return Uri.parse("content://media/external/audio/albumart/" + album_id);
    }


}
