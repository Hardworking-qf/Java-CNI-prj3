package client;

import java.io.IOException;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Modality;

public class LoginWinController {
	@FXML
	private TextField SNInput;
	@FXML
	private Button LoginBtn;
	@FXML
	private Button CancelBtn;

	public void initialize() {
		if(ClientMain.client.getSNFromFile()) {
			SNInput.setText(ClientMain.client.getSerialNumber());
			Platform.runLater(()->{LoginBtnPressed();});
		}
	}

	@FXML
	private void LoginBtnPressed() {
		if (SNInput.getText().matches("^[0-9]*$")) {
			ClientMain.client.setSerialNumber(SNInput.getText());
			if (ClientMain.getVerify()) {
				Platform.runLater(() -> {
					try {
						new MainWin(LoginWin.stage);
					} catch (IOException e) {
						e.printStackTrace();
					}
				});
			} else {
				SummonAlert("校验失败");
			}
		} else {
			SummonAlert("校验码格式错误");
		}
	}

	@FXML
	private void CancelBtnPressed() {
		System.exit(0);
	}

	// 锟斤拷锟缴碉拷锟斤拷
	private void SummonAlert(String msg) {
		Alert alert = new Alert(AlertType.INFORMATION, msg, ButtonType.CLOSE);
		alert.initModality(Modality.APPLICATION_MODAL);
		alert.setHeaderText("提示");
		alert.show();
	}
}
