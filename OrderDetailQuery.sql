select 
	ord.id, 				ord.status, 			ord.comments, 
	ord.createDate, 		ord.amount,				cli.id as clientId, 
	cli.name, 				cli.lastName, 			cli.cellphone, 
	cli.mail,				addr.id as addressId, 	addr.street,
	addr.doornumber, 		addr.zipcode, 			addr.floor, 
	addr.door,				st.id as stateId, 		st.state,
	odet.id as detailId,	prod.id as productId, 	prod.code, 
	prod.name, 				prod.description,		ext.id as extraId, 
	ext.code, 				ext.name, 				ext.price
from `Order` as ord
	left join client 		as cli 		on ord.idclient=cli.id
	left join address 		as addr 	on addr.idclient=cli.id
	left join state 		as st 		on st.id=addr.idState
	left join orderdetail 	as odet 	on odet.idOrder=ord.id
	left join product 		as prod 	on prod.id=odet.idproduct
	left join extraorderdetail as extd 	on extd.idorderdetail=odet.id
	left join extra 		as ext 		on ext.id=extd.idextra
where 
	ord.id=9;