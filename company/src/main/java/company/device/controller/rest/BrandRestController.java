package company.device.controller.rest;

import company.component.DtoFunctionFactory;
import company.device.controller.api.DeviceController;
import company.device.dto.PatchBrandRequest;
import company.device.dto.PutBrandRequest;
import company.user.entity.UserRoles;
import jakarta.annotation.security.RolesAllowed;
import jakarta.ejb.EJB;
import jakarta.inject.Inject;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.ws.rs.BadRequestException;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.Path;
import company.device.controller.api.BrandController;
import company.device.dto.GetBrandResponse;
import company.device.dto.GetBrandsResponse;
import company.device.service.BrandService;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriBuilder;
import jakarta.ws.rs.core.UriInfo;

import java.util.UUID;

/**
 * Simple framework agnostic implementation of controller.
 */
@Path("")//Annotation required by the specification.
public class BrandRestController implements BrandController {

    /**
     * Brand service.
     */
    private BrandService service;

    /**
     * Factory producing functions for conversion between DTO and entities.
     */
    private final DtoFunctionFactory factory;

    /**
     * Allows to create {@link UriBuilder} based on current request.
     */
    private final UriInfo uriInfo;

    /**
     * Current HTTP Servlet response.
     */
    private HttpServletResponse response;

    @Context
    public void setResponse(HttpServletResponse response) {
        //ATM in this implementation only HttpServletRequest can be injected with CDI so JAX-RS injection is used.
        this.response = response;
    }

    /**
     * @param service brand service
     * @param factory factory producing functions for conversion between DTO and entities
     */
    @Inject
    public BrandRestController(DtoFunctionFactory factory, UriInfo uriInfo) {
        this.factory = factory;
        this.uriInfo = uriInfo;
    }

    /**
     * @param service profession service
     */
    @EJB
    public void setService(BrandService service) {
        this.service = service;
    }


    @Override
    @RolesAllowed(UserRoles.USER)
    public GetBrandsResponse getBrands() {
        return factory.brandsToResponse().apply(service.findAll());
    }

    @Override
    @RolesAllowed(UserRoles.USER)
    public GetBrandResponse getBrand(UUID id) {
        return service.find(id)
                .map(factory.brandToResponse())
                .orElseThrow(NotFoundException::new);
    }

    @Override
    @RolesAllowed(UserRoles.ADMIN)//Secure implementation, not the interface
    public void deleteBrand(UUID id) {
        service.find(id).ifPresentOrElse(
                entity -> service.delete(id),
                () -> {
                    throw new NotFoundException();
                }
        );
    }

    @Override
    @RolesAllowed(UserRoles.ADMIN)//Secure implementation, not the interface
    public void putBrand(UUID id, PutBrandRequest request) {
        try {
            service.create(factory.requestToBrand().apply(id, request));
            response.setHeader("Location", uriInfo.getBaseUriBuilder()
                    .path(BrandController.class, "getBrand")
                    .build(id)
                    .toString());
            throw new WebApplicationException(Response.Status.CREATED);
        } catch (IllegalArgumentException ex) {
            throw new BadRequestException(ex);
        }
    }

    @Override
    @RolesAllowed(UserRoles.ADMIN)//Secure implementation, not the interface
    public void patchBrand(UUID id, PatchBrandRequest request) {
        service.find(id).ifPresentOrElse(
                entity -> service.update(factory.updateBrand().apply(entity, request)),
                () -> {
                    throw new NotFoundException();
                }
        );
    }

}
