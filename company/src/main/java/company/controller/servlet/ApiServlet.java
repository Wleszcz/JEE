package company.controller.servlet;

import company.device.controller.api.DeviceController;
import company.device.dto.PatchDeviceRequest;
import company.device.dto.PutDeviceRequest;
import company.user.controller.api.UserController;
import company.user.dto.PatchUserRequest;
import company.user.dto.PutUserRequest;
import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import company.device.controller.api.BrandController;

import java.io.IOException;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Central API servlet for fetching all request from the client and preparing responses. Servlet API does not allow
 * named path parameters so wildcard is used.
 */
@WebServlet(urlPatterns = {
        ApiServlet.Paths.API + "/*"
})
@MultipartConfig(maxFileSize = 200 * 1024)
public class ApiServlet extends HttpServlet {

    /**
     * Controller for managing collections devices' representations.
     */
    private DeviceController deviceController;

    /**
     * Controller for managing collections Brands' representations.
     */
    private BrandController brandController;

    /**
     * Controller for managing Users.
     */
    private UserController userController;

    /**
     * Definition of paths supported by this servlet. Separate inner class provides composition for static fields.
     */
    public static final class Paths {

        /**
         * All API operations. Version v1 will be used to distinguish from other implementations.
         */
        public static final String API = "/api";

    }

    /**
     * Patterns used for checking servlet path.
     */
    public static final class Patterns {

        /**
         * UUID
         */
        private static final Pattern UUID = Pattern.compile("[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}");

        /**
         * All devices.
         */
        public static final Pattern DEVICES = Pattern.compile("/devices/?");

        /**
         * Single device.
         */
        public static final Pattern DEVICE = Pattern.compile("/devices/(%s)".formatted(UUID.pattern()));

        /**
         * Single device's image.
         */
        public static final Pattern DEVICE_IMAGE = Pattern.compile("/devices/(%s)/image".formatted(UUID.pattern()));

        /**
         * All Brands.
         */
        public static final Pattern BRANDS = Pattern.compile("/brands/?");

        /**
         * All devices of single Brand.
         */
        public static final Pattern BRAND_DEVICES = Pattern.compile("/brands/(%s)/devices/?".formatted(UUID.pattern()));

        /**
         * All devices of single user.
         */
        public static final Pattern USER_DEVICES = Pattern.compile("/users/(%s)/devices/?".formatted(UUID.pattern()));


        /**
         * Single user.
         */
        public static final Pattern USER_IMAGE = Pattern.compile("/users/(%s)/image".formatted(UUID.pattern()));

        /**
         * All users.
         */
        public static final Pattern USERS = Pattern.compile("/users/?");

        /**
         * Single user.
         */
        public static final Pattern USER = Pattern.compile("/users/(%s)".formatted(UUID.pattern()));

    }

    /**
     * JSON-B mapping object. According to open liberty documentation creating this is expensive. The JSON-B is only one
     * of many solutions. JSON strings can be built by hand {@link StringBuilder} or with JSON-P API. Both JSON-B and
     * JSON-P are part of Jakarta EE whereas JSON-B is newer standard.
     */
    private final Jsonb jsonb = JsonbBuilder.create();

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (request.getMethod().equals("PATCH")) {
            doPatch(request, response);
        } else {
            super.service(request, response);
        }
    }

    @Override
    public void init() throws ServletException {
        super.init();
        deviceController = (DeviceController) getServletContext().getAttribute("deviceController");
        brandController = (BrandController) getServletContext().getAttribute("brandController");
        userController = (UserController) getServletContext().getAttribute("userController");
    }

    @SuppressWarnings("RedundantThrows")
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String path = parseRequestPath(request);
        String servletPath = request.getServletPath();
        if (Paths.API.equals(servletPath)) {
            if (path.matches(Patterns.DEVICES.pattern())) {
                response.setContentType("application/json");
                response.getWriter().write(jsonb.toJson(deviceController.getDevices()));
                return;
            } else if (path.matches(Patterns.DEVICE.pattern())) {
                response.setContentType("application/json");
                UUID uuid = extractUuid(Patterns.DEVICE, path);
                response.getWriter().write(jsonb.toJson(deviceController.getDevice(uuid)));
                return;
            } else if (path.matches(Patterns.BRANDS.pattern())) {
                response.setContentType("application/json");
                response.getWriter().write(jsonb.toJson(brandController.getBrands()));
                return;
            } else if (path.matches(Patterns.BRAND_DEVICES.pattern())) {
                response.setContentType("application/json");
                UUID uuid = extractUuid(Patterns.BRAND_DEVICES, path);
                response.getWriter().write(jsonb.toJson(deviceController.getBrandDevices(uuid)));
                return;
            } else if (path.matches(Patterns.USER_DEVICES.pattern())) {
                response.setContentType("application/json");
                UUID uuid = extractUuid(Patterns.USER_DEVICES, path);
                response.getWriter().write(jsonb.toJson(deviceController.getUserDevices(uuid)));
                return;
            } else if (path.matches(Patterns.DEVICE_IMAGE.pattern())) {
                response.setContentType("image/png");//could be dynamic but atm we support only one format
                UUID uuid = extractUuid(Patterns.DEVICE_IMAGE, path);
                byte[] image = deviceController.getDeviceImage(uuid);
                response.setContentLength(image.length);
                response.getOutputStream().write(image);
                return;
            } else if (path.matches(Patterns.USER.pattern())) {
                response.setContentType("application/json");
                UUID uuid = extractUuid(Patterns.USER, path);
                response.getWriter().write(jsonb.toJson(userController.getUser(uuid)));
                return;
            } else if (path.matches(Patterns.USERS.pattern())) {
                response.setContentType("application/json");
                response.getWriter().write(jsonb.toJson(userController.getUsers()));
                return;
            } else if (path.matches(Patterns.USER_IMAGE.pattern())) {
                response.setContentType("image/png");//could be dynamic but atm we support only one format
                UUID uuid = extractUuid(Patterns.USER_IMAGE, path);
                byte[] image = userController.getUserImage(uuid);
                response.setContentLength(image.length);
                response.getOutputStream().write(image);
                return;
            }
        }
        response.sendError(HttpServletResponse.SC_BAD_REQUEST);
    }

    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String path = parseRequestPath(request);
        String servletPath = request.getServletPath();
        if (Paths.API.equals(servletPath)) {
            if (path.matches(Patterns.DEVICE.pattern())) {
                UUID uuid = extractUuid(Patterns.DEVICE, path);
                deviceController.putDevice(uuid, jsonb.fromJson(request.getReader(), PutDeviceRequest.class));
                response.addHeader("Location", createUrl(request, Paths.API, "devices", uuid.toString()));
                return;
            } else if (path.matches(Patterns.DEVICE_IMAGE.pattern())) {
                UUID uuid = extractUuid(Patterns.DEVICE_IMAGE, path);
                deviceController.putDeviceImage(uuid, request.getPart("image").getInputStream());
                return;
            } else if (path.matches(Patterns.USER.pattern())) {
                UUID uuid = extractUuid(Patterns.USER, path);
                userController.putUser(uuid, jsonb.fromJson(request.getReader(), PutUserRequest.class));
                response.addHeader("Location", createUrl(request, Paths.API, "users", uuid.toString()));
                return;
            } else if (path.matches(Patterns.USER_IMAGE.pattern())) {
                UUID uuid = extractUuid(Patterns.USER_IMAGE, path);
                userController.putUserImage(uuid, request.getPart("image").getInputStream());
                return;
            }
        }
        response.sendError(HttpServletResponse.SC_BAD_REQUEST);
    }

    @SuppressWarnings("RedundantThrows")
    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String path = parseRequestPath(request);
        String servletPath = request.getServletPath();
        if (Paths.API.equals(servletPath)) {
            if (path.matches(Patterns.DEVICE.pattern())) {
                UUID uuid = extractUuid(Patterns.DEVICE, path);
                deviceController.deleteDevice(uuid);
                return;
            } else if (path.matches(Patterns.USER_IMAGE.pattern())) {
                UUID uuid = extractUuid(Patterns.USER_IMAGE, path);
                userController.deleteUserImage(uuid);
                return;
            } else if (path.matches(Patterns.USER.pattern())) {
                UUID uuid = extractUuid(Patterns.USER, path);
                userController.deleteUser(uuid);
                return;
            }
        }
        response.sendError(HttpServletResponse.SC_BAD_REQUEST);
    }

    /**
     * Called by the server (via the <code>service</code> method) to allow a servlet to handle a PATCH request.
     *
     * @param request  {@link HttpServletRequest} object that contains the request the client made of the servlet
     * @param response {@link HttpServletResponse} object that contains the response the servlet returns to the client
     * @throws ServletException if the request for the PATCH cannot be handled
     * @throws IOException      if an input or output error occurs while the servlet is handling the PATCH request
     */
    @SuppressWarnings("RedundantThrows")
    protected void doPatch(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String path = parseRequestPath(request);
        String servletPath = request.getServletPath();
        if (Paths.API.equals(servletPath)) {
            if (path.matches(Patterns.DEVICE.pattern())) {
                UUID uuid = extractUuid(Patterns.DEVICE, path);
                deviceController.patchDevice(uuid, jsonb.fromJson(request.getReader(), PatchDeviceRequest.class));
                return;
            }
            if (path.matches(Patterns.USER.pattern())) {
                UUID uuid = extractUuid(Patterns.USER, path);
                userController.patchUser(uuid, jsonb.fromJson(request.getReader(), PatchUserRequest.class));
                return;
            }
        }
        response.sendError(HttpServletResponse.SC_BAD_REQUEST);
    }

    /**
     * Extracts UUID from path using provided pattern. Pattern needs to contain UUID in first regular expression group.
     *
     * @param pattern regular expression pattern with
     * @param path    request path containing UUID
     * @return extracted UUID
     */
    private static UUID extractUuid(Pattern pattern, String path) {
        Matcher matcher = pattern.matcher(path);
        if (matcher.matches()) {
            return UUID.fromString(matcher.group(1));
        }
        throw new IllegalArgumentException("No UUID in path.");
    }

    /**
     * Gets path info from the request and returns it. No null is returned, instead empty string is used.
     *
     * @param request original servlet request
     * @return path info (not null)
     */
    private String parseRequestPath(HttpServletRequest request) {
        String path = request.getPathInfo();
        path = path != null ? path : "";
        return path;
    }

    /**
     * Creates URL using host, port and context root from servlet request and any number of path elements. If any of
     * path elements starts or ends with '/' device, that device is removed.
     *
     * @param request servlet request
     * @param paths   any (can be none) number of path elements
     * @return created url
     */
    public static String createUrl(HttpServletRequest request, String... paths) {
        StringBuilder builder = new StringBuilder();
        builder.append(request.getScheme())
                .append("://")
                .append(request.getServerName())
                .append(":")
                .append(request.getServerPort())
                .append(request.getContextPath());
        for (String path : paths) {
            builder.append("/")
                    .append(path, path.startsWith("/") ? 1 : 0, path.endsWith("/") ? path.length() - 1 : path.length());
        }
        return builder.toString();
    }

}
