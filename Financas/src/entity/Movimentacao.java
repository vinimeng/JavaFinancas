package entity;

import java.math.BigDecimal;
import java.sql.Timestamp;

import jakarta.persistence.Cacheable;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedQuery;

@Entity
@Cacheable(false)
@NamedQuery(name = "Movimentacao.findAll", query = "SELECT t FROM Movimentacao t INNER JOIN t.id_usuario u INNER JOIN t.categoria c INNER JOIN t.tipo m WHERE t.deletado = 0 ORDER BY t.data")
@NamedQuery(name = "Movimentacao.findByMonth", query = "SELECT t FROM Movimentacao t INNER JOIN t.id_usuario u INNER JOIN t.categoria c INNER JOIN t.tipo m WHERE t.deletado = 0 AND t.id_usuario = :usuario AND t.data > :datainicial AND t.data < :datafinal ORDER BY t.data")
@NamedQuery(name = "Movimentacao.findYears", query = "SELECT EXTRACT(YEAR t.data) as ANO FROM Movimentacao t WHERE t.deletado = 0 AND t.id_usuario = :usuario GROUP BY ANO ORDER BY t.data")
@NamedQuery(name = "Movimentacao.findLastPayed", query = "SELECT t FROM Movimentacao t INNER JOIN t.id_usuario u INNER JOIN t.categoria c INNER JOIN t.tipo m WHERE t.deletado = 0 AND t.pago = 1 AND t.id_usuario = :usuario ORDER BY t.data DESC")
@NamedQuery(name = "Movimentacao.findSaldoMesAtual", query = "SELECT SUM(t.valor * m.modificador) as SALDO FROM Movimentacao t INNER JOIN t.id_usuario u INNER JOIN t.categoria c INNER JOIN t.tipo m WHERE t.deletado = 0 AND t.pago = 1 AND t.id_usuario = :usuario AND t.data > :datainicial AND t.data < :datafinal")
@NamedQuery(name = "Movimentacao.findSaldoMesPrevisto", query = "SELECT SUM(t.valor * m.modificador) as SALDO FROM Movimentacao t INNER JOIN t.id_usuario u INNER JOIN t.categoria c INNER JOIN t.tipo m WHERE t.deletado = 0 AND t.id_usuario = :usuario AND t.data > :datainicial AND t.data < :datafinal")
@NamedQuery(name = "Movimentacao.findSaldoAtual", query = "SELECT SUM(t.valor * m.modificador) as SALDO FROM Movimentacao t INNER JOIN t.id_usuario u INNER JOIN t.categoria c INNER JOIN t.tipo m WHERE t.deletado = 0 AND t.pago = 1 AND t.id_usuario = :usuario")
@NamedQuery(name = "Movimentacao.findSaldoPrevisto", query = "SELECT SUM(t.valor * m.modificador) as SALDO FROM Movimentacao t INNER JOIN t.id_usuario u INNER JOIN t.categoria c INNER JOIN t.tipo m WHERE t.deletado = 0 AND t.id_usuario = :usuario")
public class Movimentacao {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@ManyToOne
	@JoinColumn(name = "id_usuario")
	private Usuario id_usuario;

	@ManyToOne
	@JoinColumn(name = "tipo")
	private Tipos_movimentacao tipo;

	@ManyToOne
	@JoinColumn(name = "categoria")
	private Categoria categoria;

	private Timestamp data;
	private BigDecimal valor;
	private String descricao;
	private boolean pago;
	private boolean deletado;

	public int getId() {
		return id;
	}

	public Usuario getId_usuario() {
		return id_usuario;
	}

	public void setId_usuario(Usuario id_usuario) {
		this.id_usuario = id_usuario;
	}

	public Tipos_movimentacao getTipo() {
		return tipo;
	}

	public void setTipo(Tipos_movimentacao tipo) {
		this.tipo = tipo;
	}

	public Categoria getCategoria() {
		return categoria;
	}

	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}

	public Timestamp getData() {
		return data;
	}

	public void setData(Timestamp data) {
		this.data = data;
	}

	public BigDecimal getValor() {
		return valor;
	}

	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public boolean isPago() {
		return pago;
	}

	public void setPago(boolean pago) {
		this.pago = pago;
	}

	public boolean isDeletado() {
		return deletado;
	}

	public void setDeletado(boolean deletado) {
		this.deletado = deletado;
	}
}
