import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = {FieldDependencyValidator.class})
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface ValidateFieldDependency {
    String message() default "Invalid field dependency";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    String field();

    String dependentField();
}