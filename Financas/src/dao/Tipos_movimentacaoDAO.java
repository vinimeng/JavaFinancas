package dao;

import java.util.List;

import entity.Tipos_movimentacao;

public class Tipos_movimentacaoDAO extends DAO {

	@SuppressWarnings("unchecked")
	public static List<Tipos_movimentacao> getAll() {
		var entityManager = getEntityManager();

		try {
			return entityManager.createNamedQuery("Tipos_movimentacao.findAll").getResultList();
		} catch (Exception e) {
			return null;
		} finally {
			entityManager.close();
		}
	}
}
