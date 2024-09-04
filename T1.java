import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = ProtocolValidator.class)
@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidateProtocolIfIpIsSet {
    String message() default "Invalid protocol when IP is set";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}