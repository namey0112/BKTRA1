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

import java.util.List;

public class dvRecyclerAdapter extends RecyclerView.Adapter<ViewHolder> {

    private Context context;
    private List<DonVi> dvList;

    public void setSearchList(List<DonVi> dvList) {
        this.dvList = dvList;
        notifyDataSetChanged();
    }

    public dvRecyclerAdapter(Context context, List<DonVi> dvList) {
        this.context = context;
        this.dvList = dvList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.recName.setText(dvList.get(position).getTenDv());
        holder.recImg.setImageBitmap(dvList.get(position).getLogo());
        holder.recCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, MainActivity.class);
                intent.putExtra("dvImg", dvList.get(holder.getAdapterPosition()).getLogo());
                intent.putExtra("dvName", dvList.get(holder.getAdapterPosition()).getTenDv());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return dvList.size();
    }
}

class ViewHolder extends RecyclerView.ViewHolder {
    ImageView recImg;
    TextView recName;
    CardView recCard;

    public ViewHolder(@NonNull View itemView) {
        super(itemView);

        recImg = itemView.findViewById(R.id.recImage);
        recName = itemView.findViewById(R.id.recName);
        recCard = itemView.findViewById(R.id.recCard);
    }
}
