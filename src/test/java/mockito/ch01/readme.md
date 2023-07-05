Aşağıdaki örnekler bir List'i mocklamaktadır, çünkü çoğu kişi interface'i (add(), get(), clear() gibi methodları) bilir.
Gerçekte, lütfen List sınıfını mocklamayın. Bunun yerine gerçek bir örnek kullanın.

```
public class LetsVerifySomeBehaviour {
    //mock creation
    List mockedList = mock(List.class);

    @Test
    void mockedList() {
        //using mock object
        mockedList.add("one");
        mockedList.clear();

        //verification
        verify(mockedList).add("one");
        verify(mockedList).clear();
    }
}
```

Oluşturulduktan sonra, bir mock tüm interaction'ları hatırlar. Ardından ilgilendiğiniz interaction'ları seçici olarak
doğrulayabilirsiniz. Yukarıda ki örnekte ilk olarak List'e içerisine "one" adlı string'i ekliyorum ve ardından List'i
clear ediyorum. Bunlar interaction'larımız. Daha sonra add mockedList'e add methodu veya clear() methodu çağırıldımı
kontrolünü yapabiliyorum