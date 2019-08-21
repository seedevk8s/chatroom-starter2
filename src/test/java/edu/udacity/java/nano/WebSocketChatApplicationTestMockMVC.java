package edu.udacity.java.nano;

import edu.udacity.java.nano.chat.Message;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest
public class WebSocketChatApplicationTestMockMVC {
    @Autowired
    private MockMvc mockMvc;

    private Message message;

    //	Testing login / logout page
    @Test
    public void loginPage() throws Exception {
        this.mockMvc.perform(get("/"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("login"));
    }

    // Testing if the user is correctly handled after the login action
    @Test
    public void loginUser() throws Exception {
        this.mockMvc.perform(get("/index?username=hojin"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(model().attribute("username", equalTo("hojin")));
    }


    // Testing if the chat page is correctly handled
    @Test
    public void chatPage() throws Exception {
        this.mockMvc.perform(get("/index?"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(model().attribute("username", equalTo("CUSTOMER")))
                .andExpect(view().name("chat"));
    }
}
