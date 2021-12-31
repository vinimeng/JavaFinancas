package form;

import dao.UsuarioDAO;
import entity.Usuario;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.stage.Stage;
import util.BCrypt;
import util.Utils;

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
			Utils.dialogoOK(stage, "ERRO(S)", erros);
		} else {
			usuarioLogado.setSenha(BCrypt.hashpw(senhaNova, BCrypt.gensalt()));

			if (UsuarioDAO.update(usuarioLogado)) {
				Utils.dialogoOK(stage, "SUCESSO", "SENHA TROCADA COM SUCESSO.");
				stage.close();
			} else {
				Utils.dialogoOK(stage, "ERRO(S)", "ERRO AO TROCAR SENHA.");
			}
		}
	}

	public void setStage(Stage stage) {
		this.stage = stage;
	}
}
