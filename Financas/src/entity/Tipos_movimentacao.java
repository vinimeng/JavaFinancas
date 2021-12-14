package entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;

@Entity
@Table(name = "tipos_movimentacao")
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
		return descricao;
	}

	public int getModificador() {
		return modificador;
	}

	public void setModificador(int modificador) {
		this.modificador = modificador;
	}
}
