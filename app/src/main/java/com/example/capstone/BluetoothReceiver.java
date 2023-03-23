package com.example.capstone;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;

import androidx.core.app.ActivityCompat;

public class BluetoothReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        if (BluetoothDevice.ACTION_FOUND.equals(action)) {
            // Discovery has found a device. Get the BluetoothDevice
            // object and its info from the Intent.
            BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
            if (ActivityCompat.checkSelfPermission(context, android.Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            String deviceName = device.getName();
            String deviceHardwareAddress = device.getAddress(); // MAC address
        }
        if (BluetoothAdapter.ACTION_STATE_CHANGED.equals(action)) {
            // Bluetooth state changed
        } else if (BluetoothDevice.ACTION_ACL_CONNECTED.equals(action)) {
            // A device has been connected via Bluetooth
        } else if (BluetoothDevice.ACTION_ACL_DISCONNECTED.equals(action)) {
            // A device has been disconnected via Bluetooth
        } else if (BluetoothAdapter.ACTION_DISCOVERY_STARTED.equals(action)) {
            // Bluetooth discovery has started
        } else if (BluetoothAdapter.ACTION_DISCOVERY_FINISHED.equals(action)) {
            // Bluetooth discovery has finished
        }
    }

}
