package com.sys.cub360.catholicchurch.Readadaptes;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sys.cub360.catholicchurch.R;
import com.sys.cub360.catholicchurch.Read.Readdenary;
import com.sys.cub360.catholicchurch.Readmodels.denarry;

import java.util.List;

public class denaryadapter   extends RecyclerView.Adapter< denaryadapter.Myholder>{
    private Context mcontext;
    private List<denarry> mlist;
    private String m,dio;
    public denaryadapter(Context applicationContext, List<denarry> mlist, String m, String dios) {
        this.mcontext=applicationContext;
        this.mlist=mlist;
        this.m=m;
        this.dio=dios;
    }

    @NonNull
    @Override
    public denaryadapter.Myholder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.singlell,viewGroup,false);
        return new denaryadapter.Myholder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull denaryadapter.Myholder myholder, int i) {
        denarry g=mlist.get(i);
        String denary=g.getDenary().toString().trim();
        final String dioces=g.getDioces().toString().trim();
        String date=g.getDate().toString().trim();
        String totalno=g.getTotalno().toString().trim();
        String totalc=g.getTotaalc().toString().trim();

        if(dioces.equals(dio)){
            if(date.equals(m)){
                myholder.name.setText(denary);
                myholder.no.setText(totalno);
                myholder.noc.setText(totalc);

            }
        }

        myholder.mc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               /* Intent intent = new Intent(mcontext, Readdenary.class);
                intent.putExtra("dioces",dioces);
                intent.putExtra("date",m);
                // intent.putExtra("body",body);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mcontext.startActivity(intent);*/


            }
        });

    }

    @Override
    public int getItemCount() {
        return mlist.size();
    }

    public class Myholder extends RecyclerView.ViewHolder {
        private CardView mc;
        private TextView name,no,noc;
        public Myholder(@NonNull View v) {
            super(v);
            mc=v.findViewById(R.id.ppc);
            name=v.findViewById(R.id.ppname);
            no=v.findViewById(R.id.ppno);
            noc=v.findViewById(R.id.ppnoc);
        }
    }
}
