package company.user.controller.api;

import company.user.dto.GetUserResponse;
import company.user.dto.GetUsersResponse;
import company.user.dto.PatchUserRequest;
import company.user.dto.PutUserRequest;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;

import java.io.InputStream;
import java.util.UUID;

/**
 * Controller for managing collections Users' representations.
 */
@Path("")
public interface UserController {

    /**
     * @return all Users representation
     */
    @GET
    @Path("/users")
    @Produces(MediaType.APPLICATION_JSON)
    GetUsersResponse getUsers();

    /**
     * @return single User
     */
    @GET
    @Path("/users/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    GetUserResponse getUser(@PathParam("id") UUID id);

    /**
     * @param id      user's id
     * @param request new user representation
     */
    @PUT
    @Path("/users/{id}")
    @Consumes({MediaType.APPLICATION_JSON})
    void putUser(@PathParam("id")UUID id, PutUserRequest request);

    /**
     * @param id      user's id
     * @param request user update representation
     */
    @PATCH
    @Path("/users/{id}")
    @Consumes({MediaType.APPLICATION_JSON})
    void patchUser(@PathParam("id") UUID id, PatchUserRequest request);


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
