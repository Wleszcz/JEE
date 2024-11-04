package company.component;

import jakarta.enterprise.context.ApplicationScoped;
import company.device.entity.Device;
import company.device.entity.Brand;
import company.device.model.DeviceEditModel;
import company.device.model.DeviceModel;
import company.device.model.DevicesModel;
import company.device.model.BrandModel;
import company.device.model.function.*;

import java.util.function.Function;

/**
 * Factor for creating {@link Function} implementation for converting between various objects used in different layers.
 * Instead of injecting multiple function objects single factory is injected.
 */
@ApplicationScoped
public class ModelFunctionFactory {

    /**
     * Returns a function to convert a single {@link Device} to {@link DeviceModel}.
     *
     * @return new instance
     */
    public DeviceToModelFunction deviceToModel() {
        return new DeviceToModelFunction();
    }

    /**
     * Returns a function to convert a list of {@link Device} to {@link DevicesModel}.
     *
     * @return new instance
     */
    public DevicesToModelFunction devicesToModel() {
        return new DevicesToModelFunction();
    }

    /**
     * Returns a function to convert a single {@link Device} to {@link DeviceEditModel}.
     *
     * @return new instance
     */
    public DeviceToEditModelFunction deviceToEditModel() {
        return new DeviceToEditModelFunction(userToModel());
    }

    /**
     * Returns a function to convert a single {@link DeviceModel} to {@link Device}.
     *
     * @return new instance
     */
    public ModelToDeviceFunction modelToDevice() {
        return new ModelToDeviceFunction();
    }

    /**
     * Returns a function to convert a single {@link Brand} to {@link BrandModel}.
     *
     * @return new instance
     */
    public BrandToModelFunction brandToModel() {
        return new BrandToModelFunction();
    }

    public UserToModelFunction userToModel() {
        return new UserToModelFunction();
    }

    public UsersToModelFunction usersToModel() {
        return new UsersToModelFunction();
    }

    public BrandsToModelFunction brandsToModel() {
        return new BrandsToModelFunction();
    }


    /**
     * Returns a function to update a {@link Device}.
     *
     * @return UpdateDeviceFunction instance
     */
    public UpdateDeviceWithModelFunction updateDevice() {
        return new UpdateDeviceWithModelFunction();
    }


}
