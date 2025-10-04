Feature: Logowanie do aplikacji za pomocą formularza

  Background:
    Given Użytkownik uruchamia aplikację

  @positive
  Scenario Outline: Użytkownik poprawnie loguje się do aplikacji
    When Użytkownik wpisuje "<login>" oraz "<hasło>" w pola i loguje się do aplikacji
    Then Użytkownik widzi poprawny komunikat o zalogowaniu się a następnie wylogowuje się


    Examples:
      | login    | hasło     |
      | Janusz | rahulshettyacademy  |

