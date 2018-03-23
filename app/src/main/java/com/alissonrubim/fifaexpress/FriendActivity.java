package com.alissonrubim.fifaexpress;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.*;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.alissonrubim.fifaexpress.Model.DAO.FriendDAO;
import com.alissonrubim.fifaexpress.Model.Database;
import com.alissonrubim.fifaexpress.Model.Friend;
import com.getbase.floatingactionbutton.*;
import com.getbase.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class FriendActivity extends AppCompatActivity {
    private FloatingActionButton faAddFriend;
    private FloatingActionButton faPlay;
    private ListView listViewResume;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Database database = new Database(getApplicationContext());

        bindUI();

        faAddFriend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showFriendDetailActivity();
            }
        });

        fillListViewResume();
    }

    private void showFriendDetailActivity(){
        Intent intent = new Intent(getApplicationContext(), FriendDetailActivity.class);
        startActivityForResult(intent, FriendDetailActivity.IntentId);
    }

    private void bindUI(){
        faAddFriend = findViewById(R.id.fabAddFriend);
        listViewResume = findViewById(R.id.listViewResume);
    }

    private void fillListViewResume(){
        ArrayList<Friend> friends = (new FriendDAO(getApplicationContext())).GetAll();

        ArrayList<String> spinnerArray =  new ArrayList<String>();
        for (Friend t:
                friends) {
            spinnerArray.add(t.getName());
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_list_item_1, spinnerArray);

        listViewResume.setAdapter(adapter);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == FriendDetailActivity.IntentId) {
            if (resultCode == RESULT_OK) {

            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_friend, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}