package pl.aib.rhymantic.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import pl.aib.rhymantic.R;
import pl.aib.rhymantic.holder.WordResultViewHolder;
import pl.aib.rhymantic.model.WordResult;

public class RhymesAdapter extends RecyclerView.Adapter<WordResultViewHolder> {
    private List<WordResult> data = new ArrayList<>();

    public void setData(List<WordResult> data) {
        this.data = data;
        notifyItemRangeChanged(0, this.data.size());
    }

    @NonNull
    @Override
    public WordResultViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new WordResultViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.word_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull WordResultViewHolder holder, int position) {
        holder.setData(this.data.get(position));
    }

    @Override
    public int getItemCount() {
        return this.data.size();
    }
}
