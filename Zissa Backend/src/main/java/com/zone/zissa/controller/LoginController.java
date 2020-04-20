package com.zone.zissa.controller;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zone.zissa.exception.ExceptionHandlerClass;
import com.zone.zissa.repos.UserRepository;
import com.zone.zissa.response.RestAPIMessageConstants;
import com.zone.zissa.response.ServiceResponse;

/**
 * The LoginController Class.
 */
@RestController
public class LoginController {

    private static final Logger LOGGER = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    private UserRepository userRepo;

    /**
     * Login controller
     * 
     * @return ServiceResponse
     */
    @GetMapping("/")
    public ServiceResponse<com.zone.zissa.model.User> index() {
        ServiceResponse<com.zone.zissa.model.User> response = new ServiceResponse<>();
        try {
            User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            com.zone.zissa.model.User userObject = userRepo.findByUserName(user.getUsername());
            response.setStatus(HttpServletResponse.SC_OK);
            response.setMessage(RestAPIMessageConstants.LOGIN_SUCCESS);
            response.setData(userObject);
        } catch (Exception ex) {
            LOGGER.error(RestAPIMessageConstants.LOGIN_ERROR, ex);
            return ExceptionHandlerClass.handleServiceResponse(HttpServletResponse.SC_UNAUTHORIZED,
                    RestAPIMessageConstants.LOGIN_FAILURE);
        }
        return response;
    }
}
