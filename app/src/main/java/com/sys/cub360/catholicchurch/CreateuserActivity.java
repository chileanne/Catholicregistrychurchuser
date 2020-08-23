package com.sys.cub360.catholicchurch;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;

public class CreateuserActivity extends AppCompatActivity {
    private Button mlog,mreport;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_createuser);

        mlog=findViewById(R.id.llogiut);
        mreport=findViewById(R.id.rreport);

        mlog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(getApplicationContext(), HomeActivity.class));
                finish();
            }
        });

        mreport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent main_intent = new Intent(CreateuserActivity.this, parishblogActivity.class);
                startActivity(main_intent);
                finish();
            }
        });
    }
}
