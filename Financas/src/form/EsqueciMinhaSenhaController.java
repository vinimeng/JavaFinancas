package form;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class EsqueciMinhaSenhaController {

	@FXML
    private TextField usuario;
	
	@FXML
    private ColorPicker corFavorita;
	
	@FXML
    private TextField animalFavorito;
	
	@FXML
    private Button enviarRespostasBtn;

	@FXML
    private PasswordField novaSenha;
	
	@FXML
    private PasswordField repetirNovaSenha;
	
	@FXML
    private Button trocarSenhaBtn;

	@SuppressWarnings("unused")
	private Stage stage;

	@FXML
	private void enviarRespostasBtnActionPerformed(ActionEvent event) {
		//
	}
	
	@FXML
	private void trocarSenhaBtnActionPerformed(ActionEvent event) {
		//
	}

	public void setStage(Stage stage) {
		this.stage = stage;
	}
}
