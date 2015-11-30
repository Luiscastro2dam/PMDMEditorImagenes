package com.example.clase.pmdmpractica2;


 import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Camara extends Activity {

      private final String ruta_fotos = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) + "/misfotos/";
      private File file = new File(ruta_fotos);


    @Override
      protected void onCreate(Bundle savedInstanceState) {
           super.onCreate(savedInstanceState);
           setContentView(R.layout.camara);
           file.mkdirs();
          }

     public void btnTomaFoto(View v){
         String file = ruta_fotos + getCode() + ".jpg";
         File mi_foto = new File( file );
         try {
             mi_foto.createNewFile();
         } catch (IOException ex) {
             Log.e("ERROR ", "Error:" + ex);
         }
         //
         Uri uri = Uri.fromFile( mi_foto );
         //Abre la camara para tomar la foto
         Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
         //Guarda imagen
         cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
         //Retorna a la actividad
         startActivityForResult(cameraIntent, 0);
     }
              @SuppressLint("SimpleDateFormat")
      private String getCode()
      {
           SimpleDateFormat dateFormat = new SimpleDateFormat("yyyymmddhhmmss");
           String date = dateFormat.format(new Date() );
            String photoCode = "pic_" + date;
          return photoCode;
         }


    }
