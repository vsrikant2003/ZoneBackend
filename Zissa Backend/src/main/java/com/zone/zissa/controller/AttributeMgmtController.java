package com.zone.zissa.controller;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.json.JSONException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zone.zissa.exception.ConflictException;
import com.zone.zissa.exception.DataNotFoundException;
import com.zone.zissa.exception.ExceptionHandlerClass;
import com.zone.zissa.model.AttrDataType;
import com.zone.zissa.model.Attribute;
import com.zone.zissa.model.AttributeValue;
import com.zone.zissa.model.ResourceAttribute;
import com.zone.zissa.model.ResourcebinAttribute;
import com.zone.zissa.repos.ResourceAttributeRepository;
import com.zone.zissa.repos.ResourcebinAttributeRepository;
import com.zone.zissa.response.AttrServiceResponse;
import com.zone.zissa.response.RestAPIMessageConstants;
import com.zone.zissa.response.ServiceResponse;
import com.zone.zissa.svcs.AttributeService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.Authorization;

/**
 * The AttributeMgmtController class.
 */
@RestController
@RequestMapping("/v1/attributes")
@Api(value = "zissa", tags = { "Attribute-mgmt-controller" })
public class AttributeMgmtController {

    private static final Logger LOGGER = LoggerFactory.getLogger(AttributeMgmtController.class);

    @Autowired
    private AttributeService attributeServiceImpl;

    @Autowired
    private ResourceAttributeRepository resourceAttributeRepo;

    @Autowired
    private ResourcebinAttributeRepository resourceBinAttributeRepo;

    /**
     * add Attribute controller.
     *
     * @param attributeData
     * @return Attribute
     */
    @ApiImplicitParams({
            @ApiImplicitParam(name = "attributeData", value = "attribute Data", required = true, dataType = "String", paramType = "body") })
    @ApiOperation(value = "Add Attribute", notes = "Return success response if success, or exception if something wrong", response = Attribute.class, httpMethod = "POST", authorizations = {
            @Authorization(value = "basicAuth") })
    @ApiResponses(value = { @ApiResponse(code = 200, message = "OK"), @ApiResponse(code = 409, message = "Conflict"),
            @ApiResponse(code = 403, message = "Forbidden"),
            @ApiResponse(code = 500, message = "Internal Server Error"),
            @ApiResponse(code = 400, message = "Bad Request") })
    @PostMapping
    public ServiceResponse<Attribute> addAttribute(@Valid @RequestBody String attributeData) {
        LOGGER.debug("Add new Attribute Controller implementation");
        ServiceResponse<Attribute> response = new ServiceResponse<>();

        try {
            Attribute attribute = attributeServiceImpl.addAttribute(attributeData);

            response.setData(attribute);
            response.setStatus(HttpServletResponse.SC_CREATED);
            response.setMessage(RestAPIMessageConstants.ADD_ATTRIBUTE);
        } catch (ConflictException ex) {
            LOGGER.error(RestAPIMessageConstants.ADDING_ATTRIBUTE_ERROR, ex);
            return ExceptionHandlerClass.handle(ex);
        } catch (JSONException | DataIntegrityViolationException ex) {
            LOGGER.error(RestAPIMessageConstants.ADDING_ATTRIBUTE_ERROR, ex);
            return ExceptionHandlerClass.handleServiceResponse(HttpServletResponse.SC_BAD_REQUEST,
                    RestAPIMessageConstants.ADD_ATTRIBUTE_FAILURE);
        } catch (Exception ex) {
            LOGGER.error(RestAPIMessageConstants.ADDING_ATTRIBUTE_ERROR, ex);
            return ExceptionHandlerClass.handleServiceResponse(HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                    RestAPIMessageConstants.ADD_ATTRIBUTE_FAILURE);
        }
        return response;
    }

    /**
     * Delete Attribute controller.
     *
     * @param attributeID
     * @return ServiceResponse
     */
    @ApiOperation(value = "Delete Attribute", notes = "Return success response if success, or exception if something wrong", response = ServiceResponse.class, httpMethod = "DELETE", authorizations = {
            @Authorization(value = "basicAuth") })
    @ApiResponses(value = { @ApiResponse(code = 200, message = "OK"), @ApiResponse(code = 403, message = "Forbidden"),
            @ApiResponse(code = 409, message = "Conflict"),
            @ApiResponse(code = 500, message = "Internal Server Error") })
    @DeleteMapping("/{attributeID}")
    public ServiceResponse deleteAttribute(
            @ApiParam(value = "attributeID of the attribute which needs to be deleted", required = true) @NotNull @PathVariable Short attributeID) {
        LOGGER.debug("Delete Attribute Controller implementation");

        ServiceResponse response = new ServiceResponse();

        try {
            attributeServiceImpl.deleteAttribute(attributeID);
            response.setStatus(HttpServletResponse.SC_OK);
            response.setMessage(RestAPIMessageConstants.DELETE_ATTRIBUTE);
        } catch (DataNotFoundException ex) {
            LOGGER.error(RestAPIMessageConstants.DELETE_ATTRIBUTE, ex);
            return ExceptionHandlerClass.handle(ex);
        } catch (DataIntegrityViolationException e) {
            LOGGER.error(RestAPIMessageConstants.ATTRIBUTE_DELETION_ERROR, e);
            return ExceptionHandlerClass.handleServiceResponse(HttpServletResponse.SC_CONFLICT,
                    RestAPIMessageConstants.ATTRIBUTE_DELETION_EXCEPTION);
        } catch (Exception e) {
            LOGGER.error(RestAPIMessageConstants.ATTRIBUTE_DELETION_ERROR, e);
            return ExceptionHandlerClass.handleServiceResponse(HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                    RestAPIMessageConstants.ATTRIBUTE_DELETION_FAILURE);
        }
        return response;
    }

    /**
     * Get the all attributes controller.
     *
     * @return List<Attribute>
     */
    @ApiOperation(value = "View list of all attributes", notes = "Return all attributes", response = Attribute.class, responseContainer = "List", httpMethod = "GET", authorizations = {
            @Authorization(value = "basicAuth") })
    @ApiResponses(value = { @ApiResponse(code = 200, message = "OK"), @ApiResponse(code = 403, message = "Forbidden"),
            @ApiResponse(code = 500, message = "Internal Server Error") })
    @GetMapping
    public ServiceResponse<List<Attribute>> getAllAttributes() {
        LOGGER.debug("Get all Attributes Controller implementation");
        ServiceResponse<List<Attribute>> response = new ServiceResponse<>();
        try {
            List<Attribute> allAttributes = attributeServiceImpl.getAllAttributes();
            response.setStatus(HttpServletResponse.SC_OK);
            response.setMessage(RestAPIMessageConstants.GETTING_ATTRIBUTES);
            response.setData(allAttributes);
        } catch (Exception e) {
            LOGGER.error(RestAPIMessageConstants.GETTING_ATTRIBUTES_EXCEPTION, e);
            return ExceptionHandlerClass.handleServiceResponse(HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                    RestAPIMessageConstants.GETTING_ATTRIBUTES_FAILURE);
        }
        return response;
    }

    /**
     * Get all attribute datatypes controller.
     *
     * @return List<AttrDataType>
     */
    @ApiOperation(value = "View list of all attribute datatypes", notes = "Return all attribute datatypes", response = AttrDataType.class, responseContainer = "List", httpMethod = "GET", authorizations = {
            @Authorization(value = "basicAuth") })
    @ApiResponses(value = { @ApiResponse(code = 200, message = "OK"), @ApiResponse(code = 403, message = "Forbidden"),
            @ApiResponse(code = 500, message = "Internal Server Error") })
    @GetMapping("/attributeDataTypes")
    public ServiceResponse<List<AttrDataType>> getAllAttributeDataTypes() {
        LOGGER.debug("Get all Attribute DataTypes Controller implementation");

        ServiceResponse<List<AttrDataType>> response = new ServiceResponse<>();
        try {

            List<AttrDataType> attributeDataType = attributeServiceImpl.getAllAttributeDataTypes();
            response.setStatus(HttpServletResponse.SC_OK);
            response.setMessage(RestAPIMessageConstants.GETTING_ATTRIBUTE_DATATYPES);
            response.setData(attributeDataType);

        } catch (Exception e) {
            LOGGER.error(RestAPIMessageConstants.GETTING_ATTRIBUTE_DATATYPES_ERROR, e);
            return ExceptionHandlerClass.handleServiceResponse(HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                    RestAPIMessageConstants.GETTING_ATTRIBUTE_DATATYPES_FAILURE);

        }
        return response;
    }

    /**
     * Gets the attribute details by id controller.
     *
     * @param attributeID
     * @return Attribute
     */
    @ApiOperation(value = "View attribute details by attributeid", notes = "Return attribute details", response = Attribute.class, httpMethod = "GET", authorizations = {
            @Authorization(value = "basicAuth") })
    @ApiResponses(value = { @ApiResponse(code = 200, message = "OK"), @ApiResponse(code = 403, message = "Forbidden"),
            @ApiResponse(code = 500, message = "Internal Server Error") })
    @GetMapping("/{attributeID}")
    public AttrServiceResponse<Attribute> getAttributeDetailsById(
            @ApiParam(value = "attributeID for which you need attribute details", required = true) @NotNull @PathVariable Short attributeID) {
        LOGGER.debug("Get Attribute Details Controller implementation");
        try {
            Attribute attributeObject = attributeServiceImpl.getAttributeInfoById(attributeID);
            List<ResourceAttribute> resourceAttributeList = resourceAttributeRepo.findByAttribute(attributeObject);
            List<ResourcebinAttribute> resourceBinAttributeList = resourceBinAttributeRepo
                    .findByAttribute(attributeObject);

            if ((attributeObject != null) && (resourceAttributeList.isEmpty() && resourceBinAttributeList.isEmpty())) {
                return getAttributes(attributeObject, false);
            } else if (attributeObject != null) {
                return getAttributes(attributeObject, true);
            } else {
                return ExceptionHandlerClass.handleAttributeResponse(HttpServletResponse.SC_NOT_FOUND,
                        RestAPIMessageConstants.ATTRIBUTE_NOT_EXISTS);
            }
        } catch (Exception e) {
            LOGGER.error(RestAPIMessageConstants.GET_ATTRIBUTE_BY_ID_ERROR, e);
            return ExceptionHandlerClass.handleAttributeResponse(HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                    RestAPIMessageConstants.GETTING_ATTRIBUTE_BY_ID_FAILURE);

        }
    }

    /**
     * getAttributes method
     * 
     * @param attributeObject
     * @param value
     * @return
     */
    public AttrServiceResponse<Attribute> getAttributes(Attribute attributeObject, Boolean value) {

        AttrServiceResponse<Attribute> response = new AttrServiceResponse<>();

        List<AttributeValue> list = new ArrayList<>(attributeObject.getAttributeValues());

        List<AttributeValue> result = list.stream().sorted((o1, o2) -> Integer.valueOf(o1.getAttribute_Value_ID())
                .compareTo(Integer.valueOf(o2.getAttribute_Value_ID()))).collect(Collectors.toList());

        Set<AttributeValue> resultSet = new LinkedHashSet<>(result);
        attributeObject.setAttributeValues(resultSet);
        response.setStatus(HttpServletResponse.SC_OK);
        response.setMessage(RestAPIMessageConstants.GET_ATTRIBUTE_BY_ID);
        response.setInuse(value);
        response.setData(attributeObject);

        return response;
    }

    /**
     * Update attribute controller.
     *
     * @param attributeData
     * @return Attribute
     */
    @ApiImplicitParams({
            @ApiImplicitParam(name = "attributeData", value = "attribute Data", required = true, dataType = "String", paramType = "body") })
    @ApiOperation(value = "Update Attribute", notes = "Return success response if success, or exception if something wrong", response = Attribute.class, httpMethod = "PUT", authorizations = {
            @Authorization(value = "basicAuth") })
    @ApiResponses(value = { @ApiResponse(code = 200, message = "Ok"), @ApiResponse(code = 400, message = "BadRequest"),
            @ApiResponse(code = 403, message = "Forbidden"), @ApiResponse(code = 409, message = "Conflict"),
            @ApiResponse(code = 500, message = "Internal Server Error") })
    @PutMapping
    public ServiceResponse<Attribute> updateAttribute(@RequestBody String attributeData) {
        LOGGER.debug("Update Attribute Controller implementation");
        ServiceResponse<Attribute> response = new ServiceResponse<>();

        try {

            Attribute attribute = attributeServiceImpl.updateAttribute(attributeData);
            response.setData(attribute);
            response.setStatus(HttpServletResponse.SC_OK);
            response.setMessage(RestAPIMessageConstants.UPDATE_ATTRIBUTE);

        } catch (DataNotFoundException ex) {
            LOGGER.error(RestAPIMessageConstants.UPDATE_ATTRIBUTE, ex);
            return ExceptionHandlerClass.handle(ex);
        } catch (ConflictException ex) {
            LOGGER.error(RestAPIMessageConstants.ATTRIBUTE_UPDATION_ERROR, ex);
            return ExceptionHandlerClass.handle(ex);
        } catch (JSONException | DataIntegrityViolationException ex) {
            LOGGER.error(RestAPIMessageConstants.ATTRIBUTE_UPDATION_ERROR, ex);
            return ExceptionHandlerClass.handleServiceResponse(HttpServletResponse.SC_BAD_REQUEST,
                    RestAPIMessageConstants.ATTRIBUTE_UPDATION_FAILURE);
        } catch (Exception e) {
            LOGGER.error(RestAPIMessageConstants.ATTRIBUTE_UPDATION_ERROR, e);
            return ExceptionHandlerClass.handleServiceResponse(HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                    RestAPIMessageConstants.ATTRIBUTE_UPDATION_FAILURE);
        }

        return response;
    }
}
