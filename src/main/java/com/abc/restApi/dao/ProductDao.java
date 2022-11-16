package com.abc.restApi.dao;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.web.multipart.MultipartFile;

import com.abc.restApi.entity.Product;

public interface ProductDao {

	public boolean saveProduct(Product product);

	public Product getProductById(int productId);

	public List<Product> getAllProducts();

	public boolean deleteProduct(int productid);

	public boolean updateProduct(Product product);

	public List<Product> sortProductsById_ASC();

	public List<Product> sortProductsById_DESC();

	public List<Product> sortProductsByName_ASC();

    public List<Product> sortProductsByName_DESC();

    public List<Product> getMaxPriceProducts();
    
    public List<Product> getMinPriceProducts();
    
    public double countSumOfProductPrice();
    
    public long getTotalCountOfProducts();
    
    public String deleteAllProducts();
    
   
    public int excelToDatabase(List<Product>list) ;
    	
    
}
