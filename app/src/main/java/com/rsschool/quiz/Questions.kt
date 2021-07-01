package com.rsschool.quiz

class Questions {
    class Question(val question: String, val answerOptions: Array<String>, val rightIndex: Int, val theme: Int)
    companion object {
        val questions = arrayOf(
         Question("What is the capital of Belarus?",
             arrayOf("Paris","Brest","Minsk","Moscow","Gomel"),3,R.style.Theme_Quiz_First),
         Question("What is Kotlin?",arrayOf("Animal","Programming language","OS","Beer","IDE"),2,R.style.Theme_Quiz_Second),
         Question("What is Android Studio?",arrayOf("Android Application","Recording studio","Database","Food","IDE"),5,R.style.Theme_Quiz_Third),
         Question("What is not a programming language?",arrayOf("Java","C++","C#","HTML","Kotlin"),4,R.style.Theme_Quiz_Fourth),
         Question("How many programming languages exist?",arrayOf("About 700","117","256","About 300","More than 1000"),1,R.style.Theme_Quiz_Fifth))
    }
}