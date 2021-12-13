package form;

import dao.UsuarioDao;
import entity.Usuario;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import util.BCrypt;

public class LoginController {

	@FXML
    private TextField usuario;
	
	@FXML
    private PasswordField senha;
	
	@FXML
    private Button cadastrarseBtn;
	
	@FXML
    private Button loginBtn;
	
	private Stage stage;
	
	@FXML
	private void loginBtnActionPerformed(ActionEvent event) {
		String usuario = this.usuario.getText();
		String senha = this.senha.getText();
		
		UsuarioDao ud = new UsuarioDao();
		
		Usuario user  = ud.get(usuario);
		
		if (user != null) {
			if (BCrypt.checkpw(senha, user.getSenha())) {
				Stage principalForm = new Stage();
				principalForm.setTitle("FINANÇAS PESSOAIS - TELA PRINCIPAL");
				
				FXMLLoader loader = new FXMLLoader();
				loader.setLocation(getClass().getResource("/form/Principal.fxml"));
				
				try {
					AnchorPane ap = loader.load();
					Scene scene = new Scene(ap);
					
					principalForm.setScene(scene);
					principalForm.getIcons().add(stage.getIcons().get(0));
					principalForm.setResizable(false);
					
					PrincipalController pc = loader.getController();
					pc.setStage(principalForm);
					pc.initialize(user);
					
					principalForm.show();
					stage.close();					
				} catch(Exception e) {
					e.printStackTrace();
				}
			} else {
				Dialog <String> d = new Dialog<String>();
				ButtonType type = new ButtonType("OK", ButtonData.OK_DONE);
				d.setTitle("SENHA INCORRETA");
				Stage s = (Stage) d.getDialogPane().getScene().getWindow();
				s.getIcons().add(stage.getIcons().get(0));
				d.setContentText("A SENHA INFORMADA ESTÁ INCORRETA.");
				d.getDialogPane().getButtonTypes().add(type);
				d.initOwner(stage);
				d.showAndWait();
			}
		} else {
			Dialog <String> d = new Dialog<String>();
			ButtonType type = new ButtonType("OK", ButtonData.OK_DONE);
			d.setTitle("USUÁRIO INEXISTENTE");
			Stage s = (Stage) d.getDialogPane().getScene().getWindow();
			s.getIcons().add(stage.getIcons().get(0));
			d.setContentText("O USUÁRIO INFORMADO NÃO EXISTE NA BASE DE DADOS.");
			d.getDialogPane().getButtonTypes().add(type);
			d.initOwner(stage);
			d.showAndWait();
		}
	}
	
	public void setLoginStage(Stage stage) {
		this.stage = stage;	
	}
}
