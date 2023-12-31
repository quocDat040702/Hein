package com.hein.activities.adapters;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.hein.R;
import com.hein.entity.Booking;
import com.hein.home.filter.ColorViewModel;
import com.hein.home.filter.FilterViewModel;

import java.util.List;
import java.util.Set;

public class ColorBookingdatper extends RecyclerView.Adapter<ColorBookingdatper.ViewHolder> {
    private List<ColorViewModel> colorOptions;
    public final static String DEFAULT_COLOR = "zircon";
    private LayoutInflater mInflater;
    private Resources resources;
    private String packageName;
    private Booking booking;

    public ColorBookingdatper(Context context, List<ColorViewModel> data, Set<String> bookingColor, int selectedColorPosition, Booking booking) {
        this.mInflater = LayoutInflater.from(context);
        this.colorOptions = data;
        this.resources = context.getResources();
        this.packageName = context.getPackageName();
        this.booking = booking;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.color_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ColorViewModel colorViewModel = colorOptions.get(position);
        String colorName = colorViewModel.getName();
        ColorStateList innerColor = getColorStateListByName(colorViewModel.getColorResourceName());
        ColorStateList borderColor = getColorStateListByName(DEFAULT_COLOR);

        holder.colorView.setBackgroundTintList(innerColor);
        holder.border.setBackgroundTintList(borderColor);

        if (booking.getColor() != null && booking.getColor().equals(colorName)) {
            holder.border.setBackgroundTintList(innerColor);
        }

        holder.border.setOnClickListener(view -> {
            if (booking.getColor() != null && booking.getColor().equals(colorName)) {
                booking.setColor(null);
                holder.border.setBackgroundTintList(borderColor);
                notifyDataSetChanged();
            }
            else {
                booking.setColor(colorName);
                holder.border.setBackgroundTintList(innerColor);
                notifyDataSetChanged();
            }
        });
    }

    private ColorStateList getColorStateListByName(String colorName) {
        return resources.getColorStateList(
                resources.getIdentifier(colorName, "color", packageName),
                null
        );
    }

    /*public void setBackgroundTintList(int positionActive) {
        for (int i = 0; i < colorOptions.size(); i++) {
            colorOptions.get(i).setBackgroundTintList(tintList);
            notifyItemChanged(i);
        }
    }*/

    @Override
    public int getItemCount() {
        return colorOptions.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {  
        ConstraintLayout border;
        TextView colorView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            border = itemView.findViewById(R.id.border_color_option_item);
            colorView = itemView.findViewById(R.id.inner_color_option_item);
        }
    }
}
