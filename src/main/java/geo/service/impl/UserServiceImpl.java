package geo.service.impl;

import geo.service.UserService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

/**
 * Created by ehm on 15.04.2017.
 */
@Service
public class UserServiceImpl implements UserService {

    @Override
    public String getCurrentUsername() {
        return SecurityContextHolder.getContext().getAuthentication() != null
                ? (String)SecurityContextHolder.getContext().getAuthentication().getPrincipal()
                : null;
    }
}
