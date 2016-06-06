package info.androidhive.slidingmenu;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.veysel.buyukproje.R;

import java.util.List;

/**
 * Created by VEYSEL on 28.5.2016.
 */
public class TitlesAdapter extends RecyclerView.Adapter<TitlesAdapter.MyViewHolder> {
    List <TitleClass> titleClasses;
    static OnItemClickListener onItemClickListener;
    public  TitlesAdapter(List<TitleClass> titleClasses)
    {
        this.titleClasses=titleClasses;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemview= LayoutInflater.from(parent.getContext()).inflate(R.layout.titlesrecylerview,parent,false);
        return new MyViewHolder(itemview);
    }

    @Override
    public void onBindViewHolder(TitlesAdapter.MyViewHolder holder, int position) {
TitleClass titles=titleClasses.get(position);
        holder.name.setText(titles.getUserName());
        holder.date.setText(titles.getDay());
        if(titles.getMonth().equals("01"))
            holder.month.setText("Ocak");
        else if(titles.getMonth().equals("02"))
            holder.month.setText("Şubat");
        else if(titles.getMonth().equals("03"))
            holder.month.setText("Mart");
        else if(titles.getMonth().equals("04"))
            holder.month.setText("Nisan");
        else if(titles.getMonth().equals("05"))
            holder.month.setText("Mayıs");
        else if(titles.getMonth().equals("06"))
            holder.month.setText("Haziran");
        else if(titles.getMonth().equals("07"))
            holder.month.setText("Temmuz");
        else if(titles.getMonth().equals("08"))
            holder.month.setText("Ağustos");
        else if(titles.getMonth().equals("09"))
            holder.month.setText("Eylül");
        else if(titles.getMonth().equals("10"))
            holder.month.setText("Ekim");
        else if(titles.getMonth().equals("11"))
            holder.month.setText("Kasım");
    else
            holder.month.setText("Aralık");
        holder.title.setText(titles.getTitleDetail());
        holder.setTitle(titles);
    }

    @Override
    public int getItemCount() {
        return titleClasses.size();
    }
    public void setOnItemClickListener(final OnItemClickListener mItemClickListener) {
        this.onItemClickListener = mItemClickListener;
    }
    public static class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
TextView name,title,date,month;
      LinearLayout lvname,lvtitle;
TitleClass titleClass;
        public MyViewHolder(final View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            name= (TextView) itemView.findViewById(R.id.rcName);
            title= (TextView) itemView.findViewById(R.id.rcTitle);
            date= (TextView) itemView.findViewById(R.id.rctitleDate);
            month= (TextView) itemView.findViewById(R.id.rctitleMonth);
            lvname= (LinearLayout) itemView.findViewById(R.id.lvName);
            lvtitle= (LinearLayout) itemView.findViewById(R.id.lvTitle);
            lvname.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClickListener.onItemClick(itemView,getPosition(),0);
                }
            });
            lvtitle.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClickListener.onItemClick(itemView,getPosition(),1);
                }
            });
        }

        @Override
        public void onClick(View v) {

        }
        public void setTitle(TitleClass title)
        {
            titleClass=title;
        }

    }
    public interface OnItemClickListener {
        void onItemClick(View view, int position,int yer);
    }
}
