    package com.example.contactmanager.fragments;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;

import com.example.contactmanager.R;
import com.example.contactmanager.addDv;
import com.example.contactmanager.database.DatabaseHelper;
import com.example.contactmanager.detailDvActivity;
import com.example.contactmanager.model.DonVi;
import com.example.contactmanager.searchDv;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

    /**
 * A simple {@link Fragment} subclass.
 * Use the {@link dvManager#newInstance} factory method to
 * create an instance of this fragment.
 */
public class dvManager extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    ListView lv;
    private DatabaseHelper dbHelper;
    private ArrayAdapter<String> adapter;
    private ArrayList<String> dvStrings;
    private ArrayList<DonVi> dvList;
    ImageButton btnAdd, btnSearch;
    public dvManager() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment dvManager.
     */
    // TODO: Rename and change types and number of parameters
    public static dvManager newInstance(String param1, String param2) {
        dvManager fragment = new dvManager();
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
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_dv_manager, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        btnAdd = (ImageButton) getView().findViewById(R.id.ibtnAdd);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), addDv.class);
                startActivity(intent);
                getActivity().finish();
            }
        });
        btnSearch = (ImageButton) getView().findViewById(R.id.ibtnSearch);
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), searchDv.class);
                startActivity(intent);
                getActivity().finish();
            }
        });
        lv = (ListView) getView().findViewById(R.id.lv);
        lv.setAdapter(adapter);
        dbHelper = new DatabaseHelper(getActivity());
        dvList = new ArrayList<>();
        dvStrings = new ArrayList<>();
        loadDv();

        adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, dvStrings);
        lv.setAdapter(adapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                DonVi selectedDonVi = dvList.get(position);
                Intent intent = new Intent(getActivity(), detailDvActivity.class);
                intent.putExtra("donViId", selectedDonVi.getId()); // Pass ID instead of name
                startActivity(intent);
                getActivity().finish();

            }
        });
    }
//        private void loadDv() {
//            SQLiteDatabase db = dbHelper.getReadableDatabase();
//            Cursor cursor = db.query(DatabaseHelper.TABLE_DON_VI, null, null, null, null, null, null);
//
//            if (cursor.moveToFirst()) {
//                do {
//                    String name = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_NAME_DON_VI));
//                    dvList.add(name);
//                } while (cursor.moveToNext());
//            }
//
//            cursor.close();
//            db.close();
//        }
        private void loadDv() {
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            Cursor cursor = db.query(DatabaseHelper.TABLE_DON_VI, null, null, null, null, null, null);

            if (cursor.moveToFirst()) {
                do {
                    int id = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_ID));
                    String name = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_NAME_DON_VI));
                    String email = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_EMAIL_DON_VI));
                    String website = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_WEBSITE_DON_VI));
                    byte[] logoBytes = cursor.getBlob(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_LOGO_DON_VI));
                    String diaChi = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_DIA_CHI_DON_VI));
                    String dienThoai = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_SO_DIEN_THOAI_DON_VI));
//                    Bitmap logo = (logoBytes != null) ? BitmapFactory.decodeByteArray(logoBytes, 0, logoBytes.length) : null;

                    DonVi donVi = new DonVi(id, name, email, website, logoBytes, diaChi, dienThoai);
                    dvList.add(donVi); // Add entire DonVi object
                    dvStrings.add(name); // Add name only
                } while (cursor.moveToNext());
            }

            cursor.close();
            db.close();
        }

}