package com.example.sportapp;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.drawable.ColorDrawable;
import android.media.audiofx.DynamicsProcessing;
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


public class EquipeAdaptater extends RecyclerView.Adapter<ViewHolder>{


    private final List<Equipe> equipeList;

    public EquipeAdaptater(List<Equipe> EquipeList) {
        equipeList = EquipeList;
    }

    @Override
    public void onBindViewHolder(com.example.sportapp.ViewHolder holder, int position) {
        holder.onBind(position);
    }

    @NonNull
    @Override
    public com.example.sportapp.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.equipe_content, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public int getItemCount() {
        if (equipeList != null & equipeList.size() > 0) {
            return equipeList.size();
        } else {
            return 0;
        }
    }

    public void addItems(List<Equipe> equipeList) {
        equipeList.addAll(equipeList);
        notifyDataSetChanged();
    }

    public void deleteItem(int position) {
        if (equipeList != null & equipeList.size() > 0) {
            equipeList.remove(position);
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

            Equipe equipe= equipeList.get(position);



            if (equipe.getNom() != null) {
                mNameTextView.setText(equipe.getNom());
            }

            if (equipe.getNiveau() != null) {
                mAnimeTextView.setText(equipe.getNiveau());
            }

            itemView.setOnClickListener(v -> {
                Intent intent=new Intent(itemView.getContext(), DetailEquipeActivity.class);
                intent.putExtra("key",  equipe.getKey());
                itemView.getContext().startActivity(intent);
            });

            itemView.setOnLongClickListener(v -> {
                Intent intent=new Intent(itemView.getContext(), EditEquipeActivity.class);
                intent.putExtra("key",  equipe.getKey());
                itemView.getContext().startActivity(intent);
                return false;
            });

        }
    }

}