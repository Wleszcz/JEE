package company.component;

import company.device.dto.*;
import company.device.dto.function.*;
import company.device.entity.Brand;
import company.device.entity.Device;
import company.user.dto.GetUserResponse;
import company.user.dto.GetUsersResponse;
import company.user.dto.PutUserRequest;
import company.user.dto.function.RequestToUserFunction;
import company.user.dto.function.UpdateUserPasswordWithRequestFunction;
import company.user.dto.function.UpdateUserWithRequestFunction;
import company.user.dto.function.UserToResponseFunction;
import company.user.dto.function.UsersToResponseFunction;
import company.user.entity.User;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.function.Function;

/**
 * Factor for creating {@link Function} implementation for converting between various objects used in different layers.
 * Instead of injecting multiple function objects single factory is injected.
 */
@ApplicationScoped
public class DtoFunctionFactory {

    /**
     * Returns a function to convert a single {@link Device} to {@link GetDeviceResponse}.
     *
     * @return DeviceToResponseFunction instance
     */
    public DeviceToResponseFunction deviceToResponse() {
        return new DeviceToResponseFunction();
    }

    /**
     * Returns a function to convert a list of {@link Device} to {@link GetDevicesResponse}.
     *
     * @return DevicesToResponseFunction instance
     */
    public DevicesToResponseFunction devicesToResponse() {
        return new DevicesToResponseFunction();
    }

    /**
     * Returns a function to convert a single {@link Brand} to {@link GetBrandResponse}.
     *
     * @return BrandToResponseFunction instance
     */
    public BrandToResponseFunction BrandToResponse() {
        return new BrandToResponseFunction();
    }

    /**
     * Returns a function to convert a list of {@link Brand} to {@link GetBrandsResponse}.
     *
     * @return BrandsToResponseFunction instance
     */
    public BrandsToResponseFunction BrandsToResponse() {
        return new BrandsToResponseFunction();
    }

    /**
     * Returns a function to convert a {@link PutDeviceRequest} to a {@link Device}.
     *
     * @return RequestToDeviceFunction instance
     */
    public RequestToDeviceFunction requestToDevice() {
        return new RequestToDeviceFunction();
    }

    /**
     * Returns a function to update a {@link Device}.
     *
     * @return UpdateDeviceFunction instance
     */
    public UpdateDeviceWithRequestFunction updateDevice() {
        return new UpdateDeviceWithRequestFunction();
    }

    /**
     * Returns a function to convert a {@link PutUserRequest} to a {@link User}.
     *
     * @return RequestToUserFunction instance
     */
    public RequestToUserFunction requestToUser() {
        return new RequestToUserFunction();
    }

    /**
     * Returns a function to update a {@link User}.
     *
     * @return UpdateUserFunction instance
     */
    public UpdateUserWithRequestFunction updateUser() {
        return new UpdateUserWithRequestFunction();
    }

    /**
     * Returns a function to update a {@link User}'s password.
     *
     * @return UpdateUserPasswordFunction instance
     */
    public UpdateUserPasswordWithRequestFunction updateUserPassword() {
        return new UpdateUserPasswordWithRequestFunction();
    }

    /**
     * Returns a function to convert a list of {@link User} to {@link GetUsersResponse}.
     *
     * @return UsersToResponseFunction instance
     */
    public UsersToResponseFunction usersToResponse() {
        return new UsersToResponseFunction();
    }

    /**
     * Returns a function to convert a single {@link User} to {@link GetUserResponse}.
     *
     * @return UserToResponseFunction instance
     */
    public UserToResponseFunction userToResponse() {
        return new UserToResponseFunction();
    }

}
