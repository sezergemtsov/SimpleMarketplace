package testcase.effective_mobile.services;

import jakarta.transaction.Transactional;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import testcase.effective_mobile.exceptions.InternalServerError;
import testcase.effective_mobile.exceptions.NotFoundException;
import testcase.effective_mobile.models.organization.Organization;
import testcase.effective_mobile.models.organization.OrganizationRegistrationRequest;
import testcase.effective_mobile.models.product.Grade;
import testcase.effective_mobile.models.product.Product;
import testcase.effective_mobile.models.product.ProductDto;
import testcase.effective_mobile.models.product.Review;
import testcase.effective_mobile.models.user.*;
import testcase.effective_mobile.repositories.*;

import java.util.*;

@SuppressWarnings("unused")
@Service
public class ClientService {

    final int mainCommission = 5;

    final OrganizationsRepository organizationsRepository;
    final PurchasingRepository purchasingRepository;
    final StorageRepository storageRepository;
    final ReviewRepository reviewRepository;
    final UsersRepository usersRepository;
    final OrganizationRequestRepository organizationRequestRepository;
    final NotificationsRepository notificationsRepository;
    final DiscountRepository discountRepository;
    final ReturningRepository returningRepository;
    final GradeRepository gradeRepository;


    public ClientService(OrganizationsRepository organizationsRepository, PurchasingRepository purchasingRepository, StorageRepository storageRepository, ReviewRepository reviewRepository, UsersRepository usersRepository, OrganizationRequestRepository organizationRequestRepository, NotificationsRepository notificationsRepository, DiscountRepository discountRepository, ReturningRepository returningRepository, GradeRepository gradeRepository) {
        this.organizationsRepository = organizationsRepository;
        this.purchasingRepository = purchasingRepository;
        this.storageRepository = storageRepository;
        this.reviewRepository = reviewRepository;
        this.usersRepository = usersRepository;
        this.organizationRequestRepository = organizationRequestRepository;
        this.notificationsRepository = notificationsRepository;
        this.discountRepository = discountRepository;
        this.returningRepository = returningRepository;
        this.gradeRepository = gradeRepository;
    }

    @Transactional
    public List<ProductDto> getAllProducts() {
        List<ProductDto> respond = new ArrayList<>();

        List<Product> products = storageRepository.findAll();
        products.forEach(x -> {
            if (!x.isDeleted()) {
                respond.add(convertToProductDto(x));
            }
        });
        return respond;
    }

    @Transactional
    public String buyProduct(long id, long quantity) {
        Optional<Product> product = storageRepository.findById(id);
        if (product.isEmpty()) {
            throw new NotFoundException("Продукт не найден, проверьте id");
        } else if (product.get().isDeleted()) {
            throw new NotFoundException("Продукт не найден, проверьте id");
        } else if (product.get().getQuantity() < quantity) {
            throw new InternalServerError("Количество товара в наличие меньше чем заказано, попробуйте скорректировать количество");
        } else {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            Optional<ShopUser> user = usersRepository.findByNameOfUserIgnoreCase(auth.getName());
            if (user.isEmpty()) {
                throw new InternalServerError("Не удалось подтвердить пользователя");
            } else {
                Purchasing purchasing = Purchasing.builder()
                        .date(new Date().getTime())
                        .product(product.get())
                        .quantity(quantity)
                        .user(user.get())
                        .isReturned(false)
                        .build();
                purchasingRepository.save(purchasing);
                calculateAllBalanceFromSell(purchasing);
                return "Покупка успешно совершена";
            }
        }
    }

    @Transactional
    public String returnProduct(long purchasingId) {
        Optional<Purchasing> purchasing = purchasingRepository.findById(purchasingId);
        if (purchasing.isEmpty()) {
            throw new NotFoundException("Покупка не найдена, проверьте id");
        } else if (purchasing.get().getIsReturned()) {
            throw new InternalServerError("Данная покупка уже возвращена");
        } else {
            Date purchasingDate = new Date(purchasing.get().getDate());
            Date currentDate = new Date();
            if (isReturnPeriod(purchasingDate, currentDate)) {
                purchasingRepository.changeReturning();
                ReturningInfo returning = ReturningInfo.builder()
                        .dateOfReturning(new Date().getTime())
                        .purchasing(purchasing.get())
                        .build();
                returningRepository.save(returning);
                calculateAllBalanceFromReturning(returning);
                return "Покупка успешно возвращена";
            } else {
                return "Возврат невозможен, покупка совершена более одного дня назад";
            }
        }
    }

    @Transactional
    @SuppressWarnings("deprecation")
    private boolean isReturnPeriod(Date date, Date date1) {
        return date.getYear() == date1.getYear()
                & date.getMonth() == date1.getMonth()
                & date.getDate() - date1.getDate() < 2;
    }

    @Transactional
    public String leaveReview(long productId, String reviewText) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Optional<ShopUser> user = usersRepository.findByNameOfUserIgnoreCase(auth.getName());
        if (user.isEmpty()) {
            throw new InternalServerError("Не удалось подтвердить пользователя");
        } else {
            Optional<Purchasing> product = purchasingRepository.getProductById(productId, user.get().getId());
            if (product.isEmpty()) {
                throw new NotFoundException("Вы не можете оставить отзыв на товар, который не был ранее приобретен");
            } else {
                reviewRepository.save(
                        Review.builder()
                                .review(reviewText)
                                .product(product.get().getProduct())
                                .user(user.get())
                                .build()
                );
                return "Отзыв успешно оставлен";
            }
        }
    }

    @Transactional
    public String leaveGrade(long productId, int grade) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Optional<ShopUser> user = usersRepository.findByNameOfUserIgnoreCase(auth.getName());
        if (user.isEmpty()) {
            throw new InternalServerError("Не удалось подтвердить пользователя");
        } else {
            Optional<Purchasing> product = purchasingRepository.getProductById(productId, user.get().getId());
            if (product.isEmpty()) {
                throw new NotFoundException("Вы не можете оставить отзыв на товар, который не был ранее приобретен");
            } else {
                gradeRepository.save(
                        Grade.builder()
                                .product(product.get().getProduct())
                                .user(user.get())
                                .grade(grade)
                                .build()
                );
                return "Оценка успешно сохранена";
            }
        }
    }

    @Transactional
    public List<NotificationDto> checkAllNotifications() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Optional<ShopUser> user = usersRepository.findByNameOfUserIgnoreCase(auth.getName());
        if (user.isEmpty()) {
            throw new InternalServerError("Не удалось подтвердить пользователя");
        } else {
            List<Notification> notifications = notificationsRepository.findAllByUserListContaining(user.get());
            List<NotificationDto> respond = new ArrayList<>();
            notifications.forEach(x -> respond.add(convertToNotificationDto(x)));
            if (respond.isEmpty()) {
                NotificationDto not = NotificationDto.builder()
                        .header("Новых уведомлений нет")
                        .build();
                respond.add(not);
            }
            return respond;
        }
    }

    @Transactional
    public String leaveOrganisationRegistrationRequest(String name, String description, String logo) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Optional<ShopUser> user = usersRepository.findByNameOfUserIgnoreCase(auth.getName());
        if (user.isEmpty()) {
            throw new InternalServerError("Не удалось подтвердить пользователя");
        } else {
            Organization organization = Organization.builder()
                    .name(name)
                    .description(description)
                    .logo(logo)
                    .balance(0d)
                    .creator(user.get())
                    .isFrozen(false)
                    .registered(false)
                    .isDeleted(false)
                    .build();
            organizationsRepository.save(organization);
            organizationRequestRepository.save(OrganizationRegistrationRequest.builder()
                    .organization(organization)
                    .approved(false)
                    .build());
            return "Заявка на регистрацию успешно отправлена";
        }
    }

    @Transactional
    public String registerNewProduct(String name, String description,
                                     String keyWords, String params,
                                     long orgId, long price, long quantity) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Optional<ShopUser> user = usersRepository.findByNameOfUserIgnoreCase(auth.getName());
        if (user.isEmpty()) {
            throw new InternalServerError("Не удалось подтвердить пользователя");
        } else {
            Optional<Organization> organization = organizationsRepository.findById(orgId);
            if (organization.isEmpty()) {
                throw new NotFoundException("Не найдено зарегестрированной организации, проверьте id");
            } else {
                Product product = Product.builder()
                        .name(name)
                        .description(description)
                        .keyWords(keyWords)
                        .parameters(params)
                        .price(price)
                        .organization(organization.get())
                        .quantity(quantity)
                        .build();
                return "Товар успешно зарегестрирован";
            }
        }
    }

    @Transactional
    private NotificationDto convertToNotificationDto(Notification notification) {
        return NotificationDto.builder()
                .date(new Date(notification.getDate()))
                .header(notification.getHeader())
                .text(notification.getText())
                .build();
    }

    @Transactional
    private ProductDto convertToProductDto(Product product) {

        double averGrade = 0d;
        OptionalDouble preGrade = product.getGrades().stream().mapToInt(Grade::getGrade).average();
        if (preGrade.isPresent()) {
            averGrade = preGrade.getAsDouble();
        }

        return ProductDto.builder()
                .id(product.getId())
                .name(product.getName())
                .organization(product.getOrganization().getName())
                .price(product.getPrice())
                .quantity(product.getQuantity())
                .parameters(product.getParameters())
                .reviewList(product.getReviewList())
                .keyWords(product.getKeyWords())
                .description(product.getDescription())
                .discount(product.getDiscount())
                .grade(averGrade)
                .build();

    }

    @Transactional
    private void calculateAllBalanceFromSell(Purchasing purchasing) {

        long priceOfSell = purchasing.getProduct().getPrice() * purchasing.getQuantity();
        long commission = priceOfSell * mainCommission / 100;
        long sellerIncome = priceOfSell - commission;

        Organization seller = purchasing.getProduct().getOrganization();
        Optional<Organization> main = organizationsRepository.findById(1L);
        if (main.isEmpty()) {
            throw new InternalServerError("Не найдено зарегестрированных организаций, проверьте состояние бд");
        } else {
            organizationsRepository.increaseBalance(sellerIncome, seller.getId());
            organizationsRepository.increaseBalance(commission, 1L);
            usersRepository.takeBalance(purchasing.getUser().getId(), priceOfSell);
            storageRepository.buyingProduct(purchasing.getQuantity(),purchasing.getProduct().getId());
        }
    }

    @Transactional
    private void calculateAllBalanceFromReturning(ReturningInfo returning) {
        long priceOfSell = returning.getPurchasing().getProduct().getPrice() * returning.getPurchasing().getQuantity();
        Organization seller = returning.getPurchasing().getProduct().getOrganization();
        ShopUser user = returning.getPurchasing().getUser();
        organizationsRepository.decreaseBalance(priceOfSell, seller.getId());
        usersRepository.addBalance(user.getId(), priceOfSell);
        storageRepository.increaseQuantity(returning.getPurchasing().getQuantity(),returning.getPurchasing().getProduct().getId());
    }

}
