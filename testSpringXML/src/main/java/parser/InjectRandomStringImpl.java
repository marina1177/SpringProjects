package parser;

import java.lang.reflect.Field;
import org.apache.commons.text.RandomStringGenerator;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.util.ReflectionUtils;

public class InjectRandomStringImpl implements BeanPostProcessor {


  @Override
  public Object postProcessBeforeInitialization(Object bean, String beanName)
      throws BeansException {

    Field[] fields = bean.getClass().getDeclaredFields();
    for (Field field : fields){
      InjectionRandomString annotation = field.getAnnotation(InjectionRandomString.class);
      if (annotation != null){
        int length = annotation.length();
        RandomStringGenerator generator = new RandomStringGenerator.Builder()
            .withinRange('a', 'z').build();
        String randomString = generator.generate(length);

        field.setAccessible(true);
        //библиотека спринга, позволяет не обрабатывать исключения
        ReflectionUtils.setField(field, bean, randomString);

      }
    }


    return bean;
  }

  @Override
  public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
    return bean;
  }
}
