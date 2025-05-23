package com.example.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapplication.ui.theme.MyApplicationTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyApplicationTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Column(modifier = Modifier.padding(innerPadding)) {
                        QuizScreenFull()
                    }
                }
            }
        }
    }
}

@Composable
fun QuizScreenFull() {
    val questionsArray = stringArrayResource(id = R.array.quiz_questions)
    val answers = listOf(true, true, false)
    val questions = questionsArray.mapIndexed { index, question -> question to answers[index] }
    var currentIndex by remember { mutableStateOf(0) }
    var showResult by remember { mutableStateOf(false) }
    var isCorrect by remember { mutableStateOf(false) }
    var correctCount by remember { mutableStateOf(0) }
    var wrongCount by remember { mutableStateOf(0) }
    var showFinalScore by remember { mutableStateOf(false) }
    val isLastQuestion = currentIndex == questions.lastIndex
    val currentQuestion = questions.getOrNull(currentIndex)

    if (showFinalScore) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.chatbot),
                contentDescription = stringResource(id = R.string.chatbot_icon),
                modifier = Modifier
                    .size(100.dp)
                    .padding(bottom = 16.dp)
            )

            Text(
                text = stringResource(id = R.string.quiz_finished),
                style = MaterialTheme.typography.headlineMedium
            )

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = stringResource(id = R.string.correct_answers, correctCount),
                fontSize = 20.sp
            )
            Text(
                text = stringResource(id = R.string.wrong_answers, wrongCount),
                fontSize = 20.sp
            )

            Spacer(modifier = Modifier.height(24.dp))

            Button(onClick = {
                currentIndex = 0
                correctCount = 0
                wrongCount = 0
                showFinalScore = false
                showResult = false
            }) {
                Text(stringResource(id = R.string.try_again))
            }
        }
    } else {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = currentQuestion?.first ?: "",
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier
                    .padding(top = 40.dp)
                    .fillMaxWidth()
            )

            Box(
                modifier = Modifier
                    .height(180.dp)
                    .fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                if (showResult) {
                    Box(
                        modifier = Modifier
                            .size(180.dp)
                            .background(
                                if (isCorrect) Color.Green else Color.Red,
                                shape = CircleShape
                            )
                    ) {
                        Image(
                            painter = painterResource(id = if (isCorrect) R.drawable.correct_answer else R.drawable.wrong_answer),
                            contentDescription = if (isCorrect) stringResource(id = R.string.correct_answer) else stringResource(id = R.string.wrong_answer),
                            modifier = Modifier
                                .size(180.dp)
                                .align(Alignment.Center)
                        )
                        Box(
                            modifier = Modifier
                                .size(180.dp)
                                .align(Alignment.Center),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = if (isCorrect) stringResource(id = R.string.correct_answer) else stringResource(id = R.string.wrong_answer),
                                color = Color.Black,
                                fontSize = 18.sp
                            )
                        }
                    }
                }
            }
            if (!showResult) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 12.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Button(
                        onClick = {
                            isCorrect = currentQuestion?.second == true
                            showResult = true
                            if (isCorrect) correctCount++ else wrongCount++
                        },
                        modifier = Modifier
                            .weight(1f)
                            .padding(end = 8.dp)
                    ) {
                        Text(stringResource(id = R.string.true_button))
                    }
                    Button(
                        onClick = {
                            isCorrect = currentQuestion?.second == false
                            showResult = true
                            if (isCorrect) correctCount++ else wrongCount++
                        },
                        modifier = Modifier
                            .weight(1f)
                            .padding(start = 8.dp)
                    ) {
                        Text(stringResource(id = R.string.false_button))
                    }
                }
            }
            if (showResult) {
                Button(
                    onClick = {
                        if (isLastQuestion) {
                            showFinalScore = true
                        } else {
                            currentIndex++
                            showResult = false
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp)
                        .padding(bottom = 20.dp)
                ) {
                    Text(if (isLastQuestion) stringResource(id = R.string.see_score) else stringResource(id = R.string.next_question))
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewQuestionText1() {
    val questionsArray = stringArrayResource(id = R.array.quiz_questions)
    Text(
        text = questionsArray[0],
        style = MaterialTheme.typography.headlineMedium,
        modifier = Modifier
            .padding(top = 40.dp)
            .fillMaxWidth()
    )
}

@Preview(showBackground = true)
@Composable
fun PreviewQuestionText2() {
    val questionsArray = stringArrayResource(id = R.array.quiz_questions)
    Text(
        text = questionsArray[1],
        style = MaterialTheme.typography.headlineMedium,
        modifier = Modifier
            .padding(top = 40.dp)
            .fillMaxWidth()
    )
}

@Preview(showBackground = true)
@Composable
fun PreviewAnswerCircleCorrect() {
    Box(
        modifier = Modifier
            .size(180.dp)
            .background(Color.Green, shape = CircleShape),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "Correct Answer",
            color = Color.Black,
            fontSize = 18.sp
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewAnswerCircleWrong() {
    Box(
        modifier = Modifier
            .size(180.dp)
            .background(Color.Red, shape = CircleShape),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "Wrong Answer",
            color = Color.Black,
            fontSize = 18.sp
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewTrueFalseButtons() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(24.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Button(
            onClick = {},
            modifier = Modifier
                .weight(1f)
                .padding(end = 8.dp)
        ) {
            Text("True")
        }

        Button(
            onClick = {},
            modifier = Modifier
                .weight(1f)
                .padding(start = 8.dp)
        ) {
            Text("False")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewNextQuestionButton() {
    Surface {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Button(
                onClick = {},
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
            ) {
                Text("Next Question")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewFinalScoreBox() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Quiz Finished!",
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.height(24.dp))

        Text("Correct Answers: 2", fontSize = 20.sp)
        Text("Wrong Answers: 1", fontSize = 20.sp)

        Spacer(modifier = Modifier.height(24.dp))

        Button(onClick = {}) {
            Text("Play Again")
        }
    }
}