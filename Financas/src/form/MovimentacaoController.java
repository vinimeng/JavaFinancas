package form;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Locale;

import dao.CategoriaDao;
import dao.Tipos_movimentacaoDao;
import entity.Categoria;
import entity.Tipos_movimentacao;
import entity.Usuario;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextFormatter;
import javafx.stage.Stage;
import jfxtras.scene.control.LocalDateTimePicker;
import util.CurrencyField;

public class MovimentacaoController {

	@FXML
	private ComboBox<Tipos_movimentacao> tipo;

	@FXML
	private ComboBox<Categoria> categoria;

	@FXML
	private LocalDateTimePicker data;

	@FXML
	private CurrencyField valor;

	@FXML
	private ComboBox<String> pago;

	@FXML
	private TextArea descricao;

	@FXML
	private Button criarMovimentacaoBtn;

	private Stage stage;

	@SuppressWarnings("unused")
	private Usuario usuarioLogado;

	public void initialize(Usuario usuarioLogado) {
		this.usuarioLogado = usuarioLogado;

		Tipos_movimentacaoDao tmd = new Tipos_movimentacaoDao();
		CategoriaDao cd = new CategoriaDao();

		ObservableList<Tipos_movimentacao> olm = FXCollections.observableList(tmd.getAll());
		tipo.setItems(olm);

		ObservableList<Categoria> olmp = FXCollections.observableList(cd.getAll());
		categoria.setItems(olmp);

		data.setLocalDateTime(LocalDateTime.now());
		
		pago.getItems().add("CONFIRMADO");
		pago.getItems().add("EM ABERTO");
		
		valor.initialize(new Locale("pt", "BR"), BigDecimal.ZERO);

		descricao.setTextFormatter(new TextFormatter<String>(change ->
        change.getControlNewText().length() <= 255 ? change : null));
	}
	
	@FXML
	private void criarMovimentacaoBtnActionPerformed(ActionEvent event) {
		Tipos_movimentacao tipoNovo = tipo.getValue();
		Categoria categoriaNovo = categoria.getValue();
		//LocalDateTime dataNovo = data.getDateTimeValue();
		//BigDecimal valorNovo = new BigDecimal(valor.getAmount());
		String pgNovo = pago.getValue();
		//String descricaoNovo = descricao.getText();
		
		System.out.println(data.getLocalDateTime());
		System.out.println(valor.getAmount());
		
		String erros = "";
		
		if (tipoNovo == null) {
			erros += "NECESSÁRIO ESCOLHER UM TIPO DE MOVIMENTAÇÃO.\n";
		}
		
		if (categoriaNovo == null) {
			erros += "NECESSÁRIO ESCOLHER UMA CATEGORIA.\n";
		}
		
		//if (dataNovo == null) {
		//	erros += "NECESSÁRIO PREENCHER UMA DATA VÁLIDA.\n";
		//}
		
		if (pgNovo == null) {
			erros += "NECESSÁRIO INFORMAR SE A MOVIMENTAÇÃO JÁ ESTÁ PAGA.\n";
		}
		
		if (!erros.isEmpty()) {
			Dialog <String> d = new Dialog<>();
			ButtonType type = new ButtonType("OK", ButtonData.OK_DONE);
			d.setTitle("ERROS");
			Stage s = (Stage) d.getDialogPane().getScene().getWindow();
			s.getIcons().add(stage.getIcons().get(0));
			d.setContentText(erros);
			d.getDialogPane().getButtonTypes().add(type);
			d.initOwner(stage);
			d.showAndWait();
		} else {
			
		}		
	}

	public void setStage(Stage stage) {
		this.stage = stage;
	}
}
