package com.zone.zissa.repos;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.zone.zissa.model.User;

/**
 * The UserRepository Interface for the User database table.
 * 
 */
@Transactional
public interface UserRepository extends JpaRepository<User, Integer> {

    /**
     * Find by user name method.
     *
     * @param userName
     * @return User
     */
    public User findByUserName(String userName);

    /**
     * Find by email method.
     *
     * @param email
     * @return User
     */
    public User findByEmail(String email);

    /**
     * Find by userId method.
     *
     * @param userId
     * @return User
     */
    public User findByUserID(int userId);
}
