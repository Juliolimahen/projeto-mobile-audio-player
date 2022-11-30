package com.example.musicfai.services;

import android.Manifest;
import android.content.pm.PackageManager;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.musicfai.views.MainActivity;

public interface IPermissao {
    void solicitarPermissao();
    boolean checarPermissao();
}
