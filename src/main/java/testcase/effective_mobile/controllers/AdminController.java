package testcase.effective_mobile.controllers;

import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import testcase.effective_mobile.models.organization.OrganizationRegistrationRequest;
import testcase.effective_mobile.models.product.Product;
import testcase.effective_mobile.models.user.PurchasingDto;
import testcase.effective_mobile.models.user.UserDto;
import testcase.effective_mobile.services.AdminService;
import testcase.effective_mobile.services.ClientService;

import java.util.List;

@RestController
@RequestMapping("/")
public class AdminController {
    final AdminService adminService;
    final ClientService clientService;

    public AdminController(AdminService adminService, ClientService clientService) {
        this.adminService = adminService;
        this.clientService = clientService;
    }

    @Secured("ROLE_ADMIN")
    @GetMapping("/add-balance")
    public void addBalance(@RequestParam("userId") long id, @RequestParam("balance") double balance) {
        adminService.addToUserBalance(id, balance);
    }

    @Secured("ROLE_ADMIN")
    @GetMapping("/userInfo")
    public UserDto getUserInfo(@RequestParam("userId") long id) {
       return adminService.getUserInfo(id);
    }

    @Secured("ROLE_ADMIN")
    @GetMapping("/get-userInfo")
    public List<UserDto> getAllUserInfo() {
        return adminService.getUsers();
    }

    @Secured("ROLE_ADMIN")
    @GetMapping("/get-registrations-requests")
    public List<OrganizationRegistrationRequest> getOrganizationsRegistrationRequests() {
        return adminService.getAllRequests();
    }

    @Secured("ROLE_ADMIN")
    @GetMapping("/approve-registration")
    public String approveOrganizationRegistrationRequest(@RequestParam("id") long id) {
        return adminService.approveRequest(id);
    }

    @Secured("ROLE_ADMIN")
    @GetMapping("/notify")
    public void notify(@RequestParam("userId") long id,
                       @RequestParam("header") String header,
                       @RequestParam("text") String text) {
        adminService.sendNotification(header, text, id);
    }

    @Secured("ROLE_ADMIN")
    @GetMapping("/notify-all")
    public void notifyAll(@RequestParam("header") String header, @RequestParam("text") String text) {
        adminService.sendNotificationToAll(header, text);
    }

    @Secured("ROLE_ADMIN")
    @GetMapping("/products-all")
    public List<Product> allProducts() {
        return adminService.getAllProducts();
    }

    @Secured("ROLE_ADMIN")
    @GetMapping("/user-password/set")
    public String updateUserPassword(@RequestParam("userId") long id, @RequestParam("password") String password) {
        return adminService.setUserPassword(id, password);
    }

    @Secured("ROLE_ADMIN")
    @GetMapping("/purchases-all")
    public List<PurchasingDto> allPurchases() {
        return adminService.getAllPurchasingList();
    }

    @Secured("ROLE_ADMIN")
    @GetMapping("/product-change/name")
    public void changeProductName(@RequestParam("id") long id, @RequestParam("name") String name) {
        adminService.changeProductName(id,name);
    }

    @Secured("ROLE_ADMIN")
    @GetMapping("/product-change/description")
    public void changeProductDescriptions(@RequestParam("id") long id, @RequestParam("description") String description) {
        adminService.changeProductName(id,description);
    }

    @Secured("ROLE_ADMIN")
    @GetMapping("/product-change/organization")
    public void changeProductOrganization(@RequestParam("id") long id, @RequestParam("orgId") long orgId) {
        adminService.changeProductOrganization(id, orgId);
    }

    @Secured("ROLE_ADMIN")
    @GetMapping("/product-change/price")
    public void changeProductPrice(@RequestParam("id") long id, @RequestParam("price") long price) {
        adminService.changeProductPrice(id, price);
    }

    @Secured("ROLE_ADMIN")
    @GetMapping("/product-change/quantity")
    public void changeProductQuantity(@RequestParam("id") long id, @RequestParam("quantity") long quantity) {
        adminService.changeProductQuantity(id, quantity);
    }

    @Secured("ROLE_ADMIN")
    @GetMapping("/product-change/discount")
    public void changeProductDiscount(@RequestParam("id") long id, @RequestParam("discountId") long discountId) {
        adminService.changeProductDiscount(id,discountId);
    }

    @Secured("ROLE_ADMIN")
    @GetMapping("/product-change/review")
    public void changeProductReview(@RequestParam("reviewId") long id, @RequestParam("text") String text) {
        adminService.changeProductReview(id, text);
    }

    @Secured("ROLE_ADMIN")
    @GetMapping("/product-change/review-delete")
    public void changeProductReviewRemove(@RequestParam("reviewId") long id) {
        adminService.deleteProductReview(id);
    }

    @Secured("ROLE_ADMIN")
    @GetMapping("/product-change/params")
    public void changeProductParams(@RequestParam("id") long id, @RequestParam("params") String params) {
        adminService.changeProductParameters(id, params);
    }

    @Secured("ROLE_ADMIN")
    @GetMapping("/product-change/grade")
    public void changeProductDiscount(@RequestParam("id") long id, @RequestParam("grade") int grade) {
        adminService.changeProductGrade(id, grade);
    }

}
