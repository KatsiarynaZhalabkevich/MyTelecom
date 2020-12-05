package by.epam.zhalabkevich.my_telecom.service.util;

import by.epam.zhalabkevich.my_telecom.bean.Promotion;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Validator class to validate promotions fields before creating
 *
 * @see Promotion
 */
public class PromotionDataValidator {
    private static final PromotionDataValidator instance = new PromotionDataValidator();

    private PromotionDataValidator() {
    }

    public static PromotionDataValidator getInstance() {
        return instance;
    }

    public static boolean checkDescription(String description) {
        return description != null && description.length() > 30;
    }

    public static boolean checkDates(LocalDate dateStart, LocalDate dateEnd) {
        return dateStart.isBefore(dateEnd);
    }

    public static boolean checkDiscount(double discount) {
        return discount > 0 && discount < 1;
    }

    public static boolean validatePromotion(Promotion promotion) {
        return checkDescription(promotion.getDescription()) &&
                checkDates(promotion.getDateStart(), promotion.getDateEnd()) &&
                checkDiscount(promotion.getDiscount());
    }
}
