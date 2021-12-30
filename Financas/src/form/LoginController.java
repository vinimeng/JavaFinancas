package form;

import dao.UsuarioDAO;
import entity.Usuario;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import util.BCrypt;
import util.Utils;

public class LoginController {

	@FXML
	private TextField usuario;

	@FXML
	private PasswordField senha;

	@FXML
	private Hyperlink esqueciMinhaSenhaLink;

	@FXML
	private Button cadastroBtn;

	@FXML
	private Button loginBtn;

	private Stage stage;

	private final String PRINCIPALTITULO = "FINANÇAS PESSOAIS - TELA PRINCIPAL";
	private final String PRINCIPALFMXL = "/form/Principal.fxml";

	private final String ESQUECIMINHASENHATITULO = "ESQUECI MINHA SENHA";
	private final String ESQUECIMINHASENHAFMXL = "/form/EsqueciMinhaSenha.fxml";

	private final String CADASTROTITULO = "CADASTRO";
	private final String CADASTROFMXL = "/form/Cadastro.fxml";

	@FXML
	private void loginBtnActionPerformed(ActionEvent event) {
		String usuario = this.usuario.getText();
		String senha = this.senha.getText();

		Usuario user = UsuarioDAO.get(usuario);

		if (user != null) {
			if (BCrypt.checkpw(senha, user.getSenha())) {
				Stage principalForm = new Stage();
				principalForm.setTitle(PRINCIPALTITULO);

				FXMLLoader loader = new FXMLLoader();
				loader.setLocation(getClass().getResource(PRINCIPALFMXL));

				try {
					AnchorPane ap = loader.load();
					Scene scene = new Scene(ap);

					principalForm.setScene(scene);

					ObservableList<Image> icons = stage.getIcons();

					if (!icons.isEmpty()) {
						Image logo = icons.get(0);
						principalForm.getIcons().add(logo);
					}

					principalForm.setResizable(false);

					PrincipalController pc = loader.getController();
					pc.setStage(principalForm);
					pc.initialize(user);

					principalForm.show();
					stage.close();
				} catch (Exception e) {
					Utils.dialogoOK(stage, "ERRO(S)",
							"NÃO FOI POSSÍVEL CARREGAR A TELA PRINCIPAL. MOTIVO: " + e.getMessage() + ".");
				}
			} else {
				Utils.dialogoOK(stage, "ERRO(S)", "A SENHA INFORMADA ESTÁ INCORRETA.");
			}
		} else {
			Utils.dialogoOK(stage, "ERRO(S)", "O USUÁRIO INFORMADO NÃO EXISTE NA BASE DE DADOS.");
		}
	}

	@FXML
	private void esqueciMinhaSenhaLinkActionPerformed(ActionEvent event) {
		Stage esqueciMinhaSenhaForm = new Stage();
		esqueciMinhaSenhaForm.setTitle(ESQUECIMINHASENHATITULO);

		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource(ESQUECIMINHASENHAFMXL));

		try {
			AnchorPane ap = loader.load();
			Scene scene = new Scene(ap);

			esqueciMinhaSenhaForm.setScene(scene);

			ObservableList<Image> icons = stage.getIcons();

			if (!icons.isEmpty()) {
				Image logo = icons.get(0);
				esqueciMinhaSenhaForm.getIcons().add(logo);
			}

			esqueciMinhaSenhaForm.setResizable(false);

			EsqueciMinhaSenhaController emsc = loader.getController();
			emsc.setStage(esqueciMinhaSenhaForm);

			esqueciMinhaSenhaForm.initModality(Modality.WINDOW_MODAL);
			esqueciMinhaSenhaForm.initOwner(stage);
			esqueciMinhaSenhaForm.showAndWait();
		} catch (Exception e) {
			Utils.dialogoOK(stage, "ERRO(S)",
					"NÃO FOI POSSÍVEL CARREGAR A TELA DE RECUPERAÇÃO DE SENHA. MOTIVO: " + e.getMessage() + ".");
		}
	}

	@FXML
	private void cadastroBtnActionPerformed(ActionEvent event) {
		Stage cadastroForm = new Stage();
		cadastroForm.setTitle(CADASTROTITULO);

		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource(CADASTROFMXL));

		try {
			AnchorPane ap = loader.load();
			Scene scene = new Scene(ap);

			cadastroForm.setScene(scene);

			ObservableList<Image> icons = stage.getIcons();

			if (!icons.isEmpty()) {
				Image logo = icons.get(0);
				cadastroForm.getIcons().add(logo);
			}

			cadastroForm.setResizable(false);

			CadastroController emsc = loader.getController();
			emsc.setStage(cadastroForm);

			cadastroForm.initModality(Modality.WINDOW_MODAL);
			cadastroForm.initOwner(stage);
			cadastroForm.showAndWait();
		} catch (Exception e) {
			Utils.dialogoOK(stage, "ERRO(S)",
					"NÃO FOI POSSÍVEL CARREGAR A TELA DE CADASTRO. MOTIVO: " + e.getMessage() + ".");
		}
	}

	public void setStage(Stage stage) {
		this.stage = stage;
	}
}
