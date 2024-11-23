import somepackage.annotations.AutoInjectable;

import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Properties;

public class Injector {
    private Properties properties = new Properties();

    // Загрузка файла настроек
    public Injector(String propertiesPath) {
        try (FileInputStream fis = new FileInputStream(propertiesPath)) {
            properties.load(fis);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Метод для инъекции зависимостей
    public <T> T inject(T obj) {
        Class<?> clazz = obj.getClass();
        Field[] fields = clazz.getDeclaredFields();

        for (Field field : fields) {
            if (field.isAnnotationPresent(AutoInjectable.class)) {
                String interfaceName = field.getType().getName();
                String implClassName = properties.getProperty(interfaceName);

                if (implClassName != null) {
                    try {
                        // Создание экземпляра реализации
                        Class<?> implClass = Class.forName(implClassName);
                        Object implInstance = implClass.getDeclaredConstructor().newInstance();

                        // Установка значения поля
                        field.setAccessible(true);
                        field.set(obj, implInstance);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return obj;
    }
}
