package entity;

import java.util.Locale;

import jakarta.persistence.Cacheable;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Table;

@Entity
@Table(name = "tipos_movimentacao")
@Cacheable(false)
@NamedQuery(name = "Tipos_movimentacao.findAll", query = "SELECT t FROM Tipos_movimentacao t ORDER BY t.descricao")
public class Tipos_movimentacao {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@JoinColumn(name = "id")
	private int id;

	private String descricao;
	private int modificador;

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public int getId() {
		return id;
	}

	@Override
	public String toString() {
		return descricao.toUpperCase(new Locale("pt", "BR"));
	}

	public int getModificador() {
		return modificador;
	}

	public void setModificador(int modificador) {
		this.modificador = modificador;
	}
}
