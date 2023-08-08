package com.adso.services;

import com.adso.entities.User;
import com.adso.persistence.AppEntityManager;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.NoResultException;

public class UserAuthenticationService {
    private EntityManager em = null;

    public UserAuthenticationService() {
    	EntityManagerFactory emf = AppEntityManager.getInstance().getEntityManagerFactory();
    	em = emf.createEntityManager();
    }

    public User validateUser(String username, String password) {
    	User validUserInfo = null;
        try {
            // Query the database for a user with the given email
            User user = em.createQuery("FROM User WHERE username = :username", User.class)
                    .setParameter("username", username)
                    .getSingleResult();

            // Check if the user exists and if the password matches
            if (user != null && user.getPassword().equals(password)) {
            	validUserInfo = user;
            }
        } catch (NoResultException e) {
        	
        } finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
		}
        
        return validUserInfo;
    }
}
