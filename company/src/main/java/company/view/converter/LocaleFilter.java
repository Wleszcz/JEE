package company.view.converter;

import jakarta.faces.context.FacesContext;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;

import java.io.IOException;
import java.util.Locale;

public class LocaleFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        Locale preferredLocale = httpRequest.getLocale();

        FacesContext facesContext = FacesContext.getCurrentInstance();
        if (facesContext != null) {
            facesContext.getViewRoot().setLocale(preferredLocale); // Ustawienie lokalizacji w kontekście JSF
        }

        chain.doFilter(request, response);
    }

}
