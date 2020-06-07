package com.cdn.vanburga.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cdn.vanburga.util.MailSenderUtil;

import lombok.Data;
import lombok.EqualsAndHashCode;
@Data
@Component
@EqualsAndHashCode(callSuper=false)
public class MailSenderService{
	private final Logger logger = LoggerFactory.getLogger(this.getClass());	
		
	@Autowired
	private MailSenderUtil  mailSenderUtil;
	
	public MailSenderService() {}
	
//	public MailSenderService(PaymentOrder paymentOrder, CreatedPaymentOrder createdPayment, PaymentOrderRequest paymentOrderRequest) {
//		this.paymentOrder = paymentOrder;
//		this.createdPayment = createdPayment;
//		this.paymentOrderRequest = paymentOrderRequest;
//	}
	
//	public void sendMail() throws ConfigurationException {
//		try {
//			
//			final MimeMessage mimeMessage = (MimeMessage) mailSenderUtil.getMailSender().createMimeMessage();
//			final MimeMessageHelper message = new MimeMessageHelper(mimeMessage, true);
//			message.setFrom(new InternetAddress(paymentOrder.getMerchantName() +"<"+paymentOrder.getMerchantEmail() +">"));
//			message.setTo(paymentOrderRequest.getPayer().getCustomerEmail());
//			message.setSubject("Recibiste una solicitud de pago");
//			message.setText(buildHtmlMailBody(), true);
//			mailSenderUtil.getMailSender().send(mimeMessage);
//		} catch (Exception e) {
//			throw new ConfigurationException();
//		}
//	}
//	
//	public void sendRefundMail() throws SystemPropertiesException {
//		try {
//			
//			final MimeMessage mimeMessage = (MimeMessage) mailSenderUtil.getMailSender().createMimeMessage();
//			final MimeMessageHelper message = new MimeMessageHelper(mimeMessage, true);
//			message.setFrom(new InternetAddress(paymentOrder.getMerchantName() +"<"+paymentOrder.getMerchantEmail() +">"));
//			message.setTo(paymentOrder.getCustomerEmail());
//			message.setSubject("Devolución aprobada");
//			message.setText(buildHtmlRefundMailBody(), true);
//			mailSenderUtil.getMailSender().send(mimeMessage);
//		} catch (MailException e) {
//			logger.error("Error sending Mail", e);
//		} catch (MessagingException e) {
//			logger.error("Error sending Mail", e);
//		} catch (LocalDateTimeFormatException e) {
//			logger.error("Error sending Mail", e);
//		}
//	}
//	
//	/**
//	 * Genera el mail para ser enviado al player. Luego se registra la fecha de envio en la base
//	 */
//	public void run() {
//		//Preparo el mensaje para enviar el mail
//		String message = generateMailMessage();
//		
//		//Envio el mail al correo recibido en el request
//		mailSenderUtil.sendEmail(paymentOrderRequest.getPayer().getCustomerEmail(), "Recibiste una solicitud de pago", message);
//		paymentOrder.setMailNotificationDate(LocalDateTime.now());
//		paymentOrderRepository.save(paymentOrder);
//		logger.info("Mail was sended to: " + paymentOrderRequest.getPayer().getCustomerEmail());
//	}
//
//	
//	/**
//	 * Genera el cuerpo del mensaje con los datos de la orden
//	 * 
//	 * @param paymentOrder
//	 * @param createdPayment
//	 * @param paymentRequest
//	 * @return
//	 */
//	private String generateMailMessage() {
//		StringBuffer message = new StringBuffer();
//		message.append("Hola " + paymentOrderRequest.getPayer().getCustomerName()+",");
//		message.append("\n\n");
//		message.append(paymentOrderRequest.getMerchantLayoutConfiguration().getMerchantName() + " te envi&oacute; una solicitud de pago. Para realizar el mismo, hacé clic en el siguiente link:");
//		message.append("\n").append(createdPayment.getLink());
//		message.append("\n").append("Concepto: " + paymentOrderRequest.getPaymentOrderData().getConcept());
//		message.append("\n").append("Importe a pagar: " + paymentOrderRequest.getPaymentOrderData().getCurrency() + paymentOrderRequest.getPaymentOrderData().getAmount());
//		message.append("\n").append("Si tenes alguna consulta, comunicate con tu vendedor: " + merchantEmail);
//		
//		message.append("\n\n\n").append("message created by VTOL");
//		message.append("\n\n");
//		message.append("Este mensaje se ha enviado automáticamente, por favor, no respondas. Si ya recibiste este e-mail te pedimos disculpas por las molestias ocasionadas.");
//		return message.toString();
//	}
//	
//	private String buildHtmlMailBody(){
//		StringBuffer mail = new StringBuffer();
//		
//		 int year = Calendar.getInstance().get(Calendar.YEAR);
//	    String brand = "&copy; " + year + " Napse Global";
//	    
//		mail.append("<h4>Hola " + paymentOrderRequest.getPayer().getCustomerName()+",</h4>");
//		mail.append("<p>");
//		mail.append(paymentOrderRequest.getMerchantLayoutConfiguration().getMerchantName() + " te envi&oacute; una solicitud de pago. </p>");
//		mail.append("Acced&eacute; al formulario haciendo <a href=\""+ createdPayment.getLink()+"\">click aqui</a>");
//		mail.append("<p>").append("<strong>Importe a pagar: </strong>" + paymentOrderRequest.getPaymentOrderData().getCurrency() + Double.toString(paymentOrderRequest.getPaymentOrderData().getAmount().doubleValue() / 100)).append("</p>");
//		mail.append("<h4>Detalle del pedido</h4>");
//		mail.append("<p>").append(paymentOrderRequest.getPaymentOrderData().getConcept() + ": " + paymentOrderRequest.getPaymentOrderData().getDescription()).append("</p>");
//		if(paymentOrderRequest.getPaymentOrderData().getItems() != null && !paymentOrderRequest.getPaymentOrderData().getItems().isEmpty()) {
//			mail.append("<table style=\"font-family: \"Trebuchet MS\", Arial, Helvetica, sans-serif;border-collapse: collapse;width: 100%;\">");
//			mail.append("<tr style=\"background-color: #f2f2f2;\">"
//					+ "<th style=\"padding-top: 12px;padding-bottom: 12px;text-align: left;background-color: #4CAF50;color: white;\">Producto</th>"
//					+ "<th style=\"padding-top: 12px;padding-bottom: 12px;text-align: left;background-color: #4CAF50;color: white;\">C&oacute;digo</th>"
//					+ "<th style=\"padding-top: 12px;padding-bottom: 12px;text-align: left;background-color: #4CAF50;color: white;\">Cantidad</th>"
//					+ "<th style=\"padding-top: 12px;padding-bottom: 12px;text-align: left;background-color: #4CAF50;color: white;\">Precio Unitario</th></tr>");
//			for(Item i : paymentOrderRequest.getPaymentOrderData().getItems()) {
//				mail.append("<tr style=\"background-color: #ddd;\">");
//				mail.append("<td style=\"border: 1px solid #ddd; padding: 8px;\">"+i.getDescription()+	"</td>");
//				mail.append("<td style=\"border: 1px solid #ddd; padding: 8px;\">"+i.getId()+			"</td>");
//				mail.append("<td style=\"border: 1px solid #ddd; padding: 8px;\">"+i.getQuantity()+		"</td>");
//				Double unitPrice = new Double(i.getUnitPrice());
//				double realAmount = unitPrice.doubleValue() / 100;
//				mail.append("<td style=\"border: 1px solid #ddd; padding: 8px;\">"+paymentOrderRequest.getPaymentOrderData().getCurrency() +" " + new DecimalFormat("#.0#").format(realAmount)+"</td>");
//				mail.append("</tr>");
//			}
//			mail.append("</table> </br>");
//		}
//		mail.append("<p>").append("Si ten&eacute;s alguna consulta, comunicate con tu vendedor: " + paymentOrderRequest.getMerchant().getMerchantEmail()).append("<p>");
//		mail.append("<p>").append("Si no pod&eacute;s acceder al link, intent&aacute; copiar esta url en tu navegador: " + createdPayment.getLink()).append("<p>");
//		mail.append("</br>");	
//		mail.append("<p>").append("Este mensaje se ha enviado autom&aacute;ticamente, por favor, no respondas. Si ya recibiste este e-mail te pedimos disculpas por las molestias ocasionadas.").append("<p>");
//		mail.append("</br><center>").append(brand).append("</center>");
//		
//		return mail.toString();
//	}
//	
//	private String buildHtmlRefundMailBody() throws LocalDateTimeFormatException{
//		StringBuffer mail = new StringBuffer();
//		int year = Calendar.getInstance().get(Calendar.YEAR);
//	    String brand = "&copy; " + year + " Napse Global";
//	    
//		mail.append("<h4>Hola " + paymentOrder.getCustomerName()+",</h4>");
//		mail.append("<strong>La devoluci&oacute;n fue aprobada.</strong>");
//		Double unitPrice = new Double(paymentOrderRefund.getAmount());
//		double realAmount = unitPrice.doubleValue() / 100;
//		mail.append("<p>").append("Se realiz&oacute; una devoluci&oacute;n por "+paymentOrder.getCurrency() +Double.toString(realAmount) +" con " + paymentOrder.getProviderName()).append("</p>");
//		mail.append("<p>").append("N&uacute;mero de Tarjeta: "+paymentOrder.getCardNumber()).append("</p>");
//		mail.append("<p>").append("N&uacute;mero de orden de pago devuelto: "+paymentOrder.getPaymentOrderId()).append("</p>");
//		mail.append("<p>").append("C&oacute;digo de autorizaci&oacute;n: "+paymentOrderRefund.getAuthorizationCode()).append("</p>");
//		mail.append("<p>").append("Fecha de la transaccion: "+  LocalDateTimeAttributeConverter.serializeNiceFormat(paymentOrderRefund.getRawRefundDate()) ).append("</p>");
//		mail.append("<p>").append("Referencia del vendedor: "+  paymentOrderRefund.getMerchantRefundOrderId() ).append("</p>");
//		mail.append("</br>");
//		mail.append("<p>").append("Vendedor: "+paymentOrder.getMerchantName()).append("</p>");
//		mail.append("<p>").append("Mail vendedor: "+paymentOrder.getMerchantEmail()).append("</p>");
//		mail.append("</br><center>").append(brand).append("</center>");
//		return mail.toString();
//	}
	
}
