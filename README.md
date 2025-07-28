# Funny Combination - Android Game

Це Android гра, розроблена з використанням Jetpack Compose та MVVM архітектури, де гравці повинні повторювати послідовність емодзі.

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

## Архітектура MVVM

Проект використовує сучасну MVVM (Model-View-ViewModel) архітектуру:

### Структура проекту
```
app/src/main/java/com/example/funnycombination/
├── data/                    # Data Layer
│   ├── AppDatabase.kt      # Room Database
│   ├── HighScoreDao.kt     # Data Access Object
│   ├── HighScoreEntity.kt  # Entity
│   └── HighScoreRepository.kt # Repository
├── presentation/            # Presentation Layer
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
│   │   ├── NavigationViewModel.kt
│   │   └── HighScoreViewModelFactory.kt
│   ├── state/              # State Classes
│   │   └── GameState.kt
│   ├── event/              # Event Classes
│   │   └── GameEvents.kt
│   └── theme/              # UI Theme
│       ├── Theme.kt
│       ├── Color.kt
│       └── Type.kt
└── di/                     # Dependency Injection
```

### MVVM Компоненти

#### ViewModels
- **GameViewModel** - управляє станом гри та логікою
- **HighScoreViewModel** - управляє high score системою
- **GameOverViewModel** - управляє екраном завершення гри
- **NavigationViewModel** - управляє навігацією між екранами

#### State Management
- **GameScreenState** - стан екрану гри
- **HighScoreState** - стан high score екрану
- **GameOverState** - стан екрану завершення гри
- **NavigationState** - стан навігації

#### Event Handling
- **GameEvent** - події гри (StartGame, OnEmojiClick, тощо)
- **HighScoreEvent** - події high score (LoadHighScores, AddHighScore, тощо)
- **GameOverEvent** - події завершення гри

## Технології

- **Jetpack Compose** - UI фреймворк
- **MVVM Architecture** - архітектурний патерн
- **Room Database** - локальне зберігання даних
- **ViewModel** - управління станом
- **Navigation Compose** - навігація між екранами
- **Coroutines** - асинхронні операції
- **Material Design 3** - дизайн система
- **StateFlow** - реактивний потік даних

## Останні зміни

### Архітектурні зміни
- **Повна реорганізація на MVVM** - чіткий поділ на слої
- **State Management** - централізоване управління станом
- **Event-Driven Architecture** - подієво-орієнтована архітектура
- **Separation of Concerns** - розділення відповідальності

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

## Переваги MVVM архітектури

### Тестування
- Легке тестування ViewModels
- Ізольовані компоненти
- Можливість мокати залежності

### Підтримка
- Чітка структура коду
- Легке додавання нових функцій
- Розділення відповідальності

### Масштабованість
- Модульна архітектура
- Легке розширення функціональності
- Готовність до Dependency Injection

## Ліцензія

Цей проект розроблений для навчальних цілей. 