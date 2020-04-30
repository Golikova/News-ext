package com.example.golikova.statistics;

import com.example.golikova.statistics.model.Query;
import com.example.golikova.statistics.model.User;
import common.InputOpenException;
import model.memory.short_time.Text;
import model.memory.short_time.Word;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import javax.sql.DataSource;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class DBSerfer {

    private String TEXT_FILE_NAME = "nodes.txt";

    @Bean
    @Primary
    public DataSource dataSource() {
        return DataSourceBuilder
                .create()
                .username("root")
                .password("root")
                .url("jdbc:mysql://localhost:3306/USERSERVICE_NEW?autoreconnect=true?useUnicode=yes&characterEncoding=UTF-8")
                .driverClassName("com.mysql.jdbc.Driver")
                .build();
    }

    JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource());

    public void getPersonQuerySet() throws IOException, InputOpenException {

        RowMapper<User> userRowMapper = new RowMapper<User>() {
            public User mapRow(ResultSet rs, int rowNum) throws SQLException {
                User user = new User();
                user.setId(rs.getString("id"));
                return user;
            }
        };
        List<User> users = jdbcTemplate.query("SELECT ID FROM USER ", userRowMapper);

        for (User u:
             users) {
            RowMapper<Query> queryRowMapper = new RowMapper<Query>() {
                public Query mapRow(ResultSet rs, int rowNum) throws SQLException {
                    Query query = new Query();
                    query.setId(rs.getString("id"));
                    query.setUser_id(rs.getString("user_id"));
                    query.setQuery(rs.getString("query"));
                    query.setDate_time(rs.getString("date_time"));
                    return query;
                }
            };

            List<Query> res = jdbcTemplate.query("SELECT * FROM queries where user_id = " + u.getId() , queryRowMapper);
            if (!res.isEmpty() && res!=null) {
                createDataFile(res);
                start(u.getId());
            }

        }

    }

    public void getAllQuerySet() throws IOException, InputOpenException {

            RowMapper<Query> queryRowMapper = new RowMapper<Query>() {
                public Query mapRow(ResultSet rs, int rowNum) throws SQLException {
                    Query query = new Query();
                    query.setId(rs.getString("id"));
                    query.setUser_id(rs.getString("user_id"));
                    query.setQuery(rs.getString("query"));
                    query.setDate_time(rs.getString("date_time"));
                    return query;
                }
            };

            List<Query> res = jdbcTemplate.query("SELECT * FROM queries", queryRowMapper);

            createDataFile(res);

            start("0");

        }


    private void createDataFile(List<Query> res) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(TEXT_FILE_NAME));

        for (Query q:
                res) {

            writer.write(q.getQuery() + " ");
        }
        writer.close();
    }

    public void start(String id) throws InputOpenException {

        // Открываем и парсим текстовый файл, который лежит по адресу TEXT_FILE_NAME
        Text text = new Text(TEXT_FILE_NAME);

        // Получаем список слов отранжированных по весу
        List<Word> words = text.getWords();

        // Получаем тему текста
        //List<Word> theme = text.getThem();

        Word popular = words.get(0);

        for (Word w: words) {
            if (popular.getCount() < w.getCount()) {
                popular = w;
            }
        }

        insertQuery(popular, id);

        // Получаем первое слово в списке слов
        //Word word = words.get(0);

        // Получаем лист всех словоформ
        //List<String> wordForms = word.getWordForms();

        // Получаем количество вхождений слова в текст
        //long count = word.getCount();

        // Получаем все персоны, которые встречаются в тексте
        //List<Word> objects = text.getObjects();

        // Смотрим вес слова
       //double weight = text.getWordWeight(word);



    }

    private void insertQuery(Word popular, String id) {
        jdbcTemplate.update("insert into top_queries (`user_id`, `query`, `date_time`) values " +
                        "(? ,?, CURRENT_TIMESTAMP );",
                id,
                popular.toString());
    }

}
