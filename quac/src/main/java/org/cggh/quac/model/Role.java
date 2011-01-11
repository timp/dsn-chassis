package org.cggh.quac.model;

import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.tostring.RooToString;
import org.springframework.roo.addon.entity.RooEntity;
import org.springframework.roo.addon.dbre.RooDbManaged;

@RooJavaBean
@RooToString
@RooEntity(versionField = "", table = "role", catalog = "wwarn_drupal")
@RooDbManaged(automaticallyDelete = true)
public class Role {
}
