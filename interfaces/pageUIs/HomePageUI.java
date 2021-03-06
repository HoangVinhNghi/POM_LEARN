package pageUIs;

public class HomePageUI {
    //  static : Biến tĩnh -> có thể truy cập mà không cần phải thông qua instance của class - Dữ liệu của nó sẽ không mất đi - tồn tại từ lúc khởi tạo JVM cho đến khi chạy hết các class.
    // final: sẽ không bị gán lại dữ liệu - static final sẽ được coi là 1 hằng số "const".

    public static final String REGISTER_LINK = "//a[normalize-space()='Register']";
    public static final String LOGIN_LINK = "//a[@class='ico-login']";
    public static final String HEADER_MY_ACCOUNT_LINK = "//div[@class='header-links']//a[text()='My account']";
    public static final String FOOTER_MY_ACCOUNT_LINK = "//div[@class='footer']//a[text()='My account']";
    public static final String FOOTER_SEARCH_LINK = "//div[@class='footer']//a[text()='Search']";

}
