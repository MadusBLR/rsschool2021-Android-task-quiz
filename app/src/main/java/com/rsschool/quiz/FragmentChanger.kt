package com.rsschool.quiz

interface FragmentChanger {
        fun openQuestion(questionNumber: Int, answers: MutableList<Int>)
        fun openResult(answers: MutableList<Int>)
        fun startQuiz()
        fun close()
}