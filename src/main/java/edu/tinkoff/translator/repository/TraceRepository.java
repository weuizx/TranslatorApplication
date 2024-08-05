package edu.tinkoff.translator.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


@Repository
public class TraceRepository {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public TraceRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Transactional
    public int add(String ipAddress, String sourceText, String translatedText, String sourceLang, String targetLang) {
        String query = "INSERT INTO trace (ip_address, source_text, translated_text, source_lang, target_lang) " +
                "VALUES (?, ?, ?, ?, ?)";
        return jdbcTemplate.update(query, ipAddress, sourceText, translatedText, sourceLang, targetLang);
    }

    public int selectCountAll(){
        String query = "SELECT COUNT(*) FROM trace";
        return jdbcTemplate.query(query, (rs, rn) -> rs.getInt(1)).getFirst();
    }
}