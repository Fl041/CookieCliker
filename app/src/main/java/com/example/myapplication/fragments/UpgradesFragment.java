package com.example.myapplication.fragments;

import static com.example.myapplication.AccountDBHelper.BASE_NOM;
import static com.example.myapplication.AccountDBHelper.BASE_VERSION;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;

import androidx.appcompat.widget.SwitchCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentContainerView;
import androidx.fragment.app.FragmentManager;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.AccountDBHelper;
import com.example.myapplication.R;
import com.example.myapplication.account_show;
import com.example.myapplication.jeu;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link UpgradesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UpgradesFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public UpgradesFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment UpgradesFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static UpgradesFragment newInstance(String param1, String param2) {
        UpgradesFragment fragment = new UpgradesFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_upgrades, container, false);

        // Fermer le fragment en cliquant sur la croix (closeCross) et afficher l'activité jeu
        ImageView closeCross = (ImageView) view.findViewById(R.id.closeCross);

        closeCross.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
                Intent intent = new Intent(getActivity(), jeu.class);
                startActivity(intent);
            }
        });
        AccountDBHelper dbHelper = new AccountDBHelper(getActivity(), BASE_NOM, null, BASE_VERSION);
        if (dbHelper.isconnected()) {
            Cursor cursor = dbHelper.showconnectedaccount();
            cursor.moveToFirst();
            SwitchCompat switchbutton = (SwitchCompat) view.findViewById(R.id.customSwitch);
            TextView alert = view.findViewById(R.id.alert);
            if (cursor.getString(7).equals("1")) switchbutton.setChecked(true);
            if(Integer.parseInt(cursor.getString(6) )< 30)switchbutton.setClickable(false) ;
            else{
            alert.setText("");
            switchbutton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
                        dbHelper.updateupgrade1true();
                    } else {
                        dbHelper.updateupgrade1false();
                    }
                }
            });}
        }
        return view; }
}
