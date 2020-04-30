package com.golikova.projects.resourceserver.rest;

import com.golikova.projects.resourceserver.model.Query;
import com.golikova.projects.resourceserver.model.UserData;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.golikova.projects.resourceserver.model.AccessTokenMapper;

import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@RestController
public class NoteServiceController {

	@Autowired
	JdbcTemplate jdbcTemplate;

	
	@PreAuthorize("hasRole('CREATE_NOTE')")
	@RequestMapping(value="/note", method=RequestMethod.POST,  consumes= MediaType.APPLICATION_JSON_VALUE)
	public String createNote(@RequestBody UserData userData) {
		
		AccessTokenMapper accessTokenMapper = (AccessTokenMapper)
				((OAuth2AuthenticationDetails) SecurityContextHolder.getContext().getAuthentication().getDetails()).getDecodedDetails();
		
		System.out.println("Запрос:"+userData.getSelection());
		System.out.println("Name:"+accessTokenMapper.getName());
		System.out.println("Email ID:"+accessTokenMapper.getUserName());

		if(userData.getSelection() != null && !userData.getSelection().isEmpty()) {

			jdbcTemplate.update("insert into queries (`user_id`, `query`, `date_time`) values " +
							"(? ,?, CURRENT_TIMESTAMP);",
					accessTokenMapper.getId(),
					userData.getSelection());
		}
		
		return "Note has been created successfully";
	}

	@PreAuthorize("hasRole('VIEW_NOTE')")
	@RequestMapping(value="/note", method=RequestMethod.GET)
	public String viewAllNotes() throws JSONException {

		AccessTokenMapper accessTokenMapper = (AccessTokenMapper)
				((OAuth2AuthenticationDetails) SecurityContextHolder.getContext().getAuthentication().getDetails()).getDecodedDetails();

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

		List<Query> global = jdbcTemplate.query("SELECT * FROM USERSERVICE_NEW.top_queries where user_id = 0 order by date_time desc ; " , queryRowMapper);


		List<Query> personal = jdbcTemplate.query("SELECT * FROM USERSERVICE_NEW.top_queries where user_id = "+ accessTokenMapper.getId() +" order by date_time desc ; " , queryRowMapper);

		JSONObject object = new JSONObject();
		object.put("global", global.get(0).getQuery());
		object.put("personal", personal.get(0).getQuery());
		return object.toString();
	}
	
	@PreAuthorize("hasRole('EDIT_NOTE')")
	@RequestMapping(value="/note", method=RequestMethod.PUT)
	public String updateNote() {
		return "Note has been updated successfully";
	}
	
	@PreAuthorize("hasRole('DELETE_NOTE')")
	@RequestMapping(value="/note", method=RequestMethod.DELETE)
	public String deleteNote() {
		return "Note has been deleted successfully";
	}

	@PreAuthorize("hasRole('VIEW_NOTE')")
	@RequestMapping(value="/noteById", method=RequestMethod.GET)
	public String viewNotesByID() {
		return "Notes By ID response";
	}
	
	
	
	
}
