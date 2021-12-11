package application;
	
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.fxml.FXMLLoader;


public class Main extends Application {
	
	public Stage pStage;
	
	@Override
	public void start(Stage primaryStage) {
		
		pStage = primaryStage;
		pStage.setTitle("Finanças Pessoais");
		
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("/form/Login.fxml"));
			
			BorderPane loginForm = (BorderPane) loader.load();
			Scene scene = new Scene(loginForm);
			
			pStage.setScene(scene);
			pStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("Financas");
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		
		launch(args);		
	}
}
