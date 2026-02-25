package com.gestionescale.filter;

import java.io.IOException;
import java.util.List;
import com.gestionescale.model.Escale;
import com.gestionescale.service.implementation.EscaleService;
import jakarta.servlet.*;

public class NotificationCountFilter implements Filter {
    private EscaleService escaleService = new EscaleService();

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        int notifCount = 0;
        List<Escale> arr = null;
        List<Escale> part = null;
        List<Escale> nonfact = null;
        try {
            arr = escaleService.getEscalesArrivantCetteSemaine();
            part = escaleService.getEscalesPartiesCetteSemaine();
            nonfact = escaleService.getEscalesTermineesSansFacture();
        } catch (Exception e) {
            // Log l’erreur mais ne bloque pas l’affichage de la page
            e.printStackTrace();
        }

        if (arr != null) notifCount += arr.size();
        if (part != null) notifCount += part.size();
        if (nonfact != null) notifCount += nonfact.size();
        request.setAttribute("notifCount", notifCount);
        chain.doFilter(request, response);
    }
}