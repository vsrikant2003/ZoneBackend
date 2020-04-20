package com.zone.zissa.controller;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Base64;
import java.util.List;
import java.util.Scanner;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.zone.zissa.exception.AccessDeniedException;
import com.zone.zissa.exception.ExceptionHandlerClass;
import com.zone.zissa.exception.NoContentException;
import com.zone.zissa.exception.NotFoundException;
import com.zone.zissa.model.Allocation;
import com.zone.zissa.model.Resource;
import com.zone.zissa.response.PageServiceResponse;
import com.zone.zissa.response.RestAPIMessageConstants;
import com.zone.zissa.response.ServiceResponse;
import com.zone.zissa.svcs.AllocationService;
import com.zone.zissa.svcs.impl.ResourceServiceImpl;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.Authorization;

/**
 * The AllocationMgmtController class.
 */
@RestController
@RequestMapping("/v1/allocations")
@Api(value = "zissa", tags = { "Allocation-mgmt-controller" })
public class AllocationMgmtController {

    private static final Logger LOGGER = LoggerFactory.getLogger(AllocationMgmtController.class);

    @Autowired
    private AllocationService allocationServiceImpl;

    @Value("${redmine-auth-token}")
    private String redmineAuthToken;

    @Value("${redmine-url}")
    private String redmineUrl;

    @Autowired
    private ResourceServiceImpl resourceService;

    /**
     * Get all allocation details for a resource by resourceid controller.
     *
     * @param resource_ID
     * @return List<Allocation>
     */
    @ApiOperation(value = "View allocation details by resourceid", notes = "Return allocation details", response = Allocation.class, responseContainer = "List", httpMethod = "GET", authorizations = {
            @Authorization(value = "basicAuth") })
    @ApiResponses(value = { @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 204, response = ServiceResponse.class, message = "No Content"),
            @ApiResponse(code = 403, message = "Forbidden"),
            @ApiResponse(code = 500, message = "Internal Server Error"),
            @ApiResponse(code = 404, message = "Not Found") })
    @GetMapping("/{resourceID}")
    public ServiceResponse<List<Allocation>> getAllAllocationDetailsByResource(
            @ApiParam(value = "resourceid of resource for which you need allocation details", required = true) @PathVariable Integer resourceID) {
        LOGGER.debug("Get all Allocation Details by Resource Controller implementation");
        ServiceResponse<List<Allocation>> response = new ServiceResponse<>();
        try {
            List<Allocation> allocationList = allocationServiceImpl.getAllAllocationDetailsByResource(resourceID);
            response.setStatus(HttpServletResponse.SC_OK);
            response.setMessage(RestAPIMessageConstants.GET_ALL_ALLOCATIONS);
            response.setData(allocationList);
        } catch (NoContentException ex) {
            LOGGER.error(RestAPIMessageConstants.GET_ALLOCATION_DETAILS_BY_RESOURCE_ERROR, ex);
            return ExceptionHandlerClass.handle(ex);
        } catch (AccessDeniedException ex) {
            LOGGER.error(RestAPIMessageConstants.GET_ALLOCATION_DETAILS_BY_RESOURCE_ERROR, ex);
            return ExceptionHandlerClass.handle(ex);
        } catch (NotFoundException ex) {
            LOGGER.error(RestAPIMessageConstants.GET_ALLOCATION_DETAILS_BY_RESOURCE_ERROR, ex);
            return ExceptionHandlerClass.handle(ex);
        } catch (Exception ex) {
            LOGGER.error(RestAPIMessageConstants.GET_ALLOCATION_DETAILS_BY_RESOURCE_ERROR, ex);
            return ExceptionHandlerClass.handleServiceResponse(HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                    RestAPIMessageConstants.GETTING_ALLOCATION_HISTORY_FAILURE);
        }
        return response;
    }

    /**
     * get Resources By SearchTerm controller.
     * 
     * @param categoryID
     * @param page
     * @param size
     * @param searchText
     * @param direction
     * @param attrname
     * @return PageServiceResponse<Object>
     */
    @ApiOperation(value = "View list of resource allocation details by categoryid,page,size,searchText and direction", notes = "Return allocation details", response = Resource.class, responseContainer = "List", httpMethod = "GET", authorizations = {
            @Authorization(value = "basicAuth") })
    @ApiResponses(value = { @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 500, message = "Internal Server Error"),
            @ApiResponse(code = 403, message = "Forbidden"), })
    @GetMapping
    public PageServiceResponse<Object> getResourcesBySearchTerm(@RequestParam("category_ID") List<Integer> categoryID,
            @RequestParam("page") int page, @RequestParam("size") int size,
            @RequestParam("searchText") String searchText, @RequestParam("direction") String direction,
            @RequestParam(value = "attrid", required = false) short attrid) {
        LOGGER.debug("Get all Resources allocation details by Searchterm Controller implementation");
        PageServiceResponse<Object> response = new PageServiceResponse<>();
        try {
            Object resourceSearchList = allocationServiceImpl.getAllResourcesBySearchTerm(categoryID, page, size,
                    searchText, direction, attrid);
            response.setStatus(HttpServletResponse.SC_OK);
            response.setData(resourceSearchList);
            response.setMessage(RestAPIMessageConstants.GET_RESOURCE_WITH_SEARCHTERM);
            response.setTotalRecords(resourceService.getCount());
        } catch (Exception ex) {
            LOGGER.error(RestAPIMessageConstants.GET_RESOURCE_SEARCH_ERROR, ex);
            return ExceptionHandlerClass.handlePageServiceResponse(HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                    RestAPIMessageConstants.GET_RESOURCE_WITH_SEARCH_FAILURE);
        }
        return response;
    }

    /**
     * Get all Projects controller.
     *
     * @return String
     */
    @ApiOperation(value = "View list of projects", notes = "Return all projects", response = String.class, httpMethod = "GET")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 500, message = "Internal Server Error"),
            @ApiResponse(code = 403, message = "Forbidden"), })
    @GetMapping("/projects")
    public ServiceResponse<String> getAllProjects() {
        LOGGER.debug("Get all redmine projects Controller implementation");
        String json = null;
        ServiceResponse<String> response = new ServiceResponse<>();
        try {
            byte[] encodedBytes = Base64.getEncoder().encode(redmineAuthToken.getBytes());
            String authorization = new String(encodedBytes);
            URL url = new URL(redmineUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoOutput(true);
            connection.setInstanceFollowRedirects(false);
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("Authorization", "Basic " + authorization);
            connection.connect();
            InputStream inStream = connection.getInputStream();
            json = streamToString(inStream);
            response.setStatus(HttpServletResponse.SC_OK);
            response.setMessage(RestAPIMessageConstants.GET_PROJECTS);
            response.setData(json);

        } catch (Exception ex) {
            LOGGER.error(RestAPIMessageConstants.PROJECTS_GETTING_ERROR, ex);
            return ExceptionHandlerClass.handleServiceResponse(HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                    RestAPIMessageConstants.PROJECTS_GETTING_FAILURE);
        }
        return response;
    }

    /**
     * streamToString method
     * 
     * @param inputStream
     * @return String
     */
    private static String streamToString(InputStream inputStream) {
        return new Scanner(inputStream, "UTF-8").useDelimiter("\\Z").next();
    }
}
