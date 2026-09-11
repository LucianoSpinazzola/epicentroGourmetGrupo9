package dao;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import java.util.List;
import datos.Empleado;

public class EmpleadoDao {
    private static Session session;
    private Transaction tx;
    private static EmpleadoDao instancia = null; // Patrón Singleton

    protected EmpleadoDao() {
    }

    public static EmpleadoDao getInstance() {
        if (instancia == null)
            instancia = new EmpleadoDao();
        return instancia;
    }

    protected void iniciaOperacion() throws HibernateException {
        session = HibernateUtil.getSessionFactory().openSession();
        tx = session.beginTransaction();
    }

	private void manejaExcepcion(HibernateException he) throws HibernateException {
		tx.rollback();
		throw new HibernateException("ERROR en la capa de acceso a datos", he);
	}

    public int agregar(Empleado objeto) {
        int id = 0;
        try {
            iniciaOperacion();
            id = Integer.parseInt(session.save(objeto).toString());
            tx.commit();
        } catch (HibernateException he) {
            manejaExcepcion(he);
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
        return id;
    }

    public void actualizar(Empleado objeto) {
        try {
            iniciaOperacion();
            session.update(objeto);
            tx.commit();
        } catch (HibernateException he) {
            manejaExcepcion(he);
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
    }

    public void eliminar(Empleado objeto) {
        try {
            iniciaOperacion();
            session.delete(objeto);
            tx.commit();
        } catch (HibernateException he) {
            manejaExcepcion(he);
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
    }

    public Empleado traer(int idEmpleado) {
        Empleado objeto = null;
        try {
            iniciaOperacion();
            // Corregida la consulta HQL agregando la cláusula WHERE
            objeto = (Empleado) session.createQuery("from Empleado e where e.idEmpleado = :idEmpleado")
                    .setParameter("idEmpleado", idEmpleado)
                    .uniqueResult();
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
        return objeto;
    }

    public List<Empleado> traer() throws HibernateException {
        List<Empleado> lista = null;
        try {
            iniciaOperacion();
            lista = session.createQuery("from Empleado", Empleado.class).list();
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
        return lista;
    }
}