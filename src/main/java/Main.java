import somepackage.SomeBean;

public class Main {
    public static void main(String[] args) {

        Injector injector = new Injector("src/main/resources/config.properties");


        SomeBean someBean = new SomeBean();
        injector.inject(someBean);


        someBean.foo();
    }
}
