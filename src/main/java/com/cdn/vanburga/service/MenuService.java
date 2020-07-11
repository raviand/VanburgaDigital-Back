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
import com.cdn.vanburga.repository.ExtraRepository;
import com.cdn.vanburga.repository.OrderDetailRepository;
import com.cdn.vanburga.repository.OrderRepository;
import com.cdn.vanburga.repository.ProductExtraRepository;
import com.cdn.vanburga.repository.ProductRepository;
import com.cdn.vanburga.repository.StateRepository;
import com.cdn.vanburga.util.LocalDateTimeAttributeConverter;
import com.mysql.cj.Constants;

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
	
	@Autowired @Lazy
	private ExtraRepository extraRepository;
	
	private List<State> stateList;
	
	public List<Extra>getExtras(){
		return extraRepository.findAllByOrderByCode();
	}
	
	public HttpStatus getKitchen(KitchenResponse response) {
		
	}
		
	public HttpStatus getAllCategories(CategoryResponse response) {
		logger.info("Searching all categories");
		List<Category> categoriesList = categoryRepository.findAll();
		response.setCategories(categoriesList);
		response.setStatus(HttpStatus.OK.value());
		response.setCode(ResponseCode.FOUND.fieldNumber());
		response.setMessage(ResponseCode.FOUND.fieldName());
		logger.debug("Response: " + response.toString());
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
		List<Product> productResponseList = new ArrayList<Product>();
		for(Product p : productList.get()) {
			if(p.getAvailable()) {
				Optional<List<ProductExtra>> productExtraList = productExtraRepository.findByProductExtraProduct(p.getId());
				List<Extra> extras = new ArrayList<Extra>();
				if(productExtraList.isPresent()) {
					logger.debug("Extras found - size: " + productList.get().size());
					for (ProductExtra e : productExtraList.get()) {
						if(e.getProductExtra().getExtra().getAvailable()) {
							extras.add(e.getProductExtra().getExtra());
						}	
					}
				}
				p.setExtras(extras);
				productResponseList.add(p);
			}
		}
		response.setProducts(productResponseList);
		response.setCode(ResponseCode.FOUND.fieldNumber());
		response.setStatus(HttpStatus.OK.value());
		response.setMessage(ResponseCode.FOUND.fieldName());
		return HttpStatus.OK;
		
	}
	
	
	public HttpStatus getProduct(ProductResponse response, Long id) {
		logger.info("Searching product id [" + id.toString() + "]");
		Optional<Product> product = productRepository.findById(id);
		
		if (product.isPresent()) {
				if(product.get().getAvailable()) {
					logger.debug("Product found - name: [" + product.get().getName() +"] Searching Extras of this product " );
					Optional<List<ProductExtra>> productList = productExtraRepository.findByProductExtraProduct(product.get().getId());
					if(productList.isPresent()) {
						logger.debug("Extras found - size: " + productList.get().size());
						List<Extra> extras = new ArrayList<Extra>();
						for (ProductExtra e : productList.get()) {
							if(e.getProductExtra().getExtra().getAvailable()) {
								extras.add(e.getProductExtra().getExtra());
							}	
						}
						product.get().setExtras(extras);
						
					}
					response.setProduct(product.get());
					response.setCode(ResponseCode.FOUND.fieldNumber());
					response.setStatus(HttpStatus.OK.value());
					response.setMessage(ResponseCode.FOUND.fieldName());
					return HttpStatus.OK;
				}
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
		BigDecimal totalAmount = new BigDecimal(0);
		
		try {
			validateOrderRequestCreate(orderRequest);
		
		
			//REGISTRA CLIENTE
			Client client = new Client();
			client.setName(orderRequest.getClient().getName() + " " + orderRequest.getClient().getLastName());
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
				address.setState(stateList.stream().filter(s -> s.getId().longValue() == orderRequest.getClient().getAddress().getState().getId().longValue()).findAny().get());
				address.setStreet(orderRequest.getClient().getAddress().getStreet());
				address.setReference(orderRequest.getClient().getAddress().getReference());
				address = addressRepository.save(address);
				totalAmount.add(address.getState().getAmount());
			}
			
			//Crea la orden de pedido
			Order order = new Order();
			order.setClient(client);
			order.setDelivery(orderRequest.getDelivery());
			order.setComments(orderRequest.getComment());
			order.setPaymentType(orderRequest.getPaymentType());
			order.setCreateDate(LocalDateTime.now());
			order.setAmount(new BigDecimal(0));
			//Calcular monto total de la orden
			order = orderRepository.save(order);
	
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
						extraOrder.setQuantity(e.getQuantity());
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
			orderResponse.setProducts(orderRequest.getProducts());
			orderResponse.setMessage(ResponseCode.OK.fieldName());
			orderResponse.setCode(ResponseCode.OK.fieldNumber());
			orderResponse.setStatus(HttpStatus.OK.value());
			
			
		} catch (MissingFieldException e1) {
		
			orderResponse.setCode(ResponseCode.MISSING_FIELD.fieldNumber());
			orderResponse.setStatus(HttpStatus.BAD_REQUEST.value());
			orderResponse.setMessage(ResponseCode.MISSING_FIELD.fieldName() + e1.getDescriptionString());
			logger.error("MissingFieldException: " + e1.getDescriptionString());
			return HttpStatus.BAD_REQUEST;
		
		}catch (ServiceException e1) {
			
			orderResponse.setCode(ResponseCode.ERROR_PROCESS.fieldNumber());
			orderResponse.setStatus(HttpStatus.BAD_REQUEST.value());
			orderResponse.setMessage(ResponseCode.ERROR_PROCESS.fieldName());
			logger.error("ServiceException: " + e1.getDescriptionString());
			return HttpStatus.BAD_REQUEST;
			
		} catch(Exception e) {
		
			orderResponse.setCode(ResponseCode.ERROR_PROCESS.fieldNumber());
			orderResponse.setStatus(HttpStatus.BAD_REQUEST.value());
			orderResponse.setMessage(ResponseCode.ERROR_PROCESS.fieldName());
			logger.error("Exception: " + e.getMessage());
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
		
		//Validación de status
		if(!validateOrderStatus(orderRequest, order.get())) {
			logger.info("Current order status: "+ order.get().getStatus() + " updating to: " + orderRequest.getStatus());
			orderResponse.setMessage("Invalid order status: cannot update from " + order.get().getStatus() + " to " + orderRequest.getStatus());
			orderResponse.setCode(400);
			orderResponse.setStatus(HttpStatus.BAD_REQUEST.value());
			return HttpStatus.BAD_REQUEST;
		}
		order.get().setStatus(orderRequest.getStatus());
		if(orderRequest.getDeliverTime() != null) order.get().setDeliverTime(orderRequest.getDeliverTime());
		orderRepository.save(order.get());
		orderResponse.setOrder(order.get());
		orderResponse.setMessage("Order updated");
		orderResponse.setCode(0);
		orderResponse.setStatus(HttpStatus.OK.value());
		
		
		} catch (MissingFieldException e1) {
			
			orderResponse.setCode(ResponseCode.MISSING_FIELD.fieldNumber());
			orderResponse.setStatus(HttpStatus.BAD_REQUEST.value());
			orderResponse.setMessage(ResponseCode.MISSING_FIELD.fieldName() + e1.getDescriptionString());
			logger.error("MissingFieldException: " + e1.getDescriptionString());
			return HttpStatus.BAD_REQUEST;
		
		}catch (ServiceException e1) {
			
			orderResponse.setCode(ResponseCode.ERROR_PROCESS.fieldNumber());
			orderResponse.setStatus(HttpStatus.BAD_REQUEST.value());
			orderResponse.setMessage(ResponseCode.ERROR_PROCESS.fieldName());
			logger.error("ServiceException: " + e1.getDescriptionString());
			return HttpStatus.BAD_REQUEST;
			
		} catch(Exception e) {
		
			orderResponse.setCode(ResponseCode.ERROR_PROCESS.fieldNumber());
			orderResponse.setStatus(HttpStatus.BAD_REQUEST.value());
			orderResponse.setMessage(ResponseCode.ERROR_PROCESS.fieldName());
			logger.error("Exception: " + e.getMessage());
			return HttpStatus.BAD_REQUEST;
			
		}
		return HttpStatus.OK;
	}
	
public HttpStatus cancelOrder(OrderRequest orderRequest, OrderResponse orderResponse) {
		
		logger.info("Cancelling order");
		
		try {
			validateOrderRequestUpdate(orderRequest);
			
		
		Optional<Order> order = orderRepository.findById(orderRequest.getOrderId());
		
		
		if(!order.isPresent()) {
			orderResponse.setMessage("Order not found");
			orderResponse.setCode(404);
			orderResponse.setStatus(HttpStatus.NOT_FOUND.value());
			return HttpStatus.NOT_FOUND;
		}
		
		if(!orderRequest.getStatus().equals(OrderConstant.CANCELLED)) {
			logger.error("Current order status: "+ order.get().getStatus() + ". This endpoint is to CANCEL an order.");
			orderResponse.setMessage("Invalid order status: cannot update from " + order.get().getStatus() + " to " + orderRequest.getStatus());
			orderResponse.setCode(400);
			orderResponse.setStatus(HttpStatus.BAD_REQUEST.value());
			return HttpStatus.BAD_REQUEST;
		}
		
		order.get().setStatus(OrderConstant.CANCELLED);
		orderRepository.save(order.get());
		orderResponse.setOrder(order.get());
		orderResponse.setMessage("Order updated");
		orderResponse.setCode(0);
		orderResponse.setStatus(HttpStatus.OK.value());
		
		
		} catch (MissingFieldException e1) {
			
			orderResponse.setCode(ResponseCode.MISSING_FIELD.fieldNumber());
			orderResponse.setStatus(HttpStatus.BAD_REQUEST.value());
			orderResponse.setMessage(ResponseCode.MISSING_FIELD.fieldName() + e1.getDescriptionString());
			logger.error("MissingFieldException: " + e1.getDescriptionString());
			return HttpStatus.BAD_REQUEST;
		
		}catch (ServiceException e1) {
			
			orderResponse.setCode(ResponseCode.ERROR_PROCESS.fieldNumber());
			orderResponse.setStatus(HttpStatus.BAD_REQUEST.value());
			orderResponse.setMessage(ResponseCode.ERROR_PROCESS.fieldName());
			logger.error("ServiceException: " + e1.getDescriptionString());
			return HttpStatus.BAD_REQUEST;
			
		} catch(Exception e) {
		
			orderResponse.setCode(ResponseCode.ERROR_PROCESS.fieldNumber());
			orderResponse.setStatus(HttpStatus.BAD_REQUEST.value());
			orderResponse.setMessage(ResponseCode.ERROR_PROCESS.fieldName());
			logger.error("Exception: " + e.getMessage());
			return HttpStatus.BAD_REQUEST;
			
		}
		return HttpStatus.OK;
	}
	
	public HttpStatus getOrder(Long orderId, OrderResponse orderResponse) {
		logger.info("Getting order");
		logger.debug("Getting id: " + orderId.toString());
		
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
								ex.getExtra().setQuantity(ex.getQuantity());
								extras.add(ex.getExtra());
							}
						}
					}
					od.getProduct().setExtras(extras);
					productDataList.add(od.getProduct());
					extras = new ArrayList<Extra>();
				}
			}
			order.get().setProducts(productDataList);
			if(address.isPresent()) order.get().getClient().setAddress(address.get());
			orderResponse.setOrder(order.get());
			
		}else {
			orderResponse.setMessage(ResponseCode.NOT_FOUND.fieldName());
			orderResponse.setCode(ResponseCode.NOT_FOUND.fieldNumber());
			orderResponse.setStatus(HttpStatus.NOT_FOUND.value());
			logger.warn("Order not found");
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
			String clientId,
			String name,
			String orderId,
			String state,
			String categoryId,
    		OrderResponse orderResponse) {
		logger.info("Finding orders for status " + status + " between " + dateFrom + " and " + dateTo + " with clienId " + clientId);
		
		try {
			
			validateOrderSearch(status, dateFrom, dateTo, clientId, name, orderId, state);
			
			LocalDateTime from = LocalDateTimeAttributeConverter.deserialize(dateFrom);
			//Por defecto si no se envia el campo "dateTo" se setea el campo con la fecha del sistema
			LocalDateTime to = dateTo != null ? LocalDateTimeAttributeConverter.deserialize(dateTo) : LocalDateTime.now();
			Long client = clientId != null ? new Long(clientId) : null;
			Long idOrder = orderId != null ? new Long(orderId) : null;
						
			Optional<List<Order>> orderList = orderRepository.findByParameters(from, to, status, client, name, idOrder, state);
			
			//Completo las orders con productos
			
			if(!orderList.isPresent()) {
				orderResponse.setMessage(ResponseCode.NOT_FOUND.fieldName());
				orderResponse.setCode(ResponseCode.NOT_FOUND.fieldNumber());
				orderResponse.setStatus(HttpStatus.NOT_FOUND.value());
				logger.warn("No orders found");
				return HttpStatus.NOT_FOUND;
			}
			
			List<Order> resultOrderList = new ArrayList<Order>();
			
			for(Order order : orderList.get()) {
				Optional<Address> address = addressRepository.findByClient(order.getClient()); 
				Optional<List<OrderDetail>> orderDetailList = orderDetailRepository.findByOrder(order);
				Optional<List<ExtraOrderDetail>> extraOrderDetailList = extraOrderDetailRepository.findByOrder(order);
				List<Extra> extras = new ArrayList<Extra>();
				List<Product> productDataList = new ArrayList<Product>();
				if(orderDetailList.isPresent()) {
					for (OrderDetail od : orderDetailList.get()) {
						if(extraOrderDetailList.isPresent()) {		
							for(ExtraOrderDetail ex : extraOrderDetailList.get()) {
								if(ex.getOrderDetail().getId() == od.getId()) {
									ex.getExtra().setQuantity(ex.getQuantity());
									extras.add(ex.getExtra());
								}
							}
						}
						od.getProduct().setExtras(extras);
						productDataList.add(od.getProduct());
						extras = new ArrayList<Extra>();
					}
				}
				
				if(address.isPresent()) order.getClient().setAddress(address.get());
				order.setProducts(productDataList);
				resultOrderList.add(order);
			}
			
			orderResponse.setOrders(resultOrderList);
			orderResponse.setMessage(ResponseCode.FOUND.fieldName() );
			orderResponse.setCode(ResponseCode.FOUND.fieldNumber());
			orderResponse.setStatus(HttpStatus.OK.value());
			
		} catch (FieldTypeException e) {
			logger.warn("FieldTypeException: " + e.getDescriptionString());
			orderResponse.setCode(ResponseCode.NUMBER_TYPE_ERROR.fieldNumber());
			orderResponse.setStatus(HttpStatus.BAD_REQUEST.value());
			orderResponse.setMessage(ResponseCode.NUMBER_TYPE_ERROR.fieldName());
			return HttpStatus.BAD_REQUEST;
			
		} catch (ServiceException e) {
			logger.warn("ServiceException: " + e.getDescriptionString());
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
			//if(orderRequest.getClient().getAddress().getZipCode() == null) throw new MissingFieldException("Client.Address missing").setDescriptionString("Client.Address Object");
			if(orderRequest.getClient().getAddress().getState() == null) throw new MissingFieldException("Client.Address missing").setDescriptionString("Client.Address Object");
		}
		
		
	}
	
	private void validateOrderRequestUpdate(OrderRequest orderRequest) throws ServiceException {
		if(orderRequest.getStatus() == null) throw new MissingFieldException("OrderStatus missing").setDescriptionString("OrderStatus Object");
		if(orderRequest.getOrderId() == null) throw new MissingFieldException("OrderId missing").setDescriptionString("OrderId Object");
	}
	
	private void validateOrderSearch(String status, String dateFrom, String dateTo, String clientId, String name, String orderId, String state)throws ServiceException{
		try {
			if(clientId != null) new Long(clientId);
			if(orderId != null) new Long(orderId);
			
			if(status != null && dateFrom != null && dateTo != null && clientId != null && name != null && orderId != null && state != null) {
				throw new ServiceException("Filters must have a value to filter"); 
			}
		}catch(ServiceException e) {
			logger.error("Filters must have a value to filter");
			throw new ServiceException("Filters must have a value to filter");
		}catch(Exception e) {
			logger.error("orderID must be a numeric value");
			throw new FieldTypeException("orderID must be a numeric value");
		}
	}
	
	private Boolean validateOrderStatus(OrderRequest orderRequest, Order order) {
		//Chequeo el status de la order
		String nextStatus = getNextStatus(order.getStatus());
		String previousStatus = getPreviousStatus(order.getStatus());
		//Devuelvo true si el estado es correcto
		return (nextStatus.equals(orderRequest.getStatus()) || previousStatus.equals(orderRequest.getStatus())); 
	}
	
	private String getNextStatus(String status) {
		switch(status) {
		case OrderConstant.PENDING:
			return OrderConstant.CONFIRM;
		case OrderConstant.CONFIRM:
			return OrderConstant.KITCHEN;
		case OrderConstant.KITCHEN:
			return OrderConstant.DELIVER;
		case OrderConstant.DELIVER:
			return OrderConstant.FINISHED;
		default:
			return "";
			
		}
	}
	
	private String getPreviousStatus(String status) {
		switch(status) {
		case OrderConstant.CONFIRM:
			return OrderConstant.PENDING;
		case OrderConstant.KITCHEN:
			return OrderConstant.CONFIRM;
		case OrderConstant.DELIVER:
			return OrderConstant.KITCHEN;
		case OrderConstant.FINISHED:
			return OrderConstant.DELIVER;
		default:
			return "";
			
		}
	}

	
}
