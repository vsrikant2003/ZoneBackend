package com.zone.zissa.response;

public class RestAPIMessageConstants {

    private RestAPIMessageConstants() {
        throw new IllegalAccessError("Utility class");
    }

    /**
     * Allocation module
     */
    public static final String NO_ALLOCATION_CONTENT = "No Allocation details available for given resource id";

    public static final String ALLOCATION_ACCESS_DENIED = "Permission not exist for getting allocation details by resource id";

    public static final String RESOURCE_NOT_FOUND = "Resource not exists";

    public static final String GET_ALL_ALLOCATIONS = "Getting Allocation History by Resource is successful";

    public static final String GETTING_ALLOCATION_HISTORY_FAILURE = "Failed in getting Allocation History Details by Resource";

    public static final String GET_ALLOCATION_DETAILS_BY_RESOURCE_ERROR = "Get Allocation Details By Resource throw an exception";

    public static final String GET_RESOURCE_WITH_SEARCHTERM = "Getting all the allocation resources details with search term is successful";

    public static final String GET_RESOURCE_SEARCH_ERROR = "Getting allocation resources by searchTerm throw an exception";

    public static final String GET_RESOURCE_WITH_SEARCH_FAILURE = "Failed in getting allocation Resources details with search term details";

    public static final String GET_PROJECTS = "Getting All projects is successful";

    public static final String PROJECTS_GETTING_ERROR = "Getting all projects throw an exception";

    public static final String PROJECTS_GETTING_FAILURE = "Failed to get all projects";

    /**
     * User module
     */

    public static final String ADD_USER = "User Created Successfully";

    public static final String USER_ADD_ERROR = "Adding user throw an exception";

    public static final String USER_ADD_FAILURE = "Failed in User Creation";

    public static final String USER_EXISTS = "User Already Exists";

    public static final String UPDATE_USER = "User updated successfully";

    public static final String USER_UPDATE_ERROR = "Updating user throw an exception";

    public static final String USER_UPDATE_FAILURE = "Failed in User Updation";

    public static final String GETTING_USER = "Getting all the Users is successful";

    public static final String GETTING_USER_ERROR = "Get all users throw an exception";

    public static final String GETTING_USER_FAILURE = "Failed in getting users";

    public static final String DELETE_USER = "User deleted successfully";

    public static final String USER_DELETE_ERROR = "Deleting user throw an exception";

    public static final String USER_DELETION_FAILURE = "Failed in User Deletion";

    public static final String USER_DELETION_EXCEPTION = "Cannot delete a parent row: a foreign key constraint fails";

    /**
     * Role module
     */

    public static final String ROLE_EXISTS = "Role Already Exists";

    public static final String ROLE_NAME_EXISTS = "Role name Already Exists";

    public static final String ADD_ROLE = "Role Created Successfully";

    public static final String ROLE_ADDING_ERROR = "Adding role throw an exception";

    public static final String ROLE_ADDING_FAILURE = "Failed in Role Creation";

    public static final String ROLE_NOT_EXISTS = "Role not Exists";

    public static final String UPDATE_ROLE = "Role Updated Successfully";

    public static final String ROLE_UPDATION_FAILURE = "Failed in Role Updation";

    public static final String GETTING_ALL_PERMISSIONS_BY_ROLE = "Getting Role Permission Details is successful";

    public static final String GETTING_ALL_PERMISSIONS_BY_ROLE_FAILURE = "Failed in Getting Role Permission Details";

    public static final String DELETE_ROLE = "Role deleted successful";

    public static final String DELETE_ROLE_ERROR = "Deleting role throw an exception";

    public static final String ROLE_DELETION_FAILURE = "Failed in Role Deletion";

    public static final String ROLE_DELETION_EXCEPTION = "Cannot delete a parent row: a foreign key constraint fails";

    public static final String UPDATE_ROLE_ERROR = "Updating role throw an exception";

    public static final String GETTING_PERMISSIONS_BY_ROLE_ERROR = "Getting all permissions by role throw an exception";

    public static final String NO_PERMISSION_FOR_CATEGORY_ROLE = "No Permissions available for the given category and Role";

    public static final String GETTING_ROLES = "Getting all the Roles is successful";

    public static final String GETTING_ROLES_ERROR = "Getting all the Roles throw exception";

    public static final String GETTING_ROLES_FAILURE = "Failed in Getting Roles";

    public static final String GETTING_PERMISSIONS_BY_ROLE_CATEGORY = "Getting Permission Details is successful";

    public static final String GETTING_PERMISSIONS_BY_ROLE_CATEGORY_FAILURE = "Failed in Getting Permission Details";

    public static final String GETTING_PERMISSIONS_BY_ROLE_CATEGORY_ERROR = "Getting all permissions by role and category throw an exception";

    /**
     * Ldap module
     */
    public static final String GETTING_LDAP_USERS = "Getting all the Ldap Users is Successful";

    public static final String GETTING_LDAP_USERS_ERROR = "getting all ldap users throw an exception";

    public static final String GETTING_LDAP_USERS_FAILURE = "Failed in Getting all the Ldap Users";

    public static final String LDAP_USER_NOT_EXISTS = "No users exists with the search string entered";

    public static final String LDAP_USER_BY_SEARCH_STRING = "Getting all the Ldap Users by Search string is Successful";

    public static final String LDAP_USER_BY_SEARCH_STRING_FAILURE = "Failed in Getting all the Ldap Users by Search string";

    public static final String LDAP_SEARCH_ERROR = "Getting all Ldap Users by Search string throw an exception";

    /**
     * Login module
     */
    public static final String LOGIN_SUCCESS = "Login Successful";

    public static final String LOGIN_ERROR = "Login throw an exception";

    public static final String LOGIN_FAILURE = "Bad Credentials";

    /**
     * Attribute module
     */
    public static final String ATTRIBUTE_EXISTS = "Attribute already exists";

    public static final String ATTRIBUTE_NAME_EXISTS = "Attribute name already exists";

    public static final String ADD_ATTRIBUTE = "Attribute Created Successfully";

    public static final String ADD_ATTRIBUTE_FAILURE = "Failed in Attribute Creation";

    public static final String ADDING_ATTRIBUTE_ERROR = "Adding attribute throw an exception";

    public static final String UPDATE_ATTRIBUTE = "Updated Attribute successfully";

    public static final String ATTRIBUTE_UPDATE_CONFLICT = "Attribute values is already in use";

    public static final String ATTRIBUTE_UPDATION_ERROR = "Updating attribute throw an exception";

    public static final String ATTRIBUTE_UPDATION_FAILURE = "Failed to update attribute";

    public static final String DELETE_ATTRIBUTE = "Attribute is deleted successfully";

    public static final String ATTRIBUTE_DELETION_ERROR = "Deleting attribute throw an exception";

    public static final String ATTRIBUTE_DELETION_FAILURE = "Failed in Attribute Deletion";

    public static final String ATTRIBUTE_DELETION_EXCEPTION = "Cannot delete a parent row: a foreign key constraint fails";

    public static final String GETTING_ATTRIBUTES = "Getting all the Attributes is successful";

    public static final String GETTING_ATTRIBUTES_EXCEPTION = "Getting all attribute throw an exception";

    public static final String GETTING_ATTRIBUTES_FAILURE = "Failed in getting Attributes";

    public static final String GETTING_ATTRIBUTE_DATATYPES = "Getting all the Attribute DataTypes is successful";

    public static final String GETTING_ATTRIBUTE_DATATYPES_ERROR = "Getting Attribute DataTypes throw an exception";

    public static final String GETTING_ATTRIBUTE_DATATYPES_FAILURE = "Failed to Get all Attribute DataTypes";

    public static final String GET_ATTRIBUTE_BY_ID = "Getting Attribute Details is successful";

    public static final String GET_ATTRIBUTE_BY_ID_ERROR = "Getting attribute details by id throw an exception";

    public static final String GETTING_ATTRIBUTE_BY_ID_FAILURE = "Failed in getting Attribute Details by id";

    public static final String ATTRIBUTE_NOT_EXISTS = "Attribute not Exists";

    /**
     * Category module
     */
    public static final String ADD_CATEGORY = "Category Created Successfully";

    public static final String ADDING_CATEGORY_ERROR = "Adding category throw an exception";

    public static final String ADDING_CATEGORY_FAILURE = "Failed in Category Creation";

    public static final String CODE_PATTERN_EXISTS = "Code Pattern Already Exists";

    public static final String CATEGORY_EXISTS = "Category Already Exists";

    public static final String CATEGORY_NOT_EXISTS = "Category not Exists";

    public static final String NO_ATTRIBUTE_FOR_CATEGORY = "No attributes available for the given category";

    public static final String UPDATE_CATEGORY = "Category updated successfully";

    public static final String CATEGORY_UPDATION_ERROR = "Updating category throw an exception";

    public static final String CATEGORY_UPDATION_FAILURE = "Failed in Category updation";

    public static final String CATEGORY_NAME_EXISTS = "Category name Already Exists";

    public static final String DELETE_CATEGORY = "Category deleted successfully";

    public static final String DELETE_CATEGORY_ERROR = "Deleting category throw an exception";

    public static final String CATEGORY_DELETION_FAILURE = "Failed in Category Deletion";

    public static final String CATEGORY_DELETION_EXCEPTION = "Cannot delete a parent row: a foreign key constraint fails";

    public static final String GET_CATEGORY = "Getting all the Categories is successful";

    public static final String GET_CATEGORY_ERROR = "Getting all Categories throw an exception";

    public static final String GET_CATEGORY_FAILURE = "Failed in Getting Categories";

    public static final String GET_CATEGORY_ATTRIBUTE = "Getting the Category related Attributes is successful";

    public static final String GET_CATEGORY_ATTRIBUTE_BY_CATEGORY_ERROR = "Getting attribute details by category throw an exception";

    public static final String GET_CATEGORY_ATTRIBUTE_FAILURE = "Failed in getting Category related Attributes by id";

    public static final String GET_CATEGORY_LIST_ATTRIBUTE = "Getting all the Category list related Attributes is successful";

    public static final String GET_CATEGORY_ATTRIBUTE_BY_CATEGORY_LIST_ERROR = "Getting attribute details by category list throw an exception";

    public static final String GET_CATEGORY_LIST_ATTRIBUTE_FAILURE = "Failed in Getting Category List related Attributes";

    public static final String GET_ALL_OPERATIONS = "Getting all the Operations is successful";

    public static final String GET_ALL_OPERATIONS_ERROR = "Getting operations list throw an exception";

    public static final String GET_ALL_OPERATIONS_FAILURE = "Failed in Getting operations";

    public static final String GET_CATEGORY_VIEW = "Getting all Categories with View Permission is successful";

    public static final String GET_CATEGORY_VIEW_ERROR = "Getting Categories with View Permission throw an exception";

    public static final String GET_CATEGORY_VIEW_FAILURE = "Failed in Getting Categories with View Permission";

    public static final String GET_CATEGORY_ADD = "Getting all Categories with add Permission is successful";

    public static final String GET_CATEGORY_ADD_ERROR = "Getting Categories with add Permission throw an exception";

    public static final String GET_CATEGORY_ADD_FAILURE = "Failed in Getting Categories with add Permission";

    public static final String GET_CATEGORY_DISPOSE = "Getting all Categories with dispose Permission is successful";

    public static final String GET_CATEGORY_DISPOSE_ERROR = "Getting Categories with dispose Permission throw an exception";

    public static final String GET_CATEGORY_DISPOSE_FAILURE = "Failed in Getting Categories with dispose Permission";

    public static final String GET_CATEGORY_ALLOCATION = "Getting all Categories with Allocate Permission is successful";

    public static final String GET_CATEGORY_ALLOCATION_ERROR = "Getting Categories with Allocate Permission throw an exception";

    public static final String GET_CATEGORY_ALLOCATION_FAILURE = "Failed in Getting Categories with Allocate Permission";

    /**
     * Resource module
     */

    public static final String RESOURCE_ADD_PERMISSION = "Access is denied to add resource";

    public static final String RESOURCE_UPDATE_PERMISSION = "Access is denied to add resource";

    public static final String RESOURCE_VIEW_PERMISSION = "Access is denied to get resource";

    public static final String RESOURCE_DELETE_PERMISSION = "Access is denied to delete resource";

    public static final String RESOURCE_DISPOSE_PERMISSION = "Access is denied to dispose resource";

    public static final String RESOURCE_RESTORE_PERMISSION = "Access is denied to restore resource";

    public static final String RESOURCE_ALLOCATION_PERMISSION = "Access is denied to allocate resource";

    public static final String RESOURCE_NOT_EXISTS = "Category exists but no resources available";

    public static final String DELETE_RESOURCE = "Resource deleted successful";

    public static final String DELETE_ALLOCATED_RESOURCE = "Allocated Resource cannot be deleted";

    public static final String DISPOSE_RESOURCE_REASON = "Data too long for reason to dispose resource";

    public static final String DISPOSE_ALLOCATED_RESOURCE = "Allocated Resources cannot be Disposed";

    public static final String RESOURCE_EXISTS_FAILURE = "Resource not exists";

    public static final String DISPOSE_RESOURCE_FAILURE = "No Dipsosed Resources available for the given category";

    public static final String ALLOCATION_RESOURCE_FAILURE = "Failed in Allocating some Resources with inventory code as they are already allocated";

    public static final String ADD_RESOURCE = "Resources Created Successfully";

    public static final String ADDING_RESOURCE_ERROR = "Adding resource throw an exception";

    public static final String ADDING_RESOURCE_FAILURE = "Failed in Resources Creation";

    public static final String GET_LAST_RESOURCE_BY_CATEGORY = "Getting Last Resource Details by Category is successful";

    public static final String GET_LAST_RESOURCE_BY_CATEGORY_ERROR = "Get last resource by category throw an exception";

    public static final String GET_LAST_RESOURCE_BY_CATEGORY_FAILURE = "Failed in Getting Last Resource Details";

    public static final String GET_DISPOSE_RESOURCE_BY_CATEGORY = "Getting disposed resources by Category is successful";

    public static final String GET_DISPOSED_RESOURCE_BY_CATEGORY_ERROR = "Get disposed resources by categoryId throw an exception";

    public static final String GET_DISPOSED_RESOURCE_BY_CATEGORY_FAILURE = "Failed in getting Category related disposed Resources";

    public static final String GET_RESOURCE = "Getting Resource Details is successful";

    public static final String GET_RESOURCE_ERROR = "Getting resource throw an exception";

    public static final String GET_RESOURCE_FAILURE = "Failed in Getting Resource Details";

    public static final String GET_DISPOSE_RESOURCE = "Getting Disposed Resource Details is successful";

    public static final String GET_DISPOSE_RESOURCE_ERROR = "Getting Dispose resource throw an exception";

    public static final String GET_DISPOSE_RESOURCE_FAILURE = "Failed in Getting Disposed Resource Details";

    public static final String GET_RESOURCE_DETAILS = "Getting Resources Details is successful";

    public static final String GET_RESOURCE_DETAILS_ERROR = "Getting Resources Details throw an exception";

    public static final String GET_RESOURCE_DETAILS_FAILURE = "Failed in Getting Resources Details";

    public static final String GET_ALL_RESOURCE_BY_CATEGORY = "Getting all the resources is successfull";

    public static final String GET_ALL_RESOURCE_BY_CATEGORY_ERROR = "Getting Category related Resources throw an exception";

    public static final String GET_ALL_RESOURCE_BY_CATEGORY_FAILURE = "Failed in getting Category related Resources";

    public static final String DELETE_RESOURCE_ERROR = "Delete Resource throw an exception";

    public static final String RESOURCE_DELETION_FAILURE = "Failed in Resource Deletion";

    public static final String UPDATE_RESOURCE = "Resources Updated Successfully";

    public static final String RESOURCE_UPDATION_FAILURE = "Failed in Resources Updation";

    public static final String UPDATE_RESOURCE_ERROR = "Updating resource throw an exception";

    public static final String DISPOSE_RESOURCE = "Resource is disposed successfully";

    public static final String DISPOSE_RESOURCE_ERROR = "Dispose resource throw an exception";

    public static final String RESOURCE_DISPOSE_FAILURE = "Failed in Resource Disposition";

    public static final String RESTORE_RESOURCE = "Resources is restored successfully";

    public static final String RESTORE_RESOURCE_FAILURE = "Failed in Resource Restoration";

    public static final String RESTORE_RESOURCE_ERROR = "Restoring resource throw an exception";

    public static final String GET_RESOURCE_BY_SEARCHTERM = "Getting all the resources by Searchterm is successful";

    public static final String GET_RESOURCE_BY_SEARCHTERM_ERROR = "Getting all the resources by Searchterm throw an exception";

    public static final String GET_RESOURCE_BY_SEARCHTERM_FAILURE = "Failed in getting Resources by search term";

    public static final String GET_DISPOSE_RESOURCE_BY_SEARCHTERM = "Getting all the disposed resources by searchterm is successful";

    public static final String GET_DISPOSE_RESOURCE_BY_SEARCHTERM_ERROR = "Getting all the disposed resources by Searchterm throw an exception";

    public static final String GET_DISPOSE_RESOURCE_BY_SEARCHTERM_FAILURE = "Failed in getting dispose Resources by search term";

    public static final String RESOURCE_ALLOCATION = "Resource Allocation or DeAllocation is Successful";

    public static final String RESOURCE_ALLOCATION_FAILURE = "Failed in resource Allocation or Deallocation";

    public static final String RESOURCE_ALLOCATION_ERROR = "Allocating or deallocating resources throw an exception";

}
