package com.controller;

import com.entities.BookingResult;
import com.info.ResultMap;
import com.info.ResultMessage;
import com.info.UserInfo;
import com.nodes.ProgressFrom;
import com.nodes.ToastController;
import com.task.ConfirmBookingTask;
import com.utils.NumberUtil;
import com.utils.SMSUtil;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class SchedulingController {

    @FXML
    private TextField customerTelTextField;
    @FXML
    private Button registerButton;
    @FXML
    private Button cancelButton;

    private static Stage window;

    private static UserInfo userInfo;

    private static BookingResult bookingResult;

    void showAndWait(UserInfo userInfo) {
        window = new Stage();
        window.setTitle("预约排班");
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/fxml/scheduling.fxml"));
        AnchorPane layout;
        try {
            layout = loader.load();
            Scene scene = new Scene(layout);
            window.setScene(scene);
            window.setAlwaysOnTop(true);
            sceneBindEvent(scene);
            SchedulingController.userInfo = userInfo;
            window.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @FXML
    private void initialize() {
        bindEvent();
    }

    private void bindEvent() {
//        关闭窗口
        cancelButton.setOnMouseClicked(event -> {
            if (event.getButton().equals(MouseButton.PRIMARY)) {
                close();
            }
        });
//        注册按钮
        registerButton.setOnMouseClicked(event -> {
            if (event.getButton().equals(MouseButton.PRIMARY)) {
                ResultMessage rs = NumberUtil.isPhone(customerTelTextField.getText());

                if (rs.isFlag()) {
                    userInfo.setCustomerTel(customerTelTextField.getText());
                    // 人工预约
                    ConfirmBookingTask confirmBookingTask = new ConfirmBookingTask(userInfo);
                    ProgressFrom progressFrom = new ProgressFrom(confirmBookingTask, window, "预约中，请稍后");
                    progressFrom.activateProgressBar();
                    confirmBookingTask.valueProperty().addListener(lis -> {
                        ResultMap<BookingResult> bookingResult = confirmBookingTask.getValue();
                        if (bookingResult.getResultCode().equals("1")) {
                            System.out.println("预约成功");
                            sendSMS();
                        } else {
                            System.out.println("预约失败");
                        }
                    });

                } else {
                    ToastController.getInstance().makeToast(window, rs.getInformation());
                }
            }
        });
//        回车键监听
//        customerTelTextField.setOnKeyPressed(event -> {
////            验证手机号
//            if (event.getCode().equals(KeyCode.ENTER)){
//                ResultMessage rs = NumberUtil.isPhone(customerTelTextField.getText());
//                if (rs.isFlag()){
////                    执行预约操作
//                    sendSMS();
//
//                } else {
//                    ToastController.getInstance().makeToast(window,rs.getInformation());
//                }
//            }
//        });
    }

    //    面板事件绑定
    private void sceneBindEvent(Scene scene) {
        scene.setOnKeyPressed(event -> {
            if (event.getCode().equals(KeyCode.ESCAPE)) {
                window.close();
            }
        });
    }

    void close() {
        window.close();
    }

    private void sendSMS() {
        ResultMessage rs = SMSUtil.sendSMS(customerTelTextField.getText(), "这是一条测试数据，无需回复。");
        if (rs.isFlag()) {
            window.close();
        } else {
            ToastController.getInstance().makeToast(window, rs.getInformation());
        }
    }


}
