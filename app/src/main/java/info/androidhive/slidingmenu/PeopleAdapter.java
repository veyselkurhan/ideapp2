package info.androidhive.slidingmenu;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.veysel.buyukproje.R;

import java.util.List;

/**
 * Created by VEYSEL on 28.5.2016.
 */
public class PeopleAdapter extends RecyclerView.Adapter<PeopleAdapter.MyViewHolder> {
List <PeopleClass> peopleClasses;
    static OnItemClickListener onItemClickListener;
    public  PeopleAdapter(List <PeopleClass> peopleClasses)
    {
        this.peopleClasses=peopleClasses;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.showpeople,parent,false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        PeopleClass pc=peopleClasses.get(position);
        holder.name.setText(pc.getName());
        holder.phone.setText(pc.getPhone());
        if(pc.isEngelleme())holder.engelle.setText("Engellendi");
        else holder.engelle.setText("Engelle");
        if(!pc.isTakip())
        {
            holder.tvtakip.setText("takip edilmiyor");
            holder.img.setImageResource(R.drawable.cross);
        }
        holder.setPeople(pc);
    }

    @Override
    public int getItemCount() {
        return peopleClasses.size();
    }
    public void setOnItemClickListener(final OnItemClickListener mItemClickListener) {
        this.onItemClickListener = mItemClickListener;
    }
    public static class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
TextView name,phone,engelle,tvtakip;
        ImageView img;
        LinearLayout llengelleme,lltakip;
PeopleClass peopleClass;
        public MyViewHolder(final View itemView) {
            super(itemView);
            name= (TextView) itemView.findViewById(R.id.tvfriendname);
            phone= (TextView) itemView.findViewById(R.id.tvfriendphone);
           engelle= (TextView) itemView.findViewById(R.id.tvfriendengelle);
            img= (ImageView) itemView.findViewById(R.id.imgtakip);
            lltakip= (LinearLayout) itemView.findViewById(R.id.lltakip);
            tvtakip= (TextView) itemView.findViewById(R.id.tvtakip);
llengelleme= (LinearLayout) itemView.findViewById(R.id.llfriendengelle);
            llengelleme.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClickListener.onItemClick(itemView,getPosition(),true);
                }
            });
            lltakip.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClickListener.onItemClick(itemView,getPosition(),false);
                }
            });
        }
        public void setPeople(PeopleClass pc)
        {
            peopleClass=pc;
        }

        @Override
        public void onClick(View v) {

        }

    }
    public interface OnItemClickListener {
        void onItemClick(View view, int position,boolean b);
    }
}
