# Chapter 11 Test Quality

Geliştiricilerin kaliteye olan takıntıları gayet doğaldır ve bunun iyi nedenleri vardır. İlk olarak, kalite kod yazma
mükemmelliğinin bir kanıtıdır ve hepiniz harika kodlayıcılar olarak tanınmak istersiniz, değil mi? İkinci olarak,
yazılımın kötü kalitesi nedeniyle meydana gelen bazı spektaküler felaketler hakkında hepimiz duymuşuzdur. Yazdığınız
yazılımın bir insanı ay'a göndermek amacıyla olmasa bile, yine de client'inizi hatalarla hayal kırıklığına uğratmak
istemezsiniz. Üçüncü olarak, kötü kodun bir bumerang gibi bir gün geri dönüp bizi çok sert vuracağını ve asla
unutamayacağımız bir şekilde etkileyeceğini hepimiz biliriz - bu da en iyi kodu yazma konusunda bir sebep daha!

Geliştirici testlerinin önemli (hatta kritik) olduğunu kabul ettiğimize göre, aynı şekilde testlerin kalitesiyle de
ilgilenmemiz gerekmektedir. Eğer gerçekten, gerçekten iyi olduğunu garanti edemiyorsak, o zaman sadece yanıltıcı bir
güven duygusuna sahip oluruz.

Bu bölümde iki soruya cevap vermeye çalışacağız: test kalitesini nasıl ölçeriz ve nasıl iyileştiririz?

Test kalitesinin genel olarak zor olduğunu keşfedeceğiz, en azından belirli bazı araçları kullanırken. "Methodları
değil, test etme davranışını" başarılı bir şekilde doğrulayabilecek bir araç yoktur veya testlerinizi "methodlarınızı
tutarlı bir şekilde adlandırmanızı" sağlayabilecek bir araç yoktur. Yüksek kaliteli testlere ulaşmak zordur, ancak
imkansız değildir ve doğru prensiplere bağlı kalırsak, belki de onlara ulaşmak o kadar da zor olmaz. Bazı araçlar da
doğru şekilde kullanılırsa yardımcı olabilir.

## 11.1. An Overview

Kalite hakkında konuşurken her zaman "nasıl"dan önce "eğer" sorusunu sormalıyız. "Bu durum için bir integration testi mi
yoksa bir unit testi mi yazarak daha iyi bir sonuca ulaşırım?", veya "Bu senaryoyu gerçekten kapsamam gerekiyor mu -
belki başka bir test tarafından zaten kapsanmış olabilir mi?" gibi sorular, test kodunun tasarımı ve uygulanması üzerine
düşünmeden önce gelmelidir. En yüksek kalitede bile işe yaramaz ve/veya gereksiz bir test yine de, işe yaramaz ve/veya
gereksizdir. Hiç yazılmaması gereken mükemmel testleri yazarak zamanınızı boşa harcamayın!

Bu bölümün geri kalanında, bu ilk "eğer" sorusuna olumlu bir cevap verildiğini varsayacağız. Bu genellikle her
oluşturduğunuz kod parçası için yazılması gereken unit testlerle sıklıkla karşılaşılan bir durumdur.

### Test Smells

Production koduyla uğraşırken, "code smell" terimini, kod içinde doğru görünmeyen çeşitli ifadeleri ifade etmek için
kullanırız. Bu tür code smell'leri toplanmış, isimlendirilmiş ve topluluk içinde genel olarak kabul görmüştür. Ayrıca,
PMD veya FindBugs gibi bazı araçlar, yaygın code smell'lerini bulabilme yeteneğine sahiptir. Test kodu için benzer bir
terim olan "test smell" da vardır, ancak çok daha az yaygın bir şekilde kullanılır. Ayrıca, Production kodu için olduğu
gibi genel olarak kabul görmüş bir test code smells listesi bulunmamaktadır. Tüm bunlar, test kodundaki çeşitli "kötü"
şeylerin sorun olarak kabul edildiğini, ancak Production koduyla olduğu gibi "standartlaşmış" bir şekilde
yaygınlaşmadığını gösterir.

Ayrıca, sözde kod veya test smells birçoğunun, daha genel bir kuralın bazı belirtilerine yakışan çekici isimlerden
başka bir şey olmadığını belirtmek istiyorum. Örneğin, "overriding stubbing test smell" olarak adlandırılan durum, bir
setUp() yönteminde bir stub'ın stub davranışını önce açıkladıktan sonra bazı test methodlarında override etmeniz
durumunda ortaya çıkar. Bu, okunabilirliği azalttığı için kaçınılması gereken bir durumdur. Aslında, "global"
nesnelerden kaçınma gibi genel bir kuralı izlerseniz, zaten bu code smell'i yayınlayan kodu yazmazsınız. Bu nedenle, her
olası test smell'i düşünmeye meyilli değilim. Daha çok umut ederim ki, en iyi programlama uygulamalarını - hem
Production kodunda hem de test kodunda - takip ettiğinizde, birçok test smell basitçe kodunuzda hiç olmayacaktır. Bu
nedenle, kitabın bu bölümünde, kodla çalışırken en sık karşılaştığım kötü uygulamaları açıklayacağım.

### Refactoring

Code Smells hakkında konuşurken, refactoring konusunu da atlamamız mümkün değildir. Daha önce TDD ritmini açıklarken,
refactoring terimini tanıtmış ve bazı refactoring örneklerini vermiştik. Bu bölümde test kodunun refactoring'ini
tartışacağız. Production kodu refactoring'inde olduğu gibi, test kodunun refactoring'i de hem içsel hem de dışsal
kaliteyi artırmaya yardımcı olur.

İçsel kalite açısından, iyi bir şekilde refaktör edilmiş testler daha kolay anlaşılabilir ve bakımı yapılabilir hale
gelir. Dışsal kalite açısından ise, refactoring yaparak testlerimizin gerçekten değerli bir şeyi test etme olasılığını
artırıyoruz. Bu noktada dikkatli bir okuyucu okumayı durdurabilir ve burada bir şeyin doğru olmadığını hissedebilir.

Doğru! Daha önce refactoring'i kodun "testlerin güvenli bir ağı üzerinden hareket etmek" şeklinde tanımlamıştım, ancak
şimdi bu terimi testlerin olmadığı durumlarda gerçekleştirilen bir faaliyeti tanımlamak için kullanıyorum (çünkü testler
için testimiz yok, değil mi?). Evet, doğru! Aslında, test kodunu değiştirmenin bir faaliyetini tanımlamak için
"refactoring" terimini kullanmak, terimin yanlış kullanımıdır. Ancak, bu terimin bu şekilde yaygın bir şekilde
kullanılması nedeniyle başka bir ad vermeye cesaret edemem.

### Code Quality Influences Tests Quality

Bir diğer hatırlanması gereken nokta, Production kodunuzun kalitesi ile test kodunuz arasında güçlü bir ilişkinin
bulunmasıdır. İyi, temiz, bakımı kolay testler yazmak kesinlikle mümkündür. İlk adım, daha temiz, daha iyi tasarlanmış
ve gerçekten loosely coupled Production kodu yazmaktır. Eğer Production kodunuz karmaşık ise, testlerinizin de karmaşık
olma olasılığı oldukça yüksektir. Eğer Production kodunuz düzgün ise, testlerinizin de iyi olma şansı vardır. İkinci
adım, test kodunu Production kodunuzla aynı özenle ele almaya başlamaktır. Bu, test kodunuzun Production koduna
uyguladığınız aynı kurallara (KISS, SRP) uyması anlamına gelir: yani kodunuzda kullanmadığınız şeylerden kaçınmalıdır

(örneğin, reflection methodlarının yaygın kullanımı, derin sınıf hiyerarşileri vb.), kalitesine önem vermelisiniz (
örneğin, statik olarak analiz edilmesi ve kod inceleme sürecine tabi tutulması).

### Always Observe a Failing Test

Testlerin kalitesini ölçme ve iyileştirme yollarını tartışmadan önce, bu konuyla çok ilgisi olan çok önemli bir
tavsiyeyi hatırlatmak istiyorum. Bir şakanın birazına benzese de, bir testin kalitesini kontrol etmenin bir yolu
başarısız olan bir testi gözlemlemektir: eğer başarısız olursa, bu gerçekten bazı davranışları doğruladığını kanıtlar.
Elbette, bu testin değerli olduğu anlamına gelmez, ancak testi gözleri bağlı yazmadıysanız, bir şey ifade eder. Bu
nedenle, TDD'nin RED aşamasını asla atlamamaya ve bir testin başarısız olduğunu gerçekten görerek tanık olmaya
dikkat edin.

Gerçekten de, bu tavsiye öncelikle Test-Driven Development (TDD) takipçileri için mantıklıdır. Ancak bazen testlerinizin
gerçekten bir şeyi test ettiğinden emin olmak için Production kodunu bilerek bozmak da makul olabilir.

## 11.2. Static Analysis Tools

Test kalitesini ölçmek için kullanabileceğimiz ilk yaklaşım, statik kod analizi gerçekleştiren araçları kullanmaktır. Bu
araçlar, çeşitli eksiklikleri bulmak için statik kod analizi yaparlar. Peki, bu araçlar test kodu için de faydalı mıdır?
Buna bir göz atalım.

Muhtemelen Production kodunuzu doğrulamak için statik kod analizörlerini (örneğin, PMD veya Findbugs) kullanıyorsunuz.Bu
araçların amacı, çeşitli anti-pattern'ları keşfetmek ve olası hataları belirtmektir. Çeşitli code smell'lerini
raporlamada oldukça başarılıdırlar. Günümüzde, bu tür araçları ekibin "Yapılacaklar Listesi" (Definition of Done)
parçası olarak kullanmak normal mühendislik uygulamasıdır. Bu tür araçlar genellikle IDE'lerle entegre edilir ve/veya
sürekli entegrasyon sürecinin bir parçasıdır. Doğal bir sonraki adım, test kodunuzu da doğrulamak ve şüpheli bölümlerini
ortaya çıkarmak için bu araçları kullanmaktır.

Evet, bu araçları kullanmalısınız - sonuçta neden kullanmayasınız? Sizin için çok fazla bir maliyeti olmayacaktır, bu
kesin. Sadece build sürecinize dahil edin ve - işte! - test kodunuz doğrulanmış olur. Ama..., ama gerçeklik farklıdır.
Aslında, test kodunuz üzerinde statik analiz yaparken çok fazla beklentiye girmemelisiniz. Bunun nedeni, test kodunuzun
oldukça basit olmasıdır, değil mi? Nadiren iç içe try-catch ifadeleri, opened streams, tekrar kullanılan
değişkenler, derin kalıtım hiyerarşileri, methodlardan birden fazla dönüş, hashCode()/equals() sözleşmesine
uyumsuzluklar veya statik kod analizörlerinin iyi bir şekilde ele aldığı diğer durumlar bulunmaz. Aslında testleriniz bu
kadar basittir: sadece nesneleri hazırlar, methodları çalıştırır ve doğrularsınız. Bu tür araçlar için çok fazla iş yok.

Tam bir resmi sunmak için belirtmeliyim ki, hem PMD hem de Findbugs, özellikle JUnit testlerinde sorunları keşfetmek
için tasarlanmış bazı kurallar (kontroller) sunar. Bununla birlikte, bunların kullanışlılığı oldukça sınırlıdır çünkü
sadece temel kontroller sağlanır. Örneğin, şunları doğrulayabilirler:

* doğru assert ifadeleri kullanılmıştır, örneğin assertEquals(someBoolean, true) yerine assertTrue(someBoolean) ve
  assertTrue(someVariable == null) yerine assertNull(someVariable).
* bir test yönteminde en az bir assert ifadesi bulunmalıdır.
* iki double değer, belirli bir hassasiyet düzeyiyle karşılaştırılmalıdır (ve tam eşitlikle değil).

Statik analizin başka bir formu, sınıf veya method başına düşen satır sayısı gibi çeşitli kod metriklerinin
hesaplanmasıdır ve bunların arasında ünlü döngüsel karmaşıklık (cyclomatic complexity) da bulunur. Bu metrikler
genellikle kodunuzun ne kadar karmaşık olduğunu belirtir. Test sınıfları ve methodları, production kodunuzdan aynı(hatta
daha katı) kurallara uymalıdır, bu yüzden bunları doğrulamak iyi bir fikir gibi görünmektedir. Ancak, testlerinizde
mantık bulunmadığı için (Bölüm 10.2'de tartışıldığı gibi), test yöntemlerinin karmaşıklığı varsayılan olarak çok düşük
olmalıdır. Bu nedenle, böyle bir analiz nadiren test kodunuzda herhangi bir sorunu ortaya çıkaracaktır.

Sonuç olarak, statik analiz araçları test kodu konusunda çok fazla yardımcı olmaz. Hiçbir araç veya metrik size iyi
testler yazıp yazmadığınızı söyleyemez. Onları kullanın, söylediklerine kulak verin, ancak çok fazla beklentiye
girmeyin. Bu bölümde verilen temel kurallara uygun bir şekilde testlerinizi yazarsanız, size çok fazla katkıları
olmayacaktır.

## 11.3. Code Coverage

Statik kod analizi araçlarının test code smell'lerini bulma konusunda sunabileceği şeylerden ciddi hayal kırıklığına
uğradıktan sonra (bkz. Bölüm 11.2), test kalitesini değerlendirmek için yaygın olarak kullanılan başka bir araç grubuna
yönelelim. Bu araçlar, test kodunuzdaki test smell'lerini ve herhangi bir zayıflığı bulmada daha iyi mi? Bu bölüm, kod
coverage tekniğini kullanan popüler araçlar grubuna ayrılmış olup, testlerinizle ilgili ilginç bilgiler sağlayabilirler.
Hem yeteneklerini hem de sınırlarını tartışacağız. Asıl ilgilendiğimiz şey, test kalitesini ölçmek ve umarız ki
geliştirmek için bu araçları kullanıp kullanamayacağımızdır. Şimdilik, bunları çalıştırma ile ilgili teknik konuları bir
kenara bırakacak ve sadece kod coverage araçlarının test kalitesi ölçümünde ne kadar yararlı olduğuna odaklanacağız.

Kod coverage, kodunuzun hangi kısımlarının çalıştırıldığını ölçer: yani hangi production kodunun testler sırasında
çalıştırıldığını belirler. Bu araçlar, production kodunu orijinal kodun anlamını değiştirmeyen ek ifadelerle
zenginleştirerek çalışır. Bu ek talimatların amacı, kodun çalıştırılan bölümleri (satırlar, methodlar, ifadeler)
hakkında bilgi toplamak ve bunu daha sonra kapsama raporlarında sayısal veriler olarak sunmaktır.


