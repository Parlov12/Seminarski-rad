package com.example.deliverymanager;

import android.content.Context;
import android.widget.Toast;

public class Message {

        public static void showToast(String msg, Context ctx) {
            Toast.makeText(ctx, msg, Toast.LENGTH_SHORT).show();
        }
}
