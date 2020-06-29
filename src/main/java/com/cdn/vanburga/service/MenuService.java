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

import com.cdn.vanburga.constant.OrderConstant;
import com.cdn.vanburga.constant.ResponseCode;
import com.cdn.vanburga.exception.FieldTypeException;
import com.cdn.vanburga.exception.MissingFieldException;
import com.cdn.vanburga.exception.ServiceException;
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
import com.cdn.vanburga.util.LocalDateTimeAttributeConverter;

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
		
	public HttpStatus getAllCategories(CategoryResponse response) {
		logger.info("Searching all categories");
		List<Category> categoriesList = categoryRepository.findAll();
		response.setCategories(categoriesList);
		response.setStatus(HttpStatus.OK.value());
		response.setCode(ResponseCode.FOUND.fieldNumber());
		response.setMessage(ResponseCode.FOUND.fieldName());
		
		return HttpStatus.OK;
		
	}
	
	public List<State> getStateList() {
		
		this.stateList = stateRepository.findAll();
		
		return this.stateList;
	}
	
	public HttpStatus getProductsByCategory(ProductResponse response, Long id) {
		logger.info("Searching product list by category id [" + id.toString() + "]");
		Optional<List<Product>> productList = productRepository.findByCategory(new Category(id));
		
		if (!productList.isPresent()) {
			logger.warn("No product found");
			response.setStatus(HttpStatus.NOT_FOUND.value());
			response.setCode(ResponseCode.NOT_FOUND.fieldNumber());
			response.setMessage(ResponseCode.NOT_FOUND.fieldName());
			return HttpStatus.NOT_FOUND;
		}
		List<ProductData> productDataList = new ArrayList<ProductData>();
		for(Product p : productList.get()) {
			Optional<List<ProductExtra>> productExtraList = productExtraRepository.findByProductExtraProduct(p.getId());
			if(productExtraList.isPresent()) {	
				logger.debug("Extras found - size: " + productList.get().size());
				List<Extra> extras = new ArrayList<Extra>();
				productExtraList.get().stream().forEach(e -> extras.add(e.getProductExtra().getExtra()));
				p.setExtras(extras);
			}
		}
		response.setProducts(productList.get());
		response.setCode(ResponseCode.FOUND.fieldNumber());
		response.setStatus(HttpStatus.OK.value());
		response.setMessage(ResponseCode.FOUND.fieldName());
		return HttpStatus.OK;
		
	}
	
	
	public HttpStatus getProduct(ProductResponse response, Long id) {
		logger.info("Searching product id [" + id.toString() + "]");
		Optional<Product> product = productRepository.findById(id);
		
		if (product.isPresent()) {
			logger.debug("Product found - name: [" + product.get().getName() +"] Searching Extras of this product " );
			Optional<List<ProductExtra>> productList = productExtraRepository.findByProductExtraProduct(product.get().getId());
			if(productList.isPresent()) {	
				logger.debug("Extras found - size: " + productList.get().size());
				List<Extra> extras = new ArrayList<Extra>();
				productList.get().stream().forEach(e -> extras.add(e.getProductExtra().getExtra()));
				product.get().setExtras(extras);
			}
			response.setProduct(product.get());
			response.setCode(ResponseCode.FOUND.fieldNumber());
			response.setStatus(HttpStatus.OK.value());
			response.setMessage(ResponseCode.FOUND.fieldName());
			return HttpStatus.OK;
		}
		logger.warn("product with id "+ id.toString() +" not found");
		response.setStatus(HttpStatus.NOT_FOUND.value());
		response.setCode(ResponseCode.NOT_FOUND.fieldNumber());
		response.setMessage(ResponseCode.NOT_FOUND.fieldName());
		
		return HttpStatus.NOT_FOUND;
		
	}
	
	@Transactional
	public HttpStatus createOrder(OrderRequest orderRequest, OrderResponse orderResponse) {
		
		logger.info("Creating order");
		
		try {
			validateOrderRequestCreate(orderRequest);
		
		
			//REGISTRA CLIENTE
			Client client = new Client();
			client.setName(orderRequest.getClient().getName());
			client.setLastName(orderRequest.getClient().getLastName());
			client.setMail(orderRequest.getClient().getMail());
			client.setCellphone(orderRequest.getClient().getCellphone());
			client = clientRepository.save(client);
			
			//Registra la direccion
			Address address = new Address();
			if(orderRequest.getDelivery().booleanValue()) {				
				address.setClient(client);
				address.setDoor(orderRequest.getClient().getAddress().getDoor());
				address.setDoorNumber(orderRequest.getClient().getAddress().getDoorNumber());
				address.setFloor(orderRequest.getClient().getAddress().getFloor());
				if(stateList == null || stateList.isEmpty()) {
					this.stateList = stateRepository.findAll();
				}
				address.setState(stateList.stream().filter(s -> s.getId().longValue() == orderRequest.getClient().getAddress().getState().longValue()).findAny().get());
				address.setStreet(orderRequest.getClient().getAddress().getStreet());
				address.setZipCode(orderRequest.getClient().getAddress().getZipCode());
				address = addressRepository.save(address);
			}
			
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
			for(Product pd : orderRequest.getProducts()) {
				OrderDetail orderDetail = new OrderDetail();
				orderDetail.setOrder(order);
				orderDetail.setProduct(pd);
				
				totalAmount = totalAmount.add(pd.getPrice());
				orderDetail = orderDetailRepository.save(orderDetail);
				if(pd.getExtras() != null && !pd.getExtras().isEmpty()) {				
					for(Extra e : pd.getExtras()) {
						ExtraOrderDetail extraOrder = new ExtraOrderDetail();
						extraOrder.setExtra(e);
						extraOrder.setOrderDetail(orderDetail);
						extraOrder.setQuantity(1);
						totalAmount = totalAmount.add(e.getPrice());
						extraOrder = extraOrderDetailRepository.save(extraOrder);
					}
				}
			}
			//Registro el monto total del pedido
			order.setAmount(totalAmount);
			order.setStatus(OrderConstant.PENDING);
			order = orderRepository.save(order);
			
			
			//Formo la respuesta del servicio
			orderResponse.setAddress(address);
			orderResponse.setOrder(order);
			orderResponse.setOrderDetail(orderRequest.getProducts());
			orderResponse.setMessage(ResponseCode.OK.fieldName());
			orderResponse.setCode(ResponseCode.OK.fieldNumber());
			orderResponse.setStatus(HttpStatus.OK.value());
			
		} catch (MissingFieldException e1) {
		
			orderResponse.setCode(ResponseCode.MISSING_FIELD.fieldNumber());
			orderResponse.setStatus(HttpStatus.BAD_REQUEST.value());
			orderResponse.setMessage(ResponseCode.MISSING_FIELD.fieldName() + e1.getDescriptionString());
			return HttpStatus.BAD_REQUEST;
		
		}catch (ServiceException e1) {
			
			orderResponse.setCode(ResponseCode.ERROR_PROCESS.fieldNumber());
			orderResponse.setStatus(HttpStatus.BAD_REQUEST.value());
			orderResponse.setMessage(ResponseCode.ERROR_PROCESS.fieldName());
			return HttpStatus.BAD_REQUEST;
			
		} catch(Exception e) {
		
			orderResponse.setCode(ResponseCode.ERROR_PROCESS.fieldNumber());
			orderResponse.setStatus(HttpStatus.BAD_REQUEST.value());
			orderResponse.setMessage(ResponseCode.ERROR_PROCESS.fieldName());
			return HttpStatus.BAD_REQUEST;
			
		}
		
		return HttpStatus.OK;
	}
	
	public HttpStatus updateOrder(OrderRequest orderRequest, OrderResponse orderResponse) {
		
		logger.info("Updating order");
		
		try {
			validateOrderRequestUpdate(orderRequest);
		
		Optional<Order> order = orderRepository.findById(orderRequest.getOrderId());
		
		if(!order.isPresent()) {
			orderResponse.setMessage("Order not found");
			orderResponse.setCode(404);
			orderResponse.setStatus(HttpStatus.NOT_FOUND.value());
			return HttpStatus.NOT_FOUND;
		}
		//TODO: Validar que el cambio de estado sea correcto. no podemos pasar de "canceled" a "Done" entre otras convinetas
		order.get().setStatus(orderRequest.getOrderStatus());
		
		orderResponse.setOrder(orderRepository.save(order.get()));
		orderResponse.setMessage("Order updated");
		orderResponse.setCode(0);
		orderResponse.setStatus(HttpStatus.OK.value());
		
		
		} catch (MissingFieldException e1) {
			
			orderResponse.setCode(ResponseCode.MISSING_FIELD.fieldNumber());
			orderResponse.setStatus(HttpStatus.BAD_REQUEST.value());
			orderResponse.setMessage(ResponseCode.MISSING_FIELD.fieldName() + e1.getDescriptionString());
			return HttpStatus.BAD_REQUEST;
		
		}catch (ServiceException e1) {
			
			orderResponse.setCode(ResponseCode.ERROR_PROCESS.fieldNumber());
			orderResponse.setStatus(HttpStatus.BAD_REQUEST.value());
			orderResponse.setMessage(ResponseCode.ERROR_PROCESS.fieldName());
			return HttpStatus.BAD_REQUEST;
			
		} catch(Exception e) {
		
			orderResponse.setCode(ResponseCode.ERROR_PROCESS.fieldNumber());
			orderResponse.setStatus(HttpStatus.BAD_REQUEST.value());
			orderResponse.setMessage(ResponseCode.ERROR_PROCESS.fieldName());
			return HttpStatus.BAD_REQUEST;
			
		}
		return HttpStatus.OK;
	}
	
	public HttpStatus getOrder(Long orderId, OrderResponse orderResponse) {
		
		Optional<Order> order = orderRepository.findById(orderId);
		
		if(order.isPresent()) {
			Optional<Address> address = addressRepository.findByClient(order.get().getClient()); 
			Optional<List<OrderDetail>> orderDetailList = orderDetailRepository.findByOrder(order.get());
			Optional<List<ExtraOrderDetail>> extraOrderDetailList = extraOrderDetailRepository.findByOrder(order.get());
			List<Extra> extras = new ArrayList<Extra>();
			List<Product> productDataList = new ArrayList<Product>();
			if(orderDetailList.isPresent()) {
				for (OrderDetail od : orderDetailList.get()) {
					if(extraOrderDetailList.isPresent()) {		
						for(ExtraOrderDetail ex : extraOrderDetailList.get()) {
							if(ex.getOrderDetail().getId() == od.getId()) {
								extras.add(ex.getExtra());
							}
						}
					}
					od.getProduct().setExtras(extras);
					productDataList.add(od.getProduct());
					extras = new ArrayList<Extra>();
				}
			}
			orderResponse.setOrder(order.get());
			orderResponse.setAddress(address.get());
			orderResponse.setOrderDetail(productDataList);
			
		}else {
			orderResponse.setMessage(ResponseCode.NOT_FOUND.fieldName());
			orderResponse.setCode(ResponseCode.NOT_FOUND.fieldNumber());
			orderResponse.setStatus(HttpStatus.NOT_FOUND.value());
			return HttpStatus.NOT_FOUND;
		}
		
		orderResponse.setMessage(ResponseCode.FOUND.fieldName() );
		orderResponse.setCode(ResponseCode.FOUND.fieldNumber());
		orderResponse.setStatus(HttpStatus.OK.value());
		return HttpStatus.OK;
	}
	
	public HttpStatus findOrders(String status,  
			String dateFrom,
			String dateTo,  
			String clientId,OrderResponse orderResponse) {
		
		try {
			
			validateOrderSearch(status, dateFrom, dateTo, clientId);
			
			LocalDateTime from = LocalDateTimeAttributeConverter.deserialize(dateFrom);
			//Por defecto si no se envia el campo "dateTo" se setea el campo con la fecha del sistema
			LocalDateTime to = dateTo != null ? LocalDateTimeAttributeConverter.deserialize(dateTo) : LocalDateTime.now();
			Long client = clientId != null ? new Long(clientId) : null;
						
			Optional<List<Order>> orderList = orderRepository.findByParameters(from, to, status, client);
			
			if(!orderList.isPresent()) {
				orderResponse.setMessage(ResponseCode.NOT_FOUND.fieldName());
				orderResponse.setCode(ResponseCode.NOT_FOUND.fieldNumber());
				orderResponse.setStatus(HttpStatus.NOT_FOUND.value());
				return HttpStatus.NOT_FOUND;
			}
			
			orderResponse.setOrders(orderList.get());
			
			orderResponse.setMessage(ResponseCode.FOUND.fieldName() );
			orderResponse.setCode(ResponseCode.FOUND.fieldNumber());
			orderResponse.setStatus(HttpStatus.OK.value());
			
		} catch (FieldTypeException e) {
			
			orderResponse.setCode(ResponseCode.NUMBER_TYPE_ERROR.fieldNumber());
			orderResponse.setStatus(HttpStatus.BAD_REQUEST.value());
			orderResponse.setMessage(ResponseCode.NUMBER_TYPE_ERROR.fieldName());
			return HttpStatus.BAD_REQUEST;
			
		} catch (ServiceException e) {
			
			orderResponse.setCode(ResponseCode.ERROR_PROCESS.fieldNumber());
			orderResponse.setStatus(HttpStatus.BAD_REQUEST.value());
			orderResponse.setMessage(ResponseCode.ERROR_PROCESS.fieldName());
			return HttpStatus.BAD_REQUEST;
		}
		
		return HttpStatus.OK;
	}
	
	
	//***************************************************************************************************
	//						V A L I D A C I O N E S
	//***************************************************************************************************
	private void validateOrderRequestCreate(OrderRequest orderRequest) throws ServiceException{

		//valido todos los objetos obligatorios
		
		if(orderRequest.getClient() == null) throw new MissingFieldException("Client missing").setDescriptionString("Client Object");
		if(orderRequest.getClient().getName() == null) throw new MissingFieldException("Client missing mandatory fields").setDescriptionString("Client.Name Object");
		if(orderRequest.getClient().getCellphone() == null) throw new MissingFieldException("Client missing mandatory fields").setDescriptionString("Client.Cellphone Object");
		if(orderRequest.getClient().getLastName() == null) throw new MissingFieldException("Client missing mandatory fields").setDescriptionString("Client.LastName Object");
		if(orderRequest.getClient().getMail() == null) throw new MissingFieldException("Client missing mandatory fields").setDescriptionString("Client.Mail Object");
		if(orderRequest.getProducts() == null) throw new MissingFieldException("Products missing").setDescriptionString("Products[] Object");
		if(orderRequest.getDelivery() == null) throw new MissingFieldException("Delivery missing").setDescriptionString("Delivery Object");
		if(orderRequest.getDelivery()) {
			if(orderRequest.getClient().getAddress() == null) throw new MissingFieldException("Client.Address missing").setDescriptionString("Client.Address Object");
			if(orderRequest.getClient().getAddress().getStreet() == null) throw new MissingFieldException("Client.Address missing").setDescriptionString("Client.Address Object");
			if(orderRequest.getClient().getAddress().getDoorNumber() == null) throw new MissingFieldException("Client.Address missing").setDescriptionString("Client.Address Object");
			if(orderRequest.getClient().getAddress().getZipCode() == null) throw new MissingFieldException("Client.Address missing").setDescriptionString("Client.Address Object");
			if(orderRequest.getClient().getAddress().getState() == null) throw new MissingFieldException("Client.Address missing").setDescriptionString("Client.Address Object");
		}
		
		
	}
	
	private void validateOrderRequestUpdate(OrderRequest orderRequest) throws ServiceException {
		if(orderRequest.getOrderStatus() == null) throw new MissingFieldException("OrderStatus missing").setDescriptionString("OrderStatus Object");
		if(orderRequest.getOrderId() == null) throw new MissingFieldException("OrderId missing").setDescriptionString("OrderId Object");
	}
	
	private void validateOrderSearch(String status, String dateFrom,String  dateTo, String clientId)throws ServiceException{
		try {
			if(clientId != null) new Long(clientId);
		}catch(Exception e) {
			throw new FieldTypeException("Must be a numeric value");
		}
	}

	
}
