package org.cggh.quac.model;

import javax.persistence.Transient;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.roo.addon.dbre.RooDbManaged;
import org.springframework.roo.addon.entity.RooEntity;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.tostring.RooToString;
import org.springframework.security.authentication.encoding.MessageDigestPasswordEncoder;

@RooJavaBean
@RooToString
@RooEntity(versionField = "", table = "users", catalog = "wwarn_drupal")
@RooDbManaged(automaticallyDelete = true)
public class Users {
	@Autowired
	@Transient
	transient private MessageDigestPasswordEncoder passwordEncoder;

	private String pass;

	public void setPass(String password) {
		if (password == null || password.equals("")) {
			return; // don't update password if blank is sent
		}
		String encodedPassword = passwordEncoder.encodePassword(password, null);
		this.pass = encodedPassword;
	}
}
