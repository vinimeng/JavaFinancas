package dao;

import java.sql.Timestamp;
import java.util.List;

import entity.Movimentacao;
import entity.Usuario;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class MovimentacaoDao implements Dao<Movimentacao> {
	
private static final String PERSISTENCE_UNIT = "Financas";
	
	public static String getPersistenceUnit() {
		return PERSISTENCE_UNIT;
	}

	private EntityManager getEntityManager() {
		EntityManagerFactory factory = Persistence.createEntityManagerFactory(getPersistenceUnit());

		return factory.createEntityManager();
	}

	@Override
	public Movimentacao get(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Movimentacao> getAll() {
		var entityManager = getEntityManager();
		
		try {
			return entityManager.createNamedQuery("Movimentacao.findAll").getResultList();
		} catch (Exception e) {
			return null;
		} finally {
			entityManager.close();
		}
	}
	
	@SuppressWarnings("unchecked")
	public List<Movimentacao> getByMonth(Usuario usuarioLogado, Timestamp dataInicial, Timestamp dataFinal) {
		var entityManager = getEntityManager();
		
		try {
			return entityManager.createNamedQuery("Movimentacao.findByMonth")
					.setParameter("usuario", usuarioLogado)
					.setParameter("datainicial", dataInicial)
					.setParameter("datafinal", dataFinal)
					.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			entityManager.close();
		}
	}
	
	@SuppressWarnings("unchecked")
	public List<Integer> getYears(Usuario idUsuario) {
		var entityManager = getEntityManager();
		
		try {
			return entityManager.createNamedQuery("Movimentacao.findYears")
					.setParameter("usuario", idUsuario)
					.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			entityManager.close();
		}
	}

	@Override
	public void save(Movimentacao t) {
		// TODO Auto-generated method stub

	}

	@Override
	public void update(Movimentacao t) {
		// TODO Auto-generated method stub

	}

	@Override
	public void delete(Movimentacao t) {
		// TODO Auto-generated method stub

	}

}
