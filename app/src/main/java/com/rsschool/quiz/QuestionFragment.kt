
package com.rsschool.quiz

import android.R
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import com.rsschool.quiz.databinding.FragmentQuizBinding

class QuestionFragment : Fragment() {

    private var changer: FragmentChanger? = null
    private var _binding: FragmentQuizBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentQuizBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        changer = context as FragmentChanger
    }

    private fun setDataToFragment(questionNumber: Int, answers: MutableList<Int>) {
        binding.toolbar.title = "Question №$questionNumber"
        if (questionNumber == 1) {
            binding.toolbar.navigationIcon = null
            binding.previousButton.isEnabled = false
        }
        else if (questionNumber == 5) {
            binding.nextButton.text = "Submit"
        }

        binding.question.text = Questions.questions[questionNumber - 1].question
        binding.optionOne.text = Questions.questions[questionNumber - 1].answerOptions[0]
        binding.optionTwo.text = Questions.questions[questionNumber - 1].answerOptions[1]
        binding.optionThree.text = Questions.questions[questionNumber - 1].answerOptions[2]
        binding.optionFour.text = Questions.questions[questionNumber - 1].answerOptions[3]
        binding.optionFive.text = Questions.questions[questionNumber - 1].answerOptions[4]

        when (answers[questionNumber - 1]) {
            1 -> binding.optionOne.isChecked = true
            2 -> binding.optionTwo.isChecked = true
            3 -> binding.optionThree.isChecked = true
            4 -> binding.optionFour.isChecked = true
            5 -> binding.optionFive.isChecked = true
            else -> binding.nextButton.isEnabled = false
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val questionNumber = arguments?.getInt(QUESTION_NUMBER)!!
        val answers = arguments?.get(ANSWERS) as MutableList<Int>

        setDataToFragment(questionNumber, answers)

        binding.radioGroup.setOnCheckedChangeListener { _, checkedID ->
            if (checkedID != -1) {
                binding.nextButton.isEnabled = true
                val radioButton = resources.getResourceEntryName(binding.radioGroup.checkedRadioButtonId)
                answers[questionNumber - 1] =
                when (radioButton) {
                    "option_one" -> 1
                    "option_two" -> 2
                    "option_three" -> 3
                    "option_four" -> 4
                    "option_five" -> 5
                    else -> 0
                }
            }
        }

        fun switchQuestion(next: Int) {
            changer?.openQuestion(questionNumber + next, answers)
            context?.theme?.applyStyle(Questions.questions[questionNumber - 1 + next].theme, true)
        }

        binding.nextButton.setOnClickListener {
            if (questionNumber != answers.size) {
                switchQuestion(1)
            } else {
                changer?.openResult(answers)
            }
        }

        binding.toolbar.setNavigationOnClickListener {
            switchQuestion(-1)
        }
        binding.previousButton.setOnClickListener {
            switchQuestion(-1)
        }


        activity?.onBackPressedDispatcher?.addCallback(object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if (questionNumber != 1)
                    switchQuestion(-1)
                }
        })
    }



    companion object {
        @JvmStatic
        fun newInstance(questionNumber: Int, answers: MutableList<Int>) =
            QuestionFragment().apply {
                arguments = bundleOf(
                    QUESTION_NUMBER to questionNumber,
                    ANSWERS to answers
                )
            }

        private const val QUESTION_NUMBER = "questionNumber"
        private const val ANSWERS = "answers"
    }
}
