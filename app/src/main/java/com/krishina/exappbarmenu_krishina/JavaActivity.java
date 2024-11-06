package com.krishina.exappbarmenu_krishina;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

//https://developer.android.com/guide/topics/resources/string-resource#StringArray

public class JavaActivity extends AppCompatActivity {

    private RadioGroup radioGroup;
    private RadioButton radioButton0;
    private RadioButton radioButton1;
    private RadioButton radioButton2;
    private RadioButton radioButton3;
    private TextView textViewPergunta, textViewExplicacao;
    private Button buttonResponder,buttonProximaPergunta;
    private String[] perguntas;
    private String[][] opcoes;
    private String[] explicacoes;
    private int[] respostasCorretas;
    private int index;
   private int acertos, idBotaoSelecionado;
    private RadioButton botaoSelecionado;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_java);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        radioGroup = findViewById(R.id.radioGroup);
        radioButton0 = findViewById(R.id.radioButton0);
        radioButton1 = findViewById(R.id.radioButton1);
        radioButton2 = findViewById(R.id.radioButton2);
        radioButton3 = findViewById(R.id.radioButton3);
        textViewPergunta = findViewById(R.id.textViewPergunta);
        textViewExplicacao = findViewById(R.id.textViewExplicacao);
        textViewExplicacao.setVisibility(View.GONE);
        buttonResponder = findViewById(R.id.buttonResponder);
        buttonProximaPergunta = findViewById(R.id.buttonProximaPergunta);
        perguntas = getResources().getStringArray(R.array.perguntasJava);
        explicacoes = getResources().getStringArray(R.array.explicacaoJava);
        respostasCorretas = getResources().getIntArray(R.array.respostaCorretaJava);

        opcoes = new String[][]{
                getResources().getStringArray(R.array.opcao0Java),
                getResources().getStringArray(R.array.opcao1Java),
                getResources().getStringArray(R.array.opcao2Java),
        };

        mostrarPerguntas(0);
        buttonProximaPergunta.setEnabled(false);

        buttonResponder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                idBotaoSelecionado = radioGroup.getCheckedRadioButtonId();
                botaoSelecionado = findViewById(idBotaoSelecionado);
                int indexRespostaSelecionada = radioGroup.indexOfChild(botaoSelecionado);


                //https://developer.android.com/reference/androidx/core/content/ContextCompat
                //textViewExplicacao.setBackgroundColor(getResources().getColor(R.color.acertoCor));

                if (idBotaoSelecionado != -1) {
                    VerificaAcerto verificaAcerto= new VerificaAcerto();
                    acertos += verificaAcerto.verificarAcerto(indexRespostaSelecionada,respostasCorretas[index]);

                    //muda a cor da resposta de acordo com o erro ou acerto
                    if(verificaAcerto.verificarAcerto(indexRespostaSelecionada,respostasCorretas[index]) == 1){
                        textViewExplicacao.setBackgroundColor(ContextCompat.getColor(JavaActivity.this,R.color.acertoCor));
                    } else {
                        textViewExplicacao.setBackgroundColor(ContextCompat.getColor(JavaActivity.this,R.color.erroCor));
                    }

                    textViewExplicacao.setVisibility(View.VISIBLE);
                    //System.out.println(acertos);
                    mostrarExplicacao(index);
                    buttonProximaPergunta.setEnabled(true);
                    buttonResponder.setEnabled(false);
                }

            }
        });

        buttonProximaPergunta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                index++;
                if (index<perguntas.length){
                    radioGroup.clearCheck();
                    textViewExplicacao.setVisibility(View.GONE);
                    mostrarPerguntas(index);
                    buttonResponder.setEnabled(true);
                    buttonProximaPergunta.setEnabled(false);
                } else {
                    mostrarResultado();
                }
            }
        });
    }

    private void mostrarPerguntas(int index) {
        String questao = perguntas[index];

            textViewPergunta.setText(questao);
            radioButton0.setText(opcoes[index][0]);
            radioButton1.setText(opcoes[index][1]);
            radioButton2.setText(opcoes[index][2]);
            radioButton3.setText(opcoes[index][3]);
    }

    private void mostrarResultado() {

        textViewPergunta.setBackgroundColor(ContextCompat.getColor(JavaActivity.this,R.color.acertoCor));
        textViewPergunta.setText("FIM DE SIMULADO\n\nQuantidade de acertos: " + acertos);
        radioGroup.setVisibility(View.GONE);
        textViewExplicacao.setVisibility(View.GONE);
        buttonProximaPergunta.setEnabled(false);
        buttonResponder.setEnabled(false);
    }

    private void mostrarExplicacao(int index) {
        String explicacao = explicacoes[index];
        textViewExplicacao.setText(explicacao);
    }
}