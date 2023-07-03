package baeldung.mockingASingletonWithMockito;

import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class ProductServiceTest {
    @Test
    void givenValueExistsInCache_whenGetProduct_thenDAOIsNotCalled() {
        //given
        ProductDAO productDAO = mock(ProductDAO.class);
        CacheManager cacheManager = mock(CacheManager.class);
        Product product = new Product("product1", "description");

        //when
        when(cacheManager.getValue(any(), any())).thenReturn(product);
        ProductService productService = new ProductService(productDAO, cacheManager);
        productService.getProduct("product1");

        verify(productDAO, times(0)).getProduct(any());
    }

    @Test
    void givenValueExistsInCache_whenGetProduct_thenDAOIsNotCalled_mockingStatic() {
        ProductDAO productDAO = mock(ProductDAO.class);
        CacheManager cacheManager = mock(CacheManager.class);
        Product product = new Product("product1", "description");

        try (MockedStatic<CacheManager> cacheManagerMock = mockStatic(CacheManager.class)) {
            cacheManagerMock.when(CacheManager::getInstance).thenReturn(cacheManager);
            when(cacheManager.getValue(any(), any())).thenReturn(product);

            ProductService productService = new ProductService(productDAO);
            productService.getProduct("product1");

            verify(productDAO, times(0)).getProduct(any());
        }
    }
}
