
![alt text](Velvet_Logo.png)  
### Developed By: Vincent Galloro
Velvet is a Java / Kotlin Library for faster application development.

When you have a great application idea, you can often find yourself spending too much time writing UI boilerplate, or re-writing the same code you've written in dozens of other projects.

The idea behind Velvet was to be a collection of all the useful code I've ever written. This way, I would have a library of utilities to draw from when starting a new project.

## Packages
Velvet contains the following packages:  

### Main 
* For setting up a window with an update and render loop
* Contains a keyboard and mouse object, and a basic graphics object

### VElements 
For all UI related objects
* Basic visual elements for text, image, or shape content. 
* VContainers, which store the visual elements, and travel smoothly around the screen
* Interaction handlers for mouse and keyboard input

### Multithreading
* Bot object. Can be given a list of jobs, which it will execute asynchronously
* Support for bot pools, which operate on a single job queue 

### IO
* Readers and Writers for writing custom objects to and from files, or other data-streams
* Versioning support for migrating older save files to newer program versions
* Rolling backup support for recovering to backups in-case of data loss or crashes

### Web 
For simple web-scraping
* Contains template classes for scraping data from the web
* Classes for navigating a webpage, as well as interacting with buttons / forms

### Structs 
Data structures and utility objects
* A Vector and Position class for storing 2D coordinates with built in arithmetic functions
* Data structures such as the Disjoint Set, 'Auto Set', and 'Shadow Map'
* Time related data objects

## Still to come...
Velvet is still being developed.

Demos and Documentation coming soon!
