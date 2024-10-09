package company.device.controller.api;

import company.device.dto.GetDeviceResponse;
import company.device.dto.GetDevicesResponse;
import company.device.dto.PatchDeviceRequest;
import company.device.dto.PutDeviceRequest;

import java.io.InputStream;
import java.util.UUID;

/**
 * Controller for managing collections devices' representations.
 */
public interface DeviceController {

    /**
     * @return all devices representation
     */
    GetDevicesResponse getDevices();

    /**
     * @param id Brand's id
     * @return devices representation
     */
    GetDevicesResponse getBrandDevices(UUID id);

    /**
     * @param id user's id
     * @return devices representation
     */
    GetDevicesResponse getUserDevices(UUID id);

    /**
     * @param uuid device's id
     * @return device representation
     */
    GetDeviceResponse getDevice(UUID uuid);

    /**
     * @param id      device's id
     * @param request new device representation
     */
    void putDevice(UUID id, PutDeviceRequest request);

    /**
     * @param id      device's id
     * @param request device update representation
     */
    void patchDevice(UUID id, PatchDeviceRequest request);

    /**
     * @param id device's id
     */
    void deleteDevice(UUID id);

    /**
     * @param id device's id
     * @return device's image
     */
    byte[] getDeviceImage(UUID id);

    /**
     * @param id       device's id
     * @param image device's new avatar
     */
    void putDeviceImage(UUID id, InputStream image);

}
