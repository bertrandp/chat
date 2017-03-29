package io.bertrand.chat;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.view.SimpleDraweeView;
import io.bertrand.chat.model.Message;

import java.util.List;

import static io.bertrand.chat.MainActivity.LOGIN;
import static io.bertrand.chat.MainActivity.PREFS_NAME;

/**
 * Created by Bertrand on 28/03/2017.
 */

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.ViewHolder> {

    private List<Message> dataset;
    private Context context;

    // Provide a suitable constructor (depends on the kind of dataset)
    public MessageAdapter(List<Message> dataset, Context context) {
        this.dataset = dataset;
        this.context = context;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public MessageAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View v = LayoutInflater.from(context).inflate(R.layout.message_view, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(MessageAdapter.ViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        holder.msgTV.setText(dataset.get(position).getMessage());
        holder.loginTV.setText(dataset.get(position).getLogin());

        if(dataset.get(position).getImages() != null && dataset.get(position).getImages().size() > 0) {
            Uri uri = Uri.parse(dataset.get(position).getImages().toString());
            holder.img.setImageURI(uri);
        } else {
            holder.img.setVisibility(View.GONE);
        }

        SharedPreferences settings = context.getSharedPreferences(PREFS_NAME, 0);
        if (dataset.get(position).getLogin().equals(settings.getString(LOGIN, "login"))) {

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            params.gravity = Gravity.RIGHT;

            holder.loginTV.setLayoutParams(params);
            holder.msgTV.setLayoutParams(params);

            holder.loginTV.setGravity(Gravity.RIGHT);
            holder.msgTV.setGravity(Gravity.RIGHT);
        }

    }

    @Override
    public int getItemCount() {
        return dataset.size();
    }

    // Provide a reference to the views for each data item
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView loginTV;
        public TextView msgTV;
        public SimpleDraweeView img;

        public ViewHolder(View v) {
            super(v);
            loginTV = (TextView) v.findViewById(R.id.login);
            msgTV = (TextView) v.findViewById(R.id.msgtxt);
            img = (SimpleDraweeView) v.findViewById(R.id.image);
        }
    }
}
