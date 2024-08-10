package com.kosa.gallerygather.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
public class ReviewControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @WithMockUser(username = "testuser", roles = {"USER"})
    public void createReviewTest() throws Exception {
        String requestBody = """
            {
                "title": "쩐당",
                "content": "재밌던데용",
                "rating": 5,
                "viewDate": "2024-08-10"
            }
        """;

        mockMvc.perform(post("/api/exhibition/2/review")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isOk());
    }
}
