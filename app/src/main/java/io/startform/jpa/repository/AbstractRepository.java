package io.startform.jpa.repository;

import io.startform.common.structure.Pair;
import org.apache.logging.log4j.util.TriConsumer;
import org.hibernate.MultiIdentifierLoadAccess;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static java.lang.String.format;
import static java.util.Arrays.asList;

@SuppressWarnings({"all"})
abstract public class AbstractRepository<E> {

    @Autowired
    protected SessionFactory factory;

    public void persist(E entity) {
        session().persist(entity);
    }

    protected Session session() {
        return factory.getCurrentSession();
    }

    public void save(E entity) {
        session().save(entity);
    }

    public void merge(E entity) {
        session().merge(entity);
    }

    public void saveOrUpdate(E entity) {
        session().saveOrUpdate(entity);
    }

    public void flush() {
        session().flush();
    }

    public void remove(E entity) {
        session().delete(entity);
    }

    public void remove(int id) {
        remove(get(id));
    }

    public <T extends Serializable> E get(T id) {
        return session().get(getReflection(), id);
    }

    public void refresh(E entity) {
        session().refresh(entity);
    }

    public CriteriaBuilder criteriaBuilder() {
        return session().getCriteriaBuilder();
    }

    public List<E> getList(CriteriaQuery<E> criteria) {
        return createQuery(criteria).getResultList();
    }

    public List<E> getList(String column, Object value) {
        return getList(criteriaQuery(column, value));
    }

    public List<E> getList() {
        return createQuery().getResultList();
    }

    public E uniqueResult(CriteriaQuery<E> criteria) {
        return createQuery(criteria).uniqueResult();
    }

    public E uniqueResult() {
        return createQuery().uniqueResult();
    }

    public <T extends Serializable> List<E> getList(T... ids) {
        return getList(asList(ids));
    }

    public <T extends Serializable> List<E> getList(List<T> ids) {
        return getMultiAccessor(getReflection()).multiLoad(cleanIds(ids));
    }

    public MultiIdentifierLoadAccess getMultiAccessor(Class<E> reflection) {
        return session().byMultipleIds(reflection);
    }

    public Query<E> createQuery() {
        return session().createQuery(format("from %s", getReflection().getSimpleName()));
    }

    public Query<E> createQuery(CriteriaQuery<E> criteria) {
        return session().createQuery(criteria);
    }

    public Query<E> createQuery(TriConsumer<CriteriaBuilder, CriteriaQuery<E>, Root<E>> consumer) {
        return createQuery(criteriaQuery(consumer));
    }

    public CriteriaQuery<E> criteriaQuery(String column, Object value) {
        return criteriaQuery((builder, query, root) -> {
            query.where(builder.equal(root.get(column), value));
        });
    }

    public CriteriaQuery<E> criteriaQuery(TriConsumer<CriteriaBuilder, CriteriaQuery<E>, Root<E>> consumer) {
        CriteriaBuilder  builder = criteriaBuilder();
        CriteriaQuery<E> query   = builder.createQuery(getReflection());
        Root<E>          root    = query.from(getReflection());

        query.select(root);

        consumer.accept(builder, query, root);

        return query;
    }

    public E getOne(String column, String value) {
        return uniqueResult(criteriaQuery(column, value));
    }

    public List<E> getList(String column, String value) {
        return getList(criteriaQuery(column, value));
    }

    public E getOne(TriConsumer<CriteriaBuilder, CriteriaQuery<E>, Root<E>> consumer) {
        return uniqueResult(criteriaQuery(consumer));
    }

    public List<E> getList(TriConsumer<CriteriaBuilder, CriteriaQuery<E>, Root<E>> consumer) {
        return getList(criteriaQuery(consumer));
    }

    public QueryConsumer<E> getConsumer(List<Pair<String, ?>> pairs) {
        return  (builder, query, root) -> {
            List<Predicate> predicates = new ArrayList<>();

            for (Pair<String, ?> pair : pairs) {
                predicates.add(builder.equal(root.get(pair.getValueA()), pair.getValueB()));
            }

            query.where(builder.and(predicates.toArray(new Predicate[] {})));
        };
    }

    public CriteriaQuery<E> getQuery(List<Pair<String, ?>> pairs) {
        return criteriaQuery(getConsumer(pairs));
    }

    public CriteriaQuery<E> getQuery(Pair<String, ?>... pairs) {
        return getQuery(asList(pairs));
    }

    public <T extends Serializable> List<T> cleanIds(List<T> ids) {
        return cleanIds(ids, Objects::isNull);
    }

    public <T extends Serializable> List<T> cleanIds(List<T> ids, java.util.function.Predicate<T> predicate) {
        ids.removeIf(predicate);

        return ids;
    }

    abstract public Class<E> getReflection();

    @FunctionalInterface
    public interface QueryConsumer<E> extends TriConsumer<CriteriaBuilder, CriteriaQuery<E>, Root<E>> {

        default QueryConsumer<E> before(QueryConsumer<E> consumer) {
            return (a, b, c) -> { consumer.accept(a, b, c); accept(a, b, c); };
        }

        default QueryConsumer<E> after(QueryConsumer<E> consumer) {
            return (a, b, c) -> { accept(a, b, c); consumer.accept(a, b, c); };
        }

    }

}
