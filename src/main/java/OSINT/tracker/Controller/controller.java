package OSINT.tracker.Controller;

import io.ipinfo.api.IPinfo;
import io.ipinfo.api.errors.RateLimitedException;
import io.ipinfo.api.model.IPResponse;
import jakarta.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class controller {

    @GetMapping("/loveyou")
    public String gotYa(HttpServletRequest request, Model model) throws RateLimitedException {
        String ip = request.getHeader("X-Forwarded-For");

        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }

        IPinfo ipInfo = new IPinfo.Builder()
                .setToken("76146e1de81f15")
                .build();

        try {
            IPResponse ipResponse = ipInfo.lookupIP(ip);

            model.addAttribute("ip", ipResponse.getIp());
            model.addAttribute("HostName", ipResponse.getHostname());
            model.addAttribute("Country", ipResponse.getCountryName());
            model.addAttribute("City", ipResponse.getCity());
            model.addAttribute("Latitude", ipResponse.getLatitude());
            model.addAttribute("Longitude", ipResponse.getLongitude());

            System.out.println("IP: " + ipResponse.getIp());
            System.out.println("Hostname: " + ipResponse.getHostname());
            System.out.println("Country: " + ipResponse.getCountryName());
            System.out.println("City: " + ipResponse.getCity());
            System.out.println("Latitude: " + ipResponse.getLatitude());
            System.out.println("Longitude: " + ipResponse.getLongitude());

            return "loveyou";
        } catch (RateLimitedException ex) {
            // Handle rate limits here.
            System.out.println("API rate limit reached.");
        }
        return "loveyou";
    }
}
