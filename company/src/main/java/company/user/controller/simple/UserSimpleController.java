package company.user.controller.simple;

import company.component.DtoFunctionFactory;
import company.controller.servlet.exception.BadRequestException;
import company.controller.servlet.exception.NotFoundException;
import company.device.controller.api.BrandController;
import company.device.dto.GetBrandsResponse;
import company.device.dto.PatchDeviceRequest;
import company.device.dto.PutDeviceRequest;
import company.device.entity.Device;
import company.device.service.BrandService;
import company.user.controller.api.UserController;
import company.user.dto.GetUserResponse;
import company.user.dto.GetUsersResponse;
import company.user.dto.PatchUserRequest;
import company.user.dto.PutUserRequest;
import company.user.entity.User;
import company.user.service.UserService;

import java.io.InputStream;
import java.util.UUID;

/**
 * Simple framework agnostic implementation of controller.
 */
public class UserSimpleController implements UserController {

    /**
     * User service.
     */
    private final UserService service;

    /**
     * Factory producing functions for conversion between DTO and entities.
     */
    private final DtoFunctionFactory factory;


    /**
     * @param service User service
     * @param factory factory producing functions for conversion between DTO and entities
     */
    public UserSimpleController(UserService service, DtoFunctionFactory factory) {
        this.service = service;
        this.factory = factory;
    }

    @Override
    public GetUsersResponse getUsers() {
        return factory.usersToResponse().apply(service.findAll());
    }

    @Override
    public GetUserResponse getUser(UUID id) {
        return service.find(id)
                .map(factory.userToResponse())
                .orElseThrow(NotFoundException::new);
    }

    @Override
    public void putUser(UUID id, PutUserRequest request) {
        try {
            service.create(factory.requestToUser().apply(id, request));
        } catch (IllegalArgumentException ex) {
            throw new BadRequestException(ex);
        }
    }

    @Override
    public void deleteUser(UUID id) {
        service.find(id).ifPresentOrElse(
                entity -> service.delete(id),
                () -> {
                    throw new NotFoundException();
                }
        );
    }

    @Override
    public byte[] getUserImage(UUID id) {
        return service.getImage(id)
                .orElseThrow(NotFoundException::new);
    }

    @Override
    public void putUserImage(UUID id, InputStream image) {
        service.find(id).ifPresentOrElse(
                entity -> service.updateImage(id, image),
                () -> {
                    throw new NotFoundException();
                }
        );
    }

    @Override
    public void deleteUserImage(UUID id) {
        service.deleteUserImage(id);
    }

    @Override
    public void patchUser(UUID id, PatchUserRequest request) {
        service.find(id).ifPresentOrElse(
                entity -> service.update(factory.updateUser().apply(entity, request)),
                () -> {
                    throw new NotFoundException();
                }
        );
    }
}
