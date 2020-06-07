package com.cdn.vanburga.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.cdn.vanburga.model.Address;
import com.cdn.vanburga.model.Category;
import com.cdn.vanburga.model.Client;
import com.cdn.vanburga.model.Extra;
import com.cdn.vanburga.model.ExtraOrderDetail;
import com.cdn.vanburga.model.Order;
import com.cdn.vanburga.model.OrderDetail;
import com.cdn.vanburga.model.Product;
import com.cdn.vanburga.model.ProductExtra;
import com.cdn.vanburga.model.State;
import com.cdn.vanburga.model.request.OrderRequest;
import com.cdn.vanburga.model.response.CategoryResponse;
import com.cdn.vanburga.model.response.OrderResponse;
import com.cdn.vanburga.model.response.ProductData;
import com.cdn.vanburga.model.response.ProductResponse;
import com.cdn.vanburga.repository.AddressRepository;
import com.cdn.vanburga.repository.CategoryRepository;
import com.cdn.vanburga.repository.ClientRepository;
import com.cdn.vanburga.repository.ExtraOrderDetailRepository;
import com.cdn.vanburga.repository.OrderDetailRepository;
import com.cdn.vanburga.repository.OrderRepository;
import com.cdn.vanburga.repository.ProductExtraRepository;
import com.cdn.vanburga.repository.ProductRepository;
import com.cdn.vanburga.repository.StateRepository;

@Service
public class MenuService {

	private static final Logger logger = LogManager.getLogger(MenuService.class);	
	@Autowired @Lazy
	private CategoryRepository categoryRepository;
	
	@Autowired @Lazy
	private ProductRepository productRepository;
	
	@Autowired @Lazy
	private ProductExtraRepository productExtraRepository;
	
	@Autowired @Lazy
	private ClientRepository clientRepository;
	
	@Autowired @Lazy
	private AddressRepository addressRepository;
	
	@Autowired @Lazy
	private ExtraOrderDetailRepository extraOrderDetailRepository;
	
	@Autowired @Lazy
	private OrderRepository orderRepository;
	
	@Autowired @Lazy
	private OrderDetailRepository orderDetailRepository;
	
	@Autowired @Lazy
	private StateRepository stateRepository;
	
	private List<State> stateList;
	
	private Long lastId = new Long(11);
	
	public HttpStatus getAllCategories(CategoryResponse response) {
		logger.info("Searching all categories");
		List<Category> categoriesList = categoryRepository.findAll();
		response.setCategories(categoriesList);
		response.setStatus(HttpStatus.OK);
		response.setCode(HttpStatus.OK.value());
		response.setMessage("Success");
		
		return HttpStatus.OK;
		
	}
	
	public List<State> getStateList() {
		
		this.stateList = stateRepository.findAll();
		
		return this.stateList;
	}
	
	public HttpStatus getProductsByCategory(ProductResponse response, Long id) {
		logger.info("Searching product list by category id [" + id.toString() + "]");
		Optional<List<Product>> productList = productRepository.findByCategory(new Category(id));
		
		if (productList.isPresent()) {
			response.setProducts(productList.get());
			response.setCode(HttpStatus.OK.value());
			response.setStatus(HttpStatus.OK);
			response.setMessage("Success");
			return HttpStatus.OK;
		}
		logger.warn("No product found");
		response.setStatus(HttpStatus.NOT_FOUND);
		response.setCode(HttpStatus.NOT_FOUND.value());
		response.setMessage("No products found");
		
		return HttpStatus.NOT_FOUND;
		
	}
	
	
	public HttpStatus getProduct(ProductResponse response, Long id) {
		logger.info("Searching product id [" + id.toString() + "]");
		Optional<Product> product = productRepository.findById(id);
		
		if (product.isPresent()) {
			logger.debug("Product found - name: [" + product.get().getName() +"] Searching Extras of this product " );
			Optional<List<ProductExtra>> productList = productExtraRepository.findByProductExtraProduct(product.get().getId());
			ProductData productData = new ProductData();
			productData.setProduct(product.get());
			if(productList.isPresent()) {	
				logger.debug("Extras found - size: " + productList.get().size());
				List<Extra> extras = new ArrayList<Extra>();
				productList.get().stream().forEach(e -> extras.add(e.getProductExtra().getExtra()));
				productData.setExtras(extras);
			}
			response.setProduct(productData);
			response.setCode(HttpStatus.OK.value());
			response.setStatus(HttpStatus.OK);
			response.setMessage("Success");
			return HttpStatus.OK;
		}
		logger.warn("product with id "+ id.toString() +" not found");
		response.setStatus(HttpStatus.NOT_FOUND);
		response.setCode(HttpStatus.NOT_FOUND.value());
		response.setMessage("No products found");
		
		return HttpStatus.NOT_FOUND;
		
	}
	
	@Transactional
	public HttpStatus createOrder(OrderRequest orderRequest, OrderResponse orderResponse) {
		
		logger.info("Creating order");
		
		validateFields(orderRequest);
		
		//REGISTRA CLIENTE
		Client client = new Client();
		client.setName(orderRequest.getClient().getName());
		client.setLastName(orderRequest.getClient().getLastName());
		client.setMail(orderRequest.getClient().getMail());
		client.setCellphone(orderRequest.getClient().getCellphone());
		client = clientRepository.save(client);
		
		//Registra la direccion
		Address address = new Address();
		address.setClient(client);
		address.setDoor(orderRequest.getClient().getAddress().getDoor());
		address.setDoorNumber(orderRequest.getClient().getAddress().getDoorNumber());
		address.setFloor(orderRequest.getClient().getAddress().getFloor());
		//Descomentar cuando se genera la busqueda de localidades
		address.setState(stateList.stream().filter(s -> s.getId().longValue() == orderRequest.getClient().getAddress().getState().longValue()).findAny().get());
		address.setStreet(orderRequest.getClient().getAddress().getStreet());
		address.setZipCode(orderRequest.getClient().getAddress().getZipCode());
		address = addressRepository.save(address);
		
		//Crea la orden de pedido
		Order order = new Order();
		order.setClient(client);
		order.setComments(orderRequest.getComment());
		order.setCreateDate(LocalDateTime.now());
		order.setAmount(new BigDecimal(0));
		//Calcular monto total de la orden
		order = orderRepository.save(order);

		BigDecimal totalAmount = new BigDecimal(0);
		//Registra los productos y por cada producto, sus extras
		
		//List<OrderDetail> orderDetailList = new ArrayList<OrderDetail>();
		List<ExtraOrderDetail> extraOrderDetail = new ArrayList<ExtraOrderDetail>();
		for(ProductData pd : orderRequest.getProducts()) {
			OrderDetail orderDetail = new OrderDetail();
			orderDetail.setOrder(order);
			orderDetail.setProduct(pd.getProduct());
			
			//orderDetail.setId(lastId);
			totalAmount = totalAmount.add(pd.getProduct().getPrice());
			//lastId = Long.sum(lastId, 1);
			orderDetail = orderDetailRepository.save(orderDetail);
			if(pd.getExtras() != null && !pd.getExtras().isEmpty()) {				
				for(Extra e : pd.getExtras()) {
					ExtraOrderDetail extraOrder = new ExtraOrderDetail();
					extraOrder.setExtra(e);
					extraOrder.setOrderDetail(orderDetail);
					totalAmount = totalAmount.add(e.getPrice());
					extraOrder = extraOrderDetailRepository.save(extraOrder);
					//extraOrderDetail.add(extraOrder);
				}
			}
		}
		//Registro el monto total del pedido
		order.setAmount(totalAmount);
		order = orderRepository.save(order);
		
		//orderDetailList = orderDetailRepository.saveAll(orderDetailList);
		//extraOrderDetail = extraOrderDetailRepository.saveAll(extraOrderDetail);
		
		//Formo la respuesta del servicio
		orderResponse.setAddress(address);
		orderResponse.setClient(client);
		orderResponse.setOrder(order);
		orderResponse.setOrderDetail(orderRequest.getProducts());
		orderResponse.setMessage("Order register successfully");
		orderResponse.setCode(0);
		orderResponse.setStatus(HttpStatus.OK);
		
		return HttpStatus.OK;
	}
	
	private void validateFields(OrderRequest orderRequest) {
		//Validaciones de campos obligatorios y con formato correcto
		
	}
	
}
