package com.sys.cub360.catholicchurch.Read;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;

import com.sys.cub360.catholicchurch.R;
import com.sys.cub360.catholicchurch.newparishblogActivity;

public class PickActivity extends AppCompatActivity {
    private CardView mbenin;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pick);
        mbenin=findViewById(R.id.benin);


        mbenin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), Calendaractivity.class);
                i.putExtra("province", "Benin");
               // i.putExtra("diocesi", dioces);
                finish();
                startActivity(i);
            }
        });
    }
}
