package com.zone.zissa.svcs.impl;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zone.zissa.exception.ConflictException;
import com.zone.zissa.exception.DataNotFoundException;
import com.zone.zissa.model.Role;
import com.zone.zissa.model.User;
import com.zone.zissa.repos.RoleRepository;
import com.zone.zissa.repos.UserRepository;
import com.zone.zissa.response.RestAPIMessageConstants;
import com.zone.zissa.response.ServiceResponse;
import com.zone.zissa.svcs.UserService;

/**
 * The UserServiceImpl class.
 */
@Service
public class UserServiceImpl implements UserService {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private RoleRepository roleRepo;

    @Autowired
    private UserRepository userRepo;

    /**
     * Add user service implementation.
     *
     * @param userData
     * @return User
     * @throws JSONException
     */
    @Override
    public User addUser(String userData) throws JSONException {

        LOGGER.info("Add new User Service implementation");
        User user = new User();
        Date date = new Date();
        long time = date.getTime();
        Timestamp timestamp = new Timestamp(time);
        User userObject = null;
        JSONObject object = new JSONObject(userData);
        String userName = (String) object.get("sAMAccountName");
        String email = (String) object.get("email");
        String firstName = (String) object.get("firstName");
        String lastName = (String) object.get("lastName");
        int roleId = object.getInt("role_ID");
        Role roleObject = roleRepo.findByroleID(roleId);
        user.setUserName(userName);
        user.setEmail(email);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setRole(roleObject);
        user.setActiveStatus(1);
        user.setCreatedDate(timestamp);
        User userExists = userRepo.findByUserName(userName);
        if (userExists == null) {
            userObject = userRepo.save(user);
        } else {

            throw new ConflictException(RestAPIMessageConstants.USER_EXISTS);
        }
        return userObject;
    }

    /**
     * Update user service implementation.
     *
     * @param userData
     * @return User
     * @throws JSONException
     */
    @Override
    public User updateUser(String userData) throws JSONException {

        LOGGER.info("Update User Service implementation");
        Date date = new Date();
        User user = new User();
        long time = date.getTime();
        Timestamp timestamp = new Timestamp(time);
        User userObject = null;
        JSONObject object = new JSONObject(userData);
        String userName = (String) object.get("sAMAccountName");
        String email = (String) object.get("email");
        String firstName = (String) object.get("firstName");
        String lastName = (String) object.get("lastName");
        int userId = object.getInt("user_ID");
        int roleId = object.getInt("role_ID");
        Role roleobj = roleRepo.findByroleID(roleId);
        User userExists = userRepo.findByEmail(email);
        if (userExists == null) {
            throw new DataNotFoundException(RestAPIMessageConstants.UPDATE_USER);
        } else {
            user.setUserName(userName);
            user.setEmail(email);
            user.setFirstName(firstName);
            user.setLastName(lastName);
            user.setRole(roleobj);
            user.setActiveStatus(1);
            user.setUserID(userId);
            user.setCreatedDate(timestamp);
            userObject = userRepo.save(user);
        }
        return userObject;
    }

    /**
     * Gets all users service implementation.
     *
     * @return List<User>
     */
    @Override
    public List<User> getAllUsers() {

        LOGGER.info("Get all Users Service implementation");
        List<User> userList = userRepo.findAll();
        for (User user : userList) {
            Role role = user.getRole();
            role.setPermissions(null);
            role.setDefaultCategory(null);
        }

        return userList;
    }

    /**
     * Delete user service implementation
     *
     * @param user_ID
     * @return ServiceResponseBean
     */
    @Override
    public ServiceResponse deleteUser(Integer userID) {

        LOGGER.info("Delete User Service implementation");
        ServiceResponse response = new ServiceResponse();
        User userExists = userRepo.findByUserID(userID);
        if (userExists != null) {
            userRepo.deleteById(userID);
        } else {

            throw new DataNotFoundException(RestAPIMessageConstants.DELETE_USER);

        }
        return response;
    }
}
