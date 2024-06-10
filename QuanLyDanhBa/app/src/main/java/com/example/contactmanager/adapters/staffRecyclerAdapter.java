package com.example.contactmanager.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.contactmanager.MainActivity;
import com.example.contactmanager.R;
import com.example.contactmanager.model.DonVi;
import com.example.contactmanager.model.NhanVien;

import java.util.List;

public class staffRecyclerAdapter extends RecyclerView.Adapter<staffViewHolder> {

    private Context context;
    private List<NhanVien> staffList;

    public void setSearchList(List<NhanVien> staffList) {
        this.staffList = staffList;
        notifyDataSetChanged();
    }

    public staffRecyclerAdapter(Context context, List<NhanVien> staffList) {
        this.context = context;
        this.staffList = staffList;
    }

    @NonNull
    @Override
    public staffViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_item, parent, false);
        return new staffViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull staffViewHolder holder, int position) {
        holder.recName.setText(staffList.get(position).getHoTen());
        holder.recImg.setImageBitmap(staffList.get(position).getAnhDaiDien());
        holder.recCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, MainActivity.class);
                intent.putExtra("dvImg", staffList.get(holder.getAdapterPosition()).getAnhDaiDien());
                intent.putExtra("dvName", staffList.get(holder.getAdapterPosition()).getHoTen());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return staffList.size();
    }
}

class staffViewHolder extends RecyclerView.ViewHolder {
    ImageView recImg;
    TextView recName;
    CardView recCard;

    public staffViewHolder(@NonNull View itemView) {
        super(itemView);

        recImg = itemView.findViewById(R.id.recImage);
        recName = itemView.findViewById(R.id.recName);
        recCard = itemView.findViewById(R.id.recCard);
    }
}
