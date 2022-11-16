package com.abc.restApi.dao;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.SimpleExpression;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

import com.abc.restApi.entity.Product;

@Repository
public class ProductDaoImpl implements ProductDao {

	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public boolean saveProduct(Product product) {

		boolean added = false;
		Session session = sessionFactory.openSession();
		try {

			Product prod = session.get(Product.class, product.getProductId());

			if (prod == null) {
				Transaction transaction = session.beginTransaction();
				session.save(product);
				System.out.println(product);
				transaction.commit();
				added = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (session != null) {
				session.close();
			}
		}

		return added;
	}

	@Override
	public Product getProductById(int productId) {

		Session session = sessionFactory.openSession();
		Product product = null;
		try {

			product = session.get(Product.class, productId);

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (session != null) {
				session.close();
			}
		}

		return product;
	}

	@Override
	public List<Product> getAllProducts() {
		Session session = sessionFactory.openSession();
		List<Product> list = null;
		try {
			Criteria criteria = session.createCriteria(Product.class);
			list = criteria.list();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (session != null) {
				session.close();
			}
		}

		return list;
	}

	@Override
	public boolean deleteProduct(int productid) {
		Session session = sessionFactory.openSession();
		Transaction transaction = session.beginTransaction();
		boolean deleted = false;
		try {
			Product product = session.get(Product.class, productid);
			if (product != null) {
				session.delete(product);

				transaction.commit();
				deleted = true;
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (session != null) {
				session.close();
			}
		}

		return deleted;

	}

	@Override
	public boolean updateProduct(Product product) {
		Session session = sessionFactory.openSession();
		Transaction transaction = session.beginTransaction();
		boolean updated = false;
		try {
			Product prod = session.get(Product.class, product.getProductId());
			session.evict(prod);
			if (prod != null) {
				session.update(product);
				transaction.commit();
				updated = true;
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (session != null) {
				session.close();
			}
		}

		return updated;

	}

	@Override
	public List<Product> sortProductsById_ASC() {
		Session session = sessionFactory.openSession();
		List<Product> list = null;
		try {
			Criteria criteria = session.createCriteria(Product.class);
			criteria.addOrder(Order.asc("productId"));
			list = criteria.list();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (session != null) {
				session.close();
			}
		}

		return list;
	}

	@Override
	public List<Product> sortProductsById_DESC() {
		Session session = sessionFactory.openSession();
		List<Product> list = null;
		try {
			Criteria criteria = session.createCriteria(Product.class);
			criteria.addOrder(Order.desc("productId"));
			list = criteria.list();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (session != null) {
				session.close();
			}
		}

		return list;
	}

	@Override
	public List<Product> sortProductsByName_ASC() {
		Session session = sessionFactory.openSession();
		List<Product> list = null;
		try {
			Criteria criteria = session.createCriteria(Product.class);
			criteria.addOrder(Order.asc("productName"));
			list = criteria.list();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (session != null) {
				session.close();
			}
		}

		return list;
	}

	@Override
	public List<Product> sortProductsByName_DESC() {
		Session session = sessionFactory.openSession();
		List<Product> list = null;
		try {
			Criteria criteria = session.createCriteria(Product.class);
			criteria.addOrder(Order.desc("productName"));
			list = criteria.list();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (session != null) {
				session.close();
			}
		}

		return list;
	}

	public List<Product> getMaxPriceProducts() {
		Session session = sessionFactory.openSession();
		double maxPrice = 0;
		List<Product> maxProdlist = null;

		try {
			Criteria maxCriteria = session.createCriteria(Product.class);
			Criteria eqCriteria = session.createCriteria(Product.class);

			maxCriteria.setProjection(Projections.max("productPrice"));
			List<Double> list = maxCriteria.list();
			maxPrice = list.get(0);

			eqCriteria.add(Restrictions.eq("productPrice", maxPrice));
			maxProdlist = eqCriteria.list();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (session != null) {
				session.close();
			}
		}
		return maxProdlist;

	}

	public List<Product> getMinPriceProducts() {
		Session session = sessionFactory.openSession();
		double minPrice = 0;
		List<Product> minProdlist = null;

		try {
			Criteria minCriteria = session.createCriteria(Product.class);
			Criteria eqCriteria = session.createCriteria(Product.class);

			minCriteria.setProjection(Projections.min("productPrice"));
			List<Double> list = minCriteria.list();
			minPrice = list.get(0);

			eqCriteria.add(Restrictions.eq("productPrice", minPrice));
			minProdlist = eqCriteria.list();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (session != null) {
				session.close();
			}
		}
		return minProdlist;

	}

	public double countSumOfProductPrice() {
		Session session = sessionFactory.openSession();
		double sum = 0;
		try {
			Criteria criteria = session.createCriteria(Product.class);
			criteria.setProjection(Projections.sum("productPrice"));
			List<Double> list = criteria.list();
			if (!list.isEmpty()) {
				sum = list.get(0);
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (session != null) {
				session.close();
			}
		}

		return sum;

	}

	public long getTotalCountOfProducts() {
		Session session = sessionFactory.openSession();
		long numOfProducts = 0;
		try {
			Criteria criteria = session.createCriteria(Product.class);
			criteria.setProjection(Projections.rowCount());
			List<Long> list = criteria.list();
			if (!list.isEmpty()) {
				numOfProducts = list.get(0);
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (session != null) {
				session.close();
			}
		}

		return numOfProducts;

	}

	@Override
	public String deleteAllProducts() {
		Session session = sessionFactory.openSession();
		List<Product> list = null;
		int count = 0;
		String msg = null;
		try {
			Criteria criteria = session.createCriteria(Product.class);
			list = criteria.list();
			for (Product product : list) {
				int id = product.getProductId();
				boolean deleted = deleteProduct(id);
				if (deleted) {
					count++;
				}
				msg = count + " = products deleted";

			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (session != null) {
				session.close();
			}
		}

		return msg;
	}

	public int excelToDatabase(List<Product> list) {

		int count = 0;

		for (Product product : list) {

		//	System.out.println(product);

			boolean added = saveProduct(product);

			if (added) {
				count++;

			}
		}
		return count;
	}

}
