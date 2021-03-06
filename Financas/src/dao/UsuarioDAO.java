package dao;

import entity.Usuario;

public class UsuarioDAO extends DAO {

	public static Usuario get(String usuario) {
		var entityManager = getEntityManager();

		try {
			Usuario user = (Usuario) entityManager.createNamedQuery("Usuario.findUsuario")
					.setParameter("usuario", usuario).getSingleResult();

			return user;
		} catch (Exception e) {
			return null;
		} finally {
			entityManager.close();
		}
	}

	public static boolean save(Usuario t) {
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

	public static boolean update(Usuario t) {
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

	public static boolean delete(Usuario t) {
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
