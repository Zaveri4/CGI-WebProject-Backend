# CGI-WebProject-Backend

See on lennureisijale lennu planeerimise ja lennukis istekohtade soovitamise veebirakendus,
mis võimaldab valida endale sobivat lennureisi ja saada istekohti enda soovituste põhjal.
Kasutaja saab valida lennureisi kasutades erinevaid filtreid (kas leida lennureisi sihtkoha, hinna või kuupäeva järgi).
Kui kasutaja leidis endale sobivat reisi, siis talle pakutakse välja vaba istekohta/istekohti ja ta saab ise valida, mida ta eelistab.
Näiteks, kas kasutaja tahab istekohta akna all, kas rohkem jalaruumi või lähedal väljapääsule.
Kui aga kasutaja plaanib osta mitu piletit, siis talle pakutakse välja istekohti, mis on kõrvuti.
Iga rakenduse käivitamisel lennureise ja istekohti juhuslikult genereeritakse.

## Tech Stack
- **Java 23**
- **Spring Boot 3.4.3**
- **Spring Data JPA** (PostgreSQL)
- **Liquibase** (andmebaasi migratsiooniks)
- **Lombok**
- **MapStruct** (for DTO mapping)
- **JUnit 5 + Mockito** (testimiseks)
- **Springdoc OpenAPI** (API documentatsiooniks)
- **Docker** (rakenduse ja andmebaasi käevitamiseks)

## Kuidas rakendust käivatada

1. Esiteks on vaja klonida repository kas IntelliJ'sse või teise IDE.
2. Järgmiseks on vaja rakendust build'ida, mis on võimalik teha kirjutades käsureas: `./gradlew clean build`
3. Lõpuks on vaja luua ja käivatada backend ja andmebaasi Docker'i konteinereid kirjutades käsureas: `docker-compose up -d --build`

Kui kõik on õigesti tehtud, siis on backend peaks töötama, mida on võimalik kontrollida kasutades **Swagger UI**.
Selleks, et avada Swagger'it on vaja minna `http://localhost:8080/swagger-ui.html` ja juba sealt võiks proovida HTTP päringut saata.

## Aega kuulunud backendi loomiseks umbes 20 tundi.

## Raskused
- Peamiseks raskuseks ma võin tuua see, et rakendus crash'is päris palju kordi, sest olid mõned probleemid sõltuvustega.
Esialgselt ma plaanisin Maven'i põhjal projekti teha, kuna oli niisugune kogemus, aga plobleeme sellega oli väga palju,
siis otsustasin migreeruda Gradle ja probleemid kaotasid ära.
- Teine probleemne koht oli suggestSeat meetodi loogika implementeerimine, kuna see nõudis leida neid kohti,
mis peavad olema kõrvuti, aga samal ajal täitma tingimusi, mis oli filtritega pandud.
Et õigesti valida sobivaid kohti ma kasutasin ChatGPT, et see aitaks loogikat välja mõelda.
- Viimane raskuste koht oli seotud testidega. Mõni aega veetsin selle peale, et kirjutada Integration teste.
Kasutan inspiratsiooniks oma ülikooli projekti, kus oli olemas AbstractIntegrationTest, mis päris palju aitas testide kirjutamisel.
Ülikooli projekti repo: `https://gitlab.cs.taltech.ee/kokiss/iti0302-2024-backend`
