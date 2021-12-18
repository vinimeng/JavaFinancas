package form;

import java.util.Locale;

import dao.CategoriaDao;
import dao.Tipos_movimentacaoDao;
import entity.Categoria;
import entity.Tipos_movimentacao;
import entity.Usuario;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextFormatter;
import javafx.stage.Stage;
import util.CurrencyField;
import util.DateTimePicker;

public class MovimentacaoController {

	@FXML
	private ComboBox<Tipos_movimentacao> tipo;

	@FXML
	private ComboBox<Categoria> categoria;

	@FXML
	private DateTimePicker data;

	@FXML
	private CurrencyField valor;

	@FXML
	private ComboBox<String> pago;

	@FXML
	private TextArea descricao;

	@FXML
	private Button criarMovimentacaoBtn;

	@SuppressWarnings("unused")
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

		data.setFormat("dd/MM/yyyy HH:mm:ss");

		pago.getItems().add("CONFIRMADO");
		pago.getItems().add("EM ABERTO");

		valor.initialize(new Locale("pt", "BR"), 0.00);

		descricao.setTextFormatter(new TextFormatter<String>(change ->
        change.getControlNewText().length() <= 255 ? change : null));
	}

	public void setStage(Stage stage) {
		this.stage = stage;
	}
}
