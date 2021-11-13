package com.fonoaudiologia.prosodiappfree;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.ActivityOptionsCompat;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class Resultados extends AppCompatActivity {

    int respostaCorretaContador, end, margem;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.result);

        try {
            Intent intent = getIntent();
            String parametro1 = (String) intent.getSerializableExtra("respostaCorretaContador");
            String parametro2 = (String) intent.getSerializableExtra("end");
            String parametro3 = (String) intent.getSerializableExtra("margem");
            respostaCorretaContador = Integer.valueOf(parametro1);
            end = Integer.valueOf(parametro2);
            margem = Integer.valueOf(parametro3);

        } catch (Exception ex) { }

        TextView pergunta = (TextView) findViewById(R.id.perguntas);
        TextView acertos = (TextView) findViewById(R.id.acertos);
        TextView percent = (TextView) findViewById(R.id.percent);
        ImageView img = (ImageView) findViewById(R.id.imag);

        int auxPerc = (respostaCorretaContador * 100)/end;
        String percentual = String.valueOf(auxPerc);

        pergunta.setText("Total de " + end + " perguntas.");
        acertos.setText(respostaCorretaContador + " respostas certas.");
        percent.setText(percentual + " %");



        if      ( auxPerc > 66) {
            img.setImageResource(R.drawable.happy) ;
        }
        else if ( auxPerc <= 33) {
            img.setImageResource(R.drawable.sad);
        } else img.setImageResource(R.drawable.nerd);
    }


    //volta ao MENU PRINCIPAL
    public void menu(View view) {
        super.finish();
        Intent intent = new Intent(this, SplashScreen.class);
        ActivityOptionsCompat activityOptionsCompat = ActivityOptionsCompat.makeCustomAnimation(getApplicationContext(), R.anim.fade_in, R.anim.fade_out);
        ActivityCompat.startActivity(this,intent, activityOptionsCompat.toBundle());
    }

    public void sair(View view) {
        finish();
        System.exit(0);
    }
}