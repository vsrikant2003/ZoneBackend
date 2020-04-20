package com.zone.zissa.svcs;

import java.util.List;

import javax.naming.NamingException;

import org.springframework.web.bind.annotation.RequestParam;

import com.zone.zissa.model.LdapUser;

/**
 * The Interface LdapService.
 */
public interface LdapService {

    /**
     * Get the all ldap users.
     *
     * @return List<LdapUser>
     * @throws NamingException
     */
    public List<LdapUser> getAllLdapUsers() throws NamingException;

    /**
     * Get all ldap users based on searchstring.
     * 
     * @param searchString
     * @return List<LdapUser>
     * @throws NamingException
     */
    public List<LdapUser> getAllLdapUsersBySearchString(@RequestParam("searchString") String searchString)
            throws NamingException;
}
