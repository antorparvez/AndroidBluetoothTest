package com.example.androidbluetoothtest;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.example.androidbluetoothtest.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    BluetoothAdapter myBluetoothAdapter;
    int BtEnableRequestCode = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        myBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();


        turnOnBluetooth();
        turnOffBluetooth();
    }

    private void turnOnBluetooth() {

        binding.bluetoothOn.setOnClickListener(view -> {
            if (myBluetoothAdapter == null) {
                Toast.makeText(this, "Bluetooth does not support on this device", Toast.LENGTH_SHORT).show();
            } else {
                if (!myBluetoothAdapter.isEnabled()) {


                    Intent btEnableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                    if (ActivityCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {
                        // TODO: Consider calling
                        //    ActivityCompat#requestPermissions
                        // here to request the missing permissions, and then overriding
                        //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                        //                                          int[] grantResults)
                        // to handle the case where the user grants the permission. See the documentation
                        // for ActivityCompat#requestPermissions for more details.
                        return;
                    }
                    startActivityForResult(btEnableIntent, BtEnableRequestCode);
                }
            }
        });

    }

    private void turnOffBluetooth() {
        binding.bluetoothOff.setOnClickListener(view -> {
            if (myBluetoothAdapter.isEnabled()) {
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }
                myBluetoothAdapter.disable();
            }

        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode== BtEnableRequestCode){
            if (resultCode == RESULT_OK){
                Toast.makeText(this, "Bluetooth is Enabled", Toast.LENGTH_SHORT).show();
            }else if (resultCode == RESULT_CANCELED){
                Toast.makeText(this, "Bluetooth Enabling Cancelled", Toast.LENGTH_SHORT).show();

            }
        }
    }
}