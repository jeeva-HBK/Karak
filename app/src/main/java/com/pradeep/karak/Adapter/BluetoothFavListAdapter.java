package com.pradeep.karak.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.pradeep.karak.Callbacks.ItemClickListener;
import com.pradeep.karak.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class BluetoothFavListAdapter extends RecyclerView.Adapter<BluetoothFavListAdapter.ViewHolder> {
    private JSONArray favArr;
    ItemClickListener listener;

    public BluetoothFavListAdapter(JSONArray arr, ItemClickListener listener) {
        this.favArr = arr;
        this.listener = listener;
    }

    @NonNull
    @Override
    public BluetoothFavListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.bluetooth_fav_list_item, parent, false);
        return new BluetoothFavListAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BluetoothFavListAdapter.ViewHolder holder, int position) {

        try {
            JSONObject obj = favArr.getJSONObject(position);
            String deviceMac = obj.getString("mac");
            holder.bleText.setText(deviceMac);
            holder.del.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onUnSave(deviceMac);
                }
            });

            holder.root.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onSaveClicked(deviceMac);
                }
            });

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return favArr.length();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView bleText;
        ImageView del, bleImage;
        CardView root;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            bleText = itemView.findViewById(R.id.txtBleMac);
            bleImage = itemView.findViewById(R.id.imgMachineImage);
            del = itemView.findViewById(R.id.img_del);
            root = itemView.findViewById(R.id.favRoot);
        }
    }
}
