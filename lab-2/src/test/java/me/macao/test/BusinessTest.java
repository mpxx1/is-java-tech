package me.macao.test;

import me.macao.crypto.EmailVerifier;
import me.macao.crypto.PasswordVerifier;
import me.macao.crypto.StringHasher;
import me.macao.exception.*;
import me.macao.model.*;
import me.macao.dao.Dao;
import me.macao.service.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.spy;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

public class BusinessTest {

    private final PasswordVerifier passwordVerifier = new PasswordVerifier();
    private final EmailVerifier emailVerifier = new EmailVerifier();
    private final StringHasher hasher = new StringHasher();

    @Mock
    Dao<Integer, User> mockUserDao;

    @Mock
    Dao<Integer, Cat> mockCatDao;

    @InjectMocks
    UserService userService = new UserServiceImpl(mockUserDao, mockCatDao);

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        userService = new UserServiceImpl(mockUserDao, mockCatDao);
    }

    @Test
    public void userCreateTest()
            throws ObjectNotFoundException, InvalidOperationException,
            EmailCreateException, PasswordCreateException {

        User u0 = User.builder()
                .name("Woody")
                .email("ma@gmail.com")
                .passwdHash(hasher.generate("Passwd1!"))
                .birthday(LocalDate.of(2002, 2, 2)).build();

        Mockito.when(mockUserDao.create(u0)).thenReturn(u0);

        User user = userService.createUser(
                new UserDto(
                        "ma@gmail.com",
                        "Passwd1!",
                        LocalDate.of(2002, 2, 2),
                        "Woody"));

        Mockito.verify(mockUserDao, Mockito.times(1)).create(u0);

        assertEquals("Woody", user.getName());
        assertEquals("ma@gmail.com", user.getEmail());
        assertEquals(LocalDate.of(2002, 2, 2), user.getBirthday());
        assertEquals(hasher.generate("Passwd1!"), user.getPasswdHash());
    }

    @Test
    public void catCreateTest()
            throws EmailCreateException, ObjectNotFoundException,
            InvalidOperationException, PasswordCreateException,
            SpecifyUserException {

        User u0 = User.builder()
                .name("Woody")
                .email("ma@gmail.com")
                .passwdHash(hasher.generate("Passwd1!"))
                .birthday(LocalDate.of(2002, 2, 2)).build();

        Mockito.when(mockUserDao.create(u0)).thenReturn(u0);

        User user = userService.createUser(
                new UserDto(
                "ma@gmail.com",
                "Passwd1!",
                LocalDate.of(2002, 2, 2),
                "Woody"));

        Cat c0 = Cat.builder()
                .name("Allen")
                .color(CatColor.Black)
                .breed("breed1")
                .birthday(LocalDate.of(2002, 2, 2))
                .owner(user).build();

        Mockito.when(mockCatDao.create(c0)).thenReturn(c0);

        Cat cat = userService.createCat(
                new CatDto(
                "Allen",
                LocalDate.of(2002, 2, 2),
                "breed1",
                CatColor.Black
                )
        );

        Mockito.verify(mockCatDao, Mockito.times(1)).create(c0);

        assertEquals("Allen", cat.getName());
        assertEquals(CatColor.Black, cat.getColor());
        assertEquals("breed1", cat.getBreed());
        assertEquals(LocalDate.of(2002, 2, 2), cat.getBirthday());
        assertEquals(user, cat.getOwner());
    }


    @Test
    public void getAllCatsTest()
            throws ObjectNotFoundException, InvalidOperationException,
                EmailCreateException, PasswordCreateException,
                DBTimeoutException, SpecifyUserException {

        User u0 = User.builder()
                .name("Woody")
                .email("ma@gmail.com")
                .passwdHash(hasher.generate("Passwd1!"))
                .birthday(LocalDate.of(2002, 2, 2)).build();
        User u1 = spy(u0);

        Mockito.when(mockUserDao.create(u0)).thenReturn(u1);
        User user = userService.createUser(
                new UserDto(
                "ma@gmail.com",
                "Passwd1!",
                LocalDate.of(2002, 2, 2),
                "Woody"));

        Cat c0 = Cat.builder()
                .name("Allen0")
                .color(CatColor.Black)
                .breed("breed0")
                .birthday(LocalDate.of(2002, 2, 2))
                .owner(user).build();
        Cat c1 = Cat.builder()
                .name("Allen1")
                .color(CatColor.Black)
                .breed("breed1")
                .birthday(LocalDate.of(2002, 2, 2))
                .owner(user).build();

        Mockito.when(mockCatDao.create(c0)).thenReturn(c0);
        Mockito.when(mockCatDao.create(c1)).thenReturn(c1);
        Mockito.when(mockUserDao.findAll()).thenReturn(List.of(u1));
        Mockito.doReturn(List.of(c0, c1)).when(u1).getCatList();

        userService.logout();
        userService.login(user.getEmail(), "Passwd1!");
        userService.createCat(
                new CatDto(
                "Allen0",
                c0.getBirthday(),
                c0.getBreed(),
                c0.getColor())
        );
        userService.createCat(
                new CatDto(
                "Allen1",
                c1.getBirthday(),
                c1.getBreed(),
                c1.getColor())
        );
        Collection<Cat> cats = userService.allCats();

        Mockito.verify(mockUserDao, Mockito.times(1)).findAll();
        Mockito.verify(mockCatDao, Mockito.times(2)).create(Mockito.any(Cat.class));

        assertEquals(2, cats.size());
        assertEquals(c0, cats.toArray()[0]);
        assertEquals(c1, cats.toArray()[1]);
    }

    @Test
    public void createFriendshipRequestTest()
            throws ObjectNotFoundException, InvalidOperationException,
            EmailCreateException, PasswordCreateException,
            DBTimeoutException, SpecifyUserException {

        User u0 = User.builder()
                .name("Woody")
                .email("wa0@gmail.com")
                .passwdHash(hasher.generate("Passwd1!"))
                .birthday(LocalDate.of(2002, 2, 2)).build();
        User u1 = User.builder()
                .name("Woody")
                .email("wa1@gmail.com")
                .passwdHash(hasher.generate("Passwd1!"))
                .birthday(LocalDate.of(2002, 2, 2)).build();
        User s0 = spy(u0);
        User s1 = spy(u1);

        Mockito.when(mockUserDao.create(u0)).thenReturn(s0);
        User user0 = userService.createUser(
                new UserDto(
                "wa0@gmail.com",
                "Passwd1!",
                LocalDate.of(2002, 2, 2),
                "Woody"));

        userService.logout();
        Mockito.when(mockUserDao.create(u1)).thenReturn(s1);
        User user1 = userService.createUser(
                new UserDto(
                "wa1@gmail.com",
                "Passwd1!",
                LocalDate.of(2002, 2, 2),
                "Woody"));

        Cat c0 = Cat.builder()
                .name("Allen0")
                .color(CatColor.Black)
                .breed("breed0")
                .birthday(LocalDate.of(2002, 2, 2))
                .owner(user0).build();
        Cat c1 = Cat.builder()
                .name("Allen1")
                .color(CatColor.Black)
                .breed("breed1")
                .birthday(LocalDate.of(2002, 2, 2))
                .owner(user1).build();

        userService.logout();
        Mockito.when(mockUserDao.findAll()).thenReturn(List.of(s0));
        userService.login("wa0@gmail.com", "Passwd1!");
        Mockito.when(mockCatDao.create(c0)).thenReturn(c0);
        userService.createCat(
                new CatDto(
                c0.getName(),
                c0.getBirthday(),
                c0.getBreed(),
                c0.getColor()
                )
        );
        userService.logout();

        Mockito.when(mockUserDao.findAll()).thenReturn(List.of(s1));
        userService.login("wa1@gmail.com", "Passwd1!");
        Mockito.when(mockCatDao.create(c1)).thenReturn(c1);
        userService.createCat(
                new CatDto(
                c1.getName(),
                c1.getBirthday(),
                c1.getBreed(),
                c1.getColor())
        );
        Mockito.doReturn(List.of(c0)).when(s0).getCatList();
        Mockito.doReturn(List.of(c1)).when(s1).getCatList();
        Mockito.when(mockCatDao.findAll()).thenReturn(List.of(c0));
        userService.requestFriendship(
                c1.getName(),
                "wa0@gmail.com",
                c0.getName()
        );
        userService.logout();

        assertEquals(c0, c1.getFriendshipRequests().toArray()[0]);
    }

    @Test
    public void friendCreateTest1()
            throws ObjectNotFoundException, InvalidOperationException,
                EmailCreateException, PasswordCreateException,
                DBTimeoutException, SpecifyUserException {

        User u0 = User.builder()
                .name("Woody")
                .email("wa0@gmail.com")
                .passwdHash(hasher.generate("Passwd1!"))
                .birthday(LocalDate.of(2002, 2, 2)).build();
        User u1 = User.builder()
                .name("Woody")
                .email("wa1@gmail.com")
                .passwdHash(hasher.generate("Passwd1!"))
                .birthday(LocalDate.of(2002, 2, 2)).build();
        User s0 = spy(u0);
        User s1 = spy(u1);

        Mockito.when(mockUserDao.create(u0)).thenReturn(s0);
        User user0 = userService.createUser(
                new UserDto(
                "wa0@gmail.com",
                "Passwd1!",
                LocalDate.of(2002, 2, 2),
                "Woody"));

        userService.logout();
        Mockito.when(mockUserDao.create(u1)).thenReturn(s1);
        User user1 = userService.createUser(
                new UserDto(
                "wa1@gmail.com",
                "Passwd1!",
                LocalDate.of(2002, 2, 2),
                "Woody"));

        Cat c0 = Cat.builder()
                .name("Allen0")
                .color(CatColor.Black)
                .breed("breed0")
                .birthday(LocalDate.of(2002, 2, 2))
                .owner(user0).build();
        Cat c1 = Cat.builder()
                .name("Allen1")
                .color(CatColor.Black)
                .breed("breed1")
                .birthday(LocalDate.of(2002, 2, 2))
                .owner(user1).build();

        userService.logout();
        Mockito.when(mockUserDao.findAll()).thenReturn(List.of(s0));
        userService.login("wa0@gmail.com", "Passwd1!");
        Mockito.when(mockCatDao.create(c0)).thenReturn(c0);
        userService.createCat(
                new CatDto(
                c0.getName(),
                c0.getBirthday(),
                c0.getBreed(),
                c0.getColor())
        );
        userService.logout();

        Mockito.when(mockUserDao.findAll()).thenReturn(List.of(s1));
        userService.login("wa1@gmail.com", "Passwd1!");
        Mockito.when(mockCatDao.create(c1)).thenReturn(c1);
        userService.createCat(
                new CatDto(
                c1.getName(),
                c1.getBirthday(),
                c1.getBreed(),
                c1.getColor())
        );
        Mockito.doReturn(List.of(c0)).when(s0).getCatList();
        Mockito.doReturn(List.of(c1)).when(s1).getCatList();
        Mockito.when(mockCatDao.findAll()).thenReturn(List.of(c0));
        userService.requestFriendship(
                c1.getName(),
                "wa0@gmail.com",
                c0.getName()
        );
        userService.logout();

        Mockito.when(mockUserDao.findAll()).thenReturn(List.of(s0));
        userService.login("wa0@gmail.com", "Passwd1!");
        Mockito.when(mockCatDao.findAll()).thenReturn(List.of(c1));
        userService.acceptFriendship(
                c0.getName(),
                "wa1@gmail.com",
                c1.getName()
        );
        userService.logout();

        assertEquals(c0, c1.getFriends().toArray()[0]);
        assertEquals(c1, c0.getFriends().toArray()[0]);
        assertEquals(0, c0.getFriendshipRequests().toArray().length);
        assertEquals(0, c1.getFriendshipRequests().toArray().length);
    }

    @Test
    public void friendCreateTest2()
            throws ObjectNotFoundException, InvalidOperationException,
            EmailCreateException, PasswordCreateException,
            DBTimeoutException, SpecifyUserException {

        User u0 = User.builder()
                .name("Woody")
                .email("wa0@gmail.com")
                .passwdHash(hasher.generate("Passwd1!"))
                .birthday(LocalDate.of(2002, 2, 2)).build();
        User u1 = User.builder()
                .name("Woody")
                .email("wa1@gmail.com")
                .passwdHash(hasher.generate("Passwd1!"))
                .birthday(LocalDate.of(2002, 2, 2)).build();
        User s0 = spy(u0);
        User s1 = spy(u1);

        Mockito.when(mockUserDao.create(u0)).thenReturn(s0);
        User user0 = userService.createUser(
                new UserDto(
                "wa0@gmail.com",
                "Passwd1!",
                LocalDate.of(2002, 2, 2),
                "Woody"));

        userService.logout();
        Mockito.when(mockUserDao.create(u1)).thenReturn(s1);
        User user1 = userService.createUser(
                new UserDto(
                "wa1@gmail.com",
                "Passwd1!",
                LocalDate.of(2002, 2, 2),
                "Woody"));

        Cat c0 = Cat.builder()
                .name("Allen0")
                .color(CatColor.Black)
                .breed("breed0")
                .birthday(LocalDate.of(2002, 2, 2))
                .owner(user0).build();
        Cat c1 = Cat.builder()
                .name("Allen1")
                .color(CatColor.Black)
                .breed("breed1")
                .birthday(LocalDate.of(2002, 2, 2))
                .owner(user1).build();

        userService.logout();
        Mockito.when(mockUserDao.findAll()).thenReturn(List.of(s0));
        userService.login("wa0@gmail.com", "Passwd1!");
        Mockito.when(mockCatDao.create(c0)).thenReturn(c0);
        userService.createCat(
                new CatDto(
                c0.getName(),
                c0.getBirthday(),
                c0.getBreed(),
                c0.getColor())
        );
        userService.logout();

        Mockito.when(mockUserDao.findAll()).thenReturn(List.of(s1));
        userService.login("wa1@gmail.com", "Passwd1!");
        Mockito.when(mockCatDao.create(c1)).thenReturn(c1);
        userService.createCat(
                new CatDto(
                c1.getName(),
                c1.getBirthday(),
                c1.getBreed(),
                c1.getColor())
        );
        Mockito.doReturn(List.of(c0)).when(s0).getCatList();
        Mockito.doReturn(List.of(c1)).when(s1).getCatList();
        Mockito.when(mockCatDao.findAll()).thenReturn(List.of(c0));
        userService.requestFriendship(
                c1.getName(),
                "wa0@gmail.com",
                c0.getName()
        );
        userService.logout();

        Mockito.when(mockUserDao.findAll()).thenReturn(List.of(s0));
        userService.login("wa0@gmail.com", "Passwd1!");
        Mockito.when(mockCatDao.findAll()).thenReturn(List.of(c1));
        userService.requestFriendship(
                c0.getName(),
                "wa1@gmail.com",
                c1.getName()
        );
        userService.logout();

        assertEquals(c0, c1.getFriends().toArray()[0]);
        assertEquals(c1, c0.getFriends().toArray()[0]);
        assertEquals(0, c0.getFriendshipRequests().toArray().length);
        assertEquals(0, c1.getFriendshipRequests().toArray().length);
    }

    @Test
    public void friendDropTest()
            throws ObjectNotFoundException, InvalidOperationException,
                EmailCreateException, PasswordCreateException,
                DBTimeoutException, SpecifyUserException {

        User u0 = User.builder()
                .name("Woody")
                .email("wa0@gmail.com")
                .passwdHash(hasher.generate("Passwd1!"))
                .birthday(LocalDate.of(2002, 2, 2)).build();
        User u1 = User.builder()
                .name("Woody")
                .email("wa1@gmail.com")
                .passwdHash(hasher.generate("Passwd1!"))
                .birthday(LocalDate.of(2002, 2, 2)).build();
        User s0 = spy(u0);
        User s1 = spy(u1);

        Mockito.when(mockUserDao.create(u0)).thenReturn(s0);
        User user0 = userService.createUser(
                new UserDto(
                "wa0@gmail.com",
                "Passwd1!",
                LocalDate.of(2002, 2, 2),
                "Woody"));

        userService.logout();
        Mockito.when(mockUserDao.create(u1)).thenReturn(s1);
        User user1 = userService.createUser(
                new UserDto(
                "wa1@gmail.com",
                "Passwd1!",
                LocalDate.of(2002, 2, 2),
                "Woody"));

        Cat c0 = Cat.builder()
                .name("Allen0")
                .color(CatColor.Black)
                .breed("breed0")
                .birthday(LocalDate.of(2002, 2, 2))
                .owner(user0).build();
        Cat c1 = Cat.builder()
                .name("Allen1")
                .color(CatColor.Black)
                .breed("breed1")
                .birthday(LocalDate.of(2002, 2, 2))
                .owner(user1).build();

        userService.logout();
        Mockito.when(mockUserDao.findAll()).thenReturn(List.of(s0));
        userService.login("wa0@gmail.com", "Passwd1!");
        Mockito.when(mockCatDao.create(c0)).thenReturn(c0);
        userService.createCat(
                new CatDto(
                c0.getName(),
                c0.getBirthday(),
                c0.getBreed(),
                c0.getColor())
        );
        userService.logout();

        Mockito.when(mockUserDao.findAll()).thenReturn(List.of(s1));
        userService.login("wa1@gmail.com", "Passwd1!");
        Mockito.when(mockCatDao.create(c1)).thenReturn(c1);
        userService.createCat(
                new CatDto(
                c1.getName(),
                c1.getBirthday(),
                c1.getBreed(),
                c1.getColor())
        );
        Mockito.doReturn(List.of(c0)).when(s0).getCatList();
        Mockito.doReturn(List.of(c1)).when(s1).getCatList();
        Mockito.when(mockCatDao.findAll()).thenReturn(List.of(c0));
        userService.requestFriendship(
                c1.getName(),
                "wa0@gmail.com",
                c0.getName()
        );
        userService.logout();

        Mockito.when(mockUserDao.findAll()).thenReturn(List.of(s0));
        userService.login("wa0@gmail.com", "Passwd1!");
        Mockito.when(mockCatDao.findAll()).thenReturn(List.of(c1));
        userService.acceptFriendship(
                c0.getName(),
                "wa1@gmail.com",
                c1.getName()
        );

        // dropping
        userService.dropFriend(
                c0.getName(),
                "wa1@gmail.com",
                c1.getName()
        );
        userService.logout();

        assertEquals(0, c0.getFriends().toArray().length);
        assertEquals(0, c1.getFriends().toArray().length);
        assertEquals(0, c0.getFriendshipRequests().toArray().length);
        assertEquals(1, c1.getFriendshipRequests().toArray().length);

        Mockito.when(mockUserDao.findAll()).thenReturn(List.of(s1));
        userService.login("wa1@gmail.com", "Passwd1!");
        Mockito.when(mockCatDao.findAll()).thenReturn(List.of(c0));

        userService.cancelRequest(
                c1.getName(),
                "wa0@gmail.com",
                c0.getName()
        );
        userService.logout();

        assertEquals(0, c0.getFriends().toArray().length);
        assertEquals(0, c1.getFriends().toArray().length);
        assertEquals(0, c0.getFriendshipRequests().toArray().length);
        assertEquals(0, c1.getFriendshipRequests().toArray().length);
    }

    @Test
    public void changeCatTest()
            throws EmailCreateException, ObjectNotFoundException,
                InvalidOperationException, PasswordCreateException,
                SpecifyUserException {

        User u0 = User.builder()
                .name("Woody")
                .email("ma@gmail.com")
                .passwdHash(hasher.generate("Passwd1!"))
                .birthday(LocalDate.of(2002, 2, 2)).build();

        Mockito.when(mockUserDao.create(u0)).thenReturn(u0);

        User user = userService.createUser(
                new UserDto(
                "ma@gmail.com",
                "Passwd1!",
                LocalDate.of(2002, 2, 2),
                "Woody"));

        Cat c0 = Cat.builder()
                .name("Allen")
                .color(CatColor.Black)
                .breed("breed1")
                .birthday(LocalDate.of(2002, 2, 2))
                .owner(user).build();

        Mockito.when(mockCatDao.create(c0)).thenReturn(c0);

        Cat cat = userService.createCat(
                new CatDto(
                "Allen",
                LocalDate.of(2002, 2, 2),
                "breed1",
                CatColor.Black)
        );

        Mockito.verify(mockCatDao, Mockito.times(1)).create(c0);

        assertEquals("Allen", cat.getName());
        assertEquals(CatColor.Black, cat.getColor());
        assertEquals("breed1", cat.getBreed());
        assertEquals(LocalDate.of(2002, 2, 2), cat.getBirthday());
        assertEquals(user, cat.getOwner());

        cat.setName("Batman");
        userService.updateCat(cat);

        assertEquals("Batman", cat.getName());
        assertEquals(CatColor.Black, cat.getColor());
        assertEquals("breed1", cat.getBreed());
        assertEquals(LocalDate.of(2002, 2, 2), cat.getBirthday());
        assertEquals(user, cat.getOwner());
    }

    // negative test cases

    @Test
    public void incorrectLoginTest()
            throws ObjectNotFoundException, InvalidOperationException,
            EmailCreateException, PasswordCreateException, DBTimeoutException {

        User u0 = User.builder()
                    .name("Woody")
                    .email("ma@gmail.com")
                    .passwdHash(hasher.generate("Passwd1!"))
                    .birthday(LocalDate.of(2002, 2, 2)).build();

        Mockito.when(mockUserDao.create(u0)).thenReturn(u0);

        User user = userService.createUser(
                new UserDto(
                    "ma@gmail.com",
                    "Passwd1!",
                    LocalDate.of(2002, 2, 2),
                    "Woody"));
        userService.logout();

        Mockito.when(mockUserDao.findAll()).thenReturn(List.of(u0));

        assertThrows(ObjectNotFoundException.class, () -> userService.login("ma@gmail.com", "pp"));
    }

    @Test
    public void unableToCreateCatBeforeSpecifyingUserTest()
            throws EmailCreateException, ObjectNotFoundException,
                InvalidOperationException, PasswordCreateException {

        User u0 = User.builder()
                .name("Woody")
                .email("ma@gmail.com")
                .passwdHash(hasher.generate("Passwd1!"))
                .birthday(LocalDate.of(2002, 2, 2)).build();

        Mockito.when(mockUserDao.create(u0)).thenReturn(u0);

        User user = userService.createUser(
                new UserDto(
                "ma@gmail.com",
                "Passwd1!",
                LocalDate.of(2002, 2, 2),
                "Woody"));
        userService.logout();

        Cat c0 = Cat.builder()
                .name("Allen")
                .color(CatColor.Black)
                .breed("breed1")
                .birthday(LocalDate.of(2002, 2, 2))
                .owner(user).build();

        assertThrows(SpecifyUserException.class, () -> userService.createCat(
                new CatDto(
                "Allen",
                LocalDate.of(2002, 2, 2),
                "breed1",
                CatColor.Black)
        ));
    }

    @Test
    public void secondLoginFallsTest()
            throws ObjectNotFoundException, InvalidOperationException,
            EmailCreateException, PasswordCreateException, DBTimeoutException {

        User u0 = User.builder()
                .name("Woody")
                .email("ma@gmail.com")
                .passwdHash(hasher.generate("Passwd1!"))
                .birthday(LocalDate.of(2002, 2, 2)).build();

        Mockito.when(mockUserDao.create(u0)).thenReturn(u0);

        User user = userService.createUser(
                new UserDto(
                "ma@gmail.com",
                "Passwd1!",
                LocalDate.of(2002, 2, 2),
                "Woody"));

        assertThrows(
                InvalidOperationException.class,
                () -> userService.login("ma@gmail.com", "Passwd1!")
        );
    }

    @Test
    public void catNotFoundTest()
            throws EmailCreateException, ObjectNotFoundException,
                InvalidOperationException, PasswordCreateException,
                SpecifyUserException {

        User u0 = User.builder()
                .name("Woody")
                .email("ma@gmail.com")
                .passwdHash(hasher.generate("Passwd1!"))
                .birthday(LocalDate.of(2002, 2, 2)).build();

        Mockito.when(mockUserDao.create(u0)).thenReturn(u0);

        User user = userService.createUser(
                new UserDto(
                "ma@gmail.com",
                "Passwd1!",
                LocalDate.of(2002, 2, 2),
                "Woody"));

        Cat c0 = Cat.builder()
                .name("Allen")
                .color(CatColor.Black)
                .breed("breed1")
                .birthday(LocalDate.of(2002, 2, 2))
                .owner(user).build();

        Mockito.when(mockCatDao.create(c0)).thenReturn(c0);

        Cat cat = userService.createCat(
                new CatDto(
                "Allen",
                LocalDate.of(2002, 2, 2),
                "breed1",
                CatColor.Black)
        );

        Mockito.verify(mockCatDao, Mockito.times(1)).create(c0);

        assertEquals("Allen", cat.getName());
        assertEquals(CatColor.Black, cat.getColor());
        assertEquals("breed1", cat.getBreed());
        assertEquals(LocalDate.of(2002, 2, 2), cat.getBirthday());
        assertEquals(user, cat.getOwner());

        assertThrows(
                ObjectNotFoundException.class,
                () -> userService.getCatByName("Allen0")
        );
    }

    @Test
    public void incorrectPasswordTest()
            throws ObjectNotFoundException, InvalidOperationException,
                    EmailCreateException, PasswordCreateException {

        String weakPass = "password";
        String strongPass = "Passwd1!";

        User u0 = User.builder()
                .name("Woody")
                .email("ma@gmail.com")
                .passwdHash(hasher.generate(strongPass))
                .birthday(LocalDate.of(2002, 2, 2)).build();

        Mockito.when(mockUserDao.create(u0)).thenReturn(u0);

        assertThrows(
                PasswordCreateException.class,
                () -> userService.createUser(
                        new UserDto(
                        "ma@gmail.com",
                        weakPass,
                        LocalDate.of(2002, 2, 2),
                        "Woody"))
        );

        User user = userService.createUser(
                new UserDto(
                "ma@gmail.com",
                "Passwd1!",
                LocalDate.of(2002, 2, 2),
                "Woody"));

        Mockito.verify(mockUserDao, Mockito.times(1)).create(u0);

        assertEquals("Woody", user.getName());
        assertEquals("ma@gmail.com", user.getEmail());
        assertEquals(LocalDate.of(2002, 2, 2), user.getBirthday());
        assertEquals(hasher.generate("Passwd1!"), user.getPasswdHash());
    }

    @Test
    public void incorrectEmailTest()
            throws ObjectNotFoundException, InvalidOperationException,
            EmailCreateException, PasswordCreateException {

        String wrongMail = "mail@";
        String correctMail = "me@email.com";

        User u0 = User.builder()
                .name("Woody")
                .email(correctMail)
                .passwdHash(hasher.generate("Passwd1!"))
                .birthday(LocalDate.of(2002, 2, 2)).build();

        Mockito.when(mockUserDao.create(u0)).thenReturn(u0);

        assertThrows(
                EmailCreateException.class,
                () -> userService.createUser(
                        new UserDto(
                        wrongMail,
                        "Passwd1!",
                        LocalDate.of(2002, 2, 2),
                        "Woody"))
        );

        User user = userService.createUser(
                new UserDto(
                correctMail,
                "Passwd1!",
                LocalDate.of(2002, 2, 2),
                "Woody"));

        Mockito.verify(mockUserDao, Mockito.times(1)).create(u0);

        assertEquals("Woody", user.getName());
        assertEquals(correctMail, user.getEmail());
        assertEquals(LocalDate.of(2002, 2, 2), user.getBirthday());
        assertEquals(hasher.generate("Passwd1!"), user.getPasswdHash());
    }
}
