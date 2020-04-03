package com.golikova.projects.authserver.dao;

import com.golikova.projects.authserver.model.UserEntity;

public interface OAuthDAOService {

	public UserEntity getUserDetails(String emailId);
}
