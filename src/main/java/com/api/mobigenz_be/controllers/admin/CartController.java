package com.api.mobigenz_be.controllers.admin;

import com.api.mobigenz_be.entities.Cart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.api.mobigenz_be.services.CartService;
import com.api.mobigenz_be.services.CustomerService;
import static org.springframework.http.HttpStatus.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
@CrossOrigin("*")
public class CartController {
	@Autowired
	private CartService cartService;
	
	
}
