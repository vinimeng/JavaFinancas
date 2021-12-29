package form;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.stage.Stage;

public class TrocarSenhaController {

	@FXML
    private PasswordField senhaAtual;
	
	@FXML
    private PasswordField novaSenha;
	
	@FXML
    private PasswordField repetirNovaSenha;
	
	@FXML
    private Button trocarSenhaBtn;

	@SuppressWarnings("unused")
	private Stage stage;

	@FXML
	private void trocarSenhaBtnActionPerformed(ActionEvent event) {
		//
	}

	public void setStage(Stage stage) {
		this.stage = stage;
	}
}
