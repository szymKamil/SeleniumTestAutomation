Feature: : "Przetestowanie sklepu warzywnego"

  Background:
    Given Użytkownik uruchamia aplikację sklepu ważywnego

  Scenario Outline: Użytkownik korzysta z wyszukiwarki w celu szybszego odnalezienia produktu
    Given Użytkownik korzysta z wyszukiwarki wpisując do niej "<produkt>"
    Then Tylko ten jeden produkt jest widoczny na liście

    Examples:
      | produkt |
      | Brocolli |
      | Apple |
      | Mango |
      | Pista |


#  Scenario: Dodanie produktu do koszyka i podsumowanie zamówienia
#    Given Użytkownik wybiera produkty w sklepie i dodaje je do koszyka
#    When Użytkownik otwiera koszyk
#    Then Widoczne są w nim dodane produkty
#    And Użytkownik przechodzi do podsumowania zamówienia
#
#
#  Scenario: Użytkownik korzysta z kodu rabatowego w celu obniżenia kosztu zakupów
#    Given Użytkownik wybiera produkty w sklepie i dodaje je do koszyka
#    When Użytkownik otwiera koszyk
#    And Użytkownik przechodzi do podsumowania zamówienia
#    And Użytkownik korzysta z kodu rabatowego, który obniża cenę jego zamówienia
#    Then Cena zamówienia zostaje obniżona zgodnie z kodem rabatowym
#
#  Scenario: Użytkownik dokonuje zakupu w sklepie
#    Given Użytkownik wybiera produkty w sklepie i dodaje je do koszyka
#    When Użytkownik otwiera koszyk
#    And Użytkownik przechodzi do podsumowania zamówienia
#    And Użytkownik składa zamówienie
#    And Użytkownik wybiera kraj dostawy oraz akceptuje Politykę prywatności i Warunki sklepu
#    Then Użytkownik widzi poprawny komunikat o złożeniu zamówienia