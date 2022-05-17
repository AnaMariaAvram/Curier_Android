package com.courier.manager.ui;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavDeepLinkBuilder;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.courier.manager.R;
import com.courier.manager.adapter.CourierAdapter;
import com.courier.manager.adapter.CourierListener;
import com.courier.manager.database.CourierDao;
import com.courier.manager.database.CourierEntity;
import com.courier.manager.databinding.FragmentDatabaseBinding;
import com.courier.manager.utils.Utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

//fragmentul care contine lista curierilor favoriti
@AndroidEntryPoint
public class DatabaseFragment extends Fragment implements CourierListener {

    //injectam DAO-ul de curieri prin Hil
    @Inject
    CourierDao courierDao;

    //Il folosim la legatura dintre activitate si layout/view
    private FragmentDatabaseBinding binding;

    //Adaptorul necesar pentru recyclerview
    private CourierAdapter courierAdapter;

    //Lista de curieri favoriti
    private List<CourierEntity> favoriteCouriers = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentDatabaseBinding.inflate(getLayoutInflater());
        favoriteCouriers = getData();
        Utils.setTypeOfButton(2);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initializeRecyclerView();
        listenToSearch();
    }

    //Metoda care "asculta" atunci cand user-ul cauta numele unui curieri
    private void listenToSearch() {
        binding.searchBar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (Objects.requireNonNull(binding.searchBar.getText()).toString().isEmpty()) {
                    favoriteCouriers = courierDao.getAll();
                } else {
                    favoriteCouriers = courierDao.queryUsers(binding.searchBar.getText().toString());
                }
                initializeRecyclerView();
            }
        });
    }

    //Initializarea recyclerview-ului
    private void initializeRecyclerView() {
        RecyclerView recyclerview = binding.recyclerview;
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(requireActivity().getApplicationContext());
        recyclerview.setLayoutManager(linearLayoutManager);
        recyclerview.setHasFixedSize(true);
        courierAdapter = new CourierAdapter(this);
        recyclerview.setAdapter(courierAdapter);
        displayData(favoriteCouriers);
    }

    //Trimiterea listei de curieri catre adaptorul de recyclerview
    @SuppressLint("NotifyDataSetChanged")
    private void displayData(List<CourierEntity> couriers) {
        courierAdapter.submitCourierList(couriers);
        courierAdapter.notifyDataSetChanged();
    }

    //Metoda care returneaza lista de curieri care au fost salvati
    private List<CourierEntity> getData() {
        return courierDao.getAll();
    }

    //Metoda chemata atunci cand salvam un curier
    @Override
    public void saveCourier(CourierEntity courier) {
    }

    //metoda chemata atunci cand stergem un curier
    @Override
    public void deleteCourier(CourierEntity courier) {
        deleteCourierImpl(courier);
    }

    //metoda chemata atunci cand distribuim numele unui curier
    @Override
    public void shareCourier(CourierEntity courier) {
        shareCourierImpl(courier);
    }

    //implementarea metodei de distribuire a unui curier
    private void shareCourierImpl(CourierEntity courier) {
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, courier.getName());
        sendIntent.setType("text/plain");
        Intent shareIntent = Intent.createChooser(sendIntent, null);
        startActivity(shareIntent);
    }

    //implementarea metodei de stergere a unui curier
    private void deleteCourierImpl(CourierEntity courier) {
        courierDao.delete(courier);
        favoriteCouriers = courierDao.getAll();
        initializeRecyclerView();
        showSmallNotification("Courier deleted", "The courier " + courier.getName() + " has been deleted.\nTap here and see every courier.");
    }

    private PendingIntent pendingIntent(Context context) {
        return new NavDeepLinkBuilder(context)
                .setGraph(R.navigation.nav_graph)
                .setDestination(R.id.listFragment)
                .createPendingIntent();
    }

    private void showSmallNotification(String title, String message) {
        String CHANNEL_ID = "couriers_channel";
        String CHANNEL_NAME = "Courier Notification";
        NotificationManager notificationManager = (NotificationManager) getActivity().getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationCompat.InboxStyle inboxStyle = new NotificationCompat.InboxStyle();
        inboxStyle.addLine(message);
        NotificationChannel channel = new NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_HIGH);
        channel.enableVibration(true);
        channel.setLightColor(Color.BLUE);
        channel.enableLights(true);
        channel.setShowBadge(true);
        notificationManager.createNotificationChannel(channel);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(getActivity(), CHANNEL_ID)
                .setVibrate(new long[]{0, 100})
                .setPriority(Notification.PRIORITY_MAX)
                .setLights(Color.BLUE, 3000, 3000)
                .setAutoCancel(true)
                .setContentTitle(title)
                .setContentIntent(pendingIntent(getActivity()))
                .setSmallIcon(R.mipmap.ic_launcher)
                .setStyle(inboxStyle)
                .setContentText(message);
        notificationBuilder.setChannelId("couriers_channel");
        notificationManager.notify(CHANNEL_ID, 1, notificationBuilder.build());
    }
}
