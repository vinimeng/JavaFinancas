package form;

import java.net.URL;

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
				Stage principalFormulario = new Stage();
				principalFormulario.setTitle(PRINCIPALTITULO);

				FXMLLoader loaderFXML = new FXMLLoader();
				URL principalFXML = getClass().getResource(PRINCIPALFMXL);
				loaderFXML.setLocation(principalFXML);

				try {
					AnchorPane principalPane = loaderFXML.load();
					Scene scene = new Scene(principalPane);

					principalFormulario.setScene(scene);

					ObservableList<Image> icons = stage.getIcons();

					if (!icons.isEmpty()) {
						Image logo = icons.get(0);
						principalFormulario.getIcons().add(logo);
					}

					principalFormulario.setResizable(false);

					PrincipalController principalController = loaderFXML.getController();
					principalController.setStage(principalFormulario);
					principalController.initialize(user);

					principalFormulario.show();
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
		Stage esqueciMinhaSenhaFormulario = new Stage();
		esqueciMinhaSenhaFormulario.setTitle(ESQUECIMINHASENHATITULO);

		FXMLLoader loaderFXML = new FXMLLoader();
		URL esqueciMinhaSenhaFXML = getClass().getResource(ESQUECIMINHASENHAFMXL);
		loaderFXML.setLocation(esqueciMinhaSenhaFXML);

		try {
			AnchorPane esqueciMinhaSenhaPane = loaderFXML.load();
			Scene scene = new Scene(esqueciMinhaSenhaPane);

			esqueciMinhaSenhaFormulario.setScene(scene);

			ObservableList<Image> icons = stage.getIcons();

			if (!icons.isEmpty()) {
				Image logo = icons.get(0);
				esqueciMinhaSenhaFormulario.getIcons().add(logo);
			}

			esqueciMinhaSenhaFormulario.setResizable(false);

			EsqueciMinhaSenhaController esqueciMinhaSenhaController = loaderFXML.getController();
			esqueciMinhaSenhaController.setStage(esqueciMinhaSenhaFormulario);

			esqueciMinhaSenhaFormulario.initModality(Modality.WINDOW_MODAL);
			esqueciMinhaSenhaFormulario.initOwner(stage);
			esqueciMinhaSenhaFormulario.showAndWait();
		} catch (Exception e) {
			Utils.dialogoOK(stage, "ERRO(S)",
					"NÃO FOI POSSÍVEL CARREGAR A TELA DE RECUPERAÇÃO DE SENHA. MOTIVO: " + e.getMessage() + ".");
		}
	}

	@FXML
	private void cadastroBtnActionPerformed(ActionEvent event) {
		Stage cadastroFormulario = new Stage();
		cadastroFormulario.setTitle(CADASTROTITULO);

		FXMLLoader loaderFXML = new FXMLLoader();
		URL cadastroFXML = getClass().getResource(CADASTROFMXL);
		loaderFXML.setLocation(cadastroFXML);

		try {
			AnchorPane cadastroPane = loaderFXML.load();
			Scene scene = new Scene(cadastroPane);

			cadastroFormulario.setScene(scene);

			ObservableList<Image> icons = stage.getIcons();

			if (!icons.isEmpty()) {
				Image logo = icons.get(0);
				cadastroFormulario.getIcons().add(logo);
			}

			cadastroFormulario.setResizable(false);

			CadastroController cadastroController = loaderFXML.getController();
			cadastroController.setStage(cadastroFormulario);

			cadastroFormulario.initModality(Modality.WINDOW_MODAL);
			cadastroFormulario.initOwner(stage);
			cadastroFormulario.showAndWait();
		} catch (Exception e) {
			Utils.dialogoOK(stage, "ERRO(S)",
					"NÃO FOI POSSÍVEL CARREGAR A TELA DE CADASTRO. MOTIVO: " + e.getMessage() + ".");
		}
	}

	public void setStage(Stage stage) {
		this.stage = stage;
	}
}
