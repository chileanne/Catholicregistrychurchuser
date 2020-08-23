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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;
import com.sys.cub360.catholicchurch.All_models.denarymodel;
import com.sys.cub360.catholicchurch.All_models.diocesmodel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ParishActivity extends AppCompatActivity {
    private Spinner mspinner1,mspinner2,mspinner3;
    private EditText mtext;
    private Button mbtn;
    private ProgressDialog mp;
    private DatabaseReference mentrance, mcheck,mdd;
    private  String ccheck, push_id,dccheck;
    private String pcheck,dcheck,ddcheck;
    private denarymodel mprov;
    private List<denarymodel> provlist;
    private LinearLayout ml;
    private diocesmodel mprovs;
    private List<diocesmodel> provlists;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference mdatabaseref;


    String[] banknames={"","Abuja","Benin"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parish);

        mp=new ProgressDialog(this);
        mspinner1=findViewById(R.id.pdenaryspin);
        mspinner2=findViewById(R.id.pdenaryspins);
        mspinner3=findViewById(R.id.pdenaryspinsss);
        ml=findViewById(R.id.pdl);
        mtext=findViewById(R.id.pddtexts);
        mbtn=findViewById(R.id.pddclick);


        mspinner2.setVisibility(View.GONE);
        mspinner3.setVisibility(View.GONE);
        ml.setVisibility(View.GONE);
        provlist=new ArrayList<>();
        provlists=new ArrayList<>();

        firebaseAuth =FirebaseAuth.getInstance();

        mdatabaseref= FirebaseDatabase.getInstance().getReference().child("Users");

        mdd= FirebaseDatabase.getInstance().getReference().child("Parish");
        mcheck= FirebaseDatabase.getInstance().getReference().child("Denary");
        mentrance= FirebaseDatabase.getInstance().getReference().child("Diocees");

        ArrayAdapter aa = new ArrayAdapter(getApplicationContext(), R.layout.spinner_list, banknames);
        aa.setDropDownViewResource(R.layout.spinner_list);
        mspinner1.setAdapter(aa);

        mspinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                mspinner2.setVisibility(View.GONE);
                ml.setVisibility(View.GONE);
                Toast.makeText(getApplicationContext(), banknames[i], Toast.LENGTH_LONG).show();
                final String m=adapterView.getItemAtPosition(i).toString();
                pcheck=m;
                if(!m.equals("")){
                    mentrance.addChildEventListener(new ChildEventListener() {
                        @Override
                        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                            String province=dataSnapshot.child("Province").getValue().toString();
                            String dioce=dataSnapshot.child("Dioces").getValue().toString();

                            if(province.equals(m)) {
                                Toast.makeText(getApplicationContext(), dioce, Toast.LENGTH_SHORT).show();
                                mprovs=new diocesmodel (dioce);
                                provlists.add(mprovs);
                                ArrayAdapter aa = new ArrayAdapter(getApplicationContext(), R.layout.spinner_list, provlists);
                                aa.setDropDownViewResource(R.layout.spinner_list);
                                mspinner2.setAdapter(aa);
                                mspinner2.setVisibility(View.VISIBLE);

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
                ml.setVisibility(View.GONE);
                mspinner3.setVisibility(View.GONE);
                final String m=adapterView.getItemAtPosition(i).toString();
                dcheck=m;
                if(!m.equals("")){
                    mcheck.addChildEventListener(new ChildEventListener() {
                        @Override
                        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                            String ddenary=dataSnapshot.child("denary").getValue().toString();
                            String dioce=dataSnapshot.child("Dioces").getValue().toString();

                            if(dioce.equals(m)) {
                                Toast.makeText(getApplicationContext(), dioce, Toast.LENGTH_SHORT).show();
                                mprov=new denarymodel(ddenary);
                                provlist.add(mprov);
                                ArrayAdapter aa = new ArrayAdapter(getApplicationContext(), R.layout.spinner_list, provlist);
                                aa.setDropDownViewResource(R.layout.spinner_list);
                                mspinner3.setAdapter(aa);
                                mspinner3.setVisibility(View.VISIBLE);

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


        mspinner3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String m=adapterView.getItemAtPosition(i).toString();
                ddcheck=m;
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
                final String parish=mtext.getText().toString().trim();
                if(!TextUtils.isEmpty(parish)){
                    mp.setMessage("Loading");
                    mp.setCancelable(false);
                    mp.setCanceledOnTouchOutside(false);
                    mp.show();

                    Map updatedd = new HashMap();
                    updatedd.put("Province",pcheck);
                    updatedd.put("Dioces",dcheck);
                    updatedd.put("denary", ddcheck);
                    updatedd.put("parish", parish);
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
                                FirebaseUser muser=FirebaseAuth.getInstance().getCurrentUser();
                                final String uid=muser.getUid();
                                HashMap usermaps=new HashMap<>();
                                usermaps.put("Province",pcheck);
                                usermaps.put("dioces",dcheck);
                                usermaps.put("denary",ddcheck);
                                usermaps.put("parish",parish);
                                mdatabaseref.child(uid).updateChildren(usermaps).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if(task.isSuccessful()){
                                            mp.dismiss();
                                            startActivity(new Intent(getApplicationContext(), ContinueActivity.class));
                                            finish();
                                        }else{
                                            String m=task.getException().getMessage().toString();
                                            mp.dismiss();
                                            Toast.makeText(ParishActivity.this,m,Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });






                            }
                        }
                    });
                }

            }
        });


    }
}
