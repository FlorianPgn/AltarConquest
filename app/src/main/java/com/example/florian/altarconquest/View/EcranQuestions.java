package com.example.florian.altarconquest.View;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.florian.altarconquest.Model.Question;
import com.example.florian.altarconquest.R;

public class EcranQuestions extends AppCompatActivity {

    TextView intitule;
    Button reponse1, reponse2, reponse3, reponse4;
    Question question;
    int nbReponses, idQuestion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ecran_questions);

        intitule = (TextView) findViewById(R.id.intituleQuestion);

        reponse1 = (Button) findViewById(R.id.reponseA);
        reponse2 = (Button) findViewById(R.id.reponseB);
        reponse3 = (Button) findViewById(R.id.reponseC);
        reponse4 = (Button) findViewById(R.id.reponseD);

        Intent intent = getIntent();
        idQuestion = intent.getIntExtra("Questions", 0);
        nbReponses = intent.getIntExtra("NbReponses", 0);
        Log.i("zr", "onCreate: " + nbReponses);
        System.out.println(idQuestion);

        question = new Question(idQuestion);

        intitule.setText(question.getIntitule());
        reponse1.setText(question.getReponse1());
        reponse2.setText(question.getReponse2());
        reponse3.setText(question.getReponse3());
        reponse4.setText(question.getReponse4());

        reponse1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (verifierReponse(question.getReponse1()))
                    suivant();
            }
        });

        reponse2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (verifierReponse(question.getReponse2()))
                    suivant();
            }
        });

        reponse3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (verifierReponse(question.getReponse3()))
                    suivant();
            }
        });

        reponse4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (verifierReponse(question.getReponse4()))
                    suivant();
            }
        });


    }

    private void suivant() {
        if (nbReponses<3){
            Intent intent = new Intent(this, EcranQuestions.class);
            intent.putExtra("Questions", idQuestion++);
            intent.putExtra("NbReponses", nbReponses++);
            startActivity(intent);
            finish();
        }
        else
            quitter();

    }

    private void quitter() {
        finish();
    }

    public boolean verifierReponse(String reponse) {
        return question.getBonneReponse().equals(reponse);
    }


}
