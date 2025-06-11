package POM.WebTest.BoniGarcia.Pages;

public enum GarciaMainPage {
        BONI_GARCIA_MAIN_PAGE("https://bonigarcia.dev/selenium-webdriver-java/");

        private final String url;

        GarciaMainPage(String url) {
                this.url = url;
        }

        public String getUrl() {
                return url;
        }
}
