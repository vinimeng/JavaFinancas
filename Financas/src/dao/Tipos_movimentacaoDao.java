package dao;

import java.util.List;

import entity.Tipos_movimentacao;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class Tipos_movimentacaoDao implements Dao<Tipos_movimentacao> {

	private static final String PERSISTENCE_UNIT = "Financas";

	public static String getPersistenceUnit() {
		return PERSISTENCE_UNIT;
	}

	private EntityManager getEntityManager() {
		EntityManagerFactory factory = Persistence.createEntityManagerFactory(getPersistenceUnit());

		return factory.createEntityManager();
	}

	@Override
	public Tipos_movimentacao get(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Tipos_movimentacao> getAll() {
		var entityManager = getEntityManager();

		try {
			return entityManager.createNamedQuery("Tipos_movimentacao.findAll").getResultList();
		} catch (Exception e) {
			return null;
		} finally {
			entityManager.close();
		}
	}

	@Override
	public void save(Tipos_movimentacao t) {
		// TODO Auto-generated method stub

	}

	@Override
	public void update(Tipos_movimentacao t) {
		// TODO Auto-generated method stub

	}

	@Override
	public void delete(Tipos_movimentacao t) {
		// TODO Auto-generated method stub

	}

}
