package application;

import java.io.InputStream;
import java.net.URL;

import form.LoginController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class Main extends Application {

	public Stage stage;
	private final String TITULO = "FINANÇAS PESSOAIS";
	private final String LOGO = "/assets/logo.png";
	private final String LOGINFXML = "/form/Login.fxml";

	@Override
	public void start(Stage stageInicial) {
		stage = stageInicial;
		stage.setTitle(TITULO);

		try {
			FXMLLoader loaderFXML = new FXMLLoader();
			URL loginFXML = getClass().getResource(LOGINFXML);
			loaderFXML.setLocation(loginFXML);

			BorderPane loginFormulario = (BorderPane) loaderFXML.load();
			Scene scene = new Scene(loginFormulario);

			stage.setScene(scene);

			InputStream logoResource = getClass().getResourceAsStream(LOGO);
			Image logo = new Image(logoResource);
			stage.getIcons().add(logo);

			stage.setResizable(false);

			LoginController loginController = loaderFXML.getController();
			loginController.setStage(stage);

			stage.show();
		} catch (Exception e) {
			System.out.println("NÃO FOI POSSÍVEL INICIAR O PROGRAMA " + TITULO + ". MOTIVO: " + e.getMessage());
		}
	}

	public static void main(String[] args) {
		launch(args);
	}
}
