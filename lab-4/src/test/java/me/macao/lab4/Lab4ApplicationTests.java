package me.macao.lab4;

import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Transactional
@SpringBootTest
@AutoConfigureMockMvc
class Lab4ApplicationTests {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @Sql("/sql/users.sql")
    @Sql("/sql/cats.sql")
    void getAllCats_returnsFullCollection() throws Exception {
        var requestBuilder = MockMvcRequestBuilders
                .get("/api/v0.1.0/cats/all")
                .with(user("m1@mail.ru")
                        .password("Password11!!")
                        .authorities(
                            List.of(new SimpleGrantedAuthority("ADMIN")
                        ))
                );

        this.mockMvc.perform(requestBuilder)

                .andDo(print())
                .andExpectAll(
                        status().isOk(),
                        content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON),
                        content().json("""
                        [
                                {"ownerId":22,"catId":22,"name":"Simon1","breed":"b1","catColor":"RED","birthday":"2020-12-12","friends":[],"reqsIn":[],"reqsOut":[]},
                                {"ownerId":22,"catId":23,"name":"Simon2","breed":"b1","catColor":"RED","birthday":"2020-12-12","friends":[],"reqsIn":[],"reqsOut":[]},
                                {"ownerId":23,"catId":24,"name":"Simon3","breed":"b2","catColor":"BLACK","birthday":"2020-12-12","friends":[],"reqsIn":[],"reqsOut":[]},
                                {"ownerId":23,"catId":25,"name":"Simon4","breed":"b2","catColor":"BLACK","birthday":"2020-12-12","friends":[],"reqsIn":[],"reqsOut":[]}
                        ]
                        """)
                );
    }


    @Test
    @Sql("/sql/users.sql")
    @Sql("/sql/cats.sql")
    void getAllCats_throwsException() throws Exception {
        var requestBuilder = MockMvcRequestBuilders
                .get("/api/v0.1.0/cats/all")
                .with(user("m3@mail.ru")
                        .password("Password11!!")
                        .authorities(
                                List.of(new SimpleGrantedAuthority("USER")
                                ))
                );

        this.mockMvc.perform(requestBuilder)

                .andDo(print())
                .andExpectAll(
                        status().is(405),
                        content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON),
                        content().json("""
                        {
                          "path":"/api/v0.1.0/cats/all",
                          "message":"Access Denied",
                          "statusCode":405
                        }
                        """)
                );
    }

    @Test
    @Sql("/sql/users.sql")
    @Sql("/sql/cats.sql")
    public void getMyCats_returnsTwoCats() throws Exception {
        var requestBuilder = MockMvcRequestBuilders
                .get("/api/v0.1.0/cats/my")
                .with(user("m3@mail.ru")
                        .password("Password11!!")
                        .authorities(
                                    List.of(new SimpleGrantedAuthority("USER")
                                ))
                );

        this.mockMvc.perform(requestBuilder)

                .andDo(print())
                .andExpectAll(
                        status().isOk(),
                        content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON),
                        content().json("""
                        [
                                {"ownerId":22,"catId":22,"name":"Simon1","breed":"b1","catColor":"RED","birthday":"2020-12-12","friends":[],"reqsIn":[],"reqsOut":[]},
                                {"ownerId":22,"catId":23,"name":"Simon2","breed":"b1","catColor":"RED","birthday":"2020-12-12","friends":[],"reqsIn":[],"reqsOut":[]}
                        ]
                        """)
                );
    }

    @Test
    @Sql("/sql/users.sql")
    @Sql("/sql/cats.sql")
    public void getCatById_returnsCat() throws Exception {
        var requestBuilder = MockMvcRequestBuilders
                .get("/api/v0.1.0/cats/22")
                .with(user("m3@mail.ru")
                        .password("Password11!!")
                        .authorities(
                                List.of(new SimpleGrantedAuthority("USER")
                                ))
                );

        this.mockMvc.perform(requestBuilder)

                .andDo(print())
                .andExpectAll(
                        status().isOk(),
                        content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON),
                        content().json("""
                              {"ownerId":22,"catId":22,"name":"Simon1","breed":"b1","catColor":"RED","birthday":"2020-12-12","friends":[],"reqsIn":[],"reqsOut":[]}
                        """)
                );
    }

    @Test
    @Sql("/sql/users.sql")
    @Sql("/sql/cats.sql")
    public void getExistingCatById_throwsException() throws Exception {
        var requestBuilder = MockMvcRequestBuilders
                .get("/api/v0.1.0/cats/25")
                .with(user("m3@mail.ru")
                        .password("Password11!!")
                        .authorities(
                                List.of(new SimpleGrantedAuthority("USER")
                                ))
                );

        this.mockMvc.perform(requestBuilder)

                .andDo(print())
                .andExpectAll(
                        status().is(405),
                        content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON),
                        content().json("""
                        {
                          "path":"/api/v0.1.0/cats/25",
                          "message":"Access denied",
                          "statusCode":405
                        }
                        """)
                );
    }

    @Test
    @Sql("/sql/users.sql")
    @Sql("/sql/cats.sql")
    public void getNotExistingCatById_throwsException() throws Exception {
        var requestBuilder = MockMvcRequestBuilders
                .get("/api/v0.1.0/cats/26")
                .with(user("m3@mail.ru")
                        .password("Password11!!")
                        .authorities(
                                List.of(new SimpleGrantedAuthority("USER")
                                ))
                );

        this.mockMvc.perform(requestBuilder)

                .andDo(print())
                .andExpectAll(
                        status().is(405),
                        content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON),
                        content().json("""
                        {
                          "path":"/api/v0.1.0/cats/26",
                          "message":"Access denied",
                          "statusCode":405
                        }
                        """)
                );
    }

    @Test
    @Sql("/sql/users.sql")
    @Sql("/sql/cats.sql")
    public void getNotExistingCatById_throwsExceptionToAdmin() throws Exception {
        var requestBuilder = MockMvcRequestBuilders
                .get("/api/v0.1.0/cats/26")
                .with(user("m1@mail.ru")
                        .password("Password11!!")
                        .authorities(
                                List.of(new SimpleGrantedAuthority("ADMIN")
                                ))
                );

        this.mockMvc.perform(requestBuilder)

                .andDo(print())
                .andExpectAll(
                        status().is(404),
                        content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON),
                        content().json("""
                        {
                          "path":"/api/v0.1.0/cats/26",
                          "message":"No cat with id 26",
                          "statusCode":404
                        }
                        """)
                );
    }

    @Test
    @Sql("/sql/users.sql")
    @Sql("/sql/cats.sql")
    public void deleteUserCat_succeed() throws Exception {
        var requestBuilder = MockMvcRequestBuilders
                .delete("/api/v0.1.0/cats/23")
                .with(user("m3@mail.ru")
                        .password("Password11!!")
                        .authorities(
                                List.of(new SimpleGrantedAuthority("USER")
                                ))
                );

        this.mockMvc.perform(requestBuilder);

        requestBuilder = MockMvcRequestBuilders
                .get("/api/v0.1.0/cats/my")
                .with(user("m3@mail.ru")
                        .password("Password11!!")
                        .authorities(
                                List.of(new SimpleGrantedAuthority("USER")
                                ))
                );

        this.mockMvc.perform(requestBuilder)

                .andDo(print())
                .andExpectAll(
                        status().isOk(),
                        content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON),
                        content().json("""
                              [
                                  {"ownerId":22,"catId":22,"name":"Simon1","breed":"b1","catColor":"RED","birthday":"2020-12-12","friends":[],"reqsIn":[],"reqsOut":[]}
                              ]
                              """)
                );
    }

    @Test
    @Sql("/sql/users.sql")
    @Sql("/sql/cats.sql")
    public void deleteUserCat_failed() throws Exception {
        var requestBuilder = MockMvcRequestBuilders
                .delete("/api/v0.1.0/cats/24")
                .with(user("m3@mail.ru")
                        .password("Password11!!")
                        .authorities(
                                List.of(new SimpleGrantedAuthority("USER")
                                ))
                );

        this.mockMvc.perform(requestBuilder)

                .andDo(print())
                .andExpectAll(
                        status().is(405),
                        content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON),
                        content().json("""
                        {
                          "path":"/api/v0.1.0/cats/24",
                          "message":"Access denied",
                          "statusCode":405
                        }
                        """)
                );
    }

    @Test
    @Sql("/sql/users.sql")
    @Sql("/sql/cats.sql")
    public void catUpdateByAdmin_succeed() throws Exception {
        var requestBuilder = MockMvcRequestBuilders
                .put("/api/v0.1.0/cats/22?name=Alex&breed=breed2&addFriend=24")
                .with(user("m1@mail.ru")
                        .password("Password11!!")
                        .authorities(
                                List.of(new SimpleGrantedAuthority("ADMIN")
                                ))
                );

        this.mockMvc.perform(requestBuilder)

                .andDo(print())
                .andExpectAll(
                        status().isOk(),
                        content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON),
                        content().json("""
                              {"ownerId":22,"catId":22,"name":"Alex","breed":"breed2","catColor":"RED","birthday":"2020-12-12","friends":[],"reqsIn":[],"reqsOut":[24]}
                        """)
                );

        requestBuilder = MockMvcRequestBuilders
                .get("/api/v0.1.0/cats/24")
                .with(user("m1@mail.ru")
                        .password("Password11!!")
                        .authorities(
                                List.of(new SimpleGrantedAuthority("ADMIN")
                                ))
                );

        this.mockMvc.perform(requestBuilder)

                .andDo(print())
                .andExpectAll(
                        status().isOk(),
                        content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON),
                        content().json("""
                              {"ownerId":23,"catId":24,"name":"Simon3","breed":"b2","catColor":"BLACK","birthday":"2020-12-12","friends":[],"reqsIn":[22],"reqsOut":[]}
                        """)
                );
    }

    @Test
    @Sql("/sql/users.sql")
    @Sql("/sql/cats.sql")
    public void catUpdateByAdmin_throwsNoSuchCatException() throws Exception {
        var requestBuilder = MockMvcRequestBuilders
                .put("/api/v0.1.0/cats/22?name=Alex&breed=breed2&addFriend=26")
                .with(user("m1@mail.ru")
                        .password("Password11!!")
                        .authorities(
                                List.of(new SimpleGrantedAuthority("ADMIN")
                                ))
                );

        this.mockMvc.perform(requestBuilder)

                .andDo(print())
                .andExpectAll(
                        status().is(404),
                        content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON),
                        content().json("""
                        {
                          "path":"/api/v0.1.0/cats/22",
                          "message":"No cat with id 26",
                          "statusCode":404
                        }
                        """)
                );
    }

    @Test
    @Sql("/sql/users.sql")
    @Sql("/sql/cats.sql")
    public void createCat_succeed() throws Exception {
        var requestBuilder = MockMvcRequestBuilders
                .post("/api/v0.1.0/cats/create")
                .with(user("m3@mail.ru")
                        .password("Password11!!")
                        .authorities(
                                List.of(new SimpleGrantedAuthority("USER")
                                ))
                )
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                    {"name": "Simon3_2", "breed": "b2", "color": "GREY", "birthday": "2013-02-02"}
                """);

        this.mockMvc.perform(requestBuilder)

                .andDo(print())
                .andExpectAll(
                        status().isOk(),
                        content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON),
                        content().json("""
                          {"ownerId":22,"catId":1,"name":"Simon3_2","breed":"b2","catColor":"GREY","birthday":"2013-02-02","friends":[],"reqsIn":[],"reqsOut":[]}
                        """)
                );

        requestBuilder = MockMvcRequestBuilders
                .get("/api/v0.1.0/cats/my")
                .with(user("m3@mail.ru")
                        .password("Password11!!")
                        .authorities(List.of(new SimpleGrantedAuthority("USER")))
                );

        this.mockMvc.perform(requestBuilder)

                .andDo(print())
                .andExpectAll(
                        status().isOk(),
                        content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON),
                        content().json("""
                        [
                            {"ownerId":22,"catId":22,"name":"Simon1","breed":"b1","catColor":"RED","birthday":"2020-12-12","friends":[],"reqsIn":[],"reqsOut":[]},
                            {"ownerId":22,"catId":23,"name":"Simon2","breed":"b1","catColor":"RED","birthday":"2020-12-12","friends":[],"reqsIn":[],"reqsOut":[]},
                            {"ownerId":22,"catId":1,"name":"Simon3_2","breed":"b2","catColor":"GREY","birthday":"2013-02-02","friends":[],"reqsIn":[],"reqsOut":[]}
                      ]
                      """)
                );
    }

    @Test
    @Sql("/sql/users.sql")
    @Sql("/sql/cats.sql")
    public void createCatByAdmin_throwsNotPermittedException() throws Exception {
        var requestBuilder = MockMvcRequestBuilders
                .post("/api/v0.1.0/cats/create")
                .with(user("m1@mail.ru")
                        .password("Password11!!")
                        .authorities(
                                List.of(new SimpleGrantedAuthority("ADMIN")
                                ))
                )
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                    {"name": "Simon3_2", "breed": "b2", "color": "GREY", "birthday": "2013-02-02"}
                """);

        this.mockMvc.perform(requestBuilder)

                .andDo(print())
                .andExpectAll(
                        status().is(405),
                        content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON),
                        content().json("""
                          {
                            "path":"/api/v0.1.0/cats/create",
                            "message":"Access Denied",
                            "statusCode":405
                          }
                        """)
                );
    }

    @Test
    @Sql("/sql/users.sql")
    @Sql("/sql/cats.sql")
    public void deleteCat_succeed() throws Exception {
        var requestBuilder = MockMvcRequestBuilders
                .delete("/api/v0.1.0/cats/23")
                .with(user("m3@mail.ru")
                        .password("Password11!!")
                        .authorities(
                                List.of(new SimpleGrantedAuthority("USER")
                                ))
                );

        this.mockMvc.perform(requestBuilder);

        requestBuilder = MockMvcRequestBuilders
                .get("/api/v0.1.0/cats/my")
                .with(user("m3@mail.ru")
                        .password("Password11!!")
                        .authorities(List.of(new SimpleGrantedAuthority("USER")))
                );

        this.mockMvc.perform(requestBuilder)

                .andDo(print())
                .andExpectAll(
                        status().isOk(),
                        content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON),
                        content().json("""
                        [
                            {"ownerId":22,"catId":22,"name":"Simon1","breed":"b1","catColor":"RED","birthday":"2020-12-12","friends":[],"reqsIn":[],"reqsOut":[]}
                        ]
                        """)
                );
    }

    @Test
    @Sql("/sql/users.sql")
    @Sql("/sql/cats.sql")
    public void deleteCatByAdmin_succeed() throws Exception {
        var requestBuilder = MockMvcRequestBuilders
                .delete("/api/v0.1.0/cats/23")
                .with(user("m1@mail.ru")
                        .password("Password11!!")
                        .authorities(
                                List.of(new SimpleGrantedAuthority("ADMIN")
                                ))
                );

        this.mockMvc.perform(requestBuilder);

        requestBuilder = MockMvcRequestBuilders
                .get("/api/v0.1.0/cats/my")
                .with(user("m3@mail.ru")
                        .password("Password11!!")
                        .authorities(List.of(new SimpleGrantedAuthority("USER")))
                );

        this.mockMvc.perform(requestBuilder)

                .andDo(print())
                .andExpectAll(
                        status().isOk(),
                        content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON),
                        content().json("""
                        [
                            {"ownerId":22,"catId":22,"name":"Simon1","breed":"b1","catColor":"RED","birthday":"2020-12-12","friends":[],"reqsIn":[],"reqsOut":[]}
                        ]
                        """)
                );
    }

    @Test
    @Sql("/sql/users.sql")
    @Sql("/sql/cats.sql")
    public void deleteCat_failed() throws Exception {
        var requestBuilder = MockMvcRequestBuilders
                .delete("/api/v0.1.0/cats/25")
                .with(user("m3@mail.ru")
                        .password("Password11!!")
                        .authorities(
                                List.of(new SimpleGrantedAuthority("USER")
                                ))
                );

        this.mockMvc.perform(requestBuilder)

                .andDo(print())
                .andExpectAll(
                        status().is(405),
                        content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON),
                        content().json("""
                        {
                          "path":"/api/v0.1.0/cats/25",
                          "message":"Access denied",
                          "statusCode":405
                        }
                        """)
                );
    }

    @Test
    @Sql("/sql/users.sql")
    @Sql("/sql/cats.sql")
    public void createUserTest_succeed() throws Exception {
        var requestBuilder = MockMvcRequestBuilders
                .post("/api/v0.1.0/auth/signup")
                .with(anonymous())
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                {
                  "email": "ma@mail.ru",
                  "password": "Password11!!",
                  "role": "USER"
                }
                """);

        this.mockMvc.perform(requestBuilder)

                .andDo(print())
                .andExpectAll(
                        status().isOk(),
                        content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON)
                );
    }


    @Test
    @Sql("/sql/users.sql")
    @Sql("/sql/cats.sql")
    public void createUserTest_failed() throws Exception {
        var requestBuilder = MockMvcRequestBuilders
                .post("/api/v0.1.0/auth/signup")
                .with(anonymous())
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                {
                  "email": "m1@mail.ru",
                  "password": "Password11!!",
                  "role": "USER"
                }
                """);

        this.mockMvc.perform(requestBuilder)

                .andDo(print())
                .andExpectAll(
                        status().is(400),
                        content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON),
                        content().json("""
                        {
                          "path":"/api/v0.1.0/auth/signup",
                          "message":"Email is already taken",
                          "statusCode":400
                        }
                        """)
                );
    }

    @Test
    @Sql("/sql/users.sql")
    @Sql("/sql/cats.sql")
    public void loginUser_succed() throws Exception {
        var requestBuilder = MockMvcRequestBuilders
                .post("/api/v0.1.0/auth/login")
                .with(anonymous())
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                {
                  "email": "m1@mail.ru",
                  "password": "Password11!!"
                }
                """);

        this.mockMvc.perform(requestBuilder)

                .andDo(print())
                .andExpectAll(
                        status().isOk(),
                        content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON)
                );
    }

    @Test
    @Sql("/sql/users.sql")
    @Sql("/sql/cats.sql")
    public void loginUser_failed() throws Exception {
        var requestBuilder = MockMvcRequestBuilders
                .post("/api/v0.1.0/auth/login")
                .with(anonymous())
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                {
                  "email": "ma@mail.ru",
                  "password": "Password11!!"
                }
                """);

        this.mockMvc.perform(requestBuilder)

                .andDo(print())
                .andExpectAll(
                        status().is(404),
                        content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON),
                        content().json("""
                        {
                          "path":"/api/v0.1.0/auth/login",
                          "message":"Wrong email or password",
                          "statusCode":404
                        }
                        """)
                );
    }

    @Test
    @Sql("/sql/users.sql")
    @Sql("/sql/cats.sql")
    public void getAllUsers_succeed() throws Exception {
        var requestBuilder = MockMvcRequestBuilders
                .get("/api/v0.1.0/users/all")
                .with(user("m1@mail.ru")
                        .password("Password11!!")
                        .authorities(List.of(new SimpleGrantedAuthority("ADMIN")))
                );

        this.mockMvc.perform(requestBuilder)

                .andDo(print())
                .andExpectAll(
                        status().isOk(),
                        content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON),
                        content().json("""
                        [
                            {"id":20,"email":"m1@mail.ru","role":"ADMIN","name":"alan1","birthday":"2020-12-20","cats":[]},
                            {"id":21,"email":"m2@mail.ru","role":"ADMIN","name":"alan2","birthday":"2020-12-20","cats":[]},
                            {"id":22,"email":"m3@mail.ru","role":"USER","name":null,"birthday":null,"cats":[22,23]},
                            {"id":23,"email":"m4@mail.ru","role":"USER","name":"alan4","birthday":"2020-12-20","cats":[24,25]}
                        ]
                        """)
                );
    }

    @Test
    @Sql("/sql/users.sql")
    @Sql("/sql/cats.sql")
    public void getAllUsers_failed() throws Exception {
        var requestBuilder = MockMvcRequestBuilders
                .get("/api/v0.1.0/users/all")
                .with(user("m3@mail.ru")
                        .password("Password11!!")
                        .authorities(List.of(new SimpleGrantedAuthority("USER")))
                );

        this.mockMvc.perform(requestBuilder)

                .andDo(print())
                .andExpectAll(
                        status().is(405),
                        content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON),
                        content().json("""
                        {
                          "path":"/api/v0.1.0/users/all",
                          "message":"Access Denied",
                          "statusCode":405
                        }
                        """)
                );
    }

    @Test
    @Sql("/sql/users.sql")
    @Sql("/sql/cats.sql")
    public void deleteUserById_succeed() throws Exception {
        var requestBuilder = MockMvcRequestBuilders
                .delete("/api/v0.1.0/users/22")
                .with(user("m3@mail.ru")
                        .password("Password11!!")
                        .authorities(List.of(new SimpleGrantedAuthority("USER")))
                );

        this.mockMvc.perform(requestBuilder)

                .andDo(print())
                .andExpect( status().isOk());
    }

    @Test
    @Sql("/sql/users.sql")
    @Sql("/sql/cats.sql")
    public void deleteUserByAdmin_succeed() throws Exception {
        var requestBuilder = MockMvcRequestBuilders
                .delete("/api/v0.1.0/users/22")
                .with(user("m1@mail.ru")
                        .password("Password11!!")
                        .authorities(List.of(new SimpleGrantedAuthority("ADMIN")))
                );

        this.mockMvc.perform(requestBuilder)

                .andDo(print())
                .andExpect( status().isOk());
    }

    @Test
    @Sql("/sql/users.sql")
    @Sql("/sql/cats.sql")
    public void deleteUserById_failed() throws Exception {
        var requestBuilder = MockMvcRequestBuilders
                .delete("/api/v0.1.0/users/24")
                .with(user("m3@mail.ru")
                        .password("Password11!!")
                        .authorities(List.of(new SimpleGrantedAuthority("USER")))
                );

        this.mockMvc.perform(requestBuilder)

                .andDo(print())
                .andExpectAll(
                        status().is(405),
                        content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON),
                        content().json("""
                        {
                          "path":"/api/v0.1.0/users/24",
                          "message":"Access denied",
                          "statusCode":405
                        }
                        """)
                );
    }
}
