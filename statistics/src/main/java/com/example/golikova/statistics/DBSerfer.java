package com.example.golikova.statistics;

import common.InputOpenException;
import model.memory.short_time.Text;
import model.memory.short_time.Word;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class DBSerfer {

    private String TEXT_FILE_NAME = "allnodes.txt";


    JdbcTemplate jdbcTemplate;

    public JdbcTemplate getJdbcTemplate() {
        return jdbcTemplate;
    }

    @Autowired
    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    public void createDataFile() {
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

        System.out.println(res);
    }

    public void start() throws InputOpenException {

        // Открываем и парсим текстовый файл, который лежит по адресу TEXT_FILE_NAME
        Text text = new Text(TEXT_FILE_NAME);

        // Получаем список слов отранжированных по весу
        List<Word> words = text.getWords();
        System.out.println(words);

        // Получаем тему текста
        List<Word> theme = text.getThem();

        // Получаем первое слово в списке слов
        Word word = words.get(0);

        // Получаем лист всех словоформ
        List<String> wordForms = word.getWordForms();

        // Получаем количество вхождений слова в текст
        long count = word.getCount();

        // Получаем все персоны, которые встречаются в тексте
        List<Word> objects = text.getObjects();

        // Смотрим вес слова
        double weight = text.getWordWeight(word);

    }

    public class Query {
        String id;
        String user_id;
        String query;
        String date_time;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getUser_id() {
            return user_id;
        }

        public void setUser_id(String user_id) {
            this.user_id = user_id;
        }

        public String getQuery() {
            return query;
        }

        public void setQuery(String query) {
            this.query = query;
        }

        public String getDate_time() {
            return date_time;
        }

        public void setDate_time(String date_time) {
            this.date_time = date_time;
        }
    }
}
