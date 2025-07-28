# Funny Combination - Android Game

Це Android гра, розроблена з використанням Jetpack Compose та чистої MVVM архітектури з трьома шарами (Data, Domain, Presentation) та Dependency Injection через Dagger Hilt, де гравці повинні повторювати послідовність емодзі.

## Функціональність

### Основна гра
- Гравець бачить послідовність емодзі
- Потрібно повторити послідовність правильно
- З кожним рівнем послідовність стає довшою
- Гра закінчується при неправильному повторенні

### Система High Score
- Зберігає тільки **3 найкращих результати**
- Показує дату та результат
- Автоматично оновлюється при нових рекордах
- Можна очистити всі результати

### Екран Game Over
- Показує результат гравця
- Повідомляє, чи потрапив результат у high score
- Красивий дизайн з анімацією
- Кнопки "Грати знову" та "Головне меню"

## Архітектура MVVM з трьома шарами

Проект використовує сучасну чисту архітектуру з трьома шарами та Dependency Injection:

### Структура проекту
```
app/src/main/java/com/example/funnycombination/
├── data/                    # Data Layer
│   ├── local/              # Локальна база даних
│   │   ├── AppDatabase.kt  # Room Database
│   │   ├── dao/            # Data Access Objects
│   │   │   └── HighScoreDao.kt
│   │   └── entity/         # Entities
│   │       └── HighScoreEntity.kt
│   └── repository/         # Repository implementations
│       └── HighScoreRepositoryImpl.kt
├── domain/                 # Domain Layer
│   ├── model/              # Domain models
│   │   ├── HighScore.kt
│   │   └── GameState.kt
│   ├── repository/         # Repository interfaces
│   │   └── HighScoreRepository.kt
│   └── usecase/            # Use Cases
│       ├── GetHighScoresUseCase.kt
│       ├── AddHighScoreUseCase.kt
│       └── ClearHighScoresUseCase.kt
├── presentation/           # Presentation Layer
│   ├── MainActivity.kt     # Main Activity
│   ├── screens/            # UI Screens
│   │   ├── SplashScreen.kt
│   │   ├── MainMenuScreen.kt
│   │   ├── GameScreen.kt
│   │   ├── GameOverScreen.kt
│   │   ├── HighScoreScreen.kt
│   │   └── PrivacyPolicyScreen.kt
│   ├── viewmodels/         # ViewModels
│   │   ├── GameViewModel.kt
│   │   ├── HighScoreViewModel.kt
│   │   ├── GameOverViewModel.kt
│   │   └── NavigationViewModel.kt
│   ├── state/              # State Classes
│   │   └── GameState.kt
│   ├── event/              # Event Classes
│   │   └── GameEvents.kt
│   └── theme/              # UI Theme
│       ├── Theme.kt
│       ├── Color.kt
│       └── Type.kt
├── di/                     # Dependency Injection
│   ├── DatabaseModule.kt   # Database DI
│   ├── RepositoryModule.kt # Repository DI
│   └── UseCaseModule.kt    # Use Cases DI
└── FunnyCombinationApplication.kt # Hilt Application
```

### Шари архітектури

#### Data Layer
- **AppDatabase** - Room база даних
- **HighScoreDao** - Data Access Object для роботи з базою
- **HighScoreEntity** - Entity для бази даних
- **HighScoreRepositoryImpl** - Реалізація репозиторію

#### Domain Layer
- **HighScore** - Доменна модель
- **GameState** - Доменна модель стану гри
- **HighScoreRepository** - Інтерфейс репозиторію
- **Use Cases** - Бізнес-логіка
  - `GetHighScoresUseCase` - отримання high scores
  - `AddHighScoreUseCase` - додавання нового рекорду
  - `ClearHighScoresUseCase` - очищення всіх рекордів

#### Presentation Layer
- **ViewModels** - управління станом UI
- **Screens** - UI компоненти
- **State** - класи стану
- **Events** - події користувача

### Dependency Injection (Dagger Hilt)

#### Модулі DI
- **DatabaseModule** - надає залежності для бази даних
- **RepositoryModule** - надає реалізації репозиторіїв
- **UseCaseModule** - надає Use Cases для бізнес-логіки

#### Компоненти
- **@HiltAndroidApp** - головний компонент додатку
- **@AndroidEntryPoint** - точка входу для Activity
- **@HiltViewModel** - анотація для ViewModels з Hilt
- **@Inject** - автоматична ін'єкція залежностей

## Технології

- **Jetpack Compose** - UI фреймворк
- **MVVM Architecture** - архітектурний патерн
- **Clean Architecture** - чиста архітектура з трьома шарами
- **Dagger Hilt** - Dependency Injection
- **Room Database** - локальне зберігання даних
- **ViewModel** - управління станом
- **Navigation Compose** - навігація між екранами
- **Coroutines** - асинхронні операції
- **Material Design 3** - дизайн система
- **StateFlow** - реактивний потік даних
- **Use Cases** - бізнес-логіка

## Останні зміни

### Архітектурні зміни
- **Повна реорганізація на Clean Architecture** - чіткий поділ на 3 шари
- **Dependency Injection з Dagger Hilt** - автоматична ін'єкція залежностей
- **Use Cases** - бізнес-логіка в окремих класах
- **Domain Models** - незалежні від фреймворків моделі
- **Repository Pattern** - абстракція над даними

### Data Layer
- **Room Database** - локальне зберігання
- **DAO Pattern** - доступ до даних
- **Entity Mapping** - конвертація між Entity та Domain моделями

### Domain Layer
- **Use Cases** - бізнес-логіка
- **Repository Interfaces** - абстракції репозиторіїв
- **Domain Models** - чисті моделі даних

### Presentation Layer
- **ViewModels з Hilt** - автоматична ін'єкція
- **State Management** - централізоване управління станом
- **Event-Driven Architecture** - подієво-орієнтована архітектура

### High Score Screen
- Показує тільки 3 найкращих результати
- Красивий дизайн з медалями (🥇🥈🥉)
- Кольорове кодування місць
- Покращена типографіка

### Game Over Screen
- Повністю перероблений дизайн
- Анімація появи контенту
- Детальне повідомлення про результат
- Спеціальне виділення нових рекордів
- Покращені кнопки з емодзі

### Логіка гри
- Виправлено обчислення рахунку
- Покращена обробка стану гри
- Кращий логінг для діагностики

## Запуск

1. Відкрийте проект в Android Studio
2. Синхронізуйте Gradle файли
3. Запустіть на емуляторі або реальному пристрої

## Переваги нової архітектури

### Тестування
- Легке тестування Use Cases
- Ізольовані компоненти
- Можливість мокати залежності
- Unit тести для бізнес-логіки

### Підтримка
- Чітка структура коду
- Легке додавання нових функцій
- Розділення відповідальності
- Незалежність шарів

### Масштабованість
- Модульна архітектура
- Легке розширення функціональності
- Dependency Injection
- Готовність до мікросервісів

### Dependency Injection
- Автоматична ін'єкція залежностей
- Легке тестування
- Синглтони та скоупи
- Модульність

## Ліцензія

Цей проект розроблений для навчальних цілей. 