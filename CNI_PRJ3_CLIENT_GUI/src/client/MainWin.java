package client;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class MainWin {
	public MainWin(Stage stage) throws IOException {

		Parent root = FXMLLoader.load(getClass().getResource("MainWin.fxml"));

		((Button) root.lookup("#ExitBtn")).setOnAction(e -> {
			ClientMain.endVerify();
			System.exit(0);
		});

		new Thread(() -> {
			long lastsendtime = System.currentTimeMillis();
			while (true) {
				if (System.currentTimeMillis() - lastsendtime >= 10 * 60 * 1000L) {
					ClientMain.getVerify();
					lastsendtime = System.currentTimeMillis();
				}
			}
		}).start();

		Scene scene = new Scene(root);
		stage.setTitle("Main");
		stage.setScene(scene);
		stage.show();
	}
}