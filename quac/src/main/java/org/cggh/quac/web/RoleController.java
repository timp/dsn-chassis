package org.cggh.quac.web;

import org.springframework.roo.addon.web.mvc.controller.RooWebScaffold;
import org.cggh.quac.model.Role;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.stereotype.Controller;

@RooWebScaffold(path = "roles", formBackingObject = Role.class)
@RequestMapping("/roles")
@Controller
public class RoleController {
}
