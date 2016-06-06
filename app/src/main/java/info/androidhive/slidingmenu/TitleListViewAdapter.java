package info.androidhive.slidingmenu;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.veysel.buyukproje.R;

import java.util.ArrayList;

/**
 * Created by VEYSEL on 4.5.2016.
 */
public class TitleListViewAdapter extends ArrayAdapter<TitleClass> {
    int resourceID;

    public  TitleListViewAdapter(Context context, int resourceID,ArrayList<TitleClass>titles)
    {
        super(context,resourceID,titles);
        this.resourceID = resourceID;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final TitleClass titles=getItem(position);
        convertView= LayoutInflater.from(getContext()).inflate(resourceID,parent,false);
        TextView name= (TextView) convertView.findViewById(R.id.mytitleName);
        TextView titleText= (TextView) convertView.findViewById(R.id.mytitleText);
        TextView mytitleDate= (TextView) convertView.findViewById(R.id.mytitleDate);
        TextView mytitleMonth= (TextView) convertView.findViewById(R.id.mytitleMonth);

        name.setText(titles.getUserName());
        titleText.setText(titles.getTitleDetail());
        mytitleDate.setText(titles.getDay());

        if(titles.getMonth().equals("01"))
            mytitleMonth.setText("Ocak");
        else if(titles.getMonth().equals("02"))
            mytitleMonth.setText("Şubat");
        else if(titles.getMonth().equals("03"))
            mytitleMonth.setText("Mart");
        else if(titles.getMonth().equals("04"))
            mytitleMonth.setText("Nisan");
        else if(titles.getMonth().equals("05"))
            mytitleMonth.setText("Mayıs");
        else if(titles.getMonth().equals("06"))
            mytitleMonth.setText("Haziran");
        else if(titles.getMonth().equals("07"))
            mytitleMonth.setText("Temmuz");
        else if(titles.getMonth().equals("08"))
            mytitleMonth.setText("Ağustos");
        else if(titles.getMonth().equals("09"))
            mytitleMonth.setText("Eylül");
        else if(titles.getMonth().equals("10"))
            mytitleMonth.setText("Ekim");
        else if(titles.getMonth().equals("11"))
            mytitleMonth.setText("Kasım");
        else
            mytitleMonth.setText("Aralık");

        return convertView;
    }
}
