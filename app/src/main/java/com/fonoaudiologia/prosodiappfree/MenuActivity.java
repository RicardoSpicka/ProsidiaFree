package com.fonoaudiologia.prosodiappfree;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.app.ActivityOptionsCompat;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.View;

public class MenuActivity extends AppCompatActivity {

    String alertTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_principal);
    }

    // ABRE A ACTIVITY DO JOGO ACERTE A TÔNICA
    public void tonica(View view) {
        super.finish();
        Intent intent = new Intent(this, Tonica.class);
        //startActivity(intent);
        ActivityOptionsCompat activityOptionsCompat = ActivityOptionsCompat.makeCustomAnimation(getApplicationContext(), R.anim.fade_in, R.anim.fade_out);
        ActivityCompat.startActivity(this,intent, activityOptionsCompat.toBundle());
    }

    // ABRE A ACTIVITY DO JOGO QUAL É A PERGUNTA
    public void pergunta(View view) {
        super.finish();
        Intent intent = new Intent(this, Pergunta.class);
        //startActivity(intent);
        ActivityOptionsCompat activityOptionsCompat = ActivityOptionsCompat.makeCustomAnimation(getApplicationContext(), R.anim.fade_in, R.anim.fade_out);
        ActivityCompat.startActivity(this,intent, activityOptionsCompat.toBundle());
    }

    // ABRE A ACTIVITY DO JOGO AFIRMAÇÃO OU ORDEM
    public void ordem(View view) {
        super.finish();
        Intent intent = new Intent(this, Ordem.class);
        //startActivity(intent);
        ActivityOptionsCompat activityOptionsCompat = ActivityOptionsCompat.makeCustomAnimation(getApplicationContext(), R.anim.fade_in, R.anim.fade_out);
        ActivityCompat.startActivity(this,intent, activityOptionsCompat.toBundle());
    }

    //escuta se foi clicado o botão sair
    public void sair(View view) {
        finish();
        System.exit(0);
    }

    //escuta se foi clicado o botão SOBRE O JOGO
    public void checkSobre(View view) {

        alertTitle = "SOBRE O JOGO"; /*monta a mensagem sobre o jogo*/

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(alertTitle);
        builder.setIcon(R.drawable.jacare);
        builder.setCancelable(false);
        builder.setMessage("Com as vozes de: " +
                "\n   Débora Spicka," +
                "\n   Leticia Spicka e" +
                "\n   Ricardo Spicka" +
                "\n\nDesenvolvedor: " +
                "\n    Ricardo Spicka" +
                "\n\nSupervisão Técnica: " +
                "\n   Fonoaudióloga Denise Spicka" +
                "\n\nProsodiappSuper versão 1.0" +
                "\n   Outubro de 2021");

        builder.setPositiveButton("VOLTAR", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        builder.show();
    }

    public void checkFinalidade(View view) {

        alertTitle = "FINALIDADE"; /*monta a mensagem finalidade*/

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(alertTitle);
        builder.setCancelable(false);
        builder.setIcon(R.drawable.teacher);
        builder.setMessage("\nTERAPIA EM DISTÚRBIO DA PROSODIA" +
                "\n\nO jogo QUAL É A TÔNICA trabalha a percepção de tonicidade da sílaba na palavra." +
                "\n\nO jogo QUAL É A PERGUNTA trabalha a identificação de uma pergunta." +
                "\n\nO jogo AFIRMAÇÃO ou ORDEM ajuda a identificar se uma frase é uma afirmação ou uma ordem." +
                "\n\nTodos os aspectos são habilidades do hemisfério direito do cérebro.");
        builder.setPositiveButton("VOLTAR", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        builder.show();
    }
}