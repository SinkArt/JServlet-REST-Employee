package org.example.app.repository.employee;

import org.example.app.config.HibernateUtil;

import org.example.app.dto.employee.EmployeeDtoRequest;
import org.example.app.entity.Employee;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.MutationQuery;
import org.hibernate.query.Query;

import java.util.List;
import java.util.Optional;

// Репозиторій, який безпосередньо
// маніпулює даними в БД.
public class EmployeeRepositoryImpl implements EmployeeRepository {

    @Override
    public void save(EmployeeDtoRequest request) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            // Транзакція стартує
            transaction = session.beginTransaction();
            String hql = "INSERT INTO Employee (firstName, position, telephone) " +
                    "VALUES (:firstName, :position, :telephone)";
            MutationQuery query = session.createMutationQuery(hql);
            query.setParameter("firstName", request.firstName());
            query.setParameter("position", request.position());
            query.setParameter("telephone", request.telephone());
            query.executeUpdate();
            // Транзакція виконується
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
        }
    }

    @Override
    public Optional<List<Employee>> getAll() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction transaction;
            // Транзакція стартує
            transaction = session.beginTransaction();
            List<Employee> list =
                    session.createQuery("FROM Employee", Employee.class).list();
            // Транзакція виконується
            transaction.commit();
            // Повертаємо Optional-контейнер з колецією даних
            return Optional.of(list);
        } catch (Exception e) {
            // Якщо помилка повертаємо порожній Optional-контейнер
            return Optional.empty();
        }
    }

    // ---- Path Params ----------------------

    @Override
    public Optional<Employee> getById(Long id) {
        Transaction transaction;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            // Транзакція стартує
            transaction = session.beginTransaction();
            Query<Employee> query =
                    session.createQuery("FROM Employee WHERE id = :id", Employee.class);
            query.setParameter("id", id);
            query.setMaxResults(1);
            Employee employee = query.uniqueResult();
            // Транзакція виконується
            transaction.commit();
            // Повертаємо Optional-контейнер з об'єктом
            return Optional.ofNullable(employee);
        } catch (Exception e) {
            // Якщо помилка або такого об'єкту немає в БД,
            // повертаємо порожній Optional-контейнер
            return Optional.empty();
        }
    }

    @Override
    public void update(Long id, EmployeeDtoRequest request) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            // Транзакция стартует
            transaction = session.beginTransaction();
            String hql = "UPDATE Employee SET firstName = :firstName," +
                    " position = :position, telephone = :telephone" +
                    " WHERE id = :id";
            MutationQuery query = session.createMutationQuery(hql);
            query.setParameter("firstName", request.firstName());
            query.setParameter("position", request.position());
            query.setParameter("telephone", request.telephone());
            query.setParameter("id", id);
            query.executeUpdate();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
        }
    }

    @Override
    public boolean deleteById(Long id) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            // Транзакція стартує
            transaction = session.beginTransaction();
            String hql = "DELETE FROM Employee WHERE id = :id";
            MutationQuery query = session.createMutationQuery(hql);
            query.setParameter("id", id);
            query.executeUpdate();
            // Транзакція виконується
            transaction.commit();
            return true;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            return false;
        }
    }

    // ---- Utils ----------------------

    @Override
    public Optional<Employee> getLastEntity() {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            // Транзакція стартує
            transaction = session.beginTransaction();
            Query<Employee> query =
                    session.createQuery("FROM Employee ORDER BY id DESC", Employee.class);
            query.setMaxResults(1);
            Employee employee = query.uniqueResult();
            // Транзакція виконується
            transaction.commit();
            return Optional.of(employee);
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            return Optional.empty();
        }
    }
}