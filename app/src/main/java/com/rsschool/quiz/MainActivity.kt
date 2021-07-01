package com.rsschool.quiz

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment

class MainActivity : AppCompatActivity(), FragmentChanger {

    override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.activity_main)
            startQuiz()
    }

    override fun openQuestion(questionNumber: Int, answers: MutableList<Int>) {
        val fragment: Fragment = QuestionFragment.newInstance(questionNumber, answers)
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.container, fragment).commit()
    }

    override fun openResult(answers: MutableList<Int>) {
        val fragment: Fragment = ResultFragment.newInstance(answers)
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.container, fragment).commit()
    }

    override fun startQuiz() {
        val list = mutableListOf<Int>()
        for (i in 1..5)
            list.add(0)
        openQuestion(1, list)
    }

    override fun close() {
        finish()
    }
}