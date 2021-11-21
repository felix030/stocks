package com.felixklemke.stocks;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@Component
public class StocksWhen {

    private static final String GET_ALL_VIEWABLE_STOCKS = "/stocks-mgmt/external/v1/stocks/";

    @Autowired
    private MockMvc mockMvc;


    protected ResultActions getAllViewableStocks() throws Exception {
        return mockMvc
                .perform(get(GET_ALL_VIEWABLE_STOCKS)
                        .header("Content-type", MediaType.APPLICATION_JSON))
                .andDo(print());
    }
}
