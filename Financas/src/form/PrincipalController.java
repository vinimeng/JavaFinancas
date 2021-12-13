package form;

import java.sql.Timestamp;
import java.time.LocalDateTime;

import dao.MovimentacaoDao;
import entity.Movimentacao;
import entity.Usuario;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
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
		MovimentacaoDao md = new MovimentacaoDao();
		
		ObservableList<Integer> ola = FXCollections.observableList(md.getYears(this.usuarioLogado.getId()));
		ano.setItems(ola);
		
		if (!ola.isEmpty()) {
			ano.setValue(lt.getYear());
		}
		
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
		
		Timestamp dataInicial = Timestamp.valueOf(LocalDateTime.of(lt.getYear(), lt.getMonth(), 1, 0, 0, 0));
		Timestamp dataFinal = Timestamp.valueOf(LocalDateTime.of(lt.getYear(), lt.getMonth(), lt.getMonth().length(lt.toLocalDate().isLeapYear()), 23, 59, 59));
		
		ObservableList<Movimentacao> olm = FXCollections.observableList(md.getByMonth(this.usuarioLogado.getId(), dataInicial, dataFinal));
		movimentacoes.setItems(olm);
		
		dataColuna.setCellValueFactory(new PropertyValueFactory<Movimentacao, String>("data"));
		categoriaColuna.setCellValueFactory(new PropertyValueFactory<Movimentacao, String>("categoria"));
		valorColuna.setCellValueFactory(new PropertyValueFactory<Movimentacao, String>("valor"));
		tipoColuna.setCellValueFactory(new PropertyValueFactory<Movimentacao, String>("tipo"));
	}
	
	public void setStage(Stage stage) {
		this.stage = stage;	
	}
}
