package dao;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

import entity.Movimentacao;
import entity.Usuario;

public class MovimentacaoDAO extends DAO {

	public static BigDecimal getSaldoMesAtual(Usuario usuarioLogado, Timestamp dataInicial, Timestamp dataFinal) {
		var entityManager = getEntityManager();

		try {
			return (BigDecimal) entityManager.createNamedQuery("Movimentacao.findSaldoMesAtual")
					.setParameter("usuario", usuarioLogado).setParameter("datainicial", dataInicial)
					.setParameter("datafinal", dataFinal).getSingleResult();
		} catch (Exception e) {
			return null;
		} finally {
			entityManager.close();
		}
	}

	public static BigDecimal getSaldoMesPrevisto(Usuario usuarioLogado, Timestamp dataInicial, Timestamp dataFinal) {
		var entityManager = getEntityManager();

		try {
			return (BigDecimal) entityManager.createNamedQuery("Movimentacao.findSaldoMesPrevisto")
					.setParameter("usuario", usuarioLogado).setParameter("datainicial", dataInicial)
					.setParameter("datafinal", dataFinal).getSingleResult();
		} catch (Exception e) {
			return null;
		} finally {
			entityManager.close();
		}
	}

	public static BigDecimal getSaldoAtual(Usuario usuarioLogado) {
		var entityManager = getEntityManager();

		try {
			return (BigDecimal) entityManager.createNamedQuery("Movimentacao.findSaldoAtual")
					.setParameter("usuario", usuarioLogado).getSingleResult();
		} catch (Exception e) {
			return null;
		} finally {
			entityManager.close();
		}
	}

	public static BigDecimal getSaldoPrevisto(Usuario usuarioLogado) {
		var entityManager = getEntityManager();

		try {
			return (BigDecimal) entityManager.createNamedQuery("Movimentacao.findSaldoPrevisto")
					.setParameter("usuario", usuarioLogado).getSingleResult();
		} catch (Exception e) {
			return null;
		} finally {
			entityManager.close();
		}
	}

	@SuppressWarnings("unchecked")
	public static List<Movimentacao> getLastPayed(Usuario usuarioLogado) {
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
	public static List<Movimentacao> getAll() {
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
	public static List<Movimentacao> getByMonth(Usuario usuarioLogado, Timestamp dataInicial, Timestamp dataFinal) {
		var entityManager = getEntityManager();

		try {
			return entityManager.createNamedQuery("Movimentacao.findByMonth").setParameter("usuario", usuarioLogado)
					.setParameter("datainicial", dataInicial).setParameter("datafinal", dataFinal).getResultList();
		} catch (Exception e) {
			return null;
		} finally {
			entityManager.close();
		}
	}

	@SuppressWarnings("unchecked")
	public static List<Integer> getYears(Usuario idUsuario) {
		var entityManager = getEntityManager();

		try {
			return entityManager.createNamedQuery("Movimentacao.findYears").setParameter("usuario", idUsuario)
					.getResultList();
		} catch (Exception e) {
			return null;
		} finally {
			entityManager.close();
		}
	}

	public static boolean save(Movimentacao t) {
		var em = getEntityManager();

		try {
			em.getTransaction().begin();

			em.persist(t);

			em.getTransaction().commit();
		} catch (Exception e) {
			em.getTransaction().rollback();
			return false;
		} finally {
			em.close();
		}

		return true;
	}

	public static boolean update(Movimentacao t) {
		var em = getEntityManager();

		try {
			em.getTransaction().begin();

			em.merge(t);

			em.getTransaction().commit();

		} catch (Exception e) {
			em.getTransaction().rollback();
			return false;
		} finally {
			em.close();
		}

		return true;
	}

	public static boolean delete(Movimentacao t) {
		var em = getEntityManager();

		try {
			em.getTransaction().begin();

			if (!em.contains(t)) {
				t = em.merge(t);
			}

			em.remove(t);

			em.getTransaction().commit();

		} catch (Exception e) {
			em.getTransaction().rollback();
			return false;
		} finally {
			em.close();
		}

		return true;
	}

}
