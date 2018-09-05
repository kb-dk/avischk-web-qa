package dk.kb.avischk.qa.web;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import dk.kb.avischk.dao.DAOFailureException;
import dk.kb.avischk.dao.NewspaperQADao;
import dk.kb.avischk.dao.NewspaperQADaoFactory;
import dk.kb.avischk.dto.CharacterizationInfo;
import dk.kb.avischk.dto.NewspaperEntity;

@Path("/")
public class WebQAService {
    private final Logger log = LoggerFactory.getLogger(getClass());
    
    private NewspaperQADao dao;
    
    private DateFormat sdf = new SimpleDateFormat("YYYY-mm-DD");
    
    public WebQAService() {
        log.info("Initializing service");
        dao = NewspaperQADaoFactory.getInstance();
    }
    
    @GET
    @Path("getNewspaperIDs")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getNewspaperIDs() {
        List<String> IDs;
        try {
            IDs = dao.getNewspaperIDs();
            return Response.ok(IDs, MediaType.APPLICATION_JSON).build();
        } catch (DAOFailureException e) {
            log.error("Could not get newspaper IDs from backend");
            ResponseBuilder builder = Response.status(Response.Status.INTERNAL_SERVER_ERROR);
            builder.entity("Could not get newspaper IDs from backend");
            return builder.build();
        }
    }
    
    @GET
    @Path("dates/{ID}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getDatesForNewspaper(@PathParam("ID") String ID) {
        if(ID == null) {
            log.error("ID not supplied in request");
            ResponseBuilder builder = Response.status(Response.Status.BAD_REQUEST);
            builder.entity("ID must be supplied");
            return builder.build();
        }
        
        List<Date> dates;
        try {
            dates = dao.getDatesForNewspaperID(ID);
            return Response.ok(dates, MediaType.APPLICATION_JSON).build();
        } catch (DAOFailureException e) {
            log.error("Could not get dates for newspaper ID {}", ID);
            ResponseBuilder builder = Response.status(Response.Status.INTERNAL_SERVER_ERROR);
            builder.entity("Could not get dates from backend");
            return builder.build();
        }
    }
    
    @GET 
    @Path("dates/{ID}/{date}/entities")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getEntitiesForDate(@PathParam("ID") String ID, @PathParam("date") String date) {
        if(ID == null) {
            log.error("ID not supplied in request");
            ResponseBuilder builder = Response.status(Response.Status.BAD_REQUEST);
            builder.entity("ID must be supplied");
            return builder.build();
        }
        if(date == null) {
            log.error("Date not supplied in request");
            ResponseBuilder builder = Response.status(Response.Status.BAD_REQUEST);
            builder.entity("Date must be supplied");
            return builder.build();
        } else {
            try {
                Date d = sdf.parse(date);
            } catch (ParseException e1) {
                log.error("Date could not be parsed");
                ResponseBuilder builder = Response.status(Response.Status.BAD_REQUEST);
                builder.entity("Date must be in the format YYYY-mm-DD");
                return builder.build();
            }
        }
        
        List<NewspaperEntity> entities;
        try {
            entities = dao.getEditionsForNewspaperOnDate(ID, date);
            return Response.ok(entities, MediaType.APPLICATION_JSON).build();
        } catch (DAOFailureException e) {
            log.error("Could not get entities for date {} for newspaper ID {}", date, ID);
            ResponseBuilder builder = Response.status(Response.Status.INTERNAL_SERVER_ERROR);
            builder.entity("Could not get entities from backend");
            return builder.build();
        }
    }
    
    @GET
    @Path("entity/{entity}/characterization")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getEntityCharacterisation(@PathParam("entity") String entity) {
        if(entity == null) {
            log.error("Entity was not not supplied in request");
            ResponseBuilder builder = Response.status(Response.Status.BAD_REQUEST);
            builder.entity("Entity must be supplied");
            return builder.build();
        }
        
        List<CharacterizationInfo> characterisations;
        try {
            characterisations = dao.getCharacterizationForEntity(entity);
            return Response.ok(characterisations, MediaType.APPLICATION_JSON).build();
        } catch (DAOFailureException e) {
            log.error("Could not get characterisation for newspaper entity {}", entity);
            ResponseBuilder builder = Response.status(Response.Status.INTERNAL_SERVER_ERROR);
            builder.entity("Could not get dates from backend");
            return builder.build();
        }
    }
}