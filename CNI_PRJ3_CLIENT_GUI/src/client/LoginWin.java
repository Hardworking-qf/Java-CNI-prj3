package client;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class LoginWin extends Application {
	public static Stage stage;
	public LoginWin() {
	}
	
	public LoginWin(String[] args) {
		launch(args);
	}
	
	@Override
	public void start(Stage stage) throws Exception {
		LoginWin.stage=stage;
		Parent root = FXMLLoader.load(getClass().getResource("LoginWin.fxml"));

		Scene scene = new Scene(root);
		stage.setTitle("Login");
		stage.setScene(scene);
		stage.show();			
	}
}
