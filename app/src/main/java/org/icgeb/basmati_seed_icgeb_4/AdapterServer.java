package org.icgeb.basmati_seed_icgeb_3;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;


public class AdapterServer extends RecyclerView.Adapter<AdapterServer.ViewHolder>{

    private ArrayList<ModelServer> modelServers;
    private Context context;

    public AdapterServer(ArrayList<ModelServer> modelServers, Context context){
        this.modelServers = modelServers;
        this.context = context;
    }

    @NonNull
    @Override
    public AdapterServer.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_server_model, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterServer.ViewHolder holder, int position) {
        ModelServer modelServer = modelServers.get(position);
        holder.textView1.setText(modelServer.getTitle());
        holder.textView2.setText(modelServer.getDesc());
        Picasso.get().load(modelServer.getImage()).into(holder.imageView);
        holder.imageView.setRotation(modelServer.getAngle());

    }

    @Override
    public int getItemCount() {
        return modelServers.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;
        TextView textView1;
        TextView textView2;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.resultImage);
            textView1 = itemView.findViewById(R.id.resultAngle);
            textView2 = itemView.findViewById(R.id.resultResult);
        }
    }
}

