package entity;

import java.util.Locale;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.NamedQuery;

@Entity
@NamedQuery(name = "Tipos_movimentacao.findAll", query = "SELECT t FROM Tipos_movimentacao t ORDER BY t.descricao")
public class Tipos_movimentacao {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@JoinColumn(name = "id")
	private int id;

	private String descricao;
	private int modificador;

	public int getId() {
		return id;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public int getModificador() {
		return modificador;
	}

	public void setModificador(int modificador) {
		this.modificador = modificador;
	}

	@Override
	public String toString() {
		return descricao.toUpperCase(new Locale("pt", "BR"));
	}
}
