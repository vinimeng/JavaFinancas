package form;

import java.math.BigDecimal;
import java.net.URL;
import java.sql.Timestamp;
import java.text.NumberFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

import dao.MovimentacaoDAO;
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
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;
import util.Mes;
import util.Utils;

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
	private Button editarMovimentacaoBtn;

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

	private final String MOVIMENTACAOTITULO = "MOVIMENTAÇÃO";
	private final String MOVIMENTACAOFMXL = "/form/Movimentacao.fxml";

	private final String TROCARSENHATITULO = "TROCAR SENHA";
	private final String TROCARSENHAFMXL = "/form/TrocarSenha.fxml";

	public void initialize(Usuario usuarioLogado) {
		// USUÁRIO
		this.usuarioLogado = usuarioLogado;
		usuario.setText(this.usuarioLogado.getNome());

		// DATA DE AGORA
		LocalDateTime lt = LocalDateTime.now();

		// PREENCHER ESCOLHA DE MÊS E SELECIONAR MÊS ATUAL
		preencherMes();
		mes.setValue(Mes.getMes(lt.getMonthValue()));

		/* <>------ MOVIMENTAÇÕES ------<> */
		// COLUNA COM A DATA
		dataColuna.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Movimentacao, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<Movimentacao, String> param) {
						if (param.getValue() != null) {
							LocalDateTime data = param.getValue().getData().toLocalDateTime();
							return new SimpleStringProperty(dataFormatada(data));
						} else {
							return new SimpleStringProperty("-");
						}
					}
				});
		// COLUNA CENTRALIZADA
		dataColuna.setStyle("-fx-alignment: CENTER");

		// COLUNA COM A CATEGORIA
		categoriaColuna.setCellValueFactory(new PropertyValueFactory<Movimentacao, String>("categoria"));
		// COLUNA CENTRALIZADA
		categoriaColuna.setStyle("-fx-alignment: CENTER");

		// COLUNA COM O VALOR
		valorColuna.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Movimentacao, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<Movimentacao, String> param) {
						if (param.getValue() != null) {
							int modificador = param.getValue().getTipo().getModificador();
							BigDecimal valor = param.getValue().getValor();
							return new SimpleStringProperty(valorFormatado(modificador, valor));
						} else {
							return new SimpleStringProperty("-");
						}
					}
				});
		// COLUNA À DIREITA
		valorColuna.setStyle("-fx-alignment: CENTER-RIGHT");

		// COLUNA COM O TIPO
		tipoColuna.setCellValueFactory(new PropertyValueFactory<Movimentacao, String>("tipo"));
		// COLUNA CENTRALIZADA
		tipoColuna.setStyle("-fx-alignment: CENTER");

		// COLUNA COM A CONFIRMAÇÃO DE PAGAMENTO
		pagoColuna.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Movimentacao, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<Movimentacao, String> param) {
						if (param.getValue() != null) {
							boolean isPago = param.getValue().isPago();
							return new SimpleStringProperty(pagoNaoPago(isPago));
						} else {
							return new SimpleStringProperty("-");
						}
					}
				});
		// COLUNA CENTRALIZADA
		pagoColuna.setStyle("-fx-alignment: CENTER");

		// COLUNA COM A DESCRIÇÃO
		descricaoColuna.setCellValueFactory(new PropertyValueFactory<Movimentacao, String>("descricao"));

		/* <>------ ÚLTIMA MOVIMENTAÇÃO ------<> */
		// COLUNA COM A DATA
		dataColunaUltimaMovimentacao.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Movimentacao, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<Movimentacao, String> param) {
						if (param.getValue() != null) {
							LocalDateTime data = param.getValue().getData().toLocalDateTime();
							return new SimpleStringProperty(dataFormatada(data));
						} else {
							return new SimpleStringProperty("-");
						}
					}
				});
		// COLUNA CENTRALIZADA
		dataColunaUltimaMovimentacao.setStyle("-fx-alignment: CENTER");

		// COLUNA COM A CATEGORIA
		categoriaColunaUltimaMovimentacao
				.setCellValueFactory(new PropertyValueFactory<Movimentacao, String>("categoria"));
		// COLUNA CENTRALIZADA
		categoriaColunaUltimaMovimentacao.setStyle("-fx-alignment: CENTER");

		// COLUNA COM O VALOR
		valorColunaUltimaMovimentacao.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Movimentacao, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<Movimentacao, String> param) {
						if (param.getValue() != null) {
							int modificador = param.getValue().getTipo().getModificador();
							BigDecimal valor = param.getValue().getValor();
							return new SimpleStringProperty(valorFormatado(modificador, valor));
						} else {
							return new SimpleStringProperty("-");
						}
					}
				});
		// COLUNA À DIREITA
		valorColunaUltimaMovimentacao.setStyle("-fx-alignment: CENTER-RIGHT");

		// COLUNA COM O TIPO
		tipoColunaUltimaMovimentacao.setCellValueFactory(new PropertyValueFactory<Movimentacao, String>("tipo"));
		// COLUNA CENTRALIZADA
		tipoColunaUltimaMovimentacao.setStyle("-fx-alignment: CENTER");

		// COLUNA COM A CONFIRMAÇÃO DE PAGAMENTO
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
		// COLUNA CENTRALIZADA
		pagoColunaUltimaMovimentacao.setStyle("-fx-alignment: CENTER");

		// COLUNA COM A DESCRIÇÃO
		descricaoColunaUltimaMovimentacao
				.setCellValueFactory(new PropertyValueFactory<Movimentacao, String>("descricao"));

		// PREENCHER DADOS
		fillData(true, true);
	}

	public void fillData(boolean fillYear, boolean selectCurrentYear) {
		if (fillYear) {
			// PREENCHER ANOS
			fillYear(selectCurrentYear);
		}

		if (!ano.getItems().isEmpty()) { // SE EXISTIR REGISTROS EM ALGUM ANO, BUSCAR
			// DATA ESCOLHIDA
			LocalDateTime lt = LocalDateTime.of(ano.getValue(), mes.getValue().getMes(), 1, 0, 0, 0);

			// INICIO DO MÊS (DIA 1)
			Timestamp dataInicial = Timestamp.valueOf(LocalDateTime.of(lt.getYear(), lt.getMonth(), 1, 0, 0, 0));
			// FINAL DO MêS ESCOLHIDO
			Timestamp dataFinal = Timestamp.valueOf(LocalDateTime.of(lt.getYear(), lt.getMonth(),
					lt.getMonth().length(lt.toLocalDate().isLeapYear()), 23, 59, 59));

			// MOVIMENTAÇÕES NO MÊS ESCOLHIDO
			ObservableList<Movimentacao> olm = FXCollections
					.observableList(MovimentacaoDAO.getByMonth(this.usuarioLogado, dataInicial, dataFinal));
			movimentacoes.setItems(olm);

			// ÚLTIMA MOVIMENTAÇÃO CONFIRMADA
			ObservableList<Movimentacao> olmp = FXCollections
					.observableList(MovimentacaoDAO.getLastPayed(this.usuarioLogado));
			ultimaMovimentacao.setItems(olmp);

			BigDecimal saldoMesAtual;
			BigDecimal saldoMesPrevisto;
			BigDecimal saldoAtual;
			BigDecimal saldoPrevisto;

			if (olm.isEmpty()) { // SE NÃO EXISTE MOVIMENTAÇÕES NESSE MÊS, ZERA OS SALDOS
				saldoMesAtual = BigDecimal.ZERO;
				saldoMesPrevisto = BigDecimal.ZERO;
			} else { // SE EXISTE, BUSCA OS SALDOS
				saldoMesAtual = MovimentacaoDAO.getSaldoMesAtual(usuarioLogado, dataInicial, dataFinal);
				saldoMesPrevisto = MovimentacaoDAO.getSaldoMesPrevisto(usuarioLogado, dataInicial, dataFinal);

				// SE MESMO ASSIM NADA RETORNOU, ZERA ELES
				if (saldoMesAtual == null) {
					saldoMesAtual = BigDecimal.ZERO;
				}

				if (saldoMesPrevisto == null) {
					saldoMesPrevisto = BigDecimal.ZERO;
				}
			}

			// BUSCA SALDOS TOTAIS
			saldoAtual = MovimentacaoDAO.getSaldoAtual(usuarioLogado);
			saldoPrevisto = MovimentacaoDAO.getSaldoPrevisto(usuarioLogado);

			// SE NADA RETORNOU, ZERA ELES
			if (saldoAtual == null) {
				saldoAtual = BigDecimal.ZERO;
			}

			if (saldoPrevisto == null) {
				saldoPrevisto = BigDecimal.ZERO;
			}

			// FORMATA VALORES
			this.saldoMesSelecionado.setText(valorFormatado(1, saldoMesAtual));
			this.saldoMesPrevistoSelecionado.setText(valorFormatado(1, saldoMesPrevisto));
			this.saldoAtual.setText(valorFormatado(1, saldoAtual));
			this.saldoPrevisto.setText(valorFormatado(1, saldoPrevisto));

			// ADICIONA COR DEPENDENDO DO VALOR
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
		// DATA DE AGORA
		LocalDateTime ldt = LocalDateTime.now();

		// CASO TENHA QUE SELECIONA O ANO DE AGORA
		int selectedYear = 0;
		if (!selectCurrentYear) {
			selectedYear = ano.getValue();
		}

		// BUSCA OS ANOS QUE TEM MOVIMENTAÇÃO
		ObservableList<Integer> ola = FXCollections.observableList(MovimentacaoDAO.getYears(this.usuarioLogado));

		// SE NÃO EXISTE O ANO DE AGORA, ADICIONA ELE
		if (!ola.contains(ldt.getYear())) {
			ola.add(ldt.getYear());
		}

		ano.setItems(ola);

		// SE TEM QUE SELECIONAR O ANO DE AGORA (QUANDO INICIA O PROGRAMA), OU O QUE O
		// USUÁRIO ESCOLHEU
		if (selectCurrentYear) {
			ano.setValue(ldt.getYear());
		} else {
			ano.setValue(selectedYear);
		}
	}

	@FXML
	private void refreshBtnActionPerformed(ActionEvent event) {
		refresh();
	}

	@FXML
	private void comboBoxActionPerformed(ActionEvent event) {
		fillData(false, false); // EVENTO NO COMBOBOX
	}

	@FXML
	private void criarMovimentacaoBtnActionPerformed(ActionEvent event) {
		Stage movimentacaoFormulario = new Stage();
		movimentacaoFormulario.setTitle(MOVIMENTACAOTITULO);

		FXMLLoader loaderFXML = new FXMLLoader();
		URL movimentacaoFXML = getClass().getResource(MOVIMENTACAOFMXL);
		loaderFXML.setLocation(movimentacaoFXML);

		try {
			AnchorPane movimentacaoPane = loaderFXML.load();
			Scene scene = new Scene(movimentacaoPane);

			movimentacaoFormulario.setScene(scene);

			ObservableList<Image> icons = stage.getIcons();

			if (!icons.isEmpty()) {
				Image logo = icons.get(0);
				movimentacaoFormulario.getIcons().add(logo);
			}

			movimentacaoFormulario.setResizable(false);

			MovimentacaoController mc = loaderFXML.getController();
			mc.setStage(movimentacaoFormulario);
			mc.initialize(this.usuarioLogado, null);

			movimentacaoFormulario.initModality(Modality.WINDOW_MODAL);
			movimentacaoFormulario.initOwner(stage);
			movimentacaoFormulario.showAndWait();

			refresh();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	
	@FXML
	private void editarMovimentacaoBtnActionPerformed(ActionEvent event) {
		Movimentacao movimentacaoSelecionada = movimentacoes.getSelectionModel().getSelectedItem();
		
		if (movimentacaoSelecionada != null) {
			Stage movimentacaoFormulario = new Stage();
			movimentacaoFormulario.setTitle(MOVIMENTACAOTITULO);

			FXMLLoader loaderFXML = new FXMLLoader();
			URL movimentacaoFXML = getClass().getResource(MOVIMENTACAOFMXL);
			loaderFXML.setLocation(movimentacaoFXML);

			try {
				AnchorPane movimentacaoPane = loaderFXML.load();
				Scene scene = new Scene(movimentacaoPane);

				movimentacaoFormulario.setScene(scene);

				ObservableList<Image> icons = stage.getIcons();

				if (!icons.isEmpty()) {
					Image logo = icons.get(0);
					movimentacaoFormulario.getIcons().add(logo);
				}

				movimentacaoFormulario.setResizable(false);

				MovimentacaoController mc = loaderFXML.getController();
				mc.setStage(movimentacaoFormulario);
				mc.initialize(this.usuarioLogado, movimentacaoSelecionada);

				movimentacaoFormulario.initModality(Modality.WINDOW_MODAL);
				movimentacaoFormulario.initOwner(stage);
				movimentacaoFormulario.showAndWait();

				refresh();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	@FXML
	private void excluirMovimentacaoBtnActionPerformed(ActionEvent event) {
		Movimentacao movimentacaoSelecionada = movimentacoes.getSelectionModel().getSelectedItem();

		if (movimentacaoSelecionada != null) {
			if (Utils.dialogoYESNO(stage, "EXCLUSÃO", "DESEJA MESMO EXCLUIR ESSA MOVIMENTAÇÃO?")) {
				movimentacaoSelecionada.setDeletado(true);

				MovimentacaoDAO.update(movimentacaoSelecionada);

				refresh();
			}
		}
	}

	
	@FXML
	private void trocarSenhaBtnActionPerformed(ActionEvent event) {
		Stage trocarSenhaFormulario = new Stage();
		trocarSenhaFormulario.setTitle(TROCARSENHATITULO);

		FXMLLoader loaderFXML = new FXMLLoader();
		URL trocarSenhaFXML = getClass().getResource(TROCARSENHAFMXL);
		loaderFXML.setLocation(trocarSenhaFXML);

		try {
			AnchorPane trocarSenhaPane = loaderFXML.load();
			Scene scene = new Scene(trocarSenhaPane);

			trocarSenhaFormulario.setScene(scene);

			ObservableList<Image> icons = stage.getIcons();

			if (!icons.isEmpty()) {
				Image logo = icons.get(0);
				trocarSenhaFormulario.getIcons().add(logo);
			}

			trocarSenhaFormulario.setResizable(false);

			TrocarSenhaController tsc = loaderFXML.getController();
			tsc.setStage(trocarSenhaFormulario);
			tsc.initialize(usuarioLogado);

			trocarSenhaFormulario.initModality(Modality.WINDOW_MODAL);
			trocarSenhaFormulario.initOwner(stage);
			trocarSenhaFormulario.showAndWait();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void preencherMes() {
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
	}

	
	private String dataFormatada(LocalDateTime data) {
		DateTimeFormatter formatoData = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT);
		String dataFormatada = data.format(formatoData);
		return dataFormatada;
	}

	
	private String valorFormatado(int modificador, BigDecimal valor) {
		NumberFormat dinheiro = NumberFormat.getCurrencyInstance();
		dinheiro.setMinimumFractionDigits(2);
		dinheiro.setMaximumFractionDigits(2);
		String valorFormatado = dinheiro.format(modificador * valor.doubleValue());
		return valorFormatado;
	}

	
	private String pagoNaoPago(boolean isPago) {
		return isPago ? "CONFIRMADO" : "EM ABERTO";
	}

	
	private void refresh() {
		// DESABILITA EVENTOS NOS COMBOBOX
		EventHandler<ActionEvent> filter = e -> e.consume();
		ano.addEventFilter(ActionEvent.ACTION, filter);

		fillData(true, false); // BUSCA DADOS

		// REABILITA EVENTOS NO COMBOBOX
		ano.removeEventFilter(ActionEvent.ACTION, filter);
	}

	public void setStage(Stage stage) {
		this.stage = stage;
	}
}
