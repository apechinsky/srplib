# SRPLIB

  A collection of lightweight and highly specialized libraries for java. 
  
## Primary goal

  Continue "mission" of SLF4J. Provide the same service for other development aspects.     

## Libraries

  - [contract](contract.md) - method and class contract support (Assert/Argument)
  - [conversion](conversion.md) - general purpose converter interface and it's infrastructure
  - [reflection](reflection.md) - simplify reflection API usage
  - support  - support library for other modules
  - model - general purpose model
  - binding - general purpose binding (experimental)
  - validation - general purpose validation (experimental)
  
## Design principles  
  
### Single Responsibility Principle (SRP) 
  
  Each library has clear focus on particular aspect. Library evolution should not change this.
  
###  20-80 rule
 
  Provide only essential functionality.    

### Context independence

  Libraries should not depend on particular context (framework, other library, UI technology, etc.) and should be 
  suitable for mobile as well as large enterprise applications.  



## Structure
  
  Each library includes main module and optional support module. 
  
Main module:
  
  - defines core interfaces and classes
  - does not provide "useful" implementations 
  - does not provide helper classes, factories, syntax sugar, etc.
    
Optional module:

  - provides useful implementations
  - provides factories and helper classes

