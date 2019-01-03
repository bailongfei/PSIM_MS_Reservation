package com.controller;

import com.entities.BookingResult;
import com.entities.SchedulingInfo;
import com.info.ResultMap;
import com.info.UserInfo;
import com.nodes.ProgressFrom;
import com.nodes.ToastController;
import com.task.ConfirmBookingTask;
import com.utils.NumberUtil;
import com.utils.SMSUtil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

public class SchedulingController {

    @FXML
    private TextField customerTelTextField;
    @FXML
    private Button registerButton;
    @FXML
    private Button cancelButton;
    @FXML
    private TableView<SchedulingInfo> schedulingInfoTable;
    @FXML
    private TableColumn<SchedulingInfo, String> serialNumberColumn;
    @FXML
    private TableColumn<SchedulingInfo, String> schedulingTypeColumn;
    @FXML
    private TableColumn<SchedulingInfo, String> bookingNumColumn;

    private static Stage window;

    private static UserInfo userInfo;
    private static List<SchedulingInfo> schedulingInfos;

    void showAndWait(UserInfo userInfo, List<SchedulingInfo> schedulingInfos) {
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
            SchedulingController.schedulingInfos = schedulingInfos;
            window.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @FXML
    private void initialize() {
        bindEvent();
        bindTableValue();
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
                String schedulingId = chooseScheduling();
                if (schedulingId != null) {
                    ResultMap rs = NumberUtil.isPhone(customerTelTextField.getText());

                    if (rs.getResultCode().equals("1")) {
                        userInfo.setCustomerTel(customerTelTextField.getText());
                        userInfo.setSchedulingID(schedulingId);
                        // 人工预约
                        ConfirmBookingTask confirmBookingTask = new ConfirmBookingTask(userInfo);
                        ProgressFrom progressFrom = new ProgressFrom(confirmBookingTask, window, "预约中，请稍后");
                        progressFrom.activateProgressBar();
                        confirmBookingTask.valueProperty().addListener(lis -> {
                            ResultMap<BookingResult> bookingResult = confirmBookingTask.getValue();
                            if (bookingResult.getResultCode().equals("1")) {
                                sendSMS();
                                ToastController.getInstance().makeToast(window, "预约成功");
                            } else {
                                ToastController.getInstance().makeToast(window, "预约失败");
                            }
                        });

                    } else {
                        ToastController.getInstance().makeToast(window, rs.getResultMessage());
                    }
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
        SchedulingInfo scheduling = schedulingInfoTable.getSelectionModel().getSelectedItem();
        ResultMap rs = SMSUtil.sendSMS(customerTelTextField.getText(), SMSUtil.buildBookingContent(userInfo.getCustomerName(),
                scheduling.getSchedulingDate(),
                "皮肤科(普通)",
                "下午"));
        if (rs.getResultCode().equals("1")) {
            window.close();
        } else {
            ToastController.getInstance().makeToast(window, rs.getResultMessage());
        }
    }

    private ObservableList<SchedulingInfo> schedulingInfoList = FXCollections.observableArrayList();

    private void bindTableValue() {
        if (schedulingInfoList != null && schedulingInfos.size() > 1) {
            schedulingInfoList.remove(0, schedulingInfoList.size());
        } else {
            schedulingInfoList = FXCollections.observableArrayList();
        }
        schedulingInfoList.addAll(schedulingInfos);
        serialNumberColumn.setCellFactory((col) -> new TableCell<SchedulingInfo, String>() {
            @Override
            public void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (!empty) {
                    setText(String.valueOf(getIndex() + 1));
                }
            }
        });
        schedulingTypeColumn.setCellValueFactory(cell -> cell.getValue().schedulingTypeDescProperty());
        bookingNumColumn.setCellValueFactory(cell -> cell.getValue().bookingNumDescProperty());

        schedulingInfoTable.setItems(schedulingInfoList);

    }

    private String chooseScheduling() {
        if (schedulingInfoTable.getSelectionModel().getSelectedIndex() == -1) {
            ToastController.getInstance().makeToast(window, "没有选择对应排班");
        } else {
            if (schedulingInfoTable.getSelectionModel().getSelectedItem().getBookingNum().equals("1")) {
                return schedulingInfoTable.getSelectionModel().getSelectedItem().getSchedulingID();
            } else {
                ToastController.getInstance().makeToast(window, "此排班无法预约");
            }
        }
        return null;
    }

}
