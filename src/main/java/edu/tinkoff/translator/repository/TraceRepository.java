package edu.tinkoff.translator.repository;

import edu.tinkoff.translator.configuration.JdbcConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.*;

@Slf4j
@Repository
public class TraceRepository {
    private final String url;
    private final String username;
    private final String password;

    @Autowired
    public TraceRepository(JdbcConfig jdbcConfig) {
        this.url = jdbcConfig.url();
        this.username = jdbcConfig.username();
        this.password = jdbcConfig.password();
    }

    @Transactional
    public int add(String ipAddress, String sourceText, String translatedText, String sourceLang, String targetLang) {
        String query = "INSERT INTO trace (ip_address, source_text, translated_text, source_lang, target_lang) " +
                "VALUES (?, ?, ?, ?, ?)";

        int rowsAffected = 0;

        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement statement = connection.prepareStatement(query)) {


            statement.setString(1, ipAddress);
            statement.setString(2, sourceText);
            statement.setString(3, translatedText);
            statement.setString(4, sourceLang);
            statement.setString(5, targetLang);

            rowsAffected = statement.executeUpdate();
        } catch (SQLException e) {
            log.error(e.getMessage());
        }

        return rowsAffected;


    }

    public Integer selectCountAll() {
        String query = "SELECT COUNT(*) FROM trace";

        Integer numberOfLines = null;

        try (Connection connection = DriverManager.getConnection(url, username, password);
             Statement statement = connection.createStatement()) {
            try (ResultSet resultSet = statement.executeQuery(query)) {
                while (resultSet.next()) {
                    numberOfLines = resultSet.getInt(1);
                }
            }
        } catch (SQLException e) {
            log.error(e.getMessage());
        }

        return numberOfLines;
    }
}