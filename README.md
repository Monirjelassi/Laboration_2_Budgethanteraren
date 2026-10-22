# Budgethanteraren

## Beskrivning

Budgethanteraren är en konsolapplikation där användaren registrerar inkomster
och utgifter, kategoriserar dem och får en sammanställning av sin ekonomi.
Programmet har en meny för att lägga till transaktioner, visa alla, visa saldo
och summa per kategori, filtrera på typ (inkomst eller utgift) och spara till
fil. Transaktionerna sparas som CSV i `transactions.csv` och läses in
automatiskt vid nästa start. Saknas filen startar programmet med en tom lista.

## Struktur
Lösningen är uppdelad så att varje klass har ett ansvar. `Menu` sköter all
inmatning och utskrift, `TransactionService` innehåller logiken och
`TransactionFileHandler` läser och skriver filen. Transaktionerna lagras i en
egen generisk klass, `Repository<T>`, och en transaktion är ett `record`.
Felaktig indata, till exempel ett belopp på noll eller en tom kategori, avvisas
med det egna undantaget `InvalidTransactionException`.
Trasiga rader i filen ger `FileFormatException` med hänvisning till radnummer. 
Fel loggas med `java.util.logging`. Logiken testas med JUnit.

## Felsökning

### Hur upptäckte jag buggen?



### Hur felsökte jag den?



### Hur åtgärdade jag den?


