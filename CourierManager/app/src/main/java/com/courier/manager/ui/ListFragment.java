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
import com.courier.manager.data.DataProvider;
import com.courier.manager.database.CourierDao;
import com.courier.manager.database.CourierEntity;
import com.courier.manager.databinding.FragmentListBinding;
import com.courier.manager.utils.Utils;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

//fragmentul care contine lista tuturor curierilor
@AndroidEntryPoint
public class ListFragment extends Fragment implements CourierListener {

    //clasa care umple lista de curieri
    private final DataProvider dataProvider = new DataProvider();

    //injectam DAO-ul de curieri prin Hilt
    @Inject
    CourierDao courierDao;

    //Il folosim la legatura dintre activitate si layout/view
    private FragmentListBinding binding;

    //Adaptorul necesar pentru recyclerview
    private CourierAdapter courierAdapter;

    //lista de curieri
    private ArrayList<CourierEntity> couriers = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentListBinding.inflate(getLayoutInflater());
        couriers = dataProvider.getData();
        Utils.setTypeOfButton(1);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initializeRecyclerView();

    }

    //initializarea recyclerview-ului
    private void initializeRecyclerView() {
        RecyclerView recyclerview = binding.recyclerview;
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(requireActivity().getApplicationContext());
        recyclerview.setLayoutManager(linearLayoutManager);
        recyclerview.setHasFixedSize(true);
        courierAdapter = new CourierAdapter(this);
        recyclerview.setAdapter(courierAdapter);
        displayData(couriers);
    }

    //trimiterea listei de curieri catre adaptorul de recyclerview
    @SuppressLint("NotifyDataSetChanged")
    private void displayData(List<CourierEntity> couriers) {
        courierAdapter.submitCourierList(couriers);
        courierAdapter.notifyDataSetChanged();
    }

    //metoda chemata atunci cand salvam un curier
    @Override
    public void saveCourier(CourierEntity courier) {
        saveCourierImpl(courier);
    }

    //metoda chemata atunci cand stergem un curier
    @Override
    public void deleteCourier(CourierEntity courier) {
    }

    //metoda chemata atunci cand distribuim numele unui curier
    @Override
    public void shareCourier(CourierEntity courier) {
        shareCourierImpl(courier);
    }

    //notificare pentru salvarea unui curier
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

    //intent-ul folosit atunci cand apasam pe o notificare,
    // ne va duce catre fragmentul unde este salvat curierul respectiv
    private PendingIntent pendingIntent(Context context) {
        return new NavDeepLinkBuilder(context)
                .setGraph(R.navigation.nav_graph)
                .setDestination(R.id.databaseFragment)
                .createPendingIntent();
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

    //implementarea metodei de salvare a unui curier
    private void saveCourierImpl(CourierEntity courier) {
        courierDao.insert(courier);
        showSmallNotification("Courier saved", "The courier " + courier.getName() + " has been saved.\nTap here and see your favorite couriers.");
    }

}
