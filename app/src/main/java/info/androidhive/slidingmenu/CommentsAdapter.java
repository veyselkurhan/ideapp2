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
 * Created by VEYSEL on 29.4.2016.
 */
public class CommentsAdapter extends RecyclerView.Adapter<CommentsAdapter.MyViewHolder> {
    List<CommentClass> comments;
    static OnItemClickListener onItemClickListener;
    public  CommentsAdapter(List<CommentClass> cms)
    {
        comments=cms;

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView= LayoutInflater.from(parent.getContext()).inflate(R.layout.comments2vrc, parent, false);


        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        CommentClass cm = comments.get(position);
        holder.commentText.setText(cm.getText());
       holder. like.setText(String.valueOf(cm.getLike()));
       holder. dislike.setText(String.valueOf(cm.getDislike()));
        holder.setComment(cm);
    }

    @Override
    public int getItemCount() {
        return comments.size();
    }

    public void setOnItemClickListener(final OnItemClickListener mItemClickListener) {
        this.onItemClickListener = mItemClickListener;
    }


    public static class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView commentText;
        TextView like,dislike;
        CommentClass cm;
        LinearLayout lllike,block;
        LinearLayout lldislike;
        public MyViewHolder(View view)
        {
            super(view);
            view.setOnClickListener(this);
            commentText= (TextView) view.findViewById(R.id.rcComments);
            like= (TextView) view.findViewById(R.id.tvlike);
            dislike= (TextView) view.findViewById(R.id.tvdislike);
            lllike= (LinearLayout) view.findViewById(R.id.lllike);
            block= (LinearLayout) view.findViewById(R.id.block);
            lldislike= (LinearLayout) view.findViewById(R.id.lldislike);
            lllike.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onItemClickListener.onItemClick(itemView, getPosition(),true,false);
                }
            });
            lldislike.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onItemClickListener.onItemClick(itemView, getPosition(),false,false);
                }
            });
block.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        onItemClickListener.onItemClick(itemView, getPosition(),true,true);
    }
});
        }
public void setComment(CommentClass cm)
{

    this.cm=cm;
}
        @Override
        public void onClick(View v) {

        }
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position,boolean b,boolean c);
    }
}
