# Shopping Cart
   * This project is a Java-based shopping cart pricing service. 
   * It provides product price retrieval via REST API calls and includes robust error handling and validation.
   * The service is tested using JUnit and Mockito.

# Features
   * Retrieve product prices by product name.
   * Validates product names for forbidden special characters.
   * Handles missing products, malformed JSON, and network errors.
   * Unit tests for all major scenarios.

# Technologies Used
   1. Java
   2. Gradle
   3. JUnit 5
   4. Mockito
   5. Java HTTP Client
   6. Jackson (for JSON parsing)

# Usage
   1. Add products and prices to your pricing API or mock data.
   2. Call getPrices(String productName) to retrieve the price for a product.
   3. Error Handling: Throws RuntimeException for invalid product names, unavailable products, malformed JSON, or network issues.


# Error Handling:
   * Throws RuntimeException for invalid product names, unavailable products, malformed JSON, or network issues.

# Validation
   * Product names must not contain these forbidden characters: / \ ? % # * : < > | " .  

# Testing
   * Run unit tests using Gradle: gradle test

## Project Structure
   * com.shopping.cart.ProductPrice - Model for product price.
   * com.shopping.cart.PricingService - Main service logic.
   * com.shopping.cart.test.PricingServiceTest - Unit tests.

## Setup
   1. Clone the repository.
   2. Run gradle build to compile.
   3. Run gradle test to execute tests.

## Price API

The price API is an existing API that returns the price details for a product, identified by it's name. The shopping cart should integrate with the price API to retrieve product prices. 

# List of available products
  * `cheerios`
  * `cornflakes`
  * `frosties`
  * `shreddies`
  * `weetabix`

## Example
The below is a sample with the correct values you can use to confirm your calculations

### Inputs
* Add 1 × cornflakes @ 2.52 each
* Add another 1 x cornflakes @2.52 each
* Add 1 × weetabix @ 9.98 each
  
### Results  
* Cart contains 2 x cornflakes
* Cart contains 1 x weetabix
* Subtotal = 15.02
* Tax = 1.88
* Total = 16.90