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
Gördüğünüz gibi, bu resimde bir istemci de bulunuyor ve onun hareketi (istemi) OO sistemimizde büyük bir etkinlik
başlatmış durumda. Neden bu kadar çok nesne ve mesaj var? Neden zeki bir nesne istemcinin isteğiyle başa çıkamaz? İşte,
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

DOC veya Dependen on Component olarak adlandırılan şey, SUT'in görevlerini yerine getirmek için gereken herhangi bir
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