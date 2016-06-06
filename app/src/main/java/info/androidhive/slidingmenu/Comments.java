package info.androidhive.slidingmenu;

import android.app.Fragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.veysel.buyukproje.R;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.ArrayList;


public class Comments extends Fragment {
    ArrayList<String> phoneNumbers=new ArrayList<String>();
    Button btn, btnYorum;
    ListView lv;
    EditText etYorum;


    private OnFragmentInteractionListener mListener;

    public Comments() {
        // Required empty public constructor
    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView=inflater.inflate(R.layout.fragment_comments, container, false);

        lv= (ListView) rootView.findViewById(R.id.lvComments);

        ViewGroup.LayoutParams listViewParams = (ViewGroup.LayoutParams)lv.getLayoutParams();
        listViewParams.height = 1300;
        lv.requestLayout();



        btnYorum = (Button) rootView.findViewById(R.id.btnYorumEkle);
        etYorum = (EditText) rootView.findViewById(R.id.etComment);



        SharedPreferences sharedPreferences=getActivity().getSharedPreferences("MyData", Context.MODE_PRIVATE);
        String titleID=sharedPreferences.getString("titleID", "");
        String phone=sharedPreferences.getString("myPhone","");
        Firebase firebase=new Firebase(MyFireBaseClass.url+"/titles/"+titleID+"/comments/");

        btnYorum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sharedPreferences=getActivity().getSharedPreferences("MyData",Context.MODE_PRIVATE);
                String titleID=sharedPreferences.getString("titleID", "");
                String phone=sharedPreferences.getString("MyPhone","");

                Firebase fir=new Firebase(MyFireBaseClass.url+"titles/"+titleID+"/comments/"+phone);
                fir.child(etYorum.getText().toString()).setValue(false);
                Toast.makeText(getActivity(), "eklendi", Toast.LENGTH_LONG).show();
                etYorum.setText("");
            }
        });


        firebase.addValueEventListener(new ValueEventListener() {


            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String s = "";
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    s += ds.getKey();
                    phoneNumbers.add(ds.getKey());
                }

                ArrayList<String> comments = new ArrayList<String>();
                for (int a = 0; a < phoneNumbers.size(); a++) {
                    comments.clear();
                    for (DataSnapshot ds : dataSnapshot.child(phoneNumbers.get(a)).getChildren()) {
                        comments.add(ds.getKey());
                    }
                }
                //Toast.makeText(getActivity(),s,Toast.LENGTH_LONG).show();
                ArrayAdapter ar = new ArrayAdapter(getActivity(), android.R.layout.simple_list_item_1,comments);
                lv.setAdapter(ar);
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
        return rootView;
    }



    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {

        }
    }




    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

}
