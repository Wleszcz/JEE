package company.configuration.observer;

import company.device.entity.Brand;
import company.device.entity.Device;
import company.device.entity.DeviceType;
import company.device.service.BrandService;
import company.device.service.DeviceService;
import company.user.entity.User;
import company.user.entity.UserRoles;
import company.user.service.UserService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.context.Initialized;
import jakarta.enterprise.context.control.RequestContextController;
import jakarta.enterprise.event.Observes;
import jakarta.inject.Inject;
import lombok.SneakyThrows;


import java.io.InputStream;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * Listener started automatically on CDI application context initialized. Injects proxy to the services and fills
 * database with default content. When using persistence storage application instance should be initialized only during
 * first run in order to init database with starting data. Good place to create first default admin user.
 */
@ApplicationScoped
public class InitializedData {

    /**
     * Character service.
     */
    private final DeviceService deviceService;

    /**
     * User service.
     */
    private final UserService userService;

    /**
     * Profession service.
     */
    private final BrandService brandService;

    /**
     * The CDI container provides a built-in instance of {@link RequestContextController} that is dependent scoped for
     * the purposes of activating and deactivating.
     */
    private final RequestContextController requestContextController;

    /**
     * @param characterService         character service
     * @param userService              user service
     * @param professionService        profession service
     * @param requestContextController CDI request context controller
     */
    @Inject
    public InitializedData(
            DeviceService deviceService,
            UserService userService,
            BrandService brandService,
            RequestContextController requestContextController
    ) {
        this.deviceService = deviceService;
        this.userService = userService;
        this.brandService = brandService;
        this.requestContextController = requestContextController;
    }

    public void contextInitialized(@Observes @Initialized(ApplicationScoped.class) Object init) {
        init();
    }

    /**
     * Initializes database with some example values. Should be called after creating this object. This object should be
     * created only once.
     */
    @SneakyThrows
    private void init() {
        requestContextController.activate();// start request scope in order to inject request scoped repositories

        User admin = User.builder()
                .id(UUID.fromString("c4804e0f-769e-4ab9-9ebe-0578fb4f00a6"))
                .login("admin")
                .name("System")
                .surname("Admin")
                .birthDate(LocalDate.of(1990, 10, 21))
                .email("admin@simplerpg.example.com")
                .password("adminadmin")
                .roles(List.of(UserRoles.ADMIN, UserRoles.USER))
                .build();

        User kevin = User.builder()
                .id(UUID.fromString("81e1c2a9-7f57-439b-b53d-6db88b071e4e"))
                .login("kevin")
                .name("Kevin")
                .surname("Pear")
                .birthDate(LocalDate.of(2001, 1, 16))
                .email("kevin@example.com")
                .password("useruser")
                .roles(List.of(UserRoles.USER))
                .build();

        User alice = User.builder()
                .id(UUID.fromString("ed6cfb2a-cad7-47dd-9b56-9d1e3c7a4197"))
                .login("alice")
                .name("Alice")
                .surname("Grape")
                .birthDate(LocalDate.of(2002, 3, 19))
                .email("alice@example.com")
                .password("useruser")
                .roles(List.of(UserRoles.USER))
                .build();

        userService.create(admin);
        userService.create(kevin);
        userService.create(alice);
        Brand sasung = Brand.builder()
                .id(UUID.fromString("f5875513-bf7b-4ae1-b8a5-5b70a1b90e76"))
                .name("Sasung")
                .dateOfEstablishment(new Date())
                .build();

        Brand song = Brand.builder()
                .id(UUID.fromString("5d1da2ae-6a14-4b6d-8b4f-d117867118d4"))
                .name("Song")
                .dateOfEstablishment(new Date())
                .build();

        Brand mitsubushi = Brand.builder()
                .id(UUID.fromString("2d9b1e8c-67c5-4188-a911-5f064a63d8cd"))
                .name("Mitsubushi")
                .dateOfEstablishment(new Date())
                .build();

        Brand lp = Brand.builder()
                .id(UUID.randomUUID())
                .name("LP")
                .dateOfEstablishment(new Date())
                .build();

        brandService.create(sasung);
        brandService.create(song);
        brandService.create(mitsubushi);
        brandService.create(lp);

        Device LODUFKA = Device.builder()
                .id(UUID.fromString("525d3e7b-bb1f-4c13-bf17-926d1a12e4c0"))
                .name("LODUFKA 2000")
                .price(3000)
                .mass(80000)
                .deviceType(DeviceType.FRIDGE)
                .brand(sasung)
                .image(getResourceAsByteArray("../avatar/lodufka.png"))//package relative path
                .user(kevin)
                .build();

        Device oiehfoihsaef = Device.builder()
                .id(UUID.fromString("cc0b0577-bb6f-45b7-81d6-3db88e6ac19f"))
                .name("oiehfoihsaef")
                .price(1000)
                .mass(300)
                .deviceType(DeviceType.PHONE)
                .brand(mitsubushi)
                .image(getResourceAsByteArray("../avatar/ph.png"))//package relative path
                .user(kevin)
                .build();

        Device flat10000 = Device.builder()
                .id(UUID.fromString("f08ef7e3-7f2a-4378-b1fb-2922d730c70d"))
                .name("flat10000")
                .price(5000)
                .mass(10000)
                .brand(song)
                .deviceType(DeviceType.TV)
                .image(getResourceAsByteArray("../avatar/tv.png"))//package relative path
                .user(alice)
                .build();

        Device tablet = Device.builder()
                .id(UUID.fromString("ff327e8a-77c0-4f9b-90a2-89e16895d1e1"))
                .name("tablet")
                .price(500)
                .mass(500)
                .deviceType(DeviceType.TABLET)
                .brand(lp)
                .image(getResourceAsByteArray("../avatar/tab.png"))//package relative path
                .user(alice)
                .build();

        deviceService.create(LODUFKA);
        deviceService.create(oiehfoihsaef);
        deviceService.create(flat10000);
        deviceService.create(tablet);
        requestContextController.deactivate();
    }

    /**
     * @param name name of the desired resource
     * @return array of bytes read from the resource
     */
    @SneakyThrows
    private byte[] getResourceAsByteArray(String name) {
        try (InputStream is = this.getClass().getResourceAsStream(name)) {
            if (is != null) {
                return is.readAllBytes();
            } else {
                throw new IllegalStateException("Unable to get resource %s".formatted(name));
            }
        }
    }

}
