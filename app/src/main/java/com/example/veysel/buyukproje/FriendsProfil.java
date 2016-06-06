package com.example.veysel.buyukproje;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

/**
 * Created by demirhan on 28.05.2016.
 */
public class FriendsProfil extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friendsprofile);
    }

    public void GeriGit(View v) {

        switch (v.getId()) {
            case R.id.gerigit:

                Intent intent = new Intent(FriendsProfil.this, MainActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.push_up_in, R.anim.push_up_out);
                finish();
                break;
        }
    }
}
