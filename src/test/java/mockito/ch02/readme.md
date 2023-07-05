Varsayılan olarak, bir değer döndüren tüm methodlar için, bir stub, uygun olduğu şekilde, null, bir primitive/primitive
wrapper value'su veya boş bir koleksiyon döndürür.

Örnek olarak int/Integer icin 0, boolean/Boolean için false;

- Stubbing'ler override edilebilir.
- Örneğin, common stubbing, fixture-setup'a yerleştirilebilir, ancak test methodları üzerinde bunu override etmek
  mümkündür

**Fixture setup**, testlerin başlamadan önce gereken ortamın hazırlandığı bir adımdır. Bu adımda genellikle test
verileri oluşturulur, bağımlılıklar ayarlanır veya başka test öncesi hazırlıklar yapılır.

Lütfen stubbing'i override etmenin, fazla miktarda stubbinge işaret eden potansiyel bir code smell olduğunu unutmayın

Bir kez stub edildikten sonra, bir method her ne kadar çok kez çağrılırsa çağrılsın, her zaman stub edilmiş bir değer
döndürecektir.

Aynı method ve argümanlarla yapılan stubbingler arasında en son yapılan geçerli olacaktır. Bu nedenle, aynı methodu ve
argümanları birden fazla kez stub ederseniz, en son yapılan stubbingin geçerli olacağını unutmayın.

Diğer bir deyişle, stubbing sırasının önemi vardır, ancak bu nadiren anlamlıdır, örneğin tam olarak aynı method
çağrılarını stub ettiğinizde veya bazen argüman matchers'lar kullanıldığında vb. durumlarda önemli olabilir.

```
public class HowAboutSomeStubbing {
    @Test
    void Stubbing() {
        //You can mock concrete classes, not just interfaces
        LinkedList mockedList = mock(LinkedList.class);

        //stubbing
        when(mockedList.get(0)).thenReturn("first");
        when(mockedList.get(1)).thenThrow(new RuntimeException());

        //following prints "first"
        System.out.println(mockedList.get(0));
        
        //following throws runtime exception
        //System.out.println(mockedList.get(1));
        
        //following prints "null" because get(999) was not stubbed
        System.out.println(mockedList.get(999));

        //Bir stubbed sahte çağrının doğrulanması mümkün olsa da genellikle gereksizdir.
        //Eğer kodunuz get(0) değerine dikkat ediyorsa, o zaman genellikle başka bir şey bozulur (çoğu kez verify() çağrısı çalıştırılmadan önce bile).
        //Eğer kodunuz get(0) değerine önem vermiyorsa, o zaman bu değer stub edilmemelidir.
        verify(mockedList).get(0);
        verify(mockedList).get(999);
    }
}
```