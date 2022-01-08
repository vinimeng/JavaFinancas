package form;

import dao.UsuarioDAO;
import entity.Usuario;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import util.BCrypt;
import util.Utils;

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

	private Stage stage;

	private Usuario usuarioEsquecido;

	@FXML
	private void enviarRespostasBtnActionPerformed(ActionEvent event) {
		String usuarioEsqueceu = usuario.getText();
		Color corEscolhida = corFavorita.getValue();
		String corEsqueceu = Utils.colorParaHEXString(corEscolhida);
		String animalEsqueceu = animalFavorito.getText();
		String erros = "";

		if (usuarioEsqueceu.length() < 1) {
			erros += "USU�RIO N�O PODE ESTAR VAZIO.\n";
		} else {
			usuarioEsquecido = UsuarioDAO.get(usuarioEsqueceu);

			if (usuarioEsquecido == null) {
				erros += "USU�RIO N�O EXISTE NA BASE DE DADOS.\n";
			}
		}

		if (animalEsqueceu.length() < 1) {
			erros += "ANIMAL FAVORITO N�O PODE ESTAR VAZIO.\n";
		}

		if (erros.length() > 0) {
			Utils.dialogoOK(stage, "ERRO(S)", erros);
		} else {
			erros = "";

			if (!corEsqueceu.equals(usuarioEsquecido.getCor()) || !animalEsqueceu.equals(usuarioEsquecido.getAnimal())) {
				erros += "RESPOSTAS N�O CORRESPONDEM AS CADASTRADAS PELO USU�RIO.";
			}

			if (erros.length() > 0) {
				Utils.dialogoOK(stage, "ERRO(S)", erros);
			} else {
				usuario.setDisable(true);
				corFavorita.setDisable(true);
				animalFavorito.setDisable(true);
				enviarRespostasBtn.setDisable(true);
				novaSenha.setDisable(false);
				repetirNovaSenha.setDisable(false);
				trocarSenhaBtn.setDisable(false);
			}
		}
	}

	@FXML
	private void trocarSenhaBtnActionPerformed(ActionEvent event) {
		String senhaNova = novaSenha.getText();
		String repetirSenhaNova = repetirNovaSenha.getText();
		String erros = "";

		if (senhaNova.length() < 1) {
			erros += "A NOVA SENHA INFORMADA N�O PODE SER VAZIA.\n";
		}

		if (!senhaNova.equals(repetirSenhaNova)) {
			erros += "A NOVA SENHA INFORMADA E A REPETI��O DELA N�O SE CORRESPONDEM.\n";
		}

		if (usuarioEsquecido == null) {
			erros += "NENHUM USU�RIO ENCONTRADO PARA REALIZAR A TROCA DA SENHA.\n";
		}

		if (erros.length() > 0) {
			Utils.dialogoOK(stage, "ERRO(S)", erros);
		} else {
			usuarioEsquecido.setSenha(BCrypt.hashpw(senhaNova, BCrypt.gensalt()));

			if (UsuarioDAO.update(usuarioEsquecido)) {
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
