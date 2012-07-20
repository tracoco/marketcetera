package org.marketcetera.dao.impl;

import java.util.Set;

import org.marketcetera.dao.DataAccessService;
import org.marketcetera.systemmodel.*;
import org.marketcetera.util.log.SLF4JLoggerProxy;
import org.marketcetera.util.misc.ClassVersion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;

/* $License$ */

/**
 * Initializes the users data store.
 * 
 * @author <a href="mailto:colin@marketcetera.com">Colin DuPlantis</a>
 * @version $Id: UserInitializer.java 82384 2012-07-20 19:09:59Z colin $
 * @since $Release$
 */
@ClassVersion("$Id: UserInitializer.java 82384 2012-07-20 19:09:59Z colin $")
public class UserInitializer
        implements Initializer
{
    /* (non-Javadoc)
     * @see org.marketcetera.dao.impl.Initializer#initialize()
     */
    @Override
    public void initialize()
    {
        try {
            // assign default authorities to default groups
            Group adminGroup = dataService.getGroupDao().getByName(SystemGroup.ADMINISTRATORS.name());
            // add admin authority to admin group
            Authority adminAuthority = dataService.getAuthorityDao().getByName(SystemAuthority.ROLE_ADMIN.name());
            adminGroup.getAuthorities().add(adminAuthority);
            // update admin group
            dataService.getGroupDao().save(adminGroup);
            // add user authority to user group
            Group userGroup = dataService.getGroupDao().getByName(SystemGroup.USERS.name());
            Authority userAuthority = dataService.getAuthorityDao().getByName(SystemAuthority.ROLE_USER.name());
            userGroup.getAuthorities().add(userAuthority);
            dataService.getGroupDao().save(userGroup);
            // add users as specified
            if(users != null) {
                for(UserSpecification spec : users) {
                    try {
                        execute(spec);
                    } catch (Exception e) {
                        SLF4JLoggerProxy.warn(UserInitializer.class,
                                              e,
                                              "Could not add {}",
                                              spec);
                    }
                }
            }
        } catch (Exception e) {
            SLF4JLoggerProxy.warn(UserInitializer.class,
                                  e,
                                  "Could not initialize authorities, quitting"); // TODO
            return;
        } finally {
            // remove temp authentication
            SecurityContextHolder.getContext().setAuthentication(null);
        }
    }
    /**
     * Get the users value.
     *
     * @return a <code>Set&lt;UserSpecification&gt;</code> value
     */
    public Set<UserSpecification> getUsers()
    {
        return users;
    }
    /**
     * Sets the users value.
     *
     * @param a <code>Set<UserSpecification></code> value
     */
    public void setUsers(Set<UserSpecification> inUsers)
    {
        users = inUsers;
    }
    /**
     * Executes the given specification. 
     *
     * @param inUserSpecification a <code>UserSpecification</code> value
     */
    private void execute(UserSpecification inUserSpecification)
    {
        User user = userFactory.create(inUserSpecification.getUsername(),
                                       passwordEncoder.encode(inUserSpecification.getPassword()));
        dataService.getUserDao().add(user);
        SLF4JLoggerProxy.info(UserInitializer.class,
                              "{} created",
                              user);
        for(String group : inUserSpecification.getGroups()) {
            addUserToGroup(user,
                           group);
        }
    }
    /**
     * Adds the given user to the group with the given name.
     *
     * @param inUser a <code>User</code> value
     * @param inGroupName a <code>String</code> value
     */
    private void addUserToGroup(User inUser,
                                String inGroupName)
    {
        Group group = dataService.getGroupDao().getByName(inGroupName);
        if(group != null) {
            group.getUsers().add(inUser);
            dataService.getGroupDao().save(group);
            SLF4JLoggerProxy.info(UserInitializer.class,
                                  "{} added to {}",
                                  inUser,
                                  group);
        } else {
            SLF4JLoggerProxy.warn(UserInitializer.class,
                                  "Cannot add {} to {} because group does not exist",
                                  inUser,
                                  inGroupName);
        }
    }
    /**
     * users to create
     */
    private Set<UserSpecification> users;
    /**
     * provides data services
     */
    @Autowired
    private DataAccessService dataService;
    /**
     * constructs user objects
     */
    @Autowired
    private UserFactory userFactory;
    /**
     * password encoder value
     */
    @Autowired
    private PasswordEncoder passwordEncoder;
}
