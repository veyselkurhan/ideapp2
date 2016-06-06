package info.androidhive.slidingmenu;



import android.app.DatePickerDialog;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;
import android.app.AlertDialog;


import com.example.veysel.buyukproje.MyFireBaseClass2;
import com.example.veysel.buyukproje.R;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.Calendar;



/**
 * Created by demirhan on 15.05.2016.
 */
public class Profil extends Fragment {
    Firebase firebase;

    EditText kullaniciadi, eposta, cinsiyet, dogumtarihi, ceptel, post;
    String name, email, gender, birth, phone, postSayisi;
    String cins = "0";
    Calendar myCalendar;
    Button btnUpdate;
    private String pYear;
    private String pMonth;
    private String pDay;

    public Profil(){

    }

   /* @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnItemClickListener) {
            onItemClickListener = (OnItemClickListener) context;
        } else {

        }
    }*/


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView= inflater.inflate(R.layout.fragment_profil, container, false);
        Firebase.setAndroidContext(getActivity());
        SharedPreferences sharedPreferences=getActivity().getSharedPreferences("MyData", Context.MODE_PRIVATE);
        final String myPhone=sharedPreferences.getString("MyPhone", "");


        final boolean profile=sharedPreferences.getBoolean("profile",false);
        if(profile) {

            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean("profile",false);
            editor.apply();
            phone=sharedPreferences.getString("profilPhone","");
            firebase=new Firebase(MyFireBaseClass.url+"/users/"+phone);
        }

        else
        {
            phone=sharedPreferences.getString("MyPhone", "");
            firebase=new Firebase(MyFireBaseClass.url+"/users/"+phone);
        }
            firebase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                name = (snapshot.child("Name").getValue()).toString();  //prints "Do you have data? You'll love Firebase."
                kullaniciadi.setText(name);
                ceptel.setText(phone);
                birth = (snapshot.child("BirthDate").getValue()).toString();  //prints "Do you have data? You'll love Firebase."
                dogumtarihi.setText(birth);
                gender = (snapshot.child("Gender").getValue()).toString();  //prints "Do you have data? You'll love Firebase."
                cinsiyet.setText(gender);
                email =  (snapshot.child("MailAddress").getValue()).toString();  //prints "Do you have data? You'll love Firebase."
                eposta.setText(email);
                postSayisi = (snapshot.child("stats").child("Posts").getValue()).toString();
                post.setText("Yazdığı post sayısı: " + postSayisi);

                //email = (snapshot.child("Mail").getValue()).toString();  //prints "Do you have data? You'll love Firebase."
               // eposta.setText(email);

            }
            @Override public void onCancelled(FirebaseError error) { }
        });



        kullaniciadi = (EditText) rootView.findViewById(R.id.kullaniciadi);
        eposta = (EditText) rootView.findViewById(R.id.eposta);
        cinsiyet = (EditText) rootView.findViewById(R.id.cinsiyet);
        dogumtarihi = (EditText) rootView.findViewById(R.id.dogumtarihi);
        ceptel = (EditText) rootView.findViewById(R.id.ceptel);
        post = (EditText) rootView.findViewById(R.id.postSayisi);

        myCalendar = Calendar.getInstance();
        btnUpdate = (Button) rootView.findViewById(R.id.btnUpdate);

        if(phone.equals(myPhone)){
            btnUpdate.setVisibility(View.VISIBLE);
        }else{
            btnUpdate.setVisibility(View.GONE);
            eposta.setEnabled(false);
            cinsiyet.setEnabled(false);
            dogumtarihi.setEnabled(false);
        }

        cinsiyet.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Cinsiyetler();
            }
        });

        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub

                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                pYear = year+"";
                pMonth = monthOfYear++ +"";
                pDay = dayOfMonth+"";
                //pMonth++;

                String month;
                String day;
                if (monthOfYear <= 9) {
                    pMonth = "0" + monthOfYear;
                    // month = pMonth+"";
                } else {
                    pMonth = monthOfYear + "";
                }
                if (dayOfMonth <= 9) {
                    pDay = "0" + pDay;
                } else {
                    pDay = pDay + "";
                }
                updateLabel();
            }
        };

        dogumtarihi.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                new DatePickerDialog(getActivity(), date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        btnUpdate.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Firebase fir = new Firebase(MyFireBaseClass.url + "/users/" + phone);
                fir.child("Name").setValue(kullaniciadi.getText().toString());
                fir.child("MailAddress").setValue(eposta.getText().toString());
                fir.child("Gender").setValue(cinsiyet.getText().toString());
                fir.child("BirthDate").setValue(dogumtarihi.getText().toString());
                Toast.makeText(getActivity(), "Bilgileriniz güncellendi.", Toast.LENGTH_LONG).show();

            }
            }
        );



        return rootView;

    }

    private void Cinsiyetler() {
        final CharSequence[] items = { "Erkek", "Kadın" };

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (items[item].equals("Erkek")) {
                    cinsiyet.setText("Erkek");
                    cins = "1";
                    dialog.dismiss();

                } else if (items[item].equals("Kadın")) {

                    cinsiyet.setText("Kadın");
                    cins = "2";
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    private void updateLabel() {
        dogumtarihi.setText(new StringBuilder()
                // Month is 0 based so add 1
                .append(pYear).append("-").append(pMonth).append("-")
                .append(pDay).append(""));
    }

}
