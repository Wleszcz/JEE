package company.device.controller.api;

import company.device.dto.GetDeviceResponse;
import company.device.dto.GetDevicesResponse;
import company.device.dto.PatchDeviceRequest;
import company.device.dto.PutDeviceRequest;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;

import java.io.InputStream;
import java.util.UUID;

/**
 * Controller for managing collections devices' representations.
 */
@Path("")
public interface DeviceController {

    /**
     * @return all devices representation
     */
    @GET
    @Path("/devices")
    @Produces(MediaType.APPLICATION_JSON)
    GetDevicesResponse getDevices();

    /**
     * @param id Brand's id
     * @return devices representation
     */
    @GET
    @Path("/brands/{id}/devices")
    @Produces(MediaType.APPLICATION_JSON)
    GetDevicesResponse getBrandDevices(@PathParam("id") UUID id);

    /**
     * @param id user's id
     * @return devices representation
     */
    @GET
    @Path("/users/{id}/devices/")
    @Produces(MediaType.APPLICATION_JSON)
    GetDevicesResponse getUserDevices(@PathParam("id") UUID id);

    /**
     * @param id device's id
     * @return device representation
     */
    @GET
    @Path("/devices/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    GetDeviceResponse getDevice(@PathParam("id") UUID id);

    /**
     * @param id      device's id
     * @param request new device representation
     */
    @PUT
    @Path("/devices/{id}")
    @Consumes({MediaType.APPLICATION_JSON})
    void putDevice(@PathParam("id") UUID id, PutDeviceRequest request);

    /**
     * @param id      device's id
     * @param request device update representation
     */
    @PATCH
    @Path("/devices/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    void patchDevice(@PathParam("id")UUID id, PatchDeviceRequest request);

    /**
     * @param id device's id
     */
    @DELETE
    @Path("/devices/{id}")
    void deleteDevice(@PathParam("id") UUID id);

//    /**
//     * @param id device's id
//     * @return device's image
//     */
//    @GET
//    @Path("/devices/{id}/image")
//    @Produces("image/png")
//    byte[] getDeviceImage(@PathParam("id") UUID id);
//
//    /**
//     * @param id       device's id
//     * @param image device's new avatar
//     */
//    @PUT
//    @Path("/devices/{id}/image")
//    @Consumes(MediaType.MULTIPART_FORM_DATA)
//    void putDeviceImage(@PathParam("id") UUID id, InputStream image);

}
