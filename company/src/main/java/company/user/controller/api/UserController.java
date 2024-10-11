package company.user.controller.api;

import company.device.dto.PatchDeviceRequest;
import company.device.dto.PutDeviceRequest;
import company.user.dto.GetUserResponse;
import company.user.dto.GetUsersResponse;
import company.user.dto.PatchUserRequest;
import company.user.dto.PutUserRequest;

import java.io.InputStream;
import java.util.UUID;

/**
 * Controller for managing collections Users' representations.
 */
public interface UserController {

    /**
     * @return all Users representation
     */
    GetUsersResponse getUsers();

    /**
     * @return single User
     */
    GetUserResponse getUser(UUID id);

    /**
     * @param id      user's id
     * @param request new user representation
     */
    void putUser(UUID id, PutUserRequest request);

    /**
     * @param id      user's id
     * @param request user update representation
     */
    void patchUser(UUID id, PatchUserRequest request);


    /**
     * @param id user's id
     * removes User
     */
    public void deleteUser(UUID id);

    /**
     * @param id user's id
     * @return user's image
     */
    byte[] getUserImage(UUID id);

    /**
     * @param id       device's id
     * @param image user's new avatar
     */
    void putUserImage(UUID id, InputStream image);

    /**
     * @param id user's id
     * removes User's image
     */
    public void deleteUserImage(UUID id);
}
