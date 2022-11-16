@test
Feature: Add products to the Cart
  In order to search, add products to the cart
  As an Amazon user
  Users should be able to see products in the Cart

  Background:
    Given I open the Amazon application
    Then Amazon Landing page is displayed

#  Scenario: Login to Amazon application, search and add products to the cart
#    When I logged in to the amazon application
##    When I logged in to the amazon application using valid credentials
##      | Username | Test@gmail.com |
##      | Password | Test@123       |
#    And I searched for the product below
#      | Product name | earphones |
#    Then I see the results of searched products
#    And I decide to choose the product
#    And I selected "2" quantities and added them to my cart
#    Then I see added products in my cart
#    And Finally I decided to log out