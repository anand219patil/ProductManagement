package com.abc.restApi.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.abc.restApi.dao.ProductDao;
import com.abc.restApi.entity.Product;

@Service
public class ProductServiceImpl implements ProductService {

	@Autowired
	private ProductDao dao;

	int countProductsInSheet = 0;

	@Override
	public boolean saveProduct(Product product) {
		boolean added = dao.saveProduct(product);

		return added;
	}

	@Override
	public Product getProductById(int productId) {
		Product product = dao.getProductById(productId);

		return product;
	}

	@Override
	public List<Product> getAllProducts() {
		List<Product> list = dao.getAllProducts();
		return list;
	}

	@Override
	public boolean deleteProduct(int productid) {
		boolean deleted = dao.deleteProduct(productid);
		return deleted;
	}

	@Override
	public boolean updateProduct(Product product) {
		boolean updated = dao.updateProduct(product);
		return updated;
	}

	@Override
	public List<Product> sortProductsById_ASC() {
		List<Product> list = dao.sortProductsById_ASC();
		return list;
	}

	@Override
	public List<Product> sortProductsById_DESC() {
		List<Product> list = dao.sortProductsById_DESC();
		return list;
	}

	@Override
	public List<Product> sortProductsByName_ASC() {
		List<Product> list = dao.sortProductsByName_ASC();
		return list;
	}

	@Override
	public List<Product> sortProductsByName_DESC() {
		List<Product> list = dao.sortProductsByName_DESC();
		return list;
	}

	@Override
	public List<Product> getMaxPriceProducts() {
		List<Product> list = dao.getMaxPriceProducts();
		return list;
	}

	@Override
	public List<Product> getMinPriceProducts() {
		List<Product> list = dao.getMinPriceProducts();
		return list;
	}

	@Override
	public double countSumOfProductPrice() {
		double sumofprice = dao.countSumOfProductPrice();
		return sumofprice;
	}

	@Override
	public long getTotalCountOfProducts() {
		long toatalProduct = dao.getTotalCountOfProducts();
		return toatalProduct;
	}

	public String deleteAllProducts() {
		String msg = dao.deleteAllProducts();
		return msg;
	}

	// ExcelSheetReading.............. //

	public List<Product> readExcelSheetData(String path) {
		Product product = null;
		List<Product> list = new ArrayList<>();
		try {
			FileInputStream fileIn = new FileInputStream(new File(path));

			Workbook workbook = new XSSFWorkbook(fileIn);
			Sheet sheet = workbook.getSheetAt(0);
			countProductsInSheet = sheet.getLastRowNum();

			Iterator<Row> rows = sheet.rowIterator();

			int count = 0;
			while (rows.hasNext()) {
				Row row = rows.next();
				product = new Product();

				if (count == 0) {
					count = count + 1;
					continue;
				}

				Iterator<Cell> cells = row.cellIterator();

				while (cells.hasNext()) {
					Cell cell = cells.next();

					int col = cell.getColumnIndex();

					switch (col) {
					case 0: {

						int id = (int) cell.getNumericCellValue();
						product.setProductId(id);

						break;
					}
					case 1: {
						String name = cell.getStringCellValue();
						product.setProductName(name);

						break;
					}
					case 2: {
						double price = cell.getNumericCellValue();
						product.setProductPrice(price);

						break;
					}
					case 3: {
						String type = cell.getStringCellValue();
						product.setProductType(type);

						break;
					}

					default:
						break;
					}

				}
				list.add(product);
				workbook.close();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;

	}

	@Override
	public String uploadExcelSheet(MultipartFile file, HttpSession session) {

		int addedCount = 0;
		String msg = null;
		String path = session.getServletContext().getRealPath("/uploaded");
		String fileName = file.getOriginalFilename();

		try {
			byte[] data = file.getBytes();
			FileOutputStream fileOut = new FileOutputStream(new File(path + File.separator + fileName));
			fileOut.write(data);

			List<Product> list = readExcelSheetData(path + File.separator + fileName);
			
			

			addedCount = dao.excelToDatabase(list);

			msg = " Total Products in ExcelSheet = " + countProductsInSheet+ " , Added Products from excelSheet to Database = "+addedCount;

		} catch (Exception e) {
			e.printStackTrace();
		}
		return msg;
	}

}
