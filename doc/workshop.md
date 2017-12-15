Vježba 1

1. Kreirajte model klasu UserMessage sa sljedećim field-ovima:
    
    - private String from;  
    - private String to;  
    - private String text;
    - private int priority;
    
    te pripadajućim getterima /setterima te praznim konstuktorom i all-args konstruktorom  
    dodajte toString metodu koja ispisuje sve field-ove
    
2. Implementirajte UserMessageSerializer i UserMessageDeserializer (hint: koristite json kao prijelazni format)

3. Kreirajte topic user-messages koristeći kafka-topics komandu:  
    _./bin/kafka-topics --zookeeper localhost:2181 --create --topic user-messages --partitions 2 --replication-factor 1_
    
4. Implementirajte producer nazova SimpleProducer:
    - u beskonačnoj petlji kreira nove UserInfo objekte te ih šalje u topic user_messages svakih 500 milisekundi
    - kreirani objekti neka imaju nasumice odabrana from i to polja koja ste predefinirali u nekom string nizu od nekoliko imena
    - vrijednost priority polja neka bude random broj od 1 do 5 
    - vrijednost text polje generirajte po volji (npr. uvijek fiksna poruka,  nasumićno iz nekog predefiniranog niza poruka ili koristite random utils za generiranje razlicitih poruka)
    - key kafka recorda neka bude from polje
    - Producer treba imati main metodu kako bi ga  mogli samostalno pokrenuti
    - Uspješno poslane poruke ispisujte na system output
    
5. Implementirajte consumer naziva SimpleConsumer koji čita poruke iz user-messages topica i prikazuje ih na system output
    - Consumer treba imati main metodu kako bi ga mogli samostalno pokrenuti

6. Pokrenite producera

7. Pokrenite jedan consumer i promatrajte output - da lis e poruke primaju i ispisuju na output?

8. Pokrenite još jedan consumer (ne gaseći prvi) i promatrajte output na oba

9. Pokrenite i treći consumer (ne gaseći prva dva) i promatrajte output

10. Ugasite drugi consumer i promatrajte output preostala dva
    - Da li se poruke sa istim "from" field-om pojavljuju uvijek na istom consumeru? Objasnite zašto.
 
11. Ugasite prvi consumer i promatrajte output, koja promjena se dogodila i zašto?
    
    
Vježba 2

0. Kreirajte Serde naziva UserMessageSerde za serijalizaciju i deserijalizaciju UserMessage objekata 
    - iskoristite postojeće UserMessageSerializer i UserMessageDeserializer pri implementaciji

1. Implementirajte SimpleConsumer iz vježbe 1 pomoću KafkaStreams API-a, (nazovimo ga StreamingTopicConsumer)
     - Kreirajte instancu StreamsBuilder-a
     - koristeći builder kreirajte instancu KStream-a koji čita poruke iz topica "user-messages"  i nazovite ga inputStream
     - koristite metodu "foreach" na inputStream objektu da bi ispisali poruke na system output.
     - Kreirajte instancu KafkaStreams 
     - Pokrenite KafkaStreams instancu
    
2. Implementirajte streaming aplikaciju naziva StreamingApplication1 koja:
    
    - čita poruke iz topica user-messages, filtira one koji imaju priority field vrijednost veću od 3 
    i njih upisuje u topic "high-priority-messages"
    
    Koraci
        - setupirajte KafkaStreams kao u zadatku 1
        - kreirajte inputStream objekt kao u zadatku 1
        - koristeći "filter" metodu na inputStream, filtrirajte poruke prioriteta 4 ili 5
        - koristeći "to" metodu filtirane poruke zapišite u topic "high-priority-messages"
        - Kreirajte instancu KafkaStreams 
        - Pokrenite KafkaStreams instancu
    
    - Prilagodite StreamingTopicConsumer iz koraka 1 da bi čitao poruke iz topica "high-priority-messages" i pokrenite ga.
        Da li vidite samo poruke prioriteta 4 i 5?
        
3. Implementirajte streaming aplikaciju naziva StreamingApplication2 koja 
    - čita poruke iz topica user-messages kao u zadatku 1 i 2
    - agregira poruke po senderu ("from" field) i generira KTable instancu naziva countsTable 
    u kojoj ce svaki "redak" sadržavati ime sendera (from) i broj poruka koji je on poslao
    - ispisuje countsTable u topic "user-msg-counts" 
    Koraci
        - setupirajte KafkaStreams kao u zadatku 1
        - kao i u koraku 1, kreirajte instancu KStream-a koji čita poruke iz topica "user-messages"  i nazovite ga inputStream
        - Koristite groupByKey metodu na inputStreamu da dobijet KGroupedStream objekt
        - na KGroupedStream objektu koristite mtodu count da bi dobili broj poruka u grupi
        - rezultat je KTable koji za key ima ime sendera a value je broj poruka koje je on poslao
        - koristite metodu "toStream" na KTable objektu, a zatim "to" metodu na dobivenom KStream objektu 
        da bi rezultat upisali u topic "high-priority-msg-counts"
    - Implementirajte streaming consumer naziva MessageCountsConsumer (slično kao StreamingTopicConsumer iz zadatka 1 ) 
    koji čita poruke iz topica "user-msg-counts" i pokrenite ga.
        - promatrajte output
        
    
        
4. Implementirajte streaming aplikaciju naziva StreamingApplication3 
koja kombinira funkcionalnost aplikacija StreamingApplication1 i StreamingApplication2
    
    - koristite metodu "through" na KStream objektu kako bi upis filtriranih poruka u topic "high-priority-messages" 
    napravili u istom koraku u kojem agregiramo i brojimo filtrirane poruke i upisujemo ih u topic "high-priority-msg-counts"
    
    - prilagodite MessageCountsConsumer iz zadatka 3 ove vježbe, da bi čitali poruke iz topica "high-priority-msg-counts"
        -promatrajte output
     
     


     