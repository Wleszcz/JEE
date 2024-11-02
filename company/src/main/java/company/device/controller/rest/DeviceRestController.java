package company.device.controller.rest;

import company.component.DtoFunctionFactory;
import jakarta.inject.Inject;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.ws.rs.BadRequestException;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriBuilder;
import jakarta.ws.rs.core.UriInfo;
import lombok.SneakyThrows;
import company.device.controller.api.DeviceController;
import company.device.dto.GetDeviceResponse;
import company.device.dto.GetDevicesResponse;
import company.device.dto.PatchDeviceRequest;
import company.device.dto.PutDeviceRequest;
import company.device.entity.Device;
import company.device.service.DeviceService;

import java.io.InputStream;
import java.util.UUID;

/**
 * Simple framework agnostic implementation of controller.
 */
@Path("")//Annotation required by the specification.
public class DeviceRestController implements DeviceController {

    /**
     * Device service.
     */
    private final DeviceService service;

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
     * @param service device service
     * @param factory factory producing functions for conversion between DTO and entities
     * @param uriInfo allows to create {@link UriBuilder} based on current request
     */
    @Inject
    public DeviceRestController(
            DeviceService service,
            DtoFunctionFactory factory,
            @SuppressWarnings("CdiInjectionPointsInspection") UriInfo uriInfo
    ) {
        this.service = service;
        this.factory = factory;
        this.uriInfo = uriInfo;
    }

    @Override
    public GetDevicesResponse getDevices() {
        return factory.devicesToResponse().apply(service.findAll());
    }

    @Override
    public GetDevicesResponse getBrandDevices(UUID id) {
        return service.findAllByBrand(id)
                .map(factory.devicesToResponse())
                .orElseThrow(NotFoundException::new);
    }

    @Override
    public GetDevicesResponse getUserDevices(UUID id) {
        return service.findAllByUser(id)
                .map(factory.devicesToResponse())
                .orElseThrow(NotFoundException::new);
    }

    @Override
    public GetDeviceResponse getDevice(UUID id) {
        return service.find(id)
                .map(factory.deviceToResponse())
                .orElseThrow(NotFoundException::new);
    }

    @Override
    @SneakyThrows
    public void putDevice(UUID id, PutDeviceRequest request) {
        try {
            service.create(factory.requestToDevice().apply(id, request));
            response.setHeader("Location", uriInfo.getBaseUriBuilder()
                    .path(DeviceController.class, "getDevice")
                    .build(id)
                    .toString());
            throw new WebApplicationException(Response.Status.CREATED);
        } catch (IllegalArgumentException ex) {
            throw new BadRequestException(ex);
        }
    }

    @Override
    public void patchDevice(UUID id, PatchDeviceRequest request) {
        service.find(id).ifPresentOrElse(
                entity -> service.update(factory.updateDevice().apply(entity, request)),
                () -> {
                    throw new NotFoundException();
                }
        );
    }

    @Override
    public void deleteDevice(UUID id) {
        service.find(id).ifPresentOrElse(
                entity -> service.delete(id),
                () -> {
                    throw new NotFoundException();
                }
        );
    }

}
