package com.alissonrubim.fifaexpress;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class RoundResultActivity extends AppCompatActivity {

    private Button buttonGoBack;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_round_result);

        bind();

        buttonGoBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goBack();
            }
        });
    }

    private void goBack(){
        Intent intent = new Intent(getApplicationContext(), FriendActivity.class);
        startActivity(intent);
        finish();
    }

    private void bind(){
        buttonGoBack = findViewById(R.id.buttonGoBack);
    }
}
