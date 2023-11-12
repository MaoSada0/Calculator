package main;

import javafx.animation.ScaleTransition;
import javafx.animation.SequentialTransition;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Duration;

import static java.lang.Math.*;

public class MainWindowController {

    @FXML private Pane titlePane;
    @FXML private ImageView btnClose;
    @FXML private ImageView btnMinimize;
    @FXML private Label lblResult;

    private double x;
    private double y;
    private double num1 = 0;
    private String operator = "+";
    private boolean isNewInput = true;

    public void init(Stage stage) {
        titlePane.setOnMousePressed(mouseEvent -> {
            x = mouseEvent.getSceneX();
            y = mouseEvent.getSceneY();
        });

        titlePane.setOnMouseDragged(mouseEvent -> {
            stage.setX(mouseEvent.getScreenX() - x);
            stage.setY(mouseEvent.getScreenY() - y);
        });

        btnClose.setOnMouseClicked(mouseEvent -> stage.close());
        btnMinimize.setOnMouseClicked(mouseEvent -> stage.setIconified(true));
    }

    @FXML
    void onNumberClicked(MouseEvent event) {

        Pane clickedPane = (Pane) event.getSource();
        String value = ((Pane) event.getSource()).getId().replace("btn", "");
        ScaleTransition scaleTransition = new ScaleTransition(Duration.seconds(0.1), clickedPane);
        scaleTransition.setFromX(1.0);
        scaleTransition.setFromY(1.0);
        scaleTransition.setToX(0.9);
        scaleTransition.setToY(0.9);
        ScaleTransition scaleBackTransition = new ScaleTransition(Duration.seconds(0.1), clickedPane);
        scaleBackTransition.setFromX(0.9);
        scaleBackTransition.setFromY(0.9);
        scaleBackTransition.setToX(1.0);
        scaleBackTransition.setToY(1.0);
        SequentialTransition seqTransition = new SequentialTransition(scaleTransition, scaleBackTransition);
        seqTransition.play();

        if (isNewInput) {
            lblResult.setText(value.equals("Zapyataya") ? "0." : value);
            isNewInput = false;
        } else {
            if (value.equals("Zapyataya")) {
                if (!lblResult.getText().contains(".")) {
                    lblResult.setText(lblResult.getText() + ".");
                }
            } else {
                lblResult.setText(lblResult.getText() + value);
            }
        }
    }

    @FXML
    void onSymbolClicked(MouseEvent event) {

        Pane clickedPane = (Pane) event.getSource();
        ScaleTransition scaleTransition = new ScaleTransition(Duration.seconds(0.2), clickedPane);
        scaleTransition.setFromX(1.0);
        scaleTransition.setFromY(1.0);
        scaleTransition.setToX(0.8);
        scaleTransition.setToY(0.8);
        ScaleTransition scaleBackTransition = new ScaleTransition(Duration.seconds(0.2), clickedPane);
        scaleBackTransition.setFromX(0.8);
        scaleBackTransition.setFromY(0.8);
        scaleBackTransition.setToX(1.0);
        scaleBackTransition.setToY(1.0);
        SequentialTransition seqTransition = new SequentialTransition(scaleTransition, scaleBackTransition);
        seqTransition.play();

        String symbol = ((Pane) event.getSource()).getId().replace("btn", "");

        if (symbol.equals("Equals")) {
            double num2 = Double.parseDouble(lblResult.getText());
            if (operator.equals("/") && num2 == 0) {
                lblResult.setText("Error");
            }
            else {
                double result = 0.0;
                switch (operator) {
                    case "+" -> result = num1 + num2;
                    case "-" -> result = num1 - num2;
                    case "*" -> result = num1 * num2;
                    case "/" -> result = num1 / num2;
                }
                operator = ".";
                isNewInput = true;

                if (Double.isInfinite(result)) {
                    lblResult.setText("∞");
                }
                else if (result == (int) result) {
                    lblResult.setText(String.valueOf((int) result));
                } else {
                    lblResult.setText(String.valueOf(result));
                }
            }
        } else if (symbol.equals("Clear")) {
            lblResult.setText("0");
            operator = ".";
            isNewInput = true;
        } else if (symbol.equals("Znak")) {
            double currentValue = Double.parseDouble(lblResult.getText());
            if (currentValue == (int) currentValue) {
                lblResult.setText(String.valueOf((int)-currentValue));
            } else {
                lblResult.setText(String.valueOf(-currentValue));
            }

        } else if (symbol.equals("Procent")) {
            double currentValue = Double.parseDouble(lblResult.getText());
            double res = currentValue / 100;
            if (res == (int) res) {
                lblResult.setText(String.valueOf((int)res));
            } else {
                lblResult.setText(String.valueOf(res));
            }
        }
        else if (symbol.equals("Pow")) {
            double currentValue = Double.parseDouble(lblResult.getText());
            double res = pow(currentValue, 2);

            if (res == (int) res) {
                lblResult.setText(String.valueOf((int) res));
            } else {
                lblResult.setText(String.valueOf(res));
            }
        }
        else if (symbol.equals("Sqrt")) {
            double currentValue = Double.parseDouble(lblResult.getText());
            if(currentValue <= 0){
                lblResult.setText("Error");
            }
            else{
                double res = log(currentValue);
                if (res == (int) res) {
                    lblResult.setText(String.valueOf((int) res));
                } else {
                    lblResult.setText(String.valueOf(res));
                }
            }
        }
        else if (symbol.equals("Ln")) {
            double currentValue = Double.parseDouble(lblResult.getText());
            if(currentValue <= 0){
                lblResult.setText("Error");
            }
            else{
                double res = log(currentValue);
                if (res == (int) res) {
                    lblResult.setText(String.valueOf((int) res));
                } else {
                    lblResult.setText(String.valueOf(res));
                }
            }

        }
        else {
            switch (symbol) {
                case "Plus" -> operator = handleArithmeticButtonClick("+");
                case "Minus" -> operator = handleArithmeticButtonClick("-");
                case "Multiply" -> operator = handleArithmeticButtonClick("*");
                case "Divide" -> operator = handleArithmeticButtonClick("/");
            }
            num1 = Double.parseDouble(lblResult.getText());
            isNewInput = true;
        }
    }


    private String handleArithmeticButtonClick(String newOperator) {
        if (operator.equals(".")) {
            return newOperator;
        } else {
            double num2 = Double.parseDouble(lblResult.getText());
            double result = 0.0;
            switch (operator) {
                case "+" -> result = num1 + num2;
                case "-" -> result = num1 - num2;
                case "*" -> result = num1 * num2;
                case "/" -> {
                    if (num2 == 0) {
                        lblResult.setText("Error");
                        return newOperator;
                    } else {
                        result = num1 / num2;
                    }
                }
            }

            operator = ".";
            isNewInput = true;

            if (Double.isInfinite(result)) {
                lblResult.setText("Infinity");
            } else if (result == (int) result) {
                lblResult.setText(String.valueOf((int) result));
            } else {
                lblResult.setText(String.valueOf(result));
            }
            num1 = Double.parseDouble(lblResult.getText());
            return newOperator;
        }
    }

}


