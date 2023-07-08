# Chapter 9

## 9.4. BDD: ‘Given’, ‘When’, ‘Then’

Bu bölümde, unit testlerin biraz farklı bir tarzda nasıl yazılabileceğini sunmak istiyorum. Bu tarz, Davranış Odaklı
Geliştirme (BDD) yaklaşımından gelir.

BDD, unit test etmek yerine çok daha yüksek seviye testlere (yani end-to-end testlere) uygulanabilir olduğundan,
detaylı bir şekilde açıklamayacağım. BDD hakkında internet üzerinde birçok iyi kaynak bulunmaktadır ve size biraz zaman
ayırıp bunları okumanızı öneririm. Önerim, unit testleri BDD tarzında yazma teknik yönlerine odaklanmamızdır. Ancak
önce, BDD'yi birkaç cümleyle hızlıca tanıtmama izin verin:

BDD, özellikle yüksek seviye testlerde kullanılır. TDD'ye göre daha çok client odaklıdır. BDD fikirlerinin bazıları
unit testlerine de uygulanabilir ve çok iyi sonuçlar verir.

BDD, testlerin okunabilirliğine büyük önem verir. Birçok BDD framework'u, testlerin neredeyse doğal dilde yazılmasına
izin verir.

Bazı insanlar, BDD'nin "doğru yapılmış TDD" olduğunu söyler - bu da onu öğrenmek için biraz çaba harcamanın daha fazla
nedenini gösterir.

Aslında, bu kitapta sunulan bazı unsurlar - BDD'nin önerdiğiyle çok uyumludur. Bununla birlikte, çoğu geliştirici BDD'yi
ünlü 'given/when/then' kalıbıyla ilişkilendirir, ki bu da BDD'nin ticari markasıdır. İşte tam olarak bu konu tartışmak
istediğim şey.

Given/When/Then fikri, senaryoların aşağıdaki şekilde yazılmasından kaynaklanır:

Verilen initial context (gerekler) olduğunda, bir olay gerçekleştiğinde, belirli sonuçları sağlayın.

Böyle bir şekilde yazılmış bir örnek senaryo aşağıdaki gibi olabilir:

* GIVEN - Verilen kullanıcının "John" olarak oturum açtığı durumda,
* WHEN - kullanıcının bir profil düğmesine tıkladığı durumda,
* THEN - o zaman kullanıcının verileriyle dolu bir profil sayfası görüntülenir.

## 9.4.1. Testing BDD-Style

JUnit, BDD'yi desteklemek için herhangi bir syntactic sugar sağlamaz. BDD'nin tarzını taklit etmenin yaygın yolu, "
given/when/then" kelimelerini yorumlara yerleştirmektir. Bu, aşağıda gösterildiği gibi yapılan bir testin eşdeğeridir.

```
public class BankAccountBDDTest {
    @Test
    void shouldBeEmptyAfterCreation(){
        //GIVEN
        BankAccount bankAccount = new BankAccount();

        //WHEN
        int balance = bankAccount.getBalance();

        //THEN
        assertEquals(0, balance);
    }

    @Test
    void shouldAllowToCreditAccount(){
        //GIVEN
        BankAccount bankAccount = new BankAccount();

        //WHEN
        bankAccount.deposit(100);

        //THEN
        int balance = bankAccount.getBalance();
        assertEquals(100,balance);
    }

    @Test
    void shouldAllowToDebitAccount(){
        //GIVEN
        BankAccount bankAccount = new BankAccount();

        //WHEN
        bankAccount.deposit(100);
        bankAccount.withdraw(40);

        //THEN
        int balance = bankAccount.getBalance();
        assertEquals(60,balance);
    }
}
```

Yukarıdaki örnekte şaşırtıcı bir şey yok mu? Bence yok. Şu ana kadar tartıştıklarımızdan sadece birkaç ayrıntıda
farklılık gösteriyor:

* belirli bir hikayeyi test etmek için gereken her şeyi (nesnelerin kurulumunu da içeren) içeren biraz daha uzun test
  methodları,
* her bir test methodu için net bir yapı
* action (örneğin, account.getBalance()) ve assertion (örneğin, assertEquals(60, balance)) arasında net bir ayrım.

unit testler söz konusu olduğunda, "saf" BDD'nin bir örneği diye bir şey yoktur.Yukarıda ki örnek, bir testi BDD
tarzında yazmanın bir olası yolunu göstermektedir, ancak bu muhtemelen yapmanın tek geçerli yolu değildir.

Yukarıda ki testlerde BDD'de test methodu yapısının gerçekten önemli olduğu görülüyor. Bazı test yöntemleri (biraz)
gereğinden daha uzun olabilir, sadece when ve given aşamaları arasındaki net ayrımı sağlamak için (örneğin, bir bakiye
değişkeninin var olmasına gerek yoktur - assertEquals(60, account.getBalance()) yeterli olurdu). Bu yaklaşım, her test
yöntemine hem netlik getirir hem de biraz tekrarlamayı beraberinde getirir.

Dikkat edilmesi gereken başka bir nokta, her test yönteminin içinde tek bir assertion ifadesi bulunmasıdır. Bu daha önce
eleştirilen bir konudur; ancak BDD yaklaşımıyla birlikte anlam kazanmaya başlar.

## 9.4.2. Mockito BDD-Style

JUnit'a kıyasla, Mockito BDD tarzında test yazmayı kolaylaştırır. BDDMockito sınıfı sağlar ve bu sınıf aracılığıyla,
önceki şekilde Mockito.when() yöntemini kullandığımız gibi given() yöntemini kullanabiliriz (yani, test dublörlerinde
beklentileri belirlemek için). Bu, Mockito testlerini daha BDD benzeri hale getirir. Aşağıda ki kod bunu göstermektedir

### MockitoTestBddStyle

```
public class BddMockitoTest {
    private static final int ID_USER = 329847;

    @Test
    void shouldReturnClient(){
        //GIVEN
        User USER = new User();
        UserDAO userDAO = mock(UserDAO.class); (2)
        UserService userService = new UserService(userDAO); (3)
        given(userDAO.getUserById(ID_USER)).willReturn(USER); (4)

        //WHEN
        User user = userService.loadUser(ID_USER);

        //THEN
        assertEquals(USER,user);
    }
}
```

2 - 3 - SUT'yi (Service Under Test) kurma ve bir test dublörünü (DAO) enjekte etme işlemi.

4 - given() yönteminin kullanımı, BDD'nin given/when/then ritmini korumaya yardımcı olur. given(dao.getUser(ID_USER))
.willReturn(USER); şeklindeki kullanım, when() yöntemi kullanılmadan da aynı işlevi görür. Bu, test kodunun "given"
kısmında olduğumuz için karışıklık yaratmaz.

Yukarıdaki örnekte gösterildiği gibi, bu güzel bir syntactic sugar, ancak devrim niteliğinde bir şey değildir.
Bununla birlikte, BDD testinin ritmini ifade etmeye yardımcı olması iyi bir şeydir.

## 9.5. Reducing Boilerplate Code

Mockito ile sıkça çalışıyorsanız, test dublörünün oluşturulması için boilerplate code'un azaltılmasından
hoşlanabilirsiniz. Her test yönteminde görünen myMock = mock(SomeClass.class) gibi tüm bu örnekler gerçekten can sıkıcı,
değil mi? İyi haber şu ki, Mockito bu tekrarlayıcı kodu ortadan kaldırmayı mümkün kılar. Bu bölümde, Mockito'nun
anotasyonlarını ve tek satırda yapılan stubbing özelliğini inceleyeceğiz.

Tutarlılık önemlidir. Bu bölümde sunulan özellikleri kullanmayı veya kullanmamayı seçseniz de, bunun konusunda tutarlı
olun.

Aşağıdaki kod, bu yeni Mockito özelliklerini göstermek için kullanılacak iki basit sınıfı göstermektedir.
İsimleri - SUT (System Under Test) ve Collaborator - amaçlarını her iki durumda da açıkça ortaya koyar.

```
public class Collaborator {
    public String doSth(){
        return "xyz";
    }
}
```

SUT class'ı;

```
public class SUT {
    private Collaborator collaborator;
    
    public void setCollaborator(Collaborator collaborator) {
        this.collaborator = collaborator;
    }
    
    public String useCollaborator(){
        return collaborator.doSth(); (1)
    }
}
```

1 - Collaborator'ün döndürdüğü değeri döndürmesi beklenen basit bir delegating methodu.

Bu tür bir functionality'nin tipik bir testi aşağıda gösterilmiştir. Bu kez daha yaygın bir senaryo olduğuna inandığım
için bir setUp() methodu kullanmaya karar verdim.

```
public class BoilerPlateCodeTest {
    private static Collaborator collaborator;
    private static SUT sut;

    @BeforeAll
    static void setUp(){ (1)
        sut = new SUT();
        collaborator = mock(Collaborator.class);
        sut.setCollaborator(collaborator);
    }

    @Test
    void shouldReturnABC(){
       when(collaborator.doSth()).thenReturn("abc");
       assertEquals("abc",sut.useCollaborator());
    }
}
```

1 - Bu noktada tekrarlayan kod ortaya çıkar - SUT'nin oluşturulması, test dublörlerinin oluşturulması ve enjekte
edilmesi.

Soru şu ki, bununla ilgili olarak ne yapabiliriz; Gerçekten de, test dublörü kullanılan test sayısı arttıkça, kurulum
faaliyetlerinin boyutunu azaltmanın önemi daha da belirgin hale gelir.

## 9.5.1. One-Liner Stubs

İlk yapabileceğimiz şey, işbirlikçinin oluşturulmasını ve stubbing işlemini biraz daha kısa hale getirmektir. Mockito,
tek satırda stubbing yapmayı mümkün kılar.

### Reducing boilerplate code with one-line stubbing

```
public class OneLinerStubbingTest {
    private static final Collaborator collaborator =
            when(mock(Collaborator.class).doSth()).thenReturn("abc").getMock(); (1)
    private static SUT sut;

    @BeforeAll
    static void setUp(){ (2)
        sut = new SUT();
        sut.setCollaborator(collaborator);
    }

    @Test
    void shouldReturnABC(){
        assertEquals("abc",sut.useCollaborator());
    }
}
```

1 - Bu satırda, bir test dublörü oluşturulur ve stubbing işlemi yapılır. İşlem zincirinin sonunda getMock() yöntemini
dikkate alın.

2 - setUp() methodunda test dublörü create edilmiyor

Kişisel olarak, bu özelliği çok fazla beğenmediğimi anlıyorum. Kullanımı ciddi bir kod azaltma sağlamaz ve benim
görüşüme göre biraz karmaşık bir görünüm sunar. Eğer bunu kullanmayı planlıyorsanız, gerçek oluşturma ve stubbing kodunu
setUp() yönteminizde bir araya getirmenizi öneririm, böylece tüm oluşturma kodu tek bir yerde olur. Bu yaklaşım,
testlerinizin netliğini ve bakımını artırabilir.

## 9.5.2. Mockito Annotations

Şimdi boilerplate kodun azaltılmasının ana noktasına geçelim.

### Injecting test doubles using Mockito annotations

```
public class InjectMockTest {
    @Mock
    private Collaborator collaborator;
    @InjectMocks
    private SUT sut;

    @BeforeEach
    void openMocks() {
        MockitoAnnotations.openMocks(this);
    }
    
    @Test
    void shouldReturnABC() {
        when(collaborator.doSth()).thenReturn("abc");
        assertEquals("abc", sut.useCollaborator());
    }
}
```

@BeforeEach ve ardından kullanılan openMocks methodu mock ve injectmocks anotasyonlari icin mecburidir

@InjectMocks, Mockito'ya belirli bir nesneye test dublörlerini enjekte etmesini söyleyen bir anotasyondur.

Önceki setUp() yöntemiyle karşılaştırıldığında, burada SUT oluşturma ve işbirlikçi ayarlama işlemi bulunmamaktadır.

SUT sınıfından setter (setCollaborator()) yöntemini kaldırdığınızda ve testin hala çalıştığını gördüğünüzde daha da
şaşıracaksınız. Bunun nedeni, Mockito'nun gerçekte field'leri ayarlamak için setterları çalıştırmaması, bunun yerine
doğrudan onları değiştirmek için bir reflection mekanizması kullanmasıdır.

Şimdi, öğrendiklerimizi özetleyelim ve tartışalım.
Daha iyi, daha temiz, daha okunabilir test kodları için çabalarımızda, Mockito anotasyonları olan @Mock ve @InjectMocks
hakkında bilgi edindik. Her ikisi de mock'ların oluşturulması ve enjekte edilmesiyle ilgili boilerplate code'u azaltmak
için kullanılabilir. Onları kullanarak, aşağıdaki faydaları elde ettik:

* test kodunun kurulum aşamasını kısalttık,
* tekrarlayan, boilerplate test dublörü oluşturma ve kod ayarlama işlemlerinden kurtulduk.

Ancak, test dublörü oluşturma ve enjeksiyon sorumluluğunu Mockito'ya devrederken, belirli şeylere uyum sağlamamız
gerekmektedir. Bu şunları içermektedir:

* SUT, enjeksiyon gerçekleşmeden önce oluşturulmalıdır
* Mockito'nun reflection'i kullandığını ve bu durumun kod kapsamını düşürebileceğini belirtmek isterim (ve ayrıca
  setter'ları atladığını).

@Mock ve @InjectMocks annotasyonlarını kullanmanın, kodun okunabilirliği üzerinde iyi ve kötü etkileri olabilir. Bir
yandan kod daha kısa ve tekrarlayan ifadelerle dolu olmaktan kurtulduğu için daha özlü hale gelir. Diğer yandan, daha
büyük testlerde "nerede ne oluşturulduğunu" izlemekte zorlanabilir ve tam tersi etki olan okunabilirliğin azalmasına
neden olabilir.

## 9.6. Creating Complex Objects

Şimdiye kadar test amaçlı oluşturduğumuz nesnelerin hepsi oldukça basit oldu. Bu, unit testiyle ilgili çeşitli
sorunları göstermek için kullanışlı olabilir, ancak hiç de gerçekçi değildi. Bu bölümde, test amacıyla karmaşık
nesnelerin oluşturulmasıyla ilgili sorunlara göz atacağız.

Rich domain object kullanan testleri yazmak sıkıcı bir iş olabilir. Farklı işlevlerini test etmek için bunları
farklı özellik kümeleriyle ayarlamanız gerekebilir. Ne yazık ki, bu, anlaşılması veya bakımı zor olan uzun ve anlaşılmaz
testlere yol açar. Aşağıdaki özellikler, test-fixture oluşturmanın zayıf bir yaklaşımın belirtileridir:

* her test methodunda çok fazla test düzenek kodu,
* duplicated code - genellikle birden fazla test benzer nesnelere ihtiyaç duyduğu için.
* aşırı detaylı nesne oluşturma kısımlarıyla test abstraction'ın kirlenmesi.

Bu durumu iyileştirmek için yaygın bir yaklaşım, domain nesnelerinin oluşturulmasından sorumlu olacak bazı private
methodlar oluşturmaktır. Ne yazık ki, bu genellikle hastalığı gerçekten tedavi etmez, ancak birbirini çağıran küçük
methodların iç içe geçmiş bir ağıyla değiştirir. Bu durum anlamak, hata ayıklamak ve bakım yapmak için gerçek bir kâbus
olabilir.

Bu bölümde, domain nesnelerinin oluşturulmasıyla başa çıkmak için iki yaklaşımı öğreneceğiz. Bu yaklaşımlar,
test-fixture kurulumuyla ilgili zararı en azından sınırlamak için kullanılabilir, ancak testlerimizi bu açıdan mükemmel
hale getirme garantisi vermez.

Anladığım kadarıyla, tartışmamızda kullanacağımız bir Employee sınıfı üzerinde çalışacağız. Bu sınıfın genel olarak iyi
bir şey olduğu söylenen bir şekilde değiştirilemez (immutable) olduğunu varsayacağız. Employee sınıfının kodunu
göstermek yerine, bu sınıfın birçok alanı olduğunu ve bazılarının temel türler olduğunu ve bazılarının Address, Phone ve
Position türünde nesneler olduğunu söylemek yeterli olacak. Ayrıca, tüm field'lerin bir constructor method aracılığıyla
başlatıldığını (setter yöntemler olmadığını) varsayabiliriz.

Bu sefer, aslında testlerin (doğrulama) kendilerine değil, yalnızca nesne oluşturma işlemlerine odaklanıyoruz. Aşağıda
bir örnek olarak test kodunda Employee nesnelerinin nasıl oluşturulduğunu göstereceğim.

```
public class EmployeeTest {
    private static final Phone MOBILE_PHONE = new Phone("123-456-789"); (1)
    private static final Phone STATIONARY_PHONE = new Phone("123-456-789");
    private static final Address HOME_ADDRESS = new Address("any street");

    private Employee createEmployee(String name, String surname, Position ceo) {
        return new Employee(name, surname, ceo, HOME_ADDRESS, MOBILE_PHONE, STATIONARY_PHONE); (2)
    }

    @Test
    void ceoCanDoEverything() { (3)
        Calendar calendar = Calendar.getInstance();
        calendar.set(2010, Calendar.MARCH, 1);
        Date startCeo = calendar.getTime();
        calendar.add(Calendar.DATE,1);
        Date endCeo = calendar.getTime();
        Position ceo = new Position("ceo",startCeo,endCeo);
        Employee ceoEmpl = createEmployee("ceoName","ceoSurnam",ceo); (4)
    }

    @Test
    void pmCanDoALot(){ (5)
        Calendar calendar = Calendar.getInstance();
        calendar.set(2022,Calendar.APRIL,11);
        Date startPm = calendar.getTime();
        calendar.add(Calendar.YEAR,3);
        Date endPm = calendar.getTime();
        Position ceo = new Position("pm",startPm,endPm);
        Employee pmEmpl = createEmployee("pmName","pmSurname",ceo); (6)
    }
}
```

1 - 2 - Test vakaları için önemli olmayan bazı Employee sınıfı özellikleri testler arasında tekrar kullanılır.

3 - 5 - Her iki test methodu da, bir çalışanın oluşturulmasından sorumlu olan benzer ancak biraz farklı kod içerir.

4 - 6 - createEmployee() methodunun yürütülmesi.

Bu kodun ihtiyaç duyduğu sınıfları ve recordları şu şekildedir

```
public record Employee(String name, String surname, Position position, Address address, Phone mobilePhone,
                       Phone stationaryPhone) {
}

public record Address(String address) {
}

public record Phone(String phoneNumber) {
}

public record Position(String position, Date start,Date end) {
}
```

Yukarıda gösterilen kod hakkında özel bir şey yok. Hepimizin birden fazla kez oluşturduğu türden bir kod. Özellikle
güzel veya okunabilir değil, ama amacını yerine getiriyor. createEmployee() adlı private bir method, benzer nesnelerin
oluşturulmasını en azından bir ölçüde kolaylaştırıyor.

yukarıda ki örnekte gösterilen test kodu, Employee sınıfının ayrıntılarını biliyor. Kodu daha kolay okunur hale
getirecek makul bir soyutlama yok. Test methodlarında gerçekten yapmak istediğimiz şey, bir proje yöneticisi ve bir CEO
oluşturmaktır. Ancak bunu "sadece böyle" yapmak mümkün değildir - bu basit şeyi başarmak için Employee sınıfının bazı
özelliklerine derinlemesine girmemiz gerekiyor. Bu, test kodunun okunabilirliğini düşürür.

Yukarıda ki kod örneğine baktığımızda, gelecekteki gelişimini tahmin edebiliriz. Muhtemelen daha fazla statik değer
kullanılacaktır (örneğin, başlangıç ve bitiş tarihleri önemli mi? - eğer değillerse, statik değerlere dönüşeceklerdir).
Daha sofistike test senaryolarının uygulanması sırasında, çalışanların bazı özellikleri, örneğin cep telefonu, önem
kazanacaktır. Ardından, default veya private değerlerle belirli alanları ayarlanmış çalışanları oluşturmayı mümkün kılan
yeni private utility methodlar oluşturulacaktır. Private methodlar birbirini çağırmaya başlayacak ve kod büyüyecektir.
Çok geçmeden, onunla çalışmak artık keyifli olmayacaktır.

Nesnelerin immutable özelliği genel olarak iyi bir özellik olsa da, aynı türden birden fazla nesne oluşturmayı oldukça
zorlaştırır. Bir test methodunda (veya bazı "setup" bölümlerinde) oluşturulan bir nesneyi yeniden kullanamaz ve
özelliklerini setterlar kullanarak değiştiremezsiniz. Eğer Employee sınıfı değişmez olmasaydı, testler arasında onu
yeniden kullanmak daha kolay olurdu. İlerleyen bölümlerde, değişmezliği daha az zorlu hale getirmek için nasıl
çalışılacağını öğreneceğiz.

## 9.6.1. Mummy Knows Best

Şimdi bir "Object Mother" pattern'i tanıtmak zamanı geldi. Temel olarak, bu, test-fixture oluşturma amacıyla kullanılan
bir Factory pattern'idir. Her "Object Mother" methodu tek bir birleştirici (aggregate) oluşturur. "Object Mother", nesne
oluşturmayı merkezileştirir ve niyeti açıklayan methodlar sağlayarak testleri daha okunabilir hale getirir. "Object
Mother"ı, önceki bölümde sunulan private utility methodlara daha nesne odaklı bir alternatif olarak düşünebiliriz.

### Object Mother pattern

```
public class EmployeeObjectMotherTest {
    @Test
    void ceoCanDoEveryThing() {
        Employee employee = ObjectMotherEmployee.ceoEmployee();
    }

    @Test
    void pmCanDoALot() {
        Employee employee = ObjectMotherEmployee.pmEmployee();
    }
}
```

Yukarıdaki kod çok daha net ve Employee sınıfının implementasyon ayrıntılarına girmiyor. Tüm bunlar ObjectMotherEmployee
sınıfı içinde yer alıyor. Test kodu ile nesnelerin oluşturulması kodu arasındaki bu sorumluluk ayrımı kesinlikle iyi bir
şeydir.

Bazı uygulamalarda, Object Mother, nesnelerin testler sırasında değiştirilmesini kolaylaştıran yöntemler sağlayarak
standart fabrika deseninin ötesine geçebilir. Örneğin, addAddress() yöntemi, yeni bir çalışanın adresinin ayarlanmasını
kolaylaştırmak için kullanılabilir. Hatta bu method, immutable bir Employee sınıfıyla bile çalışabilir. Örneğin, bir
Employee nesnesinden diğerine veri kopyalayarak bir constructor fonksiyonu aracılığıyla çalışabilir.

Gerçekten de, ObjectMotherEmployee sınıfının gereksiz şişebileceği ve yönetiminin zorlaşabileceği bir risk vardır.
Methodların ve bu methodlar arasındaki bağımlılıkların artmasıyla birlikte, anlamak ve sürdürmek zorlaşabilir.

## 9.6.2. Test Data Builder

Başka bir yaklaşım olarak, farklı bir oluşturma deseni olan Test Data Builder'ı kullanabiliriz. Bu desen de test kodu
düşünülerek oluşturulmuştur. Bazı ek kodlamalar gerektirse de, nesne oluşturmayı özelleştiren bir tür iç DSL (Domain
Specific Language - Alan Özgü Dil) sunar. Bu da yeni olanaklar sunar.

Aşağıdaki kod örneğine bakalım. Bu örnek, Employee türündeki farklı nesneleri oluşturmak için kullanılacak
yeni bir sınıf olan EmployeeBuilder'ı sunar. Kısalık için bazı alanlar ve yöntemler atlanmıştır.

```
public class EmployeeBuilder {
    private String firstname;
    private String lastname;
    private Address address;
    private Position position;
    private Phone mobilePhone;
    private Phone stationaryPhone;

    public EmployeeBuilder withFirstName(String firstname){ (1)
        this.firstname = firstname;
        return this;
    }

    public EmployeeBuilder withLastname(String lastname){
        this.lastname = lastname;
        return this;
    }

    public EmployeeBuilder withAddress(Address address){
        this.address = address;
        return this;
    }

    public EmployeeBuilder withPosition(Position position){
        this.position = position;
        return this;
    }

    public EmployeeBuilder withMobilePhone(Phone mobilePhone){
        this.mobilePhone = mobilePhone;
        return this;
    }

    public EmployeeBuilder withStationaryPhone(Phone stationaryPhone){
        this.stationaryPhone = stationaryPhone;
        return this;
    }

    public Employee build(){
        return new Employee(firstname,lastname,position,address,mobilePhone,stationaryPhone); (2)
    }
}
```

1 - Gerçekten de, Employee sınıfının her bir field'i, bir EmployeeBuilder örneği döndüren bir setter yöntemiyle temsil
edilir. Bu sayede methodları istediğimiz sırayla zincirleme şeklinde kullanabiliriz.

2 - Test Data Builder deseninde, build() yöntemi, Employee sınıfının bir constructor'ını çağırır ve oluşturulan nesnenin
sonucunu döndürür. Bu yöntem, yapılandırılmış değerleri bir araya getirir ve bunları kullanarak nihai nesneyi oluşturur.

EmployeeBuilder'a ek olarak, Employee sınıfı tarafından (kompozisyon yoluyla) kullanılacak diğer tüm domain sınıflarının
da constructor'larına ihtiyacımız var. Bu constructor'lar, daha önce tartıştığımız EmployeeBuilder sınıfına çok benzer
olacaktır.

```
public class PositionBuilder {
    private String title;
    private Date from;
    private Date to;

    public PositionBuilder withTitle(String title) {
        this.title = title;
        return this;
    }

    public PositionBuilder start(int year, int mount, int day) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, mount, day);
        this.from = calendar.getTime();
        return this;
    }

    public PositionBuilder end(int year, int mount, int day) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(year,mount,day);
        this.to = calendar.getTime();
        return this;
    }

    public Position build(){
        return new Position(title,from,to);
    }
}
```

Yukarıda ilginç olan, start() ve end() metodlarıdır. Bu metodlar, onları kullanmayı kolaylaştıran tamsayıları (
yıl, ay, gün) parametre olarak alır. Bir dakika içinde göreceğimiz gibi, bu builder'ın API'sinde primitive tipler
kullanarak, client'larını Date nesneleri oluşturma sürecine dahil etmekten kurtarırız.

```
public class AddressBuilder {
    private String address;

    public AddressBuilder withAddress(String address){
        this.address = address;
        return this;
    }

    public Address build(){
        return new Address(address);
    }
}
```

```
public class PhoneBuilder {
    private String phoneNumber;

    public PhoneBuilder withPhoneNumber(String phoneNumber){
        this.phoneNumber = phoneNumber;
        return this;
    }

    public Phone build(){
        return new Phone(phoneNumber);
    }
}
```

Şimdi, bu tür iki builder'ın nasıl testlerde kullanılabileceğine bakalım.

```
public class EmployeeTestDataBuilderTest {
    private EmployeeBuilder anEmployee() { (1)
        return new EmployeeBuilder()
                .withFirstName("John")
                .withLastname("Doe")
                .withMobilePhone(new PhoneBuilder().withPhoneNumber("123-456-789").build())
                .withStationaryPhone(new PhoneBuilder().withPhoneNumber("456-789-012").build())
                .withAddress(new AddressBuilder().withAddress("some street").build());
    }

    @Test
    void pmCanDoALot() {
        Employee pmEmpl = anEmployee() (2)
                .withPosition(
                        new PositionBuilder() (3)
                                .withTitle("PM")
                                .start(2010, 1, 1) (4)
                                .end(2011, 7, 3) (4)
                                .build() (4)
                ).build();
    }

    @Test
    void ceoCanDoEverything() {
        Employee ceoEmpl = anEmployee() (5)
                .withPosition(
                        new PositionBuilder()
                                .withTitle("ceo") (6)
                                .start(2011,1,1) (7)
                                .end(2012,5,6) (7)
                                .build() (7)
                ).build();
    }
}
```

1 - Burada, daha sonra daha da değiştirilecek bir "tipik" employee oluşturan bir yardımcı methodumuz var. Lütfen dikkat
edin, bu yöntem bir EmployeeBuilder döndürür. Başka bir seçenek, bu yöntemi EmployeeBuilder sınıfına taşımak ve aynı
zamanda onu static yapmaktır.

2 - 5 - Objeler oluşturulurken, anEmployee() yardımcı methodu kullanılır. Bu, "CEO, bir employee'dir, ancak şu
özelliklere sahiptir" gibi ifadeleri ifade etmemizi sağlar.

3 - 6 - Diğer tiplerde nesneler gerekiyorsa, onların constructor'ları oluşturulur ve çağrılır.

4 - 7 - Burada PositionBuilder'ın start() ve end() metodlarının kullanımı gerçekten çok basittir. Date tipinde nesneler
oluşturmak gerekmez.

İlk kez bu pattern'i uygularken hatırlıyorum: benim için çok garip bir hissiyat olmuştu. O zamanlar, bu tür kodları
okumak zordu. Ama bu sadece ilk izlenimdi. Bu pattern'i incelemeye harcadığım bir süre sonra, birkaç faydası olduğunu
keşfettim:

* Test Data Builders durumunda, birden fazla parametreye sahip özel metodlarla karşılaştırıldığında, her bir değerin
  anlamı çok net bir şekilde ortaya çıkar. Bir kişinin veya sokak adının, bir şarkının başlığının vb. ne olduğu
  konusunda herhangi bir karışıklık yoktur. Her değer, kendisine geçirildiği method adıyla kolayca tanınabilir.
* Test kodu (bir kez alıştığınızda) sorunsuz bir şekilde okunabilir. Her oluşturulan nesnenin özellikleri ne olduğu
  açıktır.
* Employee nesneleri immutable olsa da, EmployeeBuilder nesneleri değişmez değildir. Bu, onları yeniden kullanabilir ve
  değiştirebileceğimiz anlamına gelir.
* Employee class'ının çeşitli constructor'larını oluşturma düşüncesi olmaz.
* Bu tür kodları yazmak hoş bir deneyimdir, çünkü IDE size otomatik tamamlama ipuçları sunar. Ayrıca, kodun okunurluğunu
  daha da artıracak bazı syntatic sugar metotlar (örneğin but() veya and() metotları gibi) ekleyebilirsiniz.

Olumsuz tarafı ise, builder'ların oluşturulması bazı (belki de önemli) kodlamalar gerektirir. Bununla birlikte, her
bir builder'ın implementasyonunun basit olduğunu hatırlatalım.

## 9.6.3. Conclusions

Bu bölümde, test kodu içinde daha karmaşık nesnelerin nasıl oluşturulabileceğine daha yakından bakıldı.
İlk olarak, "doğal", yapılandırılmamış bir yaklaşım sunuldu. Bu yaklaşım, yalnızca çok basit domain modelleri için
önerilebilir. Birçok farklı karmaşık nesnenin oluşturulması için kullanılırsa, test kodu çok zor anlaşılır ve bakımı zor
bir hale gelir.

Ardından, bir "Object Mother" deseni tanıtıldı. Bu desen, nesne oluşturma kodunu kapsar ve bazı niyet açıklayıcı
yöntemler sağlar. Bu sayede test kodu kendisi nesne oluşturma kodundan bağımsız hale gelir. Bununla birlikte, gerçek
sorunun kaynağını kaldırmaz: bunun yerine test kodu alanından yardımcı sınıfların alanına taşır. İlk yaklaşıma göre, bu
yaklaşım sadece minimum düzeyde ek kod gerektirir.

Kabul ettiğimiz son yaklaşım, Test Data Builders kullanımıydı. Bu, nesnelerin oluşturulması konusunda bize çok
esneklik sağladı, ancak bunun karşılığında bazı ek kodların (Object Mother durumuna göre çok daha büyük) sürdürülmesi
gerekti. Karmaşık domain nesneleri için bu yaklaşımı öneririm.

Testler için domain nesneleri oluştururken zorluk yaşamak, bunların çok karmaşık olduğu anlamına gelebilir.

## 9.7. Conclusions

Bu bölümde testlerin düzenlenmesi hakkında bazı şeyler öğreneceksiniz. Temel konularla başladık. İlk olarak, test
sınıfları için paket kullanımı için iki seçeneği karşılaştırdık. Test sınıfları için bir isimlendirme şeması (
ClassNameTest) ve test yöntemleri için (should...()) bir isimlendirme şeması tartıştıktan sonra, test kodu içindeki
yorumlar konusunu ele aldık.

Daha sonra işler biraz daha zorlaştı. BDD olarak adlandırılan test yazma konusunda biraz farklı bir yaklaşımı tartıştık.
Aslında, BDD bundan çok daha fazlasıdır - bu, client gereksinimlerini yerine getirme konusunda büyük bir öneme sahip
farklı bir düşünce tarzıdır. Bu konu oldukça geniştir, ancak burada yalnızca unit testlerin nasıl yazılması üzerindeki
etkisine odaklandık.

Bu bölümden sonra, Mockito destekli testlerden bazı gereksiz kodları kaldırmanın yollarını tartışmaya devam ettik ve
nesnelerin oluşturulmasına bir göz attık - her ikisi de bize bazı kod satırlarını yazmaktan kurtarabilen ve testlerimizi
daha kolay bakım yapılabilir hale getirebilen yeteneklere sahip.

Bu son birkaç nokta, zaten testlerin bakımı ve kalitesi konusuna doğru bizi yönlendiriyor, bu da çok iyi çünkü kitabın
bir sonraki bölümünde tam olarak bunları tartışacağız. Ancak önce biraz pratik yapalım!