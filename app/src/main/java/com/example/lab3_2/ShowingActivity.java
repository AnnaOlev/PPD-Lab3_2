package com.example.lab3_2;

import android.support.v7.app.AppCompatActivity;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import java.util.ArrayList;

public class ShowingActivity extends AppCompatActivity {
    RecyclerView mRecyclerView;
    ArrayList<String> outputID = new ArrayList<>();
    ArrayList<String> outputFIO = new ArrayList<>();
    ArrayList<String> outputDATE = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_showing);
        outputID = getIntent().getStringArrayListExtra("id");
        outputFIO = getIntent().getStringArrayListExtra("fio");
        outputDATE = getIntent().getStringArrayListExtra("date");
        mRecyclerView = findViewById(R.id.outList);
        mRecyclerView.setAdapter(new CustomAdapter(this, outputID.size()));
    }

    private class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.ViewHolder> {

        private LayoutInflater inflater;
        Context context;
        private int number;

        CustomAdapter(Context context, int number) {
            this.number = number;
            this.context = context;
            this.inflater = LayoutInflater.from(context);
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            final View view = inflater.inflate(R.layout.list_item, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, final int position) {
            holder.id.setText(outputID.get(position));
            holder.name.setText(outputFIO.get(position));
            holder.date.setText(outputDATE.get(position));
        }

        @Override
        public int getItemCount() {
            return number;
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            View view;
            final TextView id, name, date;

            ViewHolder(final View view) {
                super(view);
                this.view = view;
                id = view.findViewById(R.id.id);
                name = view.findViewById(R.id.name);
                date = view.findViewById(R.id.date);
            }
        }

        private void split(String string, StringBuffer id, StringBuffer name, StringBuffer date) {
            int i = string.indexOf(' ');
            if (i != -1) {
                id.append(string, 0, i);
                string = string.substring(i + 1);
                i = string.lastIndexOf(' ');
                if (i != -1) {
                    name.append(string, 0, i - 9);
                    if (i + 1 <= string.length()) {
                        string = string.substring(i - 8);
                        date.append(string);
                    }
                } else name.append(string);
            } else id.append(string);
        }
    }
}