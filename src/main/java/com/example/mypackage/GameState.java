package com.example.mypackage;

import java.util.*;

public class GameState {
    protected String currentWord;
    private Set<String> answeredWords;
    private Set<String> incorrectlyAnsweredWords;
    private String lastTranslationAttempt;
    private String lastAnsweredWord;
    private int correctAnswersCount;
    private int incorrectAnswersCount;
    private List<String> incorrectlyAnsweredWordsWithTranslations;
    protected boolean isGameStarted;
    private Map<String, List<String>> WORDS;
    private long chatId;
    private long lastMoveTime;
    private long gameStartedById;
    private String dictionary;
    protected boolean isDictionaryClicked;
    protected boolean isTestClicked;
    protected boolean isBookClicked;
    protected boolean isCoursesClicked;
    public GameState(long chatId, String wordSet) {
        this.chatId = chatId;
        this.currentWord = null;
        this.answeredWords = new HashSet<>();
        this.incorrectlyAnsweredWords = new HashSet<>();
        this.lastTranslationAttempt = null;
        this.lastAnsweredWord = null;
        this.correctAnswersCount = 0;
        this.incorrectAnswersCount = 0;
        this.incorrectlyAnsweredWordsWithTranslations = new ArrayList<>();
        this.isGameStarted = false;
        lastMoveTime = System.currentTimeMillis();
        this.gameStartedById = chatId;
        this.dictionary = dictionary;
        updateWordSet(chatId, wordSet);

    }

    public void updateWordSet (long chatId, String wordSet) {
        if (wordSet.equals("set1")) {
            WORDS = new HashMap<>();
            WORDS.put("Mieszkanie", Arrays.asList("Квартира"));
            WORDS.put("Powierzchnia ", Arrays.asList("площадь ", "площа"));
            WORDS.put("Pokój ", Arrays.asList("комната", "кімната"));
            WORDS.put("Łazienka", Arrays.asList("ванная комната", "ванна кімната", "ванная", "ванна"));
            WORDS.put("Kuchnia ", Arrays.asList("кухня"));
            WORDS.put("Przedpokój", Arrays.asList("прихожая", "передпокій", "гостинная", "вітальня"));
            WORDS.put("Drzwi ", Arrays.asList("двери", "двері"));
            WORDS.put("Sypialnia", Arrays.asList("спальня", "школа"));
            WORDS.put("Balkon", Arrays.asList("балкон "));
            WORDS.put("Umywalka", Arrays.asList("раковина", "умывальник", "умивальник"));
            WORDS.put("Toaleta", Arrays.asList("туалет"));
            WORDS.put("Pralka", Arrays.asList("стиральная машина", "пральна машина", "стиралка", "пралька"));
            WORDS.put("Ekspres", Arrays.asList("кофемашина", "кавова машина"));
            WORDS.put("Szklanki", Arrays.asList("стаканы", "склянки"));
            WORDS.put("Ozdoby", Arrays.asList("украшения", "прикраси"));
            WORDS.put("Pościel", Arrays.asList("постельное белье", "постіль", "постельное", "постель"));
            WORDS.put("Prysznic", Arrays.asList("душ"));
            WORDS.put("Schowek", Arrays.asList("хранилище", "сховище", "тайник"));
            WORDS.put("Właściciel", Arrays.asList("владелец", "власник"));
            WORDS.put("Sprzedający", Arrays.asList("продавец", "продавець"));
            WORDS.put("Kupujący", Arrays.asList("покупатель", "покупець"));
            WORDS.put("Raty", Arrays.asList("рассрочка", "розстрочка"));
            WORDS.put("Podatek", Arrays.asList("налог", "податок"));
            WORDS.put("Koszty", Arrays.asList("расходы", "витрати"));
            WORDS.put("Czynsz", Arrays.asList("арендная плата", "орендна плата"));
            WORDS.put("Opłaty", Arrays.asList("платежи", "платежи"));
            WORDS.put("Kaucja", Arrays.asList("залог", "застав"));
            this.currentWord = null;
            this.answeredWords = new HashSet<>();
            this.incorrectlyAnsweredWords = new HashSet<>();
            this.lastTranslationAttempt = null;
            this.lastAnsweredWord = null;
            this.correctAnswersCount = 0;
            this.incorrectAnswersCount = 0;
            this.incorrectlyAnsweredWordsWithTranslations = new ArrayList<>();
            lastMoveTime = System.currentTimeMillis();
            this.gameStartedById = chatId;
        }
        else if (wordSet.equals("set2")) {
            WORDS = new HashMap<>();
            WORDS.put("Apteka", Arrays.asList("аптека"));
            WORDS.put("Gardło", Arrays.asList("горло"));
            WORDS.put("Ból", Arrays.asList("боль", "біль"));
            WORDS.put("Do ssania ", Arrays.asList("для рассасывания", "для розсмоктування"));
            WORDS.put("Skutecznie", Arrays.asList("эффективно" , "ефективно"));
            WORDS.put("Polecać", Arrays.asList("рекомендовать", "рекомендувати"));
            WORDS.put("Odporność ", Arrays.asList("иммунитет", "імунітет"));
            WORDS.put("Wzmacniać", Arrays.asList("укреплять", "зміцнювати"));
            WORDS.put("Oczywiście", Arrays.asList("естественно", "конечно", "звичайно"));
            WORDS.put("Aktywność fizyczna", Arrays.asList("физическая активность", "фізична активність", "активность физическая", "активність фізична"));
            WORDS.put("Sen", Arrays.asList("сон"));
            WORDS.put("Opakowanie", Arrays.asList("упаковка"));
            WORDS.put("Pudełko", Arrays.asList("коробка"));
            WORDS.put("Karta", Arrays.asList("карта"));
            WORDS.put("Gotówka", Arrays.asList("наличные", "готівка", "наличка"));
            WORDS.put("Zapłacić", Arrays.asList("заплатить", "заплатити"));
            WORDS.put("Paragon", Arrays.asList("чек"));
            WORDS.put("Recepta", Arrays.asList("рецепт"));
            WORDS.put("Składniki", Arrays.asList("ингредиенты", "складові"));
            WORDS.put("Konsultant", Arrays.asList("консультант"));
            WORDS.put("Kosztować", Arrays.asList("стоить", "коштувати"));
            WORDS.put("Owoce", Arrays.asList("фрукти", "фрукты"));
            WORDS.put("Warzywa", Arrays.asList("Овочі", "Овощи"));
            WORDS.put("Aromatyczny", Arrays.asList("Ароматичний", "Ароматный"));
            WORDS.put("Wołowina", Arrays.asList("Яловичина", "Говядина"));
            WORDS.put("Wieprzowina", Arrays.asList("Свинина"));
            WORDS.put("Ryby", Arrays.asList("Риба", "Рыба"));
            WORDS.put("Łosoś", Arrays.asList("Лосось"));
            WORDS.put("Dorsz", Arrays.asList("Тріска", "Треска"));
            WORDS.put("Pstrąg", Arrays.asList("Форель"));
            WORDS.put("Lokalny", Arrays.asList("Місцевий", "Местный"));
            WORDS.put("Źródło", Arrays.asList("Джерело", "Источник"));
            WORDS.put("Sklep", Arrays.asList("Магазин"));
            WORDS.put("Pokazać", Arrays.asList("Показати", "Показать"));
            WORDS.put("Droga", Arrays.asList("Дорога"));
            WORDS.put("Zakup", Arrays.asList("Покупка"));
            WORDS.put("Postępowanie", Arrays.asList("лечение", "меры", "заходи", "лікування"));
            WORDS.put("Uspokoić się", Arrays.asList("успокоиться", "заспокоїтися"));
            WORDS.put("Stres", Arrays.asList("стресс", "стрес"));
            WORDS.put("Wizyta", Arrays.asList("візит","визит", "прием", "консультация", "прийом", "консультація"));
            WORDS.put("Unikać", Arrays.asList("избегать", "уникати"));
            WORDS.put("Zadbać o siebie", Arrays.asList("позаботиться о себе" , "піклуватися про себе"));
            WORDS.put("Serce", Arrays.asList("сердце", "серце"));
            WORDS.put("Rozebrać się ", Arrays.asList("раздеться", "роздягнутися"));
            WORDS.put("Postarać się", Arrays.asList("постараться" , "намагатися"));
            this.currentWord = null;
            this.answeredWords = new HashSet<>();
            this.incorrectlyAnsweredWords = new HashSet<>();
            this.lastTranslationAttempt = null;
            this.lastAnsweredWord = null;
            this.correctAnswersCount = 0;
            this.incorrectAnswersCount = 0;
            this.incorrectlyAnsweredWordsWithTranslations = new ArrayList<>();
            lastMoveTime = System.currentTimeMillis();
            this.gameStartedById = chatId;
        }
        else if (wordSet.equals("set3")) {
            WORDS = new HashMap<>();
            WORDS.put("Galeria handlowa", Arrays.asList("Торговый центр", "Торговий центр"));
            WORDS.put("Restauracja", Arrays.asList("Ресторан", "Ресторан"));
            WORDS.put("Butik", Arrays.asList("Бутик", "Бутік"));
            WORDS.put("Knajpek", Arrays.asList("Ресторанчик"));
            WORDS.put("Odzież", Arrays.asList("Одежда" , "Одяг"));
            WORDS.put("Obuwie", Arrays.asList("Обувь", "Взуття"));
            WORDS.put("Biżuteria ", Arrays.asList("Ювелирные изделия", "Ювелірні вироби", "украшения", "бижутерия", "біжутерія"));
            WORDS.put("Kosmetyki", Arrays.asList("Косметика"));
            WORDS.put("Zabawki", Arrays.asList("Игрушки", "Іграшки"));
            WORDS.put("Księgarnia", Arrays.asList("Книжный магазин", "Книгарня"));
            WORDS.put("Sklep z elektroniką", Arrays.asList("Магазин электроники", "Магазин електроніки"));
            WORDS.put("Bankomat", Arrays.asList("Банкомат"));
            WORDS.put("Karta kredytowa", Arrays.asList("Кредитная карта", "Кредитна карта"));
            WORDS.put("Płatność gotówką", Arrays.asList("Оплата готівкою", "Оплата наличными"));
            WORDS.put("Płatność kartą", Arrays.asList("Оплата картой", "Оплата карткою"));
            WORDS.put("Oferta specjalna", Arrays.asList("Специальное предложение ", "Спеціальна пропозиція", "Oсоблива пропозиція"));
            WORDS.put("Parking", Arrays.asList("Парковка", "Паркинг", "паркінг"));
            WORDS.put("Winda", Arrays.asList("Лифт", "Ліфт"));
            WORDS.put("Bezpłatny wifi", Arrays.asList("Бесплатный Wi-Fi", "Безкоштовний Wi-Fi"));
            WORDS.put("Godziny otwarcia", Arrays.asList("Часы работы", "Години роботи"));
            WORDS.put("Zamknięty sklep", Arrays.asList("Закрытый магазин", "Закритий магазин"));
            WORDS.put("Otwarty sklep", Arrays.asList("Открытый магазин", "Відкритий магазин"));
            WORDS.put("Sklep spożywczy", Arrays.asList("Продуктовый магазин", "Продуктовий магазин"));
            WORDS.put("Sklep odzieżowy", Arrays.asList("Магазин одежды", "Магазин одягу"));
            WORDS.put("Sklep obuwniczy", Arrays.asList("Магазин обуви", "Магазин взуття"));
            WORDS.put("Sezonowa wyprzedaż", Arrays.asList("Сезонная распродажа", "Сезонний розпродаж "));
            WORDS.put("Usługi", Arrays.asList("Услуги", "Послуги"));
            WORDS.put("Koszyk", Arrays.asList("Корзина", "Кошик"));
            WORDS.put("Zwrot", Arrays.asList("Возврат", "Повернення"));
            WORDS.put("Płatność", Arrays.asList("Оплата"));
            WORDS.put("Skanować", Arrays.asList("Сканировать", "Сканувати"));
            WORDS.put("Dostawa", Arrays.asList("Доставка", "Доставка"));
            WORDS.put("Punkt informacyjny", Arrays.asList("Информационный пункт", "Інформаційний пункт"));
            WORDS.put("Winda dla niepełnosprawnych", Arrays.asList("Лифт для инвалидов", "Ліфт для інвалідів"));
            WORDS.put("Przymierzalnia", Arrays.asList("Примерочная", "Примірювальна"));
            WORDS.put("Сiuchy", Arrays.asList("Шмотки"));
            WORDS.put("Składać zamówienie", Arrays.asList("Оформить заказ", "Оформити замовлення"));
            WORDS.put("Złożyć reklamację", Arrays.asList("Подать жалобу", "Подати скаргу"));
            WORDS.put("Wziąć na raty", Arrays.asList("Взять в рассрочку", "Взяти в розстрочку"));
            WORDS.put("Składać wniosek o kredyt", Arrays.asList("Оформить кредит","Оформити кредит"));
            WORDS.put("Odbiór osobisty", Arrays.asList("Самовывоз", "Самовивіз"));
            this.currentWord = null;
            this.answeredWords = new HashSet<>();
            this.incorrectlyAnsweredWords = new HashSet<>();
            this.lastTranslationAttempt = null;
            this.lastAnsweredWord = null;
            this.correctAnswersCount = 0;
            this.incorrectAnswersCount = 0;
            this.incorrectlyAnsweredWordsWithTranslations = new ArrayList<>();
            lastMoveTime = System.currentTimeMillis();
            this.gameStartedById = chatId;
        }
        else if (wordSet.equals("set4")) {
            WORDS = new HashMap<>();
            WORDS.put("Niebo", Arrays.asList("Небо", "Небо"));
            WORDS.put("Ocean", Arrays.asList("Океан"));
            WORDS.put("Las", Arrays.asList("Ліс", "Лес"));
            WORDS.put("Góra", Arrays.asList("Гора"));
            WORDS.put("Plaża", Arrays.asList("Пляж"));
            WORDS.put("Wodospad", Arrays.asList("Водоспад", "Водопад"));
            WORDS.put("Wyspa ", Arrays.asList("Острів", "Остров"));
            WORDS.put("Pustynia", Arrays.asList("Пустеля", "Пустыня"));
            WORDS.put("Lodowiec", Arrays.asList("Льодовик", "Ледник"));
            WORDS.put("Dolina", Arrays.asList("Долина"));
            WORDS.put("Dżungla", Arrays.asList("Джунглі", "Джунгли"));
            WORDS.put("Łąka", Arrays.asList("Лука", "Луг"));
            WORDS.put("Kanion", Arrays.asList("Каньйон", "Каньон"));
            WORDS.put("Wzgórze", Arrays.asList("Пагорб", "Холм"));
            WORDS.put("Jezioro", Arrays.asList("Озеро"));
            WORDS.put("Wulkan", Arrays.asList("Вулкан"));
            WORDS.put("Rafa koralowa", Arrays.asList("Кораловий риф", "Коралловый риф"));
            WORDS.put("Jaskinia", Arrays.asList("Печера", "Пещера"));
            WORDS.put("Bagno", Arrays.asList("Болото"));
            WORDS.put("Wschód słońca", Arrays.asList("Схід сонця", "Восход солнца"));
            WORDS.put("Zachód słońca", Arrays.asList("Захід сонця", "Закат солнца", "Заход солнца"));
            WORDS.put("Tęcza", Arrays.asList("Веселка", "Радуга"));
            WORDS.put("Zorza polarna", Arrays.asList("Полярне сяйво", "Северное сияние"));
            WORDS.put("Zamieć", Arrays.asList("Хуртовина", "Метель"));
            WORDS.put("Huragan", Arrays.asList("Ураган"));
            WORDS.put("Burza z piorunami", Arrays.asList("Гроза"));
            WORDS.put("Grad", Arrays.asList("Град"));
            WORDS.put("Skała", Arrays.asList("Скеля", "Скала"));
            WORDS.put("Polana", Arrays.asList("Поляна"));
            WORDS.put("Gęsty las", Arrays.asList("Густий ліс", "Густой лес"));
            WORDS.put("Woda pitna", Arrays.asList("Питна вода", "Питьевая вода"));
            WORDS.put("Zbiornik wodny", Arrays.asList("Водойма", "Водохранилище"));
            WORDS.put("Jaskółka", Arrays.asList("Ластівка", "Ласточка"));
            WORDS.put("Orzeł", Arrays.asList("Орел", "Орёл"));
            WORDS.put("Słonecznik", Arrays.asList("Соняшник", "Подсолнух"));
            this.currentWord = null;
            this.answeredWords = new HashSet<>();
            this.incorrectlyAnsweredWords = new HashSet<>();
            this.lastTranslationAttempt = null;
            this.lastAnsweredWord = null;
            this.correctAnswersCount = 0;
            this.incorrectAnswersCount = 0;
            this.incorrectlyAnsweredWordsWithTranslations = new ArrayList<>();
            lastMoveTime = System.currentTimeMillis();
            this.gameStartedById = chatId;
        }
        else if (wordSet.equals("set5")) {
            WORDS = new HashMap<>();
            WORDS.put("Brać", Arrays.asList("Брати", "Брать"));
            WORDS.put("Czytać", Arrays.asList("Читати", "Читать"));
            WORDS.put("Nosić", Arrays.asList("Носити", "Носить"));
            WORDS.put("Leżeć", Arrays.asList("Лежати", "Лежать"));
            WORDS.put("Odpoczywać", Arrays.asList("Відпочивати", "Отдыхать"));
            WORDS.put("Gotować", Arrays.asList("Готувати", "Готовить"));
            WORDS.put("Pić", Arrays.asList("Пити", "Пить"));
            WORDS.put("Słuchać", Arrays.asList("Слухати", "Слушать"));
            WORDS.put("Rozmawiać", Arrays.asList("Розмовляти", "Говорить"));
            WORDS.put("Myć", Arrays.asList("Мити", "Мыть"));
            WORDS.put("Śpiewać", Arrays.asList("Співати", "Петь"));
            WORDS.put("Tańczyć", Arrays.asList("Танцювати", "Танцевать"));
            WORDS.put("Malować", Arrays.asList("Малювати", "Рисовать"));
            WORDS.put("Kupować", Arrays.asList("Купувати", "Покупать"));
            WORDS.put("Sprzedawać", Arrays.asList("Продавати", "Продавать"));
            WORDS.put("Wchodzić", Arrays.asList("Входити", "Входить"));
            WORDS.put("Wychodzić", Arrays.asList("Виходити", "Выходить"));
            WORDS.put("Płacić", Arrays.asList("Платити", "Платить"));
            WORDS.put("Pracować", Arrays.asList("Працювати", "Работать"));
            WORDS.put("Śmiać się", Arrays.asList("Сміятися", "Смеяться"));
            WORDS.put("Pisać", Arrays.asList("Писати", "Писать"));
            WORDS.put("Siedzieć", Arrays.asList("Сидіти", "Сидеть"));
            WORDS.put("Stać", Arrays.asList("Стати", "Стоять"));
            WORDS.put("Chodzić", Arrays.asList("Ходити", "Ходить"));
            WORDS.put("Słyszeć", Arrays.asList("Чути", "Слышать"));
            WORDS.put("Patrzeć", Arrays.asList("Дивитися", "Смотреть"));
            WORDS.put("Uśmiechać się", Arrays.asList("Посміхатися", "Улыбаться"));
            WORDS.put("Rozumieć", Arrays.asList("Розуміти", "Понимать"));
            WORDS.put("Kłaść", Arrays.asList("Класти", "Класть"));
            WORDS.put("Grać", Arrays.asList("Грати", "Играть"));
            WORDS.put("Uczyć się", Arrays.asList("Вчитися", "Учиться"));
            WORDS.put("Wypoczywać", Arrays.asList("Відпочивати", "Отдыхать"));
            WORDS.put("Oglądać", Arrays.asList("Дивитися", "Смотреть"));
            WORDS.put("Zaczynać", Arrays.asList("Починати", "Начинать"));
            WORDS.put("Kończyć", Arrays.asList("Закінчувати", "Заканчивать"));
            WORDS.put("Pomagać", Arrays.asList("Допомагати", "Помогать"));
            WORDS.put("Wspierać", Arrays.asList("Підтримувати", "Поддерживать"));
            WORDS.put("Słuchać się", Arrays.asList("Слухатися", "Слушаться"));
            WORDS.put("Spotykać się", Arrays.asList("Зустрічатися", "Встречаться"));
            WORDS.put("Znaleźć", Arrays.asList("Знайти", "Найти"));
            WORDS.put("Znosić", Arrays.asList("Терпіти", "Терпеть"));
            WORDS.put("Dzwonić", Arrays.asList("Дзвонити", "Звонить"));
            WORDS.put("Ściągać", Arrays.asList("Завантажувати", "Скачивать", "Снимать", "Знімати"));
            WORDS.put("Pracować nad", Arrays.asList("Працювати над", "Работать над"));
            WORDS.put("Robić", Arrays.asList("Робити", "Делать"));
            WORDS.put("Czuć się", Arrays.asList("Почуватися", "Чувствоваться"));
            WORDS.put("Biegać", Arrays.asList("Бігати", "Бегать"));
            this.currentWord = null;
            this.answeredWords = new HashSet<>();
            this.incorrectlyAnsweredWords = new HashSet<>();
            this.lastTranslationAttempt = null;
            this.lastAnsweredWord = null;
            this.correctAnswersCount = 0;
            this.incorrectAnswersCount = 0;
            this.incorrectlyAnsweredWordsWithTranslations = new ArrayList<>();
            lastMoveTime = System.currentTimeMillis();
            this.gameStartedById = chatId;
        }
        else if (wordSet.equals("set6")) {
            WORDS = new HashMap<>();
            WORDS.put("W paski", Arrays.asList("Полосатый", "Смугастий"));
            WORDS.put("Tani", Arrays.asList("Дешевий", "Дешёвый"));
            WORDS.put("Drogi", Arrays.asList("Дорогий", "Дорогой"));
            WORDS.put("Bezpieczny", Arrays.asList("Безпечний", "Безопасный"));
            WORDS.put("Nowoczesny", Arrays.asList("Сучасний", "Современный"));
            WORDS.put("Tradycyjny", Arrays.asList("Традиційний", "Традиционный"));
            WORDS.put("Przyjazny", Arrays.asList("Дружній", "Дружелюбный"));
            WORDS.put("Otwarty", Arrays.asList("Відкритий", "Открытый"));
            WORDS.put("Zimny", Arrays.asList("Холодний", "Холодный"));
            WORDS.put("Gorący", Arrays.asList("Гарячий", "Горячий"));
            WORDS.put("Miękki", Arrays.asList("М'який", "Мягкий"));
            WORDS.put("Twardy", Arrays.asList("Твёрдый","Твердий", "Твердый"));
            WORDS.put("W kratkę", Arrays.asList("Клетчатый", "Клітинчастий"));
            WORDS.put("Niski", Arrays.asList("Низький", "Низкий"));
            WORDS.put("Jasny", Arrays.asList("Світлий", "Светлый"));
            WORDS.put("Ciemny", Arrays.asList("Тёмный","Темний", "Темный"));
            WORDS.put("Wąski", Arrays.asList("Вузький", "Узкий"));
            WORDS.put("Szeroki", Arrays.asList("Широкий"));
            WORDS.put("Wygodny", Arrays.asList("Зручний", "Удобный"));
            WORDS.put("Niewygodny", Arrays.asList("Незручний", "Неудобный"));
            WORDS.put("Wspaniały", Arrays.asList("Прекрасний", "Замечательный", "Прекрасный"));
            WORDS.put("Przydatny", Arrays.asList("Корисний", "Полезный"));
            WORDS.put("Krzywy", Arrays.asList("Кривий", "Кривой"));
            WORDS.put("Prosty", Arrays.asList("Прямий", "Прямой"));
            WORDS.put("Niebezpieczny", Arrays.asList("Небезпечний", "Опасный", "Небезопасный"));
            WORDS.put("Zmięty", Arrays.asList("Мятый", "М'ятий"));
            WORDS.put("Właściwy", Arrays.asList("Правильний", "Подходящий"));
            WORDS.put("Nieodpowiedni", Arrays.asList("Невідповідний", "Неподходящий"));
            WORDS.put("Słodki", Arrays.asList("Солодкий", "Сладкий"));
            WORDS.put("Kwaśny", Arrays.asList("Кислий", "Кислый"));
            WORDS.put("Ostry", Arrays.asList("Гострий", "Острый"));
            WORDS.put("Wesoły", Arrays.asList("Веселий", "Весёлый"));
            WORDS.put("Smutny", Arrays.asList("Сумний", "Грустный"));
            WORDS.put("Spokojny", Arrays.asList("Спокійний", "Спокойный"));
            WORDS.put("Hałaśliwy", Arrays.asList("Шумний", "Шумный", "Громкий"));
            WORDS.put("Łatwy", Arrays.asList("Лёгкий", "Легкий", "Легкий"));
            WORDS.put("Prawdziwy", Arrays.asList("Справжній", "Настоящий"));
            WORDS.put("Fałszywy", Arrays.asList("Неправдивий", "Ложный", "Фальшивий"));
            WORDS.put("Szczęśliwy", Arrays.asList("Щасливий", "Счастливый"));
            this.currentWord = null;
            this.answeredWords = new HashSet<>();
            this.incorrectlyAnsweredWords = new HashSet<>();
            this.lastTranslationAttempt = null;
            this.lastAnsweredWord = null;
            this.correctAnswersCount = 0;
            this.incorrectAnswersCount = 0;
            this.incorrectlyAnsweredWordsWithTranslations = new ArrayList<>();
            lastMoveTime = System.currentTimeMillis();
            this.gameStartedById = chatId;
        }
        else if (wordSet.equals("set7")) {
            WORDS = new HashMap<>();
            WORDS.put("Niezbędny", Arrays.asList("необходимый", "необхідний"));
            WORDS.put("Przemoc", Arrays.asList("насилие", "насильство"));
            WORDS.put("Opanowanie", Arrays.asList("владение", "володіння"));
            WORDS.put("Wyczucie", Arrays.asList("чувство", "відчуття"));
            WORDS.put("Konsekwencja", Arrays.asList("последовательность", "послідовність"));
            WORDS.put("Elastyczność", Arrays.asList("гибкость", "гнучкість"));
            WORDS.put("Zadowolenie", Arrays.asList("удовлетворение", "задоволення"));
            WORDS.put("Podatny", Arrays.asList("подверженный", "схильний"));
            WORDS.put("Poza", Arrays.asList("поза"));
            WORDS.put("Wyobraźnia", Arrays.asList("воображение", "уява"));
            WORDS.put("Szacunek", Arrays.asList("уважение", "повага"));
            WORDS.put("Wyjątek", Arrays.asList("исключение", "виключення"));
            WORDS.put("Porównanie", Arrays.asList("сравнение", "порівняння"));
            WORDS.put("Postęp", Arrays.asList("прогресс", "прогрес"));
            WORDS.put("Niedobór", Arrays.asList("нехватка", "дефіцит"));
            WORDS.put("Wiedza", Arrays.asList("знание", "знання"));
            WORDS.put("Rozwiązanie", Arrays.asList("решение", "рішення"));
            WORDS.put("Wyzwanie", Arrays.asList("вызов", "виклик"));
            WORDS.put("Samodzielność", Arrays.asList("самостоятельность", "самостійність"));
            WORDS.put("Zaangażowanie", Arrays.asList("вовлеченность", "залученість"));
            WORDS.put("Ambicja", Arrays.asList("амбиция", "амбіція"));
            WORDS.put("Efektywność", Arrays.asList("эффективность", "ефективність"));
            WORDS.put("Współczucie", Arrays.asList("сочувствие", "співчуття"));
            WORDS.put("Współpraca", Arrays.asList("сотрудничество", "співпраця"));
            WORDS.put("Przeszkoda", Arrays.asList("препятствие", "перешкода"));
            WORDS.put("Doświadczenie", Arrays.asList("опыт", "досвід"));
            WORDS.put("Zaufanie", Arrays.asList("доверие", "довіра"));
            WORDS.put("Wrażliwość", Arrays.asList("чувствительность", "чутливість"));
            WORDS.put("Umiejętność", Arrays.asList("умение", "вміння"));
            WORDS.put("Kreatywność", Arrays.asList("креативность", "креативність"));
            WORDS.put("Zdolność", Arrays.asList("способность", "здібність"));
            WORDS.put("Tożsamość", Arrays.asList("идентичность", "тотожність"));
            WORDS.put("Harmonia", Arrays.asList("гармония", "гармонія"));
            WORDS.put("Inspiracja", Arrays.asList("вдохновение", "натхнення"));
            WORDS.put("Możliwość", Arrays.asList("возможность", "можливість"));
            WORDS.put("Umowa", Arrays.asList("соглашение", "угода"));
            WORDS.put("Wrażenie", Arrays.asList("впечатление", "враження"));
            WORDS.put("Wytrzymałość", Arrays.asList("выносливость", "витривалість"));
            WORDS.put("Optymizm", Arrays.asList("оптимизм", "оптимізм"));
            WORDS.put("Czułość", Arrays.asList("чувствительность", "чутливість"));
            WORDS.put("Upór", Arrays.asList("упрямство", "упертість"));
            WORDS.put("Samokontrola", Arrays.asList("самоконтроль", "самоконтроль"));
            WORDS.put("Władza", Arrays.asList("власть", "влада"));
            WORDS.put("Nadzieja", Arrays.asList("надежда", "надія"));
            WORDS.put("Szczerość", Arrays.asList("искренность", "щирість"));
            WORDS.put("Ambitny", Arrays.asList("амбициозный", "амбіційний"));
            WORDS.put("Przedsiębiorca", Arrays.asList("предприниматель", "підприємець"));
            WORDS.put("Lojalność", Arrays.asList("лояльность", "лояльність"));
            WORDS.put("Odwaga", Arrays.asList("смелость", "сміливість"));
            WORDS.put("Skromność", Arrays.asList("скромность", "скромність"));
            WORDS.put("Odpowiedzialność", Arrays.asList("ответственность", "відповідальність"));
            WORDS.put("Wyczucie humoru", Arrays.asList("чувство юмора", "почуття гумору"));
            WORDS.put("Cierpliwość", Arrays.asList("терпение", "терплячість"));
            WORDS.put("Twórczy", Arrays.asList("творческий", "творчий"));
            WORDS.put("Intuicyjny", Arrays.asList("интуитивный", "інтуїтивний"));
            this.currentWord = null;
            this.answeredWords = new HashSet<>();
            this.incorrectlyAnsweredWords = new HashSet<>();
            this.lastTranslationAttempt = null;
            this.lastAnsweredWord = null;
            this.correctAnswersCount = 0;
            this.incorrectAnswersCount = 0;
            this.incorrectlyAnsweredWordsWithTranslations = new ArrayList<>();
            lastMoveTime = System.currentTimeMillis();
            this.gameStartedById = chatId;

        }
        }

    public String getDictionary() {
        return dictionary;
    }
    public void setDictionary(String dictionary) {
        this.dictionary = dictionary;
    }
    public long getGameStartedById() {
        return gameStartedById;
    }

    public void setGameStartedById(Long gameStartedById) {
        this.gameStartedById = gameStartedById;
    }

    public void setChatId(long chatId) {
        this.chatId = chatId;
    }
    public long getChatId () {
        return chatId;
    }


    public Map<String, List<String>> getWORDS () {
        return WORDS;
    }

    public long getLastInteractionTime() {
        return lastMoveTime;
    }

    public void setLastInteractionTime(long lastMoveTime) {
        this.lastMoveTime = lastMoveTime;
    }

    public boolean isGameStarted(long chatId) {
        return isGameStarted;
    }

    public void setGameStarted(boolean gameStarted) {
        isGameStarted = gameStarted;
    }

    public void setDictionaryClicked(boolean dictionaryClicked) {
        isDictionaryClicked = dictionaryClicked;
    }

    public boolean isBookClicked() {
        return isBookClicked;
    }

    public boolean isCoursesClicked() {
        return isCoursesClicked;
    }

    public boolean isDictionaryClicked() {
        return isDictionaryClicked;
    }

    public boolean isTestClicked() {
        return isTestClicked;
    }

    public void setTestClicked(boolean testClicked) {
        isTestClicked = testClicked;
    }

    public void setBookClicked(boolean bookClicked) {
        isBookClicked = bookClicked;
    }

    public void setCoursesClicked(boolean coursesClicked) {
        isCoursesClicked = coursesClicked;
    }

    public String getCurrentWord() {
        return currentWord;
    }
    public List<String> getIncorrectlyAnsweredWordsWithTranslations() {
        return incorrectlyAnsweredWordsWithTranslations;
    }

    public void setIncorrectlyAnsweredWordsWithTranslations(List<String> incorrectlyAnsweredWordsWithTranslations) {
        this.incorrectlyAnsweredWordsWithTranslations = incorrectlyAnsweredWordsWithTranslations;
    }

    public void setCurrentWord(String currentWord) {
        this.currentWord = currentWord;
    }

    public Set<String> getAnsweredWords() {
        return answeredWords;
    }

    public void setAnsweredWords(Set<String> answeredWords) {
        this.answeredWords = answeredWords;
    }


    public Set<String> getIncorrectlyAnsweredWords() {
        return incorrectlyAnsweredWords;
    }

    public void setIncorrectlyAnsweredWords(Set<String> incorrectlyAnsweredWords) {
        this.incorrectlyAnsweredWords = incorrectlyAnsweredWords;
    }

    public String getLastTranslationAttempt() {
        return lastTranslationAttempt;
    }

    public void setLastTranslationAttempt(String lastTranslationAttempt) {
        this.lastTranslationAttempt = lastTranslationAttempt;
    }

    public String getLastAnsweredWord() {
        return lastAnsweredWord;
    }

    public void setLastAnsweredWord(String lastAnsweredWord) {
        this.lastAnsweredWord = lastAnsweredWord;
    }

    public int getCorrectAnswersCount() {
        return correctAnswersCount;
    }

    public void setCorrectAnswersCount(  int correctAnswersCount) {
        this.correctAnswersCount = correctAnswersCount;
    }

    public int getIncorrectAnswersCount() {
        return incorrectAnswersCount;
    }

    public void setIncorrectAnswersCount(int incorrectAnswersCount) {
        this.incorrectAnswersCount = incorrectAnswersCount;
    }
}