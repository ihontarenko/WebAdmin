package io.startform.utils;

import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

@SuppressWarnings({"unused"})
@Component
public record RequestHelper(HttpServletRequest request) {

    private static final String[] IP_HEADER_CANDIDATES = {
            "X-Forwarded-For",
            "Proxy-Client-IP",
            "WL-Proxy-Client-IP",
            "HTTP_X_FORWARDED_FOR",
            "HTTP_X_FORWARDED",
            "HTTP_X_CLUSTER_CLIENT_IP",
            "HTTP_CLIENT_IP",
            "HTTP_FORWARDED_FOR",
            "HTTP_FORWARDED",
            "HTTP_VIA",
            "REMOTE_ADDR"
    };

    public String getClientIPAddress() {
        String ipAddress = "0.0.0.0";

        for (String header : IP_HEADER_CANDIDATES) {
            String ipList = request.getHeader(header);
            if (ipList != null && ipList.length() != 0 && !"unknown".equalsIgnoreCase(ipList)) {
                ipAddress = ipList.split(",")[0];
                break;
            }
        }

        return ipAddress;
    }

}
