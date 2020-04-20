package com.zone.zissa.controller;

import java.util.List;

import javax.naming.NamingException;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.zone.zissa.exception.ExceptionHandlerClass;
import com.zone.zissa.exception.NoContentException;
import com.zone.zissa.model.LdapUser;
import com.zone.zissa.response.RestAPIMessageConstants;
import com.zone.zissa.response.ServiceResponse;
import com.zone.zissa.svcs.LdapService;

/**
 * The LdapMgmtController Class.
 */
@RestController
@RequestMapping("/v1/ldapusers")
public class LdapMgmtController {

    private static final Logger LOGGER = LoggerFactory.getLogger(LdapMgmtController.class);

    @Autowired
    private LdapService ldapServiceImpl;

    /**
     * Get all ldap users controller.
     *
     * @return List<LdapUser>
     */
    @GetMapping
    public ServiceResponse<List<LdapUser>> getAllLdapUsers() {
        LOGGER.debug("Get all Ldap Users Controller implementation");
        ServiceResponse<List<LdapUser>> response = new ServiceResponse<>();
        try {
            List<LdapUser> ldapUsers = ldapServiceImpl.getAllLdapUsers();
            response.setStatus(HttpServletResponse.SC_OK);
            response.setMessage(RestAPIMessageConstants.GETTING_LDAP_USERS);
            response.setData(ldapUsers);
        } catch (NamingException ex) {
            LOGGER.error(RestAPIMessageConstants.GETTING_LDAP_USERS_ERROR, ex);
            return ExceptionHandlerClass.handleServiceResponse(HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                    RestAPIMessageConstants.GETTING_LDAP_USERS_FAILURE);
        }
        return response;
    }

    /**
     * Get all ldap users based on search string controller.
     * 
     * @param searchString
     * @return List<LdapUser>
     */
    @GetMapping("/search")
    public ServiceResponse<List<LdapUser>> getAllLdapUsersBySearchString(
            @RequestParam("searchString") String searchString) {
        LOGGER.debug("Get all Ldap Users Controller implementation");
        ServiceResponse<List<LdapUser>> response = new ServiceResponse<>();
        try {
            List<LdapUser> filteredUserList = ldapServiceImpl.getAllLdapUsersBySearchString(searchString);
            response.setStatus(HttpServletResponse.SC_OK);
            response.setMessage(RestAPIMessageConstants.LDAP_USER_BY_SEARCH_STRING);
            response.setData(filteredUserList);
        } catch (NoContentException ex) {
            LOGGER.error(RestAPIMessageConstants.LDAP_SEARCH_ERROR, ex);
            return ExceptionHandlerClass.handle(ex);
        } catch (Exception ex) {
            LOGGER.error(RestAPIMessageConstants.LDAP_SEARCH_ERROR, ex);
            return ExceptionHandlerClass.handleServiceResponse(HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                    RestAPIMessageConstants.LDAP_USER_BY_SEARCH_STRING_FAILURE);
        }
        return response;
    }
}
