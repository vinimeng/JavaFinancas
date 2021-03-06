package entity;

import java.util.Locale;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.NamedQuery;

@Entity
@NamedQuery(name = "Categoria.findAll", query = "SELECT t FROM Categoria t WHERE t.inativo = 0 ORDER BY t.descricao")
public class Categoria {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@JoinColumn(name = "id")
	private int id;

	private String descricao;
	private boolean inativo;

	public int getId() {
		return id;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public boolean isInativo() {
		return inativo;
	}

	public void setInativo(boolean inativo) {
		this.inativo = inativo;
	}

	@Override
	public String toString() {
		return descricao.toUpperCase(new Locale("pt", "BR"));
	}
}
