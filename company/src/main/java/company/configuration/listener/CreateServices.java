package company.configuration.listener;

import company.device.repository.api.DeviceRepository;
import company.device.service.DeviceService;
import company.user.repository.api.FileRepository;
import company.user.repository.memory.SimpleFileRepository;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import company.device.repository.api.BrandRepository;
import company.device.repository.memory.DeviceInMemoryRepository;
import company.device.repository.memory.BrandInMemoryRepository;
import company.device.service.BrandService;
import company.crypto.component.Pbkdf2PasswordHash;
import company.datastore.component.DataStore;
import company.user.repository.api.UserRepository;
import company.user.repository.memory.UserInMemoryRepository;
import company.user.service.UserService;

import java.nio.file.Path;

/**
 * Listener started automatically on servlet context initialized. Creates an instance of services (business layer) and
 * puts them in the application (servlet) context.
 */
@WebListener//using annotation does not allow configuring order
public class CreateServices implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent event) {
        DataStore dataSource = (DataStore) event.getServletContext().getAttribute("datasource");

        UserRepository userRepository = new UserInMemoryRepository(dataSource);
        BrandRepository brandRepository = new BrandInMemoryRepository(dataSource);
        DeviceRepository deviceRepository = new DeviceInMemoryRepository(dataSource);

        ServletContext servletContext = event.getServletContext();
        Path path = Path.of(servletContext.getInitParameter("imagePath"));

        event.getServletContext().setAttribute("userService", new UserService(userRepository, new SimpleFileRepository(String.valueOf(path)) ,new Pbkdf2PasswordHash()));
        event.getServletContext().setAttribute("deviceService", new DeviceService(deviceRepository, brandRepository, userRepository));
        event.getServletContext().setAttribute("brandService", new BrandService(brandRepository));
    }

}
