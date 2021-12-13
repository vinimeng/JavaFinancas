package application;

import form.LoginController;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;


public class Main extends Application {

	public Stage pStage;

	@Override
	public void start(Stage primaryStage) {

		pStage = primaryStage;
		pStage.setTitle("FINANÇAS PESSOAIS");

		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("/form/Login.fxml"));

			BorderPane loginForm = (BorderPane) loader.load();
			Scene scene = new Scene(loginForm);

			pStage.setScene(scene);
			pStage.getIcons().add(new Image(this.getClass().getResourceAsStream("/assets/logo.png")));
			pStage.setResizable(false);
			
			LoginController lc = loader.getController();
			lc.setLoginStage(pStage);
			
			pStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings("unused")
	public static void main(String[] args) {
		EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("Financas");
		EntityManager entityManager = entityManagerFactory.createEntityManager();

		launch(args);
	}
}
