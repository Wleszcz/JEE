package company.configuration.listener;

import company.device.controller.simple.DeviceSimpleController;
import company.device.service.DeviceService;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import company.device.controller.simple.BrandSimpleController;
import company.device.service.BrandService;
import company.component.DtoFunctionFactory;

/**
 * Listener started automatically on servlet context initialized. Creates an instance of controllers and puts them in
 * the application (servlet) context.
 */
@WebListener//using annotation does not allow configuring order
public class CreateControllers implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent event) {
        DeviceService deviceService = (DeviceService) event.getServletContext().getAttribute("deviceService");
        BrandService brandService = (BrandService) event.getServletContext().getAttribute("brandService");

        event.getServletContext().setAttribute("deviceController", new DeviceSimpleController(
                deviceService,
                new DtoFunctionFactory()
        ));

        event.getServletContext().setAttribute("brandController", new BrandSimpleController(
                brandService,
                new DtoFunctionFactory()
        ));
    }
}
