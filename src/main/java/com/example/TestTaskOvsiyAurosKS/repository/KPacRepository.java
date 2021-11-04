package com.example.TestTaskOvsiyAurosKS.repository;

import com.example.TestTaskOvsiyAurosKS.entity.KPac;
import com.example.TestTaskOvsiyAurosKS.exception.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Repository
public class KPacRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

//    public KPac findById(long id) {
//        return jdbcTemplate.query("SELECT * FROM kpacs WHERE ID=?",
//                        ps -> ps.setLong(1, id),
//                        new BeanPropertyRowMapper<>(KPac.class))
//                .stream().findAny().orElseThrow(() -> new EntityNotFoundException(id));
//    }

    public KPac findKPacById(long id) {
        return jdbcTemplate.query("SELECT * FROM kpacs WHERE ID=?",

                ps -> ps.setLong(1, id),
                (rs, rowNum) -> new KPac(
                        rs.getLong("id"),
                        rs.getString("title"),
                        rs.getString("description"),
                        rs.getDate("creation_date").toLocalDate()
                                .format(DateTimeFormatter.ofPattern("dd-MM-yyyy"))
                )
        ).stream().findAny().orElseThrow(() -> new EntityNotFoundException(id));
    }

    public Optional<KPac> findKPacByTitle(KPac kPac) {
        return jdbcTemplate.query("SELECT * FROM kpacs WHERE title=?",
                ps -> ps.setString(1, kPac.getTitle()),
                (rs, rowNum) -> new KPac(
                        rs.getLong("id"),
                        rs.getString("title"),
                        rs.getString("description"),
                        rs.getDate("creation_date").toLocalDate()
                                .format(DateTimeFormatter.ofPattern("dd-MM-yyyy"))
                )
        ).stream().findAny();
    }

//    public List<KPac> findAllKPacs() {
//        return jdbcTemplate.query(
//                "SELECT * FROM kpacs",
//                (rs, rowNum) -> new KPac(
//                        rs.getLong("id"),
//                        rs.getString("title"),
//                        rs.getString("description"),
//                        rs.getDate("creation_date").toLocalDate()
//                                .format(DateTimeFormatter.ofPattern("dd-MM-yyyy"))
//                )
//        );
//    }

    public List<KPac> findAllKPacs() {
        return jdbcTemplate.query("SELECT * FROM kpacs",
                new BeanPropertyRowMapper<>(KPac.class));
    }

    public void addKPac(KPac kPac) {
        jdbcTemplate.update(
                "INSERT INTO kpacs(title, description, creation_date) VALUES(?, ?, ?)",
                kPac.getTitle(), kPac.getDescription(), kPac.getCreationDate());
    }

    public void deleteKPac(KPac kPac) {
        jdbcTemplate.update("DELETE FROM kpacs WHERE ID=?", kPac.getId());
    }
}
