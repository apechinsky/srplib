# SRPLIB

  A collection of highly specialized libraries. Each library is designed with Single Responsiblity Principle 
  (http://en.wikipedia.org/wiki/Single_responsibility_principle) in mind. 

## Modules

  - [contract](contract.md) - method and class contract support
  - [conversion](conversion.md) - general purpose converter interface and infrastructure
  - support  - support library for other modules
  - model - general purpose model
  - binding - general purpose binding (experimental)
  - validation - genral purpose validation (experimental)

## Structure
  
  Each library includes main module and optional support module. 
  
Main module:
  
  - defines core interfaces and classes
  - does not provide "useful" implementaions 
  - does not provide helper classes, factories, syntax sugar, etc.
    
Optional module:

  - provides useful implementations
  - privides factories and helper classes

