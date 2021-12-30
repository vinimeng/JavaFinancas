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

public class CadastroController {

	@FXML
	private TextField usuario;

	@FXML
	private TextField nome;

	@FXML
	private PasswordField novaSenha;

	@FXML
	private PasswordField repetirNovaSenha;

	@FXML
	private ColorPicker corFavorita;

	@FXML
	private TextField animalFavorito;

	@FXML
	private Button cadastrarseBtn;

	private Stage stage;

	@FXML
	private void cadastrarseBtnActionPerformed(ActionEvent event) {
		String novoUsuario = usuario.getText();
		String novoNome = nome.getText();
		String senhaNova = novaSenha.getText();
		String repetirSenhaNova = repetirNovaSenha.getText();

		Color corEscolhida = corFavorita.getValue();
		String novaCor = Utils.colorParaHEXString(corEscolhida);

		String novoAnimal = animalFavorito.getText();

		String erros = "";

		if (novoUsuario.length() < 1) {
			erros += "USU�RIO N�O PODE ESTAR VAZIO.\n";
		} else {
			if (UsuarioDAO.get(novoUsuario) != null) {
				erros += "USU�RIO J� EXISTE NA BASE DE DADOS.\n";
			}
		}

		if (novoNome.length() < 1) {
			erros += "NOME N�O PODE ESTAR VAZIO.\n";
		}

		if (senhaNova.length() < 1) {
			erros += "SENHA N�O PODE ESTAR VAZIA.\n";
		}

		if (!senhaNova.equals(repetirSenhaNova)) {
			erros += "SENHA INFORMADA E A REPETI��O DELA N�O SE CORRESPONDEM.\n";
		}

		if (novoAnimal.length() < 1) {
			erros += "ANIMAL FAVORITO N�O PODE ESTAR VAZIO.\n";
		}

		if (erros.length() > 0) {
			Utils.dialogoOK(stage, "ERRO(S)", erros);
		} else {
			Usuario usuarioNovo = new Usuario();
			usuarioNovo.setUsuario(novoUsuario);
			usuarioNovo.setNome(novoNome);
			usuarioNovo.setSenha(BCrypt.hashpw(senhaNova, BCrypt.gensalt()));
			usuarioNovo.setCor(novaCor);
			usuarioNovo.setAnimal(novoAnimal);

			UsuarioDAO.save(usuarioNovo);

			Utils.dialogoOK(stage, "SUCESSO", "USU�RIO CADASTRADO COM SUCESSO.");
			stage.close();
		}

	}

	public void setStage(Stage stage) {
		this.stage = stage;
	}
}
