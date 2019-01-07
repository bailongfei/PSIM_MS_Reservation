package com.controller;import com.entities.BookingInfo;import com.info.ResultMap;import com.info.UserInfo;import com.nodes.ProgressFrom;import com.nodes.ToastController;import com.task.CancelBookingTask;import com.task.GetBookingInfoTask;import com.task.GetSchedulingTodayTask;import com.task.ReadCardTask;import com.utils.FileLockManager;import com.utils.IniUtil;import com.utils.NumberUtil;import javafx.application.Platform;import javafx.collections.FXCollections;import javafx.collections.ObservableList;import javafx.fxml.FXML;import javafx.scene.Scene;import javafx.scene.control.*;import javafx.scene.input.KeyCode;import javafx.scene.input.MouseButton;import javafx.stage.Stage;import java.io.IOException;import java.util.List;public class BaseController {    @FXML    private TextField customerIDTextField;    @FXML    private TextField customerNoTextField;    @FXML    private TextField customerNameTextField;    @FXML    private MenuItem configButton;    @FXML    private CheckMenuItem verifyInfoStatusButton;    @FXML    private Button SSCReadButton;    @FXML    private Button MSCReadButton;    @FXML    private Button IDCReadButton;    @FXML    private Button registerButton;    @FXML    private Button clearButton;    @FXML    private TextField customerIdField;    @FXML    private Button searchButton;    @FXML    private TextField srvGroupTextField;    @FXML    private TextField customerTypeTextField;    @FXML    private TextField staffTextField;    @FXML    private ComboBox<String> srvGroupComboBox;    @FXML    private ComboBox<String> customerTypeComboBox;    @FXML    private ComboBox<String> staffComboBox;    @FXML    private TableView<BookingInfo> bookingInfoTable;    @FXML    private TableColumn<BookingInfo, String> serialNumberColumn;    @FXML    private TableColumn<BookingInfo, String> srvGroupNameColumn;    @FXML    private TableColumn<BookingInfo, String> staffNameColumn;    @FXML    private TableColumn<BookingInfo, String> bookingTimeColumn;    @FXML    private TableColumn<BookingInfo, String> YYLYColumn;    @FXML    private TableColumn<BookingInfo, String> srvCodeNameColumn;    @FXML    private TableColumn<BookingInfo, String> schedulingDateColumn;    @FXML    private TableColumn<BookingInfo, String> bookingStatusColumn;    public static Stage stage;    static UserInfo userInfo;    private ObservableList<BookingInfo> bookingInfoList = FXCollections.observableArrayList();    @FXML    private void initialize() {        setButtonEnable();        bindEvent();    }    /**     * 绑定按钮事件     */    private void bindEvent() {//        社保卡        SSCReadButton.setOnMouseClicked(event -> {            if (event.getButton().equals(MouseButton.PRIMARY)) {                readCard(ReadCardTask.SS_CARD, "");            }        });//        磁条卡        MSCReadButton.setOnMouseClicked(event -> {            if (event.getButton().equals(MouseButton.PRIMARY)) {                readCard(ReadCardTask.MS_CARD, customerIDTextField.getText());            }        });        customerIDTextField.setOnKeyPressed(event -> {            if (event.getCode().equals(KeyCode.ENTER)) {                readCard(ReadCardTask.MS_CARD, customerIDTextField.getText());            }        });//        身份证        IDCReadButton.setOnMouseClicked(event -> {            if (event.getButton().equals(MouseButton.PRIMARY)) {                readCard(ReadCardTask.ID_CARD, "");            }        });//        预约        registerButton.setOnMouseClicked(event -> {            if (event.getButton().equals(MouseButton.PRIMARY)) {//                System.out.println("预约");                register();            }        });//        清除        clearButton.setOnMouseClicked(event -> {            if (event.getButton().equals(MouseButton.PRIMARY)) {                cleanInfo();            }        });//        右侧查找病人        customerIdField.setOnKeyPressed(event -> {            if (event.getCode().equals(KeyCode.ENTER)) {                // 回车                String idString = customerIdField.getText();                if (!idString.equals("")) {                    // 验证身份证                    ResultMap rs = NumberUtil.isID(customerIdField.getText());                    if (!rs.getResultCode().equals("1")) {                        ToastController.getInstance().makeToast(stage, rs.getResultMessage());                    } else {                        // 右侧查询操作                        GetBookingInfoTask getBookingInfoTask = new GetBookingInfoTask(idString);                        ProgressFrom progressFrom = new ProgressFrom(getBookingInfoTask, stage, "查询中，请稍后");                        progressFrom.activateProgressBar();                        getBookingInfoTask.valueProperty().addListener(lis -> {                            if (getBookingInfoTask.getValue().getResultCode().equals("1")) {                                bindTableValue(getBookingInfoTask.getValue().getParam());                            } else {                                ToastController.getInstance().makeToast(stage, getBookingInfoTask.getValue().getResultMessage());                            }                        });                    }                }            }        });//        搜索按钮        searchButton.setOnMouseClicked(event -> {            if (event.getButton().equals(MouseButton.PRIMARY)) {                // 左键                String idString = customerIdField.getText();                if (!idString.equals("")) {//                    验证身份证                    ResultMap rs = NumberUtil.isID(customerIdField.getText());                    if (rs.getResultCode().equals("1")) {//                    右侧查询操作                        GetBookingInfoTask getBookingInfoTask = new GetBookingInfoTask(idString);                        ProgressFrom progressFrom = new ProgressFrom(getBookingInfoTask, stage, "查询中，请稍后");                        progressFrom.activateProgressBar();                        getBookingInfoTask.valueProperty().addListener(lis -> {                            if (getBookingInfoTask.getValue().getResultCode().equals("1")) {                                bindTableValue(getBookingInfoTask.getValue().getParam());                            } else {                                ToastController.getInstance().makeToast(stage, getBookingInfoTask.getValue().getResultMessage());                            }                        });                    } else {                        ToastController.getInstance().makeToast(stage, rs.getResultMessage());                    }                }            }        });//         配置按钮        configButton.setOnAction(event -> {            if (config != null) {                config.close();                config = new ConfigController();                config.showAndWait();            } else {                config = new ConfigController();                config.showAndWait();            }        });        verifyInfoStatusButton.setOnAction(event -> {            IniUtil.modifyVerifyInfoButton();            verifyInfoStatusButton.setSelected(IniUtil.getVerifyInfoButtonStatus());        });//        关闭程序        stage.setOnCloseRequest(event -> {            FileLockManager fileLockManager = new FileLockManager("lock.lock");            try {                fileLockManager.unLock();                System.exit(0);            } catch (IOException ignore) {            }        });    }    private ConfigController config = null;    /**     * 绑定面板按钮     */    public void paneBindEvent(Scene scene) {        scene.setOnKeyPressed(event -> {            if (event.getCode().equals(KeyCode.ESCAPE)) {//                清空                cleanInfo();            }            if (event.getCode().equals(KeyCode.F7)) {//                社保卡                readCard(ReadCardTask.SS_CARD, "");            }            if (event.getCode().equals(KeyCode.F8)) {//                磁条卡                readCard(ReadCardTask.MS_CARD, customerIDTextField.getText());            }            if (event.getCode().equals(KeyCode.F9)) {//                身份证                readCard(ReadCardTask.ID_CARD, "");            }            if (event.getCode().equals(KeyCode.F5)) {//                预约//                System.out.println("F5");                register();            }        });    }    /**     * 读卡操作     * */    private void readCard(int readCardType, String card) {        ReadCardTask readCardTask = new ReadCardTask(readCardType, card);        ProgressFrom progressFrom = new ProgressFrom(readCardTask, stage, "读卡中，请稍后");        progressFrom.activateProgressBar();        readCardTask.valueProperty().addListener(lis -> verifyInfo(readCardTask.getValue()));    }    /**     * 验证病人信息     */    private void verifyInfo(ResultMap<UserInfo> resultInfo) {        if (resultInfo.getParam() != null) {            userInfo = resultInfo.getParam();            Platform.runLater(() -> {                customerIDTextField.setText(userInfo.getCustomerID());                customerNameTextField.setText(userInfo.getCustomerName());                customerNoTextField.setText(userInfo.getCustomerNo());            });        } else {            ToastController.getInstance().makeToast(stage,resultInfo.getResultMessage());        }    }//    显示提示信息//    private void makeToast(String message){//        if (toast==null){//            toast = new Toast(stage, message, 2000, 500, 500);//            toast.doSomething(() -> System.out.println(message));//        } else {//            toast.closeStage();//            toast = new Toast(stage, message, 2000, 500, 500);//            toast.doSomething(() -> System.out.println(message));//        }//    }    /**     * 清空病人信息     */    private void cleanInfo() {        userInfo = null;        Platform.runLater(() -> {            customerIDTextField.setText("");            customerNameTextField.setText("");            customerNoTextField.setText("");        });    }    private SchedulingDateController scheduling = null;    /**     * 预约面板     */    private void register() {//        if (userInfo == null) {//            ToastController.getInstance().makeToast(stage, "当前无刷卡病人");//        } else {//            if (scheduling != null) {//                scheduling.close();//                scheduling = new SchedulingController();//                scheduling.showAndWait();//            } else {//                scheduling = new SchedulingController();//                scheduling.showAndWait();//            }//        }//        检查是否验证信息        if (!verifyInfoStatusButton.isSelected()) {            userInfo = new UserInfo();            userInfo.setCustomerID(customerIDTextField.getText());            userInfo.setCustomerNo(customerNoTextField.getText());            userInfo.setCustomerName(customerNameTextField.getText());            userInfo.setCustomerNoType("1");        }        // 获取当日号源        GetSchedulingTodayTask getSchedulingTodayTask = new GetSchedulingTodayTask("2058", "5", "1");        ProgressFrom progressFrom = new ProgressFrom(getSchedulingTodayTask, stage, "执行中，请稍后");        progressFrom.activateProgressBar();        getSchedulingTodayTask.valueProperty().addListener(lis -> {            // 当日号源            if (getSchedulingTodayTask.getValue().getResultCode().equals("1")) {                // 验证刷卡信息                if (userInfo == null) {                    ToastController.getInstance().makeToast(stage, "当前无刷卡病人");                } else {//                    打开预约面板                    if (scheduling != null) {                        scheduling.close();                        scheduling = new SchedulingDateController();                        scheduling.setData(userInfo, getSchedulingTodayTask.getValue().getParam());                        progressFrom.cancelProgressBar();                        scheduling.showAndWait();                    } else {                        scheduling = new SchedulingDateController();                        scheduling.setData(userInfo, getSchedulingTodayTask.getValue().getParam());                        //userInfo, getSchedulingTodayTask.getValue().getParam()                        progressFrom.cancelProgressBar();                        scheduling.showAndWait();                    }                }            } else {                ToastController.getInstance().makeToast(stage, getSchedulingTodayTask.getValue().getResultMessage());            }        });    }    /**     * 初始化按钮状态和按钮     */    private void setButtonEnable() {        srvGroupTextField.setDisable(IniUtil.readButtonInfo("srvGroupTextField").equals("0"));        srvGroupComboBox.setDisable(IniUtil.readButtonInfo("srvGroupComBoBox").equals("0"));        customerTypeTextField.setDisable(IniUtil.readButtonInfo("customerTypeTextField").equals("0"));        customerTypeComboBox.setDisable(IniUtil.readButtonInfo("customerTypeComBoBox").equals("0"));        staffTextField.setDisable(IniUtil.readButtonInfo("staffTextField").equals("0"));        staffComboBox.setDisable(IniUtil.readButtonInfo("staffComBoBox").equals("0"));        verifyInfoStatusButton.setSelected(IniUtil.getVerifyInfoButtonStatus());        // 表格右键菜单面板        final ContextMenu cm = new ContextMenu();        MenuItem cancelButtonContextMenu = new MenuItem("取消预约");        cm.getItems().addAll(cancelButtonContextMenu);        cancelButtonContextMenu.setOnAction(event -> {            String bookingID = bookingInfoList.get(bookingInfoTable.getSelectionModel().getFocusedIndex()).getBookingID();            System.out.println(bookingID);            cancelBooking(bookingID);        });        // 表格右键事件        bookingInfoTable.setOnMouseClicked(event -> {            if (event.getButton().equals(MouseButton.SECONDARY)) {                cm.show(bookingInfoTable, event.getScreenX(), event.getScreenY());            }            if (event.getButton().equals(MouseButton.PRIMARY)) {                cm.hide();            }        });        cancelButtonContextMenu.setDisable(IniUtil.readButtonInfo("cancelBookingButton").equals("0"));    }    /**     * 绑定预约信息     */    private void bindTableValue(List<BookingInfo> bookings) {        if (bookingInfoList != null) {            bookingInfoList.remove(0, bookingInfoList.size());        } else {            bookingInfoList = FXCollections.observableArrayList();        }        if (bookings!=null&&bookings.size()>0){            bookingInfoList.addAll(bookings);        }        // 序号列        serialNumberColumn.setCellFactory((col) -> new TableCell<BookingInfo, String>() {            @Override            public void updateItem(String item, boolean empty) {                super.updateItem(item, empty);                if (!empty) {                    int rowIndex = getIndex() + 1;                    setText(String.valueOf(rowIndex));                }            }        });        // 科室名称        srvGroupNameColumn.setCellValueFactory(cell -> cell.getValue().srvGroupNameProperty());        // 楼层信息        srvCodeNameColumn.setCellValueFactory(cell->cell.getValue().srvCodeNameProperty());        // 医生姓名        staffNameColumn.setCellValueFactory(cell -> cell.getValue().staffNameProperty());        // 预约时间        bookingTimeColumn.setCellValueFactory(cell -> cell.getValue().bookingTimeProperty());        // 预约状态        bookingStatusColumn.setCellValueFactory(cell -> cell.getValue().bookingStatusDesc());        schedulingDateColumn.setCellValueFactory(cell->cell.getValue().schedulingDateProperty());        // 预约来源        YYLYColumn.setCellValueFactory(cell -> cell.getValue().YYLYProperty());        // 数据源        bookingInfoTable.setItems(bookingInfoList);    }    /**     * 取消选中记录     */    private void cancelBooking(String bookingID) {        CancelBookingTask cancelBookingTask = new CancelBookingTask(bookingID);        ProgressFrom progressFrom = new ProgressFrom(cancelBookingTask, stage, "执行中，请稍后");        progressFrom.activateProgressBar();        cancelBookingTask.valueProperty().addListener(lis ->                ToastController.getInstance().makeToast(stage, cancelBookingTask.getValue().getResultMessage()));    }}