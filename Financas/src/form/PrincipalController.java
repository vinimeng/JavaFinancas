package form;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.NumberFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

import dao.MovimentacaoDao;
import entity.Movimentacao;
import entity.Usuario;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;
import util.Mes;

public class PrincipalController {
	@FXML
	private Label usuario;

	@FXML
	private ComboBox<Integer> ano;

	@FXML
	private ComboBox<Mes> mes;

	@FXML
	private Button trocarSenhaBtn;

	@FXML
	private Button refreshBtn;

	@FXML
	private Button criarMovimentacaoBtn;

	@FXML
	private Button excluirMovimentacaoBtn;

	@FXML
	private TableView<Movimentacao> movimentacoes;

	@FXML
	private TableColumn<Movimentacao, String> dataColuna;

	@FXML
	private TableColumn<Movimentacao, String> categoriaColuna;

	@FXML
	private TableColumn<Movimentacao, String> valorColuna;

	@FXML
	private TableColumn<Movimentacao, String> tipoColuna;

	@FXML
	private TableColumn<Movimentacao, String> pagoColuna;

	@FXML
	private TableColumn<Movimentacao, String> descricaoColuna;

	@FXML
	private Label saldoMesSelecionado;

	@FXML
	private Label saldoMesPrevistoSelecionado;

	@FXML
	private TableView<Movimentacao> ultimaMovimentacao;

	@FXML
	private TableColumn<Movimentacao, String> dataColunaUltimaMovimentacao;

	@FXML
	private TableColumn<Movimentacao, String> categoriaColunaUltimaMovimentacao;

	@FXML
	private TableColumn<Movimentacao, String> valorColunaUltimaMovimentacao;

	@FXML
	private TableColumn<Movimentacao, String> tipoColunaUltimaMovimentacao;

	@FXML
	private TableColumn<Movimentacao, String> pagoColunaUltimaMovimentacao;

	@FXML
	private TableColumn<Movimentacao, String> descricaoColunaUltimaMovimentacao;

	@FXML
	private Label saldoAtual;

	@FXML
	private Label saldoPrevisto;

	private Stage stage;

	private Usuario usuarioLogado;

	public void initialize(Usuario usuarioLogado) {
		this.usuarioLogado = usuarioLogado;

		usuario.setText(this.usuarioLogado.getNome());

		LocalDateTime lt = LocalDateTime.now();

		mes.getItems().add(Mes.JANEIRO);
		mes.getItems().add(Mes.FEVEREIRO);
		mes.getItems().add(Mes.MARCO);
		mes.getItems().add(Mes.ABRIL);
		mes.getItems().add(Mes.MAIO);
		mes.getItems().add(Mes.JUNHO);
		mes.getItems().add(Mes.JULHO);
		mes.getItems().add(Mes.AGOSTO);
		mes.getItems().add(Mes.SETEMBRO);
		mes.getItems().add(Mes.OUTUBRO);
		mes.getItems().add(Mes.NOVEMBRO);
		mes.getItems().add(Mes.DEZEMBRO);

		mes.setValue(Mes.getMes(lt.getMonthValue()));

		// MOVIMENTAÇÕES
		dataColuna.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Movimentacao, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<Movimentacao, String> param) {
						if (param.getValue() != null) {
							return new SimpleStringProperty(param.getValue().getData().toLocalDateTime()
									.format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT)));
						} else {
							return new SimpleStringProperty("-");
						}
					}
				});
		dataColuna.setStyle("-fx-alignment: CENTER");

		categoriaColuna.setCellValueFactory(new PropertyValueFactory<Movimentacao, String>("categoria"));
		categoriaColuna.setStyle("-fx-alignment: CENTER");

		valorColuna.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Movimentacao, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<Movimentacao, String> param) {
						if (param.getValue() != null) {
							NumberFormat dinheiro = NumberFormat.getCurrencyInstance();
							dinheiro.setMinimumFractionDigits(2);
							dinheiro.setMaximumFractionDigits(2);

							int receitaDespesa = param.getValue().getTipo().getModificador();

							return new SimpleStringProperty(
									dinheiro.format(receitaDespesa * param.getValue().getValor().doubleValue()));
						} else {
							return new SimpleStringProperty("-");
						}
					}
				});
		valorColuna.setStyle("-fx-alignment: CENTER-RIGHT");

		tipoColuna.setCellValueFactory(new PropertyValueFactory<Movimentacao, String>("tipo"));
		tipoColuna.setStyle("-fx-alignment: CENTER");

		pagoColuna.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Movimentacao, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<Movimentacao, String> param) {
						if (param.getValue() != null) {
							String pagoNaoPago = "EM ABERTO";

							if (param.getValue().isPago()) {
								pagoNaoPago = "CONFIRMADO";
							}

							return new SimpleStringProperty(pagoNaoPago);
						} else {
							return new SimpleStringProperty("-");
						}
					}
				});
		pagoColuna.setStyle("-fx-alignment: CENTER");

		descricaoColuna.setCellValueFactory(new PropertyValueFactory<Movimentacao, String>("descricao"));

		// ÚLTIMA MOVIMENTAÇÃO
		dataColunaUltimaMovimentacao.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Movimentacao, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<Movimentacao, String> param) {
						if (param.getValue() != null) {
							return new SimpleStringProperty(param.getValue().getData().toLocalDateTime()
									.format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT)));
						} else {
							return new SimpleStringProperty("-");
						}
					}
				});
		dataColunaUltimaMovimentacao.setStyle("-fx-alignment: CENTER");

		categoriaColunaUltimaMovimentacao
				.setCellValueFactory(new PropertyValueFactory<Movimentacao, String>("categoria"));
		categoriaColunaUltimaMovimentacao.setStyle("-fx-alignment: CENTER");

		valorColunaUltimaMovimentacao.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Movimentacao, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<Movimentacao, String> param) {
						if (param.getValue() != null) {
							NumberFormat dinheiro = NumberFormat.getCurrencyInstance();
							dinheiro.setMinimumFractionDigits(2);
							dinheiro.setMaximumFractionDigits(2);

							int receitaDespesa = param.getValue().getTipo().getModificador();

							return new SimpleStringProperty(
									dinheiro.format(receitaDespesa * param.getValue().getValor().doubleValue()));
						} else {
							return new SimpleStringProperty("-");
						}
					}
				});
		valorColunaUltimaMovimentacao.setStyle("-fx-alignment: CENTER-RIGHT");

		tipoColunaUltimaMovimentacao.setCellValueFactory(new PropertyValueFactory<Movimentacao, String>("tipo"));
		tipoColunaUltimaMovimentacao.setStyle("-fx-alignment: CENTER");

		pagoColunaUltimaMovimentacao.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Movimentacao, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<Movimentacao, String> param) {
						if (param.getValue() != null) {
							String pagoNaoPago = "EM ABERTO";

							if (param.getValue().isPago()) {
								pagoNaoPago = "CONFIRMADO";
							}

							return new SimpleStringProperty(pagoNaoPago);
						} else {
							return new SimpleStringProperty("-");
						}
					}
				});
		pagoColunaUltimaMovimentacao.setStyle("-fx-alignment: CENTER");

		descricaoColunaUltimaMovimentacao
				.setCellValueFactory(new PropertyValueFactory<Movimentacao, String>("descricao"));

		// PREENCHER DADOS
		fillData(true, true);
	}

	public void fillData(boolean fillYear, boolean selectCurrentYear) {
		MovimentacaoDao md = new MovimentacaoDao();

		if (fillYear) {
			// PREENCHER ANOS
			fillYear(selectCurrentYear);
		}

		if (!ano.getItems().isEmpty()) {
			LocalDateTime lt = LocalDateTime.of(ano.getValue(), mes.getValue().getMes(), 1, 0, 0, 0);

			Timestamp dataInicial = Timestamp.valueOf(LocalDateTime.of(lt.getYear(), lt.getMonth(), 1, 0, 0, 0));
			Timestamp dataFinal = Timestamp.valueOf(LocalDateTime.of(lt.getYear(), lt.getMonth(),
					lt.getMonth().length(lt.toLocalDate().isLeapYear()), 23, 59, 59));

			ObservableList<Movimentacao> olm = FXCollections
					.observableList(md.getByMonth(this.usuarioLogado, dataInicial, dataFinal));
			movimentacoes.setItems(olm);

			ObservableList<Movimentacao> olmp = FXCollections.observableList(md.getLastPayed(this.usuarioLogado));
			ultimaMovimentacao.setItems(olmp);

			NumberFormat dinheiro = NumberFormat.getCurrencyInstance();
			dinheiro.setMinimumFractionDigits(2);
			dinheiro.setMaximumFractionDigits(2);

			BigDecimal saldoMesAtual;
			BigDecimal saldoMesPrevisto;
			BigDecimal saldoAtual;
			BigDecimal saldoPrevisto;

			if (olm.isEmpty()) {
				saldoMesAtual = BigDecimal.ZERO;
				saldoMesPrevisto = BigDecimal.ZERO;
			} else {
				saldoMesAtual = md.getSaldoMesAtual(usuarioLogado, dataInicial, dataFinal);
				saldoMesPrevisto = md.getSaldoMesPrevisto(usuarioLogado, dataInicial, dataFinal);

				if (saldoMesAtual == null) {
					saldoMesAtual = BigDecimal.ZERO;
				}

				if (saldoMesPrevisto == null) {
					saldoMesPrevisto = BigDecimal.ZERO;
				}
			}

			saldoAtual = md.getSaldoAtual(usuarioLogado);
			saldoPrevisto = md.getSaldoPrevisto(usuarioLogado);

			if (saldoAtual == null) {
				saldoAtual = BigDecimal.ZERO;
			}

			if (saldoPrevisto == null) {
				saldoPrevisto = BigDecimal.ZERO;
			}

			this.saldoMesSelecionado.setText(dinheiro.format(saldoMesAtual.doubleValue()));
			this.saldoMesPrevistoSelecionado.setText(dinheiro.format(saldoMesPrevisto.doubleValue()));
			this.saldoAtual.setText(dinheiro.format(saldoAtual.doubleValue()));
			this.saldoPrevisto.setText(dinheiro.format(saldoPrevisto.doubleValue()));

			if (saldoMesAtual.doubleValue() > 0) {
				this.saldoMesSelecionado.setTextFill(Color.GREEN);
			} else if (saldoMesAtual.doubleValue() == 0) {
				this.saldoMesSelecionado.setTextFill(Color.BLACK);
			} else {
				this.saldoMesSelecionado.setTextFill(Color.RED);
			}

			if (saldoMesPrevisto.doubleValue() > 0) {
				this.saldoMesPrevistoSelecionado.setTextFill(Color.GREEN);
			} else if (saldoMesPrevisto.doubleValue() == 0) {
				this.saldoMesPrevistoSelecionado.setTextFill(Color.BLACK);
			} else {
				this.saldoMesPrevistoSelecionado.setTextFill(Color.RED);
			}

			if (saldoAtual.doubleValue() > 0) {
				this.saldoAtual.setTextFill(Color.GREEN);
			} else if (saldoAtual.doubleValue() == 0) {
				this.saldoAtual.setTextFill(Color.BLACK);
			} else {
				this.saldoAtual.setTextFill(Color.RED);
			}

			if (saldoPrevisto.doubleValue() > 0) {
				this.saldoPrevisto.setTextFill(Color.GREEN);
			} else if (saldoPrevisto.doubleValue() == 0) {
				this.saldoPrevisto.setTextFill(Color.BLACK);
			} else {
				this.saldoPrevisto.setTextFill(Color.RED);
			}
		}

	}

	private void fillYear(boolean selectCurrentYear) {
		LocalDateTime ldt = LocalDateTime.now();
		MovimentacaoDao md = new MovimentacaoDao();

		int selectedYear = 0;
		if (!selectCurrentYear) {
			selectedYear = ano.getValue();
		}

		ObservableList<Integer> ola = FXCollections.observableList(md.getYears(this.usuarioLogado));

		ano.setItems(ola);

		if (!ola.isEmpty()) {
			if (selectCurrentYear) {
				ano.setValue(ldt.getYear());
			} else {
				ano.setValue(selectedYear);
			}
		}
	}

	@FXML
	private void refreshBtnActionPerformed(ActionEvent event) {
		EventHandler<ActionEvent> filter = e -> e.consume();
		ano.addEventFilter(ActionEvent.ACTION, filter);
		fillData(true, false);
		ano.removeEventFilter(ActionEvent.ACTION, filter);
	}

	@FXML
	private void comboBoxActionPerformed(ActionEvent event) {
		fillData(false, false);
	}

	@FXML
	private void criarMovimentacaoBtnActionPerformed(ActionEvent event) {
		Stage movimentacaoForm = new Stage();
		movimentacaoForm.setTitle("MOVIMENTAÇÃO");

		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("/form/Movimentacao.fxml"));

		try {
			AnchorPane ap = loader.load();
			Scene scene = new Scene(ap);

			movimentacaoForm.setScene(scene);
			movimentacaoForm.getIcons().add(stage.getIcons().get(0));
			movimentacaoForm.setResizable(false);

			MovimentacaoController mc = loader.getController();
			mc.setStage(movimentacaoForm);
			mc.initialize(this.usuarioLogado);

			movimentacaoForm.initModality(Modality.WINDOW_MODAL);
			movimentacaoForm.initOwner(stage);
			movimentacaoForm.showAndWait();

			EventHandler<ActionEvent> filter = e -> e.consume();
			ano.addEventFilter(ActionEvent.ACTION, filter);
			fillData(true, false);
			ano.removeEventFilter(ActionEvent.ACTION, filter);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@FXML
	private void excluirMovimentacaoBtnActionPerformed(ActionEvent event) {
		Movimentacao movimentacaoSelecionada = movimentacoes.getSelectionModel().getSelectedItem();

		if (movimentacaoSelecionada != null) {
			Dialog<ButtonType> d = new Dialog<>();
			ButtonType type = new ButtonType("SIM", ButtonData.YES);
			ButtonType type2 = new ButtonType("NÃO", ButtonData.NO);
			d.setTitle("EXCLUSÃO");
			Stage s = (Stage) d.getDialogPane().getScene().getWindow();
			s.getIcons().add(stage.getIcons().get(0));
			d.setContentText("DESEJA MESMO EXCLUIR ESSA MOVIMENTAÇÃO?");
			d.getDialogPane().getButtonTypes().add(type);
			d.getDialogPane().getButtonTypes().add(type2);
			d.initOwner(stage);
			d.showAndWait().ifPresent(response -> {
				if (response == type) {
					MovimentacaoDao md = new MovimentacaoDao();

					movimentacaoSelecionada.setDeletado(true);

					md.update(movimentacaoSelecionada);

					EventHandler<ActionEvent> filter = e -> e.consume();
					ano.addEventFilter(ActionEvent.ACTION, filter);
					fillData(true, false);
					ano.removeEventFilter(ActionEvent.ACTION, filter);
				}
			});
		}
	}

	@FXML
	private void trocarSenhaBtnActionPerformed(ActionEvent event) {
		Stage trocarSenhaForm = new Stage();
		trocarSenhaForm.setTitle("TROCAR SENHA");

		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("/form/TrocarSenha.fxml"));

		try {
			AnchorPane ap = loader.load();
			Scene scene = new Scene(ap);

			trocarSenhaForm.setScene(scene);
			trocarSenhaForm.getIcons().add(stage.getIcons().get(0));
			trocarSenhaForm.setResizable(false);

			TrocarSenhaController tsc = loader.getController();
			tsc.setStage(trocarSenhaForm);
			tsc.initialize(usuarioLogado);

			trocarSenhaForm.initModality(Modality.WINDOW_MODAL);
			trocarSenhaForm.initOwner(stage);
			trocarSenhaForm.showAndWait();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void setStage(Stage stage) {
		this.stage = stage;
	}
}
