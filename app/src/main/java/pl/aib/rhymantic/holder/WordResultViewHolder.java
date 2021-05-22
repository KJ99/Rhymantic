package pl.aib.rhymantic.holder;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import pl.aib.rhymantic.R;
import pl.aib.rhymantic.model.WordResult;

public class WordResultViewHolder extends RecyclerView.ViewHolder {
    public WordResultViewHolder(@NonNull View itemView) {
        super(itemView);
    }

    public void setData(WordResult wordResult) {
        TextView value = this.itemView.findViewById(R.id.value);
        value.setText(wordResult.getWord());
    }
}
