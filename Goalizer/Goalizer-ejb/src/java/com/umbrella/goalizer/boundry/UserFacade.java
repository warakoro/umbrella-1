/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.umbrella.goalizer.boundry;

import com.umbrella.goalizer.entity.User;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author donya
 * @author Mamadou
 */
@Stateless
public class UserFacade extends AbstractFacade<User> {
    @PersistenceContext(unitName = "Goalizer-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public UserFacade() {
        super(User.class);
    }

    public User findByUsername(String name) {
        
       try {
            System.out.println("Facade: About to execute querry" + name);
            Query userNameQuery = em.createNamedQuery("User.findByUsername");
            userNameQuery.setParameter("name", name);
            User foundUser = (User) userNameQuery.getSingleResult();
            return foundUser;
        } catch (NoResultException e) {
            System.out.println("query user with username no result exception return null");
            return null;
        }
    }
}
