Feature: Bookstore Shelf - E2E

  @E2E @FAT @API @BST-30
  Scenario: Create a book, based on author to store it on the shelf
    Given a user creates a book
      | id    | title         | description          | pageCount | excerpt | publishDate              |
      | 14525 | The best book | The best description | 25056     | test    | 2025-05-17T09:20:28.807Z |
    And the book exists with the proper info on the DB
    Then user changes the book details
      | title             | description              |
      | The new best book | The new best description |
    And the book exists with the proper info on the DB
    Then user deletes the book
    And the book is no longer on the DB