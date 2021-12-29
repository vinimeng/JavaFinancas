package dao;

import java.math.BigDecimal;
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

	public BigDecimal getSaldoMesAtual(Usuario usuarioLogado, Timestamp dataInicial, Timestamp dataFinal) {
		var entityManager = getEntityManager();

		try {
			return (BigDecimal) entityManager.createNamedQuery("Movimentacao.findSaldoMesAtual")
					.setParameter("usuario", usuarioLogado).setParameter("datainicial", dataInicial)
					.setParameter("datafinal", dataFinal).getSingleResult();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			entityManager.close();
		}
	}

	public BigDecimal getSaldoMesPrevisto(Usuario usuarioLogado, Timestamp dataInicial, Timestamp dataFinal) {
		var entityManager = getEntityManager();

		try {
			return (BigDecimal) entityManager.createNamedQuery("Movimentacao.findSaldoMesPrevisto")
					.setParameter("usuario", usuarioLogado).setParameter("datainicial", dataInicial)
					.setParameter("datafinal", dataFinal).getSingleResult();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			entityManager.close();
		}
	}

	public BigDecimal getSaldoAtual(Usuario usuarioLogado) {
		var entityManager = getEntityManager();

		try {
			return (BigDecimal) entityManager.createNamedQuery("Movimentacao.findSaldoAtual")
					.setParameter("usuario", usuarioLogado).getSingleResult();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			entityManager.close();
		}
	}

	public BigDecimal getSaldoPrevisto(Usuario usuarioLogado) {
		var entityManager = getEntityManager();

		try {
			return (BigDecimal) entityManager.createNamedQuery("Movimentacao.findSaldoPrevisto")
					.setParameter("usuario", usuarioLogado).getSingleResult();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			entityManager.close();
		}
	}

	@SuppressWarnings("unchecked")
	public List<Movimentacao> getLastPayed(Usuario usuarioLogado) {
		var entityManager = getEntityManager();

		try {
			return entityManager.createNamedQuery("Movimentacao.findLastPayed").setParameter("usuario", usuarioLogado)
					.setMaxResults(1).getResultList();
		} catch (Exception e) {
			return null;
		} finally {
			entityManager.close();
		}
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
			return entityManager.createNamedQuery("Movimentacao.findByMonth").setParameter("usuario", usuarioLogado)
					.setParameter("datainicial", dataInicial).setParameter("datafinal", dataFinal).getResultList();
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
			return entityManager.createNamedQuery("Movimentacao.findYears").setParameter("usuario", idUsuario)
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
		var em = getEntityManager();

		try {
			em.getTransaction().begin();

			em.persist(t);

			em.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
			em.getTransaction().rollback();
		} finally {
			em.close();
		}
	}

	@Override
	public void update(Movimentacao t) {
		var em = getEntityManager();

		try {
			em.getTransaction().begin();

			em.merge(t);

			em.getTransaction().commit();

		} catch (Exception e) {
			e.printStackTrace();
			em.getTransaction().rollback();
		} finally {
			em.close();
		}
	}

	@Override
	public void delete(Movimentacao t) {
		var em = getEntityManager();

		try {
			em.getTransaction().begin();

			if (!em.contains(t)) {
				t = em.merge(t);
			}

			em.remove(t);

			em.getTransaction().commit();

		} catch (Exception e) {
			e.printStackTrace();
			em.getTransaction().rollback();
		} finally {
			em.close();
		}
	}

}
