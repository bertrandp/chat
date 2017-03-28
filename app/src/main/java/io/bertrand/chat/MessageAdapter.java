package io.bertrand.chat;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import io.bertrand.chat.model.Message;

/**
 * Created by Bertrand on 28/03/2017.
 */

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.ViewHolder> {

    private List<Message> dataset;

    // Provide a reference to the views for each data item
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView loginTV;
        public TextView msgTV;

        public ViewHolder(View v) {
            super(v);
            loginTV = (TextView) v.findViewById(R.id.login);
            msgTV = (TextView) v.findViewById(R.id.msgtxt);
        }
    }


    // Provide a suitable constructor (depends on the kind of dataset)
    public MessageAdapter(List<Message> dataset) {
        this.dataset = dataset;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public MessageAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.message_view, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(MessageAdapter.ViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        holder.msgTV.setText(dataset.get(position).getMessage());
        holder.loginTV.setText(dataset.get(position).getLogin());
    }

    @Override
    public int getItemCount() {
        return dataset.size();
    }
}
