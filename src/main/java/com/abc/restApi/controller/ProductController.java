package com.abc.restApi.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.abc.restApi.entity.Product;
import com.abc.restApi.exception.ProductAlreadyExistsException;
import com.abc.restApi.exception.ProductNotFoundException;
import com.abc.restApi.service.ProductService;

@RestController
@RequestMapping(value = "/product")
public class ProductController {

	@Autowired
	private ProductService service;

	@PostMapping(value = "/saveproduct")
	public ResponseEntity<Boolean> saveProduct(@RequestBody Product product) {

		boolean added = service.saveProduct(product);

		if (added) {
			return new ResponseEntity<Boolean>(added, HttpStatus.CREATED);
		} else {
			throw new ProductAlreadyExistsException("Product already exists with ID = " + product.getProductId());
		}

	}

	@GetMapping(value = "/getproductbyid/{productId}")
	public ResponseEntity<Product> getProductById(@PathVariable int productId) {

		Product product = service.getProductById(productId);

		if (product != null) {
			return new ResponseEntity<Product>(product, HttpStatus.OK);
		} else {
			throw new ProductNotFoundException("Product Not Found for ID = " + productId);
		}

	}

	@GetMapping(value = "/getallproducts")
	public ResponseEntity<List<Product>> getAllProduct() {

		List<Product> list = service.getAllProducts();

		if (!list.isEmpty()) {
			return new ResponseEntity<List<Product>>(list, HttpStatus.OK);
		} else {
			return new ResponseEntity<List<Product>>(HttpStatus.OK);
		}

	}

	@DeleteMapping(value = "/deleteproduct")
	public ResponseEntity<Boolean> deleteProduct(@RequestParam int productid) {
		boolean deleted = service.deleteProduct(productid);
		if (deleted) {
			return new ResponseEntity<Boolean>(deleted, HttpStatus.OK);
		} else {
			return new ResponseEntity<Boolean>(HttpStatus.OK);
		}
	}

	@PutMapping(value = "/updateproduct")
	public ResponseEntity<Boolean> updateProduct(@RequestBody Product product) {
		boolean updated = service.updateProduct(product);
		if (updated) {
			return new ResponseEntity<Boolean>(updated, HttpStatus.OK);
		} else {
			return new ResponseEntity<Boolean>(HttpStatus.OK);
		}
	}

	@GetMapping(value = "/sortproductsbyidasc")
	public ResponseEntity<List<Product>> sortProductsById_ASC() {

		List<Product> list = service.sortProductsById_ASC();

		if (!list.isEmpty()) {
			return new ResponseEntity<List<Product>>(list, HttpStatus.OK);
		} else {
			return new ResponseEntity<List<Product>>(HttpStatus.OK);
		}

	}

	@GetMapping(value = "/sortproductsbyiddesc")
	public ResponseEntity<List<Product>> sortProductsById_DESC() {

		List<Product> list = service.sortProductsById_DESC();

		if (!list.isEmpty()) {
			return new ResponseEntity<List<Product>>(list, HttpStatus.OK);
		} else {
			return new ResponseEntity<List<Product>>(HttpStatus.OK);
		}

	}

	@GetMapping(value = "/sortproductsbynameasc")
	public ResponseEntity<List<Product>> sortProductsByName_ASC() {

		List<Product> list = service.sortProductsByName_ASC();

		if (!list.isEmpty()) {
			return new ResponseEntity<List<Product>>(list, HttpStatus.OK);
		} else {
			return new ResponseEntity<List<Product>>(HttpStatus.OK);
		}

	}

	@GetMapping(value = "/sortproductsbynamedesc")
	public ResponseEntity<List<Product>> sortProductsByName_DESC() {

		List<Product> list = service.sortProductsByName_DESC();

		if (!list.isEmpty()) {
			return new ResponseEntity<List<Product>>(list, HttpStatus.OK);
		} else {
			return new ResponseEntity<List<Product>>(HttpStatus.OK);
		}

	}

	@GetMapping(value = "/getmaxpriceproducts")
	public ResponseEntity<List<Product>> getMaxPriceProducts() {

		List<Product> list = service.getMaxPriceProducts();

		if (!list.isEmpty()) {
			return new ResponseEntity<List<Product>>(list, HttpStatus.OK);
		} else {
			return new ResponseEntity<List<Product>>(HttpStatus.OK);
		}

	}

	@GetMapping(value = "/getminpriceproducts")
	public ResponseEntity<List<Product>> getMinPriceProducts() {

		List<Product> list = service.getMinPriceProducts();

		if (!list.isEmpty()) {
			return new ResponseEntity<List<Product>>(list, HttpStatus.OK);
		} else {
			return new ResponseEntity<List<Product>>(HttpStatus.OK);
		}

	}

	@GetMapping(value = "/countsumofproductprice")
	public ResponseEntity<Double> countSumOfProductPrice() {

		double sumOfPrice = service.countSumOfProductPrice();

		if (sumOfPrice != 0) {
			return new ResponseEntity<Double>(sumOfPrice, HttpStatus.OK);
		} else {
			return new ResponseEntity<Double>(HttpStatus.OK);
		}

	}

	@GetMapping(value = "/gettotalcountofproducts")
	public ResponseEntity<Double> getTotalCountOfProducts() {

		double totalProducts = service.getTotalCountOfProducts();

		if (totalProducts != 0) {
			return new ResponseEntity<Double>(totalProducts, HttpStatus.OK);
		} else {
			return new ResponseEntity<Double>(HttpStatus.OK);
		}

	}

	@DeleteMapping(value = "/deleteallproducts")
	public ResponseEntity<String> deleteAllProducts() {

		String msg = service.deleteAllProducts();

		if (msg != null) {
			return new ResponseEntity<String>(msg, HttpStatus.OK);
		} else {
			return new ResponseEntity<String>(HttpStatus.OK);
		}

	}

	///////////// import-export sheet //////////////

	@PostMapping(value = "/uploadsheet")
	public ResponseEntity<String> uploadExcelSheet(@RequestParam MultipartFile file, HttpSession session) {

		String message = service.uploadExcelSheet(file, session);

		if (message != null) {
			return new ResponseEntity<String>(message, HttpStatus.OK);
		} else {
			return new ResponseEntity<String>(HttpStatus.OK);
		}
	}

}
