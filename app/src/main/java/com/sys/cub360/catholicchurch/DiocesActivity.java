package com.sys.cub360.catholicchurch;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.sys.cub360.catholicchurch.All_models.provincemodel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DiocesActivity extends AppCompatActivity  implements AdapterView.OnItemSelectedListener{
private Spinner mspinner;
private EditText mtext;
private Button mbtn;
private ProgressDialog mp;
private DatabaseReference mentrance, mdd;
private provincemodel mprov;
private List<provincemodel>provlist;
private String ccheck, push_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dioces);
        provlist=new ArrayList<>();
        mp=new ProgressDialog(this);
        mspinner=findViewById(R.id.diocespin);
        mtext=findViewById(R.id.dtexts);
        mbtn=findViewById(R.id.dclick);



        mp.setMessage("Loading");
        mp.setCancelable(false);
        mp.setCanceledOnTouchOutside(false);
        mp.show();

        mdd = FirebaseDatabase.getInstance().getReference().child("Diocees");

        mentrance = FirebaseDatabase.getInstance().getReference().child("Province");
        mentrance.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                String province=dataSnapshot.child("Province").getValue().toString();
              mprov=new provincemodel(province);
              provlist.add(mprov);

              mp.dismiss();
                mspinner.setOnItemSelectedListener(DiocesActivity.this);
                ArrayAdapter aa = new ArrayAdapter(getApplicationContext(), R.layout.spinner_list, provlist);
                aa.setDropDownViewResource(R.layout.spinner_list);
                mspinner.setAdapter(aa);

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });





        mbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String dioces=mtext.getText().toString().trim();
                if(!TextUtils.isEmpty(dioces)){
                    mp.setMessage("Loading");
                    mp.setCancelable(false);
                    mp.setCanceledOnTouchOutside(false);
                    mp.show();

                    Map updatedd = new HashMap();
                    updatedd.put("Province",ccheck);
                    updatedd.put("Dioces",dioces);
                    updatedd.put("Totalno", "0");
                    updatedd.put("totaalc", "0");

                    DatabaseReference user_message_push = mdd.push();
                    push_id = user_message_push.getKey();

                    Map bd = new HashMap();
                    bd.put("/" + push_id, updatedd);
                    mdd.updateChildren(bd).addOnCompleteListener(new OnCompleteListener() {
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

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        String m=adapterView.getItemAtPosition(i).toString();
        ccheck=m;
        Toast.makeText(getApplicationContext(),m , Toast.LENGTH_LONG).show();

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
