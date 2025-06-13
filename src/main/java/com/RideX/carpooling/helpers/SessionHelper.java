package com.RideX.carpooling.helpers;

import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Component
public class SessionHelper {
    private static Logger logger = LoggerFactory.getLogger(SessionHelper.class);
    public static void removeMessage() {
        logger.info("SessionHelper :: removeMessage[]");
        try {
            System.out.println("removing message from session");
            ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            if (requestAttributes != null) {
                HttpSession session = requestAttributes.getRequest().getSession();
                session.removeAttribute("message");
            }
        } catch (Exception e) {
            System.out.println("Error in SessionHelper: " + e);
            e.printStackTrace();
        }
    }
}
