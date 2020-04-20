package com.zone.zissa.svcs;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.json.JSONException;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import com.zone.zissa.model.User;
import com.zone.zissa.response.ServiceResponse;

/**
 * The Interface UserService.
 */
public interface UserService {

    /**
     * Adds the User.
     *
     * @param userData
     * @return User
     * @throws JSONException
     */
    public User addUser(@Valid @RequestBody String userData) throws JSONException;

    /**
     * Update User.
     *
     * @param userData
     * @return User
     * @throws JSONException
     */
    public User updateUser(@Valid @RequestBody String userData) throws JSONException;

    /**
     * Delete User.
     *
     * @param userID
     * @return ServiceResponseBean
     */
    public ServiceResponse deleteUser(@NotNull @PathVariable Integer userId);

    /**
     * Gets all users.
     *
     * @return List<User>
     */
    public List<User> getAllUsers();
}
