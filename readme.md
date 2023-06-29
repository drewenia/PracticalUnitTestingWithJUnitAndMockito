# Chapter 1 On Tests and Tools

Bu giriş bölümü, testin ana kategorilerini sunar. Her birinin rolünü anlamamızı sağlar ve aynı zamanda unit testinin
yeri ve amacını görmemizi sağlar.

## Naming Chaos

Eğer testler konusuna derinlemesine inmeye başlarsanız, kaç farklı isimle karşılaşacağınıza şaşıracaksınız. Unit tests,
integration tests, smoke tests, stress tests, end-to-end tests, exploratory tests, system tests, performance tests,
user test, automated tests, acceptance tests vb. Birçok isimle karşılaşmanız mümkün olacak.
Bu kadar çok sayıdaki test ve mevcut sınıflandırmaların çeşitliliği karşısında şaşkına dönebilirsiniz. Her biri için bir
tanım aramaya başladığınızda ise aynı terim için büyük farklılıklar gösteren birçok tanım bulacaksınız. Bu terminolojik
karmaşa, test konularını takip etmeyi gereğinden daha zor hale getiriyor.

Bu kitapta, testle ilgili terimlerin en yaygın kullanılan setini takip etmekteyim. Seçtiğim isimlerin amacı iyi bir
şekilde tanımladığını düşünüyorum. Umarım sizin için de aynı şekilde anlamlı olurlar.

## 1.1 An Object-Oriented System

Hemen üç temel test türünü ele alacağız: unit tests (bu kitabın ana konusu), entegration tests ve end-to-end
tests. Java dünyasında nesne yönelimli (OO) programlama paradigması hakim olduğu için, bu testlerin bir OO sisteminin
test edilmesinde hangi rolü oynadığını öğreneceğiz. Şekil 1.1, böyle bir sistemin çok basit bir soyutlamasını
sunmaktadır. (Çalışanlar ve yöneticilerin ne anlama geldiğine çok yakında değineceğiz.)

![img.png](Chapter1Images/img.png)

Evet, doğru! Bir sürü daire ve ok. Daireler nesneleri temsil ediyor, oklar ise aralarında iletilen mesajları gösteriyor.
Gördüğünüz gibi, bu resimde bir client de bulunuyor ve onun hareketi (istemi) OO sistemimizde büyük bir etkinlik
başlatmış durumda. Neden bu kadar çok nesne ve mesaj var? Neden zeki bir nesne client'in isteğiyle başa çıkamaz? İşte,
bir OO dünyasının sakinlerinin çok sınırlı bilgiye ve sadece belirli bir yetenek setine sahip olduklarını görmekteyiz.
Her biri çok kısıtlı işlevselliğe sahiptir veya başka bir deyişle, kendi başlarına çok fazla şey yapamazlar. Bu nedenle,
kullanıcının bakış açısından yararlı bir şey elde etmek için işbirliği yapmaları gerekmektedir. Bu, aşağıdaki gibi bir
hareket tarzına yol açar:

- Ben sadece basit bir web denetleyicisiyim, bu yüzden sizin için verileri veritabanından alamam. Ama size yardımcı
  olabilecek biri var - ona UserDAO diyelim. Bu yüzden isteğinizi ona ileteceğim. Ah! Şimdi hatırladım ki UserDAO'nun
  HTTP isteğini anlamadığını. Onun ihtiyaç duyduğu bilgileri çıkarıp ona ileteceğim. Şimdi onun cevabını bekleyelim.

Evet, işte nasıl çalıştığı budur. Aslında, birçok sınıf sadece iletileri iletmekten başka bir şey yapmaz ve belki de
bunları bazı şekillerde dönüştürür.

Düşünüldüğünde, orada çok fazla gerçek iş yapan sınıf (yani sizin tarafınızdan yazılan sınıflar) bulunmamaktadır. ORM
Muhtemelen bunu yazmamışsınızdır. Sonuçta, etrafınızda çok sayıda sağlam çözüm olduğunda neden yazasınız? Dependency
Injection Container'i (DI)? Olası değil. Logging Framework? Hayır. Uygulamanızda gerçek iş mantığının miktarını
düşünürseniz, ne kadar az olduğuna şaşırmış olabilirsiniz. Elbette, bazı business logic'leriniz vardır. Bu nedenle
müşteriniz yeni bir uygulama için bir sipariş vermiştir. Ancak muhtemelen birçok hazır kullanıma hazır eleman
kullandınız ve sizin için çok iş yapan birçok bileşen vardır. Ve bu harika bir şeydir, çünkü kod yeniden kullanımı, özel
olarak tasarlanmış unsurlara tamamen odaklanmanızı sağlayarak hızlı bir şekilde uygulamalar oluşturmanın harika bir
yoludur. Ancak öyleyse, muhtemelen sınıflarınızın birçoğu yalnızca appropriate collabarators (uygun işbirlikçilere)
appropriate messages (uygun iletileri) ileterek şeyleri bir araya getiren sınıflardır. Diğerlerinin çalışmasını koordine
ederler. Bu tür sınıflara **managers** diyeceğiz. Çalışmaları, worker'ların yaptığı işten önemli ölçüde farklıdır.

Yakında göreceğiniz gibi, bu fark test etme üzerinde ciddi bir etkiye sahiptir.

## 1.2 Types of Developers' Tests

Bir OO (nesne yönelimli) sistem resmini akılda tutarak, her test türünün etkilendiği sistem parçalarını görselleştirmeye
çalışabiliriz. Bu, her bir geliştirici testinin kapsamını ve amacını anlamamıza yardımcı olacaktır.

Önce, bu kitap boyunca sıkça kullanacağımız iki önemli terimi tanıtmak istiyorum: SUT ve DOC.

SUT veya System Under Test olarak adlandırdığımız şey, test edilen sistemin bir parçasını ifade eder. Testin türüne
bağlı olarak, SUT çok farklı granülerlikte olabilir - bir sınıftan bir uygulamanın tamamına kadar.

DOC veya Dependent on Component olarak adlandırılan şey, SUT'in görevlerini yerine getirmek için gereken herhangi bir
varlıktır. Genellikle DOC, SUT ile aynı granülerlikte olur, yani SUT bir sınıfsa, diğer sınıfları kullanır; bir modülse,
diğer modüllerle işbirliği yapar.

"DOCs" ve "collaborators" (işbirlikçiler) terimlerini birbirinin yerine kullanacağım.

Aşağıdaki bölümler, çeşitli test türlerini çok kısa bir şekilde tanıtacak. Her biri hakkında çok daha fazla şey
söylenebilir, ancak şimdilik sadece OO sistem resmine odaklanalım ve her test türünün hangi kısmını kapsadığını görmeye
çalışalım.

## 1.2.1 Unit Tests

Unit testleri, tek bir sınıfa odaklanır. Kodunuzun çalıştığından emin olmak için kullanılırlar. Test edilecek sınıfın
gerçek collaborators (işbirlikçilerini), test dublörleriyle değiştirerek, test edilecek sınıfın çalıştığı context'in tüm
yönlerini kontrol ederler. Sistemin kullanıcıları hakkında hiçbir şey bilmezler ve katmanlar, harici sistemler ve
kaynaklar hakkında bilgi sahibi değillerdir. Çok hızlı çalışırlar ve sık sık yürütülürler.

Bu, Şekil 1.2'de gösterilmektedir. Sadece bir nesne açıkça görülebilirken, sistemdeki diğer tüm unsurlar gri tonlu
olarak gösterilir. Görünen tek unsurlar test edilecek olan SUT (System Under Test) - test edilecek nesnedir. Gri tonlu
unsurlar, test tarafından tam olarak etkilenmeyen veya çeşitli test dublörleriyle değiştirilen sistem parçalarını
simgeler. Yatay çizgiler, katmanların (örneğin, görünüm, hizmetler, DAO katmanları) sınırlarını temsil eder. Resimde
görüldüğü gibi, bir Unit testi bir katmanın içinde yer alır.

![img_1.png](Chapter1Images/img_1.png)

Her unit testing framework'u ile çalışan test, bir unit test değildir! Unit testlerinizin, 2.1 bölümünde sunulan tanıma
uygun olduğundan emin olun!

## 1.2.2 Integration Tests

Integration testleri, kodunuzun farklı modüllerinin doğru şekilde Integrationuna odaklanır ve özellikle kontrolünüz
dışındaki kodlarla Integration'ını kapsar. Bir örnek, iş sınıflarınız ile bir OSGi konteyneri, ORM framework'u veya web
services framework arasındaki bağlantı olabilir. Integration testleri, unit testlerden çok daha geniş bir kod alanını
kapsamasına rağmen, hala geliştiricinin bakış açısından kodu test eder.

Integration testleri, unit testlerden çok daha yavaş bir şekilde çalışır. Genellikle yürütülebilmesi için bazı
kaynakların (örneğin, bir application context) yapılandırılması gerekmekte ve yürütülmesi, genellikle yavaş yanıt veren
bazı varlıkların (örneğin, veritabanları, dosya sistemi veya web hizmetleri) çağrılarını içermektedir. Integration
testlerinin sonuçlarını doğrulamak için genellikle harici kaynaklara (örneğin, bir SQL sorgusu çıkarma) bakmak
gerekmektedir.

Test dublörleri, gerçek sistem parçalarının (örneğin, sınıflar veya modüller) fake yerine geçen bileşenlerdir. Bu konu,
daha sonra 5. Bölüm olan "Mocks, Stubs, Test Spies" bölümünde detaylı olarak ele alınacaktır.

![img_2.png](Chapter1Images/img_2.png)

Şekil 1.3 gösterildiği gibi, Integration testleri genellikle birkaç katmana yayılır (örneğin, services'in DAO
katmanıyla doğru şekilde çalışıp çalışmadığını test etmek için). Integration testleri, ekibiniz tarafından yazılan kodu
ve aynı zamanda test edilen uygulama tarafından kullanılan 3rd party kütüphanelerden gelen kodu yürütür. Unit
testlerde olduğu gibi, sistemde geniş alanlar ya Integration testleri tarafından etkilenmez ya da test dublörleriyle
değiştirilir. Integration testleri genellikle kullanıcı arayüzüne (GUI) dokunmaz. Bu nedenle, resimde müşteri (sistem
kullanıcısı) gösterilmez.

## 1.2.3 End-to-End Tests

End-to-End testler, kodunuzun müşterinin bakış açısından çalıştığını doğrulamak için kullanılır. Kullanıcının sistemi
nasıl kullanacağını taklit ederek, sistemin tamamı test edilir. Bu nedenle, tüm katmanları kapsarlar. End-to-End
testlerde, test dublörleri nadiren kullanılır - amaç gerçek sistem üzerinde test yapmaktır. End-to-End testler
genellikle yürütülmesi için önemli bir zaman gerektirir.

![img_3.png](Chapter1Images/img_3.png)

Şekil 1.4, tüm katmanlardan (GUI, web service layer veya test edilen sistemdeki diğer harici API'ler gibi) öğeleri
test eden bir end-to-end testini göstermektedir. End-to-end testler, front-end'den (GUI elemanlarına tıklamalar gibi)
storage layer'lara (örneğin, veritabanı depolama) kadar olan tüm bileşenleri test eder. End-to-End testler, gerçek
kullanıcıların yaptığı taleplere benzer şekilde başlatılır (örneğin, GUI elemanlarına tıklamalar).

## 1.2.4 Examples

**Test tiplerine gore ornekler:**

### Unit tests:

- FootballPlayer sınıfından bir nesnenin, ikinci sarı kartı aldıktan sonra durumunun "fired" olarak değişmesi
  gerekmektedir.
- Product sınıfının bir constructor'i, fiyat argümanı 0'dan küçükse IllegalArgumentException (anlamlı bir mesajla
  birlikte) fırlatmalıdır.

### Integration tests

- UserService sınıfının deleteAccount() metodunun, değeri 1 olan bir ID argümanıyla çağrılması, bu ID'ye sahip hesabın
  veritabanından silinmesine neden olmalıdır.
- ItemDAO sınıfına, ID'si 5 olan bir öğe için ikinci kez talep edildiğinde, gerçek veritabanına dokunmaması ve istenen
  öğeyi cache'den alması gerekmektedir.
- ParcelService, paketin ayrıntılarını bulmak için bir web servisi ile iletişim kurmalı ve paket bulunamazsa uygun hata
  bilgisiyle birlikte bir e-posta göndermek için EmailService'i kullanmalıdır.

### End-to-End tests

- Giriş yapmış bir kullanıcı, herhangi bir genel resmin yanındaki "yorum ekle" düğmesine tıklayarak yorum ekleyebilir.
  Misafir kullanıcılar (giriş yapmamış kullanıcılar), yayınlandıktan sonra bu yorumu görebilir, ancak kendi yorumlarını
  gönderemezler.
- Bir mağaza sahibi, bir Ürün Ekle formunu kullanarak mağazasına yeni bir ürün eklediğinde, bu ürünü bir Arama Formu'nda
  adını arama alanına girerek bulmak mümkün olmalıdır.
- Bir kullanıcı, geo-lokasyon verilerini whatCityIsThis web servisi aracılığıyla gönderdiğinde, sistem şehir adıyla
  yanıt vermelidir.

Yukarıda ki liste SUT'ların ve DOC'ların unit testlerden (daha küçük), integration testlerine (orta) ve End-to-End
testlere (daha büyük) geçişte nasıl "büyüdüğünü" göstermektedir. Farklılık açık bir şekilde görülebilir. Unit testlerde
SUT'lar ve DOC'lar sadece sınıflardır. Integration testleri, modüllerin veya katmanların seviyesinde etki eder.
End-to-End testlerde ise tüm uygulama test edilir (uygulamanın kendisi bir SUT haline gelir) ve diğer uygulamalar
collaborators (işbirlikçi) (DOC) olarak kullanılır.

![img_4.png](Chapter1Images/img_4.png)

![img_5.png](Chapter1Images/img_5.png)

Önceki bölümlerde sunulan tüm test türleri önemlidir. Bir geliştirme ekibi açısından, her birinin kendi değeri vardır.
Unit testler yüksek kaliteli kod sağlamaya yardımcı olur, Integration testleri farklı modüllerin etkili bir şekilde
birlikte çalıştığını doğrular, End-to-End testler ise kullanıcıların bakış açısını yansıtarak sistemi deneyimler.
Uygulamanın türüne bağlı olarak, bunlardan bazıları diğerlerine göre daha uygun olabilir.

Farklı test türlerini düşünmenin başka bir yolu, bunları bir ölçekte yerleştirmektir. Bu ölçeğin bir ucunda, yalnızca
belirli bir sistemi doğru bir şekilde uygulayıp uygulamadığımızı kontrol etmek için kullanılan unit testler bulunur.
Diğer ucunda ise, ana amaç doğru sistemi uyguladığımızı doğrulamak olan End-to-End testler bulunur. Integration testleri
ise bu ikisinin arasında yer alır.

Bu kitap unit testlere odaklanırken, diğer test türlerine çok sınırlı bir şekilde değinmektedir. Ancak, bu test
türlerinin varlığından haberdar olmak ve yalnızca unit testlere güvenmemek çok önemlidir. Unit testler
Developerlarin testlerinin temelidir, ancak genellikle tek başlarına yeterli değildirler. Unit testleri öğrenirken
bunu aklınızda tutmanız önemlidir.

Hangi testleri uygulamanız için yazmalısınız? Ne yazık ki, bu soruya kolay bir cevap yoktur. Farklı test türlerinin
doğruluk oranını tanımlayan altın bir kural bulunmamaktadır. Bu, yazdığınız uygulamanın türüne çok yüksek bir derecede
bağlıdır.

## 1.3 Verification and Design

Test yaklaşımlarının sürekliliği, iki zıt inanç arasında yer alır. Farkı netleştirmek için her iki uç noktayı da
tanıtacağım.

Bazı insanlar (onlara kolaylık sağlamak için "verifiers" olarak adlandıracağım), kodlarının çalıştığından emin
olmak istiyor. Bu, hedefleri - yapması gerektiği gibi çalıştığından emin olmak. Testi zor olan kodlar için, test
yapabilmek için mevcut tekniklere başvuracaklar. Testability kutsal amaçlarına ulaşmak için, gerektiğinde bazı
nesne yönelimli kurallardan ödün verecekler. Yansıma (reflection) kullanarak method görünürlüğünü değiştirebilir veya
final sınıflarla uğraşmak için sınıf yükleme hileleri kullanabilirler. Bu şekilde, kâbus gibi bir geçmişe sahip kod
dahil olmak üzere neredeyse her şeyi test edebilirler. "dirty hacks" kullanmakla suçlandıklarında, omuzlarını silker
ve "çamurda yüzüyorsam zaten kirli hissetmiyorum" diye yanıtlarlar.

Diğer bir grup - onları "Designerlar" olarak adlandıralım - nesne yönelimli kurallara uymayı en önemli şey olarak görür
ve bu kuralların kolayca test edilebilir bir kod ürettiğine inanır. Onlar için testler, kodun sağlığının bir
göstergesidir. Kolay yazılabilen testler, sağlam bir kodun göstergesidir. Test yazarken karşılaşılan zorluklar, kodun
sorunlarına işaret eder ve kodun yeniden düzenlenmesi gerektiğinin açık bir işaretidir. Designerlar, üretim kodu için
kullandıkları teknikleri kullanarak testler yazmayı tercih eder ve yansıma (reflection) veya sınıf yükleme hilelerinden
kaçınırlar. Designerlar özellikle TDD yaklaşımını sevmekte olup, belirli bir kod kalitesini garanti eder. Mevcut bir
kod durumunda, daha test edilebilir hale getirmek için genellikle kodu yeniden yapılandırmaya (veya yeniden yazmaya)
eğilim gösterirler.

Görüldüğü gibi, bu iki yaklaşım arasındaki çatışma asla çözülemez. Destekçiler farklı görüşlere sahiptir, farklı
ihtiyaçları ve değer verdikleri farklı şeyler vardır. Her ikisinin de üstünlüklerini "kanıtlamak" için bazı iyi
örnekleri vardır. StackOverFlow uzerinde ki bir tartisma:

- Reflection, özel metodları test etmenin en iyi yoludur.
- Evet, tasarımınız üzerinde düşünmelisiniz!

Bu ayrım, mevcut olan farklı test araçlarının sunulan özelliklerini incelerseniz de açıkça görülür. Bazıları (örneğin
JMockit ve Powermock), sizi test edilemeyen şeyleri test etme gücüyle donatarak, static sınıfları, final sınıfları ve
constructor'ları taklit etmenizi veya private metodları çağırmanızı sağlar. Diğerleri ise bu tür hileleri kullanmaktan
kaçınırlar. Örneğin, JUnit, private metodların testini kolaylaştıracak herhangi bir özellik sunmamıştır, hatta JUnit'in
ilk günlerinden beri birçok kişi böyle bir şey istemiş olmasına rağmen.

Designer ve verificator terimleri, testlere nasıl yaklaşılabileceği konusunda önemli bir farkı vurgulamak için
kullanılmıştır. Ancak, benim bildiğim kadarıyla kimse tamamen Designer veya tamamen verificator değildir. Hepimiz bu
ikisi arasında bir yerde yer alırız.

## 1.4 But Should Developers Test Their Own Code

Evet, çok defa duyduğunuz gibi, sizin (bir geliştirici olarak) kendi kodunuzu test etmemeniz gerektiği söylenir. Bu
iddianın desteklenmesi için birçok sebep sunulur, ancak en güçlü ve en yaygın kullanılan iki sebep şu şekildedir:

- Developerların test becerileri eksiktir
- Kendinizin yargılayan hakim olmalısınız

Bu konuda açık olalım. Her ikisi de iyi nedenlere dayanıyor ve kuşkusuz, birçok geliştirme ekibinin gerçek deneyimlerine
dayanıyor. Bu nedenle, bunları hafife almak yanlış olur. Bununla birlikte, test etme konusundaki "ortak bilgi"nin bir
tür yanlış anlama veya belki de test etmenin çeşitlilik ve amaçlarına dair genel bir anlayış eksikliğine işaret ettiğine
ikna oldum.

Eğer müşterilere yazılımı göndermeden önceki son testlerden bahsediyorsak, genel olarak bu tür testlerin profesyonel
test ekipleri tarafından yürütülmesi gerektiğine inanıyorum. Bir geliştiricinin GUI üzerinden tıklayarak veya deneyimli
bir testçi kadar saldırgan ve meraklı olamayacağı konusunda size katılıyorum. Ancak, bu tür testler tek seçenek
değildir! Developerlar tarafından yapılabilen birçok değerli test vardır ve yapılmalıdır.

Dahası, bazı yazılımlar Developerlar dışındakiler tarafından kolayca test edilemez! Backend çözümlerini düşünün. Bu
tür yazılımlar genellikle grafik arayüzü (GUI) veya kullanıcı dostu giriş noktaları içermez ve çevrenin mimari veya
donanım yönleriyle güçlü bağımlılıklara sahip olabilir.

Müşterinin bakış açısından yazılımı kontrol etmek önemlidir, ancak bu daha büyük bir puzzle'ın sadece tek bir
parçasıdır. Sizinle bir yazılımı paylaşmadan önce, bir geliştirme ekibi size sağlamak zorundadır. Eğer Developerlar
kendi testlerini yapmazlarsa, muhtemelen düşük kalitede bir şey sunacaklardır. Developerlarin yaptığı testler,
müşteriye sunulan ürünün kalitesini artırmanın yanı sıra, kod tabanının kalitesini de artırır ve bu herhangi bir
geliştirme ekibi için önemli bir konudur. Bu küçümsenecek bir şey değildir. Bir geliştirme ekibinin, koduna ne kadar
güvendiği ve ne kadar gurur duyduğu o kadar önemlidir ki, daha iyi sonuçlar elde eder. Developerlarin yaptığı testler,
bir ekibin güven kazanmasına ve fazla şüpheye kapılmadan ilerlemesine yardımcı olur.

Ayrıca, hataları erken yakalamak maliyeti büyük ölçüde azaltır ve onarımda geçen süreyi kısaltır. Erken aşamada daha
fazla hata bulursanız, maliyetleri o kadar az olacaktır. Bu iyi bilinen time-cost oranı Figure 1.5'te
gösterilmiştir.

![img_6.png](Chapter1Images/img_6.png)

Developerlarin yaptığı testler, hatalara karşı ilk savunma hattıdır. Hatalar ortaya çıktığı anda onları yok ederler.
Tabii ki, bu bölümün başında belirtilen nedenlerden dolayı, bazı hatalar muhtemelen geçebilir. Evet, bu olabilir! İşte
bu yüzden, başka savunma hatları da bulunmalıdır: yani yüksek becerilere sahip, uzmanlaşmış test ekipleri. Umarım kalan
tüm hataları bulup ortadan kaldırırlar.

Aslında, birçok şirket (neredeyse) sadece Developerlarin yaptığı testlere güvenir. Facebook veya WordPress gibi büyük
şirketler, continuos deployment yaklaşımını benimserler ve bu yaklaşımı "automated test'leri geçerse, üretime gider"
şeklinde özetleyebiliriz.

## 1.5 Tools Introduction

Bu bölüm, testlerin yazılması ve yürütülmesi için kullanacağım araçları tanıtıyor. Bu, testle ilgili farklı yaklaşımlar
hakkındaki son tartışmamıza (1.3 Bölümüne bakın) ilişkilidir. Bu farklı yaklaşımlar, neredeyse aynı problem alanlarını
kapsayan birçok aracın var olmasından sorumludur.

Örnek olarak, mocking frameworks'leri ele alalım. Gerçekten bu kadar çok mocking framework'e ihtiyacımız var mı? Cevap "
evet" ve bunun nedeni her mocking framework'in biraz farklı olması ve test dublörleri yazılması konusunda biraz farklı
bir yaklaşımı kolaylaştırmasıdır. Bu, test framework'lerinden IDE'lere kadar diğer araç grupları için de geçerlidir.

Genel olarak, test araçları kullanımı çok basittir. Bu iyi bir haber, değil mi? Ancak dikkatli olun – bir hile var! Bu
aldatıcı kullanım kolaylığı, birçok geliştiricinin, test yapmayı bildiğini varsaymasına neden olur, sadece test
araçlarını kullanabildikleri için – yani birkaç satır JUnit kodu yazabiliyor olmaları. Bu açıkça yanlıştır. Araçlar
düşüncesizce kullanılabilir veya yetenekli bir el tarafından kullanılabilir. Sizi yönetebilirler veya itaatkar
hizmetkarlarınız olabilirler. Önemli olan 'neden' ve 'ne için' olduğunu kavramaktır, böylece ne zaman kullanmanız
gerektiğini (veya kullanmamanız gerektiğini) bilirsiniz.

Bu kitap boyunca, belirli araçlarda somutlaşan fikirlerin önemini vurgulayacağım. Bu fikirleri iyi anlarsanız, neredeyse
herhangi bir araçla takip edebilirsiniz. Eğer sadece araçlara odaklanırsanız, yakında size değerli gelen her şeyi tekrar
öğrenmeniz gerekecektir. Fikirler kalıcıdır, ancak araçlar sadece kısa bir süre için vardır.

Eğer tercih ettiğiniz araçlar farklı ise ve farklı araçlar kullanıyorsanız, bu bir sorun değildir. Günümüzde araçlar
birbirine çok benzer. Farklı yollar boyunca evrim geçirmişlerdir, ancak birbirlerini etkilemiş ve genellikle
rakipleriyle benzer özellik setlerine sahip olmuşlardır. Modern araçları kullanarak benzer sonuçlar elde edebilir ve bu
kitapta sunulan teknikleri kullanmaya devam edebilirsiniz. Benim seçtiğim araçlar kişisel favorilerimdir ve dikkatli bir
şekilde seçilmiştir. Kitapta sunulan bazı tekniklerin, diğer araçları kullanmaktan daha kolay bir şekilde
kullanabileceğinizi düşünüyorum. Sizin için tek risk, sizin de onların üstünlüğüne ikna olmanız ve ardından araç
kutunuza yeni oyuncaklar eklemeniz olabilir. Bu pek kötü bir şey gibi görünmüyor, değil mi?

## Testing Framework : JUnit

JUnit (http://junit.org), Java için açık kaynaklı bir test framework'udur. Kent Beck tarafından 1997 yılı civarında
oluşturulmuş olup o zamandan bu yana Java geliştiricileri için de facto bir standart test aracıdır. Tüm IDE'ler (
Eclipse, IntelliJ IDEA), derleme araçları (Ant, Maven, Gradle) ve popüler framework'ler (örneğin, Spring) tarafından
desteklenmektedir. JUnit, geniş bir kullanıcı topluluğuna sahiptir ve çeşitli ilginç uzantı projeleriyle
desteklenmektedir. Unit testler için özellikle tasarlanmış olsa da, diğer test türleri için de yaygın olarak
kullanılmaktadır.

## Mock Library : Mockito

Mockito (http://mockito.org), nispeten yeni bir mocking framework (ya da daha doğrusu test-spy framework'u). Szczepan
Faber'ın gururla babası olduğu 2007'nin dördüncü çeyreğinde doğmuş ve hızla kaliteli bir ürün haline gelmiştir. Mock
süreci üzerinde tam kontrol sağlar ve "temiz ve basit API ile güzel testler yazmanıza olanak tanır". İlk olarak
Easymock'tan türetilen Mockito, önemli ölçüde gelişmiş ve öncüsünden birçok açıdan farklılık gösterir hale gelmiştir.
Mockito'yu JUnit ile birlikte kullanmak çok kolaydır.

## Matcher Libraries : FEST and Hamcrest

Testleri daha okunabilir ve bakımı daha kolay hale getirmek için kitabın bazı bölümlerinde aşağıdaki eşleştirici (
matcher) kütüphanelerini kullandım: FEST Fluent Assertions (http://code.google.com/p/fest/) ve
Hamcrest (http://code.google.com/p/hamcrest/).

## JUnit Params

JUnit'un en popüler test framework'u olması, her açıdan en iyi olduğu anlamına gelmez. Aslında, ciddi zayıf noktalara
sahiptir ve parametrized testlere destek verme konusunda zayıf kalır. Neyse ki, yıllar içinde birçok ek kütüphane
oluşturulmuş ve JUnit kullanıcılarına düzgün bir parametrized test desteği sağlamak amacıyla bunlar geliştirilmiştir.

## EasyTest

Proje web sitesinde EasyTest'i "JUnit framework'une dayalı bir Data Driven Testing Framework" olarak duyurulmuştur.
EasyTest, JUnit'in varsayılan parametrized testlerine bazı güzel iyileştirmeler sunar. Kitapta, EasyTest'in Excel
dosyalarını işleme yeteneklerini kullanacağız.

## Code Coverage : Cobertura

Code Coverage Tools'lar arasında birkaç ilginç seçenek bulunmaktadır, kişisel tercihim ise
Cobertura'dır (http://cobertura.sourceforge.net). Cobertura, tüm derleme araçları, IDE'ler ve sürekli entegrasyon
sunucuları ile iyi çalışır.

## Mock Libraries : PowerMock and EasyMock

Kitapta kullanılan mocking framework Mockito olsa da, bazı durumlarda diğer seçeneklere göz atmak faydalı olabilir.
Powermock (http://code.google.com/p/powermock/), final sınıfların ve statik methodların mock'lanması gibi güçlü
özellikler sunar. Bu özellikleri bir kez kullanarak, alternatif bir test yaklaşımını göstermek ve tartışmak için
kullanacağız. EasyMock (http://easymock.org), mock-based test ve spy based test arasındaki farkı göstermek
için kullanılacaktır.

## Mutation Testing : PIT

PIT Mutation Testing (http://pitest.org), Java için hızlı bir bytecode based mutation testi sistemidir ve unit
testlerinizin etkinliğini test etmenizi sağlar. Java 5 ve JUnit 4.6 (ve üzeri) ile çalışır.

## Utilities : Catch-Exception, Tempus-Fugit, and Unitils

Catch-Exception kütüphanesi (http://code.google.com/p/catch-exception/), production kodu tarafından uygun
exception'ların fırlatılıp fırlatılmadığını doğrulayan testlerin yazılmasına yardımcı olur. Java 1.6 gerektirir.

Unitils (http://www.unitils.org), "unit ve integration testini kolay ve sürdürülebilir hale getirmeyi amaçlayan" açık
kaynaklı bir kütüphanedir. Sadece "Reflection asserts" modülü adlı birkaç özelliğini kullanacağız.

Tempus-fugit (http://tempusfugitlibrary.org), "concurrent kod yazma ve test etme için Java micro-library" olarak
tanımlanan bir kütüphanedir. Diğer birçok şeyin yanı sıra, JUnit'e, çok sayıda thread tarafından aynı anda
çalıştırılacak kodun test edilmesi için önemli yetenekler kazandırır.

## Build Tools : Maven and Gradle

Unit testleri genellikle derleme sürecine dahil edilir, yani bir derleme aracı tarafından çalıştırılır. Bu kitapta,
testleri Maven (http://maven.org) ve Gradle (http://gradle.org) kullanarak nasıl çalıştıracağınızı anlatıyorum.

# Chapter 2 - Unit Tests

## 2.1 What is a Unit Test

Unit testlerin arkasındaki fikir basittir: şu anda üzerinde çalıştığınız sınıfın doğru şekilde çalıştığından emin
olmaktır - yani görevini yerine getirdiğinden emin olmaktır. Bu, belirli bir input data verildiğinde beklenen bir
output yanıt verip vermeyeceği veya anlamsız verilerle beslendiğinde uygun bir exception fırlatıp fırlatmayacağı gibi
konuları içerir. Dolayısıyla, beklenen davranışı doğrulayacak testler yazmak fikridir.

Ancak bu yeterli değildir. Sınıflarınızı izole bir şekilde test etmeli ve herhangi bir ortamda çalıştıklarını doğrulamak
için test etmelisiniz. Unit testleri yazarken önemli olan tek bir sınıfı ve daha fazlasını test etmemektir.
Veritabanları, Spring configuration dosyaları ve harici web servislerini unutun. Bunları Unit testlerinize dahil etmek
istemezsiniz. Sınıfınızın mantığına odaklanın. Kodunuzun düzgün çalıştığından emin olduktan sonra, diğer bileşenlerle
olan entegrasyonunu test edin! Ancak önce Unit testleri gerçekleştirin!

Ne yazık ki, hala birçok kişi Unit testlerle diğer test türlerini karıştırmaktadır veya developer'ların yazdığı
herhangi bir testi bu terimle tanımlamaktadır. Birçok insan, Unit test framework'u tarafından yürütüldüğü için
yazdıkları her testin "Unit" türünde olduğunu iddia etmektedir! Diğerleri ise "Unit testi" yapıyor olduklarını iddia
edip three layers bir yazılımı Unit olarak seçmektedir... Bu, tabii ki yanlıştır: kafa karışıklığına yol açar ve
tartışmayı zorlaştırır. Bunu yapmayın!

Unit testleri, diğer developer'ların testlerinden ayıran bazı özelliklere sahiptir. 1.2 bölümünde tartışıldığı gibi,
Unit testleri tek bir sınıfa odaklanır ve bir SUT'un yürütüldüğü context'i sıkı bir şekilde kontrol eder. Ayrıca son
derece hızlı çalışırlar ve genellikle developer'i suçlu methoda veya hatta suçlu kod satırına kadar kesin bir şekilde
hata noktalarına götürebilirler. Çalışmamızın kalitesi hakkında bu kadar kesin ve anlık geri bildirim sağlayarak,
hataları sistemin tamamına yayılmadan önce hızlı bir şekilde düzeltmemize yardımcı olurlar (bkz. 1.4 bölümü).

Kapsamlı ve titiz bir Unit test setinin varlığı, kodu korkmadan yeniden düzenleyebilmemizi sağlar.

Unit testlerinin yazılmasının bir başka faydası, kodumuzun live'a (yani her zaman güncel) bir belgesi olarak hizmet
etmeleridir. Unit testleri, Javadocs veya kodun kendisiyle birlikte geliştirilen herhangi bir metin açıklamasından çok
daha güvenilirdir.

Son ama en önemlisi, yetenekli bir developer, Unit testlerinin oluşturulma sürecini bir tasarım faaliyetine
dönüştürebilir. Bu, oldukça şaşırtıcı bir şekilde, Unit testlerinin sağladığı tüm faydalar arasında en önemlisi olabilir

## 2.2 Interactions in Unit Tests

Unit testlerle neyin test edilmesi gerektiğini ve nasıl test edilmesi gerektiğini anlamak için, test sınıfı ile SUT (
System Under Test) arasındaki etkileşimlere ve SUT ile onun DOC'ları (Dependent Objects and Collaborators - Bağımlı
Nesneler ve İşbirlikçiler) arasındaki etkileşimlere daha yakından bakmamız gerekmektedir.

Öncelikle, bir diyagram şeklinde bazı teorik bilgiler verebilirim. Şekil 2.1, bir SUT ile diğer varlıklar arasında olası
etkileşimleri göstermektedir.

![img.png](Chapter2Images/img.png)

İki etkileşim doğrudan ve SUT ile client'i (bu durumda bir test sınıfı) arasında gerçekleşir. Bu ikisi doğrudan test
kodundan "ulaşılabilir" şekildedir ve üzerlerinde kolayca işlem yapılabilir. Diğer iki etkileşim ise dolaylıdır ve SUT
ile DOC'lar (Bağımlı Nesneler ve İşbirlikçiler) arasındadır. Bu durumda, client (bir test sınıfı), bu etkileşimleri
doğrudan kontrol etme imkanına sahip değildir.

Yani, bir test sınıfı doğrudan SUT ile etkileşime geçebilir ve SUT'nin davranışını doğrulayabilir veya durum
değişikliklerini kontrol edebilir. Ancak, SUT'nin DOC'larla olan etkileşimlerini doğrudan kontrol edemez.

Başka bir olası sınıflandırma, etkileşimleri input'lar (SUT'nin bir mesaj alması) ve output (SUT'nin bir mesaj
göndermesi) olarak ayırır. Test yaparken, SUT'yi gereken bir duruma getirmek ve methodlarını çağırmak için doğrudan ve
dolaylı inputları kullanacağız. SUT'nin doğrudan ve dolaylı output'ları, SUT'nin davranışının ifadesidir; bu da SUT'nin
düzgün çalışıp çalışmadığını doğrulamak için onları kullanacağımız anlamına gelir.

Tablo 2.1, bir SUT'nin ve DOC'ların olası collaboration türlerini özetlemektedir. İlk sütun - "type of interaction" -
collaboration SUT'nin bakış açısından nasıl olduğunu açıklar. Bir test sınıfı, SUT'yi kullanan bir client (bir
kullanıcı) olarak hareket eder; bu nedenle "ilgili taraflar" sütununda yer alır.

![img_1.png](Chapter2Images/img_1.png)

Tabii ki! İşte bir örnek kod, müşteri ödemesi ve türüne dayanarak "bonus" hesaplayan bir finansal hizmeti (
FinancialService sınıfı) ve bununla etkileşen DOC'ları göstermek için:

![img_2.png](Chapter2Images/img_2.png)

Gördüğünüz gibi, SUT olan calculateBonus() metodu iki parametre alır (clientId ve payment) ve iki işbirlikçi ile
etkileşime geçer (**clientDAO** ve **calculator**). calculateBonus() metodunu tam olarak test etmek için hem input
parametrelerini (direct input), hem de işbirlikçilerinden dönen mesajları (indirect inputs) kontrol etmemiz
gerekiyor. Böylece dönen değerin (direct output) doğru olup olmadığını görebiliriz.

Tablo 2.2, calculateBonus() metodunda gerçekleşen ve test açısından önemli olan etkileşim türlerini özetler.

![img_3.png](Chapter2Images/img_3.png)

* direct input - Test class & SUT -> SUT'nin calculateBonus() metodunun clientId ve payment argümanları ile doğrudan
  çağrılması
* direct output - Test class & SUT -> SUT'nin calculateBonus() metodunu çağırdıktan sonra test sınıfına döndürülen bonus
  değeri.
* indirect output - SUT & DOC's -> SUT'nin clientDAO'nun saveBonusHistory() metoduna geçirdiği clientId ve bonus
  değerleri.
* indirect output - SUT & DOC's -> SUT'nin calculator'ın calculateBonus() metoduna geçirdiği clientType ve payment
  değerleri
* indirect input - SUT & DOC's -> clientDAO tarafından döndürülen clientType ve calculator tarafından SUT'ye döndürülen
  bonus değeri.

## 2.2.1 State vs Interaction Testing

Şimdi Figure 1.1'de gösterilen basit bir nesne yönelimli (OO) sistem soyutlamasını hatırlayalım. Bu şekilde, işçi ve
yönetici gibi iki tür sınıfın birlikte çalışarak bir client tarafından verilen bir isteği yerine getirdiği
görülmektedir. Kitap, her iki tür sınıfın da Unit testlerini açıklar. Öncelikle, işçilerin yapmış olduğu hesaplamaların
ve döndürdüğü değerlerin doğru olduğundan emin olmak istediğimiz için işçilerin dünyasına dalacağız. Bu Unit testin bir
parçası olan state testi, gerçekten basit ve uzun yıllardır tamamen kabul görmüştür. Bu tür bir test, direct input
ve output'ları kullanır. State testini, 3. Bölüm'de "Unit Tests with no Collaborators" başlığında ele alacağız.

Ardından, interactions testiyle ilgili daha zorlu konulara geçeceğiz. Yöneticilerin çalışmasına odaklanacağız ve
işbirlikçiler arasında nasıl mesajların iletiltiğine odaklanacağız. Bu, daha karmaşık ve sezgisel olmayan bir test
türüdür. Ara sıra, yeni fikirler ve araçlar ortaya çıkar ve etkileşimleri nasıl doğru bir şekilde test edileceği
konusunda hala canlı tartışmalar devam etmektedir. Gerçekten korkutucu olan, interaction testlerinin bazen iyi niyetli
olmaktan çok zarar verebilmesidir, bu yüzden sadece "nasıl" sorusuna değil aynı zamanda "olup olmadığı" sorusuna da
odaklanacağız. Bu tür bir test, indirect outputs'lara odaklanır. Interaction testini, 5. Bölüm'de "Mocklar, Stubs, Test
Spies" başlığında ele alacağız.

Direct Outputs'un test edilmesine gerçekten "State verification" denir çünkü bu, doğrudan sistemden dönen durumu veya
değerleri doğrulamayı içerir

## Why Worry about Indirect Interactions?

Bir nesne yönelimli fanatik şu noktada bana bağırarak başlayabilir: "Kapsülleme ve bilgi gizleme diye bir şey duydun mu
hiç? Öyleyse, SUT'un işbirlikçileri üzerinde hangi methodleri çağırdığı konusunda neden endişelenmeliyiz? Neden bunu
SUT'nin implementasyon detayı olarak bırakmayalım? Eğer bu SUT implementasyonunun özel bir bölümü ise, hiç
dokunmamalıyız."Bu mantıklı geliyor, değil mi? Keşke sınıflarımızı sadece API'lerini kullanarak tamamen test
edebilseydik! Ne yazık ki, bu mümkün değil. Bir örnek olarak, bir cache'den nesnelerin alınması durumunu düşünelim.

Cache'in genel fikrinin ne olduğunu hatırlayalım. İki depolama alanı bulunur: "real one", geniş kapasiteye sahip ve
average access time'a sahip, ve "cache" ise çok daha küçük kapasiteye sahip olmasına rağmen çok daha hızlı erişim
süresine sahiptir. Şimdi bir cache sistemine yönelik birkaç gereksinimi tanımlayalım. Bu tam teşekküllü bir cache
mekanizması olmayacak, ancak karşılaştığımız sorunu açıklamak için yeterli olacaktır.

Key X ile bir nesne istendiğinde, cache'li olan sistemimiz aşağıdaki basit kurallara göre hareket etmelidir:

1 - Eğer key X'e sahip nesne hiçbir depolama alanında bulunmuyorsa, sistem null değerini döndürecektir.
2 - Eğer key X'e sahip nesne herhangi bir depolama alanında bulunuyorsa, o nesne döndürülecektir.
a - Eğer key X'e sahip nesne cache storage alanında bulunuyorsa, bu depolama alanından döndürülecektir.
b - Eğer key X'e sahip nesne cache storage alanında bulunmuyorsa, ana depolama alanı aranacaktır.

Tabii ki, buradaki amaç, cache hit ratio oranını artıracak akıllı bir caching stratejisine sahip olmaktır - ancak
bu, gerçekten tartışmamızla ilgili değildir. İlgilendiğimiz şey, outputs (returned values) ve SUT ile
işbirlikçileri arasındaki etkileşimlerdir.

Yukarıda belirtilen gereksinimleri göz önüne alırsanız, state testiyle sadece 1 ve 2 numaralı gereksinimleri test
edebileceğimizi fark edeceksiniz. Bu, state testinin nesnelerin privacy'lerine saygı göstermesi nedeniyle gerçekleşir.
State testi, nesnenin içeride neler yaptığını görmeyi mümkün kılmaz - bu durumda, talep edilen nesnenin hangi depolama
alanından alındığını doğrulayamaz. Dolayısıyla, 2a ve 2b gereksinimleri state testi kullanılarak doğrulanamaz.

Bu aşağıdaki resimde gösterilmiştir. İki depolama alanından (hızlı bir cache storage ve daha yavaş real storage)
oluşan SUT'muz, tek bir get() methodi aracılığıyla erişilebilir. SUT'a request gönderen client, içsel karmaşıklığı
hakkında hiçbir şey bilmez.

![img_4.png](Chapter2Images/img_4.png)

İdeal olarak, bir request geldiğinde önce cache storage aranır ve eğer cache storage'de' verilen anahtar (bu
örnekte X) ile eşleşen bir input bulunmazsa, real storage alanı aranır. Ancak, SUT doğru şekilde uygulanmamışsa, önce
cache storage yerine real storage aranabilir. Verilen anahtarla bir nesneyi bekleyen client, bu iki durum arasındaki
farkı ayırt edemez. Onun bildiği tek şey, anahtar X ile bir nesne talep ettiği ve onu aldığıdır.

Sistemimizin beklendiği gibi çalışıp çalışmadığını gerçekten doğrulamak için interaction testi uygulanmalıdır.
İşbirlikçiler olan cache ve real storage ile yapılan çağrıların sırası kontrol edilmelidir. Bunun olmadığı
durumda, sistemimizin çalışıp çalışmadığını söyleyemeyiz.

Bu basit örnek, SUT'un observable davranışının (direct outputs) doğrulanmasının yeterli olmadığını kanıtlamaktadır.
Benzer sorunlar, diğerlerinin çabalarını koordine eden manager'ları (Section 1.1'e bakınız) test etmek
için ortaya çıkar. Daha önce belirtildiği gibi, bu tür koordinasyon sınıfları nesne yönelimli sistemlerde oldukça
popülerdir. Bu nedenle, (indirect outputs'ların) test edilmesi ile ilgili teknikler, araçlar ve sorunlar hakkında çok
zaman harcayacağız.

Başlangıçta daha basit duruma odaklanalım. Bir sonraki bölümde, işbirlikçisi(collaborators) olmayan basit nesnelerin
nasıl test edileceğini öğreneceğiz.

# Part II. Writing Unit Tests

Bir test, aşağıda ki durumlarda Unit test değildir!

- Veritabanı ile iletişim kuruyorsa,
- Ağ üzerinden iletişim kuruyorsa
- File System'a dokunuyorsa
- Diğer Unit testlerinizle aynı anda çalışamıyorsa,
- Çalıştırmak için özel işlemler yapmanız gerekiyorsa (örneğin, configuration dosyalarını düzenlemek),

# Chapter 3 Unit Tests with no Collaborators

Interaction türlerini ve Unit testlerin bileşenlerini anlamak gerçekten eğlenceli olsa da, "teoriden pratik" yapmak için
artık bu bilgiyi kullanmanın zamanı geldi. Şimdilik, SUT'unuzun herhangi bir işbirlikçiye ihtiyaç duymadığını
varsayarak, test etme konularının bir alt kümesine odaklanacağız. Bu varsayım - gerçek hayattaki sınıfların çoğu için
geçerli olmasa da - bazı önemli kavramları, fikirleri ve teknikleri göstermemizi sağlayacaktır. Bu koşullar altında
açıklamak çok daha kolay olacak, ancak bu tekniklerin kullanımı kesinlikle işbirlikçisiz bir ortamla sınırlı değildir.
Aslında, örneğin TDD yaklaşımı gibi bazıları, Unit test etmenin kendisiyle sınırlı değildir.

Daha sonraki bölümlerde (5. Bölüm'den başlayarak, Mocks, Stubs, Test Spies) bu gerçekçi olmayan varsayımı bırakıyor ve
SUT'unun çeşitli şekillerde işbirliği yaptığı işbirlikçilerle test etme tekniklerini tartışıyoruz. Ancak şimdilik,
SUT'unuzun yalnız olduğunu varsayalım.

JUnit, özellikle Unit testlerine odaklanan en popüler Java test framework'udur. Tools girişini okuduktan sonra, JUnit
testleri yazmayı ve yürütmeyi öğreneceğiz. Ayrıca, kitap boyunca tekrar tekrar kullanacağımız bazı JUnit özelliklerini
de öğreneceğiz. Bunlardan bazıları burada kısaca bahsedilecek ve daha sonra detaylı bir şekilde tartışılacak.

Bu kitap, her ne kadar yüksek kalitede Unit testleri yazmak isterseniz bilmeniz gereken her şeyi içerse de, kapsamlı
bir JUnit öğretici değildir. JUnit burada açıklananlardan daha fazlasını sunar, bunlar arasında integration ve
end-to-end testler için faydalı bazı özellikler bulunur. Bu araçta gerçekten ustalaşmak istiyorsanız, JUnit belgelerine
başvurmanız gerekmektedir.

## 3.1 Project Structure and Naming Conventions

İlk Unit test deneyimimiz için, JUnit tarafından popüler bir Unit test öğreticisinde kullanılanla neredeyse aynı olan
bir Money sınıfı kullanacağız. Unit test için, Money sınıfı, herhangi bir programlama dilinde ünlü HelloWorld örneği
tarafından oynanan rolü benzer şekilde oynar:  Çok basit (ve dürüst olmak gerekirse oldukça kullanışsız) bir sınıfla
başlayacağız. Daha sonra genişletilecektir.

Money class'ini record olarak ayarladım

### 3.2 Class To Test

```
public record Money(int amount, String currency) {

    @Override
    public boolean equals(Object anObject) {
        if (anObject instanceof Money money) {
            return money.currency().equals(currency()) && amount() == money.amount();
        }
        return false;
    }
}
```

Gördüğünüz gibi, Money sınıfı immutable'dır. İki adet field'e sahiptir. Biraz logic içeren tek method, equals()'ın
implementasyonudur

## 3.3 Your First JUnit Test

Bir test yazmadan önce, bir test senaryoları listesine ihtiyacınız vardır. Eğer yardımcı oluyorsa bir yere not
alabilirsiniz, ancak genellikle kafanızda tutmak yeterli olacaktır.

Money sınıfının koduna baktığınızda, test edilebilecek iki şey fark edebilirsiniz:

- Constructor
- equals() methodu

Constructor metodunun test edilmesi basit bir konu gibi görünüyor ve bu yüzden tam olarak bununla başlayacağız.
Burada kontrol edebileceğimiz tek şey, amount ve currency'nin doğru şekilde ayarlanmış olup olmadığıdır.

```
    @Test
    public void constructorShouldSetAmountAndCurrency(){
        Money money = new Money(10,"USD");
        assertEquals(10,money.amount());
        assertEquals("USD",money.currency());
    }
```

- Bir SUT oluşturulur.
- SUT test edilir ve sonuçlar Assert sınıfının statik assertEquals() methodları kullanılarak doğrulanır.

## 3.3.1 Test Results

Elle yapılan testler (bu konuda bir tartışma için Bölüm A.1'e bakınız) "bende çalışıyor"dan, "emin değilim, ama daha
fazla araştırmak için zamanım yok" durumuna kadar bir dizi sonuçla sonuçlanabilir. Automated testlerde durumlar farklı
görünür. Sadece birkaç olası sonuç vardır. Bunlara daha yakından bakalım.

Automated bir test, geçti veya başarısız oldu şeklinde iki durumdan birinde sonlanır. İki diğer sonuç daha az sıklıkla
gerçekleşir - bir test atlanabilir veya hata ile tamamlanabilir. Automated testlerde "Şimdi çalışması gerektiğini
düşünüyorum" gibi bir yer yoktur!

Bir testin tüm doğrulamaları karşılanırsa ve beklenmedik bir exception oluşmazsa, o test başarılı olarak kabul edilir.
Başarılı bir test genellikle IDE'lerde ve test raporlarında yeşil renkle işaretlenir.

Eğer beklenmedik bir exception oluşursa, o zaman test başarısız olur. Bu, bir doğrulamanın karşılanmaması durumunda veya
kodunuzda bir hata olması durumunda, örneğin ArrayIndexOutOfBoundsException gibi bir hata oluşması durumunda
gerçekleşir. IDE'niz ve test raporlarınız, böyle bir başarısız testi kırmızı renkle işaretleyecektir.

Bir test, bazı varsayımlarının karşılanmaması durumunda (bkz. Bölüm 6.3) veya kullanıcı tarafından açıkça atlanması
gerektiğine karar verildiğinde atlanabilir (hiç çalıştırılmaz). Bu tür bir test genellikle sarı renkle işaretlenir.

Son olarak, bir test, yürütmesini kesen beklenmedik bir durum gerçekleştiğinde hata olarak sonuçlanabilir. Bu genellikle
nadir bir durumdur ve genellikle test kodunuzda bir sorun olduğunu gösterir. Örneğin, bir test methodu bazı
parametreleri bekliyor ancak bunlar sağlanmadığında bu durum oluşabilir (bkz. Bölüm 3.6). Başarısız testler gibi, hata
durumunda sonuçlanan testler de kırmızı renkle işaretlenir. Genellikle raporlarda başarısız testlerle birlikte
gruplanır.

## 3.4 JUnit Assertions

assertEquals() Bu assertion, expected değeri actual değerle karşılaştırır ve eşit değillerse bir assertion error
fırlatır. Actual value, expected value ile eşleştiğini doğrulamak için kullanılan yaygın bir assertion'dır.

```
assertEquals(10,money.amount());
assertEquals("USD",money.currency());
```

Tablo 3.1, org.junit.Assert sınıfının assertion methodlerini göstermektedir. Gösterilen methodlerin çoğu, farklı
parametrelere sahip çeşitli varyantlarda gelir (örneğin, assertEquals() methodi, double, long, String ve diğerleri gibi
iki parametre kabul eder). Kullanılabilir assertion methodleri hakkında detaylı bilgi için JUnit Javadocs'a başvurunuz.

![img.png](Chapter3Images/img.png)

Yukarıdaki methodlerin bazıları (örneğin, assertTrue(), assertNotNull()), yalnızca bir parametre ve isteğe bağlı bir
message alır (bkz. Bölüm 3.5). Diğerleri - örneğin, assertEquals() ve assertSame() - iki parametre ve isteğe bağlı bir
message alır. Böyle bir durumda, parametrelerin sırası aşağıdaki gibidir:

1 - (optional) message
2 - expected value
3 - actual value

İlk iki parametrenin sırasının ne fark yarattığını sorabilirsiniz. A'yı B'ye karşılaştırmak ile B'yi A'ya karşılaştırmak
arasında herhangi bir fark var mı? Sonuç açısından bir fark yoktur. Sonuçları aynı/eşit veya farklı olabilir, değil mi?
Farkı sadece test başarısız olduğunda fark edersiniz. Bunun üzerinde yakında duracağız.

Başka bir nokta da yukarıdaki tüm ifadeleri gerçekten öğrenmeniz ve onlardan faydalanmanız gerektiğidir. İlk başta
sadece en basitleriyle yetinmeye meyilli olabilirsiniz: yani assertTrue() ve assertFalse(). Onlar, doğru veya yanlış
değerlendiren bir koşul yazarsanız hemen hemen her şeyi doğrulamanıza izin verecektir. Doğru, ancak assertSame(),
assertNull() veya assertNotEquals() ile sonuçları doğrulamak, test kodunuzu çok daha okunabilir hale getirecektir.
Unutmayın, "iş için doğru aracı kullanın"! Bu her seviyede geçerlidir.

## 3.5 Failing Test

Hadi bir testin başarısız olduğunda ne olduğunu görelim. Kaçınılmaz olarak birçok başarısız test göreceksiniz, bu yüzden
onunla iyi bir şekilde tanışmak daha iyi olacaktır.

Testin başarısız olması için, Money sınıfının constructor'ında küçük bir değişiklik yapmamız gerekiyor.

```
    public Money {
        amount = 15;
    }
```

Record'un constructor'ını eziyorum ve amount'un degerini 15'e set ediyorum. Ne olursa olsun, argümanla geçilen değere
bakılmaksızın, amount değişkeni 15 olarak ayarlanacak.

Tabii ki, bu değişiklik testimizdeki (assertEquals(10, money.getAmount());) assert ifadelerinden birinin başarısız
olmasına neden olacak. Testi yeniden çalıştırdıktan sonra aşağıdaki mesaj ve stacktrace görüntülenecektir:

![img_1.png](Chapter3Images/img_1.png)

Bu satır, bir assert ifadesinin başarısız olduğunu bildiriyor. assertEquals() ifadesinin her iki parametresinin
değerleri yazdırılıyor: 10 expected, ancak actual 15 döndü.

Dikkat etmemiz gereken bir şey, assert ifadelerinin parametrelerinin sırasının gerçekten önemli olmasıdır. Testin
başarısız olma nedeniyle ilgili yazdırılan bilgiler, varsayılan sıraya (hatırlayın: önce expected value, sonra actual
value) uyduğumuz varsayımına dayanmaktadır. Başka herhangi bir durumda, yazdırılan bilgiler yanıltıcı olabilirdi.

## 3.6 Parameterized Tests

Genellikle, çeşitli input değerleriyle aynı methodu test etmek ve farklı output'lar beklemek önerilir. Bu bölümde,
JUnit'in bu görevde bize nasıl yardımcı olabileceğini öğreneceğiz.

## 3.6.1 The Problem

Varsayalım ki, 10 USD ile birlikte Money sınıfının constructor'ını test etmek istiyoruz ve ayrıca onu 20 EUR ile de test
etmek istiyoruz. Şu şekilde yapabiliriz;

```
    @Test
    public void constructorShouldSetAmountAndCurrency(){
        Money money = new Money(10,"USD");
        assertEquals(10,money.amount());
        assertEquals("USD",money.currency());

        money = new Money(20,"EUR");
        assertEquals(20,money.amount());
        assertEquals("EUR",money.currency());
    }
```

Bu yaklaşım işe yarayacak, ancak dezavantajları açıkça görülebilir. İlk olarak, duplication çok fazladır ve DRY (Don't
Repeat Yourself) prensibine açıkça aykırıdır. İkincisi, bu tür kod genellikle "kopyala&yapıştır" tekniği kullanılarak
oluşturulur, bu da sadece bir kısmını değiştirirken tüm bölümü kopyalama yoluyla sorun yaşamaya kesin bir reçetedir.
Üçüncü olarak, her yeni argüman setiyle birlikte test sınıfı büyüyecektir. Yeter artık! Daha iyi bir yol olmalı!

Yukarıda sunulan tekrarlamalardan kaçınmak için çeşitli teknikler kullanabilirsiniz. Örneğin, bir for döngüsü
kullanabilirsiniz. Bu daha iyi hale getirir, ancak aynı zamanda testinize logic (çok temel bir türde olsa da) dahil
etmenize neden olur, ki bu önerilmez (bkz. Bölüm 10.2). Ayrıca, constructorShouldSetAmountAndCurrency() methodunu bir
dizi daha küçük methode bölebilirsiniz, her biri yalnızca bir set parametresini kontrol eder. Evet, ancak bu, önceki
olarak tartışılan basit yaklaşımın istenmeyen özelliklerine benzer istenmeyen özelliklere sahip olurdu.

## 3.6.2 The Solution

Neyse ki, burada kendi çözümünüzü bulmanıza gerek yok. Bu gereksinim o kadar yaygındır ki, test framework'leri tam
olarak bu tür durumlar için bazı destekler sunar. Buna "parameterized testler" denir.

Hangi parameterized test aracını kullanmaya karar verirseniz verin, genel fikir her zaman aynıdır. Bir önceki anda
tartışılan constructorShouldSetAmountAndCurrency() methodu gibi, test methodu iki bölüme ayrılır. İlk bölüm, ikinci
bölüme (actual test methoduna) aktarılacak argümanların bir kaynağı olarak hareket eder ve gerçek testi yapmakla
sorumludur.

Daha önce belirtildiği gibi, JUnit'in parameterized testler için desteği sınırlıdır. Bu görev için ek bir, çok daha
güçlü bir kütüphane kullanacağız: JUnitParams.

**JUnitParams**'ın dependency'sini gradle icerisine ekliyorum;

```
testImplementation("org.junit.jupiter:junit-jupiter-params:5.9.3")
```

JUnitParams, veri sağlayan kısmın implementasyonu konusunda size seçenek sunar. Test methodu üzerinde bir annotation
veya ayrı bir test methodi olarak şekillenebilir.

Şimdi JUnitParams kullanarak test koduna bakalım. Birkaç yeni şey olacak, ancak bunları tek tek ele alacağız. Dikkat
edilmesi gereken temel nokta, sorumlulukların net bir şekilde ayrılmasıdır: getMoney() methodu veriyi sağlar ve
constructorShouldSetAmountAndCurrency() methodu test algoritmasını sağlar. Bu iki methodun birleştirilmesi, test
methodundaki @ParameterizedTest annotation'i sayesinde gerçekleştirilir.

```
class MoneyTest {
    private static Object[] getMoney(){
        return new Object[]{
          new Object[] {10,"USD"},
          new Object[] {20,"EUR"}
        };
    }

    @ParameterizedTest
    @MethodSource("getMoney")
    public void constructorShouldSetAmountAndCurrency(int amount, String currency) {
        Money money = new Money(amount,currency);
        assertEquals(amount,money.amount());
        assertEquals(currency,money.currency());
    }
}
```

@MethodSource anotasyonu testte kullanilacak olan methodu isaret eder.

Data sağlayan methodların bir Object[] array'i döndürmesi beklenir.

constructorShouldSetAmountAndCurrency() methodu, amount ve currency olmak üzere iki parametre bekler. getMoney() methodu
tarafından döndürülen array'in her satırı bu iki değeri içerir.

İlk argüman seti, sayı olarak 10 ve currency olarak USD'yi içerecektir. İkinci argüman seti, sayı olarak 20 ve currency
olarak EUR'yi içerecektir. Sabit değerler yerine, her iki argüman da Money sınıfından bir nesne oluşturmak ve
doğrulamak için kullanılır.

JUnit'in "arka planda" iki test durumu oluşturduğunu ve şimdi iki testin gerçekleştirildiğini bildirdiğini göreceksiniz.
Daha da önemlisi, ne olduğu hakkında ayrıntılı bilgi alacaksınız: yani her test için hangi değerlerin kullanıldığı.
MoneyTest açısından, sadece testin geçtiğini biliyorsunuz. MoneyParameterizedTest için ise hangi parametrelerin dahil
olduğunu da biliyorsunuz. Başarılı bir test çalışmasında bu büyük bir olay olmayabilir, ancak bir hatada hangi veri
kombinasyonunun hataya neden olduğunu hemen bileceksiniz.

## 3.6.3 Conclusions

Özetlemek gerekirse, özel bir kod yerine parametreli testler kullanmanın avantajları aşağıdaki gibidir:

- Özelleştirilmiş bir mantık (örneğin bir döngü) yerine, hatalı olabilecek kendi mantığınızın hiçbirini dahil
  etmezsiniz. Bu, testin okunabilirliğini artırır.
- Başka bir argüman seti eklemek çok kolaydır ve kodun büyümesine neden olmaz.
- Tek bir veri sağlayan method, birden fazla test methoduna (ve hatta birden fazla test sınıfına) veri sağlamak için
  kullanılabilir, bu da kodu daha da özlü hale getirir.
- Kopyala-yapıştır kodlama olmaz, "global" değişkenler olmaz ve DRY prensibi sadakatle yerine getirilir.
- Test mantığı (kodun nasıl çalışması gerektiği) ile test verileri (hangi değerlerin test edildiği) arasında net bir
  ayrım vardır.
- Testlerin yürütülmesiyle ilgili daha detaylı sonuçlar elde ederiz.

## 3.7 Checking Expected Exceptions

Zaman zaman kodunuzun exception fırlatması gerekebilir. Belki bir method beklenmedik (geçersiz) bir değer alır. Belki de
işbirliği yaptığı third-party taraf bileşen bir exception fırlatmıştır. Neyse, exception'lar, methodlarınızın
davranışının önemli bir parçasıdır. Sonuçların döndürüldüğü kadar önemlidirler. Sınıfınızın arayüzüne aittirler ve
dolayısıyla test edilmelidirler.

Neyse ki, beklenen istisnaları kontrol etmek günümüzde çok kolaydır. Aşağıdaki kod parçacığı bunu açıklar:

```
    @Test
    public void shouldThrowExceptions(){
    }
```

Şimdi bir örnek üzerinde durabiliriz. Money sınıfına bir değişiklik yapalım. Constructor'unda bir
IllegalArgumentException fırlatmasına izin verelim, eğer:

- amount 0'dan kucukse
- currency null yada bossa

```
public Money{
        if (amount<0){
            throw new IllegalArgumentException("Illegal amount : [" + amount + "]");
        }
        if (currency == null || currency.isEmpty()){
            throw new IllegalArgumentException("Illegal currency : [" + currency + "]");
        }
    }
```

Money constructor'ini hallettikten sonra test'lerimizi yazalim;

```
    private static final int VALID_AMOUNT = 5;
    private static final String VALID_CURRENCY = "USD";

    private static Object[] getInvalidAmount() {
        return new Integer[][]{{-12387}, {-5}, {-1}};
    }

    private static Object[] getInvalidCurrency() {
        return new String[][]{{null}, {""}};
    }

    @ParameterizedTest
    @MethodSource("getInvalidAmount")
    public void constructorShouldThrowIllegalArgumentExceptionForInvalidAmount(int invalidAmount) {
        new Money(invalidAmount, VALID_CURRENCY);
    }

    @ParameterizedTest
    @MethodSource("getInvalidCurrency")
    public void constructorShouldThrowIllegalArgumentExceptionForInvalidCurrency(String currency){
        new Money(VALID_AMOUNT,currency);
    }
```

gösterilen test metodlarında dikkate değer bir başka nokta, doğrulama (assertion) eksikliğidir. Aslında beklenen
exception'ların olup olmadığını kontrol etmek için herhangi bir kod yazmaya gerek yoktur. JUnit, bunu @Test
anotasyonu attribute'ları içindeki bilgilere dayanarak otomatik olarak halleder. Aslında otomatik olarak try/catch
calistirir

* her iki test de yalnızca belirli bir istisna fırlatıldığında başarılı olacaktır.
* her iki test metodunda da veri sağlayan methodlar kullanılır.
* iki testde de assert'inlar bulunmamaktadır

Burada vurgulamak istediğim şey, methodlara ve değişkenlere anlamlı isimler vermenin önemidir. Bu, testin okunabilir
olmasını sağlar ve her method veya değişkenin rolü konusunda şüpheye yer bırakmaz. Şu satıra bir göz atalım:

```
new Money(invalidAmount, VALID_CURRENCY);
```

Değişkenler için anlamlı isimler kullanarak, oldukça okunabilir bir test elde ettik. Sadece okuyun: "Bu kod satırı,
invalid amount ve geçerli bir money currency kullanarak Money sınıfından yeni bir nesne oluşturur." Her şey tamamen
açık.

## 3.8 Test Fixture Settings

"Test fixture" terimi, testlerin çalıştırıldığı ve sonuçların tekrarlanabilir olduğu "bilinen ve sabit bir ortam"
fikrini ifade eder. Bu tür bir ortamı oluşturmak için gereken adımlar, test türlerine ve kullanılan araçlara bağlı
olarak farklılık gösterebilir, ancak temel fikir her zaman aynıdır.

Bir yazılım bileşeninin (SUT) methodlarinin yürütülmeden önce tüm unsurların yerli yerinde olduğundan emin olma zamanı
geldi. Bu bölümde, test bileşeninin oluşturulması için JUnit'in basic desteğini öğreneceğiz. Yeni bazı anotasyonları
öğrenecek ve optimal test kodu yapısını tartışacağız.

## 3.8.1 Test Fixture Examples

Test ortamının otomatik olarak oluşturulması süreci genellikle testin en zorlu kısmıdır. Bu özellikle integration ve
end-to-end testler için geçerlidir. Unit testler için durum genellikle daha basit olsa da, yine de dikkate alınması
gereken bazı konular vardır. Aşağıdaki tablo, farklı test türleri için test bileşenleri örneklerini vermektedir.

![img_2.png](Chapter3Images/img_2.png)

Şimdi basit bir Client ve Address sınıfını tanıtalım. Varsayalım ki Client sınıfının nesnelerinin bir adres
koleksiyonunu depolayabildiğini doğrulamak istiyoruz. Bu sınıfın test edilmesine yönelik ilk yaklaşımımız aşağıdaki gibi
olabilir:

```
public class Client {
    List<Address> addresses = new ArrayList<>();

    public List<Address> getAddresses() {
        return addresses;
    }

    public void addAddress(Address address) {
        addresses.add(address);
    }
}
```

```
public record Address(String street) {
}
```

Iki class'i da create ettikten sonra test'lerimizi yaziyoruz. Aslinda ilk once testi yazmamiz gerekiyor ki nelere
ihtiyacimiz oldugunu gorelim

```
class ClientTest {
    private final Address addressA = new Address("street A");
    private final Address addressB = new Address("street B");

    @Test
    public void afterCreationShouldHaveNoAddress(){
        Client client = new Client();
        assertEquals(0,client.getAddresses().size());
    }

    @Test
    public void shouldAllowToAddAddress(){
        Client client = new Client();
        client.addAddress(addressA);
        assertEquals(1,client.getAddresses().size());
    }

    @Test
    public void shouldAllowToManyAddresses(){
        Client client = new Client();
        client.addAddress(addressA);
        client.addAddress(addressB);
        assertEquals(2,client.getAddresses().size());
        assertTrue(client.getAddresses().contains(addressA));
        assertTrue(client.getAddresses().contains(addressB));
    }
}
```

Bu test düzgün olsa da, nesnelerin initialize edilmesi ile ilgili tekrar eden bir kod fazlası bulunmaktadır. Her test
yönteminde client değişkeni oluşturulmaktadır. Eğer sadece her test methodu çalıştırılmadan önce onun önceden
oluşturulmasını sağlayabilseydik...

## 3.8.3 JUnit Execution Model

Test kodunda tekrarı önlemek için, JUnit'in @Test anotasyonuyla işaretlenmiş herhangi bir test yöntemi çalıştırmadan
önce test sınıfının yeni bir instance'inin oluşturduğu gerçeğini kullanabiliriz. addressA ve addressB gibi her bir örnek
değişkeninin, afterCreationShouldHaveNoAddress(), shouldAllowToAddAddress() ve shouldAllowToAddManyAddresses() test
methodlarının herhangi biri çalıştırılmadan önce yeniden oluşturulduğu anlamına gelir.

Her test yönteminde yeni bir Client'a ihtiyacımız varsa, yapmamız gereken tek şey onu bir instance field yapmaktır;

```
class ClientTest {
    private final Address addressA = new Address("street A");
    private final Address addressB = new Address("street B");
    private final Client client = new Client();

    @Test
    public void afterCreationShouldHaveNoAddress(){
        assertEquals(0,client.getAddresses().size());
    }

    @Test
    public void shouldAllowToAddAddress(){
        client.addAddress(addressA);
        assertEquals(1,client.getAddresses().size());
    }

    @Test
    public void shouldAllowToManyAddresses(){
        client.addAddress(addressA);
        client.addAddress(addressB);
        assertEquals(2,client.getAddresses().size());
        assertTrue(client.getAddresses().contains(addressA));
        assertTrue(client.getAddresses().contains(addressB));
    }
}
```

Client simdi bir instance field... Bu test, önceki testle işlevsel olarak eşdeğerdir, ancak biraz daha kısadır.
Görülebileceği gibi, client değişkeninin oluşturulması artık birden fazla yönteme dağılmamakta, sadece bir kez
gerçekleşmektedir. Ayrıca, tekrarlamalar da yoktur. Daha fazla test yöntemi bulunsaydı, bu yaklaşımın avantajları daha
da belirgin olurdu.

Bu testin okunması biraz daha zor olabilir, çünkü nasıl çalıştığını anlamak için test methodu dışına bakmamız gerekiyor.
Bu hatırlanması gereken bir noktadır.

## 3.8.4. Annotations for Test Fixture Creation

Bazı durumlarda, önceki paragraflarda sunulan yöntem çalışmayabilir. Özellikle, Client sınıfının nesnesi üzerinde bazı
methodları çağırmamız gerektiğinde bu durum ortaya çıkar.

Aynı problemi çözmenin başka bir yolu, tüm nesnelerin oluşturulmasından sorumlu olacak bir setUp() yöntemi (metod)
tanıtmaktır. Bu methodu @Before anotasyonuyla işaretleyerek, JUnit'e tam olarak bunu yapmasını söyleriz: her test
methodu çalıştırılmadan önce bu methodu çalıştırmasını sağlarız.

```
    private final Address addressA = new Address("street A");
    private final Address addressB = new Address("street B");
    private static Client client;

    @BeforeAll
    static void setUp() {
        client = new Client();
    }
```

**@BeforeAll** anotasyonu kullanildiginda method ve field mutlaka **static** olmalidir. @BeforeAll anotasyonu, JUnit'in
bu methodu her test methodu çalıştırılmadan önce çalıştırmasını sağlar. Bu, gerçek testlerin çalıştırılması öncesinde
gerekli nesnelerin hazırlanması ve gerekli başlatma adımlarının gerçekleştirilmesi için kullanılır. @BeforeAll
anotasyonunu kullanarak, her testten önce tutarlı bir şekilde kurulum işleminin gerçekleştirilmesini sağlayabiliriz. Bu,
kod tekrarını azaltır ve test kodunu daha okunabilir ve sürdürülebilir hale getirir.

Method yürütme aşamaları şu şekilde olur:

![img_3.png](Chapter3Images/img_3.png)

**@AfterAll** anotasyonu ile işaretlenen methodlar her test methodundan sonra calisirlar

```
    @AfterAll
    static void setUpAfter(){
        System.out.println("setUpAfter() method called");
    }
```

@BeforeAll ve @AfterAll anotasyonları hakkında konuştuğumuzda, bunların unit testlerinde nadiren kullanıldığını
belirtmek istiyorum. Unit testlerinde genellikle testlerin tamamlandıktan sonra temizlenmesi (kapatma, silme veya
kaldırma) istenen dosyalar, akışlar veya veritabanlarıyla etkileşimde bulunulmaz.

## 3.9. Phases of a Unit Test

Şimdi bazı Unit testlerle karşılaştığımıza göre, yapılarını daha yakından inceleyebiliriz. Muhtemelen fark ettiğiniz
gibi, bir Unit testi üç şeyle ilgilenir: ilk olarak, test edilecek bir nesne oluşturur (SUT) ve diğer gerekli
nesneleri (SUT'un işbirlikçileri) de oluşturur, ardından SUT'un yöntemlerini çalıştırır ve son olarak sonuçları
doğrular. Bu desen, Unit testler için o kadar yaygındır ki, bu tür testler genellikle "arrange, act, assert"
testleri olarak tanımlanır.

![img_4.png](Chapter3Images/img_4.png)

phase -> arrange -> Test yürütme için gereken SUT hariç tüm nesnelerin oluşturulması.
-> Test edilecek fonksiyonelliğe sahip nesnenin oluşturulması ve başlangıç durumuna ayarlanması.

act -> Test edilecek SUT (System Under Test) yöntemlerinin yürütülmesi.

assert -> Test sonuçlarının doğrulanması

Şimdi, önceki bölümde tartıştığımız ClientTest sınıfını analiz edelim. Tablo 3.4, bu testin arrange, act, assert
yapısıyla nasıl uyumlu olduğunu göstermektedir.

![img_5.png](Chapter3Images/img_5.png)

tek bir test methodu içindeki tüm doğrulama ifadelerinin, bir nesnenin (SUT) özelliklerini doğrulaması önerilir. Tek bir
test methodu içinde birçok nesne üzerinde doğrulama yapmak kötü bir uygulama olarak kabul edilir ve kaçınılmalıdır!

## 3.10 Conclusions

Bu bölümde JUnit ile tanıştınız ve şunları öğrendiniz:

- kitap boyunca kullanılacak varsayılan proje yapısı
- Test class'ları ve test yöntemleri nasıl yazılır
- Test'ler nasıl çalıştırılır
- JUnit, test yöntemlerinde beklenen sonuçları doğrulamak için kullanılan geniş bir doğrulama (assertion) yelpazesi
  sunar
- Parameterized test'ler nasıl kullanılır
- Beklenen exception'ları doğrulama
- Test Fixture yönetimi için annotasyonları nasıl kullanabilirim? (@BeforeAll, @AfterAll etc.)

Bu bölümde ele aldığımız konular, gerçekten güzel ve kullanışlı unit testleri yazmak isterseniz yeterlidir. Ancak,
daha fazla ayrıntıya girmedik ve bunun iki nedeni vardır: İlk olarak, birçok durumda daha fazla ayrıntıya ihtiyaç
duyulmaz çünkü test framework'leri (JUnit de dahil olmak üzere) oldukça basittir. İkinci olarak, bu kitabın ilerleyen
bölümlerinde JUnit hakkında öğrendiklerinize ekleyeceğimiz bir şeyler olacak ve bu, ortaya çıkan specific context'lerde
daha anlamlı olacaktır.

JUnit, henüz ele almadığımız birçok başka özelliğe sahiptir. Bunlardan bazıları, takip eden bölümlerde ele alınırken,
diğerleri bu kitabın kapsamının dışındadır çünkü Unit testleriyle gerçek kullanım bağlantısı bulunmamaktadır.

Bu bölümde en önemli konuyu sorsaydınız, ben parameterized testlere işaret ederdim. Onların doğru kullanımı son derece
avantajlıdır ve birçok durumu kapsayan çok öz ve kısa testler yazmanıza olanak sağlar. Parameterized testlerin kullanımı
tekrarlamaları ortadan kaldırır ve testlerin okunabilirliğini artırır.

## 3.11 Exercises

Aşağıda sunulan egzersizlerin amacı iki yönlüdür: İlk olarak, kodunuzu Unit test etme fikrine alışmanıza yardımcı olmak
için buradadırlar ve ikinci olarak, bunları gerçekleştirerek JUnit özellikleri konusundaki bilginizi pekiştireceksiniz.

## 3.11.2 String Reverse

Verilen string degeri reverse edip donduren bir method'umuz var. Class'in ismi de StringUtil;

```
public class StringUtil {
    public static String reverseVerb(String verb) {
        List<String> tempArray = new ArrayList<>(verb.length());
        for (int i = 0; i < verb.length(); i++) {
            tempArray.add(verb.substring(i, i + 1));
        }
        StringBuilder reversedString = new StringBuilder(verb.length());
        for (int i = tempArray.size() - 1; i >= 0; i--) {
            reversedString.append(tempArray.get(i));
        }
        return reversedString.toString();
    }
}
```

Bu class'in bu fonksiyonunu test etmek icinde test methodlarini yaziyorum;

```
    public static Object[] getVerbs() {
        return new String[][]{{"deneme"}, {"markdown"}};
    }

    @ParameterizedTest
    @MethodSource("getVerbs")
    public void reverseVerb (String verbs) {
        String reversedString = new StringBuilder(verbs).reverse().toString();
        assertEquals(reversedString,StringUtil.reverseVerb(verbs));
    }

    @Test
    public void reverseShouldThrowNullPointerException(){
        StringUtil.reverseVerb(null);
    }
```

Test'imiz calisiyor ve olusabilecek durumlari test ediyor;

## 3.11.3 HashMap

java.util.HashMap class'ını test edecek bir test yazacagiz

- put() yöntemiyle depolanan bir nesne, get() yöntemiyle alınabilir.
- Aynı key ile ikinci bir nesne eklenmesi, eski değerin yerine geçmesine neden olsun
- clear methodu ile tum content silinsin
- null degeri bir key olarak kullanilabilsin

```
public class HashMapTest {
    final String KEY_1 = "KEY1";
    final String KEY_2 = "KEY2";
    final String NULL_KEY = null;
    final String VALUE_1 = "VALUE_1";
    final String VALUE_2 = "VALUE_2";

    private static HashMap<String, String> hashMap;

    @BeforeAll
    static void setUp() {
        hashMap = new HashMap<>();
    }

    @Test
    void putShouldReturnOneElement() {
        hashMap.put(KEY_1, VALUE_1);
        assertEquals(1, hashMap.size());
        assertTrue(hashMap.containsKey(KEY_1));
        assertEquals(VALUE_1, hashMap.get(KEY_1));
    }

    @Test
    void putWithTheSameKeyShouldReplaceValue() {
        hashMap.put(KEY_2, VALUE_1);
        hashMap.put(KEY_2, VALUE_2);
        assertEquals(1, hashMap.size());
        assertTrue(hashMap.containsKey(KEY_2));
        assertEquals(VALUE_2, hashMap.get(KEY_2));
    }

    @Test
    void clearShouldRemoveAllItems() {
        hashMap.put(KEY_1, VALUE_1);
        hashMap.put(KEY_2, VALUE_2);
        assertEquals(2, hashMap.size());
        hashMap.clear();
        assertEquals(0, hashMap.size());
        assertFalse(hashMap.containsKey(KEY_1));
        assertFalse(hashMap.containsKey(KEY_2));
    }

    @Test
    void nullValueCanBeUsedAsKey(){
        hashMap.put(NULL_KEY,VALUE_1);
        assertEquals(1, hashMap.size());
        assertTrue(hashMap.containsKey(NULL_KEY));
        assertEquals(VALUE_1,hashMap.get(NULL_KEY));
    }
}
```

## 3.11.4. Fahrenheits to Celcius with Parameterized Tests

![img_6.png](Chapter3Images/img_6.png)

Yukarıda ki resimde gosterilen test'i parameterized hale çevireceğiz;

Once methodlari iceren bir class create ediyorum;

```
public class FahrenheitCelsiusConverter {

    public static int toCelcius(int fahrenheit) {
        return (int)Math.round(((double) fahrenheit - 32.0) * 5.0 / 9.0);
    }

    public static int toFahrenheit(int celcius) {
        return (int) Math.round((double) celcius * 9.0 / 5.0 + 32.0);
    }
}
```

Parameterized testleri yaziyorum;

```
class FahrenheitCelsiusConverterTest {
    private static Object[] getValues() {
        return new Object[]{
          new Object[] {32,0},
          new Object[] {99,37},
          new Object[] {212,100}
        };
    }

    @ParameterizedTest
    @MethodSource("getValues")
    void shouldConvertCelciusToFahrenheit(int fahrenheit, int celcius) {
        assertEquals(fahrenheit,FahrenheitCelsiusConverter.toFahrenheit(celcius));
    }

    @ParameterizedTest
    @MethodSource("getValues")
    void shouldConvertFahrenheitToCelcius(int fahrenheit,int celcius){
        assertEquals(celcius,FahrenheitCelsiusConverter.toCelcius(fahrenheit));
    }
}
```

## 3.11.5 Master Your IDE

IDE'nizin etkili bir Unit test desteği sunabilmesi için harcadığınız zaman önemlidir. Özellikle, aşina olmanız gereken
iki önemli özellik vardır:

- Templates

Her kaliteli IDE, daha büyük kod yapılarını hızlı bir şekilde oluşturmak için özel şablonlar oluşturmanıza izin verir.
Verimli bir Unit testi için, en azından şunları öğrenmelisiniz:

    * Test class template'i create etmeyi
    * bazı typical code constructor'ları oluşturun - yani parameterized testler ve set-up methodları.

- Quick Navigation

Test class'i ile Production class'i arasinda dolasilabilecek shortcut tuslari ayarlayalım

## Chapter 4 Test Driven Development

Test etmeye başladığınızda, işler basitti. Kod yazdınız, ardından geçerliliğini doğrulamak için testler yazdınız. Bu
mantıklı görünüyordu ve iyi sonuçlar getiriyordu. Ancak bir süre sonra birkaç sorun çıkaran kişi ;) işleri baş aşağı
çevirmeye ve uygulama öncesinde testler yazmaya ısrar etti. Bu oldukça şaşırtıcı bir fikir gibi görünse de oldukça
popüler hale geldi ve daha iyi sonuçlar getirdiği iddia edildi.

Başlangıçta, gerçek kod yazmadan önce testler yazmak, onu mümkün olan en kısa sürede doğrulamanın bir yolu olarak
görülüyordu. Test-first yaklaşımını takip eden giderek daha fazla insan, hemen doğrulamanın sadece kazanç sağlanan bir
kısmı olduğunu keşfetti Aslında, herhangi bir uygulama yazılmadan önce testler hakkında düşünmek güçlü bir tasarım
tekniği olduğu ortaya çıktı. Kod, sadece (iyice test edildiği için) daha iyi çalışmakla kalmadı, aynı zamanda mimarisi,
bakımı ve API kullanım kolaylığı açısından da "daha iyi göründü ve hissetti".

Bu nedenle, test-first yaklaşımı artık genellikle Test Odaklı Geliştirme (Test Driven Development - TDD) olarak
adlandırılıyor. TDD ve test-first arasındaki ilişki hakkında hala bazı tartışmalar var. Bununla birlikte, bu terimlerin
genel anlamda aynı şeyi ifade ettiği görülüyor. Bu nedenle, kitap boyunca bu iki terimden birini veya diğerini
kullanmayı sırayla tercih edeceğim.

Bu kitap, test etme yönlerine odaklanmaktadır ve burada TDD yaklaşımıyla ilgili makul bir doz alacaksınız. Bununla
birlikte, onu okumaya başlamadan önce test teknikleri konusunda biraz deneyim kazanmanızı öneririm. Ayrıca, gerçekten
harika olsa da, size TDD'yi "öğretmeyecek". Pratik yapmak, pratik yapmak, pratik yapmak gerekiyor!

Bu bölümde, uygulamadan önce testlerin nasıl yazılacağına daha yakından bakacağız. Teorik bir arka plan sunmanın yanı
sıra, pratik üzerinde odaklanacağız. Unutulmaması gereken önemli bir nokta, test-first yaklaşımının yalnızca unit
testleri üzerinde gösterileceği anlamına gelmemesidir. Aksine, bu yaklaşım her test seviyesinde (ve hatta kullanılması
gereken) uygulanabilir olabilir.

## 4.1 When to Write Tests?

Önceki paragraflarda bu soruya verilen iki yanıttan bahsetmiştik:

* implementasyondan sonra test yazmak (test-last AKA code-first)
* implementasyondan once test'leri yazin (test-first)

Açıkça görüldüğü gibi, bu iki yaklaşım birbirine zıttır. Belirli bir kod parçası için hangi kuralı izleyeceğinizi
seçmeniz gerekecektir. Bununla birlikte, her uygulama görevi veya kod parçası için daha uygun olanı seçerek, tüm kodlama
sürecinde her ikisini de kullanmanız mümkündür.

Test-first yaklaşımına büyük destekçi olmama rağmen, 4.8. bölümde test-last yaklaşımını tercih etme nedenleri hakkında
bazı tartışmalar yapacağım.

Ancak, "ne zaman test yazmalıyız?" sorusuna verilen önceki iki yanıtı tamamlayan başka bir yanıt daha vardır: "her bir
hata bulunduğunda bir test yaz". Her bir yaklaşımı daha detaylı olarak açıklayalım.

## 4.1.1 Test Last (AKA Code first) Development

"Test-last" yaklaşımında, testler product kodu tamamlandıktan sonra yazılır. Bu, daha yeni "test-first" yaklaşımından
önce gelen "geleneksel" bir test yaklaşımıdır.

Koddan sonra test yazmanın avantajları ve dezavantajları bulunmaktadır. Ana avantaj, test edilen nesnenin işlevselliği
iyi anlaşıldığında testlerin yazılmasıdır. Ana dezavantaj ise geliştiricinin, SUT'nin arayüzünü (davranışını) test etmek
yerine uygulamayı test etmeye (birkaç dakika önce yazılan kodu) odaklanmasıdır. Bu, aşağıdaki gibi testlere yol
açabilir:

* Implementasyona sıkı sıkıya bağlıdır ve bu nedenle her değiştiğinde yeniden yazılması gerekebilir.
* bu sorunu hafifletmek için, mümkün olduğunca testleri implementasyondan ayırmak ve developer'ları (bilinçaltında)
  başarılı geçmesini bekledikleri test durumlarını seçmeye teşvik etmek önemlidir.

Ek olarak, "test-last" yaklaşımını kullanırken hiç test yazmama dürtüsü her zaman mevcuttur. Kodun beklenildiği gibi
çalıştığından oldukça eminseniz, neden uğraşasınız ki? Kendiniz deneyerek log mesajlarının tam olarak istediğiniz gibi
olduğunu gördüyseniz, neden uğraşasınız ki? İşleyen bir kodun ardından test yazmak için çok fazla öz disiplin
gereklidir. Ve son teslim tarihi yaklaştığında, bu ekstra ve gereksiz görünen adımı atlayarak zaman kazanma dürtüsü
artar. Bence, bu, test-first yaklaşımını takip etmek için ciddi bir neden oluşturur.

Bazı durumlarda, örneğin mevcut bir kodla çalışırken, "test-last" yaklaşımı tek mümkün olan yaklaşımdır çünkü kod zaten
mevcuttur.

## 4.1.2 Test First Development

Bu bölüm tamamen "test-first" geliştirmeye adanmıştır, bu nedenle en önemli özelliklerinin kısa bir tanımıyla
başlayalım.

Test-first yaklaşımında, gerçek kod yazılmadan önce testlerin yazılması, developer'ların test edilen nesnenin
davranışını düşünmelerini sağlar. Bu aşamada implementasyon henüz mevcut olmadığından, test senaryoları tarafından
belirlenen minimum işlevselliğe sahip olan her bir sınıfın işlevselliği azaltılır - gereksiz işlevsellikler eklenmez.
Ayrıca, genel olarak arzu edilen, çok yüksek (eğer mümkünse %100) bir code coverage düzeyine sahip olmayı sağlar (Bkz.
Bölüm 11.3).

Bu yaklaşım her test seviyesine uygulanabilir. Özellikle, unit testleri için çok popüler ve uygun bir yaklaşımdır.

## 4.1.3. Always after a Bug is Found

Bir hata bulunduktan sonra yapmanız gereken ilk şey, hemen düzeltme dürtünüzü kontrol altına almaktır. Eğer hatayı "
sadece böyle" düzeltirseniz, ondan tamamen kurtulma fırsatını kaçırırsınız. Hata daha sonra tekrar ortaya çıkabilir (
başka bir hatayı düzeltirken yapılan değişiklikler geri alındığında gibi). Hatanın tekrarlanmamasını sağlamak için onu
düzeltmeden önce bir test yazarsanız, testlerinizin güvenlik ağını güçlendirecek ve aynı hata tekrar ortaya
çıkmayacaktır.

Evet, test-first veya test-last yöntemiyle kod yazın, bir hatayla karşılaştığınızda hemen bir test yazmalısınız. Bu
test, hatayı ortaya çıkarmalı yani doğru şeyin olmasını beklemeli ve başlangıçta hata verecek şekilde, gözlemlenen
hatalı davranışı tekrarlamalıdır. Daha sonra, kodu düzelterek testin geçmesini sağlamalısınız.

Bir hatanın bulunduktan sonra bir test yazmanın, aslında test-first yaklaşımına denk geldiğini düşündüğünüzde fark
edersiniz. Hatayı taklit etmek için başarısız olan bir test yazarsınız ve ardından kodu uygularsınız.

Büyük sorun burada hatayı tam olarak belirlemek ve bunu ortaya çıkaran bir Unit testi yazabilmektir. Bu oldukça zor
olabilir. Uygulamanızın kullanıcı arayüzünü tıkladığınız süreçte iş mantığınızda birden fazla hata ortaya
çıkıyor. Tam olarak hangi sınıfın belirli bir hatalı davranıştan sorumlu olduğunu söylemek o kadar da kolay değildir.
Genellikle, öncelikle daha yüksek seviyeli bir test (yani end-to-end bir test) yazarak çözülür ve ardından nedeni
hakkında bilgi toplamak için daha odaklanmış testler yazarak arama alanı daraltılır: önce integration, ve sonunda
Unit testleri.

## 4.2 TDD Rhythm

Test-first kodlamanın güzel yanı, sürekli tekrarlanan birkaç basit adımdan oluşmasıdır. Ve-bu ilk kez karşılaşıldığında
oldukça şaşırtıcı bir şekilde - bu basit adımlar büyük bir kodlama deneyimi ve harika sonuçlar sağlar.

Şimdi, 4.1 Numaralı Şekil'e bir göz atmanı istiyorum. Bu resim basitleştirilmiştir. Tüm gerçeği anlatmaz. Daha fazla
ayrıntı eklemek için bunu daha da geliştireceğiz. Ancak, bu MASA ÜSTÜNÜZDE asılı durması gereken resimdir. Size çalışma
ritmini verir:

- Test yaz ve fail olsun (RED)
- Code uzerinde calis (GREEN)
- Gereksizleri kaldir (REFACTOR)

Asla başarısız bir test olmadan kod yazmayın!

![img.png](Chapter4Images/img.png)

Sonraki resim - Şekil 4.2 - TDD ritmi hakkında daha fazla bilgi verir. Bu resimde test listesiyle başladığınız,
bunlardan birini seçtiğiniz ve ardından red-green-refactor döngüsünü takip ederek sonunda yeşil renkle bitirdiğiniz
görülmektedir.

İş tamamlandığında (yani belirli bir işlevsellik uygulandığında), yazılacak başka test kalmadığında biter.

![img_1.png](Chapter4Images/img_1.png)

Sonraki bölümlerde TDD ritminin her bir unsurunu detaylı olarak tartışacağız.

## 4.2.1 RED - Write a Test that Fails

Uygulanması gereken bazı functionality hakkında düşünün ve bunu bir test şeklinde yazın. Bu functionality henüz
implemente edilmedi, bu nedenle test kaçınılmaz olarak başarısız olacaktır. Sorun yok. Artık şunu biliyorsunuz:

- Functionality gercekten calismiyor
- Birkez implemente edildikten sonra, test sonucu RED'den GREEN'e dönüşeceği için bunu göreceksiniz

İlk başta, henüz mevcut olmayan bir şey için testler yazmak size garip gelebilir. Kodlama alışkanlıklarınızda hafif bir
değişiklik gerektirir, ancak bir süre sonra bunu büyük bir tasarım fırsatı olarak görmeye başlayacaksınız. Testleri önce
yazarak, client'in kullanması için uygun bir API oluşturma şansınız olur. Testler, bu yeni oluşturulan API'nin ilk
client'larıdır. İşte TDD'nin asıl amacı budur: Bir API'nin tasarımı.

Kendi yazacağınız kodun client'i gibi düşünürken, gerçekten gereken şeylere odaklanmalısınız. "Bu koleksiyonu döndüren
bir getter'a gerçekten ihtiyacım var mı, yoksa bu koleksiyonun en büyük elemanını döndüren bir method daha uygun
olabilir mi?" gibi sorular sormalı ve bu soruları testler yazarak cevaplamalısınız. Bu kitap API tasarımına ayrılmamış
olsa da, bu konunun yüzeyine bile değinsek, kodlama stilinizde devrim niteliğinde bir değişiklik getirebilir. Artık "
belki bir gün birine faydalı olabilirler" düşüncesiyle gereksiz yöntemler yazmak yok, bir değişmez (immutable) sınıf
daha uygunken otomatik olarak oluşturulan getter/setter'lar da yok. Client'in (test kodunun) gerçekten neye ihtiyaç
duyduğuna odaklanın. Ve tam olarak bunu ve başka bir şeyi değil, sadece bunu test eden testler yazın.

Önce bir test yazmak, nesnenin API'si açısından düşünmenizi sağlar. Henüz uygulamanın nasıl görüneceğini
bilemeyebilirsiniz (bir vizyonunuz olsa bile). Bu iyi bir şeydir: Çünkü testleriniz, nesnenin dış davranışını ve
uygulama ayrıntılarını daha fazla test etme şansına sahip olacaktır. Bu, uygulamayla ayrılmaz bir şekilde bağlantılı
olmayan, çok daha bakımı kolay testlerin elde edilmesine yol açar.

Her zaman başarısız olan bir testle başlayın ve onun başarısız olduğunu gözlemleyin. Bu adımı ihmal etmeyin! Bu,
testinizin kalitesi hakkında bilgi edinmenin nadir yollarından biridir (bkz. Bölüm 11, Test Kalitesi).

Tabii ki, testi hemen yazdıktan sonra çalıştıramazsınız. Neden mi? Çünkü gerçekten "başarısız bir test olmadan kod
yazma" kuralını takip ettiyseniz, testiniz mevcut olmayan sınıflar ve yöntemler kullanıyor olacaktır. Basitçe
derlenebilme özelliği olmayacaktır. Bu nedenle, bu ilk adımın bir parçası olarak testi derleyebilir hale getirmeniz
gerekecektir. Genellikle, IDE (Entegre Geliştirme Ortamı) test kodundan kullanılan sınıflar ve yöntemler için
varsayılan (boş) bir uygulama oluşturarak bunu kolaylaştırır.

Gerçekte, başarısız bir test yazmak bazen değdiğinden daha fazla zahmet gerektirebilir. Bu konuda bir tartışma için
lütfen 4.9. bölüme bakınız.

### How To Choose the Next Test To Write

Sadece başarısız bir test yazmanızı "sadece öylece" yapmanızı istemek biraz adil olmayabilir. Kolay gibi görünse de,
bunu nasıl yapmanız gerektiği konusunda size nasıl ilerlemeniz gerektiğini anlatabilirim. Diyelim ki uygulanması gereken
bir işlevler listesi ve onları kapsayan bir test listesi var. Şu anda başa çıkmanız gereken soru ilk testi nasıl
seçeceğinizdir. Ardından, onu uyguladıktan ve kodu tamamlayarak TDD döngüsünü tamamladıktan sonra, bir sonraki test için
nasıl ilerleyeceksiniz? Ve sonraki test için nasıl ilerleyeceksiniz?

Bu standart bir sorundur ve bildiğim kadarıyla "standart" bir yanıt yoktur. Hangi testin bir sonraki olarak uygulanması
gerektiğini belirlemek için genel olarak kabul görmüş bir heuristik yoktur. Ancak, topluluk arasında paylaşılan bazı
ipuçları işe yarayabilir. Onlara bir göz atalım.

"Low-Hanging Fruit" kuralı, "çok basit bir şeyle başlayın. Açık bir test vakası uygulayın"

Bu teknik özellikle takıldığınız durumlarda faydalı olabilir. Bir şeyler yazmak, hatta önemsiz veya sadece minimal öneme
sahip bir şey bile olsa, bu tür "yazma bloğu" durumunu aşmanıza yardımcı olabilir. Hangi testleri yazmanız gerektiği ve
test edilen methodun nasıl davranması gerektiği konusunda şüpheleriniz varsa, ilk adımı atmak en iyi seçenek olabilir.
Bu şekilde uygulanan işlevselliği önemli olmasa bile, en azından bulmacanın bazı parçalarını (SUT sınıfını ve bazı
methodlarını) yerine getirmiş olursunuz. İlerlemenize yardımcı olabilir.

Size başlamak için basit bir test vakası yazma örneği verebilirim:

- Bir fonksiyon için (söz konusu fonksiyonun amacı ne olursa olsun) bir parametre kontrolü testi yazma örneği:
- Bir parser yazarken, boş bir String'i parser methoduna ileterek ve geriye null değeri alarak başlamak

Hiçbir durumda, test edilmesi ve uygulanması gereken ana mantığı (muhtemelen oldukça karmaşık olanı)
değiştirmeyeceksiniz. Bununla birlikte, gerçek görevle ilgili bazı bilgiler sağlayabilecek sınıf ve methodlara sahip
olacaksınız. Eğer kaybolmuş hissediyorsanız ve nasıl ilerleyeceğinizi bilmiyorsanız, bu gerçekten işe yarar olabilir.

En Bilgilendirici Olanı. Başka bir yaklaşım, uygulanan işlevsellik hakkında en fazla bilgiyi sağlayan testle
başlamaktır. Bu, topa tam isabetle vurmak gibidir: bilgi açısından mümkün olan maksimum geri dönüşü sağlar.

Bu genellikle en zor ikilemlerle yüzleşmek anlamına gelir. Neyse ki, yine de bu ikilemlerle başa çıkmak zorundasınız, bu
yüzden etrafında dönüp durmak yerine neden doğrudan aksiyona atılmıyorsunuz?

Bu yaklaşım, "ilk maçım dünya şampiyonuyla olsa bile önemli değil - eğer tüm turnuvayı kazanacaksam, yine de onu yenmek
zorunda kalacağım" demek gibi bir şeydir. Bazı insanlar bu tür motivasyonu sevebilir.

Doğru bir noktaya değindiniz. Uygulanan işlevsellik hakkında en fazla bilgiyi sağlayacak testi seçmek zor olabilir.
Ancak, cevabı belirlemek için bir yaklaşım, henüz nasıl geçireceğinizi bilmediğiniz testi düşünmektir. Bu test,
anlamadığınız veya uygulamanın daha karmaşık olabileceği alanları vurgular.

Önceki parser örneği için bu yaklaşımı benimsenmiş olsaydınız, muhtemelen bir tam cümleyi ayrıştırmaya çalışarak
başlardınız. Bu, uygulanan işlevsellik hakkında kesinlikle çok şey öğretirdi. Bir tam cümleyi ayrıştırmak,
çeşitli dilbilgisi kurallarıyla, cümle yapısıyla ve potansiyel olarak daha karmaşık ayrıştırma mantığıyla uğraşmayı
gerektirir.

Öncelikle, Typical Case'i Test Edin, Sonra Corner Case'lere Geçin. "Typical Case" test etmek oldukça makul
görünmektedir. Bu fonksiyonun en sık nasıl kullanılmasını beklediğinizi düşünün. Bir tokenizer yazarken, geçerli bir
cümleyi input olarak kullanarak başlayın. Bir otomatik satış makinesi uygulaması geliştirirken, müşterinin 1 dolarlık
bir madeni para eklediği ve makinenin stokta bulunan bir ürünü seçtiği bir durumu ele alarak başlayın. Daha sonra corner
case'leri uygulamaya geçebilirsiniz.

Bu yaklaşım, "natural" bir test sırasını temsil eder, (bkz. Bölüm 6.1) olarak tartışıldığı gibi.

Deneyiminizi Dinleyin. "next test" ikilemiyle başa çıkmak için muhtemelen en değerli yol, deneyiminize kulak
vermektir. Deneyim, bu özel durum için hangi yaklaşımın en uygun olduğunu size söylemelidir. Kişisel olarak, genellikle
önce Typical Case'leri ele almayı tercih ederim, ancak bazen diğer yaklaşımları da kullanırım.

### Readable Assertion Message

Bir başarısız testiniz olduğunda ve bunu düzeltmek için kodu implementasyona başlamadan önce, ek bir ayrıntıyla
ilgilenmek tavsiye edilir: başarısız test tarafından yazdırılan mesajın neyin yanlış olduğunu tam olarak belirttiğinden
emin olun. Bazen, aslında çoğu zaman, JUnit tarafından varsayılan olarak yazdırılan bilgiler yeterlidir. Ancak yeterli
değilse, hata mesajı üzerinde çalışın (bazı ipuçları için Bölüm 8.4'e bakın) ve sonuçtan memnun kalana kadar
düzenlemeler yapın. Ardından, bir sonraki adıma geçin.

## 4.2.2. GREEN - Write the Simplest Thing that Works

Şimdi bir başarısız testiniz ve açık bir doğrulama mesajınız olduğuna göre, tabii ki kod yazarak testi geçireceksiniz!
Buradaki amaç çok ileri gitmemektir. Testi geçirecek en küçük miktarda kodu yazmak önemlidir. Eldeki göreve odaklanın.
Çubuğu yeşile dönüştürmelisiniz, işte bu kadar. Mümkün olabilecek geliştirmeler hakkında (çok fazla) düşünmeyin,
sınıfınızın potansiyel kullanımlarıyla ilgili endişelenmeyin, "güzel olacak" özelliklerde takılıp kalmayın. Henüz
uygulanmamış olan testlerin gereksinimlerini karşılamaya çalışmayın (ancak zaten zihninizde canlandırabiliyor
olabilirsiniz). Tek başarısız test, mevcut görevinizi netleştirir: sadece onu geçirin, başka bir şey yapmayın.

Unutmayın, başka testler de olacak. Diğer gereksinimleri kapsayacaklar. Ve zamanı geldiğinde, zihninizde size "hemen
uygula beni!" diye bağıran o harika özellikleri ekleyeceksiniz. Ah, onlara bir süre bağırmalarına izin verin. Belki
gerçekten gerekli olacaklar, belki de değil. Testleriniz size söyleyecek. Şimdiki göreve odaklanın.
Diğer yandan, ters tuzağa düşmeyin. "Basit" ve "aptalca" eşanlamlı değildir! "Çalışan en basit şey" basit olmalı, ancak
makul olmalıdır. Mevcut göreve odaklanmak, özensiz kod yazmak için bir bahane değildir.

Sizden, mimar veya tasarımcı olarak edindiğiniz deneyiminizi kulaklarınızı tıkamak için teşvik etmiyorum. Daha geniş
resmi düşünmemeniz gerektiğini söylemiyorum. Söylemek istediğim, "kodunuza kulak verin". Kendi fikirleriniz olsun, ancak
kodunuza kulak verin. Tasarımınıza rehberlik edebilir. Testlerinizi de dinleyin. Onlar size gerçekten önemli olanı
söyleyecek. Sizi heyecan verici olasılıkların sonsuz diyarında gezintiye çıkarmayacaklar.

## 4.2.3. REFACTOR - Improve the Code

Soru şu ki, basit özellikleri birer birer eklemek, son kodunuzun rastgele yerleştirilmiş unsurlar yığını gibi
görünmesine neden olmaz mı? Kod işleyecek, ancak geliştirmesi kolay olacak mı, esnek olacak mı, tüm nesne yönelimli
prensiplere (KISS, DRY, SRP vb.) uyacak mı? Ve işte bu noktada TDD ritminin bir sonraki adımına ulaşıyoruz: refaktoring.

Test geçtikten sonra kodunuza bazı değişiklikler yapabilirsiniz. Testlerin güvenliği size "çubuğu yeşil tuttuğunuz
sürece" hiçbir şeyi bozmayacağınız konusunda güven verir. O halde devam edin, refaktor yapın! Duplicated kodları
kaldırın, method adlarını değiştirin, değişkenlerin kapsamını güncelleyin, parçaları bir yerden başka bir yere taşıyın!
Ve testlerinizi sık sık yeniden çalıştırın. Refaktorlarınızın ne tür bir hasara yol açtığını size tam olarak
söyleyeceklerdir, eğer bir hasar varsa.

Peki, neden bu adım gereklidir? Bazı insanlar "eğer işe yarıyorsa, onarmayın" der - o zaman neden kendinizi bu görevle
yükleyelim? Cevap şu ki, önceki adımda (bir testi karşılayacak en az kodu yazma) elinizdeki göreve odaklandınız ve
uygulanan kod yeterince açık olmayabilir. İşe yarıyor, ancak bakım yapmak kâbus olabilir. Ve ötesinde, "sadece testi
geçmek için yeterli miktarda kod" yazarken sadece o belirli kod parçasını düşünüyordunuz. Şimdi daha geniş resme bakma
ve yalnızca birkaç dakika önce yazılan birkaç satır değil, tüm kodunuzu refaktor etme zamanı geldi.

Belki bazı gereksiz tekrarlamalar vardır, belki bazı işlevler private bir function'a taşınmalıdır, belki bazı
değişkenlerin adı değiştirilmelidir. Şimdi bunları yapma zamanı geldi.

Eğer "refactoring" yapmışsanız ve testler hala geçiyorsa, ancak uygulama şu anda bozulmuş durumdaysa, bu demektir ki
gerçekten "refactoring" yapmamışsınızdır. Sadece kodunuzu değiştirmişsinizdir. "Refactoring", testlerin güvenli bir ağı
üzerinde hareket etmeyi ifade eder. Tamamen test edilmiş bir kodla çalışmayı gerektirir. "Refactoring"e bir şeylerin
bozulması ile suçladığınızda adil davranmıyorsunuz. Basitçe kötü bir şekilde yapmışsınız demektir (yani yeterince iyi
test edilmemiştir).

### Refactoring the Tests

Testleri de yeniden düzenlemek (refactoring) gereklidir. Unutmayın, testler değerli bir varlıktır. Sıklıkla
çalıştırılacak, okunacak ve güncellenecektir. Kodunuzun temelini oluşturan ve bu nedenle sağlam ve yüksek kaliteli
olması gereken unsurlardır. Kodunuzun kalitesi testlerinize güçlü bir şekilde bağlıdır, bu yüzden gerçekten çok iyi
olduklarından emin olmanız gerekmektedir.

Refactoring, kodun işlevselliğini değiştirmeden yapısını yeniden yapılandırmak anlamına gelir. Testler açısından,
refactoring yapılmış testlerin önceden refactoring işlemine başlamadan önce aynı kod yollarını kullanması, aynı
metodları çağırması, aynı argümanları kullanması ve aynı doğrulamaları yapması gerektiği anlamına gelir.

Test refactoring'in açık bir sorunu, testlerin kendi testlerinin olmamasıdır, bu nedenle refactor işlemi sırasında
istenmeyen değişikliklerin yanlışlıkla yapılma riski vardır. Ancak, bu risk ilk bakışta göründüğü kadar ciddi değildir.
Öncelikle, doğru bir şekilde yazılan unit testleri gerçekten basittir (bkz. Bölüm 10.2). Karmaşık mantık içermezler, bu
nedenle değişiklikler yapıldığında bozulma riski taşımazlar. Testleri refactor ederken, test mantığıyla çalışmaktan
ziyade, örneğin bazı ortak işlevselliği set-up methodlarına taşımak gibi, daha çok hareketleri düzenlemekle
ilgilenirsiniz. Ayrıca, bir şeyleri yanlış yaparsanız, yeşil çubuğun hemen kırmızıya dönmesi muhtemeldir ve böylece
hemen bir hata olduğunu size bildirir.

Özetlemek gerekirse, dikkatli olun, ancak testlerinizi refactor etmekten çekinmeyin. Yürütülen test sayısının
değişmediğinden emin olun ve refactor işlemi sırasında yapılan değişikliklere güvenmiyorsanız, code coverage raporuna
bir göz atın (bkz. Bölüm 11.3).

### Adding Javadocs

Refactoring sürecinde production kodu ve testler için Javadoc dokümantasyonuna dikkat etmek önemlidir. Bu durumda iki
sorun ortaya çıkabilir:

1 - Öncelikle, tasarım henüz tam olarak fixed edilmediği için şu anda herhangi bir belgeleme yapmanın bir anlamı olup
olmadığı sorusu ortaya çıkabilir.

2 - Şu anda dökümentasyon yazmak, düşüncelerinizin akışını etkileyebilir. Zaten bir sonraki yazılacak test üzerine
odaklanmış durumdasınız. Bu "flow" denilen durumu kesip farklı bir etkinliğe geçmek iyi bir fikir mi?

Bu geçerli sorular, developer'ların belge yazma konusundaki doğal direnci tarafından pekiştirilir. Bu, belgelemenin
oluşturulmasının ertelenmesine yol açar... ki bu da tabii ki hiç belge olmamasına yol açar. Bu kısa vadede hoş
görünebilir, ancak uzun vadede ölümcül olabilir.

Önerim şu şekildedir:

- Kısa tutun - bu şekilde daha az zorlanırsınız. Sadece sınıfların ve metotların iş amacı ile ilgili ve önemli tasarım
  kararları hakkında yazın.
- Eğer gerçekten, gerçekten kodlama akışını kesmek istemiyorsanız, kodun içine TODO veya FIXME işaretleri koyarak
  kendinize bir not bırakın. Bunları kodun içine, monitörünüzdeki bir Post-it notuna veya sadece kafanızda bir not
  olarak kullanabilirsiniz (dikkatli olun, bu notlar genellikle kaybolma eğilimindedir!). Tabii ki, daha büyük işlev
  parçalarını bitirdikten sonra bu sorunu düzeltmeye ayıracak bir zaman ayırmanız gerekecektir.

Kendini belgeleyen kod yazmayı hedeflemelisiniz. Açıklayıcı metod isimleri kullanın ve okunabilir testler yazın. Bu
şekilde, Javadocs'a (bazı üst düzey açıklamalar dışında) neredeyse hiçbir şey koymamanız gerekecektir.

## 4.2.4. Here We Go Again

Kod yeniden düzenlendikten sonra, herhangi bir zararın ortaya çıkmadığından emin olmak için testleri tekrar çalıştırın.
Unutmayın, yalnızca son zamanlarda üzerinde çalıştığınız testleri değil, tüm unit testlerini çalıştırın. Unit testleri
hızlı bir şekilde çalışır: yalnızca seçilenlerin çalıştırılmasıyla zaman kazanılmaz. Tüm testleri çalıştırın,
geçtiklerini görün ve böylece bir sonraki teste geçmek için güvenli olun. Test yazma işlemi tamamlanana kadar bu süreci
tekrarlayın.

## 4.3 Benefits

TDD, iyi tasarım ve iyi kod kalitesine yardımcı olan bir uygulamadır, ancak bunları garanti etmez. İyi tasarım ve iyi
kod üretmek için hala yetenek, beceri ve uzmanlık gereklidir. TDD, daha iyi tasarım kararlarını teşvik eden ve test
edilebilirliği destekleyen yapılandırılmış bir yaklaşım ve geri bildirim döngüsü sağlar, ancak sonuçta beceri, yetenek
ve uzmanlığa bağlıdır.

Test-Driven Development'ın (TDD) bazı temel faydaları:

- kodun tamamı unit test'lerden geçirilmiştir
- kod, testleri karşılamak için yazılır - "muhtemelen faydalı olabilir" veya "kesinlikle bir gün gerekecektir" gibi
  gereksiz kod parçaları yazılmaz (YAGNI).
- testin geçmesi için en az miktarda kod yazmak, basit çözümlere yol açar (KISS).
- teşekkürler refactoring aşaması sayesinde kod temiz ve okunabilir olur (DRY).
- kesintilerden sonra bile kodlamaya kolayca devam etmek mümkündür; yapmanız gereken tek şey, listeden bir sonraki testi
  almak ve bir sonraki döngüye başlamaktır. TDD'nin bu döngüsel doğası, çalışmayı düzenli ve akıcı hale getirir.

Test-first geliştirme alışkanlığına alıştıkça, hata veren testler olmadan kod yazmak size rahatsızlık verebilir

## 4.4. TDD is Not Only about Unit Tests

Bu kitap genellikle unit testlere odaklanmış olsa da, Test-Driven Development (TDD) her seviyede uygulanabilen daha
geniş bir kavramdır

İki TDD döngüsünün gösterildiği şekil, TDD'nin farklı test seviyelerinde uygulanmasını temsil eder. Dış döngü,
genellikle GUI'ye veya sistem geneline karşı yürütülen end-to-end testleri temsil eder. Bu testler, geliştirme sürecini
yönlendirir ve çeşitli daha küçük işlevlerin uygulanmasını yönlendirir. Bu end-to-end testleri karşılamak için gereken
işlevleri uygulamanız gerekmektedir ve bu da bizi iç döngüye götürür. İç döngü, unit testlerin implementasyonu simgeler
ve daha küçük işlevlerin uygulanmasına ilişkindir.

![img_2.png](Chapter4Images/img_2.png)

İç içe geçmiş bu iki döngü arasında büyük bir fark vardır. Bir geliştirici iç döngünün bir gün içinde birçok döngüyü
tamamlaması mümkünken, dış döngünün bir döngüsü birkaç gün bile sürebilir. Ancak, her iki döngü de çalışma ritmi
açısından aynıdır: kırmızıdan yeşile geçmek ve ardından refactoring yaparak yeşili korumak.

TDD yöntemine dayanan bu geliştirme yaklaşımı, iç döngüdeki unit testlerden başlayarak son kullanıcı Acceptance Test'i
de içeren daha geniş bir kullanım alanına sahiptir. Acceptance Test Driven (ATDD) olarak adlandırılan bu
yaklaşım, geliştiricilerin, testçilerin ve iş paydaşlarının işbirliğini vurgular. Acceptance Test'lerin tanımlanması ve
uygulanması geliştirme sürecini yönlendirir. Burada daha fazla zaman harcamayacağız, ancak unit testlerde TDD'yi
ustalaştıktan sonra farklı test türleriyle de denemenizi öneririm.

## 4.5 Test First Example

Test-first yaklaşımını kullanarak basit bir sınıf kodlamaya başlayalım. Önceden gösterilen TDD döngüsüne adım adım
uymaya devam edeceğiz. İlk olarak başarısız bir testle başlayacağız, ardından hata mesajının yeterince bilgilendirici
olduğundan emin olduktan sonra kodu düzeltmeye geçeceğiz. Test başarılı olduğunda ise kodu iyileştirmek için refactoring
yapmaya odaklanacağız.

Teoriden uygulamaya geçmek gerçek hayatta ek bir çalışma gerektirebilir. İşte teorik örneklerde tam olarak ele alınmayan
karmaşıklıklar ve bağımlılıklar mevcut olabilir.

Yeni kavramları veya test-first geliştirme gibi metodolojileri tanıtırken örnekleri basitleştirmek yaygındır. Domain
modelinin karmaşıklığını azaltarak, sürecin kendisine daha fazla odaklanabilir ve test-first yaklaşımının uygulamada
nasıl çalıştığını anlayabilirsiniz.

## 4.5.1 The Problem

Haydi biraz futbol oynayalım, peki mi? Bir FootballTeam sınıfı oluşturacağız, böylece farklı takımları karşılaştırabilir
ve ligde birinci olan takımı görebiliriz. Her takım, kazanılan oyun sayısını kaydedecektir.

Şimdi bunun hakkında düşünelim. Sınıfın işlevselliği ve beklenen sonuçlar hakkında bir fikir edinmek için hayal edelim.
Ayrıntılara şu anda çok fazla odaklanmamıza gerek yok. İlk hamleyi yapabilmek için bazı test fikirlerine sahip olmamız
gerekiyor. Tamam, hadi düşünelim.

Öyleyse, örneğimizde iki takımımız var ve onları karşılaştırmamız gerekiyor. Comparable arayüzünü kullanabilirim gibi
görünüyor. Evet, bu, karşılaştırma için yaygın bir Java desenidir... burada herhangi bir karmaşıklığı düşünmeye gerek
yok... İyi... Şimdi, onları karşılaştırmak için her takımın kazanılan oyun sayısını hatırlaması gerekiyor ve
karşılaştırma mekanizması bunları kullanacaktır.

Bu bilgiyi tutmak için FootballTeam sınıfının bir alanı olmalı ve bu alan bir şekilde erişilebilir olmalı... Tamam... ve
en önemli şey karşılaştırma... Birkaç test yapmamız gerekiyor: Daha fazla galibiyete sahip olan takımların ilk
sıralanması gerektiğini görmeliyiz ve iki takımın aynı sayıda galibiyeti olduğunda ne olduğunu kontrol etmeliyiz.

## 4.5.2 RED - Write a Failing Test

İki takımı karşılaştırmak için her birinin kazandığı oyun sayısını hatırlaması gerekiyor. Basitlik için, kazanılan oyun
sayısını constructor parametresi olarak alan bir FootballTeam sınıfı tasarlayalım. İlk önce, bu constructor'ın
çalıştığından emin olalım.

```
    @Test
    public void constructorShouldSetGamesWon(){
        FootballTeam footballTeam = new FootballTeam(0);
        assertEquals(3,footballTeam.getGamesWon());
    }
```

IDE burada FootballTeam'im olmadigini bize bildirecektir. Ayrıca getGamesWon() methodu da yoktur.

Testin derlenmesi için gereken kodu yazarken hatırlanması gereken iki şey var:

- Tüm production kodu, testlerden farklı bir dizin yapısında tutulmalıdır. Önerim, daha önce tarif edilen yapıyı takip
  ederek src/main/java dizinine yerleştirmenizdir.
- Sadece testin derlenmesi için gereken minimum düzeyde işlem yapmanız yeterlidir. Gerekli sınıfları ve metotları
  oluşturun, ancak iş mantığıyla ilişkilendirmeyin. Unutmayın, testin şu anda başarısız olmasını istiyoruz!

```
public class FootballTeam {
    public FootballTeam(int gamesWon){}

    public int getGamesWon(){return 0;}
}
```

Bu kodu "bedava" olarak elde etmemiz oldukça ilginç. Burada sadece IDE'nin test temelinde bunu oluşturabilmesinden
bahsetmiyorum. El ile yazsak bile, zihinsel olarak zorlayıcı bir meydan okuma değildi! Testi yazmak zorlu olabilir,
ancak kodu oluşturmak çok ama çok basitti. Her zaman böyle olmasa da, genellikle böyle olur.

Test derleniyor ve sınıfımıza ait önemli bir işlevi doğrulayan bir assert içeriyor. Bu nedenle, testi çalıştırmaya
değer. Çalıştırdığımızda, aşağıdaki mesajla berbat bir şekilde başarısız oluyor:

![img_3.png](Chapter4Images/img_3.png)

Bu konuda net olalım - bu aşamada başarısız bir test iyi bir şeydir. Şimdi testin çalıştığını ve bazı önemli işlevlerin
henüz hazır olmadığını biliyoruz. Test geçtiğinde (yani yeşil ışığı gördüğümüzde) kadar bunları uygulayacağız.

Haklısınız, testlerimizde doğru bilgilendirici hata mesajlarının olması önemlidir. Kodu hemen düzeltme dürtüsüne karşı
koyarak, hata mesajıyla ilgilenmeliyiz. Bu mesaj tam olarak neyin yanlış olduğunu belirtiyor mu? Eğer cevap "hayır" ise,
özel bir hata mesajı ekleyebiliriz (bkz. Bölüm 8.4). Eğer mevcut mesajdan memnunsanız, o zaman ilerleyebiliriz.

Eğer SUT (System Under Test) kodumuzu bozarsak ve bu test başarısız olursa, assert mesajı bize tam olarak neyin yanlış
olduğunu söyleyecek ve düzeltmek kolay olacaktır. Tamam, o zaman TDD'nin bir sonraki aşamasına geçme zamanı - tabii ki
kodu düzelterek şimdi testi geçirelim.

## 4.5.3 GREEN - Fix the code

Bu sefer düzeltme oldukça basit: constructor'a parametre olarak geçirilen değeri bir internal variable'a kaydetmek
yeterli. Aşağıda düzeltilmiş FootballTeam sınıfı gösterilmektedir:

```
public class FootballTeam {
    private final int gamesWon;

    public FootballTeam(int gamesWon){
        this.gamesWon = gamesWon;
    }

    public int getGamesWon() {
        return gamesWon;
    }
}
```

Test'i de görelim;

```
    @Test
    public void constructorShouldSetGamesWon() {
        FootballTeam footballTeam = new FootballTeam(3);
        assertEquals(3, footballTeam.getGamesWon());
    }
```

Test şimdi geçmelidir. Ancak, henüz kutlamaya başlamayın! Bu, kodu geliştirmek, yeniden düzenlemek ve yorum eklemek için
zaman olduğu anlamına gelir. Yaptığınız değişiklikler ne kadar küçük olursa olsun, herhangi bir yanlışlıkla
bozulmadığından emin olmak için testi yeniden çalıştırın.

## 4.5.4 REFACTOR - Even If Only a Little Bit

FootballTeam sınıfı gibi basit bir durumda pek fazla refaktör yapılacak bir şey olmayabilir. Ancak, testin refaktörünü
unutmamamız önemlidir! En azından, sihirli sayı olan 3'ten kurtulmak için bir THREE_GAMES_WON değişkeni
tanımlayabiliriz.

```
    private static final int THREE_GAMES_WON = 3;
    @Test
    public void constructorShouldSetGamesWon() {
        FootballTeam footballTeam = new FootballTeam(THREE_GAMES_WON);
        assertEquals(THREE_GAMES_WON, footballTeam.getGamesWon());
    }
```

## 4.5.5 First Cycle Finished

İlk TDD döngüsünü tamamladık. Her şey, başarısız olan testle başladı. Ardından hata mesajı üzerinde çalıştık ve kodu
düzelttik. Test başarılı olduğunda, kodu refaktör ettik (ve testi tekrar çalıştırdık), böylelikle döngüyü tamamladık.
Buradaki temel zorluk, kendinizi fazla implementasyondan alıkoymaktır. FootballTeam sınıfının nasıl implemente edilmesi
gerektiği konusunda baştan çok net bir görüşünüz olabilir. Kodun sadece basit olduğunu düşünerek ilerlemek ve sadece
yazmak isteyebilirsiniz. Testler için yazmaya değmeyeceğini düşündüğünüz bir "çocuk oyuncağı" olduğu hissi normaldir.
Sonuçta, şimdiye kadar testler olmadan kod yazıyorsunuz ve oldukça iyi çalışıyor, değil mi? Bununla birlikte, ilk başta
biraz garip hissettirse bile, test-first modelini takip etmenizi teşvik etmek istiyorum. Onu tanıyın, birkaç saat
boyunca kullanın ve ardından beğenip beğenmediğinize karar verin. İlk izlenimler yanıltıcı olabilir.

### The Simplest Thing that Works’ Revisited

başarısız testi karşılamak için ne yaptığımıza bir daha bakalım. Gerçekten testleri geçirmek için "en basit şeyi" yaptık
mı? Aslında, yapılanın gerçekten şu olması gerektiği tartışılabilir:

```
public class FootballTeam {
    public FootballTeam(int gamesWon) {
    }

    public int getGamesWon() {
        return 3;
    }
}
```

Bu kod parçacığı gerçekten başarısız testi karşılıyor. Buna bir saçmalık da diyebilirsiniz, ancak bu saçmalıkta bir
bilgelik vardır. Yaptığı şey, testinizin belirli bir işlevselliği kapsamak için yeterince iyi olmadığını
kanıtlamaktır. "Bakın, testiniz o kadar acınası ki, bu kadar aptalca bir şey yaparak testi geçirebilirim. Daha çok çaba
harcamalısınız." der. Ve gerçekten daha fazla test yazmanızı sağlar.

Bir sonraki test örneğin, kazanılan oyun sayısının farklı bir sayıyla - mesela 3 yerine 5 - geçip geçmediğini
doğrulamaktır. Bu tür bir test açıkça başarısız olacaktır ve ardından kodu daha akıllı hale getirmeniz gerekecektir,
böylece her iki durumu da destekler.

Bu şekilde kendinizi kodla kandırma yöntemini uygulamak isteyip istemeyeceğinize siz karar verin. Bunu denemenizi
tavsiye ederim, çünkü bu tür bir egzersiz, gerçekten iyi testlerin neleri içerdiğini anlama becerinizi geliştirmenize
yardımcı olacaktır.

## 4.5.6 More Test Cases

Constructorgörünüyor ki düzgün çalışıyor, en azından bu bir değer için! Daha fazla değer için testler eklemeliyiz
ve ayrıca, constructor'a uygun olmayan bir kazanma sayısı geçirildiğinde bir exception fırlatmasını sağlamalıyız (Bkz.
Bölüm 6.1). Her iki durumda da parameterized testlerden (Bölüm 3.6'da tartışılan) yararlanacağız.

Ilk olarak FootballTeam class'ini eski haline geri alalim;

```
public class FootballTeam {
    private final int gamesWon;
    public FootballTeam(int gamesWon) {
        this.gamesWon = gamesWon;
    }

    public int getGamesWon() {
        return gamesWon;
    }
}
```

Testimizi parameterized hale getirdik. @ValueSource ile direk degerleri methoda inject ediyorum

```
    @ParameterizedTest
    @ValueSource(ints = {0, 1, 2})
    public void constructorShouldSetGamesWon(int values) {
        FootballTeam footballTeam = new FootballTeam(values);
        assertEquals(values, footballTeam.getGamesWon());
    }
```

Exception alabilmek için de bir test yazıyorum;

```
    @ParameterizedTest
    @ValueSource(ints = {-10,-1})
    public void constructorShouldThrowExceptionForIllegalNumberOfGamesWon(int values){
        new FootballTeam(values);
    }
```

### But is It Comparable?

Constructor düzgün çalışıyor. Şimdi ana soruna geçebiliriz: yani futbol takımlarını karşılaştırmaya. İlk olarak,
Comparable arayüzünü kullanacağımızı kararlaştırdık. Bu, sadece bu sınıfın uygulamasını etkilemekle kalmayacak, aynı
zamanda API'sini ve beklenen davranışını da etkileyecek önemli bir karardır. Eğer FootballTeam karşılaştırılabilirse,
client, birkaç takımı bir koleksiyona yerleştirdikten sonra onları sıralamak için Collections.sort() yöntemini
kullanabileceğini bekleyebilir. Eğer öyleyse, bu davranış için bir test olmalıdır. Aklıma gelen tek test şu şekildedir:

```
    @Test
    void shouldBePossibleToCompareTeams(){
        FootballTeam footballTeam = new FootballTeam(ANY_NUMBER);
        assertTrue(footballTeam instanceof Comparable<?>,"Football team should implemenent Comparable");
    }
```

Bu oldukça yaygın olmayan bir testtir: genellikle instanceof operatörünü kullanan testler yazmam. Ancak, bu durumda
meşru görünüyor çünkü FootballTeam sınıfının client'ları tarafından gereken önemli bir özelliği kapsar.

Lütfen constructor'a geçirilen statik değerin adını dikkate alın: ANY_NUMBER. Bu, argümanın önemli olmadığını gösterir.
Argümanın gerçek değeri testi herhangi bir şekilde etkilemez.

Bu test başarısız olacaktır.

```
public class FootballTeam implements Comparable<FootballTeam>{
    private final int gamesWon;
    public FootballTeam(int gamesWon) {
        this.gamesWon = gamesWon;
    }

    public int getGamesWon() {
        return gamesWon;
    }

    @Override
    public int compareTo(FootballTeam team) {
        return 0;
    }
}
```

Comparable Interface'ini implemente ettikten sonra kod yukarıda ki sekilde gozukecektir. Testi tekrar calistirdigimizda
gectigini gorecegiz.

Unutmayın, her zaman sahip olduğunuz tüm birim testlerini çalıştırın. Eğer gerçekten "birim" testler ise hızlı
çalışacaklardır - sık sık çalıştırmakta sorun yok, değil mi?

### Comparison Tests

Şimdi ilk karşılaştırma testini yazalım. Fikir basittir: farklı kazanma sayılarına sahip iki takımı alın ve onları
karşılaştırın.

```
    @Test
    void teamsWithMoreMatchesWonShouldBeGreater(){
        FootballTeam team_2 = new FootballTeam(2);
        FootballTeam team_3 = new FootballTeam(3);
        assertTrue(team_3.compareTo(team_2) > 0);
    }
```

Çalıştırıldıktan sonra test başarısız olacaktır.

Production kodunu düzeltelim, ancak testin gerektirdiğinden fazlasını uygulamayalım! Aşağıda gösterilen compareTo()
yöntemi, testin başarılı olmasını sağlayacak minimal (makul) bir implementasyondur:

```
    @Override
    public int compareTo(FootballTeam team) {
        if (gamesWon > team.gamesWon) {
            return 1;
        }
        return 0;
    }
```

Testim geçti.

```
    @Test
    void teamsWithLessMatchesWonShouldBeLesser(){
        FootballTeam team_2 = new FootballTeam(2);
        FootballTeam team_3 = new FootballTeam(3);
        assertTrue(team_2.compareTo(team_3)<0);
    }
```

Yukarıda ki testi Çalıştırın, başarısız olduğunu görün, ardından FootballTeam sınıfına değişiklikler yapın ve testlerin
başarılı olmasını sağlayın. Bu testin geçmesini sağlayan implementasyon yine de basittir:

```
    @Override
    public int compareTo(FootballTeam otherTeam) {
        if (gamesWon > otherTeam.gamesWon) {
            return 1;
        } else if (gamesWon < otherTeam.gamesWon){
            return -1;
        }
        return 0;
    }
```

Daha anlasilabilir olmasi icin argument'in ismini otherTeam olarak degistiriyoruz. Simdi iki takiminda esit mac
kazanmasi uzerine testlerimizi yazalim

```
    @Test
    void teamsWithSameNumberOfMatchesWonShouldBeEqual(){
        FootballTeam team_2 = new FootballTeam(2);
        FootballTeam team_3 = new FootballTeam(2);
        assertTrue(team_2.compareTo(team_3)==0);
    }
```

Evet, bu test anında geçer çünkü mevcut uygulamamız zaten eşitlik durumlarında 0 döndürüyor. Peki şimdi ne yapmalıyız?
Kesinlikle TDD ritminde bir adım atladık. Bu eşitlik testinin başarılı olduğunu görmemişiz, bu yüzden neden geçtiğini
bilmiyoruz. Bu, FootballTeam sınıfının beklenen davranışı gerçekten uyguladığı için mi, yoksa testimiz kötü yazılmış ve
her zaman geçen bir test mi?

Şimdilik testin kusursuz olduğunu ve FootballTeam'in uygulamasının da doğru olduğunu varsayalım. TDD'nin bazı köşe
noktalarını incelediğimizde bu konuyu tekrar ele alacağız (bkz. Bölüm 4.9).

Şimdi güvenlik ağına sahip olduğumuza göre, test edilen methodu gerçekten yeniden düzenleyebiliriz. Konuyu dikkatlice
düşündükten sonra, daha basit bir uygulamaya ulaştık:

```
    @Override
    public int compareTo(FootballTeam otherTeam) {
        return gamesWon - otherTeam.gamesWon;
    }
```

Comparable arayuzu şu şekilde çalışıyor;

x = 5 olarak düşünelim

x.compareTo(7) -> -1 degerini donecektir
x.compareTo(5) -> 0 degerini donecektir
x.compareTo(3) -> 1 degerini donecektir

Bu tekrar çalıştırılan testler, uygulamanın şu ana kadar belirlenen gereksinimleri (testler şeklinde ifade edilen)
karşıladığını gösteriyor.

## 4.6. Conclusions and Comments

TDD'nin gerçek değeri, sadece testlerin uygulanmasıyla ilgili teknik yönü değildir. TDD'nin sağladığı gerçek değer,
zihniyeti ve prensipleri içermesidir. TDD'yi uygulayarak, geliştiriciler işlerini küçük, yönetilebilir adımlara bölmeyi
öğrenirler, hızlı geri bildirim döngüleri aracılığıyla anlayış kazanırlar ve deneme yanılma yöntemini kullanarak
projenin evrimini yönlendiren değerli bilgiler elde ederler.

Şimdi sadece test-first yaklaşımını kullanarak bir sınıf uyguladık. Çok küçük adımlarla ilerledik. Detaylara dikkat
ettik. Kodu iyileştirdik. Hatta doğrulama mesajlarıyla ilgilendik. Ve tüm bu süre boyunca işlevsellik bozulmadığından
emin olmak için testleri tekrar tekrar çalıştırdık. Ve tüm bu süre boyunca ilerliyorduk... adım adım... her zaman
ileriye doğru.

Şimdi sana birçok soru sormak istiyorum. Nasıl buldun? RED-GREEN-REFACTOR ritmini beğendin mi? Kod yazmanıza
yardımcı oldu mu, yoksa düşünme ve kodlama tarzınızı engelledi mi? Oluşturulan kodun gerçekten çalıştığından emin misin?
Test yapmadan daha iyi veya daha hızlı yapabilir miydin, yoksa testleri uygulamadan sonra mı yazmayı tercih ederdin?
Hatta sıradan durumlar için bile test yazarak zaman kaybettiğini hissettin mi? Kodu yeniden düzenlemek konusunda kendine
güven hissettin mi?

Bu kadar basit bir örnek, kullanışlılığını göstermek için yeterli olmayabilir. Sorun o kadar basitti ki, baştan beri
zaten aklınızda bir uygulama hazır olurdu ve testleri önce yazmanın çok faydası olmazdı. Ancak, "vahşi doğada"
test-first kullanmaya başladığınızda farklı olacak. Uygulamasını nasıl yapacağınızı hiçbir fikriniz olmayan özellikler
için testler yazacaksınız. O zaman testlerin bir çözüm bulmanıza nasıl yardımcı olduğunu göreceksiniz.

Kişisel olarak, test-first tekniğine çok şey borçlu hissediyorum ve senin de hoşuna gideceğini umarım. Ancak, test-first
gerçekten sana uygun mu, karar vermek sana bağlı. Bu kitabın geri kalanında sadece test-first tekniğine güveneceğim.
Eğer beğenirsen harika! Beğenmezsen, lütfen yine de dayan!

TDD'nin faydalarını Section 4.3'te listelenen şekliyle incelemek mantıklı olur. Bunları dikkate aldınız mı? Kodunuz
testlerle %100 coverage edilmiş mı? Herhangi "olası önemli" özellikler uygulanmış mı? Basit mi? Temiz ve okunabilir mi?
Bir kesintiden sonra çalışmanıza devam ettiğinizde farkı fark ettiniz mi? Eğer öyleyse, belki de TDD çabaya değerdir!
Test-first yöntemiyle yazdığımız testlerle ilgili ek bir yorum yapmak istiyorum. Muhtemelen fark etmişsinizdir, her
test, FootballTeam sınıfının kendi nesnesini oluşturdu. Genellikle iyi bir fikir, her testin otomatik olarak yeni bir
nesne alacağı (temiz, iyi bilinen bir durumda) bir set-up'da SUT'u oluşturmaktır. Ancak, bu durumda mümkün değildi,
çünkü her test yöntemi farklı şekilde oluşturulmuş bir futbol takımı gerektiriyordu.

## 4.7 How to Start Coding TDD

Şimdi TDD yaklaşımının harikalarını okudunuz! Ve beğendiniz! Biraz garip görünebilir, ama aynı zamanda mantıklı. Bazı
büyük programcıların koddan önce test yazdığını duydunuz. Üstün kaliteleriyle tanınan bazı projelerin test-first
yazıldığını duydunuz. TDD teknikleri hakkında biraz zaman harcadınız, ritmi biliyorsunuz ve fikir hakkında giderek daha
da heyecanlanıyorsunuz Sonunda... kararınızı verdiniz: denemek istiyorsunuz... takım arkadaşlarınızın bir adım önünde
olmak istiyorsunuz. Şimdi hemen denemek için verilen sonraki görevi sabırsızlıkla bekliyorsunuz!

Veritabanları, Spring context'i, statik sınıflar, ORM araçları, JNDI çağrıları, web servisleri, global değişkenler,deep
inheritance hiyerarşisi ve console output hepsi birden üzerinize çullanır. Hiçbir şey test-first öğreticilerinde olduğu
gibi görünmez; okuduğunuz iyi tavsiyelerin hiçbiri yardımcı olmaz gibi görünür. Sadece ilerlemek tamamen imkansızdır!

Önerim, tam olarak TDD'nin teşvik ettiği şeydir. Küçük adımlarla ilerleyin. En basit şeylerle başlayın. Test-first
yaklaşımını kullanarak kolay görevleri uygulayın. Her TDD başarısını değerlendirin. Her TDD başarısızlığından öğrenin.
Eğer test-first yaklaşımını kullanarak çözemediğiniz bir şey bulursanız, çok fazla endişelenmeyin. Birkaç hafta(saatler,
günler, aylar?) sonra böyle görevlerle başa çıkabilir hale geleceksiniz. Deneyiminizi küçük adımlarla biriktirin. Gün
gün... Bu şekilde işe yarayacak - göreceksiniz!

Her gün daha da kolaylaşacak. İlk olarak, test-first kodlamada giderek daha iyi oluyorsunuz. "Ritmi hissediyorsunuz",
elinizdeki göreve odaklanıyor, sadece hipotetik olan olası ve faydalı konular üzerinde düşünmeyi bırakıyorsunuz, vb.
İkinci olarak, yapmaktan vazgeçtiğiniz şeyleri bırakıyor ve test etme düşüncesiyle düşünmeye başlıyorsunuz. Üçüncü
olarak, tasarımınız şimdi çok daha test edilebilir ve kodunuzun sonraki bölümleri daha kolay test edilebilir hale
geliyor.

## 4.8. When not To Use Test-First?

TDD'nin büyüsü işliyor ve bir kez ona hakim olduğunuzda, başka bir şekilde kod yazmaktan çekinebilirsiniz. Ancak, bu
yaklaşımın en iyi seçenek olmadığı bazı durumlar da vardır. Bu bölümde bunlardan bahsediyoruz.

Önceki bölümde açıklandığı gibi, sadece "nasıl yüzüleceğine" dair bir rehber okuduktan sonra derin uçta atlamak
gerçekten tavsiye edilmez. :) Programlama becerilerinize olan güvenim tamdır (ve diğerleri), ancak gerçek hayatta
programlama görevlerini test-first yaklaşımını kullanarak uygulamaya henüz hazır olmadığınız bir durumla
karşılaşabilirsiniz. Basit bir şeyle başlayın, deneyim kazanın ve ardından ilerleyin.

Bu, ilk test-first karşıtı durumun, deneyim, beceri ve güven eksikliğiyle tüm olası sorunlara saldırma durumu olduğunu
gösterir. Bunu yapmayın.

Ve hatta deneyiminiz olduğunda, hala TDD ile takılabileceğiniz durumlar olacaktır.

Testlerin yazılması genellikle kullanılan teknolojilerin iyi anlaşılmasını ve sorun alanının bilgisini gerektirir. Eğer
bunlara sahip değilseniz, gerekli bilgiyi edinmek için zamanınızın olmadığını ve gerçek kod yazmak yerine (bilinmeyen)
test framework'u ile mücadele edeceğinizi biliyorsanız, muhtemelen kod-first yaklaşımla başlamak daha iyidir. Her
zamanki gibi sağduyulu olun ve hatalardan ders çıkarın.

Bir başka TDD engeli, mevcut bir kod tabanına uygulandığında ortaya çıkar. Ciddi bir refaktörasyon yapmadan TDD'yi
uygulayamayabilirsiniz, böylece testlerin güvenlik ağı olmadan gerçek anlamda refaktörasyon yapamazsınız... Ancak o
zaman cebinize bazı hileler saklamış olursunuz: miras kodunuzun tuhaflıklarını ele almanıza izin veren araçlar. Ne yazık
ki, bazen tüm kodu baştan yazmayı tercih etsek de, bunları kullanmanız gerekecek.

Ancak, tüm bunlar daha çok integration testlerine uygulanır. Unit testlerin test-first kodlamayla yapılması konusundaki
deneyimim, en azından bazı state testlerinin bu şekilde yapılabilir olduğudur. Nesneler arasındaki etkileşimlerin test
edilmesi bazen daha zor olabilir (miras kod yapısı nedeniyle kolayca test edilemeyen kod yapısı).

## 4.9. Should I Follow It Blindly?

TDD, çok basit kurallara dayanan bir yöntemdir: önce bir test yazın, ardından onun başarısız olduğunu görün, testi
başarılı hale getirin, kodu düzenleyin, hala başarılı olduğunu görün ve sonra... döngüyü tekrarlayın. Dahası, bu
kuralların geniş uygulanabilirlikleri sayesinde güzellikleri artar - neredeyse herhangi bir programlama sorununu bu
yöntemle ele alabilirsiniz. Bununla birlikte, bu kurallar kutsal metinler değildir. Bazı durumlarda bazı adımları
atlamak mantıklı olabilir. Bu bölümde, böyle durumların bazı örneklerini veriyorum.

## 4.9.1. Write Good Assertion Messages from the Beginning

JUnit ile biraz deneyim kazandıktan sonra, varsayılan hata mesajının yeterli olduğunu ve düzeltilmesi gerektiğini
bilirsiniz. Testin başarısız olmasını beklemek yerine, deneyimim bana doğruyu söylüyorsa ve gerçekten mesajı düzeltmem
gerekiyorsa, beklemekten herhangi bir anlam görmüyorum. Bu nedenle, genellikle testin başarısız olmasını beklemeksizin
iyi bir hata mesajı yazarım.

## 4.9.2. If the Test Passes "By Default"

IDE'ler harikadır. Ancak, TDD alışkanlıklarınıza müdahale edebilirler. Bazen IDE, testin başarısız olduğunu görmemenize
izin vermeyebilir ve gerekli işlevselliği otomatik olarak oluşturabilir. Bu durumu göstermek için yeterli olan,bir testi
yazmaya başladığınızı varsayalım. Bu, en iyi test olmayabilir, ancak konuyu anlatmak için yeterlidir. Yazarken, IDE'niz
muhtemelen testin derlenebilmesi için gereken methodların boş bir implementasyonunu otomatik olarak oluşturmanızı
önerecektir.

```
    @Test
    void shouldClientSetAge(){
        client.setAge(20);
        assertEquals(20,client.getAge());
    }
```

- IDE size default constructor ile bir Client class'i yaratmayı onerecek
- setAge() ve getAge() metodlarına gelindiğinde, IntelliJ IDEA iki seçenek sunacaktır: bir metod oluşturma veya
  getter/setter oluşturma.

Eğer ikinci seçeneği seçerseniz otomatik olarak getter/setter'ları oluşturacaktır. Client sınıfı içerisinde;

```
    private int age;

    public int getAge(){
        return age;
    }

    public void setAge(int age){
        this.age = age;
    }
```

Şimdi testi çalıştırırsanız, başarılı olduğunu göreceksiniz. Hata... görünüşe göre TDD'nin ilk adımını atlamışız! Bu kod
sadece derlenmekle kalmayacak, aynı zamanda ilk testi geçecek, bu yüzden başarısız olan testi görmek için hiçbir
şansınız olmayacak!

Bu tür basit kod durumlarında, bir dakika için TDD alışkanlıklarınızı bırakmanızı ve IDE'nin doğru bir şekilde işleri
yapabileceğine inanmanızı öneririm. Ancak yapmamız gereken bir şey var. Test başarısız olursa, bunun
gerçekleştirildiğinden tamamen emin olacağız. Ancak gördüğümüz tek şey yeşil renkse, testin gerçekten çalıştığından emin
olmamız gerekiyor. Daha büyük test takımlarında, çalıştırılmayan bir testin gözden kaçırılması kolay olabilir. Bu yüzden
en azından testinizin gerçekten çalıştığından emin olun.

Maalesef, daha ince detayların olduğu bazı durumlar vardır ve ekstra dikkat gerektirir. FootballTeam sınıfı için (Bölüm
4.5.6'da görebilirsiniz) oluşturduğumuz compareTo() metodunun testlerini hatırladınız mı? IDE'nin compareTo() metoduna
yerleştirdiği varsayılan dönüş değeri 0 idi. Bu durumda, eğer iki futbol takımını kazanma sayıları açısından
karşılaştırırsak, böyle bir test anında geçerli olacaktır. compareTo() metodunun kodu, önceki olarak tartışılan
getter/setter'lardan çok daha karmaşıktır. Bunun yerine şunu öneririm: Kodu bozarak testin başarısız olduğunu görün.

Bizim durumumuzda, bunun anlamı 0'ı başka bir değerle değiştirmektir, örneğin:

```
    public int compareTo(FootballTeam otherTeam){
        if (gamesWon > otherTeam.gamesWon){
            return 1;
        }else if (gamesWon < otherTeam.gamesWon){
            return -1;
        }
        return 123123;
    }
```

Şimdi testi yeniden çalıştırın ve başarısız olduğunu görün. Güzel. compareTo() metodunun değişikliğini geri alın (yani
tekrar 0 döndürsün). Testi yeniden çalıştırarak başarılı olduğunu görün. Harika. Bu, testinizin gerçekten bu davranışı
doğruladığını gösterir. Ve tam olarak öğrenmek istediğimiz buydu.

## 4.10 Exercises

Yeni bir geliştirme yöntemiyle tanıştınız, bu yöntem gerçek implementasyon öncesinde testlerin yazılmasını teşvik
ediyor. Şimdi öğrendiklerinizi uygulama zamanı geldi. Bu bölümdeki egzersizler, görevlerin karmaşıklığı açısından çok
zor değil. Önemli olan, TDD ritmine odaklanmak ve algoritma hatalarına takılmadan ilerlemektir. Ödevinizi yaparken,
TDD'yi dikkatli bir şekilde takip etmeye ve her adımı gerçekten küçük parçalara bölmeye özen gösterin. İyi şanslar!

Bu egzersizleri yaparken, "başarısız bir testiniz olmadan asla kod yazmayın" kuralına uymayı unutmayın!

## 4.10.1 Password Validator

Bir hesap oluştururken, genellikle parolanın bazı güç gereksinimlerini karşılaması gerekir. X karakter uzunluğunda
olmalı, en az Y basamaklı olmalı, alt çizgi, karma ve küçük ve büyük harflerin bir karışımını vb. Parolaları doğrulamak
için kullanacağınız kurallar (gereksinimler) size bağlıdır.

```
    @ParameterizedTest
    @ValueSource(strings = {"Test123@#", "Deneme2395$#£"})
    void validateValidPassword(String password) {
        assertTrue(PasswordValidator.validate(password));
    }
```

Testi yazıyorum ve fail alarak başlıyorum. Password'u match etmek için Büyük karakter ile başlamasına içerisinde
rakamlar olmasına ve özel karakterlere ihtiyacım olacağını belirtiyorum

Ilk olarak PasswordValidator class'ini create ediyorum, ve testi tekrar çalıştırıyorum. Gene fail aldım. Çünkü validate
methodum yok. Fail aldıktan sonra validate methodunu da class içerisine static olarak tanıtıyorum. Testi tekrar
çalıştırıyorum ve gene fail alıyorum. Ardından validate methodunda fake implementasyon yaparak true döndürüyorum ve
testi geçiriyorum. Şimdi refactor zamanı.

Bir regex yazabilmek için aşağıdaki pattern'i takip etmem yeterli olacak;

```
"^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$";
```

Regex rakamları içereceğini, küçük harf barındıracağını, büyük harf barındıracağını, semboller barındıracağını ve son
olarak da 8 digit olacagini belirtiyor

```
public class PasswordValidator {
    private static final String PASSWORD_REGEX = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$";

    public static final Pattern PASSWORD_PATTERN = Pattern.compile(PASSWORD_REGEX);

    public static boolean validate(String password) {
        return PASSWORD_PATTERN.matcher(password).matches();
    }
}
```

PasswordValidator kısmında REGEX'i kullanarak gelen password'un gereken koşulları karşıladığını doğrulamam gerekiyor

```
    @ParameterizedTest(name = "#{index} - Run test with password = {0}")
    @ValueSource(strings = {"Test123@#", "Test121233@#&&"})
    void validateValidPassword(String password) {
        assertTrue(PasswordValidator.validate(password));
    }
```

Test class'imda 2 değeri geçerek password'un geçerli olduğunun testini yapıyorum. Birde başarısız olanı yazmam gerekiyor

```
    @ParameterizedTest(name = "#{index} - Run test with password = {0}")
    @ValueSource(strings = {"test1234!'^", "test5678%"})
    void validateInvalidPassword(String password) {
        assertFalse(PasswordValidator.validate(password));
    }
```

## 4.10.2 Regex

Bu örnek, size bir dize verildiğinde, bu diziden alınmış, en az 3 basamağa sahip tüm sayıları içeren bir liste döndüren
bir method yazmanızı gerektiriyor. Aşağıda, çeşitli girdi dizeleri için beklenen sonuçların bazı örnekleri verilmiştir

![img_4.png](Chapter4Images/img_4.png)

```
    private static Stream<Arguments> provideStringArguments() {
        return Stream.of(
                Arguments.of("abc 12", new int[]{}),
                Arguments.of("cdefg 345 12bb12b3", new int[]{345}),
                Arguments.of("asd443 abr123ss 678tt", new int[]{443, 123, 678})
        );
    }
```

Testimiz de kullanılmak üzere bir dataset hazırlıyorum. Ardından test'imizi yazıyorum;

```
    @ParameterizedTest
    @MethodSource("provideStringArguments")
    void extractNumbersWithThreeOrMoreDigitsFromString(String inputString, int[] expectedNumbers) {
        assertArrayEquals(expectedNumbers, RegexUtil.extractNumbersWithThreeOrMoreDigitsFromString(inputString));
    }
```

Testimizi yazdıktan sonra hemen çalıştırıyorum ve fail'i alıyorum. RegexUtil adında bir class'ım yok.

Boş bir şekilde RegexUtil class'ini create edip testimizi tekrar çalıştırıyorum. Faili alıyorum.
extractNumbersWithThreeOrMoreDigitsFromString isimli methodumuz yok. Methodu da void donduren ve static bir method
haline getirip testi tekrar calistiyorum. Bu sefer bana diyor ki arrayEquals bir int[] ile karşılatırmaya çalışıyorsun
ama void donduruyorsun

```
public class RegexUtil {
    public static int[] extractNumbersWithThreeOrMoreDigitsFromString(String inputString){
        return new int[]{0};
    }
}
```

Fake olarak 0 dönüyorum ve test'im tekrar fail. Şimdi refactor edeceğim;

```
public class RegexUtil {

    private static final String REGEX = "\\d{3,}";
    public static final Pattern PATTERN = Pattern.compile(REGEX);

    public static int[] extractNumbersWithThreeOrMoreDigitsFromString(String inputString) {
        ArrayList<Integer> numbersArrayList = new ArrayList<>();
        Matcher matcher = PATTERN.matcher(inputString);
        while (matcher.find()) {
            numbersArrayList.add(Integer.parseInt(matcher.group(0)));
        }
        return numbersArrayList.stream().mapToInt(i -> i).toArray();
    }
}
```

Testim artık çalışıyor

## 4.10.3 Booking System

Göreviniz, (çok) basitleştirilmiş bir rezervasyon sistemi yazmaktır. Aslında, bunu tek bir sınıf olarak yazabilirsiniz
ve bu sınıf aşağıdaki özelliklere sahip olmalıdır:

- rezerve edilen saatlerin bir listesini döndürmek,
- belirli bir saatin çifte rezerve edilmesine izin vermemek,
- geçersiz değerlerle (giriş parametreleri) mantıklı bir şekilde ilgilenin.

Kısıtlamalar açısından (TDD uygulamak için daha uygun hale getirmek için), sistem şunları yapmalıdır:

- Sistemde sadece bir kaynak bulunmalıdır ve bu kaynak rezervasyon yapılabilir olmalıdır. Örneğin, bir sınıf, çim biçme
  makinesi, restoran masası veya sizin için anlamlı olan başka bir şey olabilir. Bu kaynağın rezervasyon durumunu takip
  etmek ve kullanıcıların bu kaynağı rezerve etmesine olanak tanımak, sistemimizin temel özelliklerinden biri olacaktır.
- Sistemde gün kavramı bulunmamalı veya başka bir deyişle, tüm rezervasyonların bugün için olduğu varsayılmalıdır.
- Sistem sadece düzenli tam saatlik rezervasyonlara izin vermelidir (örneğin, 4:30'tan 5:30'a rezervasyon yapılmasına
  izin vermemelidir).
- Rezervasyonla ilgili ek bilgileri hatırlamak (kimin rezervasyon yaptığı, ne zaman vb.) gerekli değildir.

Ilk olarak test'imi yazıyorum;

```
    @Test
    void shouldCreateABookingSystem() {
        BookingSystem bookingSystem = new BookingSystem();
        Object expectedModel = new BookingSystem();
        assertNotEquals(expectedModel,bookingSystem);
    }
```

Fail'i alıyorum ve hemen BookingSystem adlı class'imi create ediyorum

```
public class BookingSystem {
}
```

Testim yesile dondu. Refactor islemi goremiyorum ve hemen bir sonraki test'e geciyorum

Hemen ilk olarak booked saatlerinin listesini donen bir methoda ihtiyacım var. Bunun için de bir dataset'e ihtiyacım var

```
    static Set<LocalTime> setOfValidHours;
    private static BookingSystem bookingSystem;
    private static Stream<LocalTime> validTimesForBooking() {
        return Stream.of(
                LocalTime.of(16, 0),
                LocalTime.of(17, 0),
                LocalTime.of(18, 0)
        );
    }

    @BeforeAll
    public static void setUp() {
        bookingSystem = new BookingSystem();
        setOfValidHours = validTimesForBooking().collect(Collectors.toSet());
        setOfValidHours.forEach(item-> bookingSystem.bookHour(item));
    }
```

Dataset içerisinde saatleri create ettikten sonra bookingSystem class'i içerisine bir bookHour methodu yerleştirerek
valid olan saatleri LocalTime cinsinde gönderiyorum.

Testimi yazıyorum;

```
    @Test
    void shouldReturnListOfBookedHours(){
        assertEquals(setOfValidHours,bookingSystem.getSetOfHours());
    }
```

Fail'i aldim. Cunku getSetOfHours adında bir methodum yok

```
    public Set<LocalTime> getSetOfHours() {
        return Set.of(
                LocalTime.of(16, 0),
                LocalTime.of(17, 0),
                LocalTime.of(18, 0)
        );
    }
```

Fake bir implementasyon ile test'i GREEN'e çekiyorum. Şimdi refactor zamanı; BookingSystem'de bazı düzeltmeler yaptım

```
public class BookingSystem {

    private Set<LocalTime> setOfHours = new HashSet<>();
 
    public Set<LocalTime> getSetOfHours() {
        return setOfHours;
    }

    public void bookHour(LocalTime localTime) {

    }
}
```

Fakat testim tekrar fail'e döndü; BookingSystem'da logic'ler ekliyorum;

```
public class BookingSystem {

    private Set<LocalTime> setOfHours = new HashSet<>();
    public Set<LocalTime> getSetOfHours() {
        return setOfHours;
    }

    public void bookHour(LocalTime localTime) {
        if (setOfHours.contains(localTime)){
            throw new IllegalArgumentException("Booking is not allowed for: " + localTime);
        }
        if (localTime.getMinute()!=0){
            throw new IllegalArgumentException("Booking is allowed only for whole hour.");
        }
        setOfHours.add(localTime);
    }
}
```

Eğer gelen localtime set'imin içerisinde zaten mevcutsa exception fırlatıyorum. Gelen saatin minute'u 0 değilse gene
exception fırlatıyorum. Çünkü sadece saat başlarında kiralama mümkün. Eğer herşey tamamsa set'ime localtime'i ekliyorum

Test'im şimdi yeşil.

Booking saatini iki aynı saate yapamam

```
    @Test
    void shouldNotAllowBookHourTwice(){
        bookingSystem.bookHour(setOfValidHours.iterator().next());
    }
```

iterator.next() burada Set'in ilk LocalTime'ini dondurecek Set'imiz de zaten var olan bir saat oldugu için exception
alacağız

Yeni testim 1 tane daha saat ekleyebiliyor muyum?

```
    private final LocalTime LT_19_00 = LocalTime.of(19,0);
    
    @Test
    void bookingShouldAddHourToList(){
        bookingSystem.bookHour(LT_19_00);
        assertEquals(4,bookingSystem.getSetOfHours().size());
        assertTrue(bookingSystem.getSetOfHours().contains(LT_19_00));
    }
```

Testim başarılı bir şekilde geçti.

Son testim de saat başları hariç bir booking saati ekleyebiliyor muyum?

```
    private final LocalTime LT_18_30 = LocalTime.of(18,30);
    
    @Test
    void bookingShouldBeAllowedOnlyForWholeHour(){
        bookingSystem.bookHour(LT_18_30);
    }
```

Son testim'de de exception'i aldım ve tüm testlerim tamamlandı





