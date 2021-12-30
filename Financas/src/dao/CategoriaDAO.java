package dao;

import java.util.List;

import entity.Categoria;

public class CategoriaDAO extends DAO {

	@SuppressWarnings("unchecked")
	public static List<Categoria> getAll() {
		var entityManager = getEntityManager();

		try {
			return entityManager.createNamedQuery("Categoria.findAll").getResultList();
		} catch (Exception e) {
			return null;
		} finally {
			entityManager.close();
		}
	}
}
