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
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.sys.cub360.catholicchurch.All_models.denarymodel;
import com.sys.cub360.catholicchurch.All_models.diocesmodel;
import com.sys.cub360.catholicchurch.All_models.provincemodel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DeanaryActivity extends AppCompatActivity  {
    private Spinner mspinner1,mspinner2;
    private EditText mtext;
    private Button mbtn;
    private ProgressDialog mp;
    private DatabaseReference mentrance, mcheck,mdd;
    private  String ccheck, push_id,dccheck;
    private diocesmodel mprov;
    private List<diocesmodel> provlist;
    private LinearLayout ml;
    private String  spincheck="";

    String[] banknames={"","Abuja","Benin"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deanary);
        mp=new ProgressDialog(this);
        mspinner1=findViewById(R.id.denaryspin);
        mspinner2=findViewById(R.id.denaryspins);
        ml=findViewById(R.id.dl);
        mtext=findViewById(R.id.ddtexts);
        mbtn=findViewById(R.id.ddclick);


        mspinner2.setVisibility(View.GONE);
        ml.setVisibility(View.GONE);
        provlist=new ArrayList<>();

        mdd= FirebaseDatabase.getInstance().getReference().child("Denary");
        mcheck= FirebaseDatabase.getInstance().getReference().child("Diocees");
        mentrance= FirebaseDatabase.getInstance().getReference().child("Diocees");

        ArrayAdapter aa = new ArrayAdapter(getApplicationContext(), R.layout.spinner_list, banknames);
        aa.setDropDownViewResource(R.layout.spinner_list);
        mspinner1.setAdapter(aa);

        mspinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                mspinner2.setVisibility(View.GONE);
                ml.setVisibility(View.GONE);
                spincheck="";
                Toast.makeText(getApplicationContext(), banknames[i], Toast.LENGTH_LONG).show();
                final String m=adapterView.getItemAtPosition(i).toString();
                ccheck=m;
                if(!m.equals("")){
                    mentrance.addChildEventListener(new ChildEventListener() {
                        @Override
                        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                            String province=dataSnapshot.child("Province").getValue().toString();
                            String dioce=dataSnapshot.child("Dioces").getValue().toString();

                            if(province.equals(m)) {
                                Toast.makeText(getApplicationContext(), dioce, Toast.LENGTH_SHORT).show();
                                mprov=new diocesmodel(dioce);
                                provlist.add(mprov);
                                ArrayAdapter aa = new ArrayAdapter(getApplicationContext(), R.layout.spinner_list, provlist);
                                aa.setDropDownViewResource(R.layout.spinner_list);
                                mspinner2.setAdapter(aa);
                                mspinner2.setVisibility(View.VISIBLE);
                                spincheck="clear";

                            }

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
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });






            mspinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    String m=adapterView.getItemAtPosition(i).toString();
                    dccheck=m;
                    Toast.makeText(getApplicationContext(),m , Toast.LENGTH_LONG).show();
                    ml.setVisibility(View.VISIBLE);
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });


            mbtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String denary=mtext.getText().toString().trim();
                    if(!TextUtils.isEmpty(denary)){
                        mp.setMessage("Loading");
                        mp.setCancelable(false);
                        mp.setCanceledOnTouchOutside(false);
                        mp.show();

                        Map updatedd = new HashMap();
                        updatedd.put("Province",ccheck);
                        updatedd.put("Dioces",dccheck);
                        updatedd.put("denary", denary);
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


}
