package testcase.effective_mobile.controllers;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import testcase.effective_mobile.models.product.ProductDto;
import testcase.effective_mobile.models.user.NotificationDto;
import testcase.effective_mobile.services.AdminService;
import testcase.effective_mobile.services.ClientService;

import java.util.List;

@RestController
@RequestMapping("/")
public class ClientController {

    final AdminService adminService;
    final ClientService clientService;

    public ClientController(AdminService adminService, ClientService clientService) {
        this.adminService = adminService;
        this.clientService = clientService;
    }

    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @GetMapping("/showroom")
    public List<ProductDto> showroom() {
        return clientService.getAllProducts();
    }

    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @GetMapping("/by-product")
    public String buyProduct(@RequestParam("productId") long id, @RequestParam("quantity") long quantity) {
        return clientService.buyProduct(id, quantity);
    }

    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @GetMapping("/return")
    public String returning(@RequestParam("productId") long id) {
        return clientService.returnProduct(id);
    }

    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @GetMapping("/review")
    public String review(@RequestParam("productId") long id, @RequestParam("text") String text) {
        return clientService.leaveReview(id, text);
    }

    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @GetMapping("/grade")
    public String grade(@RequestParam("productId") long id, @RequestParam("grade") int grade) {
        return clientService.leaveGrade(id, grade);
    }

    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @GetMapping("/notifications")
    public List<NotificationDto> notifications() {
        return clientService.checkAllNotifications();
    }

    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @GetMapping("/organization-registration")
    public String organizationRegistration(@RequestParam("name") String name, @RequestParam("description") String description, @RequestParam("logo") String logo) {
        return clientService.leaveOrganisationRegistrationRequest(name, description, logo);
    }

    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @GetMapping("/product-registration")
    public String organizationRegistration(@RequestParam("name") String name,
                                           @RequestParam("description") String description,
                                           @RequestParam("keywords") String keyWords,
                                           @RequestParam("params") String params,
                                           @RequestParam("orgId") long orgId,
                                           @RequestParam("price") long price,
                                           @RequestParam("quantity") long quantity) {
        return clientService.registerNewProduct(name, description, keyWords, params, orgId, price, quantity);
    }
}
