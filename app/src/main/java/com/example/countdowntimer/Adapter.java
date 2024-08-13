package com.example.countdowntimer;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {
    LayoutInflater inflater;
    List<CountdownData> dataList;
    Context context;
    public Adapter(Context context, List<CountdownData> dataList) {
        this.inflater = LayoutInflater.from(context);
        this.dataList = dataList;
        this.context = context;
    }

    @NonNull
    @Override
    public Adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = inflater.inflate(R.layout.button_list, parent, false);
        return new Adapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull Adapter.ViewHolder holder, int position) {
        String title = dataList.get(position).getTitle();
        String date = dataList.get(position).getDate();
        holder.button.setText(title);

        holder.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, CountdownPage.class);
                intent.putExtra("TITLE", title);
                intent.putExtra("DATE", date);
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        Button button;
        public ViewHolder(@NonNull View v) {
            super(v);
            button = v.findViewById(R.id.buttons);
        }
    }
}