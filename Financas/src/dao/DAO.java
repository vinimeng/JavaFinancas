package dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public abstract class DAO {
	private static final String PERSISTENCE_UNIT = "Financas";

	protected static EntityManager getEntityManager() {
		EntityManagerFactory factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT);

		return factory.createEntityManager();
	}
}
