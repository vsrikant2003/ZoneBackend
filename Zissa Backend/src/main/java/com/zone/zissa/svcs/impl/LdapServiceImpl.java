package com.zone.zissa.svcs.impl;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.zone.zissa.exception.NoContentException;
import com.zone.zissa.model.LdapUser;
import com.zone.zissa.response.RestAPIMessageConstants;
import com.zone.zissa.svcs.LdapService;

/**
 * The LdapServiceImpl class.
 */
@Service
public class LdapServiceImpl implements LdapService {

    private static final Logger LOGGER = LoggerFactory.getLogger(LdapServiceImpl.class);

    /** The ldap url. */
    @Value("${ldap.urls}")
    public String ldapUrl;

    /** The ldap username. */
    @Value("${ldap.username}")
    public String ldapUsername;

    /** The ldap password. */
    @Value("${ldap.password}")
    public String ldapPassword;

    /** The ldap search base. */
    @Value("${ldap.searchbase}")
    public String ldapSearchBase;

    /**
     * Get all ldap users service implementation.
     *
     * @return List<LdapUser>
     * @throws NamingException
     */
    @Override
    public List<LdapUser> getAllLdapUsers() throws NamingException {

        LOGGER.info("Getting all Ldap Users Service implementation");
        Hashtable<String, String> env = new Hashtable<>();
        SearchControls searchCtrls = new SearchControls();
        searchCtrls.setSearchScope(SearchControls.SUBTREE_SCOPE);
        env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
        env.put(Context.PROVIDER_URL, ldapUrl);
        env.put(Context.REFERRAL, "follow");
        env.put(Context.SECURITY_AUTHENTICATION, "simple");
        env.put(Context.SECURITY_PRINCIPAL, ldapUsername);
        env.put(Context.SECURITY_CREDENTIALS, ldapPassword);
        String filter = "(objectCategory=user)";
        String searchBase = ldapSearchBase;
        LdapUser user = null;
        List<LdapUser> userList = null;
        DirContext context = new InitialDirContext(env);
        NamingEnumeration answer = context.search(searchBase, filter, searchCtrls);
        userList = new ArrayList<>();
        while (answer.hasMoreElements()) {
            SearchResult sr = (SearchResult) answer.next();
            Attributes attrs = sr.getAttributes();
            user = new LdapUser();
            user.setName(sr.getName());
            userList.add(user);
            if (attrs != null) {
                this.ifAttrsNotNull(user, attrs);
            }
        }
        return userList;

    }

    public void ifAttrsNotNull(LdapUser user, Attributes attrs) throws NamingException {
        NamingEnumeration ae = attrs.getAll();
        while (ae.hasMore()) {
            Attribute attribute = (Attribute) ae.next();
            NamingEnumeration e = attribute.getAll();
            if (e.hasMore()) {
                if ("sAMAccountName".equals(attribute.getID())) {
                    user.setsAMAccountName((String) e.next());
                } else if ("name".equals(attribute.getID())) {
                    user.setName((String) (e.next()));
                } else if ("mail".equals(attribute.getID())) {
                    user.setEmail((String) (e.next()));
                } else if ("givenName".equals(attribute.getID())) {
                    user.setFirstName((String) (e.next()));
                } else if ("sn".equals(attribute.getID())) {
                    user.setLastName((String) (e.next()));
                }
            }
        }
    }

    /**
     * Get all ldap users based on search string service implementation.
     *
     * @param searchString
     * @return List<LdapUser>
     * @throws NamingException
     */
    @Override
    public List<LdapUser> getAllLdapUsersBySearchString(String searchString) throws NamingException {

        LOGGER.info("Getting all Ldap Users by search string Service implementation");
        List<LdapUser> userList = this.getAllLdapUsers();
        List<LdapUser> filteredUserList = new ArrayList<>();
        for (LdapUser object : userList) {
            if (object.getFirstName().toLowerCase().startsWith(searchString.toLowerCase())) {
                filteredUserList.add(object);
            }
        }
        if (filteredUserList.isEmpty()) {
            throw new NoContentException(RestAPIMessageConstants.LDAP_USER_NOT_EXISTS);

        }
        return filteredUserList;
    }
}
