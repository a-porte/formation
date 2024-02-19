# Test suits

This repo aims at synthesising the different ways there is to write tests thanks to Scalatest.

## Sources
- Which [style](https://www.scalatest.org/user_guide/selecting_a_style) to choose?
- Precisions about [Matchers' syntax](http://doc.scalatest.org/2.0/index.html#org.scalatest.Matchers)
- Regarding [Property tables](https://www.scalatest.org/scaladoc/3.2.10/org/scalatest/prop/TableDrivenPropertyChecks.html)

## In a nutshell:
### AnyFunSuite
Simplest test suite
### AnyFlatSpec
Simple test suite, no nested structure (hence the 'flat') and using a modal (i.e. 'can', 'should' or 'must') is mandatory
### AnyFunSpec
Test suite enabling the use of a descriptive and nested structure
### AnyWordSpec
Pretty similar to FunSpec, but with different keywords ('when', 'should' and 'in')
### AnyPropSpec
Adapted for property testing
### AnyFeatureSpec
Intended for acceptance tests


## Syntax
### AnyFunSuite
- test("...")
  - //assertion
### AnyFlatSpec
- " ... " should " ... "
  - //assertion
### AnyFunSpec
- describe("...")
  - it("..." )
    - //assertion
### AnyWordSpec
- "..." when
  - "..." should
    - " ..." in
      - //assertion
### AnyPropSpec 
- property("...")
  - //assertions

  -   
### AnyFeatureSpec
- info("...")
  - Feature("...") 
    - Scenario("...")
      - Given("...")
      - // setup
      - // assertion
      - When ("...")
      - // operation to test
      - Then ("...")
      - // assertion
