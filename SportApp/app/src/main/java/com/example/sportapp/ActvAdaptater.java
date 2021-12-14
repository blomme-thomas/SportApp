package com.example.sportapp;

import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class ActvAdaptater extends RecyclerView.Adapter<ViewHolder>{


    private final List<Actv> actvList;

    public ActvAdaptater(List<Actv> ActvList) {
        actvList = ActvList;
    }

    @Override
    public void onBindViewHolder(com.example.sportapp.ViewHolder holder, int position) {
        holder.onBind(position);
    }

    @NonNull
    @Override
    public com.example.sportapp.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.actv_content, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public int getItemCount() {
        if (actvList != null & actvList.size() > 0) {
            return actvList.size();
        } else {
            return 0;
        }
    }

    public void addItems(List<Actv> actvList) {
        actvList.addAll(actvList);
        notifyDataSetChanged();
    }

    public void deleteItem(int position) {
        if (actvList != null & actvList.size() > 0) {
            actvList.remove(position);
        }
        notifyItemRemoved(position);
        notifyDataSetChanged();
    }

    public class ViewHolder extends com.example.sportapp.ViewHolder {


        @BindView(R.id.nameTextView)
        TextView mNameTextView;

        @BindView(R.id.animeTextView)
        TextView mAnimeTextView;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        protected void clear() {
            mNameTextView.setText("");
            mAnimeTextView.setText("");
        }

        public void onBind(int position) {
            super.onBind(position);

            Actv actv= actvList.get(position);



            if (actv.getSport() != null) {
                mNameTextView.setText(actv.getSport());
            }

            if (actv.getLieu() != null) {
                mAnimeTextView.setText(actv.getLieu());
            }

            itemView.setOnClickListener(v -> {
                Intent intent=new Intent(itemView.getContext(), DetailActv.class);
                intent.putExtra("key",  actv.getKey());
                itemView.getContext().startActivity(intent);
            });

            itemView.setOnLongClickListener(v -> {
                Intent intent=new Intent(itemView.getContext(), EditActv.class);
                intent.putExtra("key",  actv.getKey());
                itemView.getContext().startActivity(intent);
                return false;
            });

        }
    }

}