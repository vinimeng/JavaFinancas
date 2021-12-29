package form;

import dao.UsuarioDao;
import entity.Usuario;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Dialog;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import util.BCrypt;

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
		String novaCor = String.format(
			"#%02x%02x%02x",
			(int) (255 * corFavorita.getValue().getRed()),
			(int) (255 * corFavorita.getValue().getGreen()),
			(int) (255 * corFavorita.getValue().getBlue())
		);
		String novoAnimal = animalFavorito.getText();
		String erros = "";
		UsuarioDao ud = new UsuarioDao();
		
		if (novoUsuario.length() < 1) {
			erros += "USUÁRIO NÃO PODE ESTAR VAZIO.\n";
		} else {
			if (ud.get(novoUsuario) != null) {
				erros += "USUÁRIO JÁ EXISTE NA BASE DE DADOS.\n";
			}
		}
		
		if (novoNome.length() < 1) {
			erros += "NOME NÃO PODE ESTAR VAZIO.\n";
		}
		
		if (senhaNova.length() < 1) {
			erros += "SENHA NÃO PODE ESTAR VAZIA.\n";
		}
		
		if (!senhaNova.equals(repetirSenhaNova)) {
			erros += "SENHA INFORMADA E A REPETIÇÃO DELA NÃO SE CORRESPONDEM.\n";
		}
		
		if (novoAnimal.length() < 1) {
			erros += "ANIMAL FAVORITO NÃO PODE ESTAR VAZIO.\n";
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
			Usuario usuarioNovo = new Usuario();
			usuarioNovo.setUsuario(novoUsuario);
			usuarioNovo.setNome(novoNome);
			usuarioNovo.setSenha(BCrypt.hashpw(senhaNova, BCrypt.gensalt()));
			usuarioNovo.setPrimeira_resposta(novaCor);
			usuarioNovo.setSegunda_resposta(novoAnimal);
			
			ud.save(usuarioNovo);
			
			Dialog<String> d = new Dialog<>();
			ButtonType type = new ButtonType("OK", ButtonData.OK_DONE);
			d.setTitle("SUCESSO");
			Stage s = (Stage) d.getDialogPane().getScene().getWindow();
			s.getIcons().add(stage.getIcons().get(0));
			d.setContentText("USUÁRIO CADASTRADO COM SUCESSO.");
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
