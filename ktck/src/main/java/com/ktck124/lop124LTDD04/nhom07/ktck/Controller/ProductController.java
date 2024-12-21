package com.ktck124.lop124LTDD04.nhom07.ktck.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ktck124.lop124LTDD04.nhom07.ktck.Repository.ProductRepository;
import com.ktck124.lop124LTDD04.nhom07.ktck.modal.Product;



@RestController
@RequestMapping("/api")
public class ProductController {
	@Autowired
	private ProductRepository productRepository;
	
	@GetMapping("/product/all")
	public ResponseEntity<List<Product>> getAll() throws Exception{
		return ResponseEntity.status(HttpStatus.OK).body(productRepository.findAll());
	}
	
	@PutMapping("/product/{productId}")
	public ResponseEntity<Product> updateCarPost(@PathVariable("productId") Long carId, CarRequest req) throws Exception{
		return ResponseEntity.status(HttpStatus.OK).body(carService.updateCarPost(carId, req));
	}
}
