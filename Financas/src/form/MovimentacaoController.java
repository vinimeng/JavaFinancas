package form;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Locale;

import dao.CategoriaDAO;
import dao.MovimentacaoDAO;
import dao.Tipos_movimentacaoDAO;
import entity.Categoria;
import entity.Movimentacao;
import entity.Tipos_movimentacao;
import entity.Usuario;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
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

	private Usuario usuarioLogado;

	public void initialize(Usuario usuarioLogado) {
		this.usuarioLogado = usuarioLogado;

		ObservableList<Tipos_movimentacao> olm = FXCollections.observableList(Tipos_movimentacaoDAO.getAll());
		tipo.setItems(olm);

		if (!olm.isEmpty()) {
			tipo.setValue(olm.get(0));
		}

		ObservableList<Categoria> olmp = FXCollections.observableList(CategoriaDAO.getAll());
		categoria.setItems(olmp);

		if (!olmp.isEmpty()) {
			categoria.setValue(olmp.get(0));
		}

		data.setLocalDateTime(LocalDateTime.now());

		pago.getItems().add("CONFIRMADO");
		pago.getItems().add("EM ABERTO");
		pago.setValue("CONFIRMADO");

		valor.initialize(new Locale("pt", "BR"), BigDecimal.ZERO);

		TextFormatter<String> max255char = new TextFormatter<>(
				change -> change.getControlNewText().length() <= 255 ? change : null);
		descricao.setTextFormatter(max255char);
	}

	@FXML
	private void criarMovimentacaoBtnActionPerformed(ActionEvent event) {
		Movimentacao novaMovimentacao = new Movimentacao();

		novaMovimentacao.setId_usuario(usuarioLogado);
		novaMovimentacao.setTipo(tipo.getValue());
		novaMovimentacao.setCategoria(categoria.getValue());
		novaMovimentacao.setData(Timestamp.valueOf(data.getLocalDateTime()));

		if (pago.getValue().equals("CONFIRMADO")) {
			novaMovimentacao.setPago(true);
		} else {
			novaMovimentacao.setPago(false);
		}

		novaMovimentacao.setValor(valor.getAmount());
		novaMovimentacao.setDescricao(descricao.getText());
		novaMovimentacao.setDeletado(false);

		MovimentacaoDAO.save(novaMovimentacao);
		stage.close();
	}

	public void setStage(Stage stage) {
		this.stage = stage;
	}
}
