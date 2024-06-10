package com.example.contactmanager.fragments;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;

import com.example.contactmanager.R;
import com.example.contactmanager.addDv;
import com.example.contactmanager.addStaff;
import com.example.contactmanager.database.DatabaseHelper;
import com.example.contactmanager.detailDvActivity;
import com.example.contactmanager.model.DonVi;
import com.example.contactmanager.model.NhanVien;
import com.example.contactmanager.searchDv;
import com.example.contactmanager.searchStaff;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link staffManager#newInstance} factory method to
 * create an instance of this fragment.
 */
public class staffManager extends Fragment {

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
    private ArrayList<String> staffStrings;
    private ArrayList<NhanVien> staffList;
    ImageButton btnAdd, btnSearch;

    public staffManager() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment staffManager.
     */
    // TODO: Rename and change types and number of parameters
    public static staffManager newInstance(String param1, String param2) {
        staffManager fragment = new staffManager();
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
        return inflater.inflate(R.layout.fragment_staff_manager, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        btnAdd = (ImageButton) getView().findViewById(R.id.ibtnAdd);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), addStaff.class);
                startActivity(intent);
                getActivity().finish();
            }
        });
        btnSearch = (ImageButton) getView().findViewById(R.id.ibtnSearch);
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), searchStaff.class);
                startActivity(intent);
                getActivity().finish();
            }
        });
        lv = (ListView) getView().findViewById(R.id.lv);
        lv.setAdapter(adapter);
        dbHelper = new DatabaseHelper(getActivity());
        staffList = new ArrayList<>();
        staffStrings = new ArrayList<>();
        loadDv();

        adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, staffStrings);
        lv.setAdapter(adapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                NhanVien selectedNhanVien = staffList.get(position);
                Intent intent = new Intent(getActivity(), detailDvActivity.class);
                intent.putExtra("staffId", selectedNhanVien.getId()); // Pass ID instead of name
                startActivity(intent);
                getActivity().finish();

            }
        });
    }

    private void loadDv() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(DatabaseHelper.TABLE_DON_VI, null, null, null, null, null, null);

        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_ID));
                String hoten = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_HO_TEN));
                String email = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_EMAIL_DON_VI));
                String chucVu = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_CHUC_VU));
                byte[] logoBytes = cursor.getBlob(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_ANH_DAI_DIEN));
                String dienThoai = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_SO_DIEN_THOAI_DON_VI));
//                    Bitmap logo = (logoBytes != null) ? BitmapFactory.decodeByteArray(logoBytes, 0, logoBytes.length) : null;

                NhanVien nhanVien = new NhanVien(id, hoten, chucVu, dienThoai, email, logoBytes);
                staffList.add(nhanVien); // Add entire DonVi object
                staffStrings.add(hoten); // Add name only
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
    }

}