package testcase.effective_mobile.services;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import testcase.effective_mobile.exceptions.InternalServerError;
import testcase.effective_mobile.exceptions.NotFoundException;
import testcase.effective_mobile.models.organization.Organization;
import testcase.effective_mobile.models.organization.OrganizationRegistrationRequest;
import testcase.effective_mobile.models.product.Discount;
import testcase.effective_mobile.models.product.Product;
import testcase.effective_mobile.models.product.Review;
import testcase.effective_mobile.models.user.*;
import testcase.effective_mobile.repositories.*;

import java.util.*;

@SuppressWarnings("unused")
@Service
public class AdminService {
    final OrganizationsRepository organizationsRepository;
    final PurchasingRepository purchasingRepository;
    final StorageRepository storageRepository;
    final ReviewRepository reviewRepository;
    final UsersRepository usersRepository;
    final OrganizationRequestRepository organizationRequestRepository;
    final NotificationsRepository notificationsRepository;
    final DiscountRepository discountRepository;
    final GradeRepository gradeRepository;

    public AdminService(OrganizationsRepository organizationsRepository, PurchasingRepository purchasingRepository, StorageRepository storageRepository, ReviewRepository reviewRepository, UsersRepository usersRepository, OrganizationRequestRepository organizationRequestRepository, NotificationsRepository notificationsRepository, DiscountRepository discountRepository,GradeRepository gradeRepository) {
        this.organizationsRepository = organizationsRepository;
        this.purchasingRepository = purchasingRepository;
        this.storageRepository = storageRepository;
        this.reviewRepository = reviewRepository;
        this.usersRepository = usersRepository;
        this.organizationRequestRepository = organizationRequestRepository;
        this.notificationsRepository = notificationsRepository;
        this.discountRepository = discountRepository;
        this.gradeRepository = gradeRepository;
    }

    @Transactional
    public void addToUserBalance(long id, double balance) {
        usersRepository.addBalance(id, balance);
    }

    @Transactional
    public UserDto getUserInfo(long id) throws NotFoundException {
        Optional<ShopUser> user = usersRepository.findById(id);
        UserDto userDto;
        if (user.isEmpty()) {
            throw new NotFoundException("Пользователь не найден");
        } else {
            userDto = convertToUserDto(user.get());
        }
        return userDto;
    }

    @Transactional
    public List<UserDto> getUsers() {
        List<ShopUser> users = usersRepository.findAll();
        List<UserDto> respond = new ArrayList<>();
        for (ShopUser x : users) {
            respond.add(convertToUserDto(x));
        }
        return respond;
    }

    @Transactional
    public List<OrganizationRegistrationRequest> getAllRequests() {
        return organizationRequestRepository.findAll();
    }

    @Transactional
    public List<OrganizationRegistrationRequest> getNotApprovedRequests() {
        return organizationRequestRepository.findAllByApprovedFalse();
    }

    @Transactional
    public String approveRequest(long id) {
        String respond = "Регистрация подтверждена";
        if (!organizationRequestRepository.approvingRequest(id)) {
            throw new InternalServerError("Не удалось подтвердить регистрацию, проверьте id");
        }
        return respond;
    }

    @Transactional
    public void sendNotification(String header, String text, Long... id) {
        List<ShopUser> userList = new ArrayList<>();
        Arrays.stream(id).forEach(x -> {
            Optional<ShopUser> user = usersRepository.findById(x);
            user.ifPresent(userList::add);
        });

        notificationsRepository.save(Notification.builder()
                .header(header)
                .text(text)
                .date(new Date().getTime())
                .userList(userList)
                .build()
        );
    }

    @Transactional
    public void sendNotificationToAll(String header, String text) {
        List<ShopUser> userList = usersRepository.findAll();
        notificationsRepository.save(Notification.builder()
                .header(header)
                .text(text)
                .date(new Date().getTime())
                .userList(userList)
                .build()
        );
    }

    @Transactional
    public List<Product> getAllProducts() {
        return storageRepository.findAll();
    }

    @Transactional
    public void changeProductName(long id, String name) {
        storageRepository.changeName(name, id);
    }

    @Transactional
    public void changeProductDescription(long id, String description) {
        storageRepository.changeDescription(description, id);
    }

    @Transactional
    public void changeProductOrganization(long id, long organizationId) {
        Optional<Organization> organization = organizationsRepository.findById(organizationId);
        if (organization.isEmpty()) {
            throw new InternalServerError("Не удалось найти организацию, проверьте id");
        } else {
            storageRepository.changeOrganization(organization.get(), id);
        }
    }

    @Transactional
    public void changeProductPrice(long id, long price) {
        storageRepository.changePrice(price, id);
    }

    @Transactional
    public void changeProductQuantity(long id, long quantity) {
        storageRepository.changeQuantity(quantity, id);
    }

    @Transactional
    public void changeProductDiscount(long id, long discountId) {
        Optional<Discount> discount = discountRepository.findById(discountId);
        if (discount.isEmpty()) {
            throw new NotFoundException("Скидка не найдена, проверьте id");
        } else {
            storageRepository.changeDiscount(discount.get(), id);
        }
    }

    @Transactional
    public void changeProductReview(long reviewId, String text) {
        Optional<Review> review = reviewRepository.findById(reviewId);
        if (review.isPresent()) {
            reviewRepository.changeReview(reviewId,text);
        } else {
            throw new NotFoundException("Отзыв с таким id не найден");
        }

    }

    @Transactional
    public void deleteProductReview(long reviewId) {
        Optional<Review> review = reviewRepository.findById(reviewId);
        if (review.isPresent()) {
            reviewRepository.markToDelete(reviewId);
        } else {
            throw new NotFoundException("Отзыв с таким id не найден");
        }
    }

    @Transactional
    public void changeProductParameters(long id, String parameters) {
        storageRepository.changeParams(parameters, id);
    }

    @Transactional
    public void changeProductGrade(long id, int grade) {
        gradeRepository.changeGrade(id,grade);
    }

    @Transactional
    public String setUserPassword(long id, String password) {
        String respond = "Пароль успешно изменен";
        if (!usersRepository.setUserNewPassword(id, password)) {
            throw new InternalServerError("Не удалось изменить пароль");
        }
        return respond;
    }

    @Transactional
    public List<PurchasingDto> getAllPurchasingList() {
        List<Purchasing> purchasing = purchasingRepository.findAll();
        List<PurchasingDto> respond = new ArrayList<>();
        purchasing.forEach(x -> respond.add(convertToPurchasingDto(x)));
        return respond;
    }

    @Transactional
    public List<PurchasingDto> getPurchasingListByUserId(long id) {
        List<PurchasingDto> respond = new ArrayList<>();
        Optional<ShopUser> user = usersRepository.findById(id);
        if (user.isPresent()) {
            List<Purchasing> purchasing = purchasingRepository.findByUser(user.get());
            purchasing.forEach(x -> respond.add(convertToPurchasingDto(x)));
        } else {
            throw new NotFoundException("Пользователь не найден, проверьте id");
        }
        return respond;
    }

    @Transactional
    private UserDto convertToUserDto(ShopUser user) {
        return UserDto.builder()
                .username(user.getNameOfUser())
                .id(user.getId())
                .email(user.getEmailOfUser())
                .organizationList(user.getOrganizationList())
                .sellingRight(user.getSellingRight())
                .balance(user.getBalanceOfUser())
                .isBanned(user.getIsBanned())
                .build();
    }

    @Transactional
    private PurchasingDto convertToPurchasingDto(Purchasing purchasing) {
        return PurchasingDto.builder()
                .date(new Date(purchasing.getDate()))
                .username(purchasing.getUser().getNameOfUser())
                .userId(purchasing.getUser().getId())
                .productId(purchasing.getProduct().getId())
                .product(purchasing.getProduct().getName())
                .build();
    }

}
