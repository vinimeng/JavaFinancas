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
import util.Utils;

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
	private Button criarEditarMovimentacaoBtn;

	private Stage stage;

	private Usuario usuarioLogado;
	
	private Movimentacao movimentacao;

	public void initialize(Usuario usuarioLogado, Movimentacao movimentacao) {
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
		
		if (movimentacao != null) {
			this.movimentacao = movimentacao;
			
			tipo.setValue(movimentacao.getTipo());
			categoria.setValue(movimentacao.getCategoria());
			data.setLocalDateTime(movimentacao.getData().toLocalDateTime());
			
			String pagoOriginal = movimentacao.isPago() ? "CONFIRMADO" : "EM ABERTO";
			pago.setValue(pagoOriginal);
			
			valor.setAmount(movimentacao.getValor());
			descricao.setText(movimentacao.getDescricao());
			
			criarEditarMovimentacaoBtn.setText("EDITAR MOVIMENTAÇÃO");
			
			// AJUSTAR POSIÇÃO DO BOTÃO
			double layoutX = criarEditarMovimentacaoBtn.getTranslateX();
			criarEditarMovimentacaoBtn.setTranslateX(layoutX - 6);
		} else {
			this.movimentacao = null;
		}
	}

	@FXML
	private void criarEditarMovimentacaoBtnActionPerformed(ActionEvent event) {
		String acao;
		Movimentacao criarEditarMovimentacao;
		
		if (this.movimentacao == null) {
			acao = "CRIAR";
			criarEditarMovimentacao = new Movimentacao();
		} else {
			acao = "EDITAR";
			criarEditarMovimentacao = this.movimentacao;
		}

		criarEditarMovimentacao.setId_usuario(usuarioLogado);
		criarEditarMovimentacao.setTipo(tipo.getValue());
		criarEditarMovimentacao.setCategoria(categoria.getValue());
		criarEditarMovimentacao.setData(Timestamp.valueOf(data.getLocalDateTime()));

		if (pago.getValue().equals("CONFIRMADO")) {
			criarEditarMovimentacao.setPago(true);
		} else {
			criarEditarMovimentacao.setPago(false);
		}

		criarEditarMovimentacao.setValor(valor.getAmount());
		criarEditarMovimentacao.setDescricao(descricao.getText());
		criarEditarMovimentacao.setDeletado(false);

		boolean sucesso;
		
		if (this.movimentacao == null) {
			sucesso = MovimentacaoDAO.save(criarEditarMovimentacao);
		} else {
			sucesso = MovimentacaoDAO.update(criarEditarMovimentacao);
		}
		
		if (sucesso) {
			stage.close();
		} else {
			Utils.dialogoOK(stage, "ERRO(S)", "ERRO AO " + acao + " MOVIMENTAÇÃO.");
		}
	}

	public void setStage(Stage stage) {
		this.stage = stage;
	}
}
