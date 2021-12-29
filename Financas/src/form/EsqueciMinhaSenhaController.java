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
		String corEsqueceu = String.format(
			"#%02x%02x%02x",
			(int) (255 * corFavorita.getValue().getRed()),
			(int) (255 * corFavorita.getValue().getGreen()),
			(int) (255 * corFavorita.getValue().getBlue())
		);
		String animalEsqueceu = animalFavorito.getText();
		String erros = "";
		UsuarioDao ud = new UsuarioDao();
		
		if (usuarioEsqueceu.length() < 1) {
			erros += "USUÁRIO NÃO PODE ESTAR VAZIO.\n";
		} else {
			usuarioEsquecido = ud.get(usuarioEsqueceu);
			
			if (usuarioEsquecido == null) {
				erros += "USUÁRIO NÃO EXISTE NA BASE DE DADOS.\n";
			}
		}
		
		if (animalEsqueceu.length() < 1) {
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
			erros = "";
			
			if (corEsqueceu.equals(usuarioEsquecido.getPrimeira_resposta())) {
				if (!animalEsqueceu.equals(usuarioEsquecido.getSegunda_resposta())) {
					erros += "RESPOSTAS NÃO CORRESPONDEM AS CADASTRADAS PELO USUÁRIO.";
				}
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
			erros += "A NOVA SENHA INFORMADA NÃO PODE SER VAZIA.\n";
		}
		
		if (!senhaNova.equals(repetirSenhaNova)) {
			erros += "A NOVA SENHA INFORMADA E A REPETIÇÃO DELA NÃO SE CORRESPONDEM.\n";
		}
		
		if (usuarioEsquecido == null) {
			erros += "NENHUM USUÁRIO ENCONTRADO PARA REALIZAR A TROCA DA SENHA.\n";
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
			usuarioEsquecido.setSenha(BCrypt.hashpw(senhaNova, BCrypt.gensalt()));
			
			UsuarioDao ud = new UsuarioDao();
			ud.update(usuarioEsquecido);
			
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
