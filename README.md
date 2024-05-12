# ITMO Java-tech labs (1-4 labs, 5 -> todo)

# Lab-1

## Изучение синтаксиса языка Java и нового окружения.

В рамках лабораторной следует переписать [лабораторную работу №4](https://ronimizy.notion.site/Labs-7375b5d241c347d18616deaeeee2bc48) из прошлого семестра. 

Необходимо использовать [Javadoc](https://www.baeldung.com/javadoc) и сгенерировать html-документацию.

Фреймворк для тестирования рекомендуется [JUnit](https://mvnrepository.com/artifact/org.junit.jupiter/junit-jupiter-api).

Система сборки предоставляется на выбор студента: Gradle/Maven.

 В отличии от последующих, эта лабораторная заливается отдельным проектом (**??**)


# Lab-2

## Нужно написать сервис по учету котиков и их владельцев.

Существующая информация о котиках:
- Имя
- Дата рождения
- Порода
- Цвет (один из заранее заданных вариантов)
- Хозяин
- Список котиков, с которыми дружит этот котик (из представленных в базе)

Существующая информация о хозяевах:
- Имя
- Дата рождения
- Список котиков

Сервис должен реализовывать архитектуру **Сontroller-Service-Dao**.

Вся информация хранится в БД **PostgreSQL**. Для связи с БД должен использоваться **Hibernate**.

Проект должен собираться с помощью Maven или Gradle (на выбор студента). 
Слой доступа к данным и сервисный слой должны являться двумя разными модулями Maven/Gradle. При этом проект должен полностью собираться одной командой.

При тестировании рекомендуется использовать [Mockito](https://mvnrepository.com/artifact/org.mockito/mockito-core), чтобы избежать подключения к реальным базам данных. 

Фреймворк для тестирования рекомендуется [JUnit](https://mvnrepository.com/artifact/org.junit.jupiter/junit-jupiter-api).

В данной лабораторной **нельзя** использовать **Spring** или подобные ему фреймворки.

# Lab-3

## К созданному в прошлой лабораторной сервису добавляется Spring.

Сервис должен предоставлять HTTP интерфейс (**REST API**) для получения информации о конкретных котиках и владельцах и для получения фильтрованной информации (например, получить всех рыжих котиков)

Внимание: недопустимо отдавать через HTTP интерфейс сущности JPA. Рекомендуется создать отдельные оберточные классы.

Сервисы и DAO должны превратиться в Spring Bean’ы с использованием **Dependency Injection (Autowired)**. DAO при этом наследуют [JpaRepository](https://www.baeldung.com/spring-data-repositories#repositories) и имеет шаблонные Spring Data JPA методы.

При сдаче лабораторной нужно будет показать работоспособность endpoint’ов через HTTP запросы (рекомендуется [Postman](https://www.postman.com/downloads/)).

# Lab-4

## Владельцы недовольны, что инфрмацию о котиках может получить кто угодно. В этой лабораторной мы добавим авторизацию к сервису.

Добавляется роль администратора. Он имеет доступ ко всем методам и может создавать новых пользователей. Пользователь связан с владельцем в соотношении 1:1.

Методы по получению информации и котиках и владельцах должны быть защищены [Spring Security](https://docs.spring.io/spring-security/reference/index.html). Доступ к соотвутсвующим endpoint’ам имеют только владельцы котиков и администраторы. Доступ к методам для фильтрации имеют все авторизованные пользователи, но на выходе получают только данные о своих котиках.

**Внимание**: эндпоинты, созданные на предыдущем этапе, не должны быть удалены.

# Lab-5

## Бизнес прочитал статью о том, что микросервисы это круто и попросил нас разбить программу на микросервисы.

Из созданного приложения выделяются три микросервиса:
- Микросервис доступа к котикам.
- Микросервис доступа к владельцам.
- Микросервис с внешними интерфейсами.

Все они являются **разными приложениями**.

Все созданные ранее эндпоинты и авторизация переезжает на третий микросервис.

Общение между микросервисами происходит посредством [RabbitMQ](https://www.rabbitmq.com/)/[Kafka](https://kafka.apache.org/) (на выбор студента).

Сервисы доступа к котикам и доступа к владельцам могут либо быть подключены к одной БД, либо иметь разные БД. Во втором случае недопустимо делать один запрос на получение данных из двух БД, запроса должно быть два (по одному в каждую).

**Внимание**: недопустимо передавать через RabbitMQ/Kafka JPA сущности. Рекомендуется создать отдельные оберточные классы.

Рекомендуется выделить модуль с JPA сущностями в отдельный подключаемый модуль.

