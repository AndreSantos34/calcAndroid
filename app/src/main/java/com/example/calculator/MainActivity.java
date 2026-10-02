package com.example.calculator;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    TextView screen;
    TextView expressionScreen;

    double firstNumber = 0;
    String operator = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        screen = findViewById(R.id.screen);
        expressionScreen = findViewById(R.id.expressionScreen);

        ViewCompat.setOnApplyWindowInsetsListener(
                findViewById(R.id.main),
                (v, insets) -> {

                    Insets systemBars =
                            insets.getInsets(
                                    WindowInsetsCompat.Type.systemBars()
                            );

                    v.setPadding(
                            systemBars.left,
                            systemBars.top,
                            systemBars.right,
                            systemBars.bottom
                    );

                    return insets;
                }
        );
    }

    // =========================
    // ENTRADA DOS NÚMEROS
    // =========================

    public void inputNum(View view) {

        if (expressionScreen.getText().toString().contains("=")) {
            expressionScreen.setText("");
        }

        String number = screen.getText().toString();
        int id = view.getId();
        String digit = "";

        if (id == R.id.zeroBTN) {
            digit = "0";
        } else if (id == R.id.oneBTN) {
            digit = "1";
        } else if (id == R.id.twoBTN) {
            digit = "2";
        } else if (id == R.id.threeBTN) {
            digit = "3";
        } else if (id == R.id.fourBTN) {
            digit = "4";
        } else if (id == R.id.fiveBTN) {
            digit = "5";
        } else if (id == R.id.sixBTN) {
            digit = "6";
        } else if (id == R.id.sevenBTN) {
            digit = "7";
        } else if (id == R.id.eightBTN) {
            digit = "8";
        } else if (id == R.id.nineBTN) {
            digit = "9";
        }

        if (!digit.isEmpty()) {
            if (number.equals("0")) {
                number = digit;
            } else {
                number += digit;
            }
        } else if (id == R.id.decimalBTN) {

            if (!number.contains(".")) {
                number += ".";
            }

        } else if (id == R.id.plusminusBTN) {

            if (!number.equals("0")) {

                if (number.startsWith("-")) {
                    number = number.substring(1);
                } else {
                    number = "-" + number;
                }
            }

        }

        screen.setText(number);
    }


    // =========================
    // OPERAÇÕES
    // =========================

    public void operation(View view) {

        try {

            firstNumber = Double.parseDouble(
                    screen.getText().toString()
            );

            int id = view.getId();

            if (id == R.id.plusBTN) {
                operator = "+";
            } else if (id == R.id.minusBTN) {
                operator = "-";
            } else if (id == R.id.multiplyBTN) {
                operator = "*";
            } else if (id == R.id.divideBTN) {
                operator = "/";
            } else if (id == R.id.expBTN) {
                operator = "^";
            }

            String firstStr = (firstNumber == (long) firstNumber) ? String.valueOf((long) firstNumber) : String.valueOf(firstNumber);
            expressionScreen.setText(firstStr + " " + operator);

            // Limpa a tela para digitar o segundo número
            screen.setText("0");

        } catch (NumberFormatException e) {

            screen.setText("Erro");
        }
    }


    // =========================
    // CALCULAR
    // =========================

    public void calculate(View view) {

        try {

            double secondNumber = Double.parseDouble(
                    screen.getText().toString()
            );

            double result = 0;

            switch (operator) {

                case "+":
                    result = firstNumber + secondNumber;
                    break;

                case "-":
                    result = firstNumber - secondNumber;
                    break;

                case "*":
                    result = firstNumber * secondNumber;
                    break;

                case "/":

                    if (secondNumber == 0) {
                        screen.setText("Erro");
                        return;
                    }

                    result = firstNumber / secondNumber;
                    break;

                case "^":
                    result = Math.pow(firstNumber, secondNumber);
                    break;

                default:
                    return;
            }

            String firstStr = (firstNumber == (long) firstNumber) ? String.valueOf((long) firstNumber) : String.valueOf(firstNumber);
            String secondStr = (secondNumber == (long) secondNumber) ? String.valueOf((long) secondNumber) : String.valueOf(secondNumber);
            expressionScreen.setText(firstStr + " " + operator + " " + secondStr + " =");

            // Remove o .0 de resultados inteiros
            if (result == (long) result) {

                screen.setText(
                        String.valueOf((long) result)
                );

            } else {

                screen.setText(
                        String.valueOf(result)
                );
            }

            operator = "";

        } catch (NumberFormatException e) {

            screen.setText("Erro");
        }
    }


    // =========================
    // LIMPAR CALCULADORA
    // =========================

    public void clear(View view) {

        screen.setText("0");
        expressionScreen.setText("");

        firstNumber = 0;
        operator = "";
    }
}
