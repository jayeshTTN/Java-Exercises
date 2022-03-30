package com.prasun.BootCamp.Controller;

import com.prasun.BootCamp.security.SecurityService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import com.prasun.BootCamp.Model.ApplicationUser;
import com.prasun.BootCamp.repo.UserRepo;

@RestController
public class UserController {

	@Autowired
	UserRepo userRepo;


	@Autowired
	private SecurityService securityService;

	@Autowired
	private PasswordEncoder encoder;

	@GetMapping("/user/{user}")
	public ApplicationUser getUser(@PathVariable String user) {
		ApplicationUser userFound = userRepo.findByEmail(user);
		return userFound;
	}

	@PostMapping("/registerUser")
	public String register(@RequestBody ApplicationUser user) {
		user.setPassword(encoder.encode(user.getPassword()));
		userRepo.save(user);
		return "successfull";
	}

	@RequestMapping("/current/user")
	public ApplicationUser getCurrentUser() {
		System.out.println(SecurityContextHolder.getContext().getAuthentication());
		return (ApplicationUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	}
}
	

