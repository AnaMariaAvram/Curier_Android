package com.courier.manager.adapter;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.courier.manager.database.CourierEntity;
import com.courier.manager.databinding.ItemCourierBinding;
import com.courier.manager.utils.Utils;

//view holder-ul pentru recycler view
public class CourierViewHolder extends RecyclerView.ViewHolder {
    private final ItemCourierBinding binding;
    CourierListener listener;

    public CourierViewHolder(@NonNull ItemCourierBinding binding, CourierListener listener) {
        super(binding.getRoot());
        this.binding = binding;
        this.listener = listener;
    }

    public void bind(CourierEntity courier) {

        if (courier.getYearsOfExperience() > 1) {
            binding.textviewExperience.setText(courier.getYearsOfExperience() + " years of experience");
        } else if (courier.getYearsOfExperience() == 0) {
            binding.textviewExperience.setText("Less than one year.");
        } else {
            binding.textviewExperience.setText("One year of exp.");
        }
        binding.textviewOrders.setText(courier.getOrders() + " orders completed");
        binding.textviewName.setText(courier.getName());
        binding.buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Utils.getTypeOfButton() == 1) {
                    listener.saveCourier(courier);
                } else {
                    listener.deleteCourier(courier);
                }

            }
        });

        binding.buttonShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.shareCourier(courier);
            }
        });
    }
}
