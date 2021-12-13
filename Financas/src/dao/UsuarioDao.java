package dao;

import java.util.List;

import entity.Usuario;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class UsuarioDao implements Dao<Usuario> {
	
	private static final String PERSISTENCE_UNIT = "Financas";
	
	public static String getPersistenceUnit() {
		return PERSISTENCE_UNIT;
	}

	private EntityManager getEntityManager() {
		EntityManagerFactory factory = Persistence.createEntityManagerFactory(getPersistenceUnit());

		return factory.createEntityManager();
	}
	
	@Override
	public Usuario get(int id) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public Usuario get(String usuario) {
		var entityManager = getEntityManager();
		
		try {
			Usuario user = (Usuario) entityManager.createNamedQuery("Usuario.findUsuario").setParameter("usuario", usuario).getSingleResult();
			
			return user;
		} catch (Exception e) {
			return null;
		} finally {
			entityManager.close();
		}
	}

	@Override
	public List<Usuario> getAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void save(Usuario t) {
		// TODO Auto-generated method stub

	}

	@Override
	public void update(Usuario t) {
		// TODO Auto-generated method stub

	}

	@Override
	public void delete(Usuario t) {
		// TODO Auto-generated method stub

	}

}
