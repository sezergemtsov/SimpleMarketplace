info:
  title: Marketplace
  description: Marketplace
  version: 1.0.0
servers:
  - url: 'http://localhost:8081'
    description: back-end marketplace
	
paths:

   authority - all
   
  /login  
  /logout  
  /showroom  
  /by-product?productId=:id&quantity=:q-ty  
  /return?productId=:id   
  /review?productId=:id&text=:text   
  /grade?productId=:id&grade=:grade   
  /notifications  
  /organization-registration?name=:name&description=:description&logo=:logo  
  /product-registration?name=:name&description=:description&keywords=:keywords&params=:params&orgId=:orgId&price=:price&quantity=:quantity  
  
   authority - admin  
   
   /add-balance?userId=:id&balance=:balance  
   /userInfo?userId=:id  
   /get-userInfo  
   /get-registrations-requests  
   /approve-registration?id=:id  
   /notify?userId=:id&header=:text&text=:text  
   /notify-all?header=:text&text=:text  
   /products-all   
   /user-password/set?userId=:id&password=:password    
   /purchases-all  
   /product-change/name?id=:id&name=:name  
   /product-change/description?id=:id&description=:description  
   /product-change/organization?id=:id&orgId=:orgId  
   /product-change/price?id=:id&price=:price  
   /product-change/quantity?id=:id&quantity=:quantity  
   /product-change/discount?id=:id&discountId=:discountId  
   /product-change/review?reviewId=:id&text=:text  
   /product-change/review-delete?reviewId=:id  
   /product-change/params?id=:id&params=:params  
   /product-change/grade?id=:id&grade=:grade  
   
end
