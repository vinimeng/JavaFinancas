package entity;

import java.sql.Timestamp;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "log")
public class Log {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	private int id_usuario;
	private int id_movimentacao;
	private int id_categoria;
	private Timestamp data;
	private boolean criacao;
	private boolean edicao;
	private boolean delecao;
	private boolean ativar;
	private boolean inativar;
	private boolean login;
	private boolean logout;
	private String obs;
	
	public int getId_usuario() {
		return id_usuario;
	}
	
	public void setId_usuario(int id_usuario) {
		this.id_usuario = id_usuario;
	}
	
	public int getId_movimentacao() {
		return id_movimentacao;
	}
	
	public void setId_movimentacao(int id_movimentacao) {
		this.id_movimentacao = id_movimentacao;
	}
	
	public int getId_categoria() {
		return id_categoria;
	}
	
	public void setId_categoria(int id_categoria) {
		this.id_categoria = id_categoria;
	}
	
	public Timestamp getData() {
		return data;
	}
	
	public void setData(Timestamp data) {
		this.data = data;
	}
	
	public boolean isCriacao() {
		return criacao;
	}
	
	public void setCriacao(boolean criacao) {
		this.criacao = criacao;
	}
	
	public boolean isEdicao() {
		return edicao;
	}
	
	public void setEdicao(boolean edicao) {
		this.edicao = edicao;
	}
	
	public boolean isDelecao() {
		return delecao;
	}
	
	public void setDelecao(boolean delecao) {
		this.delecao = delecao;
	}
	
	public boolean isAtivar() {
		return ativar;
	}
	
	public void setAtivar(boolean ativar) {
		this.ativar = ativar;
	}
	
	public boolean isInativar() {
		return inativar;
	}
	
	public void setInativar(boolean inativar) {
		this.inativar = inativar;
	}
	
	public boolean isLogin() {
		return login;
	}
	
	public void setLogin(boolean login) {
		this.login = login;
	}
	
	public boolean isLogout() {
		return logout;
	}
	
	public void setLogout(boolean logout) {
		this.logout = logout;
	}
	
	public String getObs() {
		return obs;
	}
	
	public void setObs(String obs) {
		this.obs = obs;
	}
	
	public int getId() {
		return id;
	}
}
