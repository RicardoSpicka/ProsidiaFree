package com.fonoaudiologia.prosodiappfree;

import androidx.appcompat.app.AppCompatActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.app.ActivityOptionsCompat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class Pergunta extends AppCompatActivity {

    private int quizTot;                                  // quantidade total de perguntas
    private int quizCount = 1;                            // contador do numero de perguntas
    Integer tentativas;                                   // quantidade de tentativas erradas por pergunta - é regressivo
    Integer margem;                                       // valor que vai servir de base para classificar a  pontuação
    private CharSequence respEscolhida = "nenhuma";       // seta a resposta escolhida como nenhuma para o caso de não clicar em nenhum botão do radiogrupo
    private CharSequence respCerta;                       // grava a respota certa
    private int respostaCorretaContador = 0;              // inicia o contador de quizes - que são as linhas da matriz
    int end;
    String var1, var2, var3, c1, c2, c3, endTitle, alertTitle;
    RadioGroup rg;
    RadioButton radioButton, Radio01, Radio02, Radio03;
    ImageView img;
    TextView contador, acertos, resposta;
    MediaPlayer mp1, mp2, mp3, certo, erro;
    ImageButton bt01, bt02, bt03, opcao01, opcao02, opcao03;

    /*array list com os dados*/
    ArrayList<ArrayList<Object>> quizArray = new ArrayList<>();

        Object quizData[][]={ /*nessa MATRIZ cada linha é um  QUIZ
                            o primeiro ARRAY de cada linha é a figura,
                            o segundo ARRAY de cada linha é a resposta certa (PERGUNTA),
                            o terceiro e quarto ARRAYs de cada linha são as respostas erradas (AFIRMAÇÃO E ORDEM)*/
                //  magem     //  pergunta  // afirmação  // ordem  //
            {R.drawable.coracao,  R.raw.corp, R.raw.cora, R.raw.coro},
            {R.drawable.domino,   R.raw.domp, R.raw.doma, R.raw.domo},
            {R.drawable.urubu,    R.raw.urup, R.raw.urua, R.raw.uruo},
            {R.drawable.jacare,   R.raw.jacp, R.raw.jaca, R.raw.jaco},
            {R.drawable.violao,   R.raw.viop, R.raw.vioa, R.raw.vioo},
//            {R.drawable.canguru,  R.raw.canp, R.raw.cana, R.raw.cano},
//            {R.drawable.abacaxi,  R.raw.abap, R.raw.abaa, R.raw.abao},
//            {R.drawable.cavalo,   R.raw.cavp, R.raw.cava, R.raw.cavo},
//            {R.drawable.janela,   R.raw.janp, R.raw.jana, R.raw.jano},
//            {R.drawable.tomate,   R.raw.tomp, R.raw.toma, R.raw.tomo},
//            {R.drawable.revolver, R.raw.revp, R.raw.reva, R.raw.revo},
//            {R.drawable.banana,   R.raw.banp, R.raw.bana, R.raw.bano},
//            {R.drawable.cachorro, R.raw.caop, R.raw.caoa, R.raw.caoo},
//            {R.drawable.martelo,  R.raw.marp, R.raw.mara, R.raw.maro},
//            {R.drawable.xicara,   R.raw.xicp, R.raw.xica, R.raw.xico},
//            {R.drawable.medico,   R.raw.medp, R.raw.meda, R.raw.medo},
//            {R.drawable.lampada,  R.raw.lamp, R.raw.lama, R.raw.lamo},
//            {R.drawable.onibus,   R.raw.onip, R.raw.onia, R.raw.onio},
//            {R.drawable.brocolis, R.raw.brop, R.raw.broa, R.raw.broo},
//            {R.drawable.fosforo,  R.raw.fosp, R.raw.fosa, R.raw.foso},
//            {R.drawable.oculos,   R.raw.ocup, R.raw.ocua, R.raw.ocuo}
    };

        @Override
        protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.pergunta);

        end = 4;
        quizTot = quizData.length;    /*verifica quantas linhas tem a matriz - cada linha é uma pergunta do quiz*/
        margem = end/3;   /*divide no numero de perguntas em 3 para classificar a resposta*/

        /*sorteia uma linha da matriz quizData para montar o primeiro quiz */
        for(int i=0; i < quizTot; i++) {
                ArrayList<Object> tmpArray = new ArrayList<>();
                tmpArray.add(quizData[i][0]);
                tmpArray.add(quizData[i][1]);
                tmpArray.add(quizData[i][2]);
                tmpArray.add(quizData[i][3]);
                quizArray.add(tmpArray); // joga a nova array no "quizArray"
            }

        /*monta a proxima pergunta*/
        showNextQuiz();
    }

    public void showNextQuiz(){

        if (erro != null) erro.release(); //libera o mp3 caso ele não seja nulo
        if (certo != null) certo.release(); //libera o mp3 caso ele não seja nulo

        tentativas = 2; //renova as 3 tentativas cada vez que começa um novo quiz
        /*esse bloca seta o contador de quizes e o numero de acertos*/
        contador = (TextView) findViewById(R.id.desafio);
        contador.setText("Pergunta " + quizCount + " de " + end);
        acertos = (TextView) findViewById(R.id.acertos);
        acertos.setText("Acertos: " + respostaCorretaContador);

        /*aqui escolhe aleatoriamente uma perguta da lista e seta a respota correta*/
        Random random = new Random();
        int randomNum = random.nextInt(quizArray.size()); /*aqui embaralha as linhas*/
        ArrayList<Object> quiz = quizArray.get(randomNum); /* aqui escolhe uma das linhas das perguntas e joga dentro de um array quiz*/

        /*esse grupo atribui a respota correta na variável respCerta*/
        resposta = (TextView) findViewById(R.id.resposta);
        resposta.setText((int) quiz.get(1));
        respCerta = resposta.getText();

        /*carrega a imagem da pergunta*/
        img = (ImageView) findViewById(R.id.imagem);
        img.setImageResource((Integer) quiz.get(0));

        /*aqui apaga a pergunta e a resposta da array para poder embaralhar as opções*/
        quiz.remove(0); //remove a primeira indice da linha
        Collections.shuffle(quiz); //embaralha os 3 itens restantes

        /*esse bloco atribui a cada botão de resposta o AUDIO opção sorteada*/
        Radio01 = (RadioButton) findViewById(R.id.radio_1);
        Radio01.setText((int) quiz.get(0));

        /*esse bloco atribui a cada botão de resposta o AUDIO opção sorteada*/
        Radio02 = (RadioButton) findViewById(R.id.radio_2);
        Radio02.setText((int) quiz.get(1));

        /*esse bloco atribui a cada botão de resposta o AUDIO opção sorteada*/
        Radio03 = (RadioButton) findViewById(R.id.radio_3);
        Radio03.setText((int) quiz.get(2));

        /*aqui remove da matriz o pergunta que já foi usada*/
        quizArray.remove(randomNum); /*remove o numero aleatorio*/

        /*fica "ouvindo* para saber se o botão 01 foi apertado*/
        opcao01 = (ImageButton) findViewById(R.id.botao01);
        mp1 = MediaPlayer.create(this, (Integer) quiz.get(0));
        opcao01.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mp1.start(); //toca do som correspondente ao botão 01
            }
        });

        /*fica "ouvindo* para saber se o botão 02 foi apertado*/
        opcao02 = (ImageButton) findViewById(R.id.botao02);
        mp2 = MediaPlayer.create(this, (Integer) quiz.get(1));
        opcao02.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mp2.start();  //toca do som correspondente ao botão 02
            }
        });

        /*fica "ouvindo* para saber se o botão 03 foi apertado*/
        opcao03 = (ImageButton) findViewById(R.id.botao03);
        mp3 = MediaPlayer.create(this, (Integer) quiz.get(2));
        opcao03.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mp3.start(); //toca do som correspondente ao botão 03
            }
        });
    }

    /*trabalha o RADIOGRUPO de resposta. Fica "escutando" qual está setado*/
    public void onRadioButtonClicked(View view) {

        /*verifica se algum deles está setado*/
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch (view.getId()) {
            case R.id.radio_1:
                if (checked) // se o botão 01 está chegado atriu a resposta ao "respEscolhida"
                    radioButton = findViewById(view.getId());
                respEscolhida = radioButton.getText();
                break;
            case R.id.radio_2:
                if (checked) // se o botão 02 está chegado atriu a resposta ao "respEscolhida"
                    radioButton = findViewById(view.getId());
                respEscolhida = radioButton.getText();
                break;
            case R.id.radio_3:
                if (checked) // se o botão 02 está chegado atriu a resposta ao "respEscolhida"
                    radioButton = findViewById(view.getId());
                respEscolhida = radioButton.getText();
                break;
        }
    }

    /*aqui verifica se a resposta escolhida está certa*/
    public void checkResposta(View view){

        /*compara a resposta escolhia com a resposta correta*/
        if(respEscolhida == respCerta){

            alertTitle = "MUITO BOM! \nCORRETO!"; /*monta a mensagem de alerta certa*/

                    respostaCorretaContador++; //incrementa 1 ao contador de quizes

                    certo = MediaPlayer.create(this, R.raw.certo);
                    certo.start(); // toca o som de acerto

                    /*Constroi a mensagem de Acerto*/
                    AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setTitle(alertTitle);
                    builder.setCancelable(false);
                    builder.setIcon(R.drawable.happy);
                    builder.setMessage("Ganhou +1 ponto. \n\nParabéns! \n\nPontuação: " + respostaCorretaContador);
                    builder.setPositiveButton("PRÓXIMO", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            /*aqui verifica se foram usadas todas as perguntas*/
                            if (quizCount < end) { //se não chegou ao fim
                                quizCount++;           // incrementa o contador
                                showNextQuiz();        // carrega a proxima pergunta
                            } else {          //se chegou ao fim
                                fimJogo();    //carrega a mensagem de fim
                            }
                         }
                    });
                    /* Para seguir para um novo quiz é realizado alguns procedimentos*/
                    rg = findViewById(R.id.radioGroup);
                    rg.clearCheck(); //aqui limpa a escolha do RADIOGROUP
                    respEscolhida = "nenhuma"; //aqui reseta a variavel de tentativas
                    builder.show(); //aqui mostra na tela a variável construida

        } else if (respEscolhida == "nenhuma" ){

            alertTitle = "ALGO DEU ERRADO!"; /*monta a mensagem de alerta errado*/

                erro = MediaPlayer.create(this, R.raw.erro01);
                erro.start();

                /*Constroi a mensagem de Acerto*/
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle(alertTitle);
                builder.setIcon(R.drawable.tenso);
                builder.setMessage("Você precisa escolher uma das respostas clicando na bolinha correspondente.");
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        /*Não faz nada qdo apertar o botão TENTAR NOVAMENTE*/
                    }
                });
                builder.show();

        }  else if ((respEscolhida != respCerta) && ( tentativas > 1)) {

            alertTitle = "QUE PENA ... \nVOCÊ ERROU";

                tentativas--; //diminui uma tentativa

                erro = MediaPlayer.create(this, R.raw.errado);
                erro.start();

                /*Constroi a mensagem de Acerto*/
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle(alertTitle);
                builder.setCancelable(false);
                builder.setIcon(R.drawable.nerd);

                builder.setMessage("Você pode tentar de novo!" +
                                "\n\nTentativas restantes: " + tentativas +
                                "\n\nPontuação: " + respostaCorretaContador);
                builder.setPositiveButton("TENTAR NOVAMENTE", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        /*Não faz nada qdo apertar o botão TENTAR NOVAMENTE*/
                    }
            });

                    /*aqui limpa a escolha do radionButtom*/
                    rg = findViewById(R.id.radioGroup);
                    rg.clearCheck();
                    respEscolhida = "nenhuma";
                    builder.show();
        } else  {

            alertTitle = "NÃO DEU DESSA VEZ";

                erro = MediaPlayer.create(this, R.raw.errado);
                erro.start();

                /*Constroi a mensagem de Acerto*/
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle(alertTitle);
                builder.setCancelable(false);
                builder.setIcon(R.drawable.sad);

                builder.setMessage("O jogo vai continuar ... " +
                                   "\n\nNão desanime!!" +
                                   "\n\nPontuação: " + respostaCorretaContador);
                builder.setPositiveButton("CONTINUAR", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (quizCount < end) {
                            quizCount++;
                            showNextQuiz();
                        } else {
                            fimJogo();
                        }
                    }
                });

                /*aqui limpa a escolha do radionButtom*/
                rg = findViewById(R.id.radioGroup);
                rg.clearCheck();
                respEscolhida = "nenhuma";
                builder.show();
            }
    }

    private void fimJogo(){
        super.finish();
        Intent intent = new Intent(this, Resultados.class);
        intent.putExtra("respostaCorretaContador", String.valueOf(respostaCorretaContador));
        intent.putExtra("end", String.valueOf(end));
        intent.putExtra("margem", String.valueOf(margem));
        ActivityOptionsCompat activityOptionsCompat = ActivityOptionsCompat.makeCustomAnimation(getApplicationContext(), R.anim.fade_in, R.anim.fade_out);
        ActivityCompat.startActivity(this,intent, activityOptionsCompat.toBundle());
    }

    // escuta se foi clicado o COMO JOGAR
    public void checkComoJogar(View view) {

        alertTitle = "COMO JOGAR";  /*monta a mensagem de como jogar*/

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(alertTitle);
        builder.setCancelable(false);
        builder.setIcon(R.drawable.professor);
        builder.setMessage("1 - Observe a figura. \n2 - Clique nos 3 botões amarelos para ouvir as opções." +
                "\n3 - Escolha qual você acha que é uma PERGUNTA e clique na bolinha correspondente." +
                "\n4 - Clique no botão verde \"Estou certo disso\" e confira se acertou." +
                "\n\n Objetivo: Acertar todas as questões");
        builder.setPositiveButton("VOLTAR", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        builder.show();
    }

    //volta ao MENU PRINCIPAL
    public void menu(View view) {
        super.finish();
        Intent intent = new Intent(this, MenuActivity.class);
        ActivityOptionsCompat activityOptionsCompat = ActivityOptionsCompat.makeCustomAnimation(getApplicationContext(), R.anim.fade_in, R.anim.fade_out);
        ActivityCompat.startActivity(this,intent, activityOptionsCompat.toBundle());
    }

    //escuta se foi clicado o botão sair
    public void sair(View view) {
        finish();
        System.exit(0);
    }

}

