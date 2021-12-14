package form;

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
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
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
	private Button refreshBtn;
	
	@FXML
	private Button criarMovimentacaoBtn;
	
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
	
	@SuppressWarnings("unused")
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
		dataColuna.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Movimentacao, String>, ObservableValue<String>>() {
			@Override
			public ObservableValue<String> call(CellDataFeatures<Movimentacao, String> param) {
				if (param.getValue() != null) {			
		            return new SimpleStringProperty(param.getValue().getData().toLocalDateTime().format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT)));
		        } else {
		            return new SimpleStringProperty("-");
		        }
			}
		});
		dataColuna.setStyle("-fx-alignment: CENTER");
		
		categoriaColuna.setCellValueFactory(new PropertyValueFactory<Movimentacao, String>("categoria"));
		categoriaColuna.setStyle("-fx-alignment: CENTER");
		
		valorColuna.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Movimentacao, String>, ObservableValue<String>>() {
			@Override
			public ObservableValue<String> call(CellDataFeatures<Movimentacao, String> param) {
				if (param.getValue() != null) {
					NumberFormat dinheiro = NumberFormat.getCurrencyInstance();
					dinheiro.setMinimumFractionDigits(2);
					dinheiro.setMaximumFractionDigits(2);
					
					int receitaDespesa = param.getValue().getTipo().getModificador();
					
		            return new SimpleStringProperty(dinheiro.format(receitaDespesa * param.getValue().getValor().doubleValue()));
		        } else {
		            return new SimpleStringProperty("-");
		        }
			}
		});
		valorColuna.setStyle("-fx-alignment: CENTER-RIGHT");
		
		tipoColuna.setCellValueFactory(new PropertyValueFactory<Movimentacao, String>("tipo"));
		tipoColuna.setStyle("-fx-alignment: CENTER");
		
		pagoColuna.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Movimentacao, String>, ObservableValue<String>>() {
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
		dataColunaUltimaMovimentacao.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Movimentacao, String>, ObservableValue<String>>() {
			@Override
			public ObservableValue<String> call(CellDataFeatures<Movimentacao, String> param) {
				if (param.getValue() != null) {			
		            return new SimpleStringProperty(param.getValue().getData().toLocalDateTime().format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT)));
		        } else {
		            return new SimpleStringProperty("-");
		        }
			}
		});
		dataColunaUltimaMovimentacao.setStyle("-fx-alignment: CENTER");
		
		categoriaColunaUltimaMovimentacao.setCellValueFactory(new PropertyValueFactory<Movimentacao, String>("categoria"));
		categoriaColunaUltimaMovimentacao.setStyle("-fx-alignment: CENTER");
		
		valorColunaUltimaMovimentacao.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Movimentacao, String>, ObservableValue<String>>() {
			@Override
			public ObservableValue<String> call(CellDataFeatures<Movimentacao, String> param) {
				if (param.getValue() != null) {
					NumberFormat dinheiro = NumberFormat.getCurrencyInstance();
					dinheiro.setMinimumFractionDigits(2);
					dinheiro.setMaximumFractionDigits(2);
					
					int receitaDespesa = param.getValue().getTipo().getModificador();
					
		            return new SimpleStringProperty(dinheiro.format(receitaDespesa * param.getValue().getValor().doubleValue()));
		        } else {
		            return new SimpleStringProperty("-");
		        }
			}
		});
		valorColunaUltimaMovimentacao.setStyle("-fx-alignment: CENTER-RIGHT");
		
		tipoColunaUltimaMovimentacao.setCellValueFactory(new PropertyValueFactory<Movimentacao, String>("tipo"));
		tipoColunaUltimaMovimentacao.setStyle("-fx-alignment: CENTER");
		
		pagoColunaUltimaMovimentacao.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Movimentacao, String>, ObservableValue<String>>() {
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
		
		descricaoColunaUltimaMovimentacao.setCellValueFactory(new PropertyValueFactory<Movimentacao, String>("descricao"));
		
		// PREENCHER DADOS
		fillData(true, true);
	}
	
	public void fillData(boolean fillYear, boolean selectCurrentYear) {	
		MovimentacaoDao md = new MovimentacaoDao();
		
		if (fillYear) {
			fillYear(selectCurrentYear);
		}
		
		if (!ano.getItems().isEmpty()) {
			LocalDateTime lt = LocalDateTime.of(ano.getValue(), mes.getValue().getMes(), 1, 0, 0, 0);
			
			Timestamp dataInicial = Timestamp.valueOf(LocalDateTime.of(lt.getYear(), lt.getMonth(), 1, 0, 0, 0));
			Timestamp dataFinal = Timestamp.valueOf(LocalDateTime.of(lt.getYear(), lt.getMonth(), lt.getMonth().length(lt.toLocalDate().isLeapYear()), 23, 59, 59));
			
			ObservableList<Movimentacao> olm = FXCollections.observableList(md.getByMonth(this.usuarioLogado, dataInicial, dataFinal));
			movimentacoes.setItems(olm);
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
	
	public void setStage(Stage stage) {
		this.stage = stage;	
	}
}
