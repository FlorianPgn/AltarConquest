package com.example.florian.altarconquest.Model;

public class Question {

    private String reponse1, reponse2, reponse3, reponse4;
    private String intitule;
    private int id;
    public String[][] lesQuestions = {{"idReponse", "Intitulé", "Reponse1", "Reponse2", "Reponse3", "Reponse4"},
            {"3", "On appelle arbre un végétal mesurant au moins :", "4m", "6m", "7m", "10m"},
            {"1", "L’arbre se nourrit :", "D'eau", "De nitrates", "De terre", "De sucre"},
            {"2", "La photosynthèse est une fonction assurée par :", "Le tronc", "Les feuilles", "Les racines", "Les branches"},
            {"3", "Pendant la photosynthèse, l’arbre absorbe du dioxyde de carbone et rejette :",
                    "De l'azote", "Du dioxygène", "De l'oxygène", "Du nitrate"},
            {"3", "L’eau des arbres s’évapore par :", "Les racines", "Le tronc", "Les feuilles", "Les branches"},
            {"4", "Quel âge peuvent atteindre les pins de bristlecone, les plus vieux arbres au monde?"
                    , "100 ans", "500 ans", "1000 ans", "5000 ans"},
            {"1", "Le record du plus grand arbre au monde (135 m) est détenu par un :"
                    , "Eucalyptus", "Séquoia", "Baobab", "Sapin"},
            {"4", "De l’intérieur vers l’extérieur, le tronc d’un arbre est formé de :"
                    , "Aubier, écorce, coeur", "Coeur, écorce, aubier", "Écorce, coeur, aubier", "Coeur, aubier, écorce"},
            {"2", "Comment appelle t on l’ensemble des branches d’un arbre?"
                    , "Le tronc", "Le houppier", "L'écorce", "La tige"},
            {"1", "Combien existe-il de sèves différentes?", "2", "3", "4", "5"},
            {"1", "La forêt des landes est peuplé de :", "Pins", "Sapins", "Baobabs", "Chênes"},
            {"4", "Les plus gros arbres sont les :", "Sapins", "Marroniers", "Séquoia", "Baobabs"},
            {"3", "Les arbres dit “feuillus” sont appelés ainsi car :"
                    , "Ils ont beaucoup\nde feuilles", "Ils n'ont pas\nde feuilles"
                    , "Ils perdent\nleurs feuilles", "Ils ne perdent\njamais leurs feuilles"},
            {"2", "Qu’est ce qui peut être un danger pour un arbre?"
                    , "Les vaches", "Le gui", "Le lierre", "Les vignes"},
            {"3", "Comment peut t on calculer l’âge d’un arbre?"
                    , "Grâce à sa hauteur", "Grâce à son épaisseur"
                    , "Grâce aux nombres\nde cercles dans\nle tronc", "On ne peut pas"},
            {"1", "La première étape du cycle de vie d’un arbre se nomme :"
                    , "Germination", "Naissance", "Plantation", "Enracinement"},
            {"4", "Un arbre se reproduit grâce à :", "Ses feuilles", "Ses branches", "Son tronc", "Ses graines"},
            {"2", "Un arbre peut :", "Se déplacer", "Tomber malade", "Se cacher sous terre", "Mourir volontairement"}};

    public Question(int id) {
        this.id = id;
        this.intitule = lesQuestions[this.id][1];
        this.reponse1 = lesQuestions[this.id][2];
        this.reponse2 = lesQuestions[this.id][3];
        this.reponse3 = lesQuestions[this.id][4];
        this.reponse4 = lesQuestions[this.id][5];

    }

    public String getBonneReponse() {
        return lesQuestions[this.id][Integer.parseInt(lesQuestions[this.id][0]) + 1];
    }

    public String getReponse1() {
        return reponse1;
    }

    public String getReponse2() {
        return reponse2;
    }

    public String getReponse3() {
        return reponse3;
    }

    public String getReponse4() {
        return reponse4;
    }


    public String getIntitule() {
        return intitule;
    }

}