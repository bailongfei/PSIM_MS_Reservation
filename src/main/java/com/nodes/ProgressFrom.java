package com.nodes;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.concurrent.Task;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Background;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

/**
 * 加载动画
 */
public class ProgressFrom {

    private Stage dialogStage;

    public ProgressFrom(final Task<?> task, Stage primaryStage, String hint) {
        dialogStage = new Stage();
        ProgressIndicator progressIndicator = new ProgressIndicator();
        dialogStage.initOwner(primaryStage);
        dialogStage.initStyle(StageStyle.UNDECORATED);
        dialogStage.initStyle(StageStyle.TRANSPARENT);
        dialogStage.initModality(Modality.APPLICATION_MODAL);
        dialogStage.setHeight(primaryStage.getHeight());
        dialogStage.setWidth(primaryStage.getWidth());
        dialogStage.setX(primaryStage.getX());
        dialogStage.setY(primaryStage.getY());
        Label label = new Label(hint);
        label.setFont(new Font("Microsoft YaHei",24));
        progressIndicator.setProgress(1.0);
        progressIndicator.progressProperty().bind(task.progressProperty());
        Button button = new Button("取消");
        button.setFont(new Font("Microsoft YaHei",20));
        button.setOnMouseClicked(event -> {
            task.cancel(true);
            cancelProgressBar();
        });
        VBox vBox = new VBox();
        vBox.setAlignment(Pos.CENTER);
        vBox.setSpacing(20);
        vBox.setBackground(Background.EMPTY);
        vBox.getChildren().addAll(progressIndicator,label,button);
        vBox.setStyle("-fx-background-color: rgba(255,255,255,0.8);");
        Scene scene = new Scene(vBox);
        scene.setOnKeyPressed(event -> {
            if (event.getCode().equals(KeyCode.ESCAPE)){
                cancelProgressBar();
            }
        });
        scene.setFill(null);
        dialogStage.setScene(scene);
        dialogStage.setAlwaysOnTop(true);
        dialogStage.initStyle(StageStyle.TRANSPARENT);
        Thread inner = new Thread(task);
        inner.start();
        task.setOnSucceeded(event -> cancelProgressBar());
        Timeline fadeInTimeline = new Timeline();
        KeyFrame fadeOutKey1 = new KeyFrame(Duration.millis(20000));
        fadeInTimeline.getKeyFrames().add(fadeOutKey1);
        fadeInTimeline.setOnFinished((aeb) -> {
            cancelProgressBar();
        });
        fadeInTimeline.play();
    }

    /**
     * 激活加载动画
     */
    public void activateProgressBar() {
        dialogStage.show();
    }

    public void cancelProgressBar() {
        dialogStage.close();
    }
}