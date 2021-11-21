package com.felixklemke.stocks;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.felixklemke.stocks.model.WebStockRequestBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@Component
public class StocksWhen {

    private static final String GET_ALL_VIEWABLE_STOCKS = "/stocks-mgmt/external/v1/stocks/";
    private static final String POST_CREATE_NEW_STOCK = "/stocks-mgmt/external/v1/stocks/";

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;


    protected ResultActions allViewableStocks() throws Exception {
        return mockMvc
                .perform(get(GET_ALL_VIEWABLE_STOCKS)
                        .header("Content-type", MediaType.APPLICATION_JSON))
                .andDo(print());
    }

    public ResultActions createStock(WebStockRequestBody creationRequest) throws Exception {
        return mockMvc
                .perform(post(POST_CREATE_NEW_STOCK)
                        .header("Content-type", MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(creationRequest)))
                .andDo(print());
    }
}
