package dk.kb.avischk.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mchange.v2.c3p0.ComboPooledDataSource;

import dk.kb.avischk.dto.CharacterizationInfo;
import dk.kb.avischk.dto.NewspaperEntity;

public class NewspaperQADao {
    private final Logger log = LoggerFactory.getLogger(getClass());
    
    private final ComboPooledDataSource connectionPool;
    
    public NewspaperQADao(ComboPooledDataSource connectionPool) {
        this.connectionPool = connectionPool;
    }
    
    public List<String> getNewspaperIDs() throws DAOFailureException {
        log.debug("Looking up newspaper ids");
        String SQL = "SELECT distinct(avisid) FROM newspaperarchive";
        
        try (Connection conn = connectionPool.getConnection();
                PreparedStatement ps = conn.prepareStatement(SQL)) {
               try (ResultSet res = ps.executeQuery()) {
                    List<String> list = new ArrayList<>();
                   while(res.next()) {
                       list.add(res.getString(1));
                   }
                   return list;
               }
           } catch (SQLException e) {
               log.error("Failed to lookup newspaper ids", e);
               throw new DAOFailureException("Err looking up newspaper ids", e);
           }
    }
    
    public List<Date> getDatesForNewspaperID(String id) throws DAOFailureException {
        log.debug("Looking up dates for newspaper id: '{}'", id);
        String SQL = "SELECT distinct(edition_date) FROM newspaperarchive WHERE avisid = ?";
        
        try (Connection conn = connectionPool.getConnection();
                PreparedStatement ps = conn.prepareStatement(SQL)) {
               
               ps.setString(1, id);
               try (ResultSet res = ps.executeQuery()) {
                   List<Date> list = new ArrayList<>();
                   
                   while(res.next()) {
                       list.add(res.getDate(1));
                   }
                   return list;
               }
           } catch (SQLException e) {
               log.error("Failed to lookup edition dates for newspaper id {}", id, e);
               throw new DAOFailureException("Err looking up dates for newspaper id", e);
           }
    }
    
    public List<NewspaperEntity> getEditionsForNewspaperOnDate(String id, String date) throws DAOFailureException {
        log.debug("Looking up dates for newspaper id: '{}' on date '{}'", id, date);
        String SQL = "SELECT * FROM newspaperarchive WHERE avisid = ? and edition_date = ?";
        
        try (Connection conn = connectionPool.getConnection();
                PreparedStatement ps = conn.prepareStatement(SQL)) {
               
               ps.setString(1, id);
               ps.setDate(2, java.sql.Date.valueOf(date));
               try (ResultSet res = ps.executeQuery()) {
                   List<NewspaperEntity> list = new ArrayList<>();
                   
                   while(res.next()) {
                       NewspaperEntity entity = new NewspaperEntity();
                       entity.setOrigRelpath(res.getString("orig_relpath"));
                       entity.setFormatType(res.getString("format_type"));
                       entity.setEditionDate(res.getDate("edition_date"));
                       entity.setSinglePage(res.getBoolean("single_page"));
                       entity.setPageNumber(res.getInt("page_number"));
                       entity.setAvisid(res.getString("avisid"));
                       entity.setShadowPath(res.getString("shadow_path"));
                       entity.setSectionTitle(res.getString("section_title"));
                       entity.setEditionTitle(res.getString("edition_title"));
                       entity.setDeliveryDate(res.getDate("delivery_date"));
                       list.add(entity);
                   }
                   return list;
               }
           } catch (SQLException e) {
               log.error("Failed to lookup edition dates for newspaper id {}", id, e);
               throw new DAOFailureException("Err looking up dates for newspaper id", e);
           }
    }
    
    public List<CharacterizationInfo> getCharacterizationForEntity(String entity) throws DAOFailureException {
        log.debug("Looking up characterization for newspaper entity: '{}'", entity);
        String SQL = "SELECT * FROM characterisation_info WHERE orig_relpath = ?";
        
        try (Connection conn = connectionPool.getConnection();
                PreparedStatement ps = conn.prepareStatement(SQL)) {
               
               ps.setString(1, entity);
               try (ResultSet res = ps.executeQuery()) {
                   List<CharacterizationInfo> list = new ArrayList<>();
                   
                   while(res.next()) {
                       CharacterizationInfo info = new CharacterizationInfo();
                       info.setOrigRelpath(res.getString("orig_relpath"));
                       info.setTool(res.getString("tool"));
                       info.setCharacterisationDate(res.getDate("characterisation_date"));
                       info.setToolOutput(res.getString("tool_output"));
                       info.setStatus(res.getString("status"));
                       list.add(info);
                   }
                   return list;
               }
           } catch (SQLException e) {
               log.error("Failed to lookup characterization info for newspaper entity {}", entity, e);
               throw new DAOFailureException("Err looking up characterization info for newspaper entity", e);
           }
    }
    
}
