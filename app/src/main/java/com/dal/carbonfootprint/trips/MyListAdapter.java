package com.dal.carbonfootprint.trips;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.dal.carbonfootprint.R;
import com.dal.carbonfootprint.dashboard.Travel;

import org.jetbrains.annotations.NotNull;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

/**
 * @Author Carbon vision
 * List Adapater class to display user trip details in recycler view
 */
public class MyListAdapter extends RecyclerView.Adapter<MyListAdapter.ViewHolder> {
    private ArrayList<Travel> listdata;

    /**
     * Parametrized constructor
     *
     * @param listdata
     */
    public MyListAdapter(ArrayList<Travel> listdata) {
        this.listdata = listdata;
    }

    /**
     * Overwriting the method to create view holder
     *
     * @param parent
     * @param viewType
     * @return
     */
    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem = layoutInflater.inflate(R.layout.list_trips, parent, false);
        ViewHolder viewHolder = new ViewHolder(listItem);
        return viewHolder;
    }

    /**
     * Binding user vehicle details to recycler view
     *
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Travel myListData = listdata.get(position);
        holder.Name.setText(listdata.get(position).getSource() + " to " + listdata.get(position).getDestination());
        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        String strDate = dateFormat.format(listdata.get(position).getTravelDate());
        holder.Date.setText(strDate);
        holder.Distance.setText(listdata.get(position).getDistance() + " Km");
        String uri = "@drawable/myresource";  // where myresource (without the extension) is the file

        if (position % 3 == 0)
            holder.image.setImageResource(R.drawable.ic_car2);
        else if (position % 3 == 1) holder.image.setImageResource(R.drawable.ic_car3);
        else if (position % 3 == 2) holder.image.setImageResource(R.drawable.ic_car4);

    }

    @Override
    public int getItemCount() {
        return listdata.size();
    }

    /**
     * Static inner class to declare User Trip Model
     */
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView Name;
        public TextView Date;
        public TextView Distance;
        public ImageView image;
        public RelativeLayout relativeLayout;

        public ViewHolder(View itemView) {
            super(itemView);
            this.Name = (TextView) itemView.findViewById(R.id.name);
            this.Date = (TextView) itemView.findViewById(R.id.date);
            this.Distance = (TextView) itemView.findViewById(R.id.message);
            this.image = (ImageView) itemView.findViewById(R.id.carimage);
            relativeLayout = (RelativeLayout) itemView.findViewById(R.id.usersRecycler);
        }
    }
}
