package com.example.TestTaskOvsiyAurosKS.repository;

import com.example.TestTaskOvsiyAurosKS.entity.KPac;
import com.example.TestTaskOvsiyAurosKS.entity.KSet;
import com.example.TestTaskOvsiyAurosKS.exception.EntityNotFoundException;
import com.example.TestTaskOvsiyAurosKS.exception.NameExistsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class KSetRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public KSet findKSetById(long id) throws EntityNotFoundException {
        return jdbcTemplate.query("SELECT * FROM kpacs_set WHERE ID=?",

                new PreparedStatementSetter() {
                    @Override
                    public void setValues(PreparedStatement ps) throws SQLException {
                        ps.setLong(1, id);
                    }
                },
                new RowMapper<KSet>() {
                    @Override
                    public KSet mapRow(ResultSet rs, int rowNum) throws SQLException {
                        return new KSet(
                                rs.getLong("id"),
                                rs.getString("title")
                        );
                    }
                }
        ).stream().findAny().orElseThrow(() -> new EntityNotFoundException(id));
    }

    public Optional<KSet> findKSetByTitle(KSet kSet) {
        return jdbcTemplate.query("SELECT * FROM kpacs_set WHERE title=?",
                ps -> ps.setString(1, kSet.getTitle()),
                (rs, rowNum) -> new KSet(
                        rs.getLong("id"),
                        rs.getString("title")
                )
        ).stream().findAny();
    }

    public List<KSet> findAllSet() {
        return jdbcTemplate.query(
                "SELECT * FROM kpacs_set",
                (rs, rowNum) -> new KSet(
                        rs.getLong("id"),
                        rs.getString("title")
                )
        );
    }

    public void addKSet(KSet kSet, String kPacs) throws NameExistsException, EntityNotFoundException {
//        if (jdbcTemplate.query("SELECT * FROM kpacs_set WHERE title=?",
//                ps -> ps.setString(1, kSet.getTitle()),
//                (rs, rowNum) -> new KSet(
//                        rs.getLong("id"),
//                        rs.getString("title")
//                )).stream().findAny().isPresent()) {
//            throw new IllegalNameException(kSet.getTitle());
//        }

        jdbcTemplate.update(
                "INSERT INTO kpacs_set(title) VALUES(?)",
                kSet.getTitle());

        String[] words = kPacs.split(",");

        List<String> titles = new ArrayList<>();

        for (String word : words) {
            titles.add(word.trim());
        }

        String kPacsTitles = getQueryParams(titles);

        String selectQuery = "SELECT ID FROM kpacs WHERE title IN ("
                .concat(kPacsTitles)
                .concat(")");

        List<Long> kPacIds = jdbcTemplate.query(selectQuery,
                (rs, rowNum) -> rs.getLong("id")
        );

        KSet kSetFromDb = jdbcTemplate.query("SELECT * FROM kpacs_set WHERE title=?",
                new PreparedStatementSetter() {
                    @Override
                    public void setValues(PreparedStatement ps) throws SQLException {
                        ps.setString(1, kSet.getTitle());
                    }
                },
                new RowMapper<KSet>() {
                    @Override
                    public KSet mapRow(ResultSet rs, int rowNum) throws SQLException {
                        return new KSet(
                                rs.getLong("id"),
                                rs.getString("title")
                        );
                    }
                }
        ).stream().findAny().orElseThrow(() -> new EntityNotFoundException(kSet.getId()));

        StringBuilder values = new StringBuilder();
        if (kPacIds.size() != 0) {
            for (int i = 0; i < kPacIds.size(); i++) {
                if (i == kPacIds.size() - 1) {
                    values.append("("
                            .concat(String.valueOf(kSetFromDb.getId()))
                            .concat(", ")
                            .concat(String.valueOf(kPacIds.get(i)))
                            .concat(")"));
                } else {
                    values.append("("
                            .concat(String.valueOf(kSetFromDb.getId()))
                            .concat(", ")
                            .concat(String.valueOf(kPacIds.get(i)))
                            .concat("), "));
                }
            }

            String query = "INSERT INTO connections(kpacs_set_ID, kpac_ID) VALUES ".concat(values.toString());

            jdbcTemplate.update(query);
        }
    }

    public void deleteKSet(KSet kSet) {
        jdbcTemplate.update("DELETE FROM kpacs_set WHERE ID=?", kSet.getId());
    }

    public List<KPac> getKPacsFromKSet(KSet kSet) {
        List<Long> kPacIds = jdbcTemplate.query(
                "SELECT * FROM connections WHERE kpacs_set_ID=?",
                ps -> ps.setLong(1, kSet.getId()),
                (rs, rowNum) -> rs.getLong("kpac_ID")
        );

        String queryParam = getQueryParams(kPacIds);

        String query = "SELECT * FROM kpacs WHERE ID IN ("
                .concat(queryParam)
                .concat(")");

        return jdbcTemplate.query(
                query,
                (rs, rowNum) -> new KPac(
                        rs.getLong("id"),
                        rs.getString("title"),
                        rs.getString("description"),
                        rs.getDate("creation_date").toLocalDate()
                                .format(DateTimeFormatter.ofPattern("dd-MM-yyyy"))
                )
        );
    }

    private <T> String getQueryParams(List<T> data) {
        StringBuilder stringBuilder = new StringBuilder();

        for (int i = 0; i < data.size(); i++) {
            if (i == data.size() - 1) {
                stringBuilder.append("'".concat(String.valueOf(data.get(i))).concat("'"));
            } else {
                stringBuilder.append("'".concat(String.valueOf(data.get(i))).concat("', "));
            }
        }
        return stringBuilder.toString();
    }
}
