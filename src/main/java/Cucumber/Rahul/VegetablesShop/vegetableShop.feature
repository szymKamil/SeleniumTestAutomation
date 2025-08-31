# language: pl

Potrzeba biznesowa: Przetestowanie sklepu warzywnego

  Założenia: Użytkownik znajduje się w aplikacji sklepu

  Szablon scenariusza: : Użytkownik korzysta z wyszukiwarki w celu szybszego odnalezienia produktu
    Zakładając, że użytkownik skorzystał z wyszukiwarki sklepu i wpisał do niej nazwę "<produkt>"
    Wtedy tylko wyszukiwany produkt jest widoczny na liście

    Przykłady:
      | produkt  |
      | Brocolli |
      | Apple    |
      | Mango    |
      | Pista    |


  Scenariusz: : Użytkownik korzysta z wyszukiwarki w celu szybszego odnalezienia kilku produktów
    Kiedy użytkownik skorzystał z wyszukiwarki sklepu i wpisał do niej produkty:
      | Brocolli    |
      | Water Melon |
      | Banana      |
      | Strawberry  |
    Wtedy tylko wyszukiwany produkt jest widoczny na liście


  Scenariusz: Dodanie produktu i wyświetlenie go w koszyku
    # Test ma na celu sprawdzenie, czy wybranie ilości produktów i dodanie produktu do koszyka poprawnie go w nim wyświetli
    Zakładając, że użytkownik dodał do koszyka następujące produkty:
      | Orange       | 4     |
      | Water Melon      | 3     |
      | Brocolli  | 10     |
      | Almonds     | 3    |
    Kiedy użytkownik otwiera koszyk
    Wtedy widoczne są w nim dodane produkty


  Scenariusz: Użytkownik korzysta z kodu rabatowego w celu obniżenia kosztu zakupów
    Zakładając, że użytkownik wybiera produkty w sklepie i dodaje je do koszyka
    Kiedy użytkownik wyświetla koszyk
    I użytkownik przechodzi do podsumowania zamówienia
    I użytkownik korzysta z kodu rabatowego, który obniża cenę jego zamówienia
    Wtedy cena zamówienia zostaje obniżona zgodnie z kodem rabatowym

  Scenariusz: Użytkownik dokonuje zakupu w sklepie
  Given Użytkownik wybiera produkty w sklepie i dodaje je do koszyka
  When Użytkownik otwiera koszyk
  And Użytkownik przechodzi do podsumowania zamówienia
  And Użytkownik składa zamówienie
  And Użytkownik wybiera kraj dostawy oraz akceptuje Politykę prywatności i Warunki sklepu
  Then Użytkownik widzi poprawny komunikat o złożeniu zamówienia