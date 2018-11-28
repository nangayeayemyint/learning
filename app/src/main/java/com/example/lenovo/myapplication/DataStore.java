package com.example.lenovo.myapplication;


import com.example.lenovo.myapplication.models.Answer;
import com.example.lenovo.myapplication.models.SInstruction;
import com.example.lenovo.myapplication.models.SLevel;
import com.example.lenovo.myapplication.models.Question;

import java.util.ArrayList;
import java.util.List;

public class DataStore {
    List<SLevel> SLevelList = new ArrayList<>();
    List<SInstruction> SInstructionList = new ArrayList<>();
    List<Question> questionList =new ArrayList<>();
    List<Answer> answerList1 = new ArrayList<>();
    List<Answer> answerList2 = new ArrayList<>();
    List<Answer> answerList3 = new ArrayList<>();
    public DataStore(){
        SLevel levels_1=new SLevel();
        levels_1.number = 1;
        levels_1.description = "Fruit";
        levels_1.expertness = 80;
        levels_1.locked = true;
        SLevelList.add(levels_1);

        SLevel levels_2=new SLevel();
        levels_2.number = 2;
        levels_2.description = "School";
        levels_2.expertness = 50;
        levels_2.locked = true;
        SLevelList.add(levels_2);

        SLevel levels_3=new SLevel();
        levels_3.number = 3;
        levels_3.description = "University";
        levels_3.expertness = 0;
        levels_3.locked = false;
        SLevelList.add(levels_3);

        SLevel levels_4=new SLevel();
        levels_4.number = 4;
        levels_4.description = "Business";
        levels_4.expertness = 0;
        levels_4.locked = false;
        SLevelList.add(levels_4);

        SLevel levels_5=new SLevel();
        levels_5.number = 5;
        levels_5.description = "Travel";
        levels_5.expertness = 0;
        levels_5.locked = false;
        SLevelList.add(levels_5);

        SInstruction SInstruction1 = new SInstruction();
        SInstruction1.image = R.drawable.apple;
        SInstruction1.description = "Apple";
        SInstruction1.statement = "I like apple.";
        SInstruction1.uri = R.raw.sound_apple;
        SInstructionList.add(SInstruction1);

        SInstruction SInstruction2 = new SInstruction();
        SInstruction2.image= R.drawable.avocado;
        SInstruction2.description = "Avocado";
        SInstruction2.statement = "Avocados are pear-shaped vegetables, with hard skins and large stones, which are usually eaten raw.";
        SInstruction2.uri = R.raw.sound_avocado;
        SInstructionList.add(SInstruction2);

        SInstruction SInstruction3 = new SInstruction();
        SInstruction3.image = R.drawable.banana;
        SInstruction3.description = "Banana";
        SInstruction3.statement = "The cotton and coffee plants are indigenous; banana plantations surround the villages.";
        SInstruction3.uri = R.raw.sound_banana;
        SInstructionList.add(SInstruction3);

        SInstruction SInstruction4 = new SInstruction();
        SInstruction4.image = R.drawable.blackberry;
        SInstruction4.description = "Blackberry";
        SInstruction4.statement = "To her right was a blackberry thicket laden with berries – mostly red, but some dark.";
        SInstruction4.uri = R.raw.sound_blackberry;
        SInstructionList.add(SInstruction4);

        SInstruction SInstruction5 = new SInstruction();
        SInstruction5.image = R.drawable.orange;
        SInstruction5.description = "Orange";
        SInstruction5.statement = "Oranges are a very good source of vitamins, especially vitamin C.";
        SInstruction5.uri = R.raw.sound_orange;
        SInstructionList.add(SInstruction5);

        SInstruction SInstruction6 = new SInstruction();
        SInstruction6.image = R.drawable.strawberry;
        SInstruction6.description = "Strawberry";
        SInstruction6.statement = "Strawberry is used to make juices, smoothies and ice cream.";
        SInstruction6.uri = R.raw.sound_strawberry;
        SInstructionList.add(SInstruction6);

        Answer answer1 = new Answer();
        answer1.questionId =1;
        answer1.text = "Apple";
        answer1.imageResourceId = R.drawable.apple;
        answer1.isCorrect = true;
        answerList1.add(answer1);

        Answer answer2 = new Answer();
        answer2.questionId = 1;
        answer2.text = "Orange";
        answer2.imageResourceId = R.drawable.orange;
        answer2.isCorrect = false;
        answerList1.add(answer2);

        Answer answer3 = new Answer();
        answer3.questionId =1;
        answer3.text = "Banana";
        answer3.imageResourceId = R.drawable.banana;
        answer3.isCorrect = false;
        answerList1.add(answer3);

        Answer answer4 = new Answer();
        answer4.questionId =2;
        answer4.text = "Avocado";
        answer4.imageResourceId = R.drawable.avocado;
        answer4.isCorrect = false;
        answerList2.add(answer4);

        Answer answer5 = new Answer();
        answer5.questionId = 2;
        answer5.text = "Orange";
        answer5.imageResourceId = R.drawable.orange;
        answer5.isCorrect = true;
        answerList2.add(answer5);

        Answer answer6 = new Answer();
        answer6.questionId =2;
        answer6.text = "Strawberry";
        answer6.imageResourceId = R.drawable.strawberry;
        answer6.isCorrect = false;
        answerList2.add(answer6);

        Answer answer7 = new Answer();
        answer7.questionId =3;
        answer7.text = "Blackberry";
        answer7.imageResourceId = R.drawable.blackberry;
        answer7.isCorrect = true;
        answerList3.add(answer7);

        Answer answer8 = new Answer();
        answer8.questionId = 2;
        answer8.text = "Apple";
        answer8.imageResourceId = R.drawable.apple;
        answer8.isCorrect = true;
        answerList3.add(answer8);

        Answer answer9 = new Answer();
        answer9.questionId =2;
        answer9.text = "Orange";
        answer9.imageResourceId = R.drawable.orange;
        answer9.isCorrect = false;
        answerList3.add(answer9);

        Question question1 =new Question();
        question1.text = "Choose correct photo of apple?";
        question1.imageResourceId = R.drawable.apple;
        question1.answerList = answerList1;
        questionList.add(question1);

        Question question2 = new Question();
        question2.text = "Choose correct photo of orange?";
        question2.imageResourceId = R.drawable.orange;
        question2.answerList = answerList2;
        questionList.add(question2);

        Question question3 = new Question();
        question3.text = "Choose correct photo of blackberry?";
        question3.imageResourceId = R.drawable.blackberry;
        question3.answerList = answerList3;
        questionList.add(question3);
    }

    public List getSLevelList(){
        return SLevelList;
    }

    public SLevel getLevel(int position){
        return SLevelList.get(position);
    }

    public SInstruction getInstruction(int intex){
        if(intex< SInstructionList.size()){
            return SInstructionList.get(intex);
        }
        return null;
    }

    public List getInstructionList(int intex) {
        return SInstructionList;
    }

    public Question getQuestion(int intex){
        if(intex<questionList.size()){
            return questionList.get(intex);
        }
        return null;
    }
}
