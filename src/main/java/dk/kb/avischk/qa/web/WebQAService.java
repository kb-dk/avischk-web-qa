package dk.kb.avischk.qa.web;

import javax.ws.rs.Path;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Path("/")
public class WebQAService {
    private final Logger log = LoggerFactory.getLogger(getClass());
    
    
    
    public WebQAService() {
        log.info("Initializing service");
    }
    
}