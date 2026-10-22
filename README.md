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

Jag skrev mitt första enhetstest för `addTransaction` och skickade in beloppet
`100`, kategorin `"income"` och typen `"Income"`. Jag var osäker på hur
`typeInput` skulle skrivas, och tänkte på mina enum-värden `INCOME` och
`EXPENSE`. Jag kontrollerade med `assertEquals` på storleken av
`getTransactions()` och förväntade mig 1, men listan var tom. Felet kunde i
praktiken ha legat i vilken som helst av de tre parametrarna.
Men var redan på förhand relativt säker på att typeInput parametern var fel.

### Hur felsökte jag den?

Jag gick igenom `addTransaction` och följde anropet in i `parseType`. Där
jämförs indatan mot `"1"` och `"2"`, och allt annat ger `null`. Då avslutas
metoden med `false` innan något läggs i `repo`, och det är därför ingen
transaktion lades till. Jag var ovan vid hur enums och menyval hänger ihop och
behövde hjälp att se hur det egentligen skulle se ut.

### Hur åtgärdade jag den?

Felet låg i testet och inte i programmet. Metoden tar emot menyvalet från
användaren, alltså `"1"` eller `"2"`, och inte ordet "Income". När jag väl
visste var problemet låg var det en enkel fix: jag ändrade testet till att
skicka `"1"` och körde det igen. Då blev `size()` 1 som jag förväntade mig och
testet blev grönt. Jag lade också till en kontroll av returvärdet med
`assertTrue` och skrev ett test som kontrollerar att en ogiltig typ ger `false`
och att ingenting läggs till.

