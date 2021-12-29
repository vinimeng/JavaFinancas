package form;

import dao.UsuarioDao;
import entity.Usuario;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.PasswordField;
import javafx.stage.Stage;
import util.BCrypt;

public class TrocarSenhaController {

	@FXML
	private PasswordField senhaAtual;

	@FXML
	private PasswordField novaSenha;

	@FXML
	private PasswordField repetirNovaSenha;

	@FXML
	private Button trocarSenhaBtn;

	private Stage stage;
	
	private Usuario usuarioLogado;
	
	public void initialize(Usuario usuarioLogado) {
		this.usuarioLogado = usuarioLogado;
	}

	@FXML
	private void trocarSenhaBtnActionPerformed(ActionEvent event) {
		String atualSenha = senhaAtual.getText();
		String senhaNova = novaSenha.getText();
		String repetirSenhaNova = repetirNovaSenha.getText();
		String erros = "";
		
		if (!BCrypt.checkpw(atualSenha, usuarioLogado.getSenha())) {
			erros += "A SENHA ATUAL INFORMADA ESTÁ INCORRETA.\n";
		}
		
		if (senhaNova.length() < 1) {
			erros += "A NOVA SENHA INFORMADA NÃO PODE SER VAZIA.\n";
		}
		
		if (!senhaNova.equals(repetirSenhaNova)) {
			erros += "A NOVA SENHA INFORMADA E A REPETIÇÃO DELA NÃO SE CORRESPONDEM.\n";
		}
		
		if (erros.length() > 0) {
			Dialog<String> d = new Dialog<>();
			ButtonType type = new ButtonType("OK", ButtonData.OK_DONE);
			d.setTitle("ERROS");
			Stage s = (Stage) d.getDialogPane().getScene().getWindow();
			s.getIcons().add(stage.getIcons().get(0));
			d.setContentText(erros);
			d.getDialogPane().getButtonTypes().add(type);
			d.initOwner(stage);
			d.showAndWait();
		} else {
			usuarioLogado.setSenha(BCrypt.hashpw(senhaNova, BCrypt.gensalt()));
			
			UsuarioDao ud = new UsuarioDao();
			ud.update(usuarioLogado);
			
			Dialog<String> d = new Dialog<>();
			ButtonType type = new ButtonType("OK", ButtonData.OK_DONE);
			d.setTitle("SUCESSO");
			Stage s = (Stage) d.getDialogPane().getScene().getWindow();
			s.getIcons().add(stage.getIcons().get(0));
			d.setContentText("SENHA TROCADA COM SUCESSO.");
			d.getDialogPane().getButtonTypes().add(type);
			d.initOwner(stage);
			d.showAndWait();
			stage.close();
		}
	}

	public void setStage(Stage stage) {
		this.stage = stage;
	}
}
