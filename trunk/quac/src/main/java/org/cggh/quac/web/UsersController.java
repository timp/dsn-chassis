package org.cggh.quac.web;

import org.springframework.roo.addon.web.mvc.controller.RooWebScaffold;
import org.cggh.quac.model.Users;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.stereotype.Controller;

@RooWebScaffold(path = "userses", formBackingObject = Users.class)
@RequestMapping("/userses")
@Controller
public class UsersController {
}
