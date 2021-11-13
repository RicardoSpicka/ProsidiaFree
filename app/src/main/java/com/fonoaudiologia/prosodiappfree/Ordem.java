package com.fonoaudiologia.prosodiappfree;

import androidx.appcompat.app.AppCompatActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.app.ActivityOptionsCompat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class Ordem extends AppCompatActivity {

    private int quizTot;                                  // quantidade total de perguntas
    private int quizCount = 1;                            // contador do numero de perguntas
//    Integer tentativas;                                   // quantidade de tentativas erradas por pergunta - é regressivo
    Integer margem;                                       // valor que vai servir de base para classificar a  pontuação     // seta a resposta escolhida como nenhuma para o caso de não clicar em nenhum botão do radiogrupo
    private CharSequence respCerta;                       // grava a respota certa
    private int respostaCorretaContador = 0;              // inicia o contador de quizes - que são as linhas da matriz
    int end;
    String alertTitle, respEscolhida;
    RadioGroup rg;
    ImageView img, tocar;
    TextView contador, acertos, resposta;
    MediaPlayer mp1, mp2, certo, erro;

    /*array list com os dados*/
    ArrayList<ArrayList<Object>> quizArray = new ArrayList<>();

        Object quizData[][]={ /*nessa MATRIZ cada linha é um  QUIZ
                            o primeiro ARRAY de cada linha é a figura*/

              //  imagem     //   afirmação  // ordem  //
            {R.drawable.onibus,   R.raw.onia, R.raw.onio},
            {R.drawable.coracao,  R.raw.cora, R.raw.coro},
            {R.drawable.domino,   R.raw.doma, R.raw.domo},
            {R.drawable.urubu,    R.raw.urua, R.raw.uruo},
            {R.drawable.jacare,   R.raw.jaca, R.raw.jaco},
//            {R.drawable.violao,   R.raw.vioa, R.raw.vioo},
//            {R.drawable.canguru,  R.raw.cana, R.raw.cano},
//            {R.drawable.abacaxi,  R.raw.abaa, R.raw.abao},
//            {R.drawable.cavalo,   R.raw.cava, R.raw.cavo},
//            {R.drawable.janela,   R.raw.jana, R.raw.jano},
//            {R.drawable.tomate,   R.raw.toma, R.raw.tomo},
//            {R.drawable.revolver, R.raw.reva, R.raw.revo},
//            {R.drawable.banana,   R.raw.bana, R.raw.bano},
//            {R.drawable.cachorro, R.raw.caoa, R.raw.caoo},
//            {R.drawable.martelo,  R.raw.mara, R.raw.maro},
//            {R.drawable.xicara,   R.raw.xica, R.raw.xico},
//            {R.drawable.medico,   R.raw.meda, R.raw.medo},
//            {R.drawable.lampada,  R.raw.lama, R.raw.lamo},
//            {R.drawable.brocolis, R.raw.broa, R.raw.broo},
//            {R.drawable.fosforo,  R.raw.fosa, R.raw.foso},
//            {R.drawable.oculos,   R.raw.ocua, R.raw.ocuo}

    };

        @Override
        protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.ordem);

        end = 4;
        quizTot = quizData.length;    /*verifica quantas linhas tem a matriz - cada linha é uma pergunta do quiz*/
        margem = end/3;   /*divide no numero de perguntas em 3 para classificar a resposta*/

        /*sorteia uma linha da matriz quizData para montar o primeiro quiz */
        for(int i=0; i < quizTot; i++) {
                ArrayList<Object> tmpArray = new ArrayList<>();
                tmpArray.add(quizData[i][0]);
                tmpArray.add(quizData[i][1]);
                tmpArray.add(quizData[i][2]);
                quizArray.add(tmpArray); // joga a nova array no "quizArray"
            }

        /*monta a proxima pergunta*/
        showNextQuiz();
    }

    public void showNextQuiz(){

        if (erro != null) erro.release(); //libera o mp3 caso ele não seja nulo
        if (certo != null) certo.release(); //libera o mp3 caso ele não seja nulo

//        tentativas = 1; //renova as 3 tentativas cada vez que começa um novo quiz
        /*esse bloca seta o contador de quizes e o numero de acertos*/
        contador = (TextView) findViewById(R.id.desafio);
        contador.setText("Pergunta " + quizCount + " de " + end);
        acertos = (TextView) findViewById(R.id.acertos);
        acertos.setText("Acertos: " + respostaCorretaContador);

        /*aqui escolhe aleatoriamente uma perguta da lista e seta a respota correta*/
        Random random = new Random();
        int randomNum = random.nextInt(quizArray.size()); /*aqui embaralha as linhas*/
        ArrayList<Object> quiz = quizArray.get(randomNum); /* aqui escolhe uma das linhas das perguntas e joga dentro de um array quiz*/


        resposta = (TextView) findViewById(R.id.resposta);


        /*aqui escolhe aleatoriamente uma das frases dentro da pergunta*/
        Random random2 = new Random();
        int randomNum2 = random.nextInt(2); //sorteia 0 ou 1
        if (randomNum2 == (0)) {
            mp1 = MediaPlayer.create(this, (Integer) quiz.get(1)); //se a aleatória 0 for escolhida toca a frase afirmação
            // i
            respCerta = "A"; //se a escolhida for a primiera declara a resposta como "A" (Afirmação)
        } else {
            mp1 = MediaPlayer.create(this, (Integer) quiz.get(2)); //se a aleatória 1 for escolhida toca a frase ordem
            respCerta = "O"; //se a escolhida for a primiera declara a resposta como "O" (Ordem)
        }

        /*carrega a imagem da pergunta*/
        img = (ImageView) findViewById(R.id.imagem);
        img.setImageResource((Integer) quiz.get(0));

        /*aqui apaga a pergunta e a resposta da array para poder embaralhar as opções*/
        quiz.remove(0); //remove a primeira indice da linha
        Collections.shuffle(quiz); //embaralha os 3 itens restantes

        /*aqui remove da matriz o pergunta que já foi usada*/
        quizArray.remove(randomNum); /*remove o numero aleatorio*/

        /*fica "ouvindo* para saber se o botão TOCAR foi apertado*/
        tocar = (ImageView) findViewById(R.id.toca);
        tocar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mp1.start(); //toca do som correspondente a frase escolhida
            }
        });

    }

    /*aqui verifica se a resposta escolhida está certa*/
    public void checkResposta(){

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

        }  else if (respEscolhida != respCerta) {

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
        builder.setMessage("1 - Observe a figura. \n2 - Clique no botão OUVIR para ouvir a frase." +
                "\n3 - Decida se a frase é uma AFIRMAÇÃO ou uma ORDEM." +
                "\n4 - Clique nos botões correspondentes abaixo e confira se acertou." +
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

    //escuta se foi clicado o botão afirmação
    public void afirmacao(View view) {
        respEscolhida = "A";   //seta a resposta escolhida como Afirmação
        checkResposta();
    }

    //escuta se foi clicado o botão ordem
    public void ordem(View view) {
        respEscolhida = "O";   //seta a resposta escolhida como Ordem
        checkResposta();
    }
}

