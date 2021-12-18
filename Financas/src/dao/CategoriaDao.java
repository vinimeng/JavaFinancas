package dao;

import java.util.List;

import entity.Categoria;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class CategoriaDao implements Dao<Categoria> {

	private static final String PERSISTENCE_UNIT = "Financas";

	public static String getPersistenceUnit() {
		return PERSISTENCE_UNIT;
	}

	private EntityManager getEntityManager() {
		EntityManagerFactory factory = Persistence.createEntityManagerFactory(getPersistenceUnit());

		return factory.createEntityManager();
	}

	@Override
	public Categoria get(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Categoria> getAll() {
		var entityManager = getEntityManager();

		try {
			return entityManager.createNamedQuery("Categoria.findAll").getResultList();
		} catch (Exception e) {
			return null;
		} finally {
			entityManager.close();
		}
	}

	@Override
	public void save(Categoria t) {
		// TODO Auto-generated method stub

	}

	@Override
	public void update(Categoria t) {
		// TODO Auto-generated method stub

	}

	@Override
	public void delete(Categoria t) {
		// TODO Auto-generated method stub

	}

}
