package com.springboot.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCallback;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Controller
public class JdbcController {

    @Autowired
    private DataSource dataSource;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    /**
     * datasource连接
     *
     * @param name
     * @return
     * @throws SQLException
     */
    @GetMapping(value = "/getuser")
    Map<String, String> getPassword(@RequestParam(value = "name") String name) throws SQLException {
        Connection conn = dataSource.getConnection();
        PreparedStatement ps = conn.prepareStatement("select * from user where name='" + name + "'");
        ResultSet rs = ps.executeQuery();
        Map<String, String> m = new HashMap<>();
        while (rs.next()) {
            m.put("name", rs.getString("name"));
            m.put("password", rs.getString("password"));
        }
        return m;
    }

    /**
     * jdbc连接
     *
     * @throws SQLException
     */
    @GetMapping(value = "/insert")
    public void getPersonList() throws SQLException {
        jdbcTemplate.execute("insert into user(userguid,name,password) values(?,?,?)", new PreparedStatementCallback<Boolean>() {
            @Override
            public Boolean doInPreparedStatement(PreparedStatement p) throws SQLException, DataAccessException {
                p.setString(1, UUID.randomUUID().toString());
                p.setString(2, "joe");
                p.setString(3, "9867");
                p.executeUpdate();
                return true;
            }
        });
    }
}
