# Lab 04 — JUnit 5 нэгжийн тест

**Нэр:** Ц.Бэлгүтэй
**Код:** B232270053

## java -version

```
openjdk version "23.0.2" 2025-01-21
OpenJDK Runtime Environment Homebrew (build 23.0.2)
OpenJDK 64-Bit Server VM Homebrew (build 23.0.2, mixed mode, sharing)
```

## mvn -version

```
Apache Maven 3.9.9 (8e8579a9e76f7d015ee5ec7bfcdc97d260186937)
Maven home: /opt/homebrew/Cellar/maven/3.9.9/libexec
Java version: 23.0.2, vendor: Homebrew, runtime: /opt/homebrew/Cellar/openjdk/23.0.2/libexec/openjdk.jdk/Contents/Home
Default locale: en_US, platform encoding: UTF-8
OS name: "mac os x", version: "14.5", arch: "aarch64", family: "mac"
```

## Үр дүн

| Үзүүлэлт | Утга |
|---|---|
| Тестийн методын тоо (`@Test` + `@ParameterizedTest`) | 11 (6 `@Test`, 5 `@ParameterizedTest`) |
| `results/mvn-test.txt` дахь `Tests run` | 38 |
| Мутаци (`score >= 90` → `score > 90`) | `Tests run: 38, Failures: 2`, BUILD FAILURE |
| Унасан тестүүд | `ninetyIsExactlyA`, `letterGradeBoundaries[3]` (`90,A` мөр) |
| Мутацийг буцаасны дараа | `Tests run: 38, Failures: 0`, BUILD SUCCESS |

Тестийн тоо (38) методын тооноос (11) их байгаа шалтгаан нь Surefire нь `@CsvSource` болон `@ValueSource`-ийн мөр бүрийг тусдаа тест гэж тоолдог явдал юм.

## Дүгнэлт

`GradeCalculator` классыг хэрэгжүүлсэн бөгөөд `letterGrade` болон `totalScore`-д зориулан AAA бүтэцтэй, `@DisplayName`-тэй 11 тестийн метод бичсэн бөгөөд 5 нь parameterized тест байв. Мутацид `score >= 90`-ийг `score > 90` болгоход `ninetyIsExactlyA` болон `letterGradeBoundaries`-ийн `90,A` мөр гэсэн 2 тест унасан (`expected: <A> but was: <B>`). Хоёул нэг л шалтгаантай — оролт яг 90 байх үед л энэ алдаа илэрдэг. 95, 85, 89.99 зэрэг бусад утгын тестүүд мутантад ч ногоон хэвээр үлдсэн тул зөвхөн ердийн утгаар шалгасан бол алдаа илрэхгүй байх байлаа. Энэ нь ногоон тест нь зөв тест гэсэн үг биш, хязгаарын утгыг заавал яг өөр дээр нь шалгах ёстойг миний хувьд хамгийн тод харуулсан. Анхны оролдлогод macOS-ийн `sed -i` синтакс буруу байсан тул код өөрчлөгдөөгүй, мутацийн ажиллагаа `BUILD SUCCESS` гарсан; `git diff`-ээр мутаци үнэхээр орсныг шалгасны дараа л зөв нотолгоо гарсан. Мөн тестийн классыг анх `src/main` дотор буруу байрлуулсан нь JUnit-ийн `test` scope-той холбоотой compile алдаа өгсөн бөгөөд `src/test`-д зөөж засав. Өөрийн сонирхлоор `letterGrade`-д `NaN` оролтыг мөн `IllegalArgumentException`-ээр шалгах тест нэмсэн.