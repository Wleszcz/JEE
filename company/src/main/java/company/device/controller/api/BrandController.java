package company.device.controller.api;

import company.device.dto.GetBrandResponse;
import company.device.dto.GetBrandsResponse;
import company.device.dto.PatchBrandRequest;
import company.device.dto.PutBrandRequest;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;

import java.util.UUID;

/**
 * Controller for managing collections Brands' representations.
 */
@Path("")
public interface BrandController {

    /**
     * @return all Brands representation
     */
    @GET
    @Path("/brands")
    @Produces(MediaType.APPLICATION_JSON)
    GetBrandsResponse getBrands();

    @GET
    @Path("/brands/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    GetBrandResponse getBrand(@PathParam("id") UUID id);

    @DELETE
    @Path("/brands/{id}")
    void deleteBrand(@PathParam("id") UUID id);

    /**
     * @param id      device's id
     * @param request new device representation
     */
    @PUT
    @Path("/brands/{id}")
    @Consumes({MediaType.APPLICATION_JSON})
    void putBrand(@PathParam("id") UUID id, PutBrandRequest request);

    /**
     * @param id      device's id
     * @param request device update representation
     */
    @PATCH
    @Path("/brands/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    void patchBrand(@PathParam("id")UUID id, PatchBrandRequest request);

}
