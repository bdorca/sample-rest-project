# Használat

IDEA-ban érdemes megnyitni. Az első megnyitás után valószínűleg magát importálja a maven dependencyket.
Ha mégsem, akkor meg kell nyitni a `pom.xml` fájlt, itt felajánlja, hogy automatikusan importál mindent, ezt meg kell engedni neki. :) 
Ha ezután továbbra sem importája be a dolgokat, jobb oldalon van egy maven menü, ennek a bal felső sarkában van egy reimport gomb.

# Futtatás
Az IDEA felső toolbar-ján van egy legördülő menü, itt vannak a különböző configok, ezeket a menü első (Edit Configurations) opciójával lehet módosítani.
Futtatni a zöld nyilacskáva lehet. Debugolni a bogárkával.
Többféleképpen lehet futtatni, ezek az projektben mellékelve vannak:
* embedded tomcat, ez belecsomagol egy Tomcat-et a warba és elindítja. Ezt kicsit könnyebb debugolni, elég csak a zöld bogárkával elindítani.
* wildfly deploy, ez deployol a wildflyba, ezt a `Remote debug` configon keresztül lehet debugolni  
* war file buildelés és kézzel a wildfly standalone könyvtárba másolás (előfordulhat, hogy törölni kell az előző wart és a hozzá tartozó deploy státusz filet is a standalone könyvtárból)

# Config:
A propertyket az `main/resources/application.properties` fileban lehet definiálni. Ezt kell utána felmásolni a megfelelő helyre.

# Wildfly

## Deploy
A pom.xml-ben a `properties` tagen belül kell megadni a wildfly elérését. Ahhoz, hogy így lehessen deployolni, mindenképp kell neki usert csinálni.
Usert a $WILDFLY_HOME/bin/add-user.sh-val lehet létrehozni.

## Config:
A property file helyét a standalone.xml-ben kell megadni. A lenti példához a `$WILDFLY_HOME/standalone/configuration`-be kell tenni a fájlt.
```
<system-properties>
<property name="spring.config.location" value="file:${jboss.server.config.dir}/" />
</system-properties>
```
## Debug
A debugoláshoz a wildflyt debug módban kell indítani. (`--debug`)
```$WILDFLY_HOME/bin/standalone.sh -b 0.0.0.0 -bmanagement 0.0.0.0 --debug```
Induláskor egy ilyesmi sort ír:
```
  JAVA_OPTS:  -server -Xms64m -Xmx512m -XX:MetaspaceSize=96M -XX:MaxMetaspaceSize=256m -Djava.net.preferIPv4Stack=true -Djboss.modules.system.pkgs=org.jboss.byteman -Djava.awt.headless=true -agentlib:jdwp=transport=dt_socket,address=8787,server=y,suspend=n  --add-exports=java.base/sun.nio.ch=ALL-UNNAMED --add-exports=jdk.unsupported/sun.misc=ALL-UNNAMED --add-exports=jdk.unsupported/sun.reflect=ALL-UNNAMED
```
Ebből a lényeg a `-agentlib:jdwp=transport=dt_socket,address=8787,server=y,suspend=n`. Ha a port értéke nem 8787, akkor a projektben levő Remote debug configban át kell írni.
Előfordulhat, hogy nem akar csatlakozni, ilyeynkor a `$WILDFLY_HOME/bin/standalone.conf` fileban kell megkeresni ezt a sort: `#JAVA_OPTS="$JAVA_OPTS -agentlib:jdwp=transport=dt_socket,address=8787,server=y,suspend=n"` és az addresst módosítani erre: `address=*:8787`


# Kód szerkezet
Az Application és a ServletInitializer osztályokhoz átalában nem kell hozzányúlni.

## Rest Controller
Erre több példa is van, a controller packagen belül.
A `GreetingController`-ben van listás, és konkrét id alapú lekérés, valamit egy requestbodyval ellátott post hívás is.
A `ParamTestController` egy darab különböző paraméterekkel ellátott `GET` hívást tartalmaz.
Az `AnimalController` pedig csak azt mutatja be, hogy hogy lehet a különböző komponenseket egymáshoz kapcsolni (`AnimalRepo`). 
  
## DB
Az adatbázis elérést a SpringJdbcConfig osztályban lehet módosítani. Hogy nálam elinduljon, van benne egy H2 db config is, ezt törölni kell az Oracle config használata előtt és "uncomment"-ezni a `@Bean` annotációt.
A konkrét configokat a DatabaseConfig osztályból szedi, ami az application.properties file értékeivel dolgozik. (itt linkeltem egy kis segítséget a configokhoz)
Az `AnimalRepo`-ba injektáltam is a `DataSource`-ot, itt is van egy kis segítség linkelve.
