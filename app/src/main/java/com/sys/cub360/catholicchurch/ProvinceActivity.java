package com.sys.cub360.catholicchurch;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.Map;

public class ProvinceActivity extends AppCompatActivity {
    private EditText mtext;
    private Button mbtn;
    private ProgressDialog mp;
    private DatabaseReference mentrance;
    private Spinner mspinner;
    private String ccheck,push_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_province);

        mp=new ProgressDialog(this);
        mtext=findViewById(R.id.texts);
        mbtn=findViewById(R.id.click);


        mbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String ttext=mtext.getText().toString().trim();
                if(!TextUtils.isEmpty(ttext)){
                    mp.setMessage("Loading");
                    mp.setCancelable(false);
                    mp.setCanceledOnTouchOutside(false);
                    mp.show();
                    mentrance = FirebaseDatabase.getInstance().getReference().child("Province");

                    Map updatedd = new HashMap();
                    updatedd.put("Province",ttext);
                    updatedd.put("Totalno", "0");
                    updatedd.put("totaalc", "0");

                    DatabaseReference user_message_push = mentrance.push();
                    push_id = user_message_push.getKey();

                    Map bd = new HashMap();
                    bd.put("/" + push_id, updatedd);
                    mentrance.updateChildren(bd).addOnCompleteListener(new OnCompleteListener() {
                        @Override
                        public void onComplete(@NonNull Task task) {
                            if (task.isSuccessful()) {
                                mp.dismiss();
                                startActivity(new Intent(getApplicationContext(), CreateActivity.class));
                                finish();

                            }
                        }
                    });
                }
            }
        });


    }
}
