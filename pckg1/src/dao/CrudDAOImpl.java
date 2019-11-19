package dao;

import entity.SuperEntity;
import org.hibernate.Session;
import sun.reflect.generics.tree.TypeArgument;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

public abstract class CrudDAOImpl<T extends SuperEntity,ID extends Serializable> implements CrudDAO<T,ID> {

    protected Session session;
    Class<T> entity;

    public CrudDAOImpl(){
        entity = (Class<T>) ((ParameterizedType)(this.getClass().getGenericSuperclass())).getActualTypeArguments()[0];
    }

    @Override
    public List<T> findAll() throws Exception {
        return session.createQuery("from "+entity.getName()).list();
    }

    @Override
    public T find(ID id) throws Exception {
        return session.get(entity,id);
    }

    @Override
    public void save(T entity) throws Exception {
        session.save(entity);
    }

    @Override
    public void update(T entity) throws Exception {
        session.merge(entity);
    }

    @Override
    public void delete(ID id) throws Exception {
        session.delete(session.load(entity,id));
    }

    public void setSession(Session session){
        this.session=session;
    }
}