package entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Table;

@Entity
@Table(name = "usuario")
@NamedQuery(name = "Usuario.findUsuario", query = "SELECT t FROM Usuario t WHERE t.usuario = :usuario")
public class Usuario {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@JoinColumn(name = "id")
	private int id;

	private String usuario;
	private String senha;
	private String nome;
	private String primeira_resposta;
	private String segunda_resposta;

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getPrimeira_resposta() {
		return primeira_resposta;
	}

	public void setPrimeira_resposta(String primeira_resposta) {
		this.primeira_resposta = primeira_resposta;
	}

	public String getSegunda_resposta() {
		return segunda_resposta;
	}

	public void setSegunda_resposta(String segunda_resposta) {
		this.segunda_resposta = segunda_resposta;
	}

	public int getId() {
		return id;
	}

	@Override
	public String toString() {
		return nome;
	}
}
