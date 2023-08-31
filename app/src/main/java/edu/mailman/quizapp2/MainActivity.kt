package edu.mailman.quizapp2

import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var tvQuestion: TextView
    private lateinit var ivFlag: ImageView
    private lateinit var pbProgress: ProgressBar
    private lateinit var tvProgress: TextView
    private lateinit var btnAnsA: Button
    private lateinit var btnAnsB: Button
    private lateinit var btnAnsC: Button
    private lateinit var btnAnsD: Button
    private lateinit var btnSubmit: Button

    private var guess = 0
    private var answer = 0
    private var correctAnswers = 0
    private var currentPosition = 1

    private lateinit var questionList: ArrayList<Question>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tvQuestion = findViewById(R.id.tv_question)
        ivFlag = findViewById(R.id.iv_flag)
        pbProgress = findViewById(R.id.pb_progress)
        tvProgress = findViewById(R.id.tv_progress)
        btnAnsA = findViewById(R.id.btn_ans_a)
        btnAnsB = findViewById(R.id.btn_ans_b)
        btnAnsC = findViewById(R.id.btn_ans_c)
        btnAnsD = findViewById(R.id.btn_ans_d)
        btnSubmit = findViewById(R.id.btn_submit)

        // Set the same event handler for all the buttons
        btnAnsA.setOnClickListener(this)
        btnAnsB.setOnClickListener(this)
        btnAnsC.setOnClickListener(this)
        btnAnsD.setOnClickListener(this)
        btnSubmit.setOnClickListener(this)

        // Load all the questions into an array list
        questionList = Constants.getQuestions()

        // Initialize the progress bar and progress text view
        pbProgress.progress = 1
        pbProgress.max = questionList.size
        tvProgress.text = getString(R.string.x_of_y, 1, pbProgress.max)

        setQuestion()
    }

    override fun onClick(view: View?) {
        // Which button was clicked?
        when (view?.id) {
            btnAnsA.id -> {
                if (guess == 0) {
                    btnAnsA.setBackgroundColor(Color.RED)
                    btnAnsA.typeface = Typeface.DEFAULT_BOLD
                    guess = 1
                    answerView()
                }
            }
            btnAnsB.id -> {
                if (guess == 0) {
                    btnAnsB.setBackgroundColor(Color.RED)
                    btnAnsB.typeface = Typeface.DEFAULT_BOLD
                    guess = 2
                    answerView()
                }
            }
            btnAnsC.id -> {
                if (guess == 0) {
                    btnAnsC.setBackgroundColor(Color.RED)
                    btnAnsC.typeface = Typeface.DEFAULT_BOLD
                    guess = 3
                    answerView()
                }
            }
            btnAnsD.id -> {
                if (guess == 0) {
                    btnAnsD.setBackgroundColor(Color.RED)
                    btnAnsD.typeface = Typeface.DEFAULT_BOLD
                    guess = 4
                    answerView()
                }
            }
            btnSubmit.id -> {
                guess = 0
                currentPosition ++
                if (currentPosition <= questionList.size) {
                    setQuestion()
                } else {
                    // This is the last question.
                    // Pass the information to the results activity and show it.
                    val intent =
                        Intent(this, ResultActivity::class.java)
                    intent.putExtra(Constants.USER_NAME,
                        "Player")
                    intent.putExtra(Constants.CORRECT_ANSWERS,
                        correctAnswers)
                    intent.putExtra(Constants.TOTAL_QUESTIONS,
                        questionList.size)
                    startActivity(intent)
                    finish()
                }
            }
        }
    }

    private fun defaultOptionsView() {
        // Restore all the options to default
        val options = ArrayList<Button>()

        options.add(btnAnsA)
        options.add(btnAnsB)
        options.add(btnAnsC)
        options.add(btnAnsD)

        for (option in options) {
            option.setBackgroundColor(Color.WHITE)
            option.setTextColor(Color.BLACK)
            option.typeface = Typeface.DEFAULT
        }
    }

    private fun answerView() {
        // Show the correct answer in green
        when (answer) {
            1 -> {
                btnAnsA.setBackgroundColor(Color.GREEN)
            }
            2 -> {
                btnAnsB.setBackgroundColor(Color.GREEN)
            }
            3 -> {
                btnAnsC.setBackgroundColor(Color.GREEN)
            }
            4 -> {
                btnAnsD.setBackgroundColor(Color.GREEN)
            }
        }

        // Show message for correct or incorrect guess
        if (guess == answer) {
            Toast.makeText(applicationContext,
                "Correct Answer", Toast.LENGTH_SHORT).show()
            correctAnswers ++
            Log.i("eric", correctAnswers.toString())
        } else {
            Toast.makeText(applicationContext,
                "Incorrect Answer", Toast.LENGTH_SHORT).show()
        }
    }

    private fun setQuestion() {
        // Clear previous choices
        defaultOptionsView()

        // Get the next question
        val question = questionList[currentPosition - 1]

        // Update info for this question
        ivFlag.setImageResource(question.image)
        pbProgress.progress = currentPosition
        tvProgress.text =
            getString(R.string.x_of_y, currentPosition, pbProgress.max)
        tvQuestion.text = question.question
        btnAnsA.text = question.option1
        btnAnsB.text = question.option2
        btnAnsC.text = question.option3
        btnAnsD.text = question.option4
        answer = question.correctAnswer
    }
}


