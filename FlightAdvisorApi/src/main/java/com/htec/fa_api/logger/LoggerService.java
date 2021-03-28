package com.htec.fa_api.logger;

import com.htec.fa_api.security.AuthenticationFacade;
import com.htec.fa_api.service.UserService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LoggerService<T> {

    @Value("${application.logger.enabled}")
    private static boolean loggerEnabled;

    private final LoggerRepository loggerRepository;
    private final UserService userService;
    private final MessageSource messageSource;
    private final AuthenticationFacade authenticationFacade;

    public LoggerService(LoggerRepository loggerRepository, UserService userService, MessageSource messageSource, AuthenticationFacade authenticationFacade) {
        this.loggerRepository = loggerRepository;
        this.userService = userService;
        this.messageSource = messageSource;
        this.authenticationFacade = authenticationFacade;
    }

    public Logger logAction(T object, String actionType, String message) {
        if(loggerEnabled){
            Logger logger = new Logger();
            String username = SecurityContextHolder.getContext().getAuthentication().getName();
            logger.setUser(userService.getByUsername(username));
            logger.setTableName(object.getClass().getSimpleName());

            logger.setActionType(actionType);
            logger.setActionDetails(messageSource.getMessage(message, null, null).concat(object.toString()));
            return loggerRepository.save(logger);
        }
        return null;
    }

    public List<Logger> getAll() {
        return loggerRepository.getAll();
    }

}
