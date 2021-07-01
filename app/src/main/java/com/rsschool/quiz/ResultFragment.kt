package com.rsschool.quiz

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.core.os.bundleOf
import com.rsschool.quiz.databinding.FragmentResultBinding

class ResultFragment : Fragment() {

    private var launcher: FragmentChanger? = null

    private var _binding: FragmentResultBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentResultBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        launcher = context as FragmentChanger
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val answers = arguments?.get(ANSWERS_TAG) as MutableList<Int>
        var result = 0
        for (i in answers.indices) {
            if (Questions.questions[i].rightIndex == answers[i])
                result += 1
        }

        binding.result.text = "Result: $result/${answers.size}"


        binding.buttonShare.setOnClickListener {
            var text = "Your result: $result/${answers.size}\n\n"
            for (i in answers.indices) {
                text += "${i + 1}) ${Questions.questions[i].question}\n" +
                        "Your answer: ${Questions.questions[i].answerOptions[answers[i] - 1]}\n\n"
            }
            val sendIntent: Intent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_SUBJECT, "Quiz results")
                putExtra(Intent.EXTRA_TEXT, text)
                type = "text/plain"
            }
            val shareIntent = Intent.createChooser(sendIntent, null)
            startActivity(shareIntent)
        }

        binding.buttonRestart.setOnClickListener {
            launcher?.startQuiz()
            context?.theme?.applyStyle(Questions.questions[0].theme, true)
        }

        activity?.onBackPressedDispatcher?.addCallback(object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                launcher?.startQuiz()
                context?.theme?.applyStyle(Questions.questions[0].theme, true)
            }
        })

        binding.buttonExit.setOnClickListener {
            launcher?.close()
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(answers: MutableList<Int>) =
            ResultFragment().apply {
                arguments = bundleOf(
                    ANSWERS_TAG to answers
                )
            }

        private const val ANSWERS_TAG = "answers"
    }
}