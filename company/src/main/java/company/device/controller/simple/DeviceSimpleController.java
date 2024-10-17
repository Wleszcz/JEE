package company.device.controller.simple;

import company.device.controller.api.DeviceController;
import company.device.dto.GetDeviceResponse;
import company.device.dto.GetDevicesResponse;
import company.device.dto.PatchDeviceRequest;
import company.device.dto.PutDeviceRequest;
import company.device.entity.Device;
import company.device.service.DeviceService;
import company.component.DtoFunctionFactory;
import company.controller.servlet.exception.BadRequestException;
import company.controller.servlet.exception.NotFoundException;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;

import java.io.InputStream;
import java.util.UUID;

/**
 * Simple framework agnostic implementation of controller.
 */
@RequestScoped
public class DeviceSimpleController implements DeviceController {

    /**
     * device service.
     */
    private final DeviceService service;

    /**
     * Factory producing functions for conversion between DTO and entities.
     */
    private final DtoFunctionFactory factory;

    /**
     * @param service device service
     * @param factory factory producing functions for conversion between DTO and entities
     */
    @Inject
    public DeviceSimpleController(DeviceService service, DtoFunctionFactory factory) {
        this.service = service;
        this.factory = factory;
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
    public GetDeviceResponse getDevice(UUID uuid) {
        return service.find(uuid)
                .map(factory.deviceToResponse())
                .orElseThrow(NotFoundException::new);
    }

    @Override
    public void putDevice(UUID id, PutDeviceRequest request) {
        try {
            service.create(factory.requestToDevice().apply(id, request));
        } catch (IllegalArgumentException ex) {
            throw new BadRequestException(ex);
        }
    }

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

    @Override
    public byte[] getDeviceImage(UUID id) {
        return service.find(id)
                .map(Device::getImage)
                .orElseThrow(NotFoundException::new);
    }

    @Override
    public void putDeviceImage(UUID id, InputStream image) {
        service.updatePortrait(id, image);
    }

}
