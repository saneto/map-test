import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.lang.reflect.Field;

public class FieldDependencyValidator implements ConstraintValidator<ValidateFieldDependency, Object> {

    private String field;
    private String dependentField;

    @Override
    public void initialize(ValidateFieldDependency constraintAnnotation) {
        this.field = constraintAnnotation.field();
        this.dependentField = constraintAnnotation.dependentField();
    }

    @Override
    public boolean isValid(Object object, ConstraintValidatorContext context) {
        try {
            Field fieldObject = object.getClass().getDeclaredField(field);
            Field dependentFieldObject = object.getClass().getDeclaredField(dependentField);
            fieldObject.setAccessible(true);
            dependentFieldObject.setAccessible(true);

            Object fieldValue = fieldObject.get(object);
            Object dependentFieldValue = dependentFieldObject.get(object);

            if (fieldValue != null && !fieldValue.toString().isEmpty()) {
                if (dependentFieldValue == null || (dependentFieldValue instanceof java.util.Collection && ((java.util.Collection<?>) dependentFieldValue).isEmpty())) {
                    return false;
                }
            }
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}