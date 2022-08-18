package wiki;

import com.codeborne.selenide.*;
import core.BaseTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import static com.codeborne.selenide.Selenide.$$x;

public class WikiTest extends BaseTest {
    private final static String URL= "https://ru.wikipedia.org/wiki/Java";

    @Test
    public void openAllHrefs() {
        Selenide.open(URL);
        ElementsCollection hrefs = $$x("//div[@id='toc']//a[@href]");
        List<String> links = new ArrayList<>();
        //1 Перебераем и дастаем значение атрибута с помощью метода for
//        for (int i=0; i<hrefs.size(); i++ ) {
//            links.add(hrefs.get(i).getAttribute("href"));
//        }

        //2 Перебераем и дастаем значение атрибута с помощью метода for each (перебераем тип элемента из коллекции)
//        for (SelenideElement element : hrefs) {
//            links.add(element.getAttribute("href"));
//        }

        //3 Перебераем и дастаем значение атрибута с помощью STREAM API
            hrefs.stream().forEach(x->links.add(x.getAttribute("href")));

            // Перебераем значение из полученных ссылок

        //1 открытие всех полученных ссылок с помощью Stream api
        links.forEach(Selenide::open);

        //2 Открытие всех ссылок и выполнение Asserts (Получаем текущую ссылку из браузера и сравниваем ее с ссылкой из коллекции)
        for (int i=0; i<links.size(); i++) {
            String listUrl = links.get(i);
            Selenide.open(listUrl);
            String currentUrl = WebDriverRunner.getWebDriver().getCurrentUrl();
            Assertions.assertEquals(currentUrl, listUrl);
        }

        //3 Получаем случайное значение из списка с ссылками, дастаем какието значения и октрывшиеся ссылки убераем из списка
        while (links.size()>0){
            int randomNumber = new Random().nextInt(links.size());
            Selenide.open(links.get(randomNumber));
            links.remove(WebDriverRunner.getWebDriver().getCurrentUrl());
        }

        //4 Работа с STREAM_API (Получим список со всеми ссылками, но вместо ссылок получим дляну этих ссылок (список с числами))
        List<Integer> linksLenght = hrefs.stream().map(x->x.getAttribute("href").length()).collect(Collectors.toList());

    }


}






















