/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vplhome.database;

import com.vplhome.database.entity.CourseEntity;
import com.vplhome.database.entity.RegisterEntity;
import com.vplhome.database.entity.UserEntity;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

/**
 *
 * @author vpl
 */
public class HibernateUtil {

    private static final SessionFactory sessionFactory = buildSessionFactory();

    private static SessionFactory buildSessionFactory() {
        Configuration configuration = new Configuration().addResource("hibernate.cfg.xml");
        addAnnotatedClass(configuration);
        StandardServiceRegistryBuilder serviceRegistry = new StandardServiceRegistryBuilder();
        return configuration.buildSessionFactory(serviceRegistry.applySettings(configuration.getProperties()).build());
    }

    private static void addAnnotatedClass(Configuration configuration) {
        configuration.addAnnotatedClass(RegisterEntity.class);
        configuration.addAnnotatedClass(CourseEntity.class);
        configuration.addAnnotatedClass(UserEntity.class);
        configuration.configure();
    }

    public static void insert(Object persist) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        session.persist(persist);
        transaction.commit();
        session.close();
    }

    public static void update(Object persist) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        session.update(persist);
        transaction.commit();
        session.close();
    }

    public static void delete(Object persist) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        session.delete(persist);
        transaction.commit();
        session.close();
    }

    public static List<?> select(Class table, String condition) {
        String name = table.getSimpleName();
        Session session = sessionFactory.openSession();
        List list = session.createQuery(String.format("FROM %s %s", name, condition)).list();
        session.close();
        return list;
    }
    
    public static List selectSQL(String query) {
        Session session = sessionFactory.openSession();
        List list = session.createSQLQuery(String.format(query)).list();
        session.close();
        return list;
    }
}
