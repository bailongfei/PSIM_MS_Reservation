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
import javafx.stage.StageStyle;
import javafx.util.Callback;
import javafx.util.StringConverter;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class SchedulingDateController {

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
    private TableColumn<SchedulingInfo, String> schedulingDateColumn;
    @FXML
    private TableColumn<SchedulingInfo, String> schedulingTypeColumn;
    @FXML
    private TableColumn<SchedulingInfo, String> bookingNumColumn;
    @FXML
    private DatePicker datePicker;

    private static Stage window;

    private static UserInfo userInfo;

    private static ObservableList<SchedulingInfo> schedulingInfoList = FXCollections.observableArrayList();

    void showAndWait() {
        window = new Stage();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/fxml/schedulingDate.fxml"));
        AnchorPane layout;
        try {
            layout = loader.load();
            Scene scene = new Scene(layout);
            window.setScene(scene);
            window.initStyle(StageStyle.UNDECORATED);
            window.setAlwaysOnTop(true);
            window.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @FXML
    private void initialize() {
        initDatePicker();
        bindEvent();
        bindTableValue(schedulingInfoList);

    }


    private static String pattern = "yyyy-MM-dd";

    /**
     * 初始化日期控件
     */
    private void initDatePicker() {
        StringConverter<LocalDate> converter = new StringConverter<LocalDate>() {
            DateTimeFormatter dateFormatter =
                    DateTimeFormatter.ofPattern(pattern);

            @Override
            public String toString(LocalDate date) {
                if (date != null) {
                    return dateFormatter.format(date);
                } else {
                    return "";
                }
            }

            @Override
            public LocalDate fromString(String string) {
                if (string != null && !string.isEmpty()) {
                    return LocalDate.parse(string, dateFormatter);
                } else {
                    return null;
                }
            }
        };
        datePicker.setConverter(converter);
        datePicker.setPromptText(pattern.toLowerCase());

        final Callback<DatePicker, DateCell> dayCellFactory =
                new Callback<DatePicker, DateCell>() {
                    @Override
                    public DateCell call(final DatePicker datePicker) {
                        return new DateCell() {
                            @Override
                            public void updateItem(LocalDate item, boolean empty) {
                                super.updateItem(item, empty);

                                if (item.isBefore(LocalDate.now())) {
                                    setDisable(true);
                                    setStyle("-fx-background-color: #ff8497;");
                                }
                            }
                        };
                    }
                };
        datePicker.setDayCellFactory(dayCellFactory);
    }

    private void bindEvent() {
        // 关闭窗口
        cancelButton.setOnMouseClicked(event -> {
            if (event.getButton().equals(MouseButton.PRIMARY)) {
                close();
            }
        });
//        注册按钮
        registerButton.setOnMouseClicked(event -> {
            if (event.getButton().equals(MouseButton.PRIMARY)) {
                // 左键
                String schedulingId = chooseScheduling();
                if (schedulingId != null) {
                    ResultMap rs = NumberUtil.isPhone(customerTelTextField.getText());
                    if (rs.getResultCode().equals("1")) {
                        // 验证手机号
                        userInfo.setCustomerTel(customerTelTextField.getText());
                        userInfo.setSchedulingID(schedulingId);
                        // 确认预约
                        ConfirmBookingTask confirmBookingTask = new ConfirmBookingTask(userInfo);
                        ProgressFrom progressFrom = new ProgressFrom(confirmBookingTask, window, "预约中，请稍后");
                        progressFrom.activateProgressBar();
                        confirmBookingTask.valueProperty().addListener(lis -> {
                            //  返回信息
                            ResultMap<BookingResult> bookingResult = confirmBookingTask.getValue();
                            if (!bookingResult.getResultCode().equals("1")) {
                                ToastController.getInstance().makeToast(window, "预约失败");
                            } else {
                                sendSMS(SMSUtil.buildBookingContent(userInfo.getCustomerName(),
                                        schedulingInfoTable.getSelectionModel().getSelectedItem().getSchedulingDate(),
                                        "皮肤科(普通)",
                                        bookingResult.getParam().getBookingInfo()));
                                ToastController.getInstance().makeToast(window, "预约成功");
                                close();
                            }
                        });
                    } else {
                        ToastController.getInstance().makeToast(window, rs.getResultMessage());
                    }
                }
            }
        });

        datePicker.valueProperty().addListener(lis->{
            //  日期选择
            ObservableList<SchedulingInfo> filterList = FXCollections.observableArrayList();
            for (SchedulingInfo schedulingInfo:schedulingInfoList){
                if (schedulingInfo.getSchedulingDate().equals(datePicker.getValue().toString())){
                    filterList.addAll(schedulingInfo);
                }
            }
            bindTableValue(filterList);
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

    /**
     * 清空病人信息
     */
    private void sceneBindEvent(Scene scene) {
        scene.setOnKeyPressed(event -> {
            if (event.getCode().equals(KeyCode.ESCAPE)) {
                window.close();
            }
        });
    }

    /**
     * 关闭预约详情面板
     */
    void close() {
        BaseController.userInfo = null;
        window.close();
    }

    /**
     * 短息平台发送
     */
    private void sendSMS(String content) {
        SchedulingInfo scheduling = schedulingInfoTable.getSelectionModel().getSelectedItem();
        ResultMap rs = SMSUtil.sendSMS(customerTelTextField.getText(),content);
        if (rs.getResultCode().equals("1")) {
            window.close();
        } else {
            ToastController.getInstance().makeToast(window, rs.getResultMessage());
        }
    }

    /**
     * 绑定表格数据
     */
    private void bindTableValue(ObservableList<SchedulingInfo> list) {

        schedulingTypeColumn.setCellValueFactory(cell -> cell.getValue().schedulingTypeDescProperty());

        schedulingDateColumn.setCellValueFactory(cell -> cell.getValue().schedulingDateProperty());

        bookingNumColumn.setCellValueFactory(cell -> cell.getValue().bookingNumDescProperty());
        serialNumberColumn.setCellFactory((col) -> new TableCell<SchedulingInfo, String>() {
            @Override
            public void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (!empty) {
                    setText(String.valueOf(getIndex() + 1));
                }
            }
        });

        schedulingInfoTable.setItems(list);
    }

    /**
     * 传递数据
     */
    void setData(UserInfo userInfo, List<SchedulingInfo> schedulingInfos) {
        SchedulingDateController.userInfo = userInfo;
        if (schedulingInfoList != null && schedulingInfoList.size() > 1) {
            schedulingInfoList.remove(0, schedulingInfoList.size());
        } else {
            schedulingInfoList = FXCollections.observableArrayList();
        }
        schedulingInfoList.addAll(schedulingInfos);
        System.out.println(schedulingInfoList.size());

    }

    /**
     * 选择排班
     */
    private String chooseScheduling() {
        if (schedulingInfoTable.getSelectionModel().getSelectedIndex() == -1) {
            ToastController.getInstance().makeToast(window, "没有选择对应排班");
        } else {
            if (schedulingInfoTable.getSelectionModel().getSelectedItem().getBookingNum().equals("1")) {
                ToastController.getInstance().makeToast(window, "此排班无法预约");
            } else {
                return schedulingInfoTable.getSelectionModel().getSelectedItem().getSchedulingID();
            }
        }
        return null;
    }


}
