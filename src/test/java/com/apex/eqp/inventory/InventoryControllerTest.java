package com.apex.eqp.inventory;

import com.apex.eqp.inventory.controllers.InventoryController;
import com.apex.eqp.inventory.entities.Product;
import com.apex.eqp.inventory.services.ProductService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
@RequiredArgsConstructor
class InventoryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @InjectMocks
    InventoryController inventoryController;

    @Mock
    ProductService productService;

    @BeforeEach
    public void before() throws Exception {
        mockMvc = MockMvcBuilders
                .standaloneSetup(inventoryController)
                .build();
        
        Product anObject = new Product();
        anObject.setName("ww");
        anObject.setPrice(1.0);
        anObject.setQuantity(1);
        
        //... more
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson=ow.writeValueAsString(anObject );
        
        mockMvc.perform(MockMvcRequestBuilders
                .post("/api/inventory/product")
                .content(requestJson)
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk());
    }

    @SneakyThrows
    @Test
    void shouldCallController() {
        mockMvc.perform(
                get("/api/inventory/product")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk());
    }
    
    @SneakyThrows
    @Test
    void shouldCallControllerDelete() {
        mockMvc.perform(MockMvcRequestBuilders
                .delete("/api/inventory/product/new/{id}",1)
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk());
    }
    
    @SneakyThrows
    @Test
    void shouldCallControllerCreate() {
        Product anObject = new Product();
        anObject.setName("ww");
        anObject.setPrice(1.0);
        anObject.setQuantity(1);
        
        //... more
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson=ow.writeValueAsString(anObject );
        
        mockMvc.perform(MockMvcRequestBuilders
                .post("/api/inventory/product")
                .content(requestJson)
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk());
    }

}
