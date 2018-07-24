package com.steven.oschina.ui.tweet;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.steven.oschina.R;

public class LargeImageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_large_image);
    }

    public static void show(Context context, String image) {
        Intent intent = new Intent(context, LargeImageActivity.class);
        intent.putExtra("image", image);
        context.startActivity(intent);
    }
}
